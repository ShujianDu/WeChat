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
		<h2>查询账单可分期金额 </h2>
		<span></span>
	</header>
	<form action="searchResult.do" method="post" id="submitForm">
		<div class="topOneB mar-1 topOneBradius" onchange="changeWarning();">
			<table class="topTwo" style="margin-bottom: -10px;">
				<tr>
					<td class="td-le">*卡号：</td>
					<td><select id="cardNo" name="cardNo"
						onchange="changeWarning();">
							<c:forEach items="${cardList}" var="item">
								<option value="${fn:substringAfter(item.cardNo, ',')}">${fn:substring(item.cardNo, 0, 16)}</option>
							</c:forEach>
					</select></td>
				</tr>
			</table>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="cardNoWarning"
				style="color: red; font-size: 12px;"></span>
		</div>
		<div class="topOneB mar-1 bottomOneBradius" onchange="changeWarning();">
			<table class="topTwo" style="margin-bottom: -10px;">
				<tr>
					<td class="td-le">*币种：</td>
					<td><select id="currencyCode" name="currencyCode"
						onchange="changeWarning();">
							<option value="CNY">人民币</option>
					</select></td>
				</tr>
			</table>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="currencyCodeWarning"
				style="color: red; font-size: 12px;"></span>
		</div>
		
		<div class="HandIn" style="margin-top: 10px; margin-bottom: 10px;">
			<input type="submit" value="查询" onclick="return validate_form();" />
		</div>
		<c:if test="${status == 'noBill' }">
			<div class="topOneB mar-1 allOneBradius" style="margin-bottom: 10px">
				<table class="topTwo" style="margin-bottom: 10px;">
					<tr>
						<td>尊敬的客户，您申请办理账单分期已过申请时限，无法办理账单分期业务，感谢您的支持。</td>
					<tr />
				</table>
			</div>
		</c:if>
		<c:if test="${status == 'notSatisfied' }">
			<div class="topOneB mar-1 allOneBradius" style="margin-bottom: 10px">
				<table class="topTwo" style="margin-bottom: 10px;">
					<tr>
						<td>尊敬的客户，您当前卡片账单可分期金额低于我行账单可分期金额下限（1000元），无法办理账单分期业务，感谢您的支持。</td>
					<tr />
				</table>
			</div>
		</c:if>
	
	</form>

	<script type="text/javascript">
		
		var isClicked = false;
		function validate_form(){
			var cardNo = $("#cardNo");
			var currencyCode = $("#currencyCode");
			if (cardNo.val() == null || cardNo.val() == "") {
				$("#cardNoWarning").innerHTML = "请选择卡号！";
				return false;
			}
			if (currencyCode.val() == null || currencyCode.val() == "") {
				$("#currencyCodeWarning").innerHTML = "请选择币种！";
				return false;
			}
			isClicked = true;
			return true;
		}
		
		function changeWarning() {
			$("#cardNoWarning").innerHTML = "";
			$("#currencyCodeWarning").innerHTML = "";
		}
		
		window.onload = function() {
			var cardSelect = $("#cardNo");
			for ( var i = 0; i < cardSelect.length; i++) {
				if (cardSelect[i].val() == "${cardNo}") {
					cardSelect[i].selected = "selected";
				}
			}
		}


	</script>
</body>
</html>