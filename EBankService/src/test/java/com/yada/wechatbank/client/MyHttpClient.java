package com.yada.wechatbank.client;

import com.alibaba.fastjson.JSON;
import com.yada.wechatbank.model.BillSendType;

import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Echo on 2016/4/27.
 */
public class MyHttpClient extends HttpClient {
    private static final String key = "bizResult";

    private final String getCustMobile = "/getCustMobile.do";
    private final String getBillSendType = "/getBillSendType.do";
    private final String pubilcMobileNo="13800138000";
    private final String getMobilePhone = "/getMobilePhone.do"; // 获取预约办卡手机号


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
                map.put(key, pubilcMobileNo);
                break;
            }
            case getBillSendType: {
                getBillSendType(map);
                break;
            }
            default:{
                break;
            }
        }
        String   mapJson= JSON.toJSONString(map);
        result = JSON.parseObject(mapJson, targetClass);
        return null;
    }

    // 获取预约办卡手机号
    private <T> T getMobilePhone(Class<T> targetClass) {
        Map<String, Object> map = mockResult();
        map.put(key, "18888888888");
        String mapJson = JSON.toJSONString(map);
        T result = JSON.parseObject(mapJson, targetClass);
        return result;
    }


    //账单寄送方式
    public void getBillSendType(Map<String, Object> map) {
        BillSendType b = new BillSendType();
        b.setBillSendType("C");
        b.setBillSendTypeDesc("测试");
        map.put(key, b);
    }

    // 获取客户手机号
    private <T> T getCustMobile(Class<T> targetClass) {
        Map<String, Object> map = mockResult();
        map.put(key, mobileNo);
        String mapJson = JSON.toJSONString(map);
        T result = JSON.parseObject(mapJson, targetClass);
        return result;
    }

}
