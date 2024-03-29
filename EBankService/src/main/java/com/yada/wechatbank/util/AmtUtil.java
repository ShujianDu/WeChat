package com.yada.wechatbank.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AmtUtil {

	private final static BigDecimal DIVISOR = new BigDecimal(1000);
	private final static int SCALE = 2;
	private final static int SCALEBYPOWEROFTEN = 3;
	private final static String STRINGFORMAT = "%+018d";
	
	// 处理金额
	public static String procString(String target) {
		if(target == null || "".equals(target)) {
			return target;
		}
		BigDecimal bigDecimal = new BigDecimal(target);
		return bigDecimal.divide(DIVISOR, SCALE, BigDecimal.ROUND_DOWN).toString();
	}
	
	// 处理小数点后保留两位的金额字符串为带正负号共18位数字串    例：-1234.01 To -00000000001234010
	public static String procMoneyToString(String money) {
		if(money == null || "".equals(money)) {
			return money;
		}
		BigDecimal bigDecimal = new BigDecimal(money);
		return String.format(STRINGFORMAT, bigDecimal.scaleByPowerOfTen(SCALEBYPOWEROFTEN).intValue());
	}
}
