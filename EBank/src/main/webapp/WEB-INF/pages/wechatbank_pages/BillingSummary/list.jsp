<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<c:set var="path" value="${pageContext.request.contextPath }"></c:set>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/myBill.css"/>" />
<title>中国银行信用卡</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name=" format-detection" content="telephone=no" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
</head>
<body>
	<div id="myBill_box">
		<form name="value_debt">
			<select id="cardNo" name="messagePick" class="control_card">
				<option value="">请选择下列的银行卡</option>
				<c:forEach items="${cardList}" var="item" varStatus="status">
					<option class="" value="${fn:substringAfter(item, ',')}"
						name="cardNo" onclick="messageRevealWait();">
						<c:out value="${fn:substring(item, 0, 16)}" />
					</option>
				</c:forEach>
			</select> <span id="cardNoWarning"
				style="color: #e05d4f; font-size: 13px; line-height: 40px; margin-left: 16px;"></span>
		</form>
		<!--选择日期账单显示-->
		<div class="check_box">
			<div class="state">
				<span class="date">账单日期<span id="periodEndDate"></span></span><span
					class="state_1">已出账单</span>
			</div>
			<span style="display: none" id="periodStartDate"></span>
			<div class="select_date">
				<span class="left left1"></span> <span class="right right2"></span>
				<div class="date_box">
					<ul>
						<c:forEach items="${dateList}" var="item" varStatus="status">
							<c:if test="${status.index==0 }">
								<li class="current_mounth" value="${item }"><span
									class="year">${fn:substring(item,0,4)}</span> <span
									class="mounth">${fn:substring(item,4,6)}月 </span></li>
							</c:if>
							<c:if test="${status.index!=0 }">
								<li value="${item }"><span class="year">${fn:substring(item,0,4)}</span>
									<span class="mounth">${fn:substring(item,4,6)}月 </span></li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</div>
			<input type="hidden" id="date" value="${dateList[0]}" />
			<div id="noBillingWarning"
				style="color: #999999; font-size: 13px; line-height: 40px; margin-left: 16px;"></div>
			<!--实际币种,其余为页面展示的中文币种  -->
			<span id="currencyCodeReal1" style='display: none'></span> 
			<span id="currencyCodeReal2" style='display: none'></span>

		<!--还款形式和信息-->
		<div class="pay_box">
			<div class="pay_date">
				到期还款日&nbsp;&nbsp;&nbsp;&nbsp;<span id="pay_date"></span>
			</div>
			<div class="pay_value">
				账单金额&nbsp;&nbsp;&nbsp;&nbsp;
				<div class="doubleState1" id="doubleState5">
					<span id="currencyCode1" class="currencyCode"></span><span
						id="closingBalance1"></span>
				</div>
				<div class="doubleState" id="doubleState3">
					<span id="currencyCode2" class="currencyCode"></span><span
						id="closingBalance2"></span>
				</div>
			</div>
			<div class="lineGray"></div>
			<div class="pay_value">
				最低还款额&nbsp;&nbsp;&nbsp;&nbsp;
				<div class="doubleState1" id="doubleState6">
					<span id="currencyCode3" class="currencyCode"></span><span
						id="minPaymentAmount1"></span>
						<input type="button" class="check_dt" id="check_dt1"
						onclick="getBillingDetail('ALLT','currencyCodeReal1');" />
				</div>
				<div class="doubleState" id="doubleState4">
					<span id="currencyCode4" class="currencyCode"></span><span
						id="minPaymentAmount2"></span>
						<input
						type="button" class="check_dt" id="check_dt2"
						onclick="getBillingDetail('ALLT','currencyCodeReal2');" />
				</div>
			</div>
			
		</div>
		<!--未出账单-->
		<a href="#" class="noBill" onclick="getBillingDetail('UNSM','');">未出账单</a>

		<!--wait_box -->
		<div class="wait_box">
			<div class="tip_box">
				<img src="${path }/images/Loading2.gif" /> <span class="wait_text">loading</span>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${path }/js/myBill.js"
		charset="UTF-8"></script>
	<script type="text/javascript">
		//获取账单明细
		function getBillingDetail(queryType, currencyCodeReal) {
			$("#cardNoWarning").text("");
			var cardNo = $("#cardNo").val();
			if (cardNo == null || cardNo == "") {
				$("#cardNoWarning").text("*请选择卡号！");
				return;
			}
			if (queryType == 'UNSM') {
				window.location.href = "../billingdetail/list.do?cardNo="
						+ cardNo + "&queryType=" + queryType;
			} else {
				var currencyCode = $("#" + currencyCodeReal + "").text();
				var periodStartDate = $("#periodStartDate").text();
				var periodEndDate = $("#periodEndDate").text();
				window.location.href = "../billingdetail/list.do?cardNo="
						+ cardNo + "&currencyCode=" + currencyCode
						+ "&periodStartDate=" + periodStartDate
						+ "&periodEndDate=" + periodEndDate + "&queryType="
						+ queryType;
			}
		}
	</script>
</body>
</html>