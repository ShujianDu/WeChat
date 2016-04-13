package com.yada.wechatbank.model;

/**
 * 与行内系统交互返回JSON转换实体Base
 * Created by QinQiang on 2016/4/13.
 */
public class BaseModel {

    private String respCode; // 响应码
    private String respMsg; // 响应信息

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }
}
