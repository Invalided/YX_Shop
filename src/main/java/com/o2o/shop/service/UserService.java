package com.o2o.shop.service;

import com.o2o.shop.dto.user.RegisterDTO;
import com.o2o.shop.dto.user.UpdateDTO;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import com.o2o.shop.vo.UserVO;

/**
 * @author 勿忘初心
 * @since 2023-06-21-17:41
 * 用户接口
 */

public interface UserService {
    /**
     * 新增用户
     * @param userDTO
     * @return
     */
    ResultDataVO addUser(RegisterDTO userDTO);

    /**
     * 更新用户
     * @param userDTO
     * @return
     */
    ResultDataVO updateUser(UpdateDTO userDTO);

    /**
     * 删除用户
     * @param id
     * @return
     */
    ResultDataVO deleteUser(Long id);

    /**
     * 获取所有用户(分页)
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageVO getAllUser(Integer pageNum, Integer pageSize);

    /**
     * 查询指定用户
     * @param id
     * @return
     */
    UserVO getUserById(Integer id);

}
