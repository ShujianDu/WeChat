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
    <h2>信用卡办理进度</h2>
    <span></span>
</header>
	<div class="mg-tb-30">
		<form action="listP.do" method="post" id="submitForm">
			<div class="topOneB mar-1 topOneBradius">
				<table class="topTwo"  style="margin-bottom: -10px;">
					<tr>
						<td class="td-le">姓名：</td>
						<td><input type="text" name="name" id="name" maxlength="25"
							onchange="changeWarning();" value="" /></td>
					</tr>
				</table>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="nameWarning"
					style="color: red; font-size: 12px;"></span>
			</div>
			<div class="topOneB mar-1">
				<table class="topTwo"  style="margin-bottom: -10px;">
					<tr>
						<td class="td-le">证件类型：</td>
						<td><select name="identityType" id="identityType" onchange="changeWarning();">
								<option value="">--请选择证件类型--</option>
								<option value="1">身份证</option>
								<option value="2">护照</option>
								<option value="3">军人身份证</option>
								<option value="4">香港居民来往内地通行证</option>
								<option value="5">台湾居民往来大陆通行证</option>
								<option value="I">武装警察身份证</option>
								<option value="J">澳门居民来往内地通行证 </option>
								<option value="8">其它</option>
						</select></td>
					</tr>
				</table>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="typeWarning"
					style="color: red; font-size: 12px;"></span>
			</div>
			<div class="topOneB mar-1">
				<table class="topTwo" style="margin-bottom: -10px;">
					<tr>
						<td class="td-le">证件号：</td>
						<td><input type="text" name="identityNo" id="identityNo"
							maxlength="18" onchange="changeWarning();"
							value="${model.id}" /></td>
					</tr>
				</table>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="idNumWarning"
					style="color: red; font-size: 12px;"></span>
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

			<div class="topOneB mar-1 bottomOneBradius" onchange="changeWarning();">
			<table class="topTwo" style="margin-bottom: -10px;">
				<tr>
					<td class="td-le" style="width: 100px;">*手机交易码:</td>
					<td style="padding-left: 5px; padding-right: 0;"><input
						type="text" name="msgCode" id="code" maxlength="6"
						onchange="changeWarning();" value="${model.msgCode}" /></td>
					<td style="width: 100px; padding-left: 5px;"><input
						type="button" value="获取手机交易码" class="HandInHui" id="msgCodeButton"
						onclick="sendMessage();" /></td>
				</tr>
			</table>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="codeWarning"
				style="color: red; font-size: 12px;"></span>
		</div>
			<div class="HandIn" style="margin-top: 20px;">
				<input type="submit" value="提交" onclick="return validate_form();" />
				<input type="hidden"
					id="openId" name="openId" value="${openId}">
			</div>
			<input type="hidden" id="keycode" value="${keycode}" >	
		</form>
	</div>
	<div id="sending"
		style="position: absolute; position: fixed; height: 100%; background: #fff; width: 100%; top: 0; left: 0; visibility: hidden; filter: alpha(opacity = 50); opacity: 0.50;">
		<img src="<c:url value="/images/wechatbank/5-121204193R5-50.gif"/>"
			style="position: absolute; top: 50%; left: 50%; margin-left: -16px; margin-top: -16px;" />
	</div>
	<script type="text/javascript">
		var isClicked = false;
		var can = false;
		function validate_form() {
			if (isClicked == false) {
				var nameValue = document.getElementById("name").value;
				var typeVaule = document.getElementById("identityType").value;
				var identityNoValue = document.getElementById("identityNo").value;
				var nameWarning = document.getElementById("nameWarning");
				var typeWarning = document.getElementById("typeWarning");
				var idNumWarning = document.getElementById("idNumWarning");
				var code = document.getElementById("code");
				
				if (nameValue == null || nameValue == "") {
					nameWarning.innerHTML = "*姓名不能为空，请输入！";
					return false;
				}
				if (typeVaule == null || typeVaule == "") {
					typeWarning.innerHTML = "*证件类型不能为空，请选择！";
					return false;
				}
				if (identityNoValue == null || identityNoValue == "") {
					idNumWarning.innerHTML = "*证件号不能为空，请输入！";
					return false;
				}
				if (code.value == null || code.value == "") {
					document.getElementById("codeWarning").innerHTML = "请填写短信验证码！";
					return false;
				}
				var msgCode = /(^\d{6}$)/;
				if (msgCode.test(code.value) == false) {
					codeWarning.innerHTML = "短信验证码格式不正确，请重新输入！";
					return false;
				}
				/* var nameReg=/(^[\u4E00-\u9FA5A-Za-z0-9_]+$)/;
				if (nameReg.test(nameValue) == false){
					nameWarning,innerHTML = "姓名不能输入特殊字符，请重新输入";
					return false;
				}
				//证件号正则验证
				var idNumReg1 = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
				var idNumReg2 = /(^1[45][0-9]{7}|G[0-9]{8}|P[0-9]{7}|S[0-9]{7,8}|D[0-9]+$)/;
				var idNumReg3 = ;
				var idNumReg4 = ;
				var idNumReg5 = ;
				if (typeValue == "01") {     //身份证号验证
					if (idNumReg1.test(identityNoValue) == false) {
						idNumWarning.innerHTML = "身份证号格式不正确，请重新输入！";
						return false;
					}
				}else if(typeValue == "02"){  //护照号码验证
					if (idNumReg2.test(identityNoValue) == false) {
						idNumWarning.innerHTML = "护照号码格式不正确，请重新输入！";
						return false;
					}
				}else if(typeValue == "03"){  //军官证号验证
					if (idNumReg3.test(identityNoValue) ==false){
						idNumWarning.innerHTML = "军官证号格式不正确，请重新输入！";
						return false;
					}
				}else if(typeValue == "04"){  //回乡证号验证
					if (idNumReg4.test(identityNoValue) ==false){
						idNumWarning.innerHTML = "回乡证号格式不正确，请重新输入！";
						return false;
					}
				}else if(typeValue == "05"){  //同胞证号验证
					if (idNumReg5.test(identityNoValue) ==false){
						idNumWarning.innerHTML = "同胞证号格式不正确，请重新输入！";
						return false;
					}
				} */
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
			} else {
				return false;
			}
		}

		function changeWarning() {
			document.getElementById("nameWarning").innerHTML = "";
			document.getElementById("typeWarning").innerHTML = "";
			document.getElementById("idNumWarning").innerHTML = "";
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
			var openId = document.getElementById("openId").value;
			var identityNo = document.getElementById("identityNo").value;
			var type = document.getElementById("identityType").value;
			var mobilNo = document.getElementById("mobilNo").value;
			var verificationCode = document.getElementById("verificationCode").value;
			
			
			if (type == null || type == "") {
				typeWarning.innerHTML = "*证件类型不能为空，请选择！";
				return false;
			}
			if (identityNo == null || identityNo == "") {
				idNumWarning.innerHTML = "*证件号不能为空，请输入！";
				return false;
			}
			if (mobilNo == null || mobilNo == "") {
				mobilNoWarning.innerHTML = "*手机号不能为空，请输入！";
				return false;
			}
			if (verificationCode == null || verificationCode == "") {
				verificationCodeWarning.innerHTML = "*验证码不能为空，请输入！";
				return false;
			}
			
			var xmlhttp;
			if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
				xmlhttp = new XMLHttpRequest();
			} else {// code for IE6, IE5
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			//获得来自服务器的响应
			var result;
			var resultIndex;
			var resultOne;
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					result = xmlhttp.responseText;
					if (result != null && result != "") {
						resultIndex=result.indexOf(",")
						if(resultIndex!=-1){
							resultOne=result.substring(resultIndex+1);
							document.getElementById("keycode").value=resultOne;
							result=result.substring(0,resultIndex);
						}
						
						if (result == "exception"|| result=="false") {
							window.location.href = "../error.html";
						}else if(result=="errorCode"){
							document.getElementById("verificationCodeWarning").innerHTML = "您填写的验证码有误，请重新输入！";
						}else if(result=="wrongMobilNo"){
							document.getElementById("mobilNoWarning").innerHTML = "您填写的手机号有误，请重新输入!";
						}else{
						
							buttonTimeOut();
						}
					}
				}
			}
			var keycode=document.getElementById("keycode").value;
			xmlhttp.open("POST", "getMsgCode_ajax.do?openId=" + openId
					+ "&identityNo="+identityNo+"&type="+type+"&mobilNo=" +mobilNo+"&verificationCode="+encodeURI(verificationCode)+"&date=" + new Date().getTime()+"&keycode="+keycode, false);
			xmlhttp.send();

		}

		function check() {
			var openId = document.getElementById("openId").value;
			var identityNo = document.getElementById("identityNo").value;
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
			xmlhttp.open("POST", "checkMsgCode_ajax.do?openId=" + openId
					+"&identityNo="+identityNo+ "&code=" + code + "&date=" + new Date().getTime(), false);
			xmlhttp.send();
		}

	</script>
</body>
</html>

