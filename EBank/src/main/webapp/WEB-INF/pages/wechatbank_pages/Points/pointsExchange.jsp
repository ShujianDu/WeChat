<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>中国银行信用卡</title>
</head>

<body>
	<form action="${exchangeUrl }" method="post" id="submitForm">
			<input type="hidden" value="${cardNo}" name="cardNo" id="cardNo" />
			<input type="hidden" value="${sign}" id="sign" name="sign"/>  
	</form>
	<script type="text/javascript">
		$(function(){
			$("#submitForm").submit();
		});
	</script>
</body>
</html>