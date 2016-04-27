package com.yada.wechatbank.client;

import com.alibaba.fastjson.JSON;
import com.yada.wechatbank.model.BillSendType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Echo on 2016/4/27.
 */
public class MyHttpClient extends HttpClient {

    //公用手机号
    private final String pubilcMobileNo="18888888888";

    private static final String key = "bizResult";
    private final String getCustMobile = "/getCustMobile.do";
    private final String getBillSendType = "/getBillSendType.do";
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
        Map<String, Object> map = mockResult();
        switch (method){
            case getCustMobile:   {  getCustMobile(map);    break; }//查询客户手机号
            case getBillSendType: {  getBillSendType(map);  break; }//账单寄送方式查询
            case getMobilePhone:  {  getMobilePhone(map);   break; }//查询客户预约办卡手机号
            default:{ break; }
        }
        String   mapJson= JSON.toJSONString(map);
        T result = JSON.parseObject(mapJson, targetClass);
        return result;
    }

    // 获取预约办卡手机号
    private void getMobilePhone(Map<String, Object> map) {
        map.put(key, "18888888888");
    }


    //账单寄送方式
    private void getBillSendType(Map<String, Object> map) {
        BillSendType b = new BillSendType();
        b.setBillSendType("C");
        b.setBillSendTypeDesc("测试");
        map.put(key, b);
    }

    // 获取客户手机号
    private void getCustMobile(Map<String, Object> map) {
        map.put(key, pubilcMobileNo);
    }

}
