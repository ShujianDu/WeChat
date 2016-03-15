package com.yada.gcs;

import cn.com.boc.gcs.Gateway;
import cn.com.boc.gcs.GatewayException_Exception;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by locky on 2016/3/15.
 */
public class Main {
    private static String msg = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<GCS transactionID=\"TS011172\" isRequest=\"ture\" isResponse=\"false\">\n" +
            "  <system>\n" +
            "    <prop key=\"transactionCode\" value=\"011172\" />\n" +
            "    <prop key=\"transactionNumber\" value=\"201603151648550020160315164855\" />\n" +
            "    <prop key=\"transactionSessionId\" value=\"93c4399ad67d925fa40d0693adb0a222\" />\n" +
            "    <prop key=\"requestChannelId\" value=\"WX01\" />\n" +
            "    <prop key=\"txnBankCode\" value=\"003\" />\n" +
            "    <prop key=\"txnBranchCode\" value=\"00003\" />\n" +
            "    <prop key=\"txnProvinceCode\" value=\"\" />\n" +
            "    <prop key=\"txnTerminalCode\" value=\"\" />\n" +
            "    <prop key=\"txnCounterCode\" value=\"\" />\n" +
            "    <prop key=\"txnUserCode\" value=\"wx000000\" />\n" +
            "  </system>\n" +
            "  <page key=\"RQ011172\">\n" +
            "    <prop key=\"accountKeyOne\" value=\"1111111111111111\" />\n" +
            "    <prop key=\"accountKeyTwo\" value=\"1111111111111111\" />\n" +
            "    <prop key=\"currencyCode\" value=\"CNY\" />\n" +
            "    <prop key=\"transactionCode\" value=\"3110\" />\n" +
            "    <prop key=\"billDateNo\" value=\"111111111\" />\n" +
            "    <prop key=\"transactionNo\" value=\"111111111\" />\n" +
            "    <prop key=\"transactionAmount\" value=\"5555\" />\n" +
            "    <prop key=\"cardNo\" value=\"4693801111405010\" />\n" +
            "    <prop key=\"accountNoID\" value=\"11111111111111111111\" />\n" +
            "    <prop key=\"mcc\" value=\"5311\" />\n" +
            "    <prop key=\"installmentPeriods\" value=\"3\" />\n" +
            "    <prop key=\"installmentPlan\" value=\"EP01\" />\n" +
            "    <prop key=\"merchantID\" value=\"0000000\" />\n" +
            "    <prop key=\"isfeeFlag\" value=\"1\" />\n" +
            "    <prop key=\"channelId\" value=\"A\" />\n" +
            "  </page>\n" +
            "</GCS>";
    public static void main(String[] args) throws MalformedURLException, GatewayException_Exception {
        URL wsdlURL = new URL("http://22.7.14.91/tps1/ws/gateway?wsdl");
        QName SERVICE_NAME = new QName("www.boc.com.cn/gcs", "GcsGatewayService");

        Service service = Service.create(wsdlURL, SERVICE_NAME);
        Gateway client = service.getPort(Gateway.class);
        String result = client.service(msg);
        System.out.println(result);
    }

}
