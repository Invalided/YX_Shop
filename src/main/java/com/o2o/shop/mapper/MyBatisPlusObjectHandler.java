package com.o2o.shop.mapper;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author 勿忘初心
 * @since 2023-06-21-17:20
 * 用于实现create_time和update_time的自动填充
 * 07.03 数据库对应字段为last_edit_time，作以修改。
 */
@Component
public class MyBatisPlusObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        setFieldValByName("createTime", new Date(), metaObject);
        setFieldValByName("updateTime",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updateTime",new Date(),metaObject);
    }
}
