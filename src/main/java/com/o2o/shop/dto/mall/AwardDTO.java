package com.o2o.shop.dto.mall;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Positive;

/**
 * @author 勿忘初心
 * @since 2023-07-15-20:22
 * 奖品DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AwardDTO {
    /**
     * 空属性对象
     */
    private static final AwardDTO EMPTY = new AwardDTO();
    /**
     * 奖品名
     */
    private String awardName;

    /**
     * 奖品状态
     */
    @Positive(message = "状态参数错误")
    private Integer enableStatus;

    /**
     * 所属商铺id
     */
    @Range(min = 1,max=1000,message = "商铺Id错误")
    private Integer shopId;

    /**
     * 判断当前对象是否未赋值
     * @return
     */
    public boolean isEmpty(){
        return this.equals(EMPTY);
    }
}
