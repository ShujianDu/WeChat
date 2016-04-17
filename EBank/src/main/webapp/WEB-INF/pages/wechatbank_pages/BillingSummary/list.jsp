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
					<option class=""
						value="${fn:substringAfter(item, ',')}" name="cardNo"
						onclick="messageRevealWait();">
						<c:out value="${fn:substring(item, 0, 16)}" />
					</option>
				</c:forEach>
			</select>
			 <span id="cardNoWarning" style="color: red; font-size: 12px;"></span>
		</form>
		<!--选择日期账单显示-->
		<div class="check_box">
			<div class="state">
				<span class="date">账单日期<span id="periodEndDate"></span></span><span
					class="state_1">已出账单</span>
			</div>
			<div class="select_date">
				<span class="left left1"></span> <span class="right right2"></span>
				<div class="date_box">
					<ul>
						<c:forEach items="${dateList}" var="item" varStatus="status">
							<c:if test="${status.index==0 }">
								<li  class="current_mounth" value="${item }"><span
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
					<input type="hidden" id="date" value="${dateList[0]}"></input>
					<span id="noBillingWarning" style="color: red; font-size: 12px;"></span>
			<!--显示价格-->
			<div class="view_value">
				<div class="value_title"></div>
				<div class="value_box ">
					<span class="value_type"><span id="currencyCode5"></span>欠款</span>
					<span type="text" class="value"><span
						id="value1"></span></span> <input type="button" class="check_dt"
						id="check_dt1" onclick="getBillingDetail();"/>
				</div>
				<div class="value_box ">
					<span class="value_type"><span id="currencyCode6"></span>欠款</span>
					<span type="text" class="value"><span id="value2">0.00</span></span>
					<input type="button" class="check_dt" id="check_dt2" onclick="getBillingDetail();"/>
				</div>
			</div>
		</div>


		<!--还款形式和信息-->
		<div class="pay_box">
			<div class="pay_date">
				到期还款日&nbsp;&nbsp;&nbsp;&nbsp;<span id="pay_date"></span>
			</div>
			<div class="pay_value">
				账单金额&nbsp;&nbsp;&nbsp;&nbsp;
				<div class="doubleState1">
					<span id="currencyCode1" class="currencyCode"></span><span
						id="closingBalance1"></span>
				</div>
				<div class="doubleState">
					<span id="currencyCode2" class="currencyCode"></span><span
						id="closingBalance2"></span>
				</div>
			</div>
			<div class="lineGray"></div>
			<div class="pay_value">
				最低还款额&nbsp;&nbsp;&nbsp;&nbsp;
				<div class="doubleState1">
					<span id="currencyCode3" class="currencyCode"></span><span
						id="minPaymentAmount1"></span>
				</div>
				<div class="doubleState">
					<span id="currencyCode4" class="currencyCode"></span><span
						id="minPaymentAmount2"></span>
				</div>
			</div>
			<input type="button" value="账单分期" class="stage_btn" /> <input
				type="button" value="消费分期" class="spend_btn" />
		</div>

		<!--未出账单-->
		<a href="#" class="noBill">未出账单</a>

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
	function getBillingDetail(){
		
	}
	</script>
</body>
</html>

