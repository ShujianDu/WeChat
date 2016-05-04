package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.HttpClient;
import com.yada.wechatbank.client.model.PointsBalanceResp;
import com.yada.wechatbank.client.model.PointsValidatesResp;
import com.yada.wechatbank.client.model.VerificationCardNoResultResp;
import com.yada.wechatbank.kafka.MessageProducer;
import com.yada.wechatbank.kafka.TopicEnum;
import com.yada.wechatbank.model.*;
import com.yada.wechatbank.client.model.PointsDetailResp;
import com.yada.wechatbank.service.PointsService;
import com.yada.wx.db.service.dao.CustomerInfoDao;
import com.yada.wx.db.service.model.CustomerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Echo on 2016/4/12.
 */
@Service
public class PointsServiceImpl extends BaseService implements PointsService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HttpClient httpClient;
    @Value("${url.getPointsDetails}")
    private String getPointsDetails;
    @Value("${url.getPointsValidates}")
    private String getPointsValidates;
    @Value("${url.getPointsBalance}")
    private String getBalance;
    @Value("${url.verificationCardNo}")
    private String verificationCardNo;
    @Autowired
    private MessageProducer messageProducer;

    /**
     * 获取账户积分余额
     *
     * @param identityType 证件类型
     * @param identityNo   证件号
     * @return积分余额
     */
    @Override
    public PointsBalance getPointsBlance(String identityType, String identityNo) {
        String cardNo = "";
        //通过证件号和证件类型去后台查询卡号
        List<CardInfo> cardInfoList = selectCardNos(identityType, identityNo);
        if (cardInfoList != null && cardInfoList.size() != 0) {
            cardNo = cardInfoList.get(0).getCardNo();
            logger.info("@JF@查询积分余额-查询卡号cardNo[{}]", cardNo);
        }
        Map<String, String> map = new HashMap<>();
        map.put("cardNo", cardNo);
        PointsBalanceResp pointsBlanceResp = httpClient.send(getBalance, map, PointsBalanceResp.class);
        if (pointsBlanceResp == null) {
            logger.info("@JF@查询积分余额结果为空cardNo[{}]", cardNo);
            return null;
        }
        if (!"00".equals(pointsBlanceResp.getReturnCode())) {
            logger.info("@JF@查询积分余额返回错误码returnCode[{}]cardNo[{}]", pointsBlanceResp.getReturnCode(), cardNo);
            return null;
        }
        map.put("identityType",identityType);
        map.put("identityNo",identityNo);
        messageProducer.send(TopicEnum.EBANK_QUERY,"PointsGetPointsBlance",map);
        return pointsBlanceResp.getData();
    }

    /**
     * 获取积分明细
     *
     * @param identityType 证件类型
     * @param identityNo   证件号
     * @return 积分明细
     */
    @Override
    public List<PointsDetail> getPointsDetail(String identityType, String identityNo) {
        String cardNo = "";
        //通过证件号和证件类型去后台查询卡号
        List<CardInfo> cardInfoList = selectCardNos(identityType, identityNo);
        if (cardInfoList != null && cardInfoList.size() != 0) {
            cardNo = cardInfoList.get(0).getCardNo();
        }
        //查询积分明细
        Map<String, String> map = new HashMap<>();
        map.put("cardNo", cardNo);
        PointsDetailResp pointsDetailResp = httpClient.send(getPointsDetails, map, PointsDetailResp.class);
        if (pointsDetailResp == null) {
            logger.warn("@JFMX@积分明细查询返回结果为空cardNo[{}]", cardNo);
            return null;
        }
        if (!"00".equals(pointsDetailResp.getReturnCode())) {
            logger.warn("@JFMX@积分明细查询返回错误码returnCode[{}]cardNo[{}]", pointsDetailResp.getReturnCode(), cardNo);
            return null;
        }
        map.put("identityType",identityType);
        map.put("identityNo",identityNo);
        messageProducer.send(TopicEnum.EBANK_QUERY,"PointsGetPointsDetail",map);
        return pointsDetailResp.getData();
    }

    @Override
    public List<List<PointsDetail>> getList(List<PointsDetail> list) {
        List<List<PointsDetail>> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            // 没有父类ID即为父类积分明细
            if (list.get(i).getParentId() == null
                    || "".equals(list.get(i).getParentId())) {
                List<PointsDetail> detailList = new ArrayList<>();
                detailList.add(list.get(i));
                newList.add(detailList);
                list.remove(i);
                i--;
            }
        }
        // 将子类积分放入父类积分集合中
        for (int i = 0; i < list.size(); i++) {
            String parentId = list.get(i).getParentId();
            String pointuseFlg = list.get(i).getPointuseFlg();
            for (int j = 0; j < newList.size(); j++) {
                if (parentId.equals(newList.get(j).get(0).getId())) {
                    if ("冻结".equals(pointuseFlg)) {
                        String totalPoint = (Double.parseDouble(newList.get(j)
                                .get(0).getTotalPoint()) - Double
                                .parseDouble(list.get(i).getTotalPoint())) + "";
                        newList.get(j).get(0).setTotalPoint(totalPoint);
                    }
                    newList.get(j).add(list.get(i));
                    list.remove(i);
                    i--;
                }
            }
        }
        return newList;
    }

    /**
     * 获取积分有效期
     *
     * @param cardNo 卡号
     * @return 积分有效期
     */
    @Override
    public List<PointsValidates> getPointsValidates(String cardNo) {
        Map<String, String> map = new HashMap<>();
        map.put("cardNo", cardNo);
        //调用后台获取积分到期日
        PointsValidatesResp pointsValidatesResp = httpClient.send(getPointsValidates, map, PointsValidatesResp.class);
        if (pointsValidatesResp == null) {
            logger.warn("@JFDQR@查询积分到期日结果为空cardNo[{}]", cardNo);
            return null;
        }
        if (!"00".equals(pointsValidatesResp.getReturnCode())) {
            logger.warn("@JFDQR@查询积分到期日返回错误码returnCode[{}]cardNo[{}]", pointsValidatesResp.getReturnCode(), cardNo);
            return null;
        }

        messageProducer.send(TopicEnum.EBANK_QUERY,"PointsGetPointsValidates",map);
        return pointsValidatesResp.getData();
    }

    /**
     * @param identityType 证件类型
     * @param identityNo   证件号
     * @return 卡号
     */
    @Override
    public String getCardNo(String identityType, String identityNo) {
        String cardNo = null;
        //通过证件号和证件类型去后台查询卡号
        List<CardInfo> cardInfoList = selectCardNos(identityType, identityNo);
        if (cardInfoList != null && cardInfoList.size() != 0) {
            cardNo = cardInfoList.get(0).getCardNo();
        }
        return cardNo;
    }

    /**
     * 积分兑换获取加密数据
     *
     * @param cardNo 卡号
     * @return 加密后卡号和sign
     */
    @Override
    public VerificationCardNoResult verificationCardNo(String cardNo) {
        Map<String, String> map = new HashMap<>();
        map.put("cardNo", cardNo);
        VerificationCardNoResultResp verificationCardNoResultResp = httpClient.send(verificationCardNo, map, VerificationCardNoResultResp.class);
        if (verificationCardNoResultResp == null) {
            logger.warn("@JFDH@积分兑换加密返回结果为空cardNo[{}]", cardNo);
            return null;
        }
        if (!"00".equals(verificationCardNoResultResp.getReturnCode())) {
            logger.warn("@JFDH@积分兑换加密返回错误码returnCode[{}]cardNo[{}]", verificationCardNoResultResp.getReturnCode(), cardNo);
            return null;
        }
        messageProducer.send(TopicEnum.EBANK_QUERY,"PointsVerificationCardNo",map);
        return verificationCardNoResultResp.getData();
    }


}
