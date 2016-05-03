package com.yada.wechatbank.kafka;

/**
 * topic枚举
 * Created by Tx on 2016/5/3.
 */
public enum TopicEnum {
    EXCEPTION("exception"),
    EBANK_DO("ebankDo"),
    EBANK_QUERY("ebankQuery");


    private final String value;

    TopicEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
