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
	    <h2>消费分期设置</h2>
	    <span></span>
	</header>
	
	<form action="cost.do" method="post" id="submitForm" name="submitForm">
			
		<input type="hidden" id="accountKeyOne" name="accountKeyOne" value="${ciinfo.accountID}">	
		<input type="hidden" id="accountKeyTwo" name="accountKeyTwo" value="${ciinfo.accountedID}">
		<input type="hidden" id="currencyCode" name="currencyCode" value="${ciinfo.transactionCurrencyCode}">
		<input type="hidden" id="billDateNo" name="billDateNo" value="${ciinfo.cycleNumber}">
		<input type="hidden" id="transactionNo" name="transactionNo" value="${ciinfo.transactionNo}">
		<input type="hidden" id="transactionAmount" name="transactionAmount" value="${ciinfo.transactionAmount}">
		<input type="hidden" id="cardNo" name="cardNo" value="${fn:substringAfter(ciinfo.cardNo,',')}">
		<input type="hidden" id="accountNoID" name="accountNoID" value="${ciinfo.accountNoID}">
		
		<div class="topOneB mar-1 topOneBradius">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>卡号：</td>
					<td align="right"><c:out value="${fn:substring(ciinfo.cardNo,0,16)}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td class="td-le">交易描述：</td>
					<td align="right"><c:out value="${ciinfo.transactionDescription}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td class="td-le">入账币种：</td>
					<td align="right"><c:out value="${ciinfo.originalCurrencyCode}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td class="td-le">入账金额：</td>
					<td align="right"><c:out value="${ciinfo.originalTransactionAmount}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1" onchange="changeWarning();">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td colspan="2">*拟分期期数：<span id="installmentPeriodsWarning" style="color: red; font-size: 12px;"></span></br>
						<select id="installmentPeriods" name="installmentPeriods">
							<option selected="selected" value="">--请选择--</option>
							<option value="3">3</option>
							<option value="6">6</option>
							<option value="9">9</option>
							<option value="12">12</option>
							<option value="18">18</option>
							<option value="24">24</option>
						</select>
					</td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1 bottomOneBradius" onchange="changeWarning();">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td colspan="2">*手续费收取方式：</br>
						<select name="isfeeFlag" id="isfeeFlag">
								<option value="1">分期收取</option>
								<option value="0">一次性收取</option>
						</select>
					</td>
				</tr>
			</table>
		</div>
		<!-- <div class="topOneB mar-1 bottomOneBradius" onchange="changeWarning();">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>*分期手续费收取方式：<span id="isfeeFlagWarning" style="color: red; font-size: 12px;"></span></br>
						<select id="isfeeFlag" name="isfeeFlag">
							<option value="0">一次性收取</option>
						</select>
					</td>
				</tr>
			</table>
		</div> -->
		
		<div class="HandIn" style="margin-top: 10px; margin-bottom: 10px;">
			<input  id="submitButton" type="submit" value="下一步" onclick="return validate_form();" />
		</div>
		<div class="topOneB mar-1 allOneBradius" style="margin-bottom: 10px">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>温馨提示：手续费一次性收取各期费率分别为3期1.95%，6期3.6%，9期5.4%，12期7.2%，18期11.7%，24期15%。手续费分次收取各期费率分别为3期0.65%，6期0.6%，9期0.6%，12期0.6%，18期0.65%，24期0.65%。本费率表将根据市场情况适时调整。详情请登录中国银行官方网站查询。</td>
				<tr />
			</table>
		</div>
	</form>

	<script type="text/javascript">
		var isClicked = false;
		var installmentPeriodsWarning = document.getElementById("installmentPeriodsWarning");
		var isfeeFlagWarning = document.getElementById("isfeeFlagWarning");
		
		function validate_form() {
			if (isClicked == false) {
				var installmentPeriods = document.getElementById("installmentPeriods");
				var isfeeFlag = document.getElementById("isfeeFlag");
				
				if (installmentPeriods.value == null || installmentPeriods.value == "") {
					installmentPeriodsWarning.innerHTML = "请选择拟分期期数！";
					return false;
				}
				
				if (isfeeFlag.value == null || isfeeFlag.value == "") {
					isfeeFlagWarning.innerHTML = "分期手续费收取方式！";
					return false;
				}
								
				var form = document.getElementById("submitForm");
				form.action="cost.do?openId=${openId}";
				isClicked = true;
				return true;
			}
		}

		function changeWarning() {
			installmentPeriodsWarning.innerHTML = "";
			isfeeFlagWarning.innerHTML = "";
		}
	</script>
</body>
</html>