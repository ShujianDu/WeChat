package com.yada.wechatbank.client;

import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Echo on 2016/4/27.
 */
public class MyHttpClient extends HttpClient{
    private static final String key = "bizResult";

    private final String getCustMobile = "/getCustMobile.do";


    private Map<String, Object> mockResult() {
        Map<String, Object> param = new HashMap<>();
        param.put("returnCode", "00");
        param.put("returnMsg", "成功");
        return param;
    }

    public MyHttpClient(String hostAddr, int conTimeout, int readTimeout) {
        super(hostAddr, conTimeout, readTimeout);
    }

    public <T> T send(String method, Object object, Class<T> targetClass) {
        T result ;
        Map<String, Object> map = mockResult();
        switch (method){
            case getCustMobile: {
                map.put(key, "13800138000");
                break;
            }
            default:{
                break;
            }
        }
        String   mapJson= JSON.toJSONString(map);
        result = JSON.parseObject(mapJson, targetClass);
        return result;
    }


}
