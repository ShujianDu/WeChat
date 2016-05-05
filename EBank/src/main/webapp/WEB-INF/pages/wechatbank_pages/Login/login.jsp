<%@ page import="com.yada.wechatbank.model.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>中国银行信用卡</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/bootstrap.min.css"/>" />

<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/wechatbank/index.css"/>" />
<script type="text/javascript">
</script>
</head>

<body>
	<div class="logo">
    	<img src="../images/logo.gif" />
	</div>
	<div class="lineStyle">
		<span class="block" style="width:160px;">用户登录</span>
	</div>
	<div class="container">
		<form action="list.do" method="post"  id="submitForm">
			<div class="table-shadow">
				<table class="table table-none">
					<tr>
						<td class="td-left td-left-top">证件类型</td>
						<td class="td-center td-right-top" colspan="2">
							<div class="select-none">
								<select class="select " id="identityType" name="identityType">
									<option value="">请选择</option>
									<option value="01"
											<c:if test="${'01' eq model.identityType}">selected</c:if>>居民身份证
									</option>
									<option value="03"
											<c:if test="${'03' eq model.identityType}">selected</c:if>>护照
									</option>
									<option value="05"
											<c:if test="${'05' eq model.identityType}">selected</c:if>>军人身份证
									</option>
									<option value="06"
											<c:if test="${'06' eq model.identityType}">selected</c:if>>武装警察身份证
									</option>
									<option value="04"
											<c:if test="${'04' eq model.identityType}">selected</c:if>>户口簿
									</option>
									<option value="47"
											<c:if test="${'47' eq model.identityType}">selected</c:if>>港澳居民来往内地通行证（香港）
									</option>
									<option value="48"
											<c:if test="${'48' eq model.identityType}">selected</c:if>>港澳居民来往内地通行证（澳门）
									</option>
									<option value="49"
											<c:if test="${'49' eq model.identityType}">selected</c:if>>台湾居民往来大陆通行证
									</option>
									<option value="08"
											<c:if test="${'08' eq model.identityType}">selected</c:if>>外交人员身份证
									</option>
									<option value="09"
											<c:if test="${'09' eq model.identityType}">selected</c:if>>外国人居留许可证
									</option>
									<option value="10"
											<c:if test="${'10' eq model.identityType}">selected</c:if>>边民出入境通行证
									</option>
									<option value="11"
											<c:if test="${'11' eq model.identityType}">selected</c:if>>其他
									</option>
								</select>
							</div>
						</td>
					</tr>
					<tr >
						<td class="td-left td-left-top">证件号码</td>
						<td class="td-center td-right-top" colspan="2">
						<input class="form-control"  placeholder="请输入证件号码" type="text" 
						    name="username" id="username"
							maxlength="18" onchange="changeWarning();"
							value="${username}" /></td>
					</tr>
					<tr>
						<td class="td-left">查询密码</td>
						<td class="td-center td-right-border" colspan="2">
						<input class="form-control" placeholder="请输入查询密码" type="password" 
						name="password" id="password"
						 maxlength="6" onchange="changeWarning();" /></td>
					</tr>
					<tr>
						<td class="td-left td-bottom-left">附加验证码</td>
						<td class="td-center td-bottom">
							<input class="form-control" type="text" placeholder="请输入附加验证码" maxlength="4"
										name="verification" id="verificationCode"
										onchange="changeWarning();" /></td>
						</td>
						<td class="td-right td-bottom-right"><img class="tab-img" src="../showradomcode.jsp" /></td>
					</tr>
				</table>
			</div>
			<div style="min-height:20px;padding-top: 2px">
				<c:if test="${not empty message}">
					<span id="bindWarning" class="help-block fontsize text-center fontred">${message}</span>
				</c:if>
				<span id="spanWarning" class="help-block fontsize text-center fontred"></span>
			</div>
			<div class="HandIn">
				<input class="btn btn-sm btn-default btn-block btn-shadow"
				       type="submit" value="登&nbsp;&nbsp;录" onclick="return validate_form();" />
				<input type="hidden" id="openId" name="openId" value="${openId}">
			</div>
		</form>
	</div>
	<div style="margin-top: 30px;"></div>
	<div id="sending"
		style="position: absolute; position: fixed; height: 100%; background: #fff; width: 100%; top: 0; left: 0; visibility: hidden; filter: alpha(opacity = 50); opacity: 0.50;">
		<img src="<c:url value="/images/wechatbank/5-121204193R5-50.gif"/>"
			style="position: absolute; top: 50%; left: 50%; margin-left: -16px; margin-top: -16px;" />
	</div>
	<script type="text/javascript">
		var isClicked = false;
		function validate_form() {
			if (isClicked == false) {
				var identityType = $("#identityType").val();
				var idNumber = $("#username").val();
				var passwordQuery = $("#password").val();
				var verificationCode = $("#verificationCode").val();
				var spanWarning = $("#spanWarning");
				if (identityType == null || identityType == "") {
					spanWarning.text("证件类型不能为空，请选择！");
					return false;
				}
				if (idNumber == null || idNumber == "") {
					spanWarning.text("证件号不能为空，请输入！");
					return false;
				}
				if (passwordQuery == null || passwordQuery == "") {
					spanWarning.text("查询密码不能为空，请输入！");
					return false;
				}
				if (verificationCode == null
						|| verificationCode == "") {
					spanWarning.text("验证码不能为空，请输入！");
					return false;
				}
				var pswReg = /(^\d{6}$)/;
				if (pswReg.test(passwordQuery()) == false) {
					spanWarning.text("查询密码格式不正确，请重新输入！");
					return false;
				}
				var sending = $("#sending");
				sending.css("visibility","visible");
				isClicked = true;
				return true;
			} else {
				return false;
			}

		}

		function changeWarning() {
			$("#spanWarning").text("");
			$("#bindWarning").css("display","none");
		}
	</script>
</body>
</html>
