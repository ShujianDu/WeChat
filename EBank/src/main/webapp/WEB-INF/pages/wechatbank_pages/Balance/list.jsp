<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<%@include file="../../base_pages/wxjs.jsp"%>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>中国银行信用卡</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap.min.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/wechatbank/index.css"/>" />
<%@include file="../../base_pages/wxReadyFunction.jsp"%>
</head>
<body>
	<header>
		<span></span>
		<h2>我的额度</h2>
		<span></span>
	</header>
	
<div class="container">

<c:if test="${fn:length(newList) eq 0}">
	<div class="container lg">
		<div class="alert-bg text-center table-shadow">
			<div class="alert alert-font" role="alert">
				很抱歉！没有查询到您的信用卡账单。详情请咨询中国银行信用卡24小时客服热线
				<a class="alert-link text-danger text-thin" href="#">4006695566</a>。感谢您对中行信用卡的支持和厚爱！
			</div>
		</div>
	</div>
</c:if>
		<c:if test="${fn:length(newList) gt 0}">
			<c:forEach items="${newList}" var="balanceList" varStatus="status">
				<c:if test="${fn:length(balanceList) gt 0}">
					<c:forEach items="${balanceList}" var="item" begin="0" end="0" varStatus="status">
						<c:if test="${fn:length(item.currencyCode) le 0}">
							<div class="container lg">
								<div class="alert-bg text-center table-shadow">
									<div class="alert alert-font" role="alert">
										<c:out value="${fn:substring(item.cardNo,0,4)}"/>********
										<c:out value="${fn:substring(item.cardNo,fn:length(item.cardNo)-4,fn:length(item.cardNo))}"/>
										的额度无法查询。
									</div>
								</div>
							</div>
						</c:if>
						<c:if test="${fn:length(item.currencyCode) gt 0}">
						<div class="container card-style bottom-none">
							<h5><c:out value="${fn:substring(item.cardNo,0,4)}"/>
								********
								<c:out value="${fn:substring(item.cardNo,fn:length(item.cardNo)-4,fn:length(item.cardNo))}"/>
							</h5>
						</div>
						</c:if>
					</c:forEach>
					<div class="container lg container-border">
					<c:forEach items="${balanceList}" var="item" varStatus="status">
						<c:if test="${fn:length(item.currencyCode) gt 0}">
							<div class="container card-style top-none">
								<div class="cards">
									<table class="table-none">
										<thead><tr ><td class="text-left strong" ><c:out value="${item.currencyCode}" />账户</td></tr></thead>
										<tbody>
										<tr>
											<td>信用额度</td>
											<td class="text-right"><c:out value="${item.limitCount}" /></td>
										</tr>
										<tr>
											<td>总可用额</td>
											<td class="text-right"><c:out value="${item.avaliableCount}" /></td>
										</tr>
										<tr>
											<td>取现可用额</td>
											<td class="text-right"><c:out value="${item.preCashAdvanceCreditLimit}" /></td>
										</tr>
										</tbody>
									</table>
								</div>
							</div>
							
						</c:if>
					</c:forEach>
					</div>
				</c:if>
			</c:forEach>
		</c:if>

</div>
</body>
</html>
