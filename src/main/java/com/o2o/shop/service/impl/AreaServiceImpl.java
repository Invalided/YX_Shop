package com.o2o.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.o2o.shop.config.quartz.RedisCheckConfig;
import com.o2o.shop.dto.superadmin.AreaDTO;
import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import com.o2o.shop.mapper.AreaMapper;
import com.o2o.shop.model.AreaDO;
import com.o2o.shop.service.AreaService;
import com.o2o.shop.util.CacheConvert;
import com.o2o.shop.util.RedisOperator;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import com.o2o.shop.vo.area.AreaVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 勿忘初心
 * @since 2023-07-03-23:21
 */
@Service
@Slf4j
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private RedisOperator redisOperator;
    
    @Autowired
    private CacheConvert cacheConvert;
    
    @Override
    public PageVO queryAreaList() {
        List<AreaVO> areaVOList;
        boolean redisCache = false;
        // 使用Redis时先看是否存活
        if(RedisCheckConfig.redisConnected){
            // 从Redis中读取数据
            redisCache = redisOperator.keyIsExist(AreaService.AREA_LIST);
        }
        // 是否有缓存
        if(redisCache){
            areaVOList = cacheConvert.readCache(AreaService.AREA_LIST,new ArrayList<AreaVO>());
        }else{
            // 拼接QueryWrapper条件,倒序输出
            QueryWrapper<AreaDO> areaWrapper = new QueryWrapper<>();
            areaWrapper.orderByDesc("priority");
            // 通过stream流的方式将属性拷贝到VO对象中
            areaVOList = areaMapper.selectList(areaWrapper)
                    .stream()
                    .map(areaDO -> {
                        AreaVO areaVO = new AreaVO();
                        BeanUtils.copyProperties(areaDO,areaVO);
                        return areaVO;
                    }).collect(Collectors.toList());
            if(areaVOList.size() < 1){
                throw new BusinessException(ExceptionCodeEnum.EC10002);
            }
            // 转为JSON格式,先看redis是否存活
            if(RedisCheckConfig.redisConnected){
                cacheConvert.writeCache(AreaService.AREA_LIST, areaVOList);
            }
        }
        return new PageVO(areaVOList,areaVOList.size());
    }

    @Override
    public AreaVO queryAreaById(Integer id) {
        AreaVO areaVO = new AreaVO();
        AreaDO areaDO = areaMapper.selectById(id);
        if(areaDO == null){
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        BeanUtils.copyProperties(areaDO, areaVO);
        return areaVO;
    }

    @Override
    public ResultDataVO createArea(AreaDTO areaDTO) {
        // 使用QueryWrapper来拼接条件
        QueryWrapper<AreaDO> queryWrapper = new QueryWrapper();
        // 填入参数,where限制
        queryWrapper.eq("area_name",areaDTO.getAreaName());
        AreaDO areaDO = areaMapper.selectOne(queryWrapper);
        if(areaDO != null){
            throw new BusinessException(ExceptionCodeEnum.EC10007);
        }
        // 需要实例化areaDO对象，否则会出现target must be not null 错误
        areaDO = new AreaDO();
        // 对象属性转换
        BeanUtils.copyProperties(areaDTO, areaDO);
        areaMapper.insert(areaDO);
        // 清除缓存
        if(RedisCheckConfig.redisConnected){
            cacheConvert.clearCache(AreaService.AREA_LIST);
        }
        return ResultDataVO.success(null);
    }

    @Override
    public ResultDataVO deleteAreaById(Integer id) {
        // 删除前先查询
        AreaDO areaDO = areaMapper.selectById(id);
        if(areaDO == null){
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        areaMapper.deleteById(id);
        if(RedisCheckConfig.redisConnected){
            cacheConvert.clearCache(AreaService.AREA_LIST);
        }
        return ResultDataVO.success(null);
    }

    @Override
    public ResultDataVO updateArea(AreaDTO areaDTO) {
        // 更新前先查,是否属性同名问题
        QueryWrapper<AreaDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("area_name",areaDTO.getAreaName());
        AreaDO areaDO = areaMapper.selectOne(queryWrapper);
        // 判断是否为空
        if(areaDO != null){
            throw new BusinessException(ExceptionCodeEnum.EC10007);
        }
        // 再查该记录是否存在
        areaDO = areaMapper.selectById(areaDTO.getAreaId());
        // 若不为空则会取回当前的version,用于乐观锁实现
        if(areaDO == null){
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        BeanUtils.copyProperties(areaDTO, areaDO);
        areaMapper.updateById(areaDO);
        // 删除Redis缓存
        if(RedisCheckConfig.redisConnected){
            cacheConvert.clearCache(AreaService.AREA_LIST);
        }
        return ResultDataVO.success(null);
    }

    @Override
    public ResultDataVO batchDeleteArea(List<Integer> idList) {
        List<AreaDO> areaDOList = areaMapper.selectBatchIds(idList);
        // 判断查询的资源是否与需删除的资源数目一致
        if(areaDOList.size() < 1 || areaDOList.size() != idList.size()){
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        int effectNum = areaMapper.deleteBatchIds(idList);
        if(effectNum != areaDOList.size()){
            log.error("批量删除头条信息失败,删除数目与入参数目不一致");
            throw new BusinessException(ExceptionCodeEnum.EC10010);
        }
        if(RedisCheckConfig.redisConnected){
            cacheConvert.clearCache(AreaService.AREA_LIST);
        }
        return ResultDataVO.success(null);
    }

    /**
     * 清除区域列表中的缓存
     */
    public void deleteAreaCache(){
        if(redisOperator.keyIsExist(AreaService.AREA_LIST)){
            redisOperator.del(AreaService.AREA_LIST);
        }
    }
}
