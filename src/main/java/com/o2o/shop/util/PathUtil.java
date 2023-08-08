package com.o2o.shop.util;

/**
 * 图片文件路径工具
 */
public class PathUtil {
	/**
	 * 获取当前系统的文件分隔符
	 */
	private static String seperator = System.getProperty("file.separator");
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		String prefix = "win";
		if(os.toLowerCase().startsWith(prefix)) {
			basePath = "F:/o2oResources/img";
		}else {
			basePath = "/o2oResources/img";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}
	//	basePath = "F:/o2oResources/img/upload/item/ ? 路径重复";
	public static String getShopImagePath() {
		//String imagePath = "/upload/item/mall/"+shopId+"/";
		// 自行拼接shopId;
		String imagePath = "/upload/item/mall/";
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

	public static String getImageStoragePath(String typeName){
		String imgPath = null;
		switch (typeName){
			case "HeadLine":
				imgPath = getHeadLineImagePath();
				break;
			case "ShopCategory":
				imgPath = getShopCategoryPath();
				break;
			default:
				imgPath = getShopImagePath();
		}
		return imgPath.replace("/", seperator);
	}
}
