package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.*;
import com.yada.wechatbank.model.*;
import com.yada.wechatbank.service.CreditLimitTemporaryUpService;
import com.yada.wechatbank.util.AmtUtil;
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
        Map<String, String> mapCardHolderInfo = initGcsParam();
        mapCardHolderInfo.put("cardNo", cardNo);

        //获取用户姓名
        CardHolderInfoResp cardHolderInfoResp = httpClient.send(getCardHolderInfo, mapCardHolderInfo, CardHolderInfoResp.class);
        CardHolderInfo cardHolderInfo = cardHolderInfoResp == null ? null : cardHolderInfoResp.getData();
        if (cardHolderInfo == null) {
            return null;
        }
        String phoneNumber = cardHolderInfo.getMobileNo();
        String eosCustomerName = cardHolderInfo.getFamilyName();
        String eosID = String.format("0602%s%05d", date, times);
        //暂时只可提升人民币
        String eosCurrency = "CNY";
        Map<String, String> map = initGcsParam();
        map.put("eosID", eosID);
        map.put("eosCustomerName", eosCustomerName);
        map.put("eosCustomerIdType", IdTypeUtil.numIdTypeTransformToECode(certType));
        map.put("certNum", certNum);
        map.put("phoneNumber", phoneNumber);
        map.put("eosCurrency", eosCurrency);
        map.put("cardNo", cardNo);
        map.put("eosPreAddLimit", AmtUtil.procMoneyToString(eosPreAddLimit));
        map.put("eosStarLimitDate", eosStarLimitDate);
        map.put("eosEndLimitDate", eosEndLimitDate);
        map.put("cardStyle", cardStyle);
        map.put("issuingBranchId", issuingBranchId);
        map.put("pmtCreditLimit", pmtCreditLimit);
        BooleanResp resultR = httpClient.send(temporaryUpCommit, map, BooleanResp.class);
        return resultR == null ? false : resultR.getData();
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
        map.put("currencyNo", "CNY");
        CreditLimitTemporaryUpReviewResp clturr = httpClient.send(creditLimitTemporaryUpReview, map, CreditLimitTemporaryUpReviewResp.class);
        CreditLimitTemporaryUpReview creditLimitTemporaryUpReview= clturr == null ? null : clturr.getData();
        //金额转换
        if(creditLimitTemporaryUpReview!=null)
        {
            creditLimitTemporaryUpReview.setCreditLimit(AmtUtil.procString(creditLimitTemporaryUpReview.getCreditLimit()));
            creditLimitTemporaryUpReview.setAmount(AmtUtil.procString(creditLimitTemporaryUpReview.getAmount()));
        }
        return creditLimitTemporaryUpReview;
    }

    @Override
    public List<CreditLimitTemporaryUpStatus> getLimitUpHistory(String cardNo) {
        Map<String, String> map = initGcsParam();
        map.put("cardNo", cardNo);
        CreditLimitTemporaryUpStatusResp cltusr = httpClient.send(getTemporaryUpCommitStatus, map, CreditLimitTemporaryUpStatusResp.class);
        List<CreditLimitTemporaryUpStatus> list = cltusr == null ? null : cltusr.getData();
        Calendar eosStarLimitDateCalendar = Calendar.getInstance();

        List<CreditLimitTemporaryUpStatus> resultLiast=new ArrayList<>();

        if(list==null)
        {
            return null;
        }

        for (CreditLimitTemporaryUpStatus aList : list) {
            try {
                eosStarLimitDateCalendar.setTime(dateFormat.parse(aList.getEosStarLimitDate()));
                // 给申请日期增加6个月
                eosStarLimitDateCalendar.add(Calendar.MONTH, 6);
            } catch (ParseException e) {
                logger.warn("@LSTE@转换日期异常cardNo[{}]",  cardNo);
                continue;
            }
            // 增额生效开始日期加6个月后与当前日期比较
            if (eosStarLimitDateCalendar.compareTo(Calendar.getInstance()) < 0) {
                // 记录超过半年，过滤掉
                continue;
            }
            // 工单状态
            if (!"70".equals(aList.getEosState()) && !"50".equals(aList.getEosState())) {
                // 不是审批批准并已同步CSR（70）也不是审批批准（50），所以过滤掉
                continue;
            }
            //金额转换
            aList.setEosLimit(AmtUtil.procString(aList.getEosLimit()));
            resultLiast.add(aList);
        }

        return resultLiast;
    }


}
