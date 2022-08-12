package com.study.o2o.service;

import java.util.List;

import com.study.o2o.dto.AreaExecution;
import com.study.o2o.entity.Area;

public interface areaService {
	/**
	 * 定义Redis中存放的key值
	 */
	public static final String AREALISTKEY = "arealist";
	/**
	 * 获取区域列表信息，优先从缓存获取
	 * @return
	 */
	List<Area> getAreaList();
	
	/**
	 * 增加区域信息
	 * 
	 * @param area
	 * @return
	 */
	AreaExecution addArea(Area area);
	
	/**
	 * 修改区域信息
	 * 
	 * @param area
	 * @return
	 */
	AreaExecution modifyArea(Area area);
	
	/**
	 * 删除区域信息
	 * 
	 * @param areaId
	 * @return
	 */
	AreaExecution removeArea(long areaId);
}
