package com.study.o2o.util;

public class pathUtil {
	//获取文件分隔符
	private static String seperator = System.getProperty("file.separator");
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if(os.toLowerCase().startsWith("win")) {
			basePath = "F:/o2oResources/img";
		}else {
			basePath = "/o2oResources/img";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}
	//	basePath = "F:/o2oResources/img/upload/item/ ? 路径重复";
	public static String getShopImagePath(long shopId) {
		String imagePath = "/upload/item/shop/"+shopId+"/";
		return imagePath.replace("/", seperator);
	}
	public static String getHeadLineImagePath() {
		String headLinePath = "/upload/item/headline/";
		return headLinePath.replace("/", seperator);
	}
	public static String getShopCategoryPath() {
		String shopCategoryPath = "/upload/item/shopcategory/";
		return shopCategoryPath.replace("/", seperator);
	}
}
