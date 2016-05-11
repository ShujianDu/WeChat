package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.CardHolderInfoResp;
import com.yada.wechatbank.kafka.MessageProducer;
import com.yada.wechatbank.kafka.TopicEnum;
import com.yada.wechatbank.model.CardHolderInfo;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.service.CardHolderInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 个人资料serviceImpl
 *
 * @author Tx
 */
@Service
public class CardHolderInfoServiceImpl extends BaseService implements CardHolderInfoService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String DEFAULT = "未登记";
    private static final String REPLACESTRING = "****";

    @Autowired
    MessageProducer messageProducer;

    @Value("${url.getCardHolderInfo}")
    private String getCardHolderInfo;

    /**
     * 查询客户信息
     *
     * @param identityType 证件类型
     * @param identityNo   证件号码
     * @return 客户信息
     */
    public CardHolderInfo getCardHolderInfo(String identityType, String identityNo) {

        messageProducer.send(TopicEnum.EBANK_QUERY, "CardHolderGetCardHolderInfo", "证件类型[" + identityType + "]证件号[" + identityNo + "]查询卡列表");

        List<CardInfo> cardInfos = selectCardNos(identityType, identityNo);
        CardHolderInfo cardHolderInfo;
        if (cardInfos != null && cardInfos.size() != 0) {
            Map<String, String> map = initGcsParam();
            //任意有效卡
            map.put("cardNo", cardInfos.get(0).getCardNo());
            CardHolderInfoResp cardHolderInfoResp = httpClient.send(getCardHolderInfo, map, CardHolderInfoResp.class);
            cardHolderInfo = cardHolderInfoResp == null ? null : cardHolderInfoResp.getData();
        } else {
            logger.warn("@WDZL@根据用户的证件类型[{}]，证件号[{}]获取用户卡列表为空", identityType, identityNo);
            messageProducer.send(TopicEnum.EBANK_QUERY, "BillingSendWay_getCardHolderInfo", "根据用户的证件类型[" + identityType + "]证件号[" + identityNo + "]获取用户卡列表为空");
            return null;
        }
        //判断是否获取到数据
        if (cardHolderInfo == null) {
            logger.warn("@WDZL@根据用户的证件类型[{}]，证件号[{}]查询客户信息，获取到的客户信息为null", identityType, identityNo);
            messageProducer.send(TopicEnum.EBANK_QUERY, "BillingSendWay_getCardHolderInfo", "根据用户的证件类型[" + identityType + "]证件号[" + identityNo + "]查询客户信息，获取到的客户信息为null");
            return null;
        }
        //手机号码处理
        String mobileNo = cardHolderInfo.getMobileNo();
        if (mobileNo == null || "".equals(mobileNo)) {
            mobileNo = DEFAULT;
        } else if (mobileNo.length() == 11) {
            mobileNo = this.replaceStartToEnd(3, 7, mobileNo, REPLACESTRING);
        }
        cardHolderInfo.setMobileNo(mobileNo);

        //住宅电话处理
        String homeAddressPhone = cardHolderInfo.getHomeAddressPhone();
        if (homeAddressPhone == null || "".equals(homeAddressPhone)) {
            homeAddressPhone = DEFAULT;
        } else if (homeAddressPhone.length() >= 8) {
            homeAddressPhone = this.replaceStartToEnd(homeAddressPhone.length() - 4, homeAddressPhone.length(), homeAddressPhone, REPLACESTRING);
        }
        cardHolderInfo.setHomeAddressPhone(homeAddressPhone);

        //单位电话
        String workUnitPhone = cardHolderInfo.getWorkUnitPhone();
        if (workUnitPhone == null || "".equals(workUnitPhone)) {
            workUnitPhone = DEFAULT;
        } else if (workUnitPhone.length() >= 8) {
            workUnitPhone = this.replaceStartToEnd(workUnitPhone.length() - 4, workUnitPhone.length(), workUnitPhone, REPLACESTRING);
        }
        cardHolderInfo.setWorkUnitPhone(workUnitPhone);

        //电子邮箱
        String mailBox = cardHolderInfo.getMailBox();
        int index = mailBox == null ? 0 : mailBox.indexOf('@');
        if (mailBox == null || "".equals(mailBox)) {
            mailBox = DEFAULT;
        } else if (index >= 1) {
            if (index >= 8) {
                mailBox = this.replaceStartToEnd(index - 4, index, mailBox, REPLACESTRING);
            } else {
                mailBox = this.replaceStartToEnd((index + 1) / 2, index, mailBox, REPLACESTRING);
            }
        }
        cardHolderInfo.setMailBox(mailBox);

        //单位名称
        String workUnitName = cardHolderInfo.getWorkUnitName();
        if (workUnitName == null || "".equals(workUnitName)) {
            workUnitName = DEFAULT;
        } else if (workUnitName.length() > 1) {
            workUnitName = this.replaceStartToEnd((workUnitName.length() + 1) / 2, workUnitName.length(), workUnitName, REPLACESTRING);
        }
        cardHolderInfo.setWorkUnitName(workUnitName);

        //账单地址
        String billAddressLine = cardHolderInfo.getBillAddressLine();
        int billAddressLineLength =billAddressLine == null ? 0 :billAddressLine.length();
        if (billAddressLine == null || "".equals(billAddressLine)) {
            billAddressLine = DEFAULT;
        } else if (billAddressLineLength > 1) {
            if (billAddressLineLength >= 16) {
                billAddressLine = this.replaceStartToEnd(billAddressLineLength - 8, billAddressLineLength, billAddressLine, REPLACESTRING);
            } else {
                billAddressLine = this.replaceStartToEnd((billAddressLineLength + 1) / 2, billAddressLineLength, billAddressLine, REPLACESTRING);
            }
            billAddressLine = billAddressLine.replaceAll("\\d", "*");
        }
        cardHolderInfo.setBillAddressLine(billAddressLine);

        //邮政编码不修改
        String postalCode = cardHolderInfo.getPostalCode();
        if (postalCode == null || "".equals(postalCode)) {
            postalCode = DEFAULT;
        }
        cardHolderInfo.setPostalCode(postalCode);
        logger.info("@WDZL@根据卡号[{}]查询客户信息，获取到的客户信息为[{}]", cardInfos.get(0).getCardNo(), cardHolderInfo);
        return cardHolderInfo;
    }

    /**
     * 替换指定下标范围内的字符串
     *
     * @param start     开始下标
     * @param end       结束下标
     * @param oldString 原始字符
     * @param newString 替换字符
     * @return 替换后结果
     */
    private String replaceStartToEnd(int start, int end, String oldString, String newString) {
        return oldString.substring(0, start) + newString + oldString.substring(end, oldString.length());
    }

}
