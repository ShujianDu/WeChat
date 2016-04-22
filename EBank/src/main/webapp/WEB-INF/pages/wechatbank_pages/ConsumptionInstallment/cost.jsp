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
	    <h2>消费分期信息</h2>
	    <span></span>
	</header>
	
	<form action="confirm.do" method="post" id="submitForm" name="submitForm">
		<input type="hidden" id="accountKeyOne" name="accountKeyOne" value="${ciinfo.accountKeyOne}">	
		<input type="hidden" id="accountKeyTwo" name="accountKeyTwo" value="${ciinfo.accountKeyTwo}">
		<input type="hidden" id="currencyCode" name="currencyCode" value="${ciinfo.currencyCode}">
		<input type="hidden" id="billDateNo" name="billDateNo" value="${ciinfo.billDateNo}">
		<input type="hidden" id="transactionNo" name="transactionNo" value="${ciinfo.transactionNo}">
		<input type="hidden" id="transactionAmount" name="transactionAmount" value="${ciinfo.transactionAmount}">
		<input type="hidden" id="cardNo" name="cardNo" value="${ciinfo.cardNo}">
		<input type="hidden" id="accountNoID" name="accountNoID" value="${ciinfo.accountNoID}">
		<input type="hidden" id="installmentPeriods" name="installmentPeriods" value="${ciinfo.installmentPeriods}">
		
		<div class="topOneB mar-1 topOneBradius">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>卡号：</td>
					<td align="right"><c:out value="${fn:substring(cost.cardNo,0,4)}" />********<c:out value="${fn:substring(cost.cardNo,fn:length(cost.cardNo)-4,fn:length(cost.cardNo))}" /></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>入账币种：</td>
					<td align="right"><c:out value="${cost.currencyCode}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>入账金额：</td>
					<td align="right"><c:out value="${cost.installmentAmount}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>分期期数：</td>
					<td align="right"><c:out value="${cost.installmentsNumber}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>分期手续费：</td>
					<td align="right"><c:out value="${cost.installmentFee}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>手续费收取方式：</td>
					<c:if test="${ciinfo.isfeeFlag eq 0}">
					  <input type="hidden" name="isfeeFlag" value="0"/>
					  <td align="right">一次性收取</td>
					</c:if>
					<c:if test="${ciinfo.isfeeFlag eq 1}">
					  <input type="hidden" name="isfeeFlag" value="1"/>
					  <td align="right">分期收取</td>
					</c:if>
				</tr>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>分期后首期应还金额：</td>
					<td align="right"><c:out value="${cost.installmentsAlsoAmountFirst}"/></td>
				</tr>
			</table>
		</div>
		<div class="topOneB mar-1">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>分期后其余每期应还金额：</td>
					<td align="right"><c:out value="${cost.installmentsAlsoAmountEach}"/></td>
				</tr>
			</table>
		</div>
		
		<div class="topOneB mar-1">
				<table class="topTwo" style="margin-bottom: -10px;">
					<tr>
						<td class="td-le">手机号：</td>
						<td><input type="text" name="mobilNo" id="mobilNo"
							maxlength="11" onchange="changeWarning();" />
					    </td>
					</tr>
				</table>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="mobilNoWarning"
					style="color: red; font-size: 12px;"></span>
			</div>
			<div class="topOneB mar-1">
					<table class="topTwo">
						<tr>
							<td>验证码:</td>
							<td>
								<table width="100%">
									<tr>
										<td class="paddingLeft_0"><input type="text"
											maxlength="4" name="verificationCode" id="verificationCode"
											onchange="changeWarning();" /></td>
										<td width="70"><img src="../showradomcode.jsp" /></td>
									</tr>
								</table>

							</td>
						</tr>
					</table>

					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span
						id="verificationCodeWarning" style="color: red; font-size: 12px;"></span>
				</div>
		
		<div class="topOneB mar-1 bottomOneBradius"	onchange="changeWarning();">
			<table class="topTwo" style="margin-bottom: -10px;">
				<tr>
					<td class="td-le" style="width: 100px;">*手机交易码：</td>
					<td style="padding-left: 5px; padding-right: 0;">
						<input	type="text" name="msgCode" id="code" maxlength="6" onchange="changeWarning();" value="${model.msgCode}" />
					</td>
					<td style="width: 100px; padding-left: 5px;">
						<input type="button" value="获取手机交易码" class="HandInHui" id="msgCodeButton" onclick="sendMessage();" />
					</td>
				</tr>
			</table>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="codeWarning"	style="color: red; font-size: 12px;"></span>
		</div>
		<div class="HandIn" style="margin-top: 10px; margin-bottom: 10px;">
			<input  id="submitButton" type="submit" value="确认" onclick="return validate_form();" />
		</div>
	</form>
	<div class="topOneB mar-1 allOneBradius" style="margin-bottom: 10px">
		<table class="topTwo" style="margin-bottom: 10px;">
			<tr>
				<td>温馨提示：提交分期申请前请仔细核对信息，分期提交成功后将不能对分期的期数和金额进行修改；申请分期成功后：1、手续费一次性收取，您分期的首期金额与手续费将计入最近一期账单；2、手续费分次收取，您的分期手续费将分次进入每期账单。分期业务详细介绍请的登录中国银行官网查询。</td>
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
	var idNumberWarning = document.getElementById("idNumWarning");
	var nameWarning = document.getElementById("familyNameWarning");
	function validate_form() {
		if (isClicked == false) {
			var code = document.getElementById("code");
			if (code.value == null || code.value == "") {
				document.getElementById("codeWarning").innerHTML = "请填写短信验证码！";
				return false;
			}
			var msgCode = /(^\d{6}$)/;
			if (msgCode.test(code.value) == false) {
				codeWarning.innerHTML = "短信验证码格式不正确，请重新输入！";
				return false;
			}
			if (document.getElementById("timeoutdiv1").style.display == "block") {
				return false;
			}
			var sending = document.getElementById("sending");
			sending.style.visibility = "visible";
			isClicked = true;
			//验证短信码是否正确
			check();
			if (!can) {
				codeWarning.innerHTML = "短信验证码错误，请您重新输入！";
				sending.style.visibility = "hidden";
				isClicked = false;
				return false;
			}
			return true;
		}
	}

	function changeWarning() {
		document.getElementById("codeWarning").innerHTML = "";
	}
	var i = 60;
	function buttonTimeOut() {
		var msgCodeButton = document.getElementById("msgCodeButton");
		i--;
		if (i == 0) {
			i = 60;
			msgCodeButton.disabled = "";
			msgCodeButton.value = "获取验证码";
		} else {
			msgCodeButton.disabled = "disabled";
			msgCodeButton.value = i + "秒";
			setTimeout("buttonTimeOut()", 1000);
		}
	}

	function sendMessage() {
		document.getElementById("verificationCodeWarning").innerHTML = "";
		document.getElementById("mobilNoWarning").innerHTML = "";
		var mobilNo = document.getElementById("mobilNo").value;
		var verificationCode = document.getElementById("verificationCode").value;
		if (mobilNo == null || mobilNo == "") {
			mobilNoWarning.innerHTML = "*手机号不能为空，请输入！";
			return false;
		}
		if (verificationCode == null || verificationCode == "") {
			verificationCodeWarning.innerHTML = "*验证码不能为空，请输入！";
			return false;
		}
		
		
		var openId = document.getElementById("openId").value;
		//var openId = "oFDatjpd8-RY-O__qTWgVW1fhh6w";
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		//获得来自服务器的响应
		var result;
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				result = xmlhttp.responseText;
				if (result != null && result != "") {
					if (result == "exception"||result=="false") {
						window.location.href = "../error.html";
					}else if(result=="errorCode"){
						document.getElementById("verificationCodeWarning").innerHTML = "您填写的验证码有误，请重新输入!";
					}else if(result=="wrongMobilNo"){
						document.getElementById("mobilNoWarning").innerHTML = "您填写的手机号有误，请重新输入!";
					}else{
						buttonTimeOut();
					}
				}
			}
		}
		xmlhttp.open("POST", "getMsgCode_ajax.do?openId=" + openId
				+ "&date=" + new Date().getTime()+"&mobilNo=" +mobilNo+"&verificationCode="+encodeURI(verificationCode), false);
		xmlhttp.send();

	}

	function check() {
		var openId = document.getElementById("openId").value;
		var code = document.getElementById("code").value;
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		//获得来自服务器的响应
		var result;
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				result = xmlhttp.responseText;
				if (result != null && result != "") {
					if (result == "exception") {
						window.location.href = "../error.html";
						can = false;
					} else if (result == "true") {
						can = true;
					} else {
						can = false;
					}
				}
			}

		}
		xmlhttp.open("POST", "checkMagCode_ajax.do?openId=" + openId
				+ "&code=" + code + "&date=" + new Date().getTime(), false);
		xmlhttp.send();
	}

	function checkCustInfo(idOrName) {
		var openId = document.getElementById("openId");
		var idNumber = document.getElementById("idNum");
		var name = document.getElementById("familyName");
		if (idOrName == "idno") {
			if (idNumber.value == "") {
				return;
			}
		}
		if (idOrName == "name") {
			if (name.value == "") {
				return;
			}
		}
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		//获得来自服务器的响应
		var result;
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				result = xmlhttp.responseText;
				if (result != null && result != "") {
					if (result == "exception") {
						window.location.href = "../error.html";
					} else if (result == "refresh") {
						location.reload();
					} else if (result == "false") {
						if (idOrName == "idno") {
							idNumberWarning.innerHTML = "证件号有误，请重新输入！";
						} else if (idOrName == "name") {
							if (idNumber == null && idNumber == '') {
								nameWarning.innerHTML = "请先输入证件号！";
							} else {
								nameWarning.innerHTML = "姓名有误，请重新输入！";
							}
						}
					} else if (result == "true") {
						if (idOrName == "idno") {
							idNumberWarning.innerHTML = "";
						} else if (idOrName == "name") {
							nameWarning.innerHTML = "";
						}
					} else {
						document.getElementById("timeoutdiv1").style.display = "block";
						document.getElementById("timeoutdiv2").style.display = "block";
					}
				}
			}
		}
		xmlhttp.open("POST", "checkCustInfo_ajax.do?openId=" + openId.value
				+ "&idOrName=" + idOrName + "&idNumber=" + idNumber.value
				+ "&name=" + name.value + "&date=" + new Date().getTime(),
				false);
		xmlhttp.send();
	}
	</script>
</body>
</html>