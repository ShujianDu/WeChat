	<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<%@include file="../../base_pages/wxjs.jsp"%>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>中国银行信用卡</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/wechatbank/index.css"/>" />
<script type="text/javascript">
	window.onload = function() {
		var cardSelect = document.getElementById("cardNoSelect");
		for ( var i = 0; i < cardSelect.length; i++) {
			if (cardSelect[i].value == "${cardNo}") {
				cardSelect[i].selected = "selected";
			}
		}
		var listlength=${fn:length(pageList)}
		var isFollowUp=${isFollowUp}
		if(listlength==10){
			document.getElementById("moreButton").style.display = "block";
		}else{
			if(isFollowUp > 0) {
				document.getElementById("moreButton").style.display = "block";
			}
			document.getElementById("moreButton").style.display = "none";
		}
	}
</script>
<%@include file="../../base_pages/wxReadyFunction.jsp"%>
</head>

<body>
<header>
    <span></span>
    <h2>分期历史查询</h2>
    <span></span>
</header>
	<div class="mg-tb-20">
		<form action="listP.do" method="post" id="submitForm" name="submitForm">
			<div class="topOneB mar-1 topOneBradius">
				<table class="topTwo">
					<tr>
						<td width="40">卡号:</td>
						<td>
						<select name="cardNo" id="cardNoSelect">
							<c:forEach items="${cardList}" var="item" varStatus="status">
								<option value="${fn:substringAfter(item, ',')}" name="cardNo">
									<c:out value="${fn:substring(item,0,16)}" />
								</option>
							</c:forEach>
						</select>
						</td>
					</tr>
				</table>
			</div>
			<div class="topOneB mar-1 bottomOneBradius" >
				<table class="topTwo">
					<tr>
						<td width="70">币种:</td>
						<td>
							<select name="currencyCodeChinese" id="dateSelect">
								<option value="人民币">人民币</option>
							</select>
						</td>
					</tr>
				</table>
			</div>
			<div class="HandIn" style="margin-top: 20px;">
				<input type="submit" value="确定" onclick="return validate_form();" /> 
			</div>
		</form>
	</div>
	<div style="margin-top: 20px; margin-bottom: 10px;">
		<c:if test="${pageList ne null}">
			<c:if test="${fn:length(pageList) eq 0}">
				<div class="topOneB mar-1 allOneBradius">
					<table class="topTwo mb-10">
						<tr>
							<td>
								<span class="black">很抱歉！没有符合的交易记录。详情请咨询中国银行信用卡24小时客服热线4006695566。感谢您对中行信用卡的支持和厚爱！ </span>
							</td>
						</tr>
					</table>
				</div>
			</c:if>
			<c:if test="${fn:length(pageList) gt 0}">
				<c:forEach items="${pageList}" var="item" varStatus="status">
					<div class="topOneB mar-1 topOneBradius">
						<table class="topTwo">
							<tr>
								<td class="td-le">分期日期：</td>
								<td align="right"><c:out value="${item.instalmentOriginalTransactionDate}" /></td>
							</tr>
						</table>
					</div>
					<div class="topOneB mar-1">
						<table class="topTwo">
							<tr>
								<td class="td-le">卡号：</td>
								<td align="right"><c:out value="${fn:substring(item.cardNo,0,4)}" />********<c:out value="${fn:substring(item.cardNo,fn:length(item.cardNo)-4,fn:length(item.cardNo))}" /></td>
							</tr>
						</table>
					</div>
					<div class="topOneB mar-1">
						<table class="topTwo">
							<tr>
								<td class="td-le">分期描述：</td>
								<td align="right"><c:out value="${item.instalmentRuleDescription}" /></td>
							</tr>
						</table>
					</div>
					<div class="topOneB mar-1">
						<table class="topTwo">
							<tr>
								<td class="td-le">分期币种：</td>
								<td align="right"><c:out value="${item.currencyCode}" /></td>
							</tr>
						</table>
					</div>
					<div class="topOneB mar-1">
						<table class="topTwo">
							<tr>
								<td class="td-le">分期金额：</td>
								<td align="right"><c:out value="${item.instalmentOriginalAmount}" /></td>
							</tr>
						</table>
					</div>
					<div class="topOneB mar-1">
						<table class="topTwo">
							<tr>
								<td class="td-le">期数：</td>
								<td align="right"><c:out value="${item.instalmentOriginalNumber}" /></td>
							</tr>
						</table>
					</div>
					<div class="topOneB mar-1">
						<table class="topTwo">
							<tr>
								<td class="td-le">完成日期：</td>
								<td align="right"><c:out value="${item.instalmentCompleteDate}" /></td>
							</tr>
						</table>
					</div>
					<div class="topOneB mar-1 bottomOneBradius">
						<table class="topTwo">
							<tr>
								<td>
									<a class="allA"	href="show.do?number=${status.index}&cardNo?=${item.cardNo}">查看详情</a>
								</td>
							</tr>
						</table>
					</div>
					<div style="margin-top: 10px;">
					</div>
				</c:forEach>
			</c:if>
		</c:if>
	</div>
	<div id="addViewDiv"></div>
	<div style="margin: 20px 10px;">
		<input type="button" value="查看更多" class="HandInHui" style="width: 100%;display: none;margin: 10px auto;border:1px solid #999" id="moreButton" onclick="getMore();" />	
	</div>	
	<div id="sending"
		style="position: absolute; position: fixed; height: 100%; background: #fff; width: 100%; top: 0; left: 0; visibility: hidden; filter: alpha(opacity =  50); opacity: 0.50;">
		<img src="<c:url value="/images/wechatbank/5-121204193R5-50.gif"/>"
			style="position: absolute; top: 50%; left: 50%; margin-left: -16px; margin-top: -16px;" />
	</div>
	<script type="text/javascript">
		var isClicked = false;
		var page = 0;
		function validate_form() {
			if (isClicked == false) {
				var sending = document.getElementById("sending");
				sending.style.visibility = "visible";
				isClicked = true;
				return true;
			} else {
				return false;
			}
		}
		function getMore() {
			page++;
			if(isClicked==false){
				var sending = document.getElementById("sending");
				sending.style.visibility = "visible";
				isClicked = true;
				var xmlhttp;
				if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
					xmlhttp = new XMLHttpRequest();
				} else {// code for IE6, IE5
					xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
				}
				//获得来自服务器的响应
				var result;
				xmlhttp.onreadystatechange=function(){
				    if (xmlhttp.readyState==4 && xmlhttp.status==200){
				    	result=xmlhttp.responseText;
				    	if(result==null||result=="null"){
				    		document.getElementById("moreButton").style.display = "none";
						}else if(result=="exception"){
							window.location.href="../error.html";
						}else{
							var list=eval("("+result+")");
							if(list.length>0&&list[0].instalmentOriginalAmount!=""){
								for(var i=0;i<list.length;i++){
									 var cardNo = list[i].cardNo;
									 var cardNoLength = cardNo.length;
									 cardNo = cardNo.substring(0,4)+"********"+cardNo.substring(cardNoLength-4,cardNoLength);
									//增加HTML对象，并附加到容器最后
									var newDiv = "<div class='topOneB mar-1 topOneBradius'>"+
									             "<table class='topTwo'>"+
												 "<tr>"+
												 "<td class='td-le'>交易日期：</td>"+
												 "<td align='right'>"+list[i].instalmentOriginalTransactionDate+"</td>"+
												 "</tr>"+
									             "</table>"+
											     "</div>"+
									             "<div class='topOneB mar-1'>"+
									             "<table class='topTwo'>"+
												 "<tr>"+
												 "<td class='td-le'>卡号：</td>"+
												 "<td align='right'>"+cardNo+"</td>"+
												 "</tr>"+
									             "</table>"+
											     "</div>"+
									             "<div class='topOneB mar-1'>"+
									             "<table class='topTwo'>"+
												 "<tr>"+
												 "<td class='td-le'>分期描述：</td>"+
												 "<td align='right'>"+list[i].instalmentRuleDescription+"</td>"+
												 "</tr>"+
									             "</table>"+
											     "</div>"+
											     "<div class='topOneB mar-1'>"+
									             "<table class='topTwo'>"+
												 "<tr>"+
												 "<td class='td-le'>分期币种：</td>"+
												 "<td align='right'>"+list[i].currencyCode+"</td>"+
												 "</tr>"+
									             "</table>"+
											     "</div>"+
									             "<div class='topOneB mar-1'>"+
									             "<table class='topTwo'>"+
												 "<tr>"+
												 "<td class='td-le'>分期金额：</td>"+
												 "<td align='right'>"+list[i].instalmentOriginalAmount+"</td>"+
												 "</tr>"+
									             "</table>"+
											     "</div>"+
									             "<div class='topOneB mar-1'>"+
									             "<table class='topTwo'>"+
												 "<tr>"+
												 "<td class='td-le'>期数：</td>"+
												 "<td align='right'>"+list[i].instalmentOriginalNumber+"</td>"+
												 "</tr>"+
									             "</table>"+
											     "</div>"+
											     "<div class='topOneB mar-1'>"+
									             "<table class='topTwo'>"+
												 "<tr>"+
												 "<td class='td-le'>完成日期：</td>"+
												 "<td align='right'>"+list[i].instalmentCompleteDate+"</td>"+
												 "</tr>"+
									             "</table>"+
											     "</div>"+
									             "<div class='topOneB mar-1 bottomOneBradius'>"+
									             "<table class='topTwo'>"+
												 "<tr>"+
												 "<td><a class='allA' href='show.do?number="+(page*10+i)+"&cardNo?="+cardNo+'>查看详情</a></td>"+
												 "</tr>"+
									             "</table>"+
											     "</div>"+
											     "<div style='margin-top: 10px;'></div>";
									document.getElementById("addViewDiv").insertAdjacentHTML(
											"beforeEnd", newDiv);
								}
								if(list.length==10){
									document.getElementById("moreButton").style.display = "block";
								}else{
									document.getElementById("moreButton").style.display = "none";
								}
							}else{
								document.getElementById("moreButton").style.display = "none";
							}
						}
				    	sending.style.visibility = "hidden";
						isClicked=false;
				    }
				}
				xmlhttp.open("GET","getMore_ajax.do?cardNo=${cardNo}&currencyCodeChinese=${currencyCodeChinese}&date="+new Date().getTime(),true);
				xmlhttp.send();
			}
		}
	</script>
</body>
</html>