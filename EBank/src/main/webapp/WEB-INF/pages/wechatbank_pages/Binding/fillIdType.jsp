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
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/bootstrap.min.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/wechatbank/index.css"/>" />
<%@include file="../../base_pages/wxHideOptionMenu.jsp"%>
</head>

<body>
		<header>
		<span></span>
		<h2>补充证件类型</h2>
		<span></span>
	</header>
	<div class="container">
		<form action="fillIdentityType.do" method="post" id="submitForm">
			<div class="table-shadow">
				<table class="table table-none">
					<tr>
						<td class="td-left td-left-top" >证件类型</td>
						<td class="td-center td-right-top" colspan="2">
						<select class="select" id="idType" name="idType">
							<option value="">请选择证件类型</option>
							<option value="01">居民身份证</option>
							<option value="03">护照</option>
							<option value="05">军人身份证</option>
							<option value="06">武装警察身份证</option>
							<option value="04">户口簿</option>
							<option value="47">港澳居民来往内地通行证（香港）</option>
							<option value="48">港澳居民来往内地通行证（澳门）</option>
							<option value="49">台湾居民往来大陆通行证</option>
							<option value="08">外交人员身份证</option>
							<option value="09">外国人居留许可证</option>
							<option value="10">边民出入境通行证</option>
							<option value="11">其他</option>
						</select> 
						</td>
					</tr>
					<tr>
						<td class="td-left td-bottom-left">证件号码</td>
						<td class="td-center td-bottom-right" colspan="2"><input readonly="readonly" class="form-control" type="text" placeholder="请输入证件号码" value="${idNum}" name="idNumber" id="idNumber"/></td>
					</tr>
				</table>
			</div>
				<div style="margin-top:10px;">
			 <c:if test="${msg eq 1}">
					<span class="help-block fontsize text-center fontred">未找到有效的信用卡，请确认类型</span>
			</c:if>
					<span id="idTypeWarning" class="help-block fontsize text-center fontred"  style="margin-top:2px;"></span>
				</div>
			
			<div class="HandIn" style="margin-top:10px">
				<input type="submit" class="btn btn-sm btn-default btn-block btn-shadow" onclick="return validate_form();" value="提&nbsp;&nbsp;交">
					<input type="hidden" id="openId" name="openId" value="${model.openId}"> <input
					type="hidden" id="toUser" name="toUser" value="${model.toUser}">
			</div>
		</form>
	</div>
	<div style="margin-top: 30px;"></div>
	<div id="sending"
		style="position: absolute; position: fixed; height: 100%; background: #fff; width: 100%; top: 0; left: 0; visibility: hidden; filter: alpha(opacity =           50); opacity: 0.50;">
		<img src=""
			style="position: absolute; top: 50%; left: 50%; margin-left: -16px; margin-top: -16px;" />
	</div>
	<script type="text/javascript">
	function validate_form() {
		var isClicked = false;
		if (isClicked == false) {
			var idType = document.getElementById("idType");
			var idTypeWarning = document.getElementById("idTypeWarning");
			if (idType.value == null || idType.value == "") {
				idTypeWarning.innerHTML = "请您选择证件类型后再继续操作";
				return false;
			}
			isClicked = true;
			return true;
		} else {
			return false;
		}

	}

	</script>
</body>
</html>
