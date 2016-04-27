package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.*;
import com.yada.wechatbank.model.*;
import com.yada.wechatbank.service.BillInstallmentService;
import com.yada.wechatbank.util.AmtUtil;
import com.yada.wechatbank.util.Crypt;
import com.yada.wx.db.service.dao.InstallmentInfoDao;
import com.yada.wx.db.service.model.InstallmentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.RuntimeErrorException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 账单分期service实现
 * @author Tx
 */
@Service
@Transactional
public class BillInstallmentServiceImpl extends BaseService implements BillInstallmentService {
    private final Logger logger = LoggerFactory
            .getLogger(this.getClass());

    @Value("${url.getBillingPeriod}")
    private String getBillingPeriod;
    @Value("${url.getAmountLimit}")
    private String getAmountLimit;
    @Value("${url.queryBillCost}")
    private String queryBillCost;
    @Value("${url.billInstallment}")
    private String billInstallment;
    @Value("${url.billingSummary}")
    protected String billingSummary;

    @Autowired
    private InstallmentInfoDao installmentInfoDao;

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
                logger.error("@ZDFQ@将卡号进行展示处理时出现错误[" + cardList
                        + "]", e);
            }
        }
        return cardList;
    }

    @Override
    public BillingSummary getCurrentPeriodBill(String cardNo) {
        List<BillingPeriod> usableBillPeriods = new ArrayList<>();
        BillingSummary billingSummary = null;
        Map<String,String> map=initGcsParam();
        map.put("cardNo",cardNo);
        // 查询卡片账期
        BillingPeriodResp billingPeriodResp = httpClient.send(getBillingPeriod, map, BillingPeriodResp.class);
        List<BillingPeriod> billingPeriods = billingPeriodResp == null ? null : billingPeriodResp.getBizResult();
        if (billingPeriods == null) {
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
                    throw new RuntimeErrorException(null, "账单日期不在月周期内！");
                }
            } catch (Exception e) {
                logger.error("账单日期不存在或格式错误！", e);
                throw new RuntimeErrorException(null, "账单日期不存在或格式错误！");
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
                if (billingSummaryResp == null || billingSummaryResp.getBizResult() == null) {
                    return null;
                }
                billingSummary=billingSummaryResp.getBizResult();
                billingSummary.setCardNo(cardNo);
                logger.debug("@ZDFQ@调用行内服务根据cardNo[{}]获取当期账单,获取到的当期账单billingSummary[{}]",cardNo,billingSummary);
            } catch (Exception e) {
                logger.warn("查询账单错误：" + e.getMessage());
                return null;
            }
        }
        return billingSummary;
    }

    public AmountLimit getAmountLimit(String cardNo, String currencyCode) {
        Map<String, String> map = initDirectSaleParam();
        map.put("cardNo", cardNo);
        map.put("currencyCode", currencyCode);
        AmountLimitResp amountLimitResp = httpClient.send(getAmountLimit, map, AmountLimitResp.class);
        AmountLimit amountLimit = amountLimitResp == null ? null : amountLimitResp.getBizResult();
        if (amountLimit != null && amountLimit.getRespCode() != null && "".equals(amountLimit.getRespCode())) {
            amountLimit.setMaxAmount(parseString(amountLimit.getMaxAmount()));
            amountLimit.setShowMinAmount(parseString(AmtUtil.procString(amountLimit.getMinAmount())));
        }
        return amountLimit;
    }

    public BillCost getBillCost(String accountId, String accountNo, String currencyCode, String billLowerAmount, String billActualAmount, String installmentsNumber, String feeInstallmentsFlag) {
        Map<String, String> map = initDirectSaleParam();
        map.put("accountId", accountId);
        map.put("accountNumber", accountNo);
        map.put("currencyCode", currencyCode);
        map.put("billLowerAmount", billLowerAmount);
        map.put("billActualAmount", billActualAmount);
        map.put("installmentsNumber", installmentsNumber);
        map.put("feeInstallmentsFlag", feeInstallmentsFlag);
        BillCostResp billCostResp = httpClient.send(queryBillCost, map, BillCostResp.class);
        return billCostResp == null ? null : billCostResp.getBizResult();
    }

    @Transactional
    public boolean billInstallment(String accountId, String accountNo, String cardNo, String currencyCode, String billLowerAmount, String billActualAmount, String installmentsNumber, String feeInstallmentsFlag) {
        boolean returnRes;
        Map<String, String> map = initDirectSaleParam();
        map.put("accountId", accountId);
        map.put("accountNumber", accountNo);
        map.put("currencyCode", currencyCode);
        map.put("billLowerAmount", billLowerAmount);
        map.put("billActualAmount", billActualAmount);
        map.put("installmentsNumber", installmentsNumber);
        map.put("feeInstallmentsFlag", feeInstallmentsFlag);
        String tDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        InstallmentInfo bi = new InstallmentInfo(cardNo, "账单分期", currencyCode, billActualAmount, installmentsNumber, feeInstallmentsFlag, tDate);
        StringResp stringResp = httpClient.send(billInstallment, bi, StringResp.class);
        String resultCode = stringResp == null ? null : stringResp.getBizResult();

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
        } catch (Exception e) {
            logger.error("", e);
        }
        return returnRes;
    }

    @Override
    public String verificationMobileNo(String identityType, String identityNo, String mobileNo) {
        String mobile = getCustMobileNo(identityType, identityNo);
        if (mobile == null) {
            return "exception";
        } else if ("".equals(mobile.trim())) {
            return "noMobileNumber";
        } else if (!mobile.equals(mobileNo)) {
            return "wrongMobilNo";
        }
        return "";
    }


    /**
     * 转换字符串，将金额上下限小数点后两位全部转化为00
     * @param amt 金额
     * @return 返回转换后的金额
     */

    private String parseString(String amt) {
        StringBuilder oldAmt = new StringBuilder(amt);
        StringBuilder newAmt = oldAmt.delete(oldAmt.length() - 2,
                oldAmt.length());
        StringBuilder resultAmt = newAmt.append("00");
        return resultAmt.toString();
    }


}
