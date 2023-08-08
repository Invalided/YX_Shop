package com.o2o.shop.api.v1;

import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import com.o2o.shop.util.RedisOperator;
import com.o2o.shop.vo.ResultDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * RedisController Redis简单使用示例
 * @author 勿忘初心
 * @since 2023-06-30-19:33
 *
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/v1/redis")
public class RedisController {

    /**
     * 注入redis工具
     */

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 根据key获取value
     * @param key key
     * @return
     */
    @GetMapping
    public String getKey(@RequestParam @NotNull(message = "参数不能为空") String key){
        String value = redisOperator.get(key);
        // 获取不到资源则抛出异常
        if(value == null){
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        return value;
    }


    /**
     * 仅作演示使用，传入key、value值存入缓存
     * @param key key
     * @param value value
     * @return
     */
    @PostMapping
    public ResultDataVO createCache(@RequestParam @NotNull(message = "参数不能为空") String key,
                                    @RequestParam @NotNull(message = "参数不能为空") String value){

        // 判断key是否存在
        if(redisOperator.keyIsExist(key)){
            // 获取先前的key值，将新值追加，逗号分割
            StringBuffer stringBuffer = new StringBuffer(getKey(key));
            stringBuffer.append(","+value);
            value = stringBuffer.toString();
        }
        // 添加一条缓存信息，60s后过期
        redisOperator.set(key,value,60);
        return ResultDataVO.success(null);
    }

    /**
     * 根据key删除缓存
     * @param key key
     * @return
     */
    @DeleteMapping
    public ResultDataVO deleteCacheByKey(@RequestParam @NotNull(message = "参数不能为空") String key){
        // 存在则删除，不存在抛出异常
        if(redisOperator.keyIsExist(key)){
            redisOperator.del(key);
        }else{
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        return ResultDataVO.success(null);
    }

}
