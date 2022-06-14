package com.study.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.o2o.dao.PersonInfoDao;
import com.study.o2o.dto.PersonInfoExecution;
import com.study.o2o.entity.PersonInfo;
import com.study.o2o.enums.PersonInfoStateEnum;
import com.study.o2o.exceptions.PersonInfoOperationException;
import com.study.o2o.service.PersonInfoService;
import com.study.o2o.util.pageCalculator;

@Service
public class PersonInfoServiceImpl implements PersonInfoService {

	@Autowired
	private PersonInfoDao personInfoDao;

	@Override
	public PersonInfo getPersonInfoById(Long userId) {
		return personInfoDao.queryPersonInfoById(userId);
	}

	@Override
	public PersonInfoExecution getPersonInfoList(PersonInfo personInfoCondition, int pageIndex, int pageSize) {
		// 页转行
		int rowIndex = pageCalculator.calculateRowIndex(pageIndex, pageSize);
		// 获取用户信息列表
		List<PersonInfo> personInfoList = personInfoDao.queryPersonInfoList(personInfoCondition, rowIndex, pageSize);
		int count = personInfoDao.queryPersonInfoCount(personInfoCondition);
		PersonInfoExecution pe = new PersonInfoExecution();
		if (personInfoList != null) {
			pe.setPersonInfoList(personInfoList);
			pe.setCount(count);
		} else {
			pe.setState(PersonInfoStateEnum.INNER_ERROR.getState());
		}
		return pe;
	}

	@Override
	@Transactional
	public PersonInfoExecution modifyPersonInfo(PersonInfo personInfo) {
		//空值判断，主要判断用户Id是否为空
		if(personInfo == null || personInfo.getUserId() == null) {
			return new PersonInfoExecution(PersonInfoStateEnum.EMPTY);
		}else {
			try {
				//更新用户信息
				int effectedNum = personInfoDao.updatePersonInfo(personInfo);
				if(effectedNum <= 0) {
					return new PersonInfoExecution(PersonInfoStateEnum.INNER_ERROR);
				}else {
					personInfo = personInfoDao.queryPersonInfoById(personInfo.getUserId());
					return new PersonInfoExecution(PersonInfoStateEnum.SUCCESS,personInfo);
				}
			} catch (Exception e) {
				throw new PersonInfoOperationException("updatePersonInfo error :"+e.getMessage());
			}
		}
	}

}
