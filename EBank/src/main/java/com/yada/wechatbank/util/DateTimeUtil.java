package com.yada.wechatbank.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期帮助类
 * @author echo
 *
 */
public class DateTimeUtil {

	/** 
	 * 定义时间类型常量 
	 */  
	private final static int SECOND=1;  
	private final static int MINUTE=2;  
	private final static int HOUR=3;  
	private final static int DAY=4; 
	
	private Date now;  
	public Date getNow() {  
	    return now;  
	}  
	public void setNow(Date now) {  
	    this.now = now;  
	}  
	/** 
	 * 构造方法，初始化now时间 
	 */  
	public DateTimeUtil(){  
	    now=new Date();  
	}  
	
	/** 
	 * 将一个表示时间段的数转换为毫秒数 
	 * @param num 时间差数值,支持小数 
	 * @param type 时间类型：1->秒,2->分钟,3->小时,4->天 
	 * @return long类型时间差毫秒数，当为-1时表示参数有错 
	 */  
	public long formatToTimeMillis(double num, int type) {  
	    if (num <= 0)  
	        return 0;  
	    switch (type) {  
	    case SECOND:  
	        return (long) (num * 1000);  
	    case MINUTE:  
	        return (long) (num * 60 * 1000);  
	    case HOUR:  
	        return (long) (num * 60 * 60 * 1000);  
	    case DAY:  
	        return (long) (num * 24 * 60 * 60  * 1000);  
	    default:  
	        return -1;  
	    }  
	}
	
	/** 
	 * 获取某一指定时间的前一段时间 
	 * @param num 时间差数值 
	 * @param type 时间差类型：1->秒,2->分钟,3->小时,4->天 
	 * @return 返回Date对象
	 */  
	public Date getPreTime(double num,int type){  
		Date date = getNow();
	    long nowLong=date.getTime();//将参考日期转换为毫秒时间  
	    Date time = new Date(nowLong-formatToTimeMillis(num, type));//减去时间差毫秒数  
	    return time;  
	} 
	
	
	public Date parseDateTime(String str) {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");//小写的mm表示的是分钟  
		try {
			Date date=sdf.parse(str);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;  
	}
	
	/**
	 * 当前日期格式转换器
	 */
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");
	/**
	 * 获取当前日期
	 * 
	 * @return
	 */
	public static String getDate() {
		return dateFormat.format(Calendar.getInstance().getTime());
	}
	/**
	 * 获取几天后的日期
	 * @param count 天数
	 * @return
	 */
	public static String getAfterDate(int count){
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, count);
		return dateFormat.format(calendar.getTime());
	}

}
