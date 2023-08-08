package com.o2o.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.o2o.shop.constant.GlobalConstant;
import com.o2o.shop.dto.authentication.LocalAuthDTO;
import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import com.o2o.shop.mapper.LocalAuthMapper;
import com.o2o.shop.mapper.PersonInfoMapper;
import com.o2o.shop.model.LocalAuthDO;
import com.o2o.shop.model.PersonInfoDO;
import com.o2o.shop.service.LocalAuthService;
import com.o2o.shop.util.Md5Generator;
import com.o2o.shop.vo.LocalAuthVO;
import com.o2o.shop.vo.ResultDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

/**
 * @author 勿忘初心
 * @since 2023-07-11-19:24
 */
@Service
@Slf4j
public class LocalAuthServiceImpl implements LocalAuthService {

    @Autowired
    private LocalAuthMapper localAuthMapper;

    @Autowired
    private PersonInfoMapper personInfoMapper;

    /**
     * HttpSession = HttpServletRequest.getAttribute()
     */
    @Autowired
    private HttpSession sessionOperator;
    
    @Override
    @Transactional
    public ResultDataVO userRegister(LocalAuthDTO localAuthDTO) {
        // 验证用户类型,目前只允许用户和商户注册
        Integer userType = localAuthDTO.getUserType();
        if(userType == null || userType < GlobalConstant.RoleType.USER.getValue()
                || userType > GlobalConstant.RoleType.MANAGER.getValue()){
            log.error("注册类型错误,类型为空或管理员类型暂不允许注册");
            throw new BusinessException(ExceptionCodeEnum.EC10001);
        }

        // 判断用户名是否重复
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username",localAuthDTO.getUsername());
        LocalAuthDO existInfo = localAuthMapper.selectOne(queryWrapper);
        if(existInfo != null){
            log.error("{} 注册失败,用户名已被占用", existInfo.getUsername());
            throw new BusinessException(ExceptionCodeEnum.EC20000);
        }
        // 先写入用户默认信息到PersonInfo表中,再写入auth表中
        PersonInfoDO personInfoDO = PersonInfoDO.builder()
                .name(localAuthDTO.getUsername())
                .userType(userType)
                .build();
        int effectNum = personInfoMapper.insert(personInfoDO);
        if(effectNum < 1){
            log.error("用户注册初始化信息失败,未能创建用户");
            throw new BusinessException(ExceptionCodeEnum.EC20003);
        }
        // 账号信息处理
        LocalAuthDO localAuthDO = new LocalAuthDO();
        BeanUtils.copyProperties(localAuthDTO,localAuthDO);
        String md5Password = Md5Generator.getMd5(localAuthDTO.getPassword());
        // 赋值新的密码
        localAuthDO.setPassword(md5Password);
        localAuthDO.setUserId(personInfoDO.getUserId());
        // 写入数据到auth表中
        effectNum = localAuthMapper.insert(localAuthDO);
        if(effectNum < 1){
            log.error("用户注册信息失败" );
            throw new BusinessException(ExceptionCodeEnum.EC10010);
        }
        return ResultDataVO.success(null);
    }

    @Override
    public ResultDataVO userModifyPassword(LocalAuthDTO localAuthDTO) {
        // 当前requestSessionId
        log.debug("User modifyPassword requestSession  "+sessionOperator.getId());
        // 获取session中存放的信息,此处需要用VO获取
        LocalAuthVO localSessionInfo = (LocalAuthVO) sessionOperator.getAttribute(GlobalConstant.USER_SESSION_INFO);
        Integer currentUserId = localAuthDTO.getUserId();
        // 查询存储的信息是否存在
        if(localSessionInfo != null && localSessionInfo.getPersonInfo() != null){
            // 取出用户信息
            PersonInfoDO personInfo = localSessionInfo.getPersonInfo();
            // 判断当前request和session中存放的id是否一致
            if(!personInfo.getUserId().equals(currentUserId)){
                log.error("uid {}, name {} 请求异常,session信息与实际不一致", personInfo.getUserId(),
                        personInfo.getName());
                throw new BusinessException(ExceptionCodeEnum.EC10004);
            }
        }else{
            log.error("未登录修改密码");
            throw new BusinessException(ExceptionCodeEnum.EC20005);
        }
        // 修改前先查,根据session中的authId获取
        Integer sessionAuthId = localSessionInfo.getLocalAuthId();
        LocalAuthDO localAuthInfo = localAuthMapper.selectById(sessionAuthId);

        if(localAuthInfo == null){
            log.error("authId {} 用户不存在", sessionAuthId);
            throw new BusinessException(ExceptionCodeEnum.EC20001);
        }
        // 验证原密码是否正确
        String currentPassword = Md5Generator.getMd5(localAuthDTO.getPassword());
        if(!currentPassword.equals(localAuthInfo.getPassword())){
            log.error("{} 原密码不正确",localAuthInfo.getUsername());
            throw new BusinessException(ExceptionCodeEnum.EC20006);
        }
        // 新密码是否与原密码一致
        String modifyPassword = Md5Generator.getMd5(localAuthDTO.getNewPassword());
        if(modifyPassword.equals(localAuthInfo.getPassword())) {
            log.error("{} 新密码与原密码相同", localAuthInfo.getUsername());
            throw new BusinessException(ExceptionCodeEnum.EC20007);
        }
        // 设置新密码
        localAuthInfo.setPassword(modifyPassword);
        int effectNum = localAuthMapper.updateById(localAuthInfo);
        if(effectNum < 1){
            log.error("authId {} 修改密码失败", localAuthInfo.getLocalAuthId());
            throw new BusinessException(ExceptionCodeEnum.EC10010);
        }
        // 移除session,登录状态失效
        sessionOperator.removeAttribute(GlobalConstant.USER_SESSION_INFO);
        return ResultDataVO.success(null);
    }

