package com.o2o.shop.api.v1.superadmin;

import com.o2o.shop.dto.superadmin.PersonInfoDTO;
import com.o2o.shop.service.PersonInfoService;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.PersonInfoVO;
import com.o2o.shop.vo.ResultDataVO;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * PersonInfoController 用户信息管理(Admin)
 * @author 勿忘初心
 * @since 2023-07-07-20:35
 */
@RestController
@Validated
@RequestMapping("/admin")
public class PersonInfoController {

    @Autowired
    private PersonInfoService personInfoService;

    /**
     * 根据用户Id获取用户信息
     * @param id 用户Id
     * @return
     */
    @GetMapping("/person/{id}")
    public PersonInfoVO getPersonInfoById(@PathVariable @NotNull(message = "用户id不能为空") Integer id){
        return personInfoService.queryPersonInfoById(id);
    }

    /**
     * 分页查询返回用户数据
     * @param pageNum 当前页码
     * @param pageSize 页面数量
     * @return
     */
    @GetMapping("/person/all")
    public PageVO getPersonInfoList(@NotNull @Range(min = 1,max = 100,message = "参数错误") Integer pageNum,
                                    @NotNull @Range(min = 1,max = 20,message = "参数错误") Integer pageSize){
        return personInfoService.queryPersonInfoList(pageNum, pageSize);
    }

    /**
     * 修改用户信息
     * @param personInfoDTO 个人信息dto对象
     * @return
     */
    @PutMapping("/person/update")
    public ResultDataVO updatePersonInfo(@RequestBody @Validated PersonInfoDTO personInfoDTO){
        return personInfoService.updatePersonInfo(personInfoDTO);
    }

}
