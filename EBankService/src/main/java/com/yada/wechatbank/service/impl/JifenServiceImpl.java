package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.model.JifenDetail;
import com.yada.wechatbank.model.JifenValidate;
import com.yada.wechatbank.service.JifenService;
import com.yada.wx.db.service.dao.CustomerInfoDao;
import com.yada.wx.db.service.model.CustomerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Echo on 2016/4/12.
 */
@Service
public class JifenServiceImpl implements JifenService{

    @Autowired
    private CustomerInfoDao customerInfoDao;
    @Override
    public List<JifenDetail> getJifenDetail(String identityNo,String identityType) {
        String cardNo ="";
        //通过证件号去数据库查询默认卡
        List<CustomerInfo> cusList = customerInfoDao.findByIdentityNo(identityNo);
        if(cusList!=null && cusList.size()!=0){
            cardNo=cusList.get(0).getDefCardNo();
        }else{
            //TODO 通过证件号和证件类型去后台查询卡号
        }
        //TODO 获取EcifNo
        String ecifNo = "";
        // 没有拿到EcifNo，则返回List
        if (ecifNo == null || ecifNo.equals("")) {
            return new ArrayList<JifenDetail>();
        }
        List<JifenDetail> list = null;
        //TODO 查询积分明细
        return list;
    }

    @Override
    public List<List<JifenDetail>> getList(List<JifenDetail> list) {
        List<List<JifenDetail>> newList = new ArrayList<List<JifenDetail>>();
        for (int i = 0; i < list.size(); i++) {
            // 没有父类ID即为父类积分明细
            if (list.get(i).getParentId() == null
                    || "".equals(list.get(i).getParentId())) {
                List<JifenDetail> detailList = new ArrayList<JifenDetail>();
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

    @Override
    public List<JifenValidate> getJifenValidates(String cardNo) {
        //TODO 获取EcifNo
        String ecifNo = "";
        // 没有拿到EcifNo，则返回List
        if (ecifNo == null || ecifNo.equals("")) {
            return new ArrayList<JifenValidate>();
        }
        List<JifenValidate> list = null;
        //TODO 调用后台获取积分到期日
        return list;
    }

}
