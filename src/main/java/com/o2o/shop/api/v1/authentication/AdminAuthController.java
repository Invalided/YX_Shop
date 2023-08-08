package com.o2o.shop.api.v1.authentication;

import com.o2o.shop.constant.GlobalConstant;
import com.o2o.shop.dto.authentication.LocalAuthDTO;
import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import com.o2o.shop.service.LocalAuthService;
import com.o2o.shop.validator.authentication.AuthChangeSequence;
import com.o2o.shop.validator.authentication.AuthCheckSequence;
import com.o2o.shop.vo.LocalAuthVO;
import com.o2o.shop.vo.ResultDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * AdminAuthController 管理员登录
 * @author 勿忘初心
 * @since 2023-07-11-15:24
 * 超级管理员登录处理
 */
@RestController
@Slf4j
@RequestMapping("/admin/auth")
@Validated
public class AdminAuthController {

    @Autowired
    private LocalAuthService localAuthService;

    @Autowired
    private HttpServletRequest httpSession;

    /**
     * 管理员登录接口
     * @param localAuthDTO DTO对象 新增类型校验
     * @return
     */
    @PostMapping("/login")
    LocalAuthVO loginCheck(@RequestBody @Validated(AuthCheckSequence.class) LocalAuthDTO localAuthDTO){
        // 判断用户类型
        if(!localAuthDTO.getUserType().equals(GlobalConstant.RoleType.ADMIN.getValue())){
            log.error("userName {} 未授权登录管理账户",localAuthDTO.getUsername());
            throw new BusinessException(ExceptionCodeEnum.EC10004);
        }
        return localAuthService.userLoginCheck(localAuthDTO);
    }

    /**
     * 管理员修改密码
     * @param localAuthDTO
     * @return
     */
    @PutMapping("/password")
    ResultDataVO userChangePassword(@RequestBody
                                    @Validated(AuthChangeSequence.class)
                                    LocalAuthDTO localAuthDTO){
        return localAuthService.userModifyPassword(localAuthDTO);
    }

    /**
     * 管理员退出登录
     * @return
     */
    @GetMapping("logout")
    ResultDataVO userLogOut(){
        return localAuthService.userLogout();
    }
}
