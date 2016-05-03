package com.yada.wechatbank.kafka;

/**
 * Created by Tx on 2016/5/3.
 */
public class MessageData {

    //数据产生时间
    private String datetime;
    //数据
    private Object data;

    public MessageData(String datetime,Object data)
    {
        this.datetime=datetime;
        this.data=data;
    }

    public void setDatetime(String datetime){
        this.datetime=datetime;
    }
    public String getDatetime()
    {
        return this.datetime;
    }

    public void setData(Object data){
        this.data=data;
    }
    public Object getData()
    {
        return this.data;
    }
}
