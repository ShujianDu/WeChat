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
	    <h2>分期历史详情</h2>
	    <span></span>
	</header>
	
	<form action="" method="post" id="submitForm" name="submitForm">
		<div style="margin-top: 20px;">
		<div class="topOneB mar-1 topOneBradius">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>卡号：</td>
					<td align="right"><c:out value="${fn:substring(model.cardNo,0,4)}" />********<c:out value="${fn:substring(model.cardNo,fn:length(model.cardNo)-4,fn:length(model.cardNo))}" /></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>分期日期：</td>
					<td align="right"><c:out value="${model.instalmentOriginalTransactionDate}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>分期描述：</td>
					<td align="right"><c:out value="${model.instalmentRuleDescription}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>分期币种：</td>
					<td align="right"><c:out value="${model.currencyCode}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>分期金额：</td>
					<td align="right"><c:out value="${model.instalmentOriginalAmount}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>期数：</td>
					<td align="right"><c:out value="${model.instalmentOriginalNumber}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>完成日期：</td>
					<td align="right"><c:out value="${model.instalmentCompleteDate}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>分期手续费收取方式：</td>
					<td align="right"><c:if test="${model.instFeeFlag=='1'}">分期收取</c:if>
						<c:if test="${model.instFeeFlag=='0'}">一次性收取</c:if></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>首次入帐金额：</td>
					<td align="right"><c:out value="${model.instalmentFirstPostingAmount}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>下次入帐金额：</td>
					<td align="right"><c:out value="${model.instalmentNextPostingAmount}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>已入账期数：</td>
					<td align="right"><c:out value="${model.instalmentPostedNumber}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>已入账金额：</td>
					<td align="right"><c:out value="${model.instalmentReversalAmount}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>剩余未入账期数：</td>
					<td align="right"><c:out value="${model.instalmentOutstandingNumber}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>剩余未入账金额：</td>
					<td align="right"><c:out value="${model.instalmentOutstandingAmount}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1 bottomOneBradius">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>下次入帐日期：</td>
					<td align="right"><c:out value="${model.instalmentNextPostingDate}"/></td>
				</tr>
			</table>
		</div>
		<div style="margin-top: 30px;">
	</form>

</body>
</html>