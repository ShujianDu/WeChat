<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="format-detection" content= "telephone=no" />
	<%@include file="../../base_pages/base.jsp"%>
	<title>中国银行信用卡</title>
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/myLine.css"/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/DefaultStyle.css"/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value="/css/BlackTip.css"/>" />
	<script src="<c:url value="/js/chart.js"/>" type="text/javascript"></script>
	<script src="<c:url value="/js/myLine.js"/>" type="text/javascript"></script>
	<script>
		$(function(){
			var doughnutData = [
				{
					value: 0,
					color:"#ed6139",
				},
				{
					value: 1,
					color: "#0ca8f5",
				},
			];
			var ctx = $("#chart-area").get(0).getContext("2d");
			window.myDoughnut = new Chart(ctx).Doughnut(doughnutData, {responsive : true});
		});
	</script>
</head>
<body>
<div id="myLine_box">
	<form name="value_debt">
		<select name="cardNo" id="cardNo" onchange="messageRevealWait()" class="control_card" >
			<option value="">请选择银行卡</option>
			<c:forEach items="${cardList}" var="item" varStatus="status">
				<option class="" value="${fn:substringAfter(item.cardNo, ',')}">
					<c:out value="${fn:substring(item.cardNo, 0, 16)}" />
				</option>
			</c:forEach>
		</select>
	</form>
	<!--额度信息-->
	<div class="credit_box">
		<!--选择币种-->
		<div class="checked_box" id="currencyChinaCode">
		</div>
		<!--显示额度动态效果-->
		<img src="../images/help.png" class="help">
		<div id="canvas-holder">
			<canvas id="chart-area"  />
		</div>

		<!--显示额度-->
		<div class="canvas_content">
			<span class="credit_title">信用额度</span>
			<span class="credit_value"><span id="credit_value">0.00</span></span>
			<span class="no_value" ><span id="no_value">0.00</span></span>
		</div>
		<div class="creditTip">
			<div id="state"></div>
		</div>
		<div class="creditTip">
			<img src="../images/tip1.png"/>
			<span style="width:56px;">信用额度<span id="credit0" style="padding-left:14px;">0.00</span></span>
		</div>
		<div class="creditTip">
			<img src="../images/tip1.png"/>
			<span>可取现额度<span id="credit1">0.00</span></span>
		</div>
		<div class="creditTip">
			<img src="../images/tip2.png"/>
			<span>总可用额度<span id="credit2">0.00</span></span>
		</div>
	</div>


	<!--black_box-->
	<div class="black_box">
		<div class="tip_box">
			<span class="tip_close"></span>
			<span class="tip_title">提示</span>
			<span class="tip_content">信用额度是我行根据您的资信状况等为您核定、在卡片有效期内可循环使用的最高授信额度,调额业务仅限于主卡客户申请</span>
			<span class="tip_close2">我知道了</span>
		</div>
	</div>
	<!--error_box-->
	<div class="error_box">
		<div class="tip_box">
			<span class="tip_close"></span>
			<span class="tip_title"><img src="../images/no.png" class="myPointImg"/></span>
			<span class="tip_content">很抱歉！没有查询到您的信用卡账单。详情请咨询中国银行信用卡24小时客服热线
				4006695566。感谢您对中行信用卡的支持和厚爱！</span>
			<span class="tip_close2">我知道了</span>
		</div>
	</div>
	<!--wait_box -->
	<div class="wait_box">
		<div class="tip_box">
			<img src="../images/Loading2.gif"/>
			<span class="wait_text">loading</span>
		</div>
	</div>
</div>

</div>
</body>
</html>
