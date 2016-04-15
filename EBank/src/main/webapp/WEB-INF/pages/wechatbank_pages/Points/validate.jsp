<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<%@include file="../../base_pages/wxjs.jsp"%>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>中国银行信用卡</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/wechatbank/index.css"/>" />
<%@include file="../../base_pages/wxReadyFunction.jsp"%>
</head>

<body>
	<header>
		<span></span>
		<h2>积分有效期</h2>
		<span></span>
	</header>
	<div class="mg-tb-20">
		<!-- 不存在积分 -->
		<c:if test="${fn:length(pointsValidatesList) eq 0}">
			<div class="topOneB mar-1 allOneBradius">
				<table class="topTwo mb-10">
					<tr>
						<td><span class="">很抱歉！没有查询到您的积分到期日。详情请咨询中国银行信用卡24小时客服热线4006695566。感谢您对中行信用卡的支持和厚爱！</span></td>
					</tr>
				</table>
			</div>
		</c:if>
		<!-- 存在积分 -->
		<c:if test="${fn:length(pointsValidatesList) gt 0}">
			<c:forEach items="${pointsValidatesList}" var="item" varStatus="status">
				<div class="topOneB mar-1 topOneBradius">
					<table class="topTwo">
						<tr>
							<td width="120">积分余额:</td>
							<td align="right"><c:out value='${item.totalPoint}' /></td>
						</tr>
					</table>
				</div>
				<div class="topOneB mar-1 bottomOneBradius">
					<table class="topTwo">
						<tr>
							<td width="120">积分到期日:</td>
							<td align="right"><c:out value='${item.pointExpireDate}' /></td>
						</tr>
					</table>
				</div>
				<div style="margin-top: 10px;"></div>
			</c:forEach>
		</c:if>
	</div>
</body>
</html>
