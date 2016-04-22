package com.yada.wechatbank.service;

import java.util.List;

/**
 * 海淘卡service
 * Created by pangChangSong on 2016/4/21.
 */
public interface WbicCardInfoService {

    /**
     * 查询海淘卡
     * @param idNum 证件号
     * @param idType 证件类型
     * @return 卡集合
     */
    List<String> getWbicCards(String idNum, String idType);

    /**
     * 海淘用户发送短信
     * @param cardNo 卡号
     * @return
     */
    Boolean wbicCardInfoSendSms(String cardNo);
}
