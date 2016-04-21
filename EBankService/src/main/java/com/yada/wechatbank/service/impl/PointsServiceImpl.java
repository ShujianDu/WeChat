package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.HttpClient;
import com.yada.wechatbank.client.model.PointsBalanceResp;
import com.yada.wechatbank.client.model.PointsValidatesResp;
import com.yada.wechatbank.model.PointsBalance;
import com.yada.wechatbank.model.PointsDetail;
import com.yada.wechatbank.client.model.PointsDetailResp;
import com.yada.wechatbank.model.CardInfo;
import com.yada.wechatbank.model.PointsValidates;
import com.yada.wechatbank.service.PointsService;
import com.yada.wx.db.service.dao.CustomerInfoDao;
import com.yada.wx.db.service.model.CustomerInfo;
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

    @Autowired
    private CustomerInfoDao customerInfoDao;
    @Autowired
    private HttpClient httpClient;
    @Value("${url.getPointsDetails}")
    private String getPointsDetails;
    @Value("${url.getPointsValidates}")
    private String getPointsValidates;
    @Value("${url.getBalance}")
    private String getBalance;

    /**
     * 获取账户积分余额
     * @param identityNo 证件号
     * @param identityType 证件类型
     * @return
     */
    @Override
    public PointsBalance getPointsBlance(String identityNo, String identityType) {
        String cardNo = "";
        //通过证件号和证件类型去后台查询卡号
        List<CardInfo> cardInfoList = selectCardNos(identityNo,identityType);
        if (cardInfoList!=null && cardInfoList.size()!=0){
            cardNo=cardInfoList.get(0).getCardNo();
        }
        Map<String,String> map = new HashMap<>();
        map.put("cardNo",cardNo);
        PointsBalanceResp pointsBlanceResp = httpClient.send("getBalance",map,PointsBalanceResp.class);
        return pointsBlanceResp.getPointsBlance();
    }

    /**
     * 获取积分明细
     * @param identityNo 证件号
     * @param identityType 证件类型
     * @return
     */
    @Override
    public List<PointsDetail> getPointsDetail(String identityNo,String identityType) {
        String cardNo ="";
        //通过证件号和证件类型去后台查询卡号
        List<CardInfo> cardInfoList = selectCardNos(identityNo,identityType);
        if (cardInfoList!=null && cardInfoList.size()!=0){
            cardNo=cardInfoList.get(0).getCardNo();
        }
        //查询积分明细
        Map<String,String> map = new HashMap<>();
        map.put("cardNo",cardNo);
        PointsDetailResp pointsDetailResp = httpClient.send(getPointsDetails,map,PointsDetailResp.class);
        List<PointsDetail> pointsList = null;
        if(pointsDetailResp!=null){
            pointsList = pointsDetailResp.getBizResult();
        }
        return pointsList;
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
     * @param cardNo 卡号
     * @return
     */
    @Override
    public List<PointsValidates> getPointsValidates(String cardNo) {
        Map<String,String> map = new HashMap<>();
        map.put("cardNo",cardNo);
        //调用后台获取积分到期日
        PointsValidatesResp pointsValidatesResp = httpClient.send(getPointsValidates,map,PointsValidatesResp.class);
        return pointsValidatesResp.getBizResult();
    }

}
