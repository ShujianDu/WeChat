package com.yada.wechatbank.client;

import com.alibaba.fastjson.JSON;
import com.yada.wechatbank.model.Balance;
import com.yada.wechatbank.model.BillSendType;
import com.yada.wechatbank.model.CardInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Echo on 2016/4/27.
 */
public class MyHttpClient extends HttpClient {

    private static final String key = "bizResult";
    private final String getCustMobile = "/getCustMobile.do";
    private final String getBillSendType = "/getBillSendType.do";
    private final String getMobilePhone = "/getMobilePhone.do"; // 获取预约办卡手机号
    private final String getCardInfos = "/getCardInfos.do"; // 获取客户卡列表
    private final String sendSMS = "/sendSMS.do"; // 发送短信验证码
    private final String getCardBalance = "/getCardBalance.do";//查询额度
    private final String getWbicCards = "/getWbicCards.do";//海淘卡查询
    private final String wbicCardInfoSendSms = "/wbicCardInfoSendSms.do";//海淘卡用户发送短信


    protected final String pubilcMobileNo="18888888888";//手机号
    protected final String idType="03";//证件类型-护照
    protected final String idNo="MOCK01";//证件号

    //测试一卡数据
    protected final String cardNo1="6225888899990001"; //测试卡1
    protected final String cardNo1_Currency1="CNY"; //人民币
    protected final String cardNo1_Currency2="USD"; //美元

    //测试二卡数据
    protected final String cardNo2="6225888899990002"; //测试卡2
    protected final String cardNo2_Currency1="CNY"; //人民币
    protected final String cardNo2_Currency2="JPY"; //日元

    //测试三卡数据
    protected final String cardNo3="6225888899990003"; //测试卡3
    protected final String cardNo3_Currency1="CNY"; //人民币
    protected final String cardNo3_Currency2="HKD"; //法郎

    //测试四卡数据
    protected final String cardNo4="6225888899990004"; //测试卡4
    protected final String cardNo4_Currency1="CNY"; //人民币

    //卡列表
    public List<String> getCardNoList() {
        List<String> list=new ArrayList<>();
        list.add(cardNo1);
        list.add(cardNo2);
        list.add(cardNo3);
        list.add(cardNo4);
        return list;
    }

    private Map<String, Object> mockResult() {
        Map<String, Object> param = new HashMap<>();
        param.put("returnCode", "00");
        param.put("returnMsg", "成功");
        return param;
    }

    public MyHttpClient(String hostAddr, int conTimeout, int readTimeout) {
        super(hostAddr, conTimeout, readTimeout);
    }

    @Override
    public <T> T send(String method, Object object, Class<T> targetClass) {
        Map<String, Object> map = mockResult();
        Map<String,String> requestMap=(HashMap<String,String>)object;
        switch (method){
            case getCustMobile:   {  getCustMobile(map);    break; }//查询客户手机号
            case getBillSendType: {  getBillSendType(requestMap,map);  break; }//账单寄送方式查询
            case getMobilePhone:  {  getMobilePhone(map);   break; }//查询客户预约办卡手机号
            case getCardInfos:  {  getCardInfos(requestMap,map); break; }//查询客户预约办卡手机号
            case sendSMS:  {  sendSMS(map); break; } //发送短信验证码
            case getCardBalance:  {  getCardBalance(map, object);   break; }//根据卡号查询额度
            case getWbicCards:  {  getWbicCards(map, object);   break; }//查询海淘卡
            case wbicCardInfoSendSms:  {  wbicCardInfoSendSms(map, object);   break; }//给海淘卡用户发送短信
            default:{ break; }
        }
        String mapJson = JSON.toJSONString(map);
        T result = JSON.parseObject(mapJson, targetClass);
        return result;
    }

    // 获取预约办卡手机号
    private void getMobilePhone(Map<String, Object> map) {
        map.put(key, "18888888888");
    }

    // 发送短信验证码
    private void sendSMS(Map<String, Object> map) {
        map.put(key, "true");
    }

    //账单寄送方式
    private void getBillSendType(Map<String,String> reqeustMap,Map<String, Object> responseMap) {
      if(getCardNoList().contains(reqeustMap.get("cardNo"))){responseMap.put(key,null); }
        BillSendType b = new BillSendType();
        b.setBillSendType("C");
        b.setBillSendTypeDesc("测试");
        responseMap.put(key, b);
    }

    // 获取客户手机号
    private void getCustMobile(Map<String, Object> map) {
        map.put(key, pubilcMobileNo);
    }


    // 获取客户卡列表
    private void getCardInfos(Map<String,String> reqeustMap,Map<String, Object> responseMap) {
        if(!idType.equals(reqeustMap.get("idType"))|| !idNo.equals(reqeustMap.get("idNo"))){responseMap.put(key,null); }
        List<CardInfo> cardInfoList = new ArrayList<>();
        for(String cardNo:getCardNoList()) {
            CardInfo cardInfo = new CardInfo();
            cardInfo.setCardNo(cardNo);
            cardInfoList.add(cardInfo);
        }
        responseMap.put(key, cardInfoList);
    }
    // 更新客户账单寄送方式
    private void updateBillSendType(Map<String,String> reqeustMap,Map<String, Object> responseMap) {
        if(getCardNoList().contains(reqeustMap.get("cardNo"))){responseMap.put(key,null); }
        responseMap.put(key, true);
    }

    //根据卡号查询额度
    private void getCardBalance(Map<String, Object> map, Object object){
        //获得参数map
        Map<String, String> param = (Map<String, String>) object;
        String cardNo = param.get("cardNo");
        if("11111111111111111".equals(cardNo)){
            //返回正常值
            List<Balance> list = new ArrayList<>();

            Balance balance = new Balance();
            balance.setCardNo("11111111111111111");
            balance.setCurrencyCode("CNY");
            balance.setPreCashAdvanceCreditLimit("31");
            balance.setWholeCreditLimit("101");
            balance.setPeriodAvailableCreditLimit("100");

            Balance balance2 = new Balance();
            balance2.setCardNo("11111111111111111");
            balance2.setCurrencyCode("USD");
            balance2.setPreCashAdvanceCreditLimit("33");
            balance2.setWholeCreditLimit("130");
            balance2.setPeriodAvailableCreditLimit("102");
            list.add(balance);
            list.add(balance2);
            map.put(key, list);
        }else if("222222222222222222".equals(cardNo)){
            //返回空值
            List<Balance> list = new ArrayList<>();
            map.put(key, list);
        }else{
            map = null;
        }
    }

    //查询海淘卡
    private void getWbicCards(Map<String, Object> map, Object object){
        //获得参数map
        Map<String, String> param = (Map<String, String>) object;
        String idNum = param.get("idNum");
        String idType = param.get("idType");
        if("110625199301280000".equals(idNum) && "IDCD".equals(idType)){
            String cardNo = "111111222222333333";
            map.put(key, cardNo);
        }else{
            map = null;
        }
    }

    //给海淘卡用户发送短信
    private void wbicCardInfoSendSms(Map<String, Object> map, Object object){
        //获得参数map
        Map<String, String> param = (Map<String, String>) object;
        String cardNo = param.get("cardNo");
        if("11111111111111111111".equals(cardNo)){
            map.put(key, true);
        }else{
            map = null;
        }
    }

}
