package com.yada;

public class DesTest {
    public static void main(String[] args) {
        Des test = new Des();
        String oldString = "aaa_5149587707356840_vvv";
        String SPKEY = "WX_BOC_JFDH";
        System.out.println("1。分配的SPKEY为:  " + SPKEY);
        System.out.println("2。的内容为:  " + oldString);
        String reValue = test.encrypt(oldString, SPKEY);
        reValue = reValue.trim().intern();
        System.out.println("进行3-DES加密后的内容: " + reValue);
        String reValue2 = test.decrypt(reValue, SPKEY);
        System.out.println("进行3-DES解密后的内容: " + reValue2);
    }
}
