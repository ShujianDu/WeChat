package com.yada.wechatbank.service.impl;

import com.yada.wechatbank.base.BaseService;
import com.yada.wechatbank.client.model.CardApplyResp;
import com.yada.wechatbank.model.CardApplyList;
import com.yada.wechatbank.service.CardApplyService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 预约办卡进度查询
 * Created by QinQiang on 2016/4/11.
 */
@Service
public class CardApplyServiceImpl extends BaseService implements CardApplyService {

    @Override
    public CardApplyList getCrdCardSchedule(String name, String identityType, String identityNo, int currentPage) {

        // 调用行内SAS获取JSON数据
        Map<String, String> param = initDirectSaleParam();
        param.put("name", name);
        param.put("idType", identityType);
        param.put("id", identityNo);
        param.put("currentPage", "" + currentPage);

        CardApplyResp cardApplyResp = httpClient.send(cardApplyUrl, param, CardApplyResp.class);
        return cardApplyResp == null ? null : cardApplyResp.getBizResult();
    }

}
