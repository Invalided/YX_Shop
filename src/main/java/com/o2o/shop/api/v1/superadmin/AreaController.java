package com.o2o.shop.api.v1.superadmin;

import com.o2o.shop.dto.superadmin.AreaDTO;
import com.o2o.shop.service.AreaService;
import com.o2o.shop.validator.area.AddGroup;
import com.o2o.shop.validator.area.UpdateGroup;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import com.o2o.shop.vo.area.AreaVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * AreaController  区域信息管理(Admin)
 * @author 勿忘初心
 * @since 2023-07-04-13:45
 */
@RestController
@Validated
@Slf4j
@RequestMapping("/admin")
public class AreaController {

    @Autowired
    private AreaService areaService;

    /**
     * 根据Id获取区域信息
     * @param id 区域Id
     * @return
     */
    @GetMapping("/area/{id}")
    public AreaVO getAreaById(@PathVariable @NotNull(message = "id不能为空")
                                            @Min(value = 1,message = "参数不合法") Integer id){
        return areaService.queryAreaById(id);
    }

    /**
     * 获取所有的区域信息
     * @return
     */
    @GetMapping("/area/list")
    public PageVO getAreaList(){
        return areaService.queryAreaList();
    }

    /**
     * 新增区域信息
     * @param areaDTO 新增区域信息DTO对象
     * @return
     */
    @PostMapping("/area/add")
    public ResultDataVO createArea(@RequestBody @Validated(AddGroup.class) AreaDTO areaDTO){
        return areaService.createArea(areaDTO);
    }

    /**
     * 删除区域信息
     * @param id 区域id
     * @return
     */
    @DeleteMapping("/area/{id}")
    public ResultDataVO deleteAreaById(@PathVariable Integer id){
        return areaService.deleteAreaById(id);
    }

    /**
     * 更新区域信息
     * @param areaDTO
     * @return
     */
    @PutMapping("/area/update")
    public ResultDataVO updateArea(@RequestBody @Validated(UpdateGroup.class) AreaDTO areaDTO){
        return areaService.updateArea(areaDTO);
    }

    /**
     * 批量删除区域信息
     * @param idList 区域信息id集合
     * @return
     */
    @DeleteMapping("/area/batch")
    public ResultDataVO batchDeleteArea(@RequestBody
                                            @NotEmpty List<Integer> idList){
        return areaService.batchDeleteArea(idList);
    }
}
