package com.yada.wechatbank.model;

import java.util.List;

/**
 * 预约办卡查询返回信息
 * Created by QinQiang on 2016/4/11.
 */
public class CardApplyList {

    // 是否有下一页
    private Boolean hasNext;

    private List<CardApply> cardApplies;

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<CardApply> getCardApplies() {
        return cardApplies;
    }

    public void setCardApplies(List<CardApply> cardApplies) {
        this.cardApplies = cardApplies;
    }
}
