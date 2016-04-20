package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.BillingPeriodResp;
import com.yada.wechatbank.model.*;
import com.yada.wechatbank.service.BillInstallmentService;
import com.yada.wechatbank.util.Crypt;
import com.yada.wx.db.service.dao.InstallmentInfoDao;
import com.yada.wx.db.service.model.InstallmentInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BillInstallmentServiceImpl extends BaseService implements BillInstallmentService {
    private final Logger logger = LoggerFactory
            .getLogger(this.getClass());

    @Value(value = "${url.getCurrentPeriodBillMethod}")
    private String getCurrentPeriodBillMethod;
    @Value(value = "${url.getBillingPeriodMethod}")
    private String getBillingPeriodMethod;
    @Value(value = "${url.getAmountLimitMethod}")
    private String getAmountLimitMethod;
    @Value(value = "${url.queryBillCostMethod}")
    private String queryBillCostMethod;
    @Value(value = "${url.billInstallmentMethod}")
    private String billInstallmentMethod;

    @Autowired
    private InstallmentInfoDao installmentInfoDao;

    @Override
    public List<CardInfo> getProessCardNoList(String identityType, String identityNo) {
        List<CardInfo> cardList = selectCardNos(identityType,identityNo);
        if (cardList != null || cardList.size() != 0) {
            try {
                for(CardInfo c:cardList)
                {
                    c.setCardNo(Crypt.cardNoOneEncode(c.getCardNo()));
                }
                return cardList;
            } catch (Exception e) {
                logger.error("@WDZD@�����Ž���չʾ����ʱ���ִ���[" + cardList
                        + "]", e);
            }
        }
        return cardList;
    }

    @Override
    public BillingSummary getCurrentPeriodBill(String cardNo) {
        List<BillingPeriod> usableBillPeriods = new ArrayList<>();
        BillingSummary billingSummary = null;
        // ��ѯ��Ƭ����
        List<BillingPeriod> billingPeriods = httpClient.send(getCurrentPeriodBillMethod, cardNo, List.class);

        // ѭ���������ҿ�������
        for (BillingPeriod temp : billingPeriods) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MONTH, -1);
            try {
                Date endDate = df.parse(temp.getPeriodEndDate());
                if (calendar.getTime().getTime() > endDate.getTime()) {
                    throw new RuntimeErrorException(null, "�˵����ڲ����������ڣ�");
                }
            } catch (Exception e) {
                logger.error("�˵����ڲ����ڻ��ʽ����", e);
                throw new RuntimeErrorException(null, "�˵����ڲ����ڻ��ʽ����");
            }

            if (temp.getCurrencyCode().equalsIgnoreCase("�����")) {
                usableBillPeriods.add(temp);
                break;
            }
        }
        // �������ڲ�ѯ
        for (BillingPeriod temp: usableBillPeriods) {
            try {
                Map<String, String> map = initGcsParam();
                map.put("statementNo", temp.getStatementNo());
                map.put("accountId", temp.getAccountId());
                billingSummary = httpClient.send(getCurrentPeriodBillMethod, map, BillingSummary.class);
                billingSummary.setCardNo(cardNo);
                logger.debug("@ZDFQ@�������ڷ������cardNo[" + cardNo
                        + "]��ȡ�����˵�,��ȡ���ĵ����˵�billingSummary[" + billingSummary + "]");
            } catch (Exception e) {
                logger.warn("��ѯ�˵�����" + e.getMessage());
                return null;
            }
        }
        return billingSummary;
    }

    public AmountLimit getAmountLimit(String cardNo, String currencyCode) {
        Map<String, String> map = new HashMap<>();
        map.put("cardNo", cardNo);
        map.put("currencyCode", currencyCode);
        AmountLimit amountLimit = httpClient.send(getAmountLimitMethod, map, AmountLimit.class);
        if (amountLimit != null && amountLimit.getRespCode() != null && "".equals(amountLimit.getRespCode())) {
            amountLimit.setMaxAmount(parseString(amountLimit.getMaxAmount()));
            amountLimit.setShowMinAmount(parseString(amountLimit.getShowMinAmount()));
        }

        return amountLimit;

    }

    public BillCost getBillCost(String accountId, String accountNo, String currencyCode, String billLowerAmount, String billActualAmount, String installmentsNumber, String feeInstallmentsFlag) {
        Map<String, String> map = initDirectSaleParam();
        map.put("accountId", accountId);
        map.put("accountNo", accountNo);
        map.put("currencyCode", currencyCode);
        map.put("billLowerAmount", billLowerAmount);
        map.put("billActualAmount", billActualAmount);
        map.put("installmentsNumber", installmentsNumber);
        map.put("feeInstallmentsFlag", feeInstallmentsFlag);
        return httpClient.send(queryBillCostMethod, map, BillCost.class);
    }

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
        map.put("installmentPlanID", "BI01");
        String tDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        InstallmentInfo bi = new InstallmentInfo(cardNo, "�˵�����", currencyCode, billActualAmount, installmentsNumber, feeInstallmentsFlag, tDate);
        String resultCode = httpClient.send(billInstallmentMethod, bi, String.class);
        if (resultCode != null) {
            bi.setGcsCode(resultCode);
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
            logger.error("",e);
        }
        return returnRes;
    }


        /**
         * ת���ַ����������������С�������λȫ��ת��Ϊ00
         * @param amt  ���
         * @return ����ת����Ľ��
         */

    private String parseString(String amt) {
        StringBuffer oldAmt = new StringBuffer(amt);
        StringBuffer newAmt = oldAmt.delete(oldAmt.length() - 2,
                oldAmt.length());
        StringBuffer resultAmt = newAmt.append("00");
        return resultAmt.toString();
    }


}
