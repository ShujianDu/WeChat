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
		var areaStr = "${areaStr}";
		var areaButton = $("#areaButton");
		if (areaStr != null && areaStr != "") {
			if (areaStr.length > 11) {
				areaButton.val(areaStr.substr(0,11)+ "...");
			} else {
				areaButton.val(areaStr);
			}
		}
	}
</script>
<%@include file="../../base_pages/wxReadyFunction.jsp"%>
</head>
<body>
	<header id="top">
		<span></span>
		<h2>信用卡预约申请</h2>
		<span></span>
	</header>
	<div style="margin-top: 10px;">
		<form action="listP.do" method="post" id="submitForm">
			<div class="topOneB mar-1 topOneBradius"
				onchange="changeWarning();">
				<table class="topTwo" style="margin-bottom: -10px;">
					<tr>
						<td class="td-le">*联系地址：</td>
						<td><input type="button" value="省/市/区" class="HandInHui"
							id="areaButton" onclick="choiceAddress();" /></td>
						<input type="hidden" id="provId" name="provId"
							value="${model.provId}" />
						<input type="hidden" id="cityId" name="cityId"
							value="${model.cityId}" />
						<input type="hidden" id="areaId" name="areaId"
							value="${model.areaId}" />
					</tr>
				</table>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="areaWarning"
					style="color: red; font-size: 12px;"></span>
			</div>
			<div class="topOneB mar-1">
				<table class="topTwo" style="margin-bottom: -10px;">
					<tr>
						<td class="td-le">*姓名：</td>
						<td><input type="text" name="clientName" id="clientName"
							maxlength="25" onchange="changeWarning();"
							value="${model.clientName}" /></td>
					</tr>
				</table>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="clientNameWarning"
					style="color: red; font-size: 12px;"></span>
			</div>
			<div class="topOneB mar-1">
				<table class="topTwo" style="margin-bottom: -10px;">
					<tr>
						<td class="td-le">联系电话：</td>
						<td style="width: 27%; padding-right: 0;"><input type="text"
							name="areaCode" id="areaCode" style="width: 100%;" maxlength="5"
							onchange="changeWarning();" value="${model.areaCode}" /></td>
						<td style="width: 5px; padding: 0;">-</td>
						<td style="padding-left: 0;"><input type="text"
							name="phoneNum" id="phoneNum" style="width: 100%;" maxlength="8"
							onchange="changeWarning();" value="${model.phoneNum}" /></td>
					</tr>
				</table>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="phoneWarning"
					style="color: red; font-size: 12px;"></span>
			</div>
			<div class="topOneB mar-1">
				<table class="topTwo" style="margin-bottom: -10px;">
					<tr>
						<td class="td-le">*手机号码：</td>
						<td><input type="text" name="mobilePhone" id="mobilePhone"
							maxlength="11" onchange="changeWarning();"
							value="${model.mobilePhone}" /></td>
					</tr>
				</table>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="mobilePhoneWarning"
					style="color: red; font-size: 12px;"></span>
			</div>
			<div class="topOneB mar-1 bottomOneBradius">
				<table class="topTwo" style="margin-bottom: -10px;">
					<tr>
						<td class="td-le">*详细地址：</td>
						<td><input type="text" name="serviceAddr" id="serviceAddr"
							maxlength="250" onchange="changeWarning();"
							value="${model.serviceAddr}" /></td>
					</tr>
				</table>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="serviceAddrWarning"
					style="color: red; font-size: 12px;"></span>
			</div>
			<div class="HandIn" style="margin-top: 10px; margin-bottom: 10px;">
				<input type="submit" value="提交" onclick="return validate_form();" />
				<input type="hidden" id="openId" name="openId" value="${openId}">
			</div>
		</form>
		<div class="topOneB mar-1 allOneBradius" style="margin-bottom: 10px">
			<table class="topTwo" style="margin-bottom: 10px;">
				<tr>
					<td>(*是必填项)<br />
						请就近填写您的联系地址，感谢您对中国银行的支持，我们会在2-5个工作日内联系您，请您保持电话畅通。
					</td>
				<tr />
			</table>
		</div>
	</div>
	<div id="sending"
		style="position: absolute; position: fixed; height: 100%; background: #fff; width: 100%; top: 0; left: 0; visibility: hidden; filter: alpha(opacity =   50); opacity: 0.50;">
		<img src="<c:url value="/images/wechatbank/5-121204193R5-50.gif"/>"
			style="position: absolute; top: 50%; left: 50%; margin-left: -16px; margin-top: -16px;" />
	</div>
	<div id="affirmdiv" 
		style="position: absolute; top: 50%; left: 50%; width: 280px; margin-left: -140px; margin-top: -16px; margin-bottom: 10px; display: none;">
		<div id="isHaveBookingDiv" style="display: none;margin-bottom: 10px;">
			<div class="topOneB mar-1 allOneBradius bcS">
				<table class="topTwo">
					<tr>
						<td colspan="2"><span id="isHaveBookingWarning" style="color: red;"></span></td>
					</tr>
					<tr>
						<td width="50%">
							<input type="button" value="确认" class="HandInHui" onclick="affirm();" />
						</td>
						<td width="50%">
							<input type="button" value="取消" class="HandInHui" onclick="cancel();" />
						</td>
					</tr>
				</table>
			</div>
		</div>		
	</div>
	<script type="text/javascript">
		var isClicked = false;
		var can = false;
		var bookingWarning = $("#isHaveBookingWarning");
		var clientNameWarning = $("#clientNameWarning");
		var phoneWarning = $("#phoneWarning");
		var mobilePhoneWarning = $("#mobilePhoneWarning");
		var serviceAddrWarning = $("#serviceAddrWarning");
		var areaWarning = $("#areaWarning");
		function validate_form() {
			if (isClicked == false) {
				var areaButton = $("#areaButton");
				var clientName = $("#clientName").val();
				var areaCode = $("#areaCode").val();
				var phoneNum = $("#phoneNum").val();
				var mobilePhone = $("#mobilePhone").val();
				var serviceAddr = $("#serviceAddr").val();
				if (areaButton.value == "省/市/区") {
					$("#areaWarning").text("请选择省/市/区");
					return false;
				}
				//不能输入“|”正则表达式
				var allReg=/(\|)/;
				if (clientName == null || clientName == "") {
					clientNameWarning.text("请填写姓名！");
					return false;
				}
				if(allReg.test(clientName) == true){
					clientNameWarning.text("姓名不能输入“|”符号！");
					return false;
				}
				var areaCodeReg = /(^\d{3,5}$)/;
				if (areaCode != null && areaCode != "" && areaCodeReg.test(areaCode) == false) {
					phoneWarning.text("区号格式不正确，请重新输入！");
					return false;
				}
				if (phoneNum != null && phoneNum != "") {
					var phoneNumReg = /(^\d{7,8}$)/;
					if (phoneNumReg.test(phoneNum) == false) {
						phoneWarning.text("号码格式不正确，请重新输入！");
						return false;
					}
					if (areaCode == null || areaCode == "") {
						phoneWarning.text("请填写区号！");
						return false;
					}
				}
				if (mobilePhone == null || mobilePhone == "") {
					mobilePhoneWarning.text("请填写手机号码！");
					return false;
				}
				var mobilePhoneReg = /(^\d{11}$)/;
				if (mobilePhoneReg.test(mobilePhone) == false) {
					mobilePhoneWarning.text("手机号码格式不正确，请重新输入！");
					return false;
				}
				if (serviceAddr == null || serviceAddr == "") {
					serviceAddrWarning.text("请填写详细地址！");
					return false;
				}
				if(allReg.test(serviceAddr) == true){
					serviceAddrWarning.text("详细地址不能输入“|”符号！");
					return false;
				}
				var sending = $("#sending");
				sending.css("visibility", "visible");
				isClicked = true;
				if (bookingWarning.text() == null|| bookingWarning.text() == "") {
					//验证是否提交过预约信息
					isBooking();
					if (can) {
						bookingWarning.text("您已经进行过预约，是否需要再次预约？");
						$("#affirmdiv").css("display","block");
						$("#isHaveBookingDiv").css("display","block");
						closeform("disabled");
						sending.css("visibility", "hidden");
						isClicked = false;
						return false;
					}
				}
				return true;
			} else {
				return false;
			}
		}
		
		function closeform(closeoropen){
			var form = $("#submitForm");
			for(var i=0;i<form.elements.length;i++){
				form.elements[i].attr("disabled",closeoropen);
			}
		}
		
		function affirm(){
			var sending = $("#sending");
			sending.css("visibility", "visible");
			closeform("");
			var form = $("#submitForm");
			form.attr("action","listP.do");
			form.submit();
		}
		
		function cancel(){
			bookingWarning.text("");
			$("#isHaveBookingDiv").css("display","none");
			$("#affirmdiv").css("display","none");
			closeform("");
		}

		function choiceAddress() {
			var sending = $("#sending");
			sending.css("visibility", "visible");
			var form = $("#submitForm");
			form.attr("action","address.do");
			form.submit();
		}

		function isBooking() {
			var clientName = $("#clientName").val();
			var mobilePhone = $("#mobilePhone").val();

			$.ajax({
				url: "isBooking_ajax.do",
				data: {
					clientName: clientName,
					mobilePhone: mobilePhone,
					timestamp: new Date().getTime()
				},
				type: "post",
				dataType: "text",
				async: false,
				success: function (result) {
					if (result != null && result != "") {
						if (result == "exception") {
							window.location.href="../error.html";
							can = false;
						} else if (result == "true") {
							can = true;
						} else {
							can = false;
						}
					}
				}
			});
		}

		function changeWarning() {
			areaWarning.text("");
			clientNameWarning.text("");
			phoneWarning.text("");
			mobilePhoneWarning.text("");
			serviceAddrWarning.text("");
		}
	</script>
</body>
</html>

