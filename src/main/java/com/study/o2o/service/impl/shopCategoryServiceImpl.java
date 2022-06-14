package com.study.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.o2o.cache.JedisUtil;
import com.study.o2o.dao.shopCategoryDao;
import com.study.o2o.dto.ShopCategoryExecution;
import com.study.o2o.dto.imageHolder;
import com.study.o2o.entity.shopCategory;
import com.study.o2o.enums.shopCategoryStateEnum;
import com.study.o2o.exceptions.headLineOperationException;
import com.study.o2o.exceptions.shopCategoryOperationException;
import com.study.o2o.service.shopCategoryService;
import com.study.o2o.util.imageUtil;
import com.study.o2o.util.pathUtil;

@Service
public class shopCategoryServiceImpl implements shopCategoryService {

	@Autowired
	private shopCategoryDao shopCategoryDao;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private JedisUtil.Strings jedistStrings;

	@Override
	public List<shopCategory> getShopCategoryList(shopCategory shopCategoryCondition) {
		// 定义redis的key前缀
		String key = SCLISTKEY;
		// 定义接收对象
		List<shopCategory> shopCategoryList = null;
		// 定义Json数据转换操作类
		ObjectMapper mapper = new ObjectMapper();
		// 拼接出redis的key
		if (shopCategoryCondition == null) {
			// 若查询条件为空，则列出所有的首页大类，即parentId为空的店铺类别
			key = key + "_allfirstlevel";
		} else if (shopCategoryCondition != null && shopCategoryCondition.getParent() != null
				&& shopCategoryCondition.getParent().getShopCategoryId() != null) {
			// 若parentId非空,则列出该parentId下的所有子类别
			key = key + "_parent" + shopCategoryCondition.getParent().getShopCategoryId();
		} else if (shopCategoryCondition != null) {
			// 列出所有子类别，不管其输入哪个类，都列出来
			key = key + "_allsecondlevel";
		}
		// 判断key是否存在
		if (!jedisKeys.exists(key)) {
			// 若不存在，则从数据库中取出相应数据
			shopCategoryList = shopCategoryDao.queryShopCategory(shopCategoryCondition);
			for (shopCategory shopCategory : shopCategoryList) {
				System.out.println(shopCategory.toString());
			}
			// 将相关的实体类集合转换成string,存入redis里面对应的key中
			String jsonString;
			try {
				jsonString = mapper.writeValueAsString(shopCategoryList);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				throw new shopCategoryOperationException(e.getMessage());
			}
			jedistStrings.set(key, jsonString);
		} else {
			// 若存在直接从redis中取出相应的数据
			String jsonString = jedistStrings.get(key);
			System.out.println("jsonString "+jsonString);
			// 指定要将string转换成的集合类型
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, shopCategory.class);
			try {
				shopCategoryList = mapper.readValue(jsonString, javaType);
			} catch (JsonParseException e) {
				e.printStackTrace();
				throw new shopCategoryOperationException(e.getMessage());
			} catch (JsonMappingException e) {
				e.printStackTrace();
				throw new shopCategoryOperationException(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				throw new shopCategoryOperationException(e.getMessage());
			}
		}
		return shopCategoryList;
	}

