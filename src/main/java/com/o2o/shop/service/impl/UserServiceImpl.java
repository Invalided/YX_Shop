package com.o2o.shop.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o2o.shop.dto.user.RegisterDTO;
import com.o2o.shop.dto.user.UpdateDTO;
import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import com.o2o.shop.mapper.UserMapper;
import com.o2o.shop.model.UserDO;
import com.o2o.shop.service.UserService;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import com.o2o.shop.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 勿忘初心
 * @since 2023-06-21-17:41
 * 用户接口实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ResultDataVO addUser(RegisterDTO userDTO) {
        UserDO userDO = new UserDO();
        // 将userDTO获取的数据复制到user对象中
        userDTO.setId(null);
        BeanUtils.copyProperties(userDTO, userDO);
        userMapper.insert(userDO);
        return ResultDataVO.success(null);
    }

    /**
     * 更新删除操作前先查
     * @param userDTO
     * @return
     */
    @Override
    public ResultDataVO updateUser(UpdateDTO userDTO) {
        /**
         * todo 用户输入的数据名称与DTO不一致时,若未使用@NotNULL注解，则会出现字段为NULL但操作成功的情况，
         *  需结合实际进行二次校验
         */
        // 获取需要更新的用户信息
        UserDO userDO = userMapper.selectById(userDTO.getId());
        if(userDO == null){
            throw new BusinessException(ExceptionCodeEnum.EC20001);
        }
        // 复制对应字段
        BeanUtils.copyProperties(userDTO, userDO);
        int size = userMapper.updateById(userDO);
        return ResultDataVO.success(null);
    }

    @Override
    public ResultDataVO deleteUser(Long id) {
        UserDO userDO = userMapper.selectById(id);
        if(userDO == null){
            throw new BusinessException(ExceptionCodeEnum.EC20001);
        }
        // 逻辑删除
        userMapper.deleteById(id);
        return ResultDataVO.success(null);
    }

    @Override
    public PageVO getAllUser(Integer pageNum, Integer pageSize) {
        // todo 参数二次校验，超出范围则抛异常
        // 添加查询条件
        Page<UserDO> userPage = new Page<>(pageNum,pageSize);
        userMapper.selectPage(userPage, null);
        List<UserVO> userVos = userPage.getRecords().stream()
                .map(userDO -> {
                    UserVO userVO = new UserVO();
                    BeanUtils.copyProperties(userDO,userVO);
                    return userVO;
                }).collect(Collectors.toList());
        return new PageVO(userVos, userVos.size());
    }

    @Override
    public UserVO getUserById(Integer id) {
       UserDO userDO = userMapper.selectById(id);
       if(userDO == null){
           throw new BusinessException(ExceptionCodeEnum.EC20001);
       }
       UserVO userVO = new UserVO();
       BeanUtils.copyProperties(userDO,userVO);
       return userVO;
    }
}
