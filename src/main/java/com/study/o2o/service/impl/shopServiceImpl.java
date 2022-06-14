package com.study.o2o.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.o2o.dao.ShopAuthMapDao;
import com.study.o2o.dao.shopDao;
import com.study.o2o.dto.imageHolder;
import com.study.o2o.dto.shopExecution;
import com.study.o2o.entity.Shop;
import com.study.o2o.entity.ShopAuthMap;
import com.study.o2o.enums.shopStateEnum;
import com.study.o2o.exceptions.shopOperationException;
import com.study.o2o.service.ShopService;
import com.study.o2o.util.imageUtil;
import com.study.o2o.util.pageCalculator;
import com.study.o2o.util.pathUtil;

@Service
public class shopServiceImpl implements ShopService {
	private final static Logger LOG = LoggerFactory.getLogger(shopServiceImpl.class);
	@Autowired
	private shopDao shopDao;
	@Autowired
	private ShopAuthMapDao shopAuthMapDao;

	@Override
	// 事务支持
	@Transactional
	public shopExecution addShop(Shop shop, imageHolder thumbnail) {
		// 控制判断
		if (shop == null) {
			return new shopExecution(shopStateEnum.NULL_SHOP);
		}
		try {
			// 给店铺信息赋值
			shop.setEnableStatus(0);
			shop.setCreateTime(new Date());
			shop.setLastEditTime(new Date());
			// 添加店铺信息
			int effectedNum = shopDao.insertShop(shop);
			if (effectedNum <= 0) {
				LOG.error("添加店铺信息的时候，返回0条变更");
				throw new shopOperationException("店铺创建失败");
			} else {
				if (thumbnail.getImage() != null) {
					// 存储图片
					try {
						addShopImg(shop, thumbnail);
					} catch (Exception e) {
						LOG.error("addShopImg Error:" + e.getMessage());
						throw new shopOperationException("添加店铺图片失败");
					}
					// 更新店铺的图片地址
					effectedNum = shopDao.updateShop(shop);
					if (effectedNum <= 0) {
						LOG.error("更新图片地址失败");
						throw new shopOperationException("添加店铺图片失败");
					}
					// 执行增加shopAuthMap操作
					ShopAuthMap shopAuthMap = new ShopAuthMap();
					shopAuthMap.setEmployee(shop.getOwner());
					shopAuthMap.setShop(shop);
					shopAuthMap.setTitle("店家");
					shopAuthMap.setTitleFlag(0);
					shopAuthMap.setCreateTime(new Date());
					shopAuthMap.setLastEditTime(new Date());
					shopAuthMap.setEnableStatus(1);
					try {
						effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
						if (effectedNum <= 0) {
							LOG.error("addShop:授权创建失败");
							throw new shopOperationException("授权创建失败");
						}
					} catch (Exception e) {
						LOG.error("insertShopAuthMap error:" + e.getMessage());
						throw new shopOperationException("授权创建失败");
					}
				}
			}
		} catch (Exception e) {
			LOG.error("addShop error" + e.getMessage());
			throw new shopOperationException("创建店铺失败,请联系管理员");
		}

		return new shopExecution(shopStateEnum.CHECK, shop);
	}

	private void addShopImg(Shop shop, imageHolder thumbnail) {
		// 获取shop图片目录的相对路径值
		String dest = pathUtil.getShopImagePath(shop.getShopId());
		String shopImgAddr = imageUtil.generateThumbnail(thumbnail, dest);
		shop.setShopImg(shopImgAddr);
	}

	@Override
	public Shop getByShopId(long shopId) {
		// 调用dao层直接返回值即可
		return shopDao.queryByShopId(shopId);
	}

	@Override
	@Transactional
	public shopExecution modifyShop(Shop shop, imageHolder thumbnail) throws shopOperationException {
		// 判断商铺是否存在
		if (shop == null || shop.getShopId() == null) {
			return new shopExecution(shopStateEnum.NULL_SHOP);
		} else {
			// 1.判断是否需要处理图片
			try {
				// 判断是否有需要处理的图片
				if (thumbnail != null && thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
					Shop tempShop = shopDao.queryByShopId(shop.getShopId());
					if(tempShop.getShopImg() != null) {
						imageUtil.deleteFileOrPath(tempShop.getShopImg());
					}
					addShopImg(shop, thumbnail);
				}
				// 2.更新店铺信息
				shop.setLastEditTime(new Date());
				int effectedNum = shopDao.updateShop(shop);
				if (effectedNum <= 0) {
					return new shopExecution(shopStateEnum.INNER_ERROR);
				} else {
					shop = shopDao.queryByShopId(shop.getShopId());
					return new shopExecution(shopStateEnum.SUCCESS, shop);
				}
			} catch (Exception e) {
				throw new shopOperationException("modifyShop error: " + e.getMessage());
			}
		}

	}

	@Override
	public shopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
		int rowIndex = pageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
		int count = shopDao.queryShopCount(shopCondition);
		shopExecution se = new shopExecution();
		if (shopList != null) {
			se.setShopList(shopList);
			se.setCount(count);
		} else {
			se.setState(shopStateEnum.INNER_ERROR.getState());
		}
		return se;
	}
}
