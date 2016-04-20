package com.yada.wechatbank.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 币种转换类
 * @author Tx
 * 
 */
public class CurrencyUtil {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static String DEFAULT_FILE_NAME = "CurrencyUtil.map";
	private static String DEFAULT_ENCODING = "UTF-8";

	private Map<String, String> codeToChinese = null;
	private String filePath = null;
	private Charset charset = null;

	public CurrencyUtil(String filePath, String encoding) {
		this.filePath = filePath;
		this.charset = Charset.forName(encoding);
		load();
	}

	public CurrencyUtil() {
		//TODO 发布到WAS上有可能改变获取方式
		this.filePath = CurrencyUtil.class.getClassLoader().getResource("").getPath() + DEFAULT_FILE_NAME;
		this.charset = Charset.forName(DEFAULT_ENCODING);
		load();
	}

	private boolean load() {
		try (InputStream inputStream = new FileInputStream(new File(filePath));
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
			HashMap<String, String> tempCodeToChinese = new HashMap<String, String>();
			for (String line = null; (line = reader.readLine()) != null;) {
				if (line.contains("-")) {
					String[] split = line.split("-");
					tempCodeToChinese.put(split[0], split[1]);
				}
			}
			codeToChinese = tempCodeToChinese;
			return true;
		} catch (Exception e) {
			logger.error("", e);
			return false;
		}
	}

	public boolean reload() {
		return load();
	}

	public  String translateChinese(String currencyCode) {
		return codeToChinese.get(currencyCode);
	}




}
