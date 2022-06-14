package com.study.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.o2o.dao.productCategoryDao;
import com.study.o2o.dao.productDao;
import com.study.o2o.dto.productCategoryExecution;
import com.study.o2o.entity.productCategory;
import com.study.o2o.enums.productCategoryStateEnum;
import com.study.o2o.exceptions.productCategoryOperationException;
import com.study.o2o.service.productCategoryService;

@Service
public class productCategoryServiceImpl implements productCategoryService{
	@Autowired
	private productCategoryDao productCategoryDao;
	@Autowired
	private productDao productDao;
	
	@Override
	public List<productCategory> getProductCategoryList(long shopId) {
		return productCategoryDao.queryProductCategoryList(shopId);
	}

	@Override
	@Transactional
	public productCategoryExecution batchAddProductCategory(List<productCategory> productCategoryList)
			throws productCategoryOperationException {
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
				if (effectedNum <= 0) {
					throw new productCategoryOperationException("店铺类别创建失败");
				} else {
					return new productCategoryExecution(productCategoryStateEnum.SUCCESS);
				}

			} catch (Exception e) {
				throw new productCategoryOperationException("batchAddProductCategory error: " + e.getMessage());
			}
		} else {
			return new productCategoryExecution(productCategoryStateEnum.EMPTY_LIST);
		}
	}

	@Override
	@Transactional
	public productCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
			throws productCategoryOperationException {
		//解除tb_product里的商品与该productCategoryId的关联
		try {
			int effectedNum = productDao.updateProductCategoryToNull(productCategoryId);
			if(effectedNum<0) {
				throw new productCategoryOperationException("商品类别更新失败");
			}
		} catch (Exception e) {
			throw new productCategoryOperationException("deleteProductCategory error:"+e.getMessage());
		}
		//删除该productCatgory
		try {
			int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
			if(effectedNum <= 0) {
				throw new productCategoryOperationException("商品类别删除失败");
			}else {
				return new productCategoryExecution(productCategoryStateEnum.SUCCESS);
			}
		} catch (Exception e) {
			throw new productCategoryOperationException("deleteProductCategory error: "+e.getMessage());
		}
	}
		
}
