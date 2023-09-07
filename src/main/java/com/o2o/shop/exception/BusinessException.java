package com.o2o.shop.exception;

import lombok.Getter;

/**
 * @author 勿忘初心
 * @since 2023-06-20-0:53
 *
 * 业务异常类
 */

@Getter
public class BusinessException extends RuntimeException {

    /**
     * 错误对象枚举
     */
    private ExceptionCodeEnum codeEnum;
    /**
     * 根据传入的异常枚举解析异常相关信息。
     * @param codeEnum
     */
    public BusinessException(ExceptionCodeEnum codeEnum){
        this.codeEnum = codeEnum;
    }
}

