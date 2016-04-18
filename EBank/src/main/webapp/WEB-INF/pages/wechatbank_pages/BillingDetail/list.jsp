<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<c:set var="path" value="${pageContext.request.contextPath }"></c:set>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/BillingDetails.css"/>" />
<title>中国银行信用卡</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name=" format-detection" content="telephone=no" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
</head>
<body>
	<div class="BillingDetails_content">
		<input value="${cardNo }" id="control_card" type="text"
			disabled="disabled" />
		<!--BillingDetails-->
		<ul class="showMoreNChildren" pagesize="4">

			<c:forEach items="${billingDetailList}" var="item" varStatus="status">
				<li class="Billing_box">
				    <span id="BillingName">${item.transactionDescription }</span>
					<span id="BillingStage">
					   <c:if test="${item.debitCreditCode=='DEBT'}">
						支出
						</c:if>
						<c:if test="${item.debitCreditCode=='CRED'}">
							存入
						</c:if>
					</span> 
					<span id="periodEndDate">${item.transactionDate}</span>
					<span id="currencyCode">${item.currencyCode} ${item.transactionAmount} </span>
				</li>
			</c:forEach>
		</ul>
		<div class="readMore" id="readMore"onclick="getMore();">查看更多</div>
	</div>
	<script type="text/javascript" src="${path }/js/BillingDetails.js"
		charset="UTF-8"></script>
</body>
</html>

