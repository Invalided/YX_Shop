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
     * 错误编码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String message;

    /**
     * 根据传入的异常枚举解析异常相关信息。
     * @param codeEnum
     */
    public BusinessException(ExceptionCodeEnum codeEnum){
        this.message = codeEnum.getMessage();
        this.code = codeEnum.getCode();
    }
}

