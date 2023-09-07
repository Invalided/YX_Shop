package com.o2o.shop.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.o2o.shop.exception.ExceptionCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author 勿忘初心
 * @since 2023-06-19-17:30
 * 统一结果返回格式
 */
@Data
@Builder
@AllArgsConstructor
public class ResultDataVO<T> {
    /**
     * 调用结果状态
     */
    private Boolean success;
    /**
     * 响应代码
     */
    private Integer code;
    /**
     * 详细信息
     */
    private String message;
    /**
     * 返回数据,数据为空则不返回
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;


    /**
     * 操作成功时返回的数据
     * @param result
     * @param <T>
     * @return
     */
    public static <T> ResultDataVO<T> success(T result) {

        return ResultDataVO.<T>builder()
                .success(true)
                .code(ExceptionCodeEnum.EC0.getCode())
                .message(ExceptionCodeEnum.EC0.getMessage())
                .data(result)
                .build();
    }

    /**
     * 操作失败
     * @param <T>
     * @param exceptionCodeEnum 错误类型枚举
     * @return
     */
    public static <T> ResultDataVO<T> failure(ExceptionCodeEnum exceptionCodeEnum){

        return ResultDataVO.<T>builder()
                .success(false)
                .code(exceptionCodeEnum.getCode())
                .message(exceptionCodeEnum.getMessage())
                .data(null)
                .build();
    }

    /**
     * 操作失败,返回信息
     * @param exceptionCodeEnum 错误信息列表
     * @param result 对应失败信息对象
     * @param <T>
     * @return
     */
    public static <T> ResultDataVO<T> failure(ExceptionCodeEnum exceptionCodeEnum, T result){

        return ResultDataVO.<T>builder()
                .success(false)
                .code(exceptionCodeEnum.getCode())
                .message(exceptionCodeEnum.getMessage())
                .data(result)
                .build();
    }
}
