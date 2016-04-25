package com.yada.wechatbank.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 币种转换工具类
 * @author Tx
 */
public class CurrencyUtil {

	private static Map<String, String> map = null;

	static {
		map=new HashMap<>();
		map.put("AED","迪尔汗");
		map.put("AFN","阿富汗尼");
		map.put("ALL","阿尔巴尼亚列克");
		map.put("AMD","亚美尼亚德拉姆");
		map.put("ANG","安的列斯盾");
		map.put("AOA","安哥拉宽扎");
		map.put("ARS","阿根廷比索");
		map.put("AUD","澳大利亚元");
		map.put("AWG","阿鲁巴弗罗林");
		map.put("AZM","阿塞拜疆马纳特");
		map.put("BAM","马克");
		map.put("BBD","巴巴多斯元");
		map.put("BDT","孟加拉塔卡");
		map.put("BGN","保加利亚列弗");
		map.put("BHD","巴林第纳尔");
		map.put("BIF","布隆迪法郎");
		map.put("BMD","百慕大元");
		map.put("BND","文莱元");
		map.put("BOB","玻利维亚诺");
		map.put("BOV","玻利维亚mvdol");
		map.put("BRL","巴西雷亚尔");
		map.put("BSD","巴哈马元");
		map.put("BTN","不丹努扎姆");
		map.put("BWP","博茨瓦纳普拉");
		map.put("BYR","白俄罗斯卢布");
		map.put("BZD","伯利兹元");
		map.put("CAD","加拿大元");
		map.put("CDF","刚果法郎");
		map.put("CHF","瑞士法郎");
		map.put("CLF","智利比索");
		map.put("CLP","智利比索");
		map.put("CNY","人民币");
		map.put("COP","哥伦比亚比索");
		map.put("COU","哥伦比亚");
		map.put("CRC","哥斯达黎加科朗");
		map.put("CSD","塞尔维亚第纳尔");
		map.put("CUP","古巴比索");
		map.put("CVE","佛得角埃斯库多");
		map.put("CYP","塞浦路斯镑");
		map.put("CZK","捷克克朗");
		map.put("DJF","吉布提法郎");
		map.put("DKK","丹麦克朗");
		map.put("DOP","多米尼加比索");
		map.put("DZD","阿尔及利亚第纳尔");
		map.put("EEK","爱沙尼亚克朗");
		map.put("EGP","埃及镑");
		map.put("ERN","厄立特里亚纳克法");
		map.put("ETB","埃塞俄比亚比尔");
		map.put("EUR","欧元");
		map.put("FJD","斐济元");
		map.put("FKP","福克兰镑");
		map.put("GBP","英镑");
		map.put("GEL","格鲁吉亚拉里");
		map.put("GHC","加纳塞地");
		map.put("GIP","直布罗陀镑");
		map.put("GMD","冈比亚达拉西");
		map.put("GNF","几内亚法郎");
		map.put("GTQ","危地马拉格查尔");
		map.put("GYD","圭亚那元");
		map.put("JPY","日元");
		map.put("HKD","港币");
		map.put("HNL","洪都拉斯伦皮拉");
		map.put("HRK","克罗地亚库纳");
		map.put("HTG","海地古德");
		map.put("HUF","匈牙利-福林");
		map.put("CNS","12873");
		map.put("IDR","印尼盾");
		map.put("ILS","坚戈");
		map.put("LAK","老挝基普");
		map.put("LBP","黎巴嫩镑");
		map.put("LKR","斯里兰卡卢比");
		map.put("LRD","利比里亚元");
		map.put("LSL","莱索托洛蒂");
		map.put("LTL","立陶宛立特");
		map.put("LVL","拉脱维亚拉特");
		map.put("LYD","利比亚第纳尔");
		map.put("MAD","摩洛哥迪拉姆");
		map.put("MDL","摩尔多瓦列伊");
		map.put("MGA","阿里亚里");
		map.put("MKD","前南马其顿代纳尔");
		map.put("MMK","缅甸元");
		map.put("MNT","蒙古图格里克");
		map.put("MOP","澳门元");
		map.put("MRO","毛里塔尼亚乌吉亚");
		map.put("MTL","马耳他里拉");
		map.put("MUR","毛里求斯卢比");
		map.put("MVR","马尔代夫拉菲亚");
		map.put("MWK","马拉维克瓦查");
		map.put("MXN","墨西哥比索");
		map.put("MYR","马来西亚林吉特");
		map.put("MZM","莫桑比克梅蒂卡尔");
		map.put("NAD","纳米比亚元");
		map.put("NGN","尼日利亚奈拉");
		map.put("NIO","尼加拉瓜科多巴");
		map.put("NOK","挪威克朗");
		map.put("NPR","尼泊尔卢比");
		map.put("NZD","新西兰元");
		map.put("OMR","阿曼里亚尔");
		map.put("PAB","巴拿马巴波亚");
		map.put("PEN","秘鲁新索尔");
		map.put("PGK","巴布亚新几内亚基那");
		map.put("PHP","菲律宾比索");
		map.put("PKR","巴基斯坦卢比");
		map.put("PLN","波兰兹罗提");
		map.put("PYG","巴拉圭瓜拉尼");
		map.put("QAR","卡塔尔里亚尔");
		map.put("ROL","罗马尼亚列伊");
		map.put("RUB","俄罗斯卢布");
		map.put("RWF","卢旺达法郎");
		map.put("SAR","沙特里亚尔");
		map.put("SBD","所罗门群岛元");
		map.put("SCR","塞舌尔卢比");
		map.put("SDD","苏丹第纳尔");
		map.put("SEK","瑞典克朗");
		map.put("SGD","新加坡元");
		map.put("SHP","圣赫勒拿镑");
		map.put("SIT","斯洛文尼亚托拉尔");
		map.put("SKK","斯洛伐克克朗");
		map.put("SLL","塞拉利昂利昂");
		map.put("SOS","索马里先令");
		map.put("SRD","苏里南元");
		map.put("STD","圣多美和普林西比多布拉");
		map.put("SVC","萨尔瓦多科朗");
		map.put("SYP","叙利亚镑");
		map.put("SZL","斯威士兰里兰吉尼");
		map.put("THB","泰铢");
		map.put("TJS","塔吉克斯坦索莫尼");
		map.put("TMM","土库曼斯坦马纳特");
		map.put("TND","突尼斯第纳尔");
		map.put("TOP","汤加潘加");
		map.put("TRY","土耳其里拉");
		map.put("TTD","特立尼达和多巴哥元");
		map.put("TWD","新台币元");
		map.put("TZS","坦桑尼亚先令");
		map.put("UAH","乌克兰格里夫尼亚");
		map.put("UGX","乌干达先令");
		map.put("USD","美元");
		map.put("UYU","乌拉圭比索");
		map.put("UZS","乌兹别克斯坦苏姆");
		map.put("VEB","委内瑞拉博利瓦");
		map.put("VND","越南盾");
		map.put("VUV","瓦努阿图瓦图");
		map.put("WST","萨摩亚塔拉");
		map.put("XAF","中非法郎");
		map.put("XCD","东加勒比元");
		map.put("SDR","特别提款权");
		map.put("XFO","金法郎");
		map.put("XFU","UIC-法郎");
		map.put("XOF","西非法郎");
		map.put("XPD","钯");
		map.put("XPF","太平洋法郎");
		map.put("XPT","铂");
		map.put("XTS","为测试保留的代码");
		map.put("XXX","没有货币的交换");
		map.put("YER","也门里亚尔");
		map.put("ZAR","南非兰特");
		map.put("ZMK","赞比亚克瓦查");
		map.put("ZWD","津巴布韦元");
		map.put("KZT","坚戈");
	}

	public  static String translateChinese(String currencyCode) {
		return map.get(currencyCode);
	}




}
