package com.o2o.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.o2o.shop.model.LocalAuthDO;
import com.o2o.shop.vo.LocalAuthVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
@Mapper
public interface LocalAuthMapper extends BaseMapper<LocalAuthDO> {
    /**
     * 根据用户名密码获取用户相关信息
     *
     * @param userName
     * @param passWord
     * @return
     */
    LocalAuthVO queryLocalAuthInfo(@Param("username") String userName,
                                   @Param("password") String passWord);

}
