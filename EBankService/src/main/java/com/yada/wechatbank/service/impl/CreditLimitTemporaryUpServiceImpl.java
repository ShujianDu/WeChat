package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.*;
import com.yada.wechatbank.model.*;
import com.yada.wechatbank.service.BillInstallmentService;
import com.yada.wechatbank.service.CreditLimitTemporaryUpService;
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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 临时提升额度service实现
 * @author Tx
 */
@Service
public class CreditLimitTemporaryUpServiceImpl extends BaseService implements CreditLimitTemporaryUpService {
    private final Logger logger = LoggerFactory
            .getLogger(this.getClass());

    // 信用卡提额的尝试次数
    private AtomicInteger creditLimitUpTimes = new AtomicInteger(0);
    @Value("url.getCardHolderInfoMethod")
    private String getCardHolderInfoMethod;
    @Value("${url.temporaryUpCommitMethod}")
    private String temporaryUpCommitMethod;
    @Value("${url.creditLimitTemporaryUpReviewMethod}")
    private String creditLimitTemporaryUpReviewMethod;
    @Value("${url.getTemporaryUpCommitStatusMethod}")
    private String getTemporaryUpCommitStatusMethod;

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
    public Boolean temporaryUpCommit( String certType, String certNum, String cardNo, String eosPreAddLimit, String eosStarLimitDate, String eosEndLimitDate, String cardStyle, String issuingBranchId, String pmtCreditLimit) {
        String date = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
        int times = creditLimitUpTimes.getAndIncrement();
        if (times >= 100000) {
            creditLimitUpTimes.set(0);
            times = creditLimitUpTimes.getAndIncrement();
        }
        Map<String,String> mapCardHolderInfo=initDirectSaleParam();
        mapCardHolderInfo.put("cardNo",cardNo);

        //获取用户姓名
        CardHolderInfo cardHolderInfo = httpClient.send(getCardHolderInfoMethod,mapCardHolderInfo , CardHolderInfoResp.class).getBizResult();

        if(cardHolderInfo == null){ return null;}

        //TODO 问王玉是否可使用该接口手机号
        String phoneNumber=cardHolderInfo.getMobileNo();
        String eosCustomerName=cardHolderInfo.getFamilyName();
        String eosID = String.format("0602%s%05d", date, times);
        //暂时只可提升人民币
        String eosCurrency="CNY";
        Map<String,String> map=initDirectSaleParam();
        map.put("eosID",eosID);
        map.put("eosCustomerName",eosCustomerName);
        map.put("eosCustomerIdType",  idTypeChange(certType));
        map.put("certNum",certNum);
        map.put("phoneNumber",phoneNumber);
        map.put("eosCurrency",eosCurrency);
        map.put("cardNo",cardNo);
        map.put("eosPreAddLimit",eosPreAddLimit);
        map.put("eosStarLimitDate",eosStarLimitDate);
        map.put("eosEndLimitDate",eosEndLimitDate);
        map.put("cardStyle",cardStyle);
        map.put("issuingBranchId",issuingBranchId);
        map.put("pmtCreditLimit",pmtCreditLimit);
        Boolean result = httpClient.send(temporaryUpCommitMethod,map, BooleanResp.class).getBizResult();
        return result == null ? false : result;
    }

    @Override
    public CreditLimitTemporaryUpReview getAmount(String certType, String certNum,  String cardNo) {
        String mobileNo=getCustMobileNo(certType,certNum);
        if(mobileNo==null||"".equals(mobileNo))
        {
            return null;
        }
        Map<String,String> map=initGcsParam();
        map.put("certType",  idTypeChange(certType));
        map.put("certNum",  certNum);
        map.put("cardNo",  cardNo);
        map.put("phoneNumber",  mobileNo);
        CreditLimitTemporaryUpReview c=httpClient.send(creditLimitTemporaryUpReviewMethod,map,CreditLimitTemporaryUpReviewResp.class).getBizResult();
        return c;
    }

    @Override
    public List<CreditLimitTemporaryUpStatus> getLimitUpHistory(String certType, String certNum, String cardNo) {
        Map<String,String> map=initGcsParam();
        map.put("certType",idTypeChange(certType));
        map.put("certNum",certNum);
        map.put("cardNo",cardNo);
        return httpClient.send(getTemporaryUpCommitStatusMethod,map,CreditLimitTemporaryUpStatusResp.class).getBizResult();
    }

    /**
     * 将证件号类型转换为gcs系统需要的类型
     *
     * @param certType   证件类型
     * @return
     */
    private String idTypeChange(String certType) {
        String eosCustomerIdType = "";
        int type = Integer.valueOf(certType);
        switch (type) {
            case 1:
                eosCustomerIdType = "IDCD";
                break;
            case 2:
                eosCustomerIdType = "TPID";
                break;
            case 3:
                eosCustomerIdType = "SSNO";
                break;
            case 4:
                eosCustomerIdType = "RSBL";
                break;
            case 5:
                eosCustomerIdType = "OFFR";
                break;
            case 6:
                eosCustomerIdType = "WJID";
                break;
            case 8:
                eosCustomerIdType = "DPID";
                break;
            case 9:
                eosCustomerIdType = "FRPM";
                break;
            case 10:
                eosCustomerIdType = "BRPA";
                break;
            case 11:
                eosCustomerIdType = "OTHC";
                break;
            case 47:
                eosCustomerIdType = "HKID";
                break;
            case 48:
                eosCustomerIdType = "MCID";
                break;
            case 49:
                eosCustomerIdType = "TWID";
                break;
        }
        return eosCustomerIdType;
    }


}
