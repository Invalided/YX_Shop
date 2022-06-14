package com.study.o2o.service;

public interface CacheService {

	/**
	 * 依据key前缀除删除匹配该模式下的所有key-value 如传入:shopcategory,则shopcategory_allfirstlevel
	 * 等的以shopcategory开头的key_value都会被清空
	 * @param keyPrefix
	 */
	void removeFromCache(String keyPrefix);
}
