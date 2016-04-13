<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<%@include file="../../base_pages/wxjs.jsp"%>
<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>中国银行信用卡</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap.min.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/wechatbank/index.css"/>" />
<script src="../js/jquery-2.1.1.min.js"></script>

<script type="text/javascript">
	window.onload = function() {
		if (${fn:length(defCardNo)== 0}) {
			var bindDefCard = document.getElementById("bindDefCard");
			bindDefCard.style.display = "block";
		}
	}
	
	function changeDefCard(){
		var yes = document.getElementById("yes");
		yes.style.display = "none";
		var bindDefCard = document.getElementById("bindDefCard");
		bindDefCard.style.display = "block";
	}
</script>
<%@include file="../../base_pages/wxReadyFunction.jsp"%>
</head>
<body>
	<header>
	    <span></span>
	    <h2>默认卡片绑定</h2>
	    <span></span>
	</header>
		<c:if test="${fn:length(defCardNo) gt 0}">
		<div class="container mg-top">
			<div class="img-bg text-center table-shadow">
				<div style="height:10px;"></div>
				<h5 class="" style="font-weight: 300; font-size:4em;">您目前绑定的卡片是<br>
				<p class="strong" style="margin-top:10px">
					<c:out value="${fn:substring(defCardNo,0,4)}" />********<c:out value="${fn:substring(defCardNo,fn:length(defCardNo)-4,fn:length(defCardNo))}" />
				</p>
				</h5>
			</div>
		</div>
			<div class="container HandIn" id="yes" style="display:block; margin-top:10px;">
				<input class="btn btn-sm btn-default btn-block btn-shadow" type="button" value="更改绑定" onclick="changeDefCard();" />
			</div>
		</c:if>
			<form action="bindDefCardP.do" method="post" id="submitForm">
		<div style="display:none; margin-top:10px;" id="bindDefCard">
				<div class="container">
					<div class="table-shadow">
					<ul id="mytab" class="nav nav-pills nav-stacked font-lg" role="tablist">
							<input type="hidden"  name="defaultCard" id="defaultCard"/>
									<c:forEach items="${cardListCrypt}" var="item">
									<li class="" role="presentation">
										<a href="#" onclick="changeCard(this,'${fn:substring(item,17,66)}')">${fn:substring(item, 0, 16)}
										  <span name="aSpan" class="glyphicon glyphicon-ok pull-right hidden"></span>
										</a>
									</li>
									</c:forEach>
				</div>
				  <p id="defaultCardWarning" class="text-center" style="color: red; font-size:3.75em; margin-top:10px;"></p>
					<div class="HandIn" style="margin-top:10px;">
					<input class="btn btn-default btn-block btn-shadow" type="submit" value="提交" onclick="return validate_form();" />
					<input type="hidden" id="openId" name="openId" value="${model.openId}">
					</div>
				</div>
			</div>
			</form>

	<div id="sending"
		style="position: absolute; position: fixed; height: 100%; background: #fff; width: 100%; top: 0; left: 0; visibility: hidden; filter: alpha(opacity = 50); opacity: 0.50;">
		<img src="<c:url value="/images/wechatbank/5-121204193R5-50.gif"/>"
			style="position: absolute; top: 50%; left: 50%; margin-left: -16px; margin-top: -16px;" />
	</div>

	<script type="text/javascript">
		var isClicked=false;
		function validate_form() {
			if(isClicked==false){
				var defaultCardValue = document.getElementById("defaultCard").value;
				if (defaultCardValue == null || defaultCardValue == "") {
					document.getElementById("defaultCardWarning").innerHTML = "请选择默认卡";
					return false;
				}
				var sending = document.getElementById("sending");
				sending.style.visibility = "visible";
				isClicked=true;
				return true;
			}else{
				return false;
			}
		}
		
		function showCardStyle() {
			var cardNo = document.getElementById("defaultCard").value;
			document.getElementById("defaultCardWarning").innerHTML = "";
			var style = document.getElementById(cardNo).value;
			document.getElementById("cardStyle").innerHTML = style;
		}
		
		
		function changeCard(a,cardNo)
		{
			$("span[name='aSpan']").attr("class","glyphicon glyphicon-ok pull-right hidden");
			var aSpan=$(a).children();
			aSpan.attr("class","glyphicon glyphicon-ok pull-right");
			$("#defaultCard").val(cardNo);
		}
	</script>
</body>
</html>
