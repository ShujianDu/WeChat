<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <%@ include file="../base_pages/base.jsp" %>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/DefaultStyle.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/fixedPage.css"/>"/>
    <title>中国银行<sitemesh:write property='title'/></title>
    <sitemesh:write property='head'/>
</head>

<body>
<!--header-->
<div class="header">
    <img class="header_logo" src="<c:url value="/images/logo.png"/>"/>
    <input class="header_menu" type="button" style="background-color: transparent;">
</div>
<%@ include file="menu.jsp" %>
<sitemesh:write property='body'/>
<!--bottom-->
<div class="bottom_box1">
    <span class="hot_line">信用卡热线:(+86)40066-95566</span>
    <span class="hot_line">中国银行&nbsp;&nbsp;版权所有</span>
</div>
</body>
</html>