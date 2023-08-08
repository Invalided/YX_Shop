package com.o2o.shop.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.o2o.shop.model.PersonInfoDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 勿忘初心
 * @since 2023-07-12-16:00
 * 用户信息验证VO对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalAuthVO {
    /**
     * 账号Id,该字段将不会返回给前端
     */
    @JsonIgnore
    private Integer localAuthId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码,该字段将不会返回
     */
    @JsonIgnore
    private String passWord;
    /**
     * 用户信息对象
     */
    private PersonInfoDO personInfo;
}
