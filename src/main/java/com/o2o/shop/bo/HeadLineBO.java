package com.o2o.shop.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 勿忘初心
 * @since 2023-07-15-14:27
 * 用于实现头条类的分页查询、条件拼接
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeadLineBO {

    /**
     * 头条名称
     */
    private String lineName;

    /**
     * 启用状态
     */
    private Integer enableStatus;
}
