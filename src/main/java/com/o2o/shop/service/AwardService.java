package com.o2o.shop.service;

import com.o2o.shop.dto.mall.AwardDTO;
import com.o2o.shop.dto.shopmanager.ManageAwardDTO;
import com.o2o.shop.vo.AwardVO;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author 勿忘初心
 * @since 2023-07-23-13:09
 * 奖品管理
 */
@Validated
public interface AwardService {

    /**
     * 根据条件查询奖品信息,该方法使用MP的QueryWrapper实现条件查询
     *
     * @param awardDTO 奖品DTO,NotNUll
     * @param pageNum 当前页码
     * @param pageSize 页面数据量
     * @return
     */
    PageVO queryAwardByCondition(@NotNull(message = "AwardDTO不能为空") AwardDTO awardDTO, Integer pageNum, Integer pageSize);

    /**
     * 根据奖品id获取奖品的详细信息，奖品详情图等
     * @param awardId
     * @return
     */
    AwardVO queryAwardById(Integer awardId);

    /**
     * 创建奖品
     * @param manageAwardDTO 奖品管理DTO对象
     * @param thumbnail 缩略图
     * @return
     */

    ResultDataVO createAward(ManageAwardDTO manageAwardDTO, MultipartFile thumbnail);

    /**
     * 修改奖品信息
     * @param manageAwardDTO 奖品管理DTO对象
     * @param thumbnail 缩略图
     * @return
     */
    ResultDataVO updateAward(ManageAwardDTO manageAwardDTO, MultipartFile thumbnail);
}
