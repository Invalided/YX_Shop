package com.o2o.shop.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

/**
 * @author 勿忘初心
 * @since 2023-06-27-17:42
 * 用户信息更改传输对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDTO {

    @Min(value = 1,message = "id值不合法")
    private Integer id;
    @NotNull(message = "名称不能为空")
    @Length(min = 4,max=10,message = "名称必须在4-10个字符之间")
    private String username;
    @Length(min = 6,max=16, message = "密码长度须在6-16位之间")
    private String password;
    @Range(min = 1,max=120,message = "年龄不在合法范围内")
    private Integer age;
}
