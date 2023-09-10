package com.o2o.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o2o.shop.bo.HeadLineBO;
import com.o2o.shop.config.quartz.RedisCheckConfig;
import com.o2o.shop.constant.GlobalConstant;
import com.o2o.shop.dto.superadmin.HeadLineDTO;
import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import com.o2o.shop.mapper.HeadLineMapper;
import com.o2o.shop.model.HeadLineDO;
import com.o2o.shop.service.HeadLineService;
import com.o2o.shop.util.CacheConvert;
import com.o2o.shop.util.ImageStorage;
import com.o2o.shop.util.PathUtil;
import com.o2o.shop.util.RedisOperator;
import com.o2o.shop.vo.HeadLineVO;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 勿忘初心
 * @since 2023-07-06-16:19
 */
@Service
@Slf4j
public class HeadLineServiceImpl implements HeadLineService {
    
    @Autowired
    private RedisOperator redisOperator;
    @Autowired
    private HeadLineMapper headLineMapper;
    @Autowired
    private CacheConvert cacheConvert;
    @Autowired
    private ImageStorage imageStorage;

    @Override
    public List<HeadLineVO> allHeadLineList() {
        List<HeadLineVO> headLineVOList;
        // 先从Redis中读取数据
        boolean redisCache = false;
        if(RedisCheckConfig.redisConnected){
            redisCache = redisOperator.keyIsExist(HeadLineService.HEAD_LINE);
        }
        // 是否有缓存
        if(redisCache){
            // 取出存放的JSON字符串,并将其转换
            headLineVOList = cacheConvert.readCache(HeadLineService.HEAD_LINE,new ArrayList<HeadLineVO>());
        }else{
            // 拼接QueryWrapper条件,倒序输出
            QueryWrapper<HeadLineDO> headLineWrapper = new QueryWrapper<>();
            headLineWrapper.orderByDesc("priority");
            // 通过stream流的方式将属性拷贝到VO对象中
            headLineVOList = headLineMapper.selectList(headLineWrapper)
                    .stream()
                    .map(headLineDO -> {
                        HeadLineVO headLineVO = new HeadLineVO();
                        BeanUtils.copyProperties(headLineDO,headLineVO);
                        return headLineVO;
                    }).collect(Collectors.toList());
            if(headLineVOList.size() < 1){
                throw new BusinessException(ExceptionCodeEnum.EC10002);
            }
            // 转为JSON格式,先查redis存活状态
            if(RedisCheckConfig.redisConnected){
                cacheConvert.writeCache(HeadLineService.HEAD_LINE,headLineVOList);
            }
        }
        if(headLineVOList == null){
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        return headLineVOList;
    }

    @Override
    public PageVO<HeadLineVO> allHeadLineList(Integer pageNum, Integer pageSize, HeadLineBO headLineCondition) {
        // 验证pageNum和PageSize是否合法
        if(pageNum == null){
            pageNum = 1;
        }
        // 默认5条数据
        if(pageSize == null){
            pageSize = 5;
        }
        // 分页条件查询将不进入缓存
        Page<HeadLineDO> page = new Page<>(pageNum,pageSize);
        Page<HeadLineVO> headLinePage = headLineMapper.queryHeadLinePage(page, headLineCondition);
        List<HeadLineVO> headLineList = headLinePage.getRecords();
        if(headLineList.isEmpty()){
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        return new PageVO(headLineList,headLineList.size());
    }


    @Override
    public HeadLineVO queryHeadLine(Integer id) {
        HeadLineVO headLineVO = new HeadLineVO();
        HeadLineDO headLineDO = headLineMapper.selectById(id);
        if(headLineDO == null){
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        BeanUtils.copyProperties(headLineDO, headLineVO);
        return headLineVO;
    }

    @Override
    public ResultDataVO createHeadLine(HeadLineDTO headLineDTO, MultipartFile headLineImg) {
        // 先取出图片
        if(headLineImg.isEmpty()){
            throw new BusinessException(ExceptionCodeEnum.EC10008);
        }
        // 文件的相对存放路径
        String relativePath = imageStorage.handImage(headLineImg, GlobalConstant.HEADLINE_IMAGE, null);
        // 设置当前文件对象的路径属性
        headLineDTO.setLineImg(relativePath);
        HeadLineDO headLineDO = new HeadLineDO();
        // 拷贝属性，写入数据库
        BeanUtils.copyProperties(headLineDTO,headLineDO);
        headLineMapper.insert(headLineDO);
        // 清除缓存
        if(RedisCheckConfig.redisConnected){
            cacheConvert.clearCache(HeadLineService.HEAD_LINE);
        }
        return ResultDataVO.success(null);
    }

    @Override
    public ResultDataVO deleteHeadLineById(Integer id) {
        // 删除前先查询
        HeadLineDO headLineDO = headLineMapper.selectById(id);
        if(headLineDO == null){
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        headLineMapper.deleteById(id);
        if(RedisCheckConfig.redisConnected){
            cacheConvert.clearCache(HeadLineService.HEAD_LINE);
        }
        return ResultDataVO.success(null);
    }

    @Override
    public ResultDataVO updateHeadLine(HeadLineDTO headLineDTO, MultipartFile headLineImg) {
        // 更新前先查
        HeadLineDO headLineDO = headLineMapper.selectById(headLineDTO.getLineId());
        if(headLineDO == null){
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }

        // 先判断是否有图片信息
        if(headLineImg != null){
            String relativePath = imageStorage.handImage(headLineImg,GlobalConstant.HEADLINE_IMAGE,null);
            headLineDTO.setLineImg(relativePath);
        }
        // 拷贝属性，写入数据库
        BeanUtils.copyProperties(headLineDTO,headLineDO);
        int effectNum = headLineMapper.updateById(headLineDO);
        if(effectNum < 1){
            log.error("更新头条信息操作失败");
            throw new BusinessException(ExceptionCodeEnum.EC10010);
        }
        // 清除缓存
        if(RedisCheckConfig.redisConnected){
            cacheConvert.clearCache(HeadLineService.HEAD_LINE);
        }
        return ResultDataVO.success(null);
    }

    @Override
    public ResultDataVO batchDeleteHeadLine(List<Integer> idList) {
        List<HeadLineDO> headLineList = headLineMapper.selectBatchIds(idList);
        // 判断查询的资源是否与需删除的资源数目一致
        if(headLineList.size() < 1 || headLineList.size() != idList.size()){
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        headLineMapper.deleteBatchIds(idList);
        if(RedisCheckConfig.redisConnected){
            cacheConvert.clearCache(HeadLineService.HEAD_LINE);
        }
        return ResultDataVO.success(null);
    }


    /**
     * 创建文件目录
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        // 绝对路径
        String realFileParentPath = PathUtil.getImgBasePath()+targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

}
