<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>中国银行信用卡</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/wechatbank/index.css"/>" />
</head>
<body>
<header>
    <span></span>
    <h2>账单寄送方式</h2>
    <span></span>
</header>
	<div style="margin-top: 30px;">
<c:forEach items="${sendTypeList}" var="item" varStatus="status">
	<div style="margin-top: 10px; margin-bottom: 10px;">
			<div class="topOneB mar-1 topOneBradius">
				<table class="topTwo">
					<tr>
						<td><span class="">卡号：
						<c:out value="${fn:substring(item.cardNo,0,16)}" />
					</tr>
				</table>
			</div>
			<div class="topOneB mar-1 bottomOneBradius">
				<table class="topTwo">
					<tr>
						<td width="120" style="font-weight:600;">当前账单寄送方式:</td>
					</tr>
					<tr>	
						<td  align="center"><span class="black"><c:out value="${item.billSendTypeDesc}" /></span></td>
					</tr>
					<tr>	
					<td><a class="allA"	href="../billingsendway/edit.do?cardNo=${fn:substringAfter(item.cardNo,',')}&billSendType=${item.billSendType}">更改账单寄送方式</a></td>
					</tr>
				</table>
			</div>
			<div style="margin-top: 5px;"></div>
			<div style="margin-top: 20px;"></div>
	</div>
</c:forEach>
</div>
</body>
</html>
