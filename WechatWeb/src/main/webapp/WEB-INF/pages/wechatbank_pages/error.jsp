<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../base_pages/base.jsp"%>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>信用卡微信客服</title>
<%--<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/wechatbank/index.css"/>" />--%>

	<link rel="stylesheet" type="text/css" href="<c:url value="/css/UpgradeYES.css"/>" rel="stylesheet" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/DefaultStyle.css"/>" rel="stylesheet" />
	<script src="<c:url value="/js/UpgradeYES.js"/>" type="text/javascript"></script>
</head>
<body>
	<div  class="UpgradeYES_content">

		<div class="dynamic_box">
			<img src="<c:url value="/images/errorIcon.png"/>" class="imgyes">
             <c:if test="${null eq msg}">
			<span class="textYes">抱歉：系统繁忙，请稍后再试！</span>
			 </c:if>
			<c:if test="${null ne msg}">
				<span class="textYes">${msg}</span>
			</c:if>
		</div>
	</div>

</body>
</html>