    @Override
    public LocalAuthVO userLoginCheck(LocalAuthDTO localAuthDTO) {
        // 当前requestSessionId
        log.info("User login requestSession  "+sessionOperator.getId());
        // 密码哈希
        String md5Password = Md5Generator.getMd5(localAuthDTO.getPassword());
        // 若Session未过期则无需重复登录
        LocalAuthVO sessionUserInfo = (LocalAuthVO)sessionOperator.getAttribute(GlobalConstant.USER_SESSION_INFO);
        // Session过期则查询数据库
        if(!(sessionUserInfo != null &&
                userSessionEquals(localAuthDTO, sessionUserInfo))){
            // 根据用户名密码查询
            sessionUserInfo = localAuthMapper.queryLocalAuthInfo(
                    localAuthDTO.getUsername(), md5Password);
            if(sessionUserInfo == null){
                log.error("{} 登录失败,用户名或密码错误", localAuthDTO.getUsername());
                throw new BusinessException(ExceptionCodeEnum.EC20002);
            }
            // 判断是否有对应的用户信息
            PersonInfoDO personInfoDO = sessionUserInfo.getPersonInfo();
            if(personInfoDO != null){
                if(personInfoDO.getEnableStatus() == 0){
                    log.error("{} 账号异常已被禁用", localAuthDTO.getUsername());
                    throw new BusinessException(ExceptionCodeEnum.EC20004);
                }
                // 用户类型是否匹配
                if(!personInfoDO.getUserType().equals(localAuthDTO.getUserType())){
                    log.error("uid {}, userName {} , 用户未授权访问",sessionUserInfo.getLocalAuthId(),
                            sessionUserInfo.getUserName());
                    throw new BusinessException(ExceptionCodeEnum.EC10004);
                }

            }else{
                throw new BusinessException(ExceptionCodeEnum.EC20001);
            }
            // 设置session,存放本次request状态下已登录的用户信息
            sessionOperator.setAttribute(GlobalConstant.USER_SESSION_INFO, sessionUserInfo);
        }

        return sessionUserInfo;
    }

    @Override
    public ResultDataVO userLogout() {
        // 当前requestSessionId
        log.info("User logout requestSession  "+sessionOperator.getId());
        sessionOperator.removeAttribute(GlobalConstant.USER_SESSION_INFO);
        return ResultDataVO.success(null);
    }

    @Override
    public LocalAuthVO getUserAuthSession() {
        return (LocalAuthVO) sessionOperator.getAttribute(GlobalConstant.USER_SESSION_INFO);
    }

    /**
     * 判断session中是否存在当前dto对象，防止重复登录
     * @param localAuthDTO 用户dto
     * @param userSessionInfo session存储信息
     * @return
     */
    private boolean userSessionEquals(LocalAuthDTO localAuthDTO,LocalAuthVO userSessionInfo){
        return localAuthDTO.getUserType().equals(userSessionInfo.getPersonInfo().getUserType()) &&
                localAuthDTO.getUsername().equals(userSessionInfo.getUserName()) &&
                Md5Generator.getMd5(localAuthDTO.getPassword()).equals(userSessionInfo.getPassWord());
    }
}