	@Override
	@Transactional
	public ShopCategoryExecution addShopCategory(shopCategory shopCategory, imageHolder thumbnail) {
		// 空值判断
		if (shopCategory != null) {
			// 设置默认值
			shopCategory.setCreateTime(new Date());
			shopCategory.setLastEditTime(new Date());
			if (thumbnail != null) {
				// 若上传有图片流，则进行存储操作，并给shopCategory实体类设置上相对路径
				addThumbnail(shopCategory, thumbnail);
			}
			try {
				// 往数据库添加店铺信息
				int effectedNum = shopCategoryDao.insertShopCategory(shopCategory);
				if (effectedNum > 0) {
					// 删除店铺类别之前在redis中存储的一切key,for简单实现
					deleteRedis4ShopCategory();
					return new ShopCategoryExecution(shopCategoryStateEnum.SUCCESS);
				} else {
					return new ShopCategoryExecution(shopCategoryStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new shopCategoryOperationException("添加店铺类别信息失败:" + e.toString());
			}
		} else {
			return new ShopCategoryExecution(shopCategoryStateEnum.EMPTY);
		}
	}

	@Override
	@Transactional
	public ShopCategoryExecution modifyShopCategory(shopCategory shopCategory, imageHolder thumbnail) {
		// 空值判断，主要判断shopCategoryId不为空
		if (shopCategory.getShopCategoryId() != null && shopCategory.getShopCategoryId() > 0) {
			// 设定默认值
			shopCategory.setLastEditTime(new Date());
			if (thumbnail != null) {
				// 若上传的图片不为空，则先获取之前的图片路径
				shopCategory tempShopCategory = shopCategoryDao.queryShopCategoryById(shopCategory.getShopCategoryId());
				if (tempShopCategory.getShopCategoryImg() != null) {
					// 若之前图片不为空，则先移除之前的图片
					imageUtil.deleteFileOrPath(tempShopCategory.getShopCategoryImg());
				}
				// 存储新的图片
				addThumbnail(shopCategory, thumbnail);
			}
			try {
				// 更新数据库信息
				int effectedNum = shopCategoryDao.updateShopCategory(shopCategory);
				if (effectedNum > 0) {
					// 删除店铺类别之前在redis里存储的一切key,for简单实现
					deleteRedis4ShopCategory();
					return new ShopCategoryExecution(shopCategoryStateEnum.SUCCESS, shopCategory);
				} else {
					return new ShopCategoryExecution(shopCategoryStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new shopCategoryOperationException("更新店铺类被信息失败:" + e.toString());
			}
		} else {
			return new ShopCategoryExecution(shopCategoryStateEnum.EMPTY);
		}
	}
	
	@Override
	@Transactional
	public ShopCategoryExecution removeShopCategoryById(long shopCategoryId) {
		// 空值判断，主要判断shopCategoryId不为空
		if (shopCategoryId > 0) {
			try {
				// 根据Id查询头条信息
				shopCategory tempShopCategory = shopCategoryDao.queryShopCategoryById(shopCategoryId);
				if (tempShopCategory.getShopCategoryImg() != null) {
					// 若头条原先存有图片， 则将该图片文件删除
					imageUtil.deleteFileOrPath(tempShopCategory.getShopCategoryImg());
				}
				// 删除数据库里对应的头条信息
				int effectedNum = shopCategoryDao.deleteShopCategory(shopCategoryId);
				if (effectedNum > 0) {
					//删除Redis中对应的值
					deleteRedis4ShopCategory();
					return new ShopCategoryExecution(shopCategoryStateEnum.SUCCESS);
				} else {
					return new ShopCategoryExecution(shopCategoryStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new headLineOperationException("删除类别信息失败:" + e.toString());
			}
		} else {
			return new ShopCategoryExecution(shopCategoryStateEnum.EMPTY);
		}
	}

	@Override
	public shopCategory getShopCategoryById(Long shopCategoryId) {
		return shopCategoryDao.queryShopCategoryById(shopCategoryId);
	}

	/**
	 * 存储图片
	 * 
	 * @param shopCategory
	 * @param thumbnail
	 */
	private void addThumbnail(shopCategory shopCategory, imageHolder thumbnail) {
		String dest = pathUtil.getShopCategoryPath();
		String thumbnailAddr = imageUtil.generateNormalImg(thumbnail, dest);
		shopCategory.setShopCategoryImg(thumbnailAddr);
	}

	private void deleteRedis4ShopCategory() {
		String prefix = SCLISTKEY;
		// 获取跟店铺类别相关的redis key
		Set<String> keySet = jedisKeys.keys(prefix + "*");
		for (String key : keySet) {
			// 逐条删除
			jedisKeys.del(key);
		}
	}

}
