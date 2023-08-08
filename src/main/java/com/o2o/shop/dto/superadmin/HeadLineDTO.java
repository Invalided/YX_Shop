package com.o2o.shop.dto.superadmin;

import com.o2o.shop.validator.area.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 勿忘初心
 * @since 2023-07-06-16:10
 * 头条DTO类
 */
@Data
public class HeadLineDTO {
    /**
     * 头条Id
     */
    @NotNull(message = "头条Id不能为空",groups = UpdateGroup.class)
    private Integer lineId;

    /**
     * 头条名称
     */
    @NotBlank(message = "头条名称不能为空")
    private String lineName;

    /**
     * 头条链接
     */
    private String lineLink;

    /**
     * 头条图片路径
     */
    private String lineImg;

    /**
     * 头条权重
     */
    @NotNull(message = "权重不能为空")
    private Integer priority;

    /**
     * 启用状态
     * @JsonInclude(JsonInclude.Include.NON_NULL) 该注解指明该对象为null时不返回
     */
    @NotNull(message = "状态不能为空")
    private Integer enableStatus;

}
