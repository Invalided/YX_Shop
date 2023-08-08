package com.o2o.shop.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author 勿忘初心
 * @since 2023-06-20-18:35
 * 数据传输对象，用于接收前端入参并绑定
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDTO {

    @Min(value = 1,message = "id值不合法")
    @Positive
    private Integer id;
    @NotBlank(message = "姓名不能为空")
    @Length(min = 4,max=10,message = "姓名必须在4-10个字符之间")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Length(min = 6,max=16, message = "密码长度须在6-16位之间")
    private String password;
    @Range(min = 1,max=120,message = "年龄不在合法范围内")
    @NotNull(message = "age不能为空")
    private Integer age;
}
