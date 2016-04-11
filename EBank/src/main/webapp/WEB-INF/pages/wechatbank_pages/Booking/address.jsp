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
	href="<c:url value="/css/wechatbank/index.css"/>" />
<script type="text/javascript">
	window.onload = function() {
		var provId = "${model.provId}";
		var cityId = "${model.cityId}";
		var areaId = "${model.areaId}";
		if (provId != null && provId != "") {
			var provSelect = document.getElementById("provSelect");
			select(provSelect, provId);
		}
		if (cityId != null && cityId != "") {
			var citySelect = document.getElementById("citySelect");
			document.getElementById("citySelect").disabled = "";
			select(citySelect, cityId);
		}
		if (areaId != null && areaId != "") {
			var areaSelect = document.getElementById("areaSelect");
			document.getElementById("areaSelect").disabled = "";
			select(areaSelect, areaId);
		}
	}
	function select(objSelect, id) {
		for ( var i = 0; i < objSelect.length; i++) {
			if (id == objSelect[i].value) {
				objSelect[i].selected = "selected";
			}
		}
	}
</script>
<%@include file="../../base_pages/wxReadyFunction.jsp"%>
</head>

<body>
<header>
    <span></span>
    <h2>信用卡预约申请</h2>
    <span></span>
</header>
	<div class="mg-tb-30">
		<form action="addressP.do" method="post" id="submitForm">
		<input type="hidden" name="clientName" value="${model.clientName}">
		<input type="hidden" name="areaCode" value="${model.areaCode}">
		<input type="hidden" name="phoneNum" value="${model.phoneNum}">
		<input type="hidden" name="mobilePhone" value="${model.mobilePhone}">
		<input type="hidden" name="serviceAddr" value="${model.serviceAddr}">
			<div class="topOneB mar-1 topOneBradius">
				<table class="topTwo">
					<tr>
						<td width="70" style="text-align:right;padding-right:0;padding-left:0;"><span>省市：</span></td>
						<td><select name="provId" id="provSelect"
							onchange="changeProv();">
								<option value="">--请选择省市--</option>
								<c:forEach items="${provinceList}" var="item">
									<option value="${item.orgId}^${item.orgName}">
										<c:out value="${item.orgName}" />
									</option>
								</c:forEach>
						</select></td>
					</tr>
				</table>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="provWarning"
					style="color: red; font-size: 12px;"></span>
			</div>

			<div class="topOneB  mar-1">
				<table class="topTwo">
					<tr>
						<td width="70" style="text-align:right;padding-right:0;padding-left:0;"><span>城市：</span></td>
						<td><select name="cityId" id="citySelect"
							onchange="changeCity();" disabled="disabled">
								<option value="">--请选择市区--</option>
								<c:if test="${fn:length(cityList) gt 0}">
									<c:forEach items="${cityList}" var="city">
										<option value="${city.orgId}^${city.orgName}">
											<c:out value="${city.orgName}" />
										</option>
									</c:forEach>
								</c:if>
						</select></td>
					</tr>
				</table>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="cityWarning"
					style="color: red; font-size: 12px;"></span>
			</div>

			<div class="topOneB mar-1 bottomOneBradius">
				<table class="topTwo">
					<tr>
						<td width="70" style="text-align:right;padding-right:0;padding-left:0;"><span>区县：</span></td>
						<td><select name="areaId" id="areaSelect"
							onchange="changeArea();" disabled="disabled">
								<option value="">--请选择区县--</option>
								<c:if test="${fn:length(areaList) gt 0}">
									<c:forEach items="${areaList}" var="area">
										<option value="${area.orgId}^${area.orgName}">
											<c:out value="${area.orgName}" />
										</option>
									</c:forEach>
								</c:if>
						</select></td>
					</tr>
				</table>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="areaWarning"
					style="color: red; font-size: 12px;"></span>
			</div>

			<div class="HandIn" style="margin-top: 10px;">
				<input type="submit" value="确定" onclick="return validate_form();" />
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
		var provSelect = document.getElementById("provSelect");
		var citySelect = document.getElementById("citySelect");
		var areaSelect = document.getElementById("areaSelect");
		function validate_form() {
			if (isClicked == false) {
				if (provSelect.value == null || provSelect.value == "") {
					document.getElementById("provWarning").innerHTML = "请选择省市！";
					return false;
				}
				if (citySelect.value == null || citySelect.value == "") {
					document.getElementById("cityWarning").innerHTML = "请选择市区！";
					return false;
				}
				if (areaSelect.value == null || areaSelect.value == "") {
					document.getElementById("areaWarning").innerHTML = "请选择区县！";
					return false;
				}
				citySelect.disabled = "";
				areaSelect.disabled = "";
				var sending = document.getElementById("sending");
				sending.style.visibility = "visible";
				isClicked = true;
				return true;
			} else {
				return false;
			}
		}

		function changeProv() {
			removeSelectedItem(citySelect);
			if (provSelect.value != null && provSelect.value != "") {
				getOrgList(provSelect,citySelect);
				citySelect.disabled = "";
			} else {
				removeSelectedItem(citySelect);
				removeSelectedItem(areaSelect);
				citySelect.disabled = "disabled";
				areaSelect.disabled = "disabled";
			}
			document.getElementById("provWarning").innerHTML = "";
		}

		function changeCity() {
			removeSelectedItem(areaSelect);
			if (citySelect.value != null && citySelect.value != "") {
				getOrgList(citySelect,areaSelect);
				document.getElementById("areaSelect").disabled = "";
			} else {
				removeSelectedItem(areaSelect);
				areaSelect.disabled = "disabled";
			}
			document.getElementById("cityWarning").innerHTML = "";
		}

		function changeArea() {
			document.getElementById("areaWarning").innerHTML = "";
		}

		function removeSelectedItem(objSelect) {
			for ( var i = objSelect.options.length - 1; i > 0; i--) {
				objSelect.options.remove(i);
			}
		}

		function getOrgList(pOrgSelect,orgSelect) {
			var pOrgId = pOrgSelect.value;
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
					if (result != null&&result!="") {
						if(result=="exception"){
							window.location.href="../error.html"; 
						}else{
							var list = eval("("+result+")");
							for ( var i = 0; i < list.length; i++) {
								orgSelect.add(new Option(list[i].orgName,
										list[i].orgId + "^" + list[i].orgName));
							}
						}
					}
				}
			}
			xmlhttp.open("POST", "getOrg_ajax.do?pOrgId=" + pOrgId + "&date="
					+ new Date().getTime(), true);
			xmlhttp.send();
		}

	</script>
</body>
</html>