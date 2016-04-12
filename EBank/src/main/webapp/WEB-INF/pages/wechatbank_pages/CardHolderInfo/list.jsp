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
	    <h2>我的资料</h2>
	    <span></span>
	</header>
	<div class="mg-tb-30">
		<div class="topOneB mar-1 topOneBradius">
			<table class="topTwo">
				<tr>
					<td class="td-le">手机号码:</td>
					<td align="right"><c:out value="${cardHolderInfo.mobileNo}" /></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo">
				<tr>
					<td class="td-le">住宅电话:</td>
					<td align="right"><c:out value="${cardHolderInfo.homeAddressPhone}" /></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo">
				<tr>
					<td class="td-le">单位电话:</td>
					<td align="right"><c:out value="${cardHolderInfo.workUnitPhone}" /></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo">
				<tr>
					<td class="td-le">电子邮箱:</td>
					<td align="right"><c:out value="${cardHolderInfo.EMail}" /></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo">
				<tr>
					<td class="td-le">单位名称:</td>
					<td align="right"><c:out value="${cardHolderInfo.workUnitName}" /></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo">
				<tr>
					<td class="td-le">账单地址:</td>
					<td align="right"><c:out value="${cardHolderInfo.billAddressLine}" /></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1 bottomOneBradius">
			<table class="topTwo">
				<tr>
					<td class="td-le">邮政编码:</td>
					<td align="right"><c:out value="${cardHolderInfo.postalCode}" /></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1 allOneBradius bcS" style="margin-top: 10px;margin-bottom: 10px">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>温馨提示：如需修改客户资料，请拨打中国银行信用卡客服热线4006695566。</td>
				<tr />
			</table>
		</div>
	</div>	
	<div id="sending"
		style="position: absolute; position: fixed; height: 100%; background: #fff; width: 100%; top: 0; left: 0; visibility: hidden; filter: alpha(opacity = 50); opacity: 0.50;">
		<img src="<c:url value="/images/wechatbank/5-121204193R5-50.gif"/>"
			style="position: absolute; top: 50%; left: 50%; margin-left: -16px; margin-top: -16px;" />
	</div>
	<script type="text/javascript">
		
	</script>
</body>
</html>
