package com.yada.wechatbank.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Http客户端
 * Created by QinQiang on 2016/4/12.
 */
public class HttpClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private int conTimeout; // 连接超时时间
    private int readTimeout; // 读取超时时间

    private String hostAddr;

    public HttpClient(String hostAddr, int conTimeout, int readTimeout) {
        this.hostAddr = hostAddr;
        this.conTimeout = conTimeout;
        this.readTimeout = readTimeout;
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
        try {
            String data = JSON.toJSONString(object);
            String respStr = postRequest(method, data);
            result = JSON.parseObject(respStr, targetClass);
        } catch (JSONException e) {
            logger.error("数据转换异常", e);
        } catch (Exception e) {
            logger.error("HttpClient通讯时发生错误", e);
        } finally {
            return result;
        }
    }

    /**
     * POST请求
     *
     * @param data 请求数据
     * @return String
     */
    private String postRequest(String method, String data) {

        StringBuffer sb = new StringBuffer();
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
                logger.info("通讯异常,响应码[" + conn.getResponseCode() + "]");
                throw new RuntimeException("通讯异常,响应码[" + conn.getResponseCode() + "]");
            }
        } catch (IOException e) {
            logger.error("HttpClient通讯异常:", e);
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
        return sb.toString();
    }
}
