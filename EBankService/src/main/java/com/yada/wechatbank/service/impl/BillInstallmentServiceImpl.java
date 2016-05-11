package com.yada.wechatbank.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.AmountLimitResp;
import com.yada.wechatbank.client.model.BillCostResp;
import com.yada.wechatbank.client.model.BillingPeriodResp;
import com.yada.wechatbank.client.model.BillingSummaryResp;
import com.yada.wechatbank.client.model.StringResp;
import com.yada.wechatbank.kafka.MessageProducer;
import com.yada.wechatbank.kafka.TopicEnum;
import com.yada.wechatbank.model.AmountLimit;
import com.yada.wechatbank.model.BillCost;
import com.yada.wechatbank.model.BillingPeriod;
import com.yada.wechatbank.model.BillingSummary;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.service.BillInstallmentService;
import com.yada.wechatbank.util.AmtUtil;
import com.yada.wechatbank.util.Crypt;
import com.yada.wx.db.service.dao.InstallmentInfoDao;
import com.yada.wx.db.service.model.InstallmentInfo;

/**
 * 账单分期service实现
 *
 * @author Tx
 */
@Service
@Transactional
public class BillInstallmentServiceImpl extends BaseService implements BillInstallmentService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MessageProducer messageProducer;
    @Autowired
    private InstallmentInfoDao installmentInfoDao;

    @Value("${url.billingPeriod}")
    private String getBillingPeriod;
    @Value("${url.getAmountLimit}")
    private String getAmountLimit;
    @Value("${url.queryBillCost}")
    private String queryBillCost;
    @Value("${url.billInstallment}")
    private String billInstallment;
    @Value("${url.billingSummary}")
    protected String billingSummary;

    @Override
    public List<CardInfo> getProessCardNoList(String identityType, String identityNo) {
        List<CardInfo> cardList = selectCardNos(identityType, identityNo);
        if (cardList != null && cardList.size() != 0) {
            try {
                for (CardInfo c : cardList) {
                    c.setCardNo(Crypt.cardNoOneEncode(c.getCardNo()));
                }
                return cardList;
            } catch (Exception e) {
                logger.warn("@BillInstallment@将卡号进行展示处理时出现错误[" + cardList + "]", e);
            }
        }
        return cardList;
    }

    @Override
    public BillingSummary getCurrentPeriodBill(String cardNo) {
        List<BillingPeriod> usableBillPeriods = new ArrayList<>();
        BillingSummary billingSummary = null;
        Map<String, String> map = initGcsParam();
        map.put("cardNo", cardNo);
        // kafka事件记录
        messageProducer.send(TopicEnum.EBANK_QUERY,
                "BillingSummaryGetCurrentPeriodBill", map);
        // 查询卡片账期
        BillingPeriodResp billingPeriodResp = httpClient.send(getBillingPeriod, map, BillingPeriodResp.class);
        List<BillingPeriod> billingPeriods = billingPeriodResp == null ? null : billingPeriodResp.getData();
        if (billingPeriods == null) {
            logger.warn("@BillingSummary@通过后台根据用户卡[{}]获取用户账单周期时返回为null", cardNo);
            messageProducer.send(TopicEnum.EBANK_QUERY,
                    "BillingSummary_getCurrentPeriodBill",
                    "通过后台根据用户卡[" + cardNo + "]获取用户账单周期时返回为null");
            return null;
        }
        // 循环遍历查找可用账期
        for (BillingPeriod temp : billingPeriods) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MONTH, -1);
            try {
                Date endDate = df.parse(temp.getPeriodEndDate());
                if (calendar.getTime().getTime() > endDate.getTime()) {
                    logger.warn("cardNo[{}]账单日期不在月周期内！", cardNo);
                    messageProducer.send(TopicEnum.EBANK_QUERY,
                            "BillingSummaryGetCurrentPeriodBill",
                            "cardNo[" + cardNo + "]账单日期不在月周期内！");
                    return null;
                }
            } catch (Exception e) {
                logger.warn("cardNo[{}]账单日期不存在或格式错误！error[{}]", cardNo, e.getMessage());
                messageProducer.send(TopicEnum.EBANK_QUERY,
                        "BillingSummaryGetCurrentPeriodBill", "cardNo[" + cardNo +
                                "]账单日期不存在或格式错误！");
                return null;
            }

            if (temp.getCurrencyCode().equalsIgnoreCase("CNY")) {
                usableBillPeriods.add(temp);
                break;
            }
        }
        // 根据账期查询
        for (BillingPeriod temp : usableBillPeriods) {
            try {
                Map<String, String> mapSummary = initGcsParam();
                mapSummary.put("statementNo", temp.getStatementNo());
                mapSummary.put("accountId", temp.getAccountId());
                BillingSummaryResp billingSummaryResp = httpClient.send(this.billingSummary, mapSummary, BillingSummaryResp.class);
                if (billingSummaryResp == null || billingSummaryResp.getData() == null) {
                    return null;
                }
                billingSummary = billingSummaryResp.getData();
                billingSummary.setCardNo(cardNo);
                // 金额转换
                billingSummary.setClosingBalance(AmtUtil.procString(billingSummary.getClosingBalance()));
                billingSummary.setMinPaymentAmount(AmtUtil.procString(billingSummary.getMinPaymentAmount()));
            } catch (Exception e) {
                logger.warn("cardNo[{}]查询账单错误：[{}]", cardNo, e.getMessage());
                messageProducer.send(TopicEnum.EBANK_QUERY,
                        "BillingSummaryGetCurrentPeriodBill", "cardNo[" + cardNo +
                                "]查询账单错误！");
                return null;
            }
        }
        return billingSummary;
    }

    public AmountLimit getAmountLimit(String cardNo, String currencyCode) {
        Map<String, String> map = initGcsParam();
        map.put("cardNo", cardNo);
        map.put("currencyCode", currencyCode);
        // kafka事件记录
        messageProducer.send(TopicEnum.EBANK_QUERY, "BillingSummaryGetAmountLimit", map);
        AmountLimitResp amountLimitResp = httpClient.send(getAmountLimit, map, AmountLimitResp.class);
        AmountLimit amountLimit = amountLimitResp == null ? null : amountLimitResp.getData();
        if (amountLimitResp != null) {
            Map<String, String> mapResp = getCodeAndMsg(amountLimitResp);
            if (mapResp != null) {
                if (amountLimit == null) {
                    amountLimit = new AmountLimit();
                }
                amountLimit.setRespCode(mapResp.get("gcsMessage"));
            }
        }
        if (amountLimit != null && amountLimit.getRespCode() != null && "".equals(amountLimit.getRespCode())) {
            amountLimit.setMaxAmount(parseString(AmtUtil.procString(amountLimit.getMaxAmount())));
            amountLimit.setShowMinAmount(parseString(AmtUtil.procString(amountLimit.getMinAmount())));
        }
        return amountLimit;
    }

    public BillCost getBillCost(String accountId, String accountNo, String currencyCode, String billLowerAmount, String billActualAmount,
                                String installmentsNumber, String feeInstallmentsFlag) {
        Map<String, String> map = initGcsParam();
        map.put("accountId", accountId);
        map.put("accountNumber", accountNo);
        map.put("currencyCode", currencyCode);
        map.put("billLowerAmount", billLowerAmount);
        map.put("billActualAmount", AmtUtil.procMoneyToString(billActualAmount));
        map.put("installmentsNumber", installmentsNumber);
        map.put("feeInstallmentsFlag", feeInstallmentsFlag);
        map.put("channelId", "A");
        // kafka事件记录
        messageProducer.send(TopicEnum.EBANK_QUERY, "BillingSummaryGetBillCost", map);
        BillCostResp billCostResp = httpClient.send(queryBillCost, map, BillCostResp.class);
        BillCost billCost = billCostResp == null ? null : billCostResp.getData();
        // 金额转换
        if (billCost != null) {
            billCost.setInstallmentsfee(AmtUtil.procString(billCost.getInstallmentsfee()));
            billCost.setInstallmentsAlsoAmountFirst(AmtUtil.procString(billCost.getInstallmentsAlsoAmountFirst()));
            billCost.setInstallmentsAlsoAmountEach(AmtUtil.procString(billCost.getInstallmentsAlsoAmountEach()));
            billCost.setCurrentBillMinimum(AmtUtil.procString(billCost.getCurrentBillMinimum()));
            billCost.setCurrentBillSurplusAmount(AmtUtil.procString(billCost.getCurrentBillSurplusAmount()));
        }
        return billCost;
    }

    @Transactional
    public boolean billInstallment(String accountId, String accountNo, String cardNo, String currencyCode, String billLowerAmount, String billActualAmount,
                                   String installmentsNumber, String feeInstallmentsFlag) {
        boolean returnRes;
        Map<String, String> map = initGcsParam();
        map.put("accountId", accountId);
        map.put("accountNumber", accountNo);
        map.put("currencyCode", currencyCode);
        map.put("billLowerAmount", billLowerAmount);
        map.put("billActualAmount", AmtUtil.procMoneyToString(billActualAmount));
        map.put("installmentsNumber", installmentsNumber);
        map.put("feeInstallmentsFlag", feeInstallmentsFlag);
        map.put("channelId", "A");
        String tDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        // kafka事件记录
        messageProducer.send(TopicEnum.EBANK_QUERY, "BillingSummaryBillInstallment", map);
        InstallmentInfo bi = new InstallmentInfo(cardNo, "账单分期", currencyCode, billActualAmount, installmentsNumber, feeInstallmentsFlag, tDate);
        StringResp stringResp = httpClient.send(billInstallment, map, StringResp.class);
        String resultCode = stringResp == null ? null : stringResp.getData();
        if (resultCode != null) {
            bi.setGcsCode(resultCode);
        } else {
            return false;
        }
        if ("+GC00000".equals(resultCode)) {
            bi.setStatus("1");
            returnRes = true;
        } else {
            bi.setStatus("0");
            returnRes = false;
        }
        try {
            installmentInfoDao.save(bi);
            messageProducer.send(TopicEnum.EBANK_DO, "BillingSummaryBillInstallment", bi);
        } catch (Exception e) {
            logger.error("", e);
        }
        return returnRes;
    }

    @Override
    public String verificationMobileNo(String identityType, String identityNo, String mobileNo) {
        String mobile = getCustMobileNo(identityType, identityNo);
        String result = "";
        if (mobile == null) {
            result = "exception";
        } else if ("".equals(mobile.trim())) {
            result = "noMobileNumber";
        } else if (!mobile.equals(mobileNo)) {
            result = "wrongMobilNo";
        }
        messageProducer.send(TopicEnum.EBANK_QUERY, "BillingSummaryVerificationMobileNo", result);
        return result;
    }

    /**
     * 转换字符串，将金额上下限小数点后两位全部转化为00
     *
     * @param amt 金额
     * @return 返回转换后的金额
     */

    private String parseString(String amt) {
        StringBuilder oldAmt = new StringBuilder(amt);
        StringBuilder newAmt = oldAmt.delete(oldAmt.length() - 2, oldAmt.length());
        StringBuilder resultAmt = newAmt.append("00");
        return resultAmt.toString();
    }

}
