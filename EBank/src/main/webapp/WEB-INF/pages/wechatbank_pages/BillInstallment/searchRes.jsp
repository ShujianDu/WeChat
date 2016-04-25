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
	<header id="top">
		<span></span>
		<h2>账单查询结果</h2>
		<span></span>
	</header>
	<form action="billCost.do" method="post" id="submitForm">

		<div class="topOneB mar-1 topOneBradius" onchange="changeWarning();">
			<table class="topTwo" style="margin-bottom: -10px;">
				<tr>
					<td>您选择的信用卡：</td>
					<td align="right">${fn:substring(cardNo,0,16)}</td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1" onchange="changeWarning();">
			<table class="topTwo" style="margin-bottom: -10px;">
				<tr>
					<td >可分期金额上限：</td>
					<td align="right">${amountLimit.maxAmount}</td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1" onchange="changeWarning();">
			<table class="topTwo" style="margin-bottom: -10px;">
				<tr>
					<td>可分期金额下限：</td>
					<td align="right">${amountLimit.showMinAmount}</td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1" onchange="changeWarning();">
			<table class="topTwo" style="margin-bottom: -10px;">
				<tr>
					<td >分期币种：</td>
					<td align="right">${amountLimit.currencyCode}</td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1" onchange="changeWarning();">
			<table class="topTwo" style="margin-top:-4px;">
				<tr>
					<td>*拟分期金额:</td>
					<td ><input type="text" id="billActualAmount" name="billActualAmount"/></td>
				</tr>
			</table>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="amountWarning"
				style="color: red; font-size: 12px;"></span>
		</div>
		<div class="topOneB mar-1" onchange="changeWarning();">
			<table class="topTwo" style="margin-top:-10px;">
				<tr>
					<td>*手续费收取方式：</td>
					<td > <select name="feeInstallmentsFlag" id="feeInstallmentsFlag">
								<option value="1">分期收取</option>
								<option value="0">一次性收取</option>
						</select>
					</td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1 bottomOneBradius" onchange="changeWarning();">
			<table class="topTwo" style="margin-top:-10px;">
				<tr>
					<td>*拟分期期数：</td>
					<td ><select id="installmentsNumber" name="installmentsNumber"
						onchange="changeWarning();" >
							<option selected="selected" value="" style="margin-top: 10px;">--请选择期数--</option>
								<option value="3">3</option>
								<option value="6">6</option>
								<option value="9">9</option>
								<option value="12">12</option>
								<option value="18">18</option>
								<option value="24">24</option>
					</select></td>
				</tr>
			</table>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="countWarning"
				style="color: red; font-size: 12px;"></span>
		</div>
		<div class="HandIn" style="margin-top: 10px; margin-bottom: 10px;">
			<input type="submit" value="下一步" onclick="return validate_form();" />
			<input type="hidden" id="accountId" name="accountId" value="${amountLimit.accountId}" />
			<input type="hidden" id="accountNo" name="accountNo" value="${amountLimit.accountNo}" />
			<input type="hidden" id="currencyCode" name="currencyCode" value="${currencyCode}" />
			<input type="hidden" id="billLowerAmount" name="billLowerAmount" value="${amountLimit.minAmount}" />
			<input type="hidden" id="cardNo" name="cardNo" value="${fn:substringAfter(cardNo, ',')}" />
		</div>
	</form>
	<div class="topOneB mar-1 allOneBradius" style="margin-bottom: 10px">
		<table class="topTwo" style="margin-bottom: 10px;">
			<tr>
				<td>温馨提示：手续费一次性收取各期费率分别为3期1.95%，6期3.6%，9期5.4%，12期7.2%，18期11.7%，24期15%。手续费分次收取各期费率分别为3期0.65%，6期0.6%，9期0.6%，12期0.6%，18期0.65%，24期0.65%。本费率表将根据市场情况适时调整。详情请登录中国银行官方网站查询。</td>
			<tr />
		</table>
	</div>
	<div id="sending"
		style="position: absolute; position: fixed; height: 100%; background: #fff; width: 100%; top: 0; left: 0; visibility: hidden; filter: alpha(opacity =     50); opacity: 0.50;">
		<img src="<c:url value="/images/wechatbank/5-121204193R5-50.gif"/>"
			style="position: absolute; top: 50%; left: 50%; margin-left: -16px; margin-top: -16px;" />
	</div>
	<div id="timeoutdiv1"
		style="position: absolute; position: fixed; height: 100%; background: #fff; width: 100%; top: 0; left: 0; display: none; filter: alpha(opacity =     70); opacity: 0.70;">
	</div>
	<div id="timeoutdiv2" class="topOneB mar-1 allOneBradius bcS"
		style="position: absolute; top: 50%; left: 50%; width: 280px; margin-left: -140px; margin-top: -16px; margin-bottom: 10px; display: none;">
		<table class="topTwo" style="margin-bottom: 10px;">
			<tr>
				<td style="font-size: 16px;">该页面已失效，请返回重新操作！</td>
			<tr />
		</table>
	</div>
	<script type="text/javascript">
		var isClicked = false;
		var can = false;
		var amountWarning = $("#amountWarning");
		var countWarning = $("#countWarning");
		function validate_form() {
			if (isClicked == false) {
				var billActualAmount = $("#billActualAmount");
				var installmentsNumber = $("#installmentsNumber");
				var billLowerAmount = ${amountLimit.showMinAmount};
				var billCeilingAmount = ${amountLimit.maxAmount};
				if (billActualAmount.val() == null || billActualAmount.val() == "") {
					$("#amountWarning").text ("请填写分期金额！");
					return false;
				}
				
				if (billActualAmount.val() < billLowerAmount || billActualAmount.val() > billCeilingAmount) {
					$("#amountWarning").text ("请正确填写分期金额！");
					return false;
				}
				
				var amountVal =/^([0-9]+)$/;
				if (amountVal.test(billActualAmount.val()) == false) {
					$("#amountWarning").text ("请填写正确分期金额！");
					return false;
				}
				if (installmentsNumber.val() == null || installmentsNumber.val() == "") {
					$("#countWarning").text ("请选择分期期数！");
					return false;
				}
				
				var sending =$("#sending");
				sending.style.visibility = "visible";
				isClicked = true;
				return true;
			}
		}

		function changeWarning() {
			$("#amountWarning").text ("");
			$("#countWarning").text ("");
		}
		
	</script>
</body>
</html>