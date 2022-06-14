package com.study.o2o.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.study.o2o.entity.localAuth;

public interface localAuthDao {
	/**
	 * 通过帐号和密码查询对应信息，登录用
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	localAuth queryLocalByUserNameAndPwd(@Param("username") String username, @Param("password") String password);

	/**
	 * 通过用户Id查询对应localauth
	 * 
	 * @param userId
	 * @return
	 */
	localAuth queryLocalByUserId(@Param("userId") long userId);

	/**
	 * 添加平台帐号
	 * 
	 * @param localAuth
	 * @return
	 */
	int insertLocalAuth(localAuth localAuth);

	/**
	 * 通过userId,username,password更改密码
	 * 
	 * @param localAuth
	 * @return
	 */
	int updateLocalAuth(@Param("userId") Long userId, @Param("username") String username,
			@Param("password") String password, @Param("newPassword") String newPassword,
			@Param("lastEditTime") Date lastEditTime);
}
