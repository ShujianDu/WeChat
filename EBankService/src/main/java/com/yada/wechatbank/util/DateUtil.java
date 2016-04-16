package com.yada.wechatbank.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理工具类
 * 
 * @author liangtieluan
 *
 */
public class DateUtil {
	/**
	 * 
	 * @param dateStr
	 *            日期原始字符串
	 * @param curPattern
	 *            日期对象格式
	 * @param targetPattern
	 *            日期转换后字符串格式
	 * @return 指定格式的字符串
	 */
	public static String parseDate(String dateStr, String curPattern, String targetPattern) {
		if (dateStr == null || dateStr.equals("")) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(curPattern);
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new SimpleDateFormat(targetPattern).format(date);
	}
}
