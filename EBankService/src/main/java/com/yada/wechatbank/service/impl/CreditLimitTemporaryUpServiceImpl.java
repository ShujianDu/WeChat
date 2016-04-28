package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.*;
import com.yada.wechatbank.model.*;
import com.yada.wechatbank.service.CreditLimitTemporaryUpService;
import com.yada.wechatbank.util.Crypt;
import com.yada.wechatbank.util.IdTypeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 临时提升额度service实现
 *
 * @author Tx
 */
@Service
public class CreditLimitTemporaryUpServiceImpl extends BaseService implements CreditLimitTemporaryUpService {
    private final Logger logger = LoggerFactory
            .getLogger(this.getClass());

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // 信用卡提额的尝试次数
    private AtomicInteger creditLimitUpTimes = new AtomicInteger(0);
    @Value("${url.getCardHolderInfo}")
    private String getCardHolderInfo;
    @Value("${url.temporaryUpCommit}")
    private String temporaryUpCommit;
    @Value("${url.creditLimitTemporaryUpReview}")
    private String creditLimitTemporaryUpReview;
    @Value("${url.getTemporaryUpCommitStatus}")
    private String getTemporaryUpCommitStatus;

    @Override
    public List<String> getProessCardNoList(String identityType, String identityNo) {
        List<String> cardList = selectCardNoList(identityType, identityNo);
        if (cardList != null && cardList.size() != 0) {
            try {
                return Crypt.cardNoCrypt(cardList);
            } catch (Exception e) {
                logger.error("@LSTE@将卡号进行展示处理时出现错误[" + cardList
                        + "]", e);
            }
        }
        return null;
    }

    @Override
    public Boolean temporaryUpCommit(String certType, String certNum, String cardNo, String eosPreAddLimit, String eosStarLimitDate, String eosEndLimitDate, String cardStyle, String issuingBranchId, String pmtCreditLimit) {
        String date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        int times = creditLimitUpTimes.getAndIncrement();
        if (times >= 100000) {
            creditLimitUpTimes.set(0);
            times = creditLimitUpTimes.getAndIncrement();
        }
        Map<String, String> mapCardHolderInfo = initDirectSaleParam();
        mapCardHolderInfo.put("cardNo", cardNo);

        //获取用户姓名
        CardHolderInfoResp cardHolderInfoResp = httpClient.send(getCardHolderInfo, mapCardHolderInfo, CardHolderInfoResp.class);
        CardHolderInfo cardHolderInfo = cardHolderInfoResp == null ? null : cardHolderInfoResp.getBizResult();
        if (cardHolderInfo == null) {
            return null;
        }

        //TODO 流程由原有通过数据库获取的手机号，更换为个人资料查询的手机号（需发出确认）
        String phoneNumber = cardHolderInfo.getMobileNo();
        String eosCustomerName = cardHolderInfo.getFamilyName();
        String eosID = String.format("0602%s%05d", date, times);
        //暂时只可提升人民币
        String eosCurrency = "CNY";
        Map<String, String> map = initDirectSaleParam();
        map.put("eosID", eosID);
        map.put("eosCustomerName", eosCustomerName);
        map.put("eosCustomerIdType", IdTypeUtil.numIdTypeTransformToECode(certType));
        map.put("certNum", certNum);
        map.put("phoneNumber", phoneNumber);
        map.put("eosCurrency", eosCurrency);
        map.put("cardNo", cardNo);
        map.put("eosPreAddLimit", eosPreAddLimit);
        map.put("eosStarLimitDate", eosStarLimitDate);
        map.put("eosEndLimitDate", eosEndLimitDate);
        map.put("cardStyle", cardStyle);
        map.put("issuingBranchId", issuingBranchId);
        map.put("pmtCreditLimit", pmtCreditLimit);
        BooleanResp resultR = httpClient.send(temporaryUpCommit, map, BooleanResp.class);
        Boolean b = resultR == null ? false : resultR.getData();
        return b == null ? false : b;
    }

    @Override
    public CreditLimitTemporaryUpReview getAmount(String certType, String certNum, String cardNo) {
        String mobileNo = getCustMobileNo(certType, certNum);
        if (mobileNo == null || "".equals(mobileNo)) {
            return null;
        }
        Map<String, String> map = initGcsParam();
        map.put("certType", IdTypeUtil.numIdTypeTransformToECode(certType));
        map.put("certNum", certNum);
        map.put("cardNo", cardNo);
        map.put("phoneNumber", mobileNo);
        CreditLimitTemporaryUpReviewResp clturr = httpClient.send(creditLimitTemporaryUpReview, map, CreditLimitTemporaryUpReviewResp.class);
        return clturr == null ? null : clturr.getBizResult();
    }

    @Override
    public List<CreditLimitTemporaryUpStatus> getLimitUpHistory(String certType, String certNum, String cardNo) {
        Map<String, String> map = initGcsParam();
        map.put("certType", IdTypeUtil.numIdTypeTransformToECode(certType));
        map.put("certNum", certNum);
        map.put("cardNo", cardNo);
        CreditLimitTemporaryUpStatusResp cltusr = httpClient.send(getTemporaryUpCommitStatus, map, CreditLimitTemporaryUpStatusResp.class);
        List<CreditLimitTemporaryUpStatus> list = cltusr == null ? null : cltusr.getBizResult();
        Calendar eosStarLimitDateCalendar = Calendar.getInstance();

        // 给申请日期增加6个月
        eosStarLimitDateCalendar.add(Calendar.MONTH, 6);

        for (int i = 0; i < list.size(); i++) {
            try {
                eosStarLimitDateCalendar.setTime(dateFormat.parse(list.get(i).getEosStarLimitDate()));
            } catch (ParseException e) {
                logger.warn("@LSTE@转换日期异常certType[{}] certNum[{}] cardNo[{}]", certType, certNum, cardNo);
                list.remove(i);
            }
            // 增额生效开始日期加6个月后与当前日期比较
            if (eosStarLimitDateCalendar.compareTo(Calendar.getInstance()) < 0) {
                // 记录超过半年，过滤掉
                list.remove(list.get(i));
                continue;
            }
            // 工单状态
            if (!"70".equals(list.get(i).getEosState()) && !"50".equals(list.get(i).getEosState())) {
                // 不是审批批准并已同步CSR（70）也不是审批批准（50），所以过滤掉
                list.remove(i);
                continue;
            }
        }
        return list;
    }


}
