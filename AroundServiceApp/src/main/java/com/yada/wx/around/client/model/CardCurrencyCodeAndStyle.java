package com.yada.wx.around.client.model;

import java.util.List;

/**
 * 币种和卡产品类型
 * Created by QinQiang on 2016/5/10.
 */
public class CardCurrencyCodeAndStyle {

    private List<String> currencyCodes; // 币种列表
    private String productType; // 卡片产品类型

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public List<String> getCurrencyCodes() {
        return currencyCodes;
    }

    public void setCurrencyCodes(List<String> currencyCodes) {
        this.currencyCodes = currencyCodes;
    }
}
