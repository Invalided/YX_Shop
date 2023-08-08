package com.o2o.shop.api.v1.shopmanager;

import com.o2o.shop.dto.mall.AwardDTO;
import com.o2o.shop.dto.shopmanager.ManageAwardDTO;
import com.o2o.shop.service.AwardService;
import com.o2o.shop.validator.area.AddGroup;
import com.o2o.shop.validator.area.UpdateGroup;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * AwardManageController 奖品管理(Manager)
 * @author 勿忘初心
 * @since 2023-07-23-15:10
 */
@RestController
@RequestMapping("/manager")
@Validated
public class AwardManageController {

    @Autowired
    private AwardService awardService;

    /**
     * 获取当前的奖品列表
     * @param pageNum 当前页码
     * @param pageSize 页面数据量
     * @return
     */
    @GetMapping("/award/list")
    PageVO queryAwardListByShopId(AwardDTO awardDTO,
                                  @Range(min = 1,max = 10,message = "pageNum不合法") Integer pageNum,
                                  @Range(min = 1,max = 10,message = "pageSize不合法") Integer pageSize){
        return awardService.queryAwardByCondition(awardDTO,pageNum,pageSize);
    }

    /**
     * 创建奖品信息
     * @param manageAwardDTO 奖品DTO
     * @param thumbnail 缩略图
     * @return
     */
    @PostMapping("/award/operation")
    ResultDataVO createAward(@Validated(AddGroup.class) ManageAwardDTO manageAwardDTO,
                             @RequestParam MultipartFile thumbnail){
        return awardService.createAward(manageAwardDTO,thumbnail);
    }

    /**
     * 更新奖品信息
     * @param manageAwardDTO 奖品DTO
     * @param thumbnail 缩略图
     * @return
     */
    @PutMapping("/award/edit")
    ResultDataVO updateAward(@Validated(UpdateGroup.class) ManageAwardDTO manageAwardDTO,
                             MultipartFile thumbnail){
        return awardService.updateAward(manageAwardDTO,thumbnail);
    }
}
