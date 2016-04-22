<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
	<title>提升额度</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name=" format-detection" content= "telephone=no" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<%@include file="../../base_pages/base.jsp" %>
	<link href="css/UpgradeYES.css" rel="stylesheet" type="text/css">
	<script src="js/UpgradeYES.js" type="text/javascript"></script>
</head>
<body>
<!--UpgradeYES-->
<div class="UpgradeYES_content">
	<div class="UpgradeYES_title">提升额度</div>
	<!--第一种卡信息-->
	<div class="dynamic_box">
		<img src="images/no.png" class="imgyes">
		<span class="textYes">提交额度临时提升申请失败！</span>
	</div>
	<span class="tip_text">${failMsg }</span>
</div>
</body>
</html>
