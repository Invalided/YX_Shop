package com.study.o2o.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class codeUtil {
	/**
	 * 检查验证码是否符合预期
	 * 
	 * @param request
	 * @return
	 */
	public static boolean checkVerifyCode(HttpServletRequest request) {
		String verifyCodeExpected = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		String verifyCodeActual = httpServletRequestUtil.getString(request, "verifyCodeActual");
		if(verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected)) {
			return false;
		} 
		return true;
	}
	/**
	 * 生成二维码
	 * @param content
	 * @param resp
	 * @return
	 */
	
	public static BitMatrix generateQRCodeStream(String content,HttpServletResponse resp) {
		//给响应添加头部信息，主要是告诉浏览器返回的是图片流
		resp.setHeader("Cache-Control","no-store");
		//对图片不要进行缓存 图片有过期时间
		resp.setHeader("Pragma", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/png");
		//设置吐图片的文字编码以及内边框距
		Map<EncodeHintType,Object> hint = new HashMap<EncodeHintType, Object>();
		hint.put(EncodeHintType.CHARACTER_SET,"UTF-8");
		//设置边距
		hint.put(EncodeHintType.MARGIN, 0);
		BitMatrix bitMatrix;
		try {
			//参数顺序分别为:编码内容,编码类型,生成图片宽度,生成图片高度,设置参数
			bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 300, 300);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return bitMatrix;
	}
	
}
