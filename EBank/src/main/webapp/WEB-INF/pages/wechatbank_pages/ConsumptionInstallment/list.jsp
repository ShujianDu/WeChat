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
		//消费信息集合长度
		var listlength=${fn:length(pageList)};
		//一页显示条数,页面传值,可配
		var onepage = '${onepage}';
		//再次查询是否有下一页，0:没有，1：有
		var isFollowUp='${isFollowUp}';
		//记录日志
		console.log('--listlength:'+listlength+'--onepage:'+onepage+"--isFollowUp:"+isFollowUp);
		if(listlength==onepage&&isFollowUp==1){
			$("#moreButton").css('display','block');
		}else{
			$("#moreButton").css('display','none');
		}
	}
</script>
<%@include file="../../base_pages/wxReadyFunction.jsp"%>
</head>

<body>
<header>
    <span></span>
    <h2>消费分期</h2>
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
							<c:if test="${cardNo== fn:substringAfter(item, ',')}">
							<option selected="selected"value="${fn:substringAfter(item, ',')}" name="cardNo">
									${fn:substring(item, 0, 16)}
								</option>
							</c:if>
							<c:if test="${cardNo!= fn:substringAfter(item, ',')}">
							<option value="${fn:substringAfter(item, ',')}" name="cardNo">
									${fn:substring(item, 0, 16)}
								</option>
							</c:if>
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
							<select name="currencyCode" id="dateSelect">
								<option value="CNY">人民币</option>
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
								<span class="black">尊敬的客户，您当前没有可以申请消费分期的消费交易，感谢您的支持。</span>
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
								<td class="td-le">交易日期：</td>
								<td align="right"><c:out value="${item.transactionDate}" /></td>
							</tr>
						</table>
					</div>
					<div class="topOneB mar-1">
						<table class="topTwo">
							<tr>
								<td><c:out value="${item.currencyChinaCode}" /></td>
								<td align="right"><c:out value="${item.originalTransactionAmount}" /></td>
							</tr>
						</table>
					</div>
					<div class="topOneB mar-1">
						<table class="topTwo">
							<tr>
								<td colspan="2"><c:out value="${item.transactionDescription}" /></td>
							</tr>
						</table>
					</div>
					<div class="topOneB mar-1 bottomOneBradius">
						<table class="topTwo">
							<tr>
								<td colspan='2'>
									<a class="allA"	href="show.do?accountKeyOne=${item.accountID}&accountKeyTwo=${item.accountedID}
									&transactionCurrencyCode=${item.transactionCurrencyCode}&billDateNo=${item.cycleNumber}
									&transactionAmount=${item.transactionAmount}&cardNo=${cardNo}&accountNoID=${item.accountNoID}
									&transactionNo=${item.transactionNo}&transactionDescription=${item.transactionDescription}
									&originalCurrencyCode=${item.originalCurrencyCode}&originalTransactionAmount=${item.originalTransactionAmount}">办理消费分期</a>
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
	<span id="noBillingWarning"
				style="color: #999999; font-size: 13px; line-height: 40px; margin-left: 16px;"></span>
	<input type="hidden" id="startnum" value="${startnum }"/>
	<input type="hidden" id="cardNo" value="${cardNo }"/>
	<input type="hidden" id="originalCurrencyCode" value="${currencyCode }"/>
	<div id="sending"
		style="position: absolute; position: fixed; height: 100%; background: #fff; width: 100%; top: 0; left: 0; visibility: hidden; filter: alpha(opacity =  50); opacity: 0.50;">
		<img src="<c:url value="/images/wechatbank/5-121204193R5-50.gif"/>"
			style="position: absolute; top: 50%; left: 50%; margin-left: -16px; margin-top: -16px;" />
	</div>
	<script type="text/javascript">
		var isClicked = false;
		function validate_form() {
			if (isClicked == false) {
				var sending =  $("#sending");
				sending.css({visibility:"visible"}); 
				isClicked = true;
				return true;
			} else {
				return false;
			}
		}
		function getMore() {
			if(isClicked==false){
				var sending = $("#sending");
				sending.css({visibility:"visible"});
				isClicked = true;
				var cardNo = $("#cardNo").val();
				var startnum = $("#startnum").val();
				var originalCurrencyCode = $("#originalCurrencyCode").val();
				console.log("hidden value cardNo:"+cardNo+"--startnum:"+startnum+"--originalCurrencyCode:"+originalCurrencyCode);
				var noBillingWarning = $("#noBillingWarning");
				noBillingWarning.text("");
				$.ajax({
		            url: "getMore_ajax.do",
		            data: {
		            	cardNo: cardNo,
		            	startnum: startnum,
		            	originalCurrencyCode:originalCurrencyCode
		            },
		            type: "post",
		            dataType: "json",
		            async: false,
		            success: function (json) {
		            	if(json==""){
		            		noBillingWarning.text("*您好，没有更多的消费信息！");
				    		$("#moreButton").css('display','none');
				    		sending.css({visibility:"hidden"});
							isClicked=false;
							return;
						}else if(json==null){
							window.location.href="../error.html";
							sending.css({visibility:"hidden"});
							isClicked=false;
							return;
						}else{
							for(var i=0;i<json.length;i++){
								var newDiv = "<div class='topOneB mar-1 topOneBradius'>"+
					             "<table class='topTwo'>"+
								 "<tr>"+
								 "<td class='td-le'>交易日期：</td>"+
								 "<td align='right'>"+json[i].transactionDate+"</td>"+
								 "</tr>"+
					             "</table>"+
							     "</div>"+
							     "<div class='topOneB mar-1'>"+
					             "<table class='topTwo'>"+
								 "<tr>"+
								 "<td>"+json[i].currencyChinaCode+"</td>"+
								 "<td align='right'>"+json[i].originalTransactionAmount+"</td>"+
								 "</tr>"+
					             "</table>"+
							     "</div>"+
							     "<div class='topOneB mar-1'>"+
					             "<table class='topTwo'>"+
								 "<tr>"+
								 "<td colspan='2'>"+json[i].transactionDescription+"</td>"+
								 "</tr>"+
					             "</table>"+
							     "</div>"+
					             "<div class='topOneB mar-1 bottomOneBradius'>"+
					             "<table class='topTwo'>"+
								 "<tr>"+
								 "<td colspan='2'><a class='allA' href='show.do?accountKeyOne="+json[i].accountID+
								"&accountKeyTwo="+json[i].accountedID+"+&transactionCurrencyCode="+json[i].transactionCurrencyCode+
								"&billDateNo="+json[i].cycleNumber+"&transactionAmount="+json[i].transactionAmount+
								"&cardNo="+cardNo+"&accountNoID="+json[i].accountNoID+"&transactionNo="+json[i].transactionNo+
								"&transactionDescription="+json[i].transactionDescription+"&originalCurrencyCode="+json[i].originalCurrencyCode+
								"&originalTransactionAmount="+json[i].originalTransactionAmount+"'>办理消费分期</a></td>"+
								 "</tr>"+
					             "</table>"+
							     "</div>"+
							     "<div style='margin-top: 10px;'></div>";
							     $("#addViewDiv").append(newDiv);
							}
						//判断是否显示获取更多按钮
						if(json.length==json[0].onepage&&json[0].isFollowUp==1){
							$("#startnum").val(json[0].startnum);
							$("#moreButton").css('display','block');
						}else{
							$("#moreButton").css('display','none');
						}
		            	sending.css({visibility:"hidden"});
						isClicked=false;
						}
		            }
		        });
				
			}
		}
	</script>
</body>
</html>