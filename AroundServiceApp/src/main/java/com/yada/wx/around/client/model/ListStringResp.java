package com.yada.wx.around.client.model;

import java.util.List;

/**
 * 返回String通用实体
 *
 * @author Tx
 */
public class ListStringResp extends BaseModel {

    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
