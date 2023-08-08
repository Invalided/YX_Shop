package com.o2o.shop.vo.area;

import lombok.Data;
import java.util.Date;

/**
 * @author 勿忘初心
 * @since 2023-07-03-23:05
 * 用于区域信息的返回对象
 */
@Data
public class AreaVO {

    /**
     * 区域Id
     */
    private Integer areaId;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 区域权重
     */
    private Integer priority;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
