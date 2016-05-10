<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>中国银行信用卡</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/bootstrap.min.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/wechatbank/index.css"/>" />
</head>
<body>
<div class="container mg-top">
	<div class="img-bg text-center table-shadow">
		<div style="height:30px;"></div>
		<h5 class="text-danger" style="font-weight: 300">您的账户已锁定，请在30分钟后再次尝试</h5>
		<div style="height:20px;"></div>
	</div>
</div>
</body>
</html>