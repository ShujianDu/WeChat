package com.yada.wechatbank.kafka;

import com.yada.wechatbank.kafka.MessageProducer;
import com.yada.wechatbank.kafka.TopicEnum;

/**
 * kafka事件推送mock测试类
 * Created by Tx on 2016/5/4.
 */
public class MyMessageProducer extends MessageProducer {


    public MyMessageProducer()
    {
        super("22.7.16.109:9090");
    }

    @Override
    public void send(TopicEnum topic, String key, Object message) {

    }
}
