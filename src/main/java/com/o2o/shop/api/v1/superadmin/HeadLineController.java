package com.o2o.shop.api.v1.superadmin;

import com.o2o.shop.dto.superadmin.HeadLineDTO;
import com.o2o.shop.service.HeadLineService;
import com.o2o.shop.validator.area.UpdateGroup;
import com.o2o.shop.vo.HeadLineVO;
import com.o2o.shop.vo.ResultDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * HeadLineController 头条信息管理(Admin)
 * @author 勿忘初心
 * @since 2023-07-06-17:49
 */

@RestController
@Slf4j
@Validated
@RequestMapping("/admin")
public class HeadLineController {

    @Autowired
    private HeadLineService headLineService;
    /**
     * 获取所有的头条信息
     * @return
     */
    @GetMapping("/headline/all")
    public List<HeadLineVO> allHeadLineList(){
        return headLineService.allHeadLineList();
    }

    /**
     * 获取指定的头条信息
     * @param id 头条id
     * @return
     */
    @GetMapping("/headline/{id}")
    public HeadLineVO queryHeadLineById(@PathVariable
                                 @NotNull(message = "头条id不能为空")
                                 @Min(value = 1,message = "参数不合法") Integer id){
        return headLineService.queryHeadLine(id);
    }

    /**
     * 新增头条信息,此处不能使用RequestBody注解，因为需要上传图片，contentType = multipart/form-data,
     * @RequestBody 注解将限制参数类型为application/json
     * @param headLineDTO 头条信息DTO
     * @param headLineImg 头条图像
     * @return
     */
    @PostMapping("/headline/add")
    public ResultDataVO createHeadLine(@Validated HeadLineDTO headLineDTO,
                                @RequestParam MultipartFile headLineImg){
        return headLineService.createHeadLine(headLineDTO,headLineImg);
    }

    /**
     * 根据id删除头条信息
     * @param id 头条id
     * @return
     */
    @DeleteMapping("/headline/{id}")
    public ResultDataVO deleteHandLineById(@PathVariable Integer id){
        return headLineService.deleteHeadLineById(id);
    }

    /**
     * 根据Id更新头条信息
     * @param headLineDTO 头条对象DTO
     * @param headLineImg 更新时允许为空
     * @return
     */
    @PutMapping("/headline/update")
    public ResultDataVO updateHandLineById(@Validated(value = {UpdateGroup.class}) HeadLineDTO headLineDTO,
                                           @RequestParam(required = false) MultipartFile headLineImg){
        return headLineService.updateHeadLine(headLineDTO,headLineImg);
    }

    /**
     * 批量删除头条信息
     * @param idList 头条信息Id列表
     * @return
     */
    @DeleteMapping("/headline/batch")
    public ResultDataVO batchDeleteArea(@RequestBody
                                        @NotEmpty List<Integer> idList){
        return headLineService.batchDeleteHeadLine(idList);
    }

}
