<%@ page language="java" contentType="image/jpeg" pageEncoding="GBK" import="java.awt.image.*,javax.imageio.*" %>
<jsp:useBean id="rc" class="com.yada.wechatbank.util.RandomChar" scope="page"/>
<%
    //����ҳ�治����

    response.reset();
    response.setContentType("image/jpeg");
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
//���ڴ��д���ͼ���60 ��18 4���ַ� 0�������� 
    BufferedImage image = rc.getRandomImage(58, 25, 4, 1);
//������sessionֵ 
    session.setAttribute("jcmsrandomchar", rc.getRandomString());

    ImageIO.write(image, "JPEG", response.getOutputStream());
    response.flushBuffer();
//response.reset();
    out.clear();
    out = pageContext.pushBody();
%> 
