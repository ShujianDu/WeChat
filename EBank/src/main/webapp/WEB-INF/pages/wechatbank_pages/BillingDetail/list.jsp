<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<c:set var="path" value="${pageContext.request.contextPath }"></c:set>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/BillingDetails.css"/>" />
	<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/BlackTip.css"/>" />
	<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/DefaultStyle.css"/>" />
<title>中国银行信用卡</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name=" format-detection" content="telephone=no" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<script type="text/javascript">
		window.onload = function() {
			var onepage = "${onepage}";
			//如果数据小于一页展示的条数，获取更多按钮隐藏
			var detailListLength =${fn:length(billingDetailList)} ;
			console.log("onePage="+onepage+"--detailListLength="+detailListLength);
			if(detailListLength<onepage){
				$("#readMore").hide();
			}
		}
	</script>
</head>
<body>
	<div class="BillingDetails_content">
	<div class="BillingDetails"><span class="name">
	<c:if test="${model.queryType=='ALLT' }">已出账单明细</c:if>
	<c:if test="${model.queryType=='UNSM' }">未出账单明细</c:if>
	</span><span class="dynamicData" id="CardNumber">${fn:substring(cardNo, 0, 16)}</span></div>
		<!--BillingDetails-->
		<ul class="showMoreNChildren" id="showMoreNChildren">
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
					<span id="currencyCode"><span>${item.currencyChinaCode} </span><span>${item.transactionAmount} </span></span>
				</li>
			</c:forEach>
		</ul>
		<div class="readMore" id="readMore" onclick="getMore();">查看更多</div>
		<span id="noBillingWarning"
				style="color: #999999; font-size: 13px; line-height: 40px; margin-left: 16px;"></span>
	</div>
	<!--ajax查询用到的参数  -->
	<input value="${fn:substringAfter(cardNo, ',')}" id="cardNo" type="hidden"/>
	<input value="${model.queryType}" id="queryType" type="hidden"/>
	<input value="${startnum}" id="startnum" type="hidden"/>
	<input value="${model.periodStartDate}" id="periodStartDate" type="hidden"/>
	<input value="${model.periodEndDate}" id="periodEndDateAjax" type="hidden"/>
	<input value="${model.currencyCode}" id="currencyCodeReal" type="hidden"/>
	<!-- 一次最多显示的条数 -->
	<input value="${onepage}" id="onepage" type="hidden"/>
	<script type="text/javascript" src="${path }/js/BillingDetails.js"
		charset="UTF-8"></script>
<script> $(".BillingDetails_content").height(parseInt(document.documentElement.clientHeight)-211);
/* $(".BillingDetails_content").height()=600; */
</script> 
</body>
</html>

