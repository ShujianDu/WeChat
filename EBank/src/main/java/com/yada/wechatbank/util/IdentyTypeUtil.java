package com.yada.wechatbank.util;

/**
 * 证件类型转换工具类
 * 
 * @author liangtieluan
 *
 */
public class IdentyTypeUtil {
	/**
	 * 直销系统证件类型转换为Gcs系统证件类型
	 * 
	 * @param idType
	 * @return
	 */
	public static String idTypeZxToGcs(String idType) {
		String gcsIdType = "";
		switch (idType) {
		case "1":
			// 身份证
			gcsIdType = "IDCD";
			break;
		case "2":
			// 护照
			gcsIdType = "SSNO";
			break;
		case "3":
			// 军人身份证
			gcsIdType = "OFFR";
			break;
		case "4":
			// 香港居民来往内地通行证
			gcsIdType = "HKID";
			break;
		case "5":
			// 台湾居民往来大陆通行证
			gcsIdType = "TWID";
			break;
		case "I":
			// 武装警察身份证
			gcsIdType = "WJID";
			break;
		case "J":
			// 澳门居民来往内地通行证
			gcsIdType = "MCID";
			break;
		case "8":
			// 其它
			gcsIdType = "OTHC";
			break;
		default:
			break;
		}
		return gcsIdType;
	}
}
