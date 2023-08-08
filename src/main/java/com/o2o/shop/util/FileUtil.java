package com.o2o.shop.util;


import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文件转换工具
 * @author xxx
 * @Date: 2023-06-30 2:50
 * 23.06.30 修复文件扩展名获取错误而导致的异常
 */
@Component
public class FileUtil {
	/**
	 * 根据url拿取file
	 * 
	 * @param url    文件地址
	 * @param suffix 文件后缀名
	 */
	public static File createFileByUrl(String url, String suffix) {
		byte[] byteFile = getImageFromNetByUrl(url);
		if (byteFile != null) {
			File file = getFileFromBytes(byteFile, suffix);
			return file;
		} else {
			return null;
		}
	}

	/**
	 * 根据地址获得数据的字节流
	 * 
	 * @param strUrl 网络连接地址
	 * @return
	 */
	private static byte[] getImageFromNetByUrl(String strUrl) {
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			// 通过输入流获取图片数据
			InputStream inStream = conn.getInputStream();
			// 得到图片的二进制数据
			byte[] btImg = readInputStream(inStream);
			return btImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从输入流中获取数据
	 * 
	 * @param inStream 输入流
	 *
	 * @return
	 * @throws Exception
	 */
	private static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	/**
	 * 创建临时工具
	 * @param b
	 * @param suffix
	 * @return
	 */
	private static File getFileFromBytes(byte[] b, String suffix) {
		BufferedOutputStream stream = null;
		File file = null;
		try {
			file = File.createTempFile("pattern", "." + suffix);
			System.out.println("临时文件位置：" + file.getCanonicalPath());
			FileOutputStream fstream = new FileOutputStream(file);
			stream = new BufferedOutputStream(fstream);
			stream.write(b);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return file;
	}

	public static MultipartFile createImg(String url) {
		try {
			// File转换成MultipartFile
			File file = FileUtil.createFileByUrl(url, "jpg");
			FileInputStream inputStream = new FileInputStream(file);
			MultipartFile multipartFile = new MockMultipartFile(file.getName(), inputStream);
			return multipartFile;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将File类型转换为MultipartFile
	 * @param filePath	本地文件路径
	 * @return
	 */
	public static MultipartFile fileToMultipart(String filePath) {
		try {

			// 创建文件对象
			File file = new File(filePath);
			// 将其转为文件输入流
			FileInputStream fileInputStream = new FileInputStream(file);
			// 将文件流转换为MultipartFile类型
			MultipartFile multipartFile = new MockMultipartFile("file", file.getName(),
					"text/plain", fileInputStream);

			return multipartFile;


		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * base64转是否可以转为文件
	 * @param filePath
	 * @param base64Data
	 * @return
	 * @throws Exception
	 */
	public static boolean base64ToFile(String filePath, String base64Data)  throws Exception {
		String dataPrfix = "";
        String data = "";
        
        if(base64Data == null || "".equals(base64Data)){
            return false;
        }else{
            String [] d = base64Data.split("base64,");
            if(d != null && d.length == 2){
                dataPrfix = d[0];
                data = d[1];
            }else{
                return false;
            }
        }

        // 因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
        byte[] bs = Base64Utils.decodeFromString(data);
        // 使用apache提供的工具类操作流
        org.apache.commons.io.FileUtils.writeByteArrayToFile(new File(filePath), bs);
        
        return true;
	}
	public static void main(String[] args) {
	}
}
