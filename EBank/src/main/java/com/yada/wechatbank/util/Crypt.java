package com.yada.wechatbank.util;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import Decoder.BASE64Decoder;



public class Crypt {
    private static final byte[] key = "openIdAndTimeKey".getBytes();

    private static String Algorithm = "Blowfish"; // 定义 加密算法,可用
    // DES,DESede,Blowfish

    static boolean debug = false;

//    static {
//        Security.addProvider(new com.sun.crypto.provider.SunJCE());
//    }

    // 加密
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

    // 解密
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

    // 字节码转换成16进制字符串
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    // 将16进制字符串转换成字节码
    public static byte[] hex2byte(String hex) {
        byte[] bts = new byte[hex.length() / 2];
        for (int i = 0; i < bts.length; i++) {
            bts[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2),
                    16);
        }
        return bts;
    }

    public static void main(String[] args) throws Exception {
        debug = false;
        String testStr = "abcdefg;20141219104020";
        long s = System.currentTimeMillis();
        System.out.println(encode(testStr));
        System.out.println(decode(encode(testStr)));
        System.out.println(encode(testStr));
        System.out.println(decode(encode(testStr)));
        System.out.println(encode(testStr));
        System.out.println(decode(encode(testStr)));
        System.out.println(encode(testStr));
        System.out.println(decode(encode(testStr)));
        System.out.println(System.currentTimeMillis() - s);
    }

    public static String getFromBASE64(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } 
        catch (Exception e) {
            return null;
        }
    }
    /**
     * 将传到页面的卡列表加密
     * @param cardList
     * @return
     * @throws Exception 
     */
    public static List<String> cardNoCrypt(List<String> cardList) throws Exception{
    	for(int i=0;i<cardList.size();i++){
			String cardNo = cardList.get(i);
			//页面显示的省略卡号
			String cardNoShow = cardNo.substring(0, 4)+"********"+cardNo.substring(cardNo.length()-4,cardNo.length());
				//加密后的卡号
				String cardNoCrypt = Crypt.encode(cardNo);
				//传递到页面的卡号及省略卡号的拼接字符串
				String cardNoPage = cardNoShow+","+cardNoCrypt;
				cardList.remove(i);
				cardList.add(i, cardNoPage);
		}
    	return cardList;
    }
    /**
     * 将加密的卡号集合解密
     * @param cardList
     * @return
     * @throws Exception 
     */
    public static List<String> cardNoDecode(List<String> cardList) throws Exception{
    	for(int i=0;i<cardList.size();i++){
				String cardNo = decode(cardList.get(i).substring(17));
				cardList.remove(i);
				cardList.add(i, cardNo);
    	}
    	return cardList;
    }
    /**
     * 将一张卡号加密
     * @param cardNo
     * @return
     * @throws Exception
     */
    public static String cardNoOneEncode(String cardNo) throws Exception{
    	return cardNo.substring(0,4)+"********"+cardNo.substring(cardNo.length()-4,cardNo.length())+","+encode(cardNo);
    }

}