package com.yada.wechatbank.controller.mock;

import com.alibaba.fastjson.JSON;
import com.yada.wechatbank.model.CardApply;
import com.yada.wechatbank.model.CardApplyList;
import com.yada.wechatbank.model.CardInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 模拟行内返回
 * Created by QinQiang on 2016/4/11.
 */
@Controller
@RequestMapping(value = "mock")
public class MockController {

    private static final String key = "bizResult";

    private Map<String, Object> mockResult() {
        Map<String, Object> param = new HashMap<>();
        param.put("returnCode", "00");
        param.put("returnMsg", "成功");
        return param;
    }

    @RequestMapping(value = "getMobilePhone")
    @ResponseBody
    public String getMobilePhone() {
        Map<String, Object> map = mockResult();
        map.put(key, "18888888888");
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "sendSMS")
    @ResponseBody
    public String sendSMS() {
        Map<String, Object> map = mockResult();
        map.put(key, "true");
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "cardApply")
    @ResponseBody
    public String cardApply() {
        Map<String, Object> map = mockResult();
        CardApplyList list = new CardApplyList();
        list.setHasNext(true);
        List<CardApply> cardApplies = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CardApply cardApply = new CardApply();
            cardApply.setApplyNo("No:" + i);
            cardApply.setPdnDes("PdnDes" + i);
            cardApply.setPassDate("tPassDate" + i);
            cardApply.setPhaseDesc("Desc" + i);
            cardApplies.add(cardApply);
        }
        list.setCardApplies(cardApplies);
        map.put(key, list);
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "tempCreditCardReportLost")
    @ResponseBody
    public String tempCreditCardReportLost() {
        Map<String, Object> map = mockResult();
        map.put(key, true);
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "creditCardReportLost")
    @ResponseBody
    public String creditCardReportLost() {
        Map<String, Object> map = mockResult();
        map.put(key, true);
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "relieveCreditCardTempReportLost")
    @ResponseBody
    public String relieveCreditCardTempReportLost() {
        Map<String, Object> map = mockResult();
        map.put(key, true);
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "getCardInfos")
    @ResponseBody
    public String getCardInfos() {
        Map<String, Object> map = mockResult();
        List<CardInfo> cardInfoList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            CardInfo cardInfo = new CardInfo();
            cardInfo.setCardNo("622588014852929" + i);
            cardInfoList.add(cardInfo);
        }
        map.put(key, cardInfoList);
        return JSON.toJSONString(map);
    }
}
