package com.o2o.shop.dto.superadmin;


import com.o2o.shop.validator.area.AddGroup;
import com.o2o.shop.validator.area.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * @author 勿忘初心
 * @since 2023-07-04-18:46
 * 合并DTO参数，新增分组校验
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AreaDTO {
    /**
     * 区域Id
     * update时必需参数
     */
    @NotNull(message = "区域id不能为空",groups = {UpdateGroup.class, Default.class})
    private Integer areaId;

    /**
     * 区域名称
     * create update是均需参数
     */
    @NotBlank(message = "区域名称不能为空",groups = {AddGroup.class, UpdateGroup.class,Default.class})
    private String areaName;

    /**
     * 默认id
     */
    @NotNull(message = "默认id不能为空")
    private Integer defaultId;
}
