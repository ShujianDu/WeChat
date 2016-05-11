package com.yada.wechatbank.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 证件类型转换工具类
 *
 * @author Tx
 */
public class IdTypeUtil {

    private static Map<String, String> map = null;

    static {
        map = new HashMap<>();
        map.put("01", "IDCD");
        map.put("02", "TPID");
        map.put("03", "SSNO");
        map.put("04", "RSBL");
        map.put("05", "OFFR");
        map.put("06", "WJID");
        map.put("08", "DPID");
        map.put("09", "FRPM");
        map.put("10", "BRPA");
        map.put("11", "OTHC");
        map.put("47", "HKID");
        map.put("48", "MCID");
        map.put("49", "TWID");
    }

    /**
     * 将证件号类型转换为gcs系统需要的类型
     *
     * @param param 证件类型
     * @return 英文代码
     */
    public static String numIdTypeTransformToECode(String param) {
        return map.get(param);
    }

}
