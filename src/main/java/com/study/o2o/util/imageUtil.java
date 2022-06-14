package com.study.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.study.o2o.dto.imageHolder;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class imageUtil {
	//获取当前的路径位置
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	//设置时间格式
	private static final SimpleDateFormat sDateForamt = new SimpleDateFormat("yyyyMMddHHmmss");
	//随机数对象
	private static final Random r = new Random();
	//日志对象
	private static org.slf4j.Logger logger = LoggerFactory.getLogger(imageUtil.class);
	/**
	 * 将CommonsMutipartFile类转化为File类
	 * @param cFile
	 * @return
	 */
	public static File transferCommonsMutipartFiletoFile(CommonsMultipartFile cFile) {
		File newFile = new File(cFile.getOriginalFilename());
		try {
			cFile.transferTo(newFile);
		} catch (IllegalStateException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return newFile;
	}
	
	
	/**
	 * 处理缩略图,并返回新生成图片的相对路径
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateThumbnail(imageHolder thumbnail, String targetAddr) {
		//生成不重复随机的图片名称
		String realFileName = getRandomFileName();
		//获取文件扩展名
		String extension = getFileExtension(thumbnail.getImageName());
		//生成target目录
		makeDirPath(targetAddr);
		//获取相对路径
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is"+relativeAddr);
		//生成文件路径
		File dest = new File(pathUtil.getImgBasePath()+relativeAddr);
		//创建缩略图
		logger.debug("current complete addrs is"+pathUtil.getImgBasePath()+relativeAddr);
		try {
			Thumbnails.of(thumbnail.getImage()).size(200,200)
			.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
			.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
			
		}
		return relativeAddr; 
	}
	public static String generateNormalImg(imageHolder thumbnail, String targetAddr) {
		// 获取不重复的随机名
		String realFileName = getRandomFileName();
		// 获取文件的扩展名如png,jpg等
		String extension = getFileExtension(thumbnail.getImageName());
		// 如果目标路径不存在，则自动创建
		makeDirPath(targetAddr);
		// 获取文件存储的相对路径(带文件名)
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is :" + relativeAddr);
		// 获取文件要保存到的目标路径
		File dest = new File(pathUtil.getImgBasePath() + relativeAddr);
		logger.debug("current complete addr is :" + pathUtil.getImgBasePath() + relativeAddr);
		// 调用Thumbnails生成带有水印的图片
		try {
			Thumbnails.of(thumbnail.getImage()).size(337, 640)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
					.outputQuality(0.9f).toFile(dest);
		} catch (IOException e) {
			logger.error(e.toString());
			throw new RuntimeException("创建缩图片失败：" + e.toString());
		}
		// 返回图片相对路径地址
		return relativeAddr;
	}
	/**
	 * 生成随机文件名,当前年月日小时分钟秒钟+五位随机数
	 * @param args
	 * @throws IOException
	 */
	public static String getRandomFileName() {
		//获取随机五位数 10000-99999
		int rannum = r.nextInt(89999)+10000;
		String nowTimeString  = sDateForamt.format(new Date());
		return nowTimeString+rannum;
	}
	/**
	 * 获取输入文件流的扩展名
	 * @param args
	 * @throws IOException
	 */
	private static String getFileExtension(String  fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
	/**
	 * 创建目标路径所涉及的的目录,即/home/o2oProject/image/xxx.jpg,
	 * 则home o2oProject image这三个目录需要自动创建
	 * @param args
	 * @throws IOException
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = pathUtil.getImgBasePath()+targetAddr;
		File dirPath = new File(realFileParentPath);
		if(!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}
	public static void main(String[] args) throws IOException {
		
		//使用thumbnails类对图片进行处理
		Thumbnails.of(new File("C:/Users/Administrator/Desktop/计科194A第23组悦享校园/java.jpg")).size(200, 200)
		.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
		.outputQuality(0.8f).toFile("C:/Users/Administrator/Desktop/计科194A第23组悦享校园/javaNew.jpg");
	}
	/**
	 * storePath是文件的路径还是目录的路径
	 * 如果storePath是文件路径则删除文件
	 * 如果storePath是目录路径则删除该目录下的所有文件
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath  = new File(pathUtil.getImgBasePath()+storePath);
		if(fileOrPath.exists()) {
			//如果是目录递归删除
			if(fileOrPath.isDirectory()) {
				File files[] = fileOrPath.listFiles();
				for(int i = 0;i<files.length;i++) {
					files[i].delete();
				}
			}
			//最终删除目录
			fileOrPath.delete();
		}
	}
}
