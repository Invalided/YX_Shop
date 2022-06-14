package com.study.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.o2o.cache.JedisUtil;
import com.study.o2o.dao.areaDao;
import com.study.o2o.dto.AreaExecution;
import com.study.o2o.entity.Area;
import com.study.o2o.enums.AreaStateEnum;
import com.study.o2o.exceptions.areaOperationExcetion;
import com.study.o2o.service.areaService;

@Service
public class areaServiceImpl implements areaService{
	@Autowired
	private areaDao areaDao;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private JedisUtil.Strings jedisStrings;
	
	@Override
	//添加事务处理
	@Transactional
	public List<Area> getAreaList() {
		String key = AREALISTKEY;
		List<Area> areaList = null;
		ObjectMapper mapper = new ObjectMapper();
		//Redis中存放的数据以键值对存储 KEYS STRINGS
		//判断key值是否存在
		if(!jedisKeys.exists(key)) {
			//获取到返回的areaList对象
			areaList = areaDao.queryArea();
			String jsonStirng;
			try {
				//将获取的areaList对象转化为String
				jsonStirng = mapper.writeValueAsString(areaList);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				throw new areaOperationExcetion(e.getMessage());
			}
			//存放更新后的数据
			jedisStrings.set(key, jsonStirng);
			
		}else {
			//通过key值获取Redis中已存放的数据
			String jsonString = jedisStrings.get(key);
			//通过mapper的Factory工厂方法获取到对应的类型数据	
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
			try {
				areaList = mapper.readValue(jsonString, javaType);
			} catch (JsonParseException e) {
				e.printStackTrace();
				throw new areaOperationExcetion(e.getMessage());
			} catch (JsonMappingException e) {
				e.printStackTrace();
				throw new areaOperationExcetion(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				throw new areaOperationExcetion(e.getMessage());
			}
		}
		return areaList;
	}

	@Override
	public AreaExecution addArea(Area area) {
		//空值判断,主要是判断areaName不为空
		if(area.getAreaName() != null && !"".equals(area.getAreaName())) {
			//设置默认值
			area.setCreateTime(new Date());
			area.setLastEditTime(new Date());
			try {
				//更新区域信息
				int effectedNum = areaDao.insertArea(area);
				if(effectedNum>0) {
					deleteRedis4Area();
					return new AreaExecution(AreaStateEnum.SUCCESS,area);
				}else {
					return new AreaExecution(AreaStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new areaOperationExcetion("添加区信息失败:"+e.toString());
			}
		}else {
			return new AreaExecution(AreaStateEnum.EMPTY);
		}
	}

	@Override
	public AreaExecution modifyArea(Area area) {
		//空值判断
		if(area.getAreaId() != null && area.getAreaId() > 0) {
			//设置默认值
			area.setLastEditTime(new Date());
			try {
				//更新区域信息
				int effectedNum = areaDao.updateArea(area);
				if(effectedNum > 0) {
					deleteRedis4Area();
					return new AreaExecution(AreaStateEnum.SUCCESS,area);
				}else {
					return new AreaExecution(AreaStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new areaOperationExcetion("更新区域信息失败:"+e.toString());
			}
		}
		else {
			return new AreaExecution(AreaStateEnum.EMPTY);
		}
	}

	@Override
	public AreaExecution removeArea(long areaId) {
		//空值判断
		if(areaId>0) {
			try {
				//更新区域信息
				int effectedNum = areaDao.deleteArea(areaId);
				if(effectedNum>0) {
					deleteRedis4Area();
					return new AreaExecution(AreaStateEnum.SUCCESS);
				}else {
					return new AreaExecution(AreaStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new areaOperationExcetion("删除区域信息失败:"+e.getMessage());
			}
		}else {
			return new AreaExecution(AreaStateEnum.EMPTY);
		}
	}
	
	private void deleteRedis4Area() {
		String key = AREALISTKEY;
		//若redis存在对应的key,则将key清除
		if(jedisKeys.exists(key)) {
			jedisKeys.del(key);
		}
	}
}
