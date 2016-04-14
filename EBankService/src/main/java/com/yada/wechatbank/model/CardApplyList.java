package com.yada.wechatbank.model;

import com.yada.wechatbank.base.BaseModel;

import java.util.List;

/**
 * 预约办卡查询返回信息
 * Created by QinQiang on 2016/4/11.
 */
public class CardApplyList extends BaseModel {

    // 是否有下一页
    private Boolean next;

    private List<CardApply> applyList;

    /**
     * 是否有下一页
     *
     * @return true-有 false-没有
     */
    public Boolean hasNext() {
        return next;
    }

    public Boolean getNext() {
        return next;
    }

    public void setNext(Boolean next) {
        this.next = next;
    }

    public List<CardApply> getApplyList() {
        return applyList;
    }

    public void setApplyList(List<CardApply> applyList) {
        this.applyList = applyList;
    }

    @Override
    public String toString() {
        return "CardApplyList [next=" + next + ", applyList=" + applyList + "]";
    }
}
