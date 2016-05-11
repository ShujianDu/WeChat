package com.yada;


/**
 * 积分加密/解密
 */
public class PointDes {
//    private char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private Des des = new Des();
//    private Random random = new Random();

    public String encrypt(String data, String key) {
//        char[] cs = new char[6];
//        for (int i = 0; i < 6; i++) {
//            cs[i] = chars[random.nextInt(26)];
//        }
        return des.encrypt(String.format("aaa_%s_vvv", data), key);
    }

    public String decrypt(String data, String key) {
        String d = des.decrypt(data, key);
        return d.substring(4, d.length() - 4);
    }
}
