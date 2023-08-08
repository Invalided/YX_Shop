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
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResultDataVO<T> failure(Integer code, String message){

        return ResultDataVO.<T>builder()
                .success(false)
                .code(code)
                .message(message)
                .data(null)
                .build();
    }
}
