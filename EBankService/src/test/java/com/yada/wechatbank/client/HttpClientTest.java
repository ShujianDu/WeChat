package com.yada.wechatbank.client;

import org.junit.Test;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试HttpClient
 * Created by QinQiang on 2016/4/12.
 */
public class HttpClientTest {

    @Test
    public static void main(String[] args) {
        try {
            String reqUrl = "http://localhost/EBank/cardapply/getJson.do";
            int conTimeout = 10000;
            int readTimeout = 10000;

            HttpClient httpClient = new HttpClient(reqUrl, conTimeout, readTimeout);
            Map<String, String> map = new HashMap<>();
            map.put("key1", "value1");
            map.put("key2", "value2");
            map.put("key3", "value3");

            Map<String, String> respMap = httpClient.send(map, map.getClass());
            System.out.print(respMap.keySet());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
