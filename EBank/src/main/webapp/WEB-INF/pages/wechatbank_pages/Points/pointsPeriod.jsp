<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <%@include file="../../base_pages/base.jsp"%>
    <%@include file="../../base_pages/wxjs.jsp"%>
    <%@include file="../../base_pages/wxReadyFunction.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name=" format-detection" content= "telephone=no" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <link href="<c:url value="/css/PointFinitePeriod.css"/>" rel="stylesheet" type="text/css">

    <link href="<c:url value="/css/DefaultStyle.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/css/BlackTip.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/css/fixedDetails.css"/>" rel="stylesheet" type="text/css">
    <title>积分详情</title>
</head>
<body>
<div class="PointFinitePeriod_content">
    <div class="PointFinitePeriod_title">积分有效期</div>
    <!-- 不存在积分 -->
    <c:if test="${fn:length(pointsValidatesList) eq 0}">
        <div class="IntegrationContainer">
            <div id="Integration_box">
                <div class="myPoint_box">
                    <div class="myPoint_view">
                        <img src="../images/points/no.png" class="myPointImg"/>
                        <span class="myPointNull">很抱歉！没有查询到您的积分到期日。详情请咨询中国银行信用卡24小时客服热线4006695566。感谢您对中行信用卡的支持和厚爱！</span>
                    </div>
                </div>
            </div>
        </div>
    </c:if>
    <!-- 存在积分 -->
    <c:if test="${fn:length(pointsValidatesList) gt 0}">
        <c:forEach items="${pointsValidatesList}" var="item" varStatus="status">
            <div class="dynamic_box">
            <div class="productName">
                <span class="name">信用卡号：</span>
                <span class="dynamicData">
                     <c:out value="${fn:substring(item.cardNo,0,4)}" />********<c:out value="${fn:substring(item.cardNo,fn:length(item.cardNo)-4,fn:length(item.cardNo))}" />
                </span>
            </div>
            <div class="productName">
                <span class="name">积分余额：</span>
                <span class="dynamicData" ><c:out value='${item.totalPoint}' /></span>
            </div>
            <div class="productName">
                <span class="name">积分到期日：</span>
                <span class="dynamicData" ><c:out value='${item.pointExpireDate}' /></span>
            </div>
            </div>
        </c:forEach>
    </c:if>
</div>
</body>
</html>