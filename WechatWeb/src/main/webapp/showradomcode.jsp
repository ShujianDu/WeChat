<%@ page language="java" contentType="image/jpeg" pageEncoding="GBK" import="java.awt.image.*,javax.imageio.*" %>
<jsp:useBean id="rc" class="com.yada.wechatbank.util.RandomChar" scope="page"/>
<%
    //设置页面不缓存

    response.reset();
    response.setContentType("image/jpeg");
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
//在内存中创建图象宽60 高18 4个字符 0条干扰线 
    BufferedImage image = rc.getRandomImage(58, 25, 4, 1);
//设置其session值 
    session.setAttribute("jcmsrandomchar", rc.getRandomString());

    ImageIO.write(image, "JPEG", response.getOutputStream());
    response.flushBuffer();
//response.reset();
    out.clear();
    out = pageContext.pushBody();
%> 
