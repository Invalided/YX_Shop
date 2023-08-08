package com.o2o.shop.validator.extention;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @author 勿忘初心
 * @since 2023-07-15-23:40
 * 自定义校验注解需要实现相关方法，并重写逻辑
 */
public class ListNotNullValidatorImpl implements ConstraintValidator<ListNotNull, List> {

    @Override
    public boolean isValid(List value, ConstraintValidatorContext context) {
        // 若包含空对象则验证失败
        for(Object obj : value){
            if(obj == null){
                return false;
            }
        }
        return true;
    }
}
