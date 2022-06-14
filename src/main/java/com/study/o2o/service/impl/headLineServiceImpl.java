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
import com.study.o2o.dao.headLineDao;
import com.study.o2o.dto.HeadLineExecution;
import com.study.o2o.dto.imageHolder;
import com.study.o2o.entity.headLine;
import com.study.o2o.enums.HeadLineStateEnum;
import com.study.o2o.exceptions.headLineOperationException;
import com.study.o2o.service.headLineService;
import com.study.o2o.util.imageUtil;
import com.study.o2o.util.pathUtil;

@Service
public class headLineServiceImpl implements headLineService{
	@Autowired
	private headLineDao headLineDao;
	@Autowired
	private JedisUtil.Keys jedisKeys;
	@Autowired
	private JedisUtil.Strings jedisString;
	
	
	@Override
	@Transactional
	public List<headLine> getHeadLineList(headLine headlineCondition){
		//定义redis的key前缀
		String key = HLLISTKEY;
		//定义接收对象
		List<headLine> headLineList = null;
		//定义jackson数据转换操作类
		ObjectMapper mapper = new ObjectMapper();
		//拼接出redis的key
		if(headlineCondition != null && headlineCondition.getEnableStatus()!=null) {
			key = key +"_"+headlineCondition.getEnableStatus();
		}
		//判断key是否存在
		if(!jedisKeys.exists(key)) {
			//若不存在，则从数据库中里面取出相应的数据
			headLineList  = headLineDao.queryHeadLine(headlineCondition);
			//将相关的实体类型转换为String类型
			String jsonString = null;
				try {
					jsonString = mapper.writeValueAsString(headLineList);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					throw new headLineOperationException(e.getMessage());
				}
			jedisString.set(key, jsonString);
		}else {
			//若存在，则直接从Redis里面取出相应数据
			String jsonString = jedisString.get(key);
			//指定要将String转化为对应的集合类
			JavaType javatype = mapper.getTypeFactory().constructParametricType(ArrayList.class, headLine.class);
			//将相关key对应的value里的string转化为对象的实体集合
			try {
				headLineList = mapper.readValue(jsonString, javatype);
			} catch (JsonParseException e) {
				e.printStackTrace();
				throw new headLineOperationException(e.getMessage());
			} catch (JsonMappingException e) {
				e.printStackTrace();
				throw new headLineOperationException(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				throw new headLineOperationException(e.getMessage());
			}
		}
		return headLineList;
	}

	
	
	@Override
	@Transactional
	public HeadLineExecution addHeadLine(headLine headLine, imageHolder thumbnail) {
		//空值判断
		if(headLine != null) {
			//设定默认值
			headLine.setCreateTime(new Date());
			headLine.setLastEditTime(new Date());
			//若传入的头条图片为非空,则存储图片并在实体类里将图片的相对路径设置
			if(thumbnail != null) {
				addThumbnail(headLine, thumbnail);
			}
			try {
				//往数据库中插入头条信息
				int effectedNum = headLineDao.insertHeadLine(headLine);
				if(effectedNum>0) {
					deleteRedis4HeadLine();
					return new HeadLineExecution(HeadLineStateEnum.SUCCESS,headLine);
				}else {
					return new HeadLineExecution(HeadLineStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new headLineOperationException("添加区域信息失败:"+e.toString());
			}
		}else {
			return new HeadLineExecution(HeadLineStateEnum.EMPTY);
		}
	}


	@Override
	@Transactional
	public HeadLineExecution modifyHeadLine(headLine headLine, imageHolder thumbnail) {
		//空值判断, 主要是判断头条Id是否为空
		if(headLine.getLineId()!=null && headLine.getLineId()>0) {
			//设定默认值
			headLine.setLastEditTime(new Date());
			if(thumbnail != null) {
				//若需要修改的图片流不为空,则需要处理现有的图片
				headLine tempHeadLine = headLineDao.queryHeadLineById(headLine.getLineId());
				if(tempHeadLine.getLineImg()!=null) {
					//若原来是存储有图片的，则将原先图片删除
					imageUtil.deleteFileOrPath(tempHeadLine.getLineImg());
				}
				//添加新的图片，并将新的图片相对路径设置到实体类里
				addThumbnail(headLine, thumbnail);
			}
			try {
				// 更新头条信息
				int effectedNum = headLineDao.updateHeadLine(headLine);
				if(effectedNum>0) {
					deleteRedis4HeadLine();
					return new HeadLineExecution(HeadLineStateEnum.SUCCESS, headLine);
				}else {
					return new HeadLineExecution(HeadLineStateEnum.INNER_ERROR);
				}
			} catch (Exception e) {
				throw new headLineOperationException("更新头条信息失败:"+e.toString());
			}
		}else {
			return new HeadLineExecution(HeadLineStateEnum.EMPTY);
		}
	}


	@Override
	@Transactional
	public HeadLineExecution removeHeadLine(long headLineId) {
		//空值判读，主要判断头条Id是否为非空
		if(headLineId>0) {
			try {
				//根据Id查询头条信息
				headLine tempHeadLine = headLineDao.queryHeadLineById(headLineId);
				if(tempHeadLine.getLineImg() != null) {
					//若头条原先存有图片， 则将该图片文件删除
					imageUtil.deleteFileOrPath(tempHeadLine.getLineImg());
				}
				//删除数据库里对应的头条信息
				int effectedNum = headLineDao.deleteHeadLine(headLineId);
				if(effectedNum>0) {
					deleteRedis4HeadLine();
					return new HeadLineExecution(HeadLineStateEnum.SUCCESS);
				}else {
					return new HeadLineExecution(HeadLineStateEnum.INNER_ERROR);
				}
			}catch (Exception e) {
				throw new headLineOperationException("删除头条信息失败:"+e.toString());
			}
		}else {
			return new HeadLineExecution(HeadLineStateEnum.EMPTY);
		}
	}


	@Override
	@Transactional
	public HeadLineExecution removeHeadLineList(List<Long> headLineIdList) {
		// 空值判断
		if(headLineIdList != null && headLineIdList.size()>0) {
			try {
				//根据传入的id列表获取头条列表
				List<headLine> headLineList = headLineDao.queryHeadLineByIds(headLineIdList);
				for(headLine headLine:headLineList) {
					//遍历头条列表，若头条的图片非空，则将图片删除
					if(headLine.getLineImg()!=null) {
						imageUtil.deleteFileOrPath(headLine.getLineImg());
					}
				}
				//批量删除数据库中的头条信息
				int effectedNum = headLineDao.batchDeleteHeadLine(headLineIdList);
				if(effectedNum>0){
					deleteRedis4HeadLine();
					return new HeadLineExecution(HeadLineStateEnum.SUCCESS);
				}else {
					return new HeadLineExecution(HeadLineStateEnum.INNER_ERROR);
				}
			}catch (Exception e) {
				throw new headLineOperationException("删除头条信息失败:"+e.toString());
			}
		}else {
			return new HeadLineExecution(HeadLineStateEnum.EMPTY);
		}
	}
	
	/**
	 * 存储图片
	 * 
	 * @param headLine
	 * @param thumbnail
	 */
	private void addThumbnail(headLine headLine,imageHolder thumbnail) {
		String dest = pathUtil.getHeadLineImagePath();
		//获取图片存放的地址
		String thumbnailAddr = imageUtil.generateNormalImg(thumbnail, dest);
		headLine.setLineImg(thumbnailAddr);
	}

	/**
	 * 移除与实体类相关的redis key-value
	 */
	private void deleteRedis4HeadLine() {
		String prefix = HLLISTKEY;
		//获取与头条相关的redis key
		Set<String> keySet = jedisKeys.keys(prefix+"*");
		for(String key : keySet) {
			//逐条删除
			jedisKeys.del(key);
		}
	}
}
