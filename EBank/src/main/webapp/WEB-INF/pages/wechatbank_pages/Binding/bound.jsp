<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>中国银行信用卡</title>
<link rel="stylesheet" type="text/css" href="../css/bootstrap.min.css">
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/wechatbank/index.css"/>" />
</head>
<body>	
	<div class="container mg-top">
		<div class="img-bg text-center table-shadow">
			<div style="height:15px;"></div>
			<h5 style="font-weight: 300">您的微信号已经绑定了卡片<br>如需更换默认卡片<p></p>
				<a class="text-danger" href="bindDefCard.do?openId=${openId}">请点击这里</a>
			</h5>
		</div>
		<div style="height:20px;"></div>
		<button type="submit" class="btn btn-sm btn-default btn-block btn-shadow">返&nbsp;&nbsp;回</button>
	</div>
	
</body>
</html>

