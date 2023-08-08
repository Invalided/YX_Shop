package com.o2o.shop.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 勿忘初心
 * @since 2023-06-21-17:53
 * 用户类返回对象
 */
@Data
public class UserVO {
    private Long id;
    private String username;
    private Integer age;
    private Date createTime;
}
