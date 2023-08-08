package com.o2o.shop.dto.shopmanager;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Positive;
import java.time.Instant;

/**
 * @author 勿忘初心
 * @since 2023-07-23-23:37
 * 积分DTO对象,由于包含了Instant对象，因此必须使用JSON格式传输,否则将出现转换异常
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManagePointDTO {

    /**
     * 用户id
     */
    @Positive(message = "用户id不合法")
    private Integer userId;
    /**
     * 商铺id
     */
    @Range(min = 1,max = 10000, message = "商铺id不合法")
    private Integer shopId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 商铺名称
     */
    private String shopName;
    /**
     * 积分创建时间
     */
    private Instant createTime;
}
