package com.o2o.shop.service;

import com.o2o.shop.dto.superadmin.PersonInfoDTO;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.PersonInfoVO;
import com.o2o.shop.vo.ResultDataVO;

/**
 *
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
public interface PersonInfoService {

    /**
     * 根据用户Id获取用户信息
     * @param userId
     * @return
     */
    PersonInfoVO queryPersonInfoById(Integer userId);

    /**
     * 分页查询返回用户数据
     * @param pageNum 当前页码
     * @param pageSize 页面数量
     * @return
     */
    PageVO queryPersonInfoList(Integer pageNum,Integer pageSize);

    /**
     * 修改用户信息
     * @param personInfoDTO 个人信息dto对象
     * @return
     */
    ResultDataVO updatePersonInfo(PersonInfoDTO personInfoDTO);

    // 删除账号暂不实现，在上方的禁用中体现
}
