package com.study.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.study.o2o.entity.headLine;

public interface headLineDao {
	/**
	 * 根据传入的查询条件(头条名查询头条)
	 * @param headLineCondition
	 * @return
	 */
	List<headLine> queryHeadLine(@Param("headLineCondition") headLine headLineCondition);
	
	/**
	 * 根据头条id返回唯一的头条信息
	 * 
	 * @param lineId
	 * @return
	 */
	headLine queryHeadLineById(long lineId);
	
	/**
	 * 根据传入的Id列表查询头条信息(供超级管理员选定删除头条的时候用,主要是处理图片)
	 * 
	 * @param lineIdList
	 * @return
	 */
	List<headLine> queryHeadLineByIds(List<Long> lineIdList);
	
	/**
	 * 插入头条
	 * 
	 * @param headLine
	 * @return
	 */
	int insertHeadLine(headLine headLine);
	
	/**
	 * 更新头条信息
	 * 
	 * @param headLine
	 * @return
	 */
	int updateHeadLine(headLine headLine);
	
	/**
	 * 删除头条
	 * @param headLineId
	 * @return
	 */
	int deleteHeadLine(long headLineId);

	/**
	 * 批量删除头条信息
	 * 
	 * @param lineIdList
	 * @return
	 */
	int batchDeleteHeadLine(List<Long> lineIdList);
}
