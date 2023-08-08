package com.o2o.shop.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o2o.shop.dto.superadmin.PersonInfoDTO;
import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import com.o2o.shop.mapper.PersonInfoMapper;
import com.o2o.shop.model.PersonInfoDO;
import com.o2o.shop.service.PersonInfoService;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.PersonInfoVO;
import com.o2o.shop.vo.ResultDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 勿忘初心
 * @since 2023-07-07-14:42
 */
@Service
@Slf4j
public class PersonInfoServiceImpl implements PersonInfoService {

    @Autowired
    private PersonInfoMapper personInfoMapper;

    @Override
    public PersonInfoVO queryPersonInfoById(Integer userId) {
        PersonInfoDO personInfoDO = personInfoMapper.selectById(userId);
        if(personInfoDO == null){
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        PersonInfoVO personInfoVO = new PersonInfoVO();
        BeanUtils.copyProperties(personInfoDO, personInfoVO);
        return personInfoVO;
    }

    @Override
    public PageVO queryPersonInfoList(Integer pageNum, Integer pageSize) {

        // 拼接查询条件
        Page<PersonInfoDO> personInfoPage = new Page<>(pageNum,pageSize);
        personInfoMapper.selectPage(personInfoPage,null);
        List<PersonInfoVO> personInfoVOList = personInfoPage.getRecords().stream()
                .map(personInfoDO -> {
                    // 实例化需要返回的对象
                    PersonInfoVO personInfoVO = new PersonInfoVO();
                    // 复制属性
                    BeanUtils.copyProperties(personInfoDO, personInfoVO);
                    return personInfoVO;
                }).collect(Collectors.toList());
        return new PageVO(personInfoVOList,personInfoVOList.size());
    }

    @Override
    public ResultDataVO updatePersonInfo(PersonInfoDTO personInfoDTO) {
        // 更新前先查
        PersonInfoDO personInfoDO = personInfoMapper.selectById(personInfoDTO.getUserId());
        if(personInfoDO == null){
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        BeanUtils.copyProperties(personInfoDTO,personInfoDO);
        int effectNum = personInfoMapper.updateById(personInfoDO);
        if(effectNum < 1){
            log.error("用户信息修改失败");
            throw new BusinessException(ExceptionCodeEnum.EC10010);
        }
        return ResultDataVO.success(null);
    }
}
