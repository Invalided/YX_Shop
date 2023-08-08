package com.o2o.shop.dto.superadmin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author 勿忘初心
 * @since 2023-07-07-14:22
 * 用户信息DTO类，主要管理用户账号的启用/禁用状态
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonInfoDTO {
    /**
     * 用户Id
     */
    @NotNull(message = "用户Id不能为空")
    private Integer userId;


    /**
     * 0:账号封禁,1:账号启用
     */
    @NotNull(message = "状态不能为空")
    private Integer enableStatus;

    /**
     * 1.顾客，2.店家，3.超级管理员
     */
    private Integer userType;
}
