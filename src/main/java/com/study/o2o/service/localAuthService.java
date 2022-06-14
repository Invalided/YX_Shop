package com.study.o2o.service;

import com.study.o2o.dto.localAuthExecution;
import com.study.o2o.entity.localAuth;
import com.study.o2o.exceptions.localAuthOperationExcetpion;

public interface localAuthService {
	/**
	 * 通过帐号和密码获取平台帐号信息
	 * 
	 * @param userName
	 * @return
	 */
	localAuth getLocalAuthByUsernameAndPwd(String userName, String password);

	/**
	 * 通过userId获取平台帐号信息
	 * 
	 * @param userId
	 * @return
	 */
	localAuth getLocalAuthByUserId(long userId);

	/**
	 * 绑定微信，生成平台专属的帐号
	 * 
	 * @param localAuth
	 * @return
	 * @throws RuntimeException
	 */
	localAuthExecution bindLocalAuth(localAuth localAuth) throws localAuthOperationExcetpion;

	/**
	 * 修改平台帐号的登录密码
	 * 
	 * @param localAuthId
	 * @param userName
	 * @param password
	 * @param newPassword
	 * @param lastEditTime
	 * @return
	 */
	localAuthExecution modifyLocalAuth(Long userId, String username, String password, String newPassword)
			throws localAuthOperationExcetpion;
	/**
	 * 平台用户注册
	 * 
	 * @param localAuth
	 * @return
	 * @throws localAuthOperationExcetpion
	 */
	localAuthExecution registerLocalAuth(localAuth localAuth) throws localAuthOperationExcetpion;
}
