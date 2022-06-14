package com.study.o2o.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.o2o.dao.PersonInfoDao;
import com.study.o2o.dao.localAuthDao;
import com.study.o2o.dto.localAuthExecution;
import com.study.o2o.entity.localAuth;
import com.study.o2o.enums.localAuthStateEnum;
import com.study.o2o.exceptions.localAuthOperationExcetpion;
import com.study.o2o.service.localAuthService;
import com.study.o2o.util.MD5;

@Service
public class localAuthServiceImpl implements localAuthService {
	private static Logger log = LoggerFactory.getLogger(localAuthServiceImpl.class);
	@Autowired
	private localAuthDao localAuthDao;
	@Autowired
	private PersonInfoDao personInfoDao;

	@Override
	public localAuth getLocalAuthByUsernameAndPwd(String userName, String password) {
		return localAuthDao.queryLocalByUserNameAndPwd(userName, MD5.getMd5(password));
	}

	@Override
	public localAuth getLocalAuthByUserId(long userId) {
		return localAuthDao.queryLocalByUserId(userId);
	}

	/**
	 * 绑定微信账号
	 */
	@Override
	@Transactional
	public localAuthExecution bindLocalAuth(localAuth localAuth) throws localAuthOperationExcetpion {
		// 空值判断,传入的localAuth账号密码，用户信息特别是 userId不能为空，否则直接返回错误
		if (localAuth == null || localAuth.getPassword() == null || localAuth.getUsername() == null
				|| localAuth.getPersonInfo() == null || localAuth.getPersonInfo().getUserId() == null) {
			return new localAuthExecution(localAuthStateEnum.ONLY_ONE_ACCOUNT);
		}
		try {
			localAuth.setCreateTime(new Date());
			localAuth.setLastEditTime(new Date());
			// 对密码进行MD5加密
			localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
			int effected = localAuthDao.insertLocalAuth(localAuth);
			// 判断是否创建成功
			if (effected <= 0) {
				throw new localAuthOperationExcetpion("帐号绑定失败");
			} else {
				return new localAuthExecution(localAuthStateEnum.SUCCESS, localAuth);
			}
		} catch (Exception e) {
			throw new localAuthOperationExcetpion("insertLocalAuth error:" + e.getMessage());
		}
	}

	@Override
	@Transactional
	public localAuthExecution modifyLocalAuth(Long userId, String userName, String password, String newPassword)
			throws localAuthOperationExcetpion {
		// 非空判断,判断传入的用户ID,帐号,新旧密码是否为空，新旧密码是否相同，若不满足则返回错误信息
		if (userId != null && userName != null && password != null && newPassword != null
				&& !password.equals(newPassword)) {
			try {
				// 更新密码
				int effectedNum = localAuthDao.updateLocalAuth(userId, userName, MD5.getMd5(password),
						MD5.getMd5(newPassword), new Date());
				// 判断更新是否成功
				if (effectedNum <= 0) {
					throw new localAuthOperationExcetpion("更新密码失败");
				}
				return new localAuthExecution(localAuthStateEnum.SUCCESS);
			} catch (Exception e) {
				throw new localAuthOperationExcetpion("更新密码失败:" + e.toString());
			}
		} else {
			return new localAuthExecution(localAuthStateEnum.NULL_AUTH_INFO);
		}
	}

	/**
	 * 05.13添加 用户注册
	 * 先写入person_info
	 * 使用了generate_key 会在写入数据库后将对象的值填入
	 * 再根据user_id创建帐号
	 * 
	 */
	@Override
	@Transactional
	public localAuthExecution registerLocalAuth(localAuth localAuth) throws localAuthOperationExcetpion {
		int effected = 0;
		try {	
			if(localAuth.getPersonInfo()!=null) {
				try {
					//先创建person_info相关信息
					effected = personInfoDao.insertPersonInfo(localAuth.getPersonInfo());
					// 判断是否创建成功
					if (effected <= 0) {
						throw new localAuthOperationExcetpion("帐号信息初始化失败");
					}
				} catch (Exception e) {
					log.error("insertWechatAuth error:" + e.toString());
					throw new localAuthOperationExcetpion("insert localAuth Error"+e.getMessage());
				}
			}
			else {
					return new localAuthExecution(localAuthStateEnum.SUCCESS, localAuth);
			}
			// 对密码进行MD5加密
			localAuth.setPassword(MD5.getMd5(localAuth.getPassword()));
			//获取用户的信息写入数据
			localAuth.setLocalAuthId(localAuth.getPersonInfo().getUserId());
			localAuth.setCreateTime(new Date());
			localAuth.setLastEditTime(new Date());		
			effected = localAuthDao.insertLocalAuth(localAuth);
			if(effected<=0) {
				throw new localAuthOperationExcetpion("帐号注册失败");
			}else {
				return new localAuthExecution(localAuthStateEnum.SUCCESS,localAuth);
			}
		} catch (Exception e) {
			throw new localAuthOperationExcetpion("registerLocalAuth error:" + e.getMessage());
		}
	}

}
