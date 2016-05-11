package com.yada.wechatbank.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yada.wechatbank.base.BaseModel;
import com.yada.wechatbank.exception.CommunicationException;
import com.yada.wechatbank.kafka.MessageProducer;
import com.yada.wechatbank.kafka.TopicEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;


/**
 * Http客户端
 * Created by QinQiang on 2016/4/12.
 */
public class HttpClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private int conTimeout; // 连接超时时间
    private int readTimeout; // 读取超时时间
    private MessageProducer messageProducer;

    private Set<String> error_code;

    private String hostAddr;

    private String title;

    public HttpClient(String hostAddr, int conTimeout, int readTimeout,MessageProducer messageProducer,String title) {
        error_code= new HashSet<>();
        error_code.add("01");
        error_code.add("99");
        this.hostAddr = hostAddr;
        this.conTimeout = conTimeout;
        this.readTimeout = readTimeout;
        this.messageProducer=messageProducer;
        this.title = title;
    }

    /**
     * 发送POST请求
     *
     * @param object      发送对象
     * @param targetClass 返回对象类型
     * @return Object
     */
    public <T> T send(String method, Object object, Class<T> targetClass) {
        T result = null;
        StackTraceElement[] s = new Exception().getStackTrace();
        logger.info("模块[{}]调用[{}]行内接口，传入参数为[{}]", s[1].getClassName(), method, object.toString());
        StringBuffer message = new StringBuffer();
        try {
            String data = JSON.toJSONString(object);
            String respStr = postRequest(method, data);
            result = JSON.parseObject(respStr, targetClass);
            BaseModel baseMode=(BaseModel)result;

            message.append("datetime").append(new SimpleDateFormat("yyyyMMddHHmmss")).append("title").append(title).append("msg");

            if(error_code.contains(baseMode.getReturnCode()))
            {
                message.append("行内服务返回异常,响应码[").append(baseMode.getReturnCode()).append("]，响应信息[").append(baseMode.getReturnMsg()).append("]");
                logger.error(message.toString());
                messageProducer.send(TopicEnum.EXCEPTION, "httpClientCommunicationReturnFailedCode", JSONObject.toJSONString(message));
                throw new CommunicationException(message.toString());
            }
            logger.info("模块[{}]调用[{}]行内接口，返回参数为[{}]", s[1].getClassName(), method, respStr);
            return result;
        } catch (JSONException e) {
            logger.error("HttpClient 数据转换异常", e);
            return result;
        } catch (Exception e) {
            logger.error("HttpClient 通讯时发生错误", e);
            messageProducer.send(TopicEnum.EXCEPTION, "httpClientCommunicationReturnFailedCode", "与行内服务通过HttpClient 通讯时发生错误");
            throw new CommunicationException("与行内服务通过HttpClient 通讯时发生错误",e);
        }
    }

    /**
     * POST请求
     *
     * @param data 请求数据
     * @return String
     */
    private String postRequest(String method, String data) {
        StringBuilder sb = new StringBuilder();
        HttpURLConnection conn = null;
        OutputStreamWriter writer = null;
        BufferedReader bufferedReader = null;
        try {
            URL url = new URL(hostAddr + method);
            conn = (HttpURLConnection) url.openConnection();
            // 发送Post强求，开启其读写的功能
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 发送post请求
            conn.setRequestMethod("POST");
            // Post请求不能使用缓存
            conn.setUseCaches(false);
            // 设置数据格式
            conn.setRequestProperty("Content-type", "application/json;charset=UTF-8");
            conn.setRequestProperty("accept", "application/json;charset=utf-8");
            // 设置超时时间
            conn.setReadTimeout(readTimeout);
            conn.setConnectTimeout(conTimeout);
            // 发送Post请求
            conn.connect();
            // 获得输出流
            writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            // 发送参数
            writer.write(data);
            writer.flush();
            // 读取响应,响应吗等于200，请求发送成功
            if (conn.getResponseCode() == 200) {
                // 从链接中获取一个输入流对象
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    sb.append(str);
                }
                return sb.toString();
            } else {
                String msg="与行内服务SAS 通讯异常,响应码[\" + conn.getResponseCode() + \"]";
                logger.error(msg);
                messageProducer.send(TopicEnum.EXCEPTION, "httpClientConnectSASException", msg);
                throw new CommunicationException("msg");
            }
        } catch (IOException e) {
            logger.error("与行内服务SAS 通讯异常:", e);
            messageProducer.send(TopicEnum.EXCEPTION, "httpClientConnectSASException", "与行内服务SAS 通讯异常:" + e.getMessage());
            throw new CommunicationException("与行内服务SAS 通讯异常:",e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}
