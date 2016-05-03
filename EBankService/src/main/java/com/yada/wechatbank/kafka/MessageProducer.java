package com.yada.wechatbank.kafka;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * kafka事件生产者公用类
 * Created by Tx on 2016/5/3.
 */
public class MessageProducer {

    private final Logger logger = LoggerFactory
            .getLogger(this.getClass());

    // 0：生产者不等待响应；1：生产者等待leader写入本地日志；all：生产者等待leader同步
    public final String acks ="all";
    public final String retries="0";
    public final String batchSize="16384";
    public final String lingerMs="1";
    public final String bufferMemory="33554432";
    public final String keySerializer="org.apache.kafka.common.serialization.StringSerializer";
    public final String valueSerializer="org.apache.kafka.common.serialization.StringSerializer";
    private KafkaProducer<String,String> kafkaProducer;
    private SimpleDateFormat df ;


    public MessageProducer(String bootstrapServers)
    {
        Properties props= new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("acks", acks);
        props.put("retries", retries);
        props.put("batch.size", batchSize);
        props.put("linger.ms", lingerMs);
        props.put("buffer.memory", bufferMemory);
        props.put("key.serializer", keySerializer);
        props.put("value.serializer", valueSerializer);
        kafkaProducer=new KafkaProducer<>(props);
        df= new SimpleDateFormat("yyyyMMddHHmmss");
    }

    /**
     *  @param topic       模块名称-action 异常(exception）操作（ebankDo）查询（ebankQuery）
     * @param key         业务名称（请开发人员将业务值记录到字典表中）
     * @param message     具体业务传输数据
     */
    public void send(TopicEnum topic ,String key ,Object message ) {
        MessageData m=new MessageData(df.format(new Date()),message);
        String data= JSON.toJSONString(m);
        kafkaProducer.send(new ProducerRecord<>(topic.getValue(), key, data));
    }


    public void close()
    {
        try {
            kafkaProducer.close();
        } catch (Exception e) {
            logger.error("kafka生产者服务关闭错误",e);
        }
    }

}
