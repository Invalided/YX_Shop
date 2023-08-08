package com.o2o.shop.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.o2o.shop.constant.GlobalConstant;
import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author 勿忘初心
 * @since 2023-07-09-18:07
 * 图片存储工具类,合并PathUtil
 */
@Component
@Slf4j
public class ImageStorage {
    /**
     * 指定不同操作系统的基础路径
     */
    private static final String WIN_PATH = "F:/o2oResources/img";
    private static final String LINUX_PATH = "/o2oResources/img";

    /**
     * 通用路径前缀
     */
    private static final String COMMON_PATH = "/upload/item/";

    /**
     * 获取当前系统的文件分隔符
     */
    private static final String SEPARATOR = System.getProperty("file.separator");

    /**
     * 获取当前操作系统的基础绝对路径
     * @return
     */
    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        StringBuffer basePath = new StringBuffer();
        String prefix = "win";
        if(os.toLowerCase().startsWith(prefix)) {
            basePath.append(WIN_PATH);
        }else {
            basePath.append(LINUX_PATH);
        }
        // 拼接对应的业务名称
        return basePath.toString().replace("/", SEPARATOR);
    }

    /**
     *
     * @param multipartFile 图片文件对象
     * @param sourceName 头条headLine,商铺mall,类别shopCategory
     * @param id 商铺id,存储对应商铺的图片
     * @return 相对路径地址
     */
    public String handImage(MultipartFile multipartFile,String sourceName,Integer id){
        // 业务相对位置路径
        StringBuffer path = new StringBuffer(COMMON_PATH + sourceName.toLowerCase());
        // 处理商铺图片存储
        if(GlobalConstant.MALL_IMAGE.equals(sourceName) && id != null){
            path.append("/").append(id);
        }
        // 为文件路径拼接结尾符
        path.append("/");
        // 开始处理图片
        try {
            // 获取图像的原始文件名
            String fileName = multipartFile.getOriginalFilename();
            int lastIndexOf = fileName.lastIndexOf(".");
            //获取文件的后缀名 .jpg
            String suffix = fileName.substring(lastIndexOf);
            // 使用uuid生成一个新的名称
            fileName = IdWorker.getId()+suffix;
            // 图片文件绝对存储文件夹路径
            String realPath = getImgBasePath() + path;
            // 根据分隔符构建路径,如果目标路径不存在,则自动创建
            makeDirPath(realPath.replace("/", SEPARATOR));
            // 创建文件对象,拼接文件名称
            File dest = new File(realPath+fileName);
            log.info("文件路径:{}", dest.getPath());
            log.info("文件名:{}", dest.getName());
            // 将文件对象传输到目标地址
            multipartFile.transferTo(dest);
            // 将文件名和业务相对路径拼接
            path.append(fileName);
        } catch (IOException e) {
            log.error("捕获{}图片处理异常", sourceName, e);
            throw new BusinessException(ExceptionCodeEnum.EC10009);
        }
        // 返回时也需要重新构建路径
        return path.toString().replace("/", SEPARATOR);
    }

    /**
     * 确定目标路径是否存在，不存在则创建
     * @param targetPath 目标路径
     */
    private static void makeDirPath(String targetPath) {
        // 绝对路径
        File dirPath = new File(targetPath);
        // 文件夹不存在则创建
        if(!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 根据路径删除指定的图片
     * @param imagePath
     * @return
     */
    public boolean deleteImageByPath(String imagePath){
        boolean result = false;
        // 拼接图片的绝对路径并创建file对象
        File fileOrPath  = new File(getImgBasePath() + imagePath);
        if(fileOrPath.exists()) {
            //如果是目录递归删除
            if(fileOrPath.isDirectory()) {
                File[] files = fileOrPath.listFiles();
                for(int i = 0;i<files.length;i++) {
                    files[i].delete();
                }
            }
            //最终删除目录
            result = fileOrPath.delete();
        }
        return result;
    }

}
