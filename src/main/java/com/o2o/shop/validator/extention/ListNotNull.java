package com.o2o.shop.validator.extention;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author 勿忘初心
 * @since 2023-07-15-23:19
 * 自定义注解,用于判断List集合中的自定义对象是否含有空值
 */
@Documented
@Retention(RUNTIME)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Constraint(validatedBy = ListNotNullValidatorImpl.class)
public @interface ListNotNull {

    String message() default "List不允许空元素";

    Class<?>[] groups () default {};

    Class<? extends Payload> [] payload() default {};


    @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
    @Retention(RUNTIME)
    @Documented
    @interface List {
        ListNotNull [] value();
    }
}
