package com.yada.wx.around.client.model;

/**
 * 与行内系统交互返回JSON转换实体Base
 * Created by QinQiang on 2016/4/13.
 */
public class BaseModel {

    private String returnCode; // 响应码
    private String returnMsg; // 响应信息

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }
}
