package com.study.o2o.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.o2o.dao.productDao;
import com.study.o2o.dao.productImgDao;
import com.study.o2o.dto.imageHolder;
import com.study.o2o.dto.productExecution;
import com.study.o2o.entity.Product;
import com.study.o2o.entity.productImg;
import com.study.o2o.enums.productStateEnum;
import com.study.o2o.exceptions.productOperationException;
import com.study.o2o.service.productService;
import com.study.o2o.util.imageUtil;
import com.study.o2o.util.pageCalculator;
import com.study.o2o.util.pathUtil;

@Service
public class productServiceImpl implements productService{
	@Autowired
	private productDao productDao;
	@Autowired
	private productImgDao productImgDao;
	@Override
	@Transactional
	//1.处理缩略图，获取缩略图相关路径并赋值给product
	//2.向tb_produc写入商品信息，获取proudctId
	//3.结合productId批处理商品详情图
	//4.将商品详情图列表批量插入tb_product_img
	public productExecution addProduct(Product product, imageHolder thumbnail, List<imageHolder> productImgHolderList)
			throws productOperationException {
		//空值判断
		if(product!=null && product.getShop()!=null && product.getShop().getShopId()!=null) {
			//为商品设置上默认属性
			product.setCreateTime(new Date());
			product.setLastEditTime(new Date());
			//默认上架状态
			product.setEnableStatus(1);
			//若商品缩略图不为空则添加
			if(thumbnail != null) {
				addThumbnail(product,thumbnail);
			}
			try {
				int effectedNum = productDao.insertProduct(product);
				if(effectedNum<=0) {
					throw new productOperationException("创建商品失败");
				}
			} catch (Exception e) {
				throw new productOperationException("创建商品失败"+e.toString());
			}
			//若商品详情图不为空则添加
			if(productImgHolderList != null && productImgHolderList.size()>0) {
				addProductImgList(product, productImgHolderList);
			}
			return new productExecution(productStateEnum.SUCCESS,product);
		}else {
			return new productExecution(productStateEnum.EMPTY);
		}
	}
	/**
	 * 添加缩略图
	 * @param product
	 * @param thumbnail
	 */
	private void addThumbnail(Product product,imageHolder thumbnail) {
		String dest = pathUtil.getShopImagePath(product.getShop().getShopId());
		String thumbnailAddr = imageUtil.generateThumbnail(thumbnail, dest);
		product.setImgAddr(thumbnailAddr);
	}
	/**
	 * 处理详情图，并返回新生成图片的相对值路径
	 * 
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	private void addProductImgList(Product product,List<imageHolder> productImgHolderList) {
		//获取图片存储路径,此处直接存放到对应的店铺文件夹下
		String dest = pathUtil.getShopImagePath(product.getShop().getShopId());
		List<productImg> productImgList = new ArrayList<>();
		//遍历图片一次去处理，并添加图片至productImg实体类中
		for (imageHolder productImgHolder:productImgHolderList) {
			String imgAddr = imageUtil.generateNormalImg(productImgHolder, dest);
			productImg productImg = new productImg();
			productImg.setImgAddr(imgAddr);
			productImg.setProductId(product.getProductId());
			productImg.setCreateTime(new Date());
			productImgList.add(productImg);
		}
		//如果确实图片需要添加的,就执行批量添加操作
		if(productImgList.size()>0) {
			try {
				int effectedNum = productImgDao.batchInsertProductImg(productImgList);
				if(effectedNum<=0) {
					throw new productOperationException("创建商品详情图片失败");
				}
			} catch (Exception e) {
				throw new productOperationException("创建商品详情图片失败"+e.toString());
			}
		}
	}
	//通过商品id查询
	@Override
	public Product getProductById(long product) {
		return productDao.queryProductByProductId(product);
	}
	@Override
	/**
	 * 1.处理缩略图，获取缩略图相对路径并赋值给product
	 * 如果已存在缩略图则先删除缩略图再添加新图，之后获取缩略图的相对路径并赋值给product
	 * 2.向tb_product写入商品信息，获取productId
	 * 3.通过productId批量处理商品详情图
	 * 4.将商品详情图列表批量插入tb_product_img中
	 */
	public productExecution modifyProduct(Product product, imageHolder thumbnail,
			List<imageHolder> productImgHolderList) throws productOperationException {
		//空值判断
		if(product != null && product.getShop() != null && product.getShop().getShopId() != null) {
			//设置商品的默认属性
			product.setLastEditTime(new Date());
			//若缩略图不为空且原有缩略图不为空则删除原有缩略图并添加
			if(thumbnail != null) {
				//先获取一遍原有信息, 因为原来的信息中有原始图片地址
				Product tempProduct = productDao.queryProductByProductId(product.getProductId());
				if(tempProduct.getImgAddr()!=null) {
					imageUtil.deleteFileOrPath(tempProduct.getImgAddr());
				}
				addThumbnail(product, thumbnail);
			}
			//如果有新存入的商品详情图, 则将原先的删除, 并添加新的图片
			if(productImgHolderList != null && productImgHolderList.size() > 0 ) {
				deleteProductImgList(product.getProductId());
				addProductImgList(product, productImgHolderList);
			}
			try {
				//更新商品信息
				int effectedNum = productDao.updateProduct(product);
				if(effectedNum <= 0) {
					throw new productOperationException("更新商品信息失败");
				}
				return new productExecution(productStateEnum.SUCCESS,product);
			}catch(Exception e) {
				throw new productOperationException("更新商品信息失败:"+e.toString());
			}
		}else {
			return new productExecution(productStateEnum.EMPTY);
		}
	}
	
	private void deleteProductImgList(long productId) {
		//根据productId获取原来的图片
		List<productImg> productImgList = productImgDao.queryProductImgList(productId);
		//删除原来的图片
		for(productImg productImg:productImgList) {
			imageUtil.deleteFileOrPath(productImg.getImgAddr());
		}
		//删除数据库中原有图片的信息
		productImgDao.deleteProductImgByProductId(productId);
	}
	@Override
	public productExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
		//页码转为数据库中的行码, 并调用dao层取回指定页码的数据
		int rowIndex  = pageCalculator.calculateRowIndex(pageIndex, pageSize);
		List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
		//基于同样查询条件返回该查询条件下的商品总数
		int count = productDao.queryProductCount(productCondition);
		productExecution pe = new productExecution();
		pe.setProductList(productList);
		pe.setCount(count);
		return pe;
	}

}
