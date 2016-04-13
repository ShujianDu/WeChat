<%@ page import="com.yada.wechatbank.model.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<%@include file="../../base_pages/wxjs.jsp"%>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>中国银行信用卡</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap.min.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/css/wechatbank/index.css"/>" />
<%@include file="../../base_pages/wxReadyFunction.jsp"%>
</head>

<body>
	<div class="logo">
		<img src="../images/logo.gif" />
	</div>
	<div class="lineStyle">
		<span class="block">用户身份验证</span>
	</div>
	<div class="container">
		<form action="bindingM.do" method="post" id="submitForm">
			<div class="table-shadow">
				<table class="table table-none">
					<tr>
						<td class="td-left td-left-top">证件类型</td>
						<td class="td-center td-right-top" colspan="2">
							<div class="select-none">
							<select class="select "id="idType" name="idType">
							<option value="">请选择</option>
							<option value="01"
									<c:if test="${'01' eq model.idType}">selected</c:if>>居民身份证</option>
							<option value="03"
									<c:if test="${'03' eq model.idType}">selected</c:if>>护照</option>
							<option value="05"
									<c:if test="${'05' eq model.idType}">selected</c:if>>军人身份证</option>
							<option value="06"
									<c:if test="${'06' eq model.idType}">selected</c:if>>武装警察身份证</option>
							<option value="04"
									<c:if test="${'04' eq model.idType}">selected</c:if>>户口簿</option>
							<option value="47"
									<c:if test="${'47' eq model.idType}">selected</c:if>>港澳居民来往内地通行证（香港）</option>
							<option value="48"
									<c:if test="${'48' eq model.idType}">selected</c:if>>港澳居民来往内地通行证（澳门）</option>
							<option value="49"
									<c:if test="${'49' eq model.idType}">selected</c:if>>台湾居民往来大陆通行证</option>
							<option value="08"
									<c:if test="${'08' eq model.idType}">selected</c:if>>外交人员身份证</option>
							<option value="09"
									<c:if test="${'09' eq model.idType}">selected</c:if>>外国人居留许可证</option>
							<option value="10"
									<c:if test="${'10' eq model.idType}">selected</c:if>>边民出入境通行证</option>
							<option value="11"
									<c:if test="${'11' eq model.idType}">selected</c:if>>其他</option>
							</select>
							</div>
						</td>
					</tr>
					<tr>
						<td class="td-left">证件号码</td>
						<td class="td-center td-right-border" colspan="2">
							<input class="form-control"  type="text" placeholder="请输入证件号码" name="idNumber" id="idNumber" maxlength="18" onchange="changeWarning();" value="${model.idNumber}" />
						</td>
					</tr>
					<tr>
						<td class="td-left">手机号码</td>
						<td class="td-center td-right-border" colspan="2">
							<input class="form-control" type="text" placeholder="请输入预留手机号码" name="mobilNo" id="mobilNo"
								   maxlength="11" onchange="changeWarning();" /></td>
					</tr>
					<tr>
						<td class="td-left">查询密码</td>
						<td class="td-center td-right-border" colspan="2">
							<input class="form-control" type="password"  placeholder="请输入6位查询密码"
								   name="passwordQuery"
								   id="passwordQuery" maxlength="6"/>
						</td>
					</tr>
					<tr>
						<td class="td-left">附加验证码</td>
						<td class="td-center">
							<input class="form-control" type="text" placeholder="请输入附加验证码"
								   maxlength="4" name="verificationCode" id="verificationCode"
								   onchange="changeWarning();" />
						</td>
						<td class="td-right td-right-border" ><img class="tab-img" src="../showradomcode.jsp" /></td>
					</tr>
					<tr>
						<td class="td-left td-bottom-left">手机验证码</td>
						<td class="td-center td-bottom"><input class="form-control" type="text" placeholder="请输入手机验证码" id="mobileCode" name="mobileCode"/></td>
						<td class="td-right td-bottom-right">
							<input type="button" class="btn btn-xs btn-default btn-smaller" name="getMobileCodeButton" id="getMobileCodeButton" value="获取验证码" onclick="getMobileCodeFunction()">
						</td>
					</tr>
				</table>
			</div>
			<div class="checkbox text-center" style="margin-top:10px; margin-bottom:10px;"><label class="box-center"> 
			        <input type="checkbox" id="check"></td>
						已阅读<a class="text-info" href='duty.do?openId=${model.openId}'>客户须知与免责声明</a></label>
						
						<c:if test="${msg eq 1}">
							<span class="help-block fontsize text-center fontred" id="bindWarning" style="display: block;">
								未找到您名下有效的信用卡卡号
							</span>
						</c:if>
						<c:if test="${msg eq 2}">
							<span class="help-block fontsize text-center fontred" id="bindWarning" style="display: block;">
								证件号或密码有误！
							</span>
						</c:if>
						<c:if test="${msg eq 3}">
							<span class="help-block fontsize text-center fontred" id="bindWarning" style="display: block;">
								短信验证码有误请重新输入！
							</span>
						</c:if>
			            <span class="help-block fontsize text-center fontred" id="spanMess"></span>
			</div>
			
			<div class="HandIn">
				<input class="btn btn-sm btn-default btn-block btn-shadow"
				       type="submit" value="提&nbsp;&nbsp;交" onclick="return validate_form();" />
				<input type="hidden"
					id="openId" name="openId" value="${model.openId}"> <input
					type="hidden" id="toUser" name="toUser" value="${model.toUser}">
			</div>
			<input type="hidden" id="keycode" value="${keycode}">
			
			
		</form>
	</div>
	 <div class="text-center ad-bottom"><label class="box-center">
			<a class="text-info" href="https://apply.mcard.boc.cn/apply/mobile/index?channelId=2&customerId=4900001">还没有中国银行信用卡，马上申请吧</a></label>
		</div>
	<div id="sending"
		style="position: absolute; position: fixed; height: 100%; background: #fff; width: 100%; top: 0; left: 0; visibility: hidden; filter: alpha(opacity =             50); opacity: 0.50;">
		<img src="<c:url value="/images/wechatbank/5-121204193R5-50.gif"/>"
			style="position: absolute; top: 50%; left: 50%; margin-left: -16px; margin-top: -16px;" />
	</div>
	
	<script type="text/javascript">
		var isClicked = false;
		function validate_form() {

			    spanMess.innerHTML = "";
				var idNumber = document.getElementById("idNumber");
				var idType = document.getElementById("idType");
				var mobileCode = document.getElementById("mobileCode");
				var passwordQuery = document.getElementById("passwordQuery");
				var verificationCode = document
						.getElementById("verificationCode");
				var check = document.getElementById("check");
				var sending = document.getElementById("sending");
				
				var spanMess = document.getElementById("spanMess");
				if (idType.value == null || idType.value == "") {
					spanMess.innerHTML = "请您选择证件类型后再继续操作";
					return false;
				}
				if (idNumber.value == null || idNumber.value == "") {
					spanMess.innerHTML = "证件号不能为空，请输入！";
					return false;
				}

				if (passwordQuery.value == null || passwordQuery.value == "") {
					spanMess.innerHTML = "查询密码不能为空，请输入！";
					return false;
				}
				var pswReg = /(^\d{6}$)/;
				if (pswReg.test(passwordQuery.value) == false) {
					spanMess.innerHTML = "查询密码格式不正确，请重新输入！";
					return false;
				}
				if (verificationCode.value == null
							|| verificationCode.value == "") {
						spanMess.innerHTML = "附加验证码不能为空，请输入！";
						return false;
				}
				var mobileCodeValue = mobileCode.value.replace(/\s/gi, '');
				if (mobileCodeValue == null || mobileCodeValue == "") {
					spanMess.innerHTML = "手机验证码不能为空，请输入！";
					return false;
				}
				if (check.checked == "") {
					spanMess.innerHTML = "请勾选已阅读免责声明！";
					return false;
				}
				sending.style.visibility = "visible";
				isClicked = true;
				return true;
		}

		function changeWarning() {
			document.getElementById("spanMess").innerHTML = "";
			var bindWarning = document.getElementById("bindWarning");
			if (bindWarning) {
				bindWarning.style.display = "none";
			}
		}

		function getMobileCodeFunction() {
			var openId = document.getElementById("openId").value;
			var idType = document.getElementById("idType").value;
			var identityNo = document.getElementById("idNumber").value;
			var mobilNo = document.getElementById("mobilNo").value;
			var verificationCode = document.getElementById("verificationCode").value;
			var spanMess = document.getElementById("spanMess").value;
			if (idType == null || idType == "") {
				document.getElementById("spanMess").innerHTML = "请选择证件类型";
				return false;
			}
			if (identityNo == null || identityNo == "") {
				document.getElementById("spanMess").innerHTML = "请输入证件号码";
				return false;
			}
			if (mobilNo == null || mobilNo == "") {
				spanMess.innerHTML = "手机号不能为空，请输入！";
				return false;
			}
			if (verificationCode == null || verificationCode == "") {
				spanMess.innerHTML = "附加验证码不能为空，请输入！";
				return false;
			}
			failureSendSMS = "";
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
						resultIndex = result.indexOf(",")
						if (resultIndex != -1) {
							resultOne = result.substring(resultIndex + 1);
							document.getElementById("keycode").value = resultOne;
							result = result.substring(0, resultIndex);
						}
						if (result == "exception" || result == "false") {
							failureSendSMS = "false";
							i = 0;
							document.getElementById("spanMess").innerHTML = "证件号错误，请核对证件号！";
						} else if (result == "locked") {
							window.location.href = "./locked.do";
						} else if (result == "keycodeexception") {
							window.location.href = "../error.html";
						} else if (result == "noMobileNumber") {
							failureSendSMS = "false";
							i = 0;
							document.getElementById("spanMess").innerHTML = "未获取到您申请时填写的手机号！";
						} else if (result == "wrongMobilNo") {
							document.getElementById("spanMess").innerHTML = "您填写的手机号有误，请重新输入!";
						} else if (result == "errorCode") {
							document.getElementById("spanMess").innerHTML = "您填写的验证码有误，请重新输入！";
						} else {
							buttonTimeOut();
						}
					}
				}
			}
			var keycode = document.getElementById("keycode").value;
			xmlhttp.open("POST", "getSMSCode_ajax.do?openId=" + openId
					+ "&idType=" + idType + "&identityNo=" + identityNo
					+ "&mobilNo=" + mobilNo + "&verificationCode="
					+ encodeURI(verificationCode) + "&date="
					+ new Date().getTime() + "&keycode=" + keycode, true);
			xmlhttp.send();
		}

		var i = 60;
		var failureSendSMS = "";
		function buttonTimeOut() {
			var msgCodeButton = document.getElementById("getMobileCodeButton");
			i--;
			if (i <= 0) {
				i = 60;
				if (failureSendSMS == "") {
					msgCodeButton.disabled = "";
					msgCodeButton.value = "重新获取";
				}
			} else {
				msgCodeButton.disabled = "disabled";
				msgCodeButton.value = i + "秒";
				setTimeout("buttonTimeOut()", 1000);
			}
		}

		var j = 30;
		function disabledButton() {
			var smsCodeButton = document.getElementById("getMobileCodeButton");
			j--;
			if (j <= 0) {
				j = 30;
				smsCodeButton.disabled = "";
				smsCodeButton.value = "获取验证码";
			} else {
				smsCodeButton.disabled = "disabled";
				smsCodeButton.value = j + "秒";
				setTimeout("disabledButton()", 1000);
			}
		}
	</script>
</body>
</html>
