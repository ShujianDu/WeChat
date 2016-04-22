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
		<h2>分期付款</h2>
		<span></span>
	</header>
		<div class="HandIn" style="margin-top: 10px; margin-bottom: 10px;">
			<input type="submit" name="button" value="申请消费分期" onclick="window.location='../consumptionInstallment/list.do'"/>
		</div>
		<div class="HandIn" style="margin-top: 10px; margin-bottom: 10px;">
			<input type="submit" name="button" value="申请账单分期" onclick="window.location='search.do'"/>
		</div>
		<div class="HandIn" style="margin-top: 10px; margin-bottom: 10px;">
			<input type="submit" name="button" value="分期历史查询" onclick="window.location='../historyInstalment/list.do?'"/>
		</div>
		<div class="topOneB mar-1 allOneBradius" style="margin-bottom: 10px">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>
						“分期轻松购”是中国银行为信用卡持卡人特别定制的分期付款方式：您只需消费满一定金额，并在指定日期前通过微信等渠道申请，即可将普通消费转为分期付款。<br />
						<br />
						分期轻松购•消费分期：<br />
						交易日后至最近一期账单日2天前，即可申请将单笔600元（含）以上的普通刷卡消费交易转为分期偿还。<br />
						</br>
						分期轻松购•账单分期：<br />
						账单日后至当期账单还款日2天前，即可申请将当期账单人民币新增刷卡消费金额转为分期偿还。每期账单申请账单分期的金额需为整数，最低为人民币1,000元，最高不超过当期账单新增刷卡消费（不含预借现金、分期付款交易以及其他本行指定的交易）总额的90%。
					</td>
				<tr />
			</table>
		</div>
</body>
</html>