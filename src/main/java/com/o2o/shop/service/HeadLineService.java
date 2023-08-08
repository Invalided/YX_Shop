package com.o2o.shop.service;

import com.o2o.shop.bo.HeadLineBO;
import com.o2o.shop.dto.superadmin.HeadLineDTO;
import com.o2o.shop.vo.HeadLineVO;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 
 * 头条信息接口类
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
public interface HeadLineService {
    /**
     * Redis key 头条信息
     */
    String HEAD_LINE = "head_line";

    /**
     * 获取所有的头条信息
     * @return
     */
    List<HeadLineVO> allHeadLineList();

    /**
     * 头条信息分页查询，获取
     * @param pageNum 当前页码
     * @param PageSize 页码数据量
     * @param headLineCondition 查询条件
     * @return
     */
    PageVO<HeadLineVO> allHeadLineList(Integer pageNum, Integer PageSize, HeadLineBO headLineCondition);

    /**
     * 获取指定的头条信息
     * @param id
     * @return
     */
    HeadLineVO queryHeadLine(Integer id);

    /**
     * 新增头条信息
     * @param headLineDTO
     * @param headLineImg
     * @return
     */
    ResultDataVO createHeadLine(HeadLineDTO headLineDTO, MultipartFile headLineImg);

    /**
     * 删除头条信息
     * @param id
     * @return
     */
    ResultDataVO deleteHeadLineById(Integer id);

    /**
     * 更新头条信息
     * @param headLineDTO
     * @param headLineImg
     * @return
     */
    ResultDataVO updateHeadLine(HeadLineDTO headLineDTO,MultipartFile headLineImg);

    /**
     * 批量删除头条信息
     * @param idList
     * @return
     */
    ResultDataVO batchDeleteHeadLine(List<Integer> idList);
}
