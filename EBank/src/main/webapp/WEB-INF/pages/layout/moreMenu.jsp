<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <%@ include file="../base_pages/base.jsp" %>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/moreMenu.css"/>"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/BlackTip.css"/>"/>
    <title>更多功能</title>
</head>

<body>
<!--moreMenu-->
<div class="Menu_content">

    <a href="${ctx}/billingsummary/list.do" class="menu"><span class="index3 pageLink">账单查询</span></a>
    <a href="${ctx}/balance/list.do" class="menu"><span class="index4 pageLink">额度查询</span></a>
    <a href="${ctx}/points/list.do" class="menu"><span class="index2 pageLink">积分查询</span></a>
    <a href="${cardApply}" class="menu"><span class="index5 pageLink">我要办卡</span></a>
    <a href="${ctx}/cardapply/list.do" class="menu"><span class="index6 pageLink">办卡进度</span></a>
    <a href="#" class="menu"><span class="index7 pageLink">开卡激活</span></a>
    <a href="${activity}" class="menu"><span class="index8 pageLink">活动报名</span></a>
</div>
<!--wait_box -->
<div class="wait_box">
    <div class="tip_box">
        <img src="images/Loading2.gif"/>
        <span class="wait_text">loading</span>
    </div>
</div>

</body>
</html>