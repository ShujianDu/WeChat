package com.yada.wechatbank.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 账单寄送方式描述转换
 *
 * @author Tx
 */
public class BillSendTypeUtil {

    private static Map<String, String> map = null;

    static {
        map = new HashMap<>();
        map.put("01", "IDCD");
        map.put("0","纸质对账单");
        map.put("4","不发送对账单");
        map.put("5","不发送对账单");
        map.put("6","不发送对账单");
        map.put("7","只寄送明细对账单");
        map.put("8","纸质对账单、手机对账单、电子对账单");
        map.put("9","纸质对账单、手机对账单");
        map.put("A","不发送对账单");
        map.put("B","不发送对账单");
        map.put("C","手机对账单");
        map.put("D","手机对账单、电子对账单");
        map.put("E","电子对账单");
        map.put("F","电子对账单");
        map.put("G","电子对账单、手机对账单");
        map.put("H","不发送对账单");
        map.put("I","手机对账单");
        map.put("J","手机对账单");
        map.put("K","电子对账单");
        map.put("L","手机对账单、电子对账单");
        map.put("M","手机对账单、电子对账单");
        map.put("N","手机对账单");
        map.put("O","纸质对账单");
        map.put("P","纸质对账单");
        map.put("Q","纸质对账单、手机对账单");
        map.put("R","纸质对账单、手机对账单、电子对账单");
        map.put("S","纸质对账单、电子对账单");
        map.put("T","纸质对账单、电子对账单");
        map.put("U","纸质对账单、手机对账单、电子对账单");
        map.put("V","纸质对账单");
        map.put("3","不发送对账单");
        map.put("W","纸质对账单、手机对账单");
        map.put("X","纸质对账单、手机对账单");
        map.put("Y","纸质对账单、电子对账单");
        map.put("Z","纸质对账单、手机对账单、电子对账单");
        map.put("1","纸质对账单、电子对账单");
        map.put("2","电子对账单");
    }

    /**
     * 将证件号类型转换为gcs系统需要的类型
     *
     * @param param   账单寄送方式代码
     * @return        中文描述
     */
    public static String billSenTypeTransformToDes(String param) {return map.get(param); }

}
