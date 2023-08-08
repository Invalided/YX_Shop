package com.o2o.shop.api.v1.authentication;

import com.o2o.shop.dto.authentication.LocalAuthDTO;
import com.o2o.shop.service.LocalAuthService;
import com.o2o.shop.validator.authentication.AuthChangeSequence;
import com.o2o.shop.validator.authentication.AuthCheckSequence;
import com.o2o.shop.vo.LocalAuthVO;
import com.o2o.shop.vo.ResultDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * LocalAuthController 用户/商家登录
 * @author 勿忘初心
 * @since 2023-07-11-15:24
 * 用户登录处理器
 */
@RestController
@Slf4j
@RequestMapping("/local/auth")
@Validated
public class LocalAuthController {

    @Autowired
    private LocalAuthService localAuthService;


    /**
     * 用户注册信息接口
     * @param localAuthDTO 账户DTO对象
     * @return
     */
    @PostMapping("/register")
    ResultDataVO userRegister(@RequestBody @Validated(AuthCheckSequence.class) LocalAuthDTO localAuthDTO){
        return localAuthService.userRegister(localAuthDTO);
    }

    /**
     * 用户登录接口
     * @param localAuthDTO 本地账号DTO对象
     * @return
     */
    @PostMapping("/login")
    LocalAuthVO loginCheck(@RequestBody @Validated(AuthCheckSequence.class) LocalAuthDTO localAuthDTO){
        return localAuthService.userLoginCheck(localAuthDTO);

    }

    /**
     * 用户修改密码
     * @param localAuthDTO 本地账号对象
     * @return
     */
    @PutMapping("/password")
    ResultDataVO userChangePassword(@RequestBody
                                    @Validated(AuthChangeSequence.class)
                                    LocalAuthDTO localAuthDTO){
        return localAuthService.userModifyPassword(localAuthDTO);
    }

    /**
     * 用户退出登录
     * @return
     */
    @GetMapping("logout")
    ResultDataVO userLogOut(){
        return localAuthService.userLogout();
    }
}
