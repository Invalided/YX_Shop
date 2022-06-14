package com.study.o2o.service;
import java.io.IOException;
import java.util.List;

import com.study.o2o.dto.HeadLineExecution;
import com.study.o2o.dto.imageHolder;
import com.study.o2o.entity.headLine;


public interface headLineService {
	//定义redis中的key前缀
	public static final String HLLISTKEY = "headlinelist";
	
	/**
	 * 根据传入的条件返回指定的头条列表
	 * @param headlineCondition
	 * @return
	 * @throws IOException
	 */
	List<headLine> getHeadLineList(headLine headlineCondition) throws IOException;
	
	/**
	 * 添加头条信息,并循环存储头条图片
	 * @param headLine
	 * @param thumbnail
	 * @return
	 */
	HeadLineExecution addHeadLine(headLine headLine,imageHolder thumbnail);
	
	/**
	 * 修改头条信息
	 * @param headLine
	 * @param thumbnail
	 * @return
	 */
	HeadLineExecution modifyHeadLine(headLine headLine,imageHolder thumbnail);
	
	/**
	 * 删除单条头条
	 * @param headLineId
	 * @return
	 */
	HeadLineExecution removeHeadLine(long headLineId);
	
	/**
	 * 批量删除头条
	 * 
	 * @param headLineIdList
	 * @return
	 */
	HeadLineExecution removeHeadLineList(List<Long> headLineIdList);
}
