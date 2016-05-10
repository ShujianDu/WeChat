package com.yada;

/**
 * 为了兼容积分
 */
public class CardNoDecrypt {

    private Des des = new Des();

    /**
     * 加密
     *
     * @param src   要进行加密的String
     * @param spkey 密钥字符串
     * @return String 3-DES加密后的String
     */
    String pack(String src, String spkey) {
        return des.encrypt(src, spkey);
    }

    /**
     * 解密
     *
     * @param src   要进行解密的String
     * @param spkey 密钥字符串
     * @return String 解密后的支付串
     */
    String unpack(String src, String spkey) {
        return des.decrypt(src, spkey);
    }
}
