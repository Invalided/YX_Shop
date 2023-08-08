package com.o2o.shop.api.v1;

import com.o2o.shop.dto.user.RegisterDTO;
import com.o2o.shop.dto.user.UpdateDTO;
import com.o2o.shop.service.UserService;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import com.o2o.shop.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * UserController 用户模块简单示例
 * @Author: 勿忘初心
 * @Date: 2023-06-26 0:56
 */


@RestController
@RequestMapping("/v1/user")
@Slf4j
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 根据Id获取用户信息
     * @param uid 用户uid
     * @return
     */
    @GetMapping("/{id}")
    public UserVO getUser(@Min(value = 1,message = "输入的参数不合法") @PathVariable(name = "id") Integer uid) {
        return userService.getUserById(uid);
    }

    /**
     * 新增用户信息
     * @param addUserDto 新增用户DTO对象
     * @return
     */
    @PostMapping
    public ResultDataVO addUser(@Validated @RequestBody RegisterDTO addUserDto) {
        return userService.addUser(addUserDto);
    }

    /**
     * 修改用户信息
     * @param updateUserDto 更新用户DTO对象
     * @return
     */
    @PutMapping
    public ResultDataVO updateUser(@Validated @RequestBody UpdateDTO updateUserDto) {
        return userService.updateUser(updateUserDto);
    }

    /***
     * 根据id删除用户
     * @param id 用户id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResultDataVO deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    /**
     * 分页查询用户
     * @param pageNum 当前页码
     * @param pageSize 页面数量
     * @return
     */
    @GetMapping
    public PageVO getUser(@NotNull @Range(min = 1,max = 100,message = "参数错误") Integer pageNum,
                          @NotNull @Range(min = 1,max = 10,message = "参数错误") Integer pageSize) {
        return userService.getAllUser(pageNum, pageSize);
    }
}
