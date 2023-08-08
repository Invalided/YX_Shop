package com.o2o.shop.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 勿忘初心
 * @since 2023-07-09-16:52
 * Redis缓存读取转换操作
 */
@Component
@Slf4j
public class CacheConvert<T> {

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 提供JSON转换
     */
    private ObjectMapper objectMapper = new ObjectMapper();
    /**
     * 根据key和存储的类信息返回缓存中的存在的数据并转换类型
     * @param key 缓存key
     * @param referenceType 参考类信息
     * @return
     */
    public List<T> readCache(String key,List<T> referenceType){
        List<T> list = null;
        // 取出存放的JSON字符串
        String headLineListJSON = redisOperator.get(key);
        // 将JSON字符串转为对象
        try {
            list = objectMapper.readValue(headLineListJSON,
                    new TypeReference<List<T>>(){});
        }catch (JacksonException e){
            log.error("捕获Redis中获取Json转换异常", e);
            throw new BusinessException(ExceptionCodeEnum.EC10000);
        }
        return list;
    }

    /**
     * 根据传入的list参数转为JSON对象并存入Redis
     * @param key 缓存对象的键值
     * @param sourceList 需存入的List对象
     */
    public void writeCache(String key,List<T> sourceList){
        // 转为JSON格式
        try {
            String listJSON = objectMapper.writeValueAsString(sourceList);
            // 存入Redis中
            redisOperator.set(key, listJSON);
        } catch (JsonProcessingException e) {
            log.error("捕获Redis中对象转为JSON格式异常", e);
            throw new BusinessException(ExceptionCodeEnum.EC10000);
        }
    }

    /**
     * 根据key清除指定缓存
     * @param key
     */
    public void clearCache(String key){
        if(redisOperator.keyIsExist(key)){
            redisOperator.del(key);
        }
    }
}
