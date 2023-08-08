package com.o2o.shop.service;

import com.o2o.shop.dto.superadmin.AreaDTO;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import com.o2o.shop.vo.area.AreaVO;

import java.util.List;

/**
 * @author 勿忘初心
 * @since 2023-07-03-23:03
 * 区域信息接口
 */
public interface AreaService {
    /**
     * redis中area的key
     */
    String AREA_LIST = "area_list";
    /**
     * 查询所有的区域信息
     *
     * @return
     */
    PageVO queryAreaList();

    /**
     * 根据Id获取区域信息
     * @return
     * @param id
     */
    AreaVO queryAreaById(Integer id);

    /**
     * 新增区域信息
     * @param areaDTO
     * @return
     */
    ResultDataVO createArea(AreaDTO areaDTO);

    /**
     * 根据Id删除区域信息
     * @param  id
     * @return
     */
    ResultDataVO deleteAreaById(Integer id);

    /**
     * 更新区域信息
     * @param areaDTO
     * @return
     */
    ResultDataVO updateArea(AreaDTO areaDTO);

    /**
     * 批量删除区域信息
     * @param idList
     * @return
     */
    ResultDataVO batchDeleteArea(List<Integer> idList);
}
