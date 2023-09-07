package com.o2o.shop.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.shop.vo.ResultDataVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Resource;
import javax.validation.ConstraintViolationException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.zip.DataFormatException;

/**
 * @Author: 勿忘初心
 * @Date: 2023-06-28 17:22
 * 全局异常统一处理
 * 新增响应体增强，用于消除重复调用ResultVO对象返回数据而产生的代码冗余以及不便
 * @RestControllerAdvice 全局捕获抛出的异常，全局数据绑定，全局数据预处理。
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice implements ResponseBodyAdvice<Object> {


    /**
     * json格式化操作
     */
    @Resource
    private ObjectMapper objectMapper;

    // todo 继承AbstractErrorController实现相关异常拦截
    /**
     * 继承自AbstractErrorController,用于处理未进入Controller的HTTP相关错误 400
     * @param errorAttributes
     */

    /**
     * http请求的方法不正确
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResultDataVO handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("捕获http请求的方法不匹配异常", e);
        return ResultDataVO.failure(ExceptionCodeEnum.EC10006);
    }

    /**
     * 请求参数不全
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResultDataVO missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException e) {
        log.error("捕获参数缺失异常", e);
        return ResultDataVO.failure(ExceptionCodeEnum.EC10001);
    }

    /**
     * 请求参数类型不正确
     */
    @ExceptionHandler(TypeMismatchException.class)
    public ResultDataVO typeMismatchExceptionHandler(TypeMismatchException e) {
        log.error("捕获请求参数类型不匹配异常", e);
        return ResultDataVO.failure(ExceptionCodeEnum.EC10001);
    }

    /**
     * 数据格式转换错误
     */
    @ExceptionHandler(DataFormatException.class)
    @ResponseBody
    public ResultDataVO dataFormatExceptionHandler(DataFormatException e) {
        log.error("捕获数据格式转换错误异常", e);
        return ResultDataVO.failure(ExceptionCodeEnum.EC10001);
    }


    /**
     * 请求缺少必要参数异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResultDataVO handleHttpBindException(HttpMessageNotReadableException e) {
        log.error("捕获HTTP请求参数缺失异常", e);
        return ResultDataVO.failure(ExceptionCodeEnum.EC10001);
    }

    /**
     * 业务异常捕获
     *
     * @param businessException
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    public ResultDataVO handleBusinessException(BusinessException businessException) {
        log.error("捕获业务异常", businessException);
        return ResultDataVO.failure(businessException.getCodeEnum());
    }


    /**
     * 普通参数入参不合法,类型不匹配
     * @return
     */
    //@ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    //public ResultDataVO handleArgumentTypeException(MethodArgumentTypeMismatchException e){
    //    log.error("捕获参数类型不匹配异常",e);
    //    return ResultDataVO.failure(ExceptionCodeEnum.EC10001);
    //}

    /**
     * 请求参数绑定到JavaBean对象参数检验失败异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public ResultDataVO handleBeanBindException(BindException e) {
        StringBuffer errMsg = new StringBuffer();
        if(e instanceof MethodArgumentNotValidException){
            log.error("捕获绑定参数类型无效参数异常", e);
        }else{
            log.error("捕获绑定Bean对象参数异常",e);
        }
        // 参数错误有多个，需要用List接收
        List<ObjectError> errorList = e.getBindingResult().getAllErrors();
        // 拼接错误信息
        errorList.forEach(error ->
                errMsg.append(error.getDefaultMessage()).append(";"));
        return ResultDataVO.failure(ExceptionCodeEnum.EC10001);
    }

    /**
     * 普通类型参数对象绑定失败异常
     *
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultDataVO handleMethodArgsNotValidException(ConstraintViolationException e) {
        log.error("捕获普通参数异常", e);
        String sourceMsg = e.getMessage();
        // [),需要+1
        int index = sourceMsg.lastIndexOf(".") + 1;
        String errMsg = sourceMsg.substring(index).replace(": ", "");
        return ResultDataVO.failure(ExceptionCodeEnum.EC10001);
    }

    /**
     * Servlet请求异常错误，用于捕获使用表单数据提交图片时出现的异常信息
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MissingServletRequestPartException.class)
    public ResultDataVO handleMissingServletRequestPart(MissingServletRequestPartException e) {
        log.error("捕获表单数据获取异常,可能发生在图片上传时参数缺失或图片不存在", e);
        return ResultDataVO.failure(ExceptionCodeEnum.EC10001);
    }

    /**
     * 图片大小超过限制异常，application.yml中的max-file-size: 2MB,限制文件2MB
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResultDataVO handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error("捕获图片上传超过指定大小异常", e);
        return ResultDataVO.failure(ExceptionCodeEnum.EC10012);
    }

    /**
     * redis服务异常,该异常捕获后将不向前端返回,只记录日志中
     *
     * @param e
     */
    @ExceptionHandler(value = RedisConnectionFailureException.class)
    public void handRedisConnectionFailureException(RedisConnectionFailureException e) {
        log.error("捕获Redis服务连接异常,", e);
    }

    /**
     * 运行时异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    public ResultDataVO handleRuntimeException(RuntimeException e) {
        log.error("捕获运行时异常", e);
        return ResultDataVO.failure(ExceptionCodeEnum.EC10000);
    }


    /**
     * 系统级异常
     *
     * @param throwable
     */
    @ExceptionHandler(value = Throwable.class)
    public ResultDataVO handleThrowable(Throwable throwable) {
        log.error("捕获系统级异常", throwable);
        return ResultDataVO.failure(ExceptionCodeEnum.EC10000);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        /**
         * 未被捕获的错误进行拦截
         */
        if(body == null){
            log.error("未处理的异常信息,可能发生在Redis/数据库连接错误中,请检查错误日志");
            return ResultDataVO.failure(ExceptionCodeEnum.EC10000);
        }

        /**
         * 返回类型为String则需要手动序列化
         */
        if (body instanceof String) {
            return objectMapper.writeValueAsString(ResultDataVO.success(body));
        }
        /**
         * 已被包装为全局VO对象直接返回
         */
        if (body instanceof ResultDataVO) {
            return body;
        }
        /**
         * 判断是否为404,500等错误类型
         */
        if (body instanceof LinkedHashMap) {
            LinkedHashMap<String, Object> httpErrorCode = (LinkedHashMap<String, Object>) body;
            Integer code = (Integer) httpErrorCode.get("status");
            String message = (String) httpErrorCode.get("error");
            return new ResultDataVO(false, code, message, null);

        }

        return ResultDataVO.success(body);
    }
}
