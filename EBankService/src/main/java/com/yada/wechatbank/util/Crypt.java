package com.yada.wechatbank.util;

import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 * 页面展示时需要将卡号加密
 * 
 * @author liangtieluan
 *
 */

public class Crypt {

	private static final byte[] key = "MyKey".getBytes();

	private static String Algorithm = "Blowfish"; // 定义 加密算法,可用

	/**
	 * 加密
	 * 
	 * @param input
	 *            加密数据
	 * @return 加密后的数据
	 * @throws Exception
	 *             异常
	 */
	public static String encode(String input) throws Exception {
		if (input == null)
			return null;
		byte[] bytes = input.getBytes();
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] cipherByte = c1.doFinal(bytes);
		return byte2hex(cipherByte);
	}

	/**
	 * 解密
	 * 
	 * @param input
	 *            需要解密的数据
	 * @return 解密后的数据
	 * @throws Exception
	 *             异常
	 */
	public static String decode(String input) throws Exception {
		if (input == null)
			return null;
		byte[] bytes = hex2byte(input);
		SecretKey deskey = new javax.crypto.spec.SecretKeySpec(key, Algorithm);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.DECRYPT_MODE, deskey);
		byte[] clearByte = c1.doFinal(bytes);
		return new String(clearByte);
	}

	/**
	 * 字节码转换成16进制字符串
	 * 
	 * @param b
	 *            转换数据
	 * @return 转换结果
	 */
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp;
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	/**
	 * 将16进制字符串转换成字节码
	 * 
	 * @param hex
	 *            转换数据
	 * @return 转换结果
	 */
	public static byte[] hex2byte(String hex) {
		byte[] bts = new byte[hex.length() / 2];
		for (int i = 0; i < bts.length; i++) {
			bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bts;
	}

	/**
	 * 将传到页面的卡列表加密
	 * 
	 * @param cardList
	 *            需要加密的卡列表集合
	 * @return 加密好的卡列表
	 * @throws Exception
	 */
	public static List<String> cardNoCrypt(List<String> cardList) throws Exception {
		for (int i = 0; i < cardList.size(); i++) {
			String cardNo = cardList.get(i);
			// 页面显示的省略卡号
			String cardNoShow = cardNo.substring(0, 4) + "********" + cardNo.substring(cardNo.length() - 4, cardNo.length());
			// 加密后的卡号
			String cardNoCrypt = Crypt.encode(cardNo);
			// 传递到页面的卡号及省略卡号的拼接字符串
			String cardNoPage = cardNoShow + "," + cardNoCrypt;
			cardList.remove(i);
			cardList.add(i, cardNoPage);
		}
		return cardList;
	}

	/**
	 * 将加密的卡号集合解密
	 * 
	 * @param cardList
	 *            需要解密的卡列表集合
	 * @return 解密的卡列表集合
	 * @throws Exception
	 */
	public static List<String> cardNoDecode(List<String> cardList) throws Exception {
		for (int i = 0; i < cardList.size(); i++) {
			String cardNo = decode(cardList.get(i).substring(17));
			cardList.remove(i);
			cardList.add(i, cardNo);
		}
		return cardList;
	}

	/**
	 * 将一张卡号加密
	 * 
	 * @param cardNo
	 *            需要解密的卡号
	 * @return 加密的卡号
	 * @throws Exception
	 */
	public static String cardNoOneEncode(String cardNo) throws Exception {
		return cardNo.substring(0, 4) + "********" + cardNo.substring(cardNo.length() - 4, cardNo.length()) + "," + encode(cardNo);
	}

}