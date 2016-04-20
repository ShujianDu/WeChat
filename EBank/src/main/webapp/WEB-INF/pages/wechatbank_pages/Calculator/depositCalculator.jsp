<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>存款计算器</title>
<%@include file="../../base_pages/base.jsp"%>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/wechatbank/index.css"/>" />
<script src="../js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="../js/calculator/CalcDeposit.js" type="text/javascript"></script>
<script src="../js/calculator/CheckDataFunction.js" type="text/javascript"></script>
<script src="../js/calculator/Components.js" type="text/javascript"></script>
<script type="text/javascript">
	var data = {
		"item_0" : [ {
			"id" : "1",
			"name" : "三个月",
			"number" : 0.25,
			"rate" : 2.85
		}, {
			"id" : "2",
			"name" : "半年",
			"number" : 0.5,
			"rate" : 3.05
		}, {
			"id" : "3",
			"name" : "一年",
			"number" : 1,
			"rate" : 3.25
		}, {
			"id" : "4",
			"name" : "二年",
			"number" : 2,
			"rate" : 3.75
		}, {
			"id" : "5",
			"name" : "三年",
			"number" : 3,
			"rate" : 4.25
		}, {
			"id" : "6",
			"name" : "五年",
			"number" : 5,
			"rate" : 4.75
		} ],
		"item_1" : [ {
			"id" : "1",
			"name" : "一年",
			"number" : 1,
			"rate" : 2.85
		}, {
			"id" : "2",
			"name" : "三年",
			"number" : 3,
			"rate" : 2.90
		}, {
			"id" : "3",
			"name" : "五年",
			"number" : 5,
			"rate" : 3.00
		} ]
	};

	window.onload = function() {
		var saveTime = document.getElementById("tbSaveTime");
		for ( var i in data.item_0) {
			saveTime.options.add(new Option(data.item_0[i].name,
					data.item_0[i].id));
		}
		document.getElementById("tbYearRate").value = data.item_0[0].rate;
	}

	function changeall() {
		Cleartxt();

		var saveTime = document.getElementById("tbSaveTime");
		saveTime.options.length = 0;

		var rbDepositWay_0 = document.getElementById("rbDepositWay_0");
		if (rbDepositWay_0.checked == true) {
			for ( var i in data.item_0) {
				saveTime.options.add(new Option(data.item_0[i].name,
						data.item_0[i].id));
			}

			if (document.getElementById("rbCalcOption_0").checked) {
				document.getElementById("Layer1").innerText = "初期存入金额";
				document.getElementById("layerresult").innerText = "到期本息总额";
			} else {
				document.getElementById("Layer1").innerText = "到期本息总额";
				document.getElementById("layerresult").innerText = "初期存入金额";
			}
		}

		var rbDepositWay_1 = document.getElementById("rbDepositWay_1");
		if (rbDepositWay_1.checked == true) {
			for ( var i in data.item_1) {
				saveTime.options.add(new Option(data.item_1[i].name,
						data.item_1[i].id));
			}

			if (document.getElementById("rbCalcOption_0").checked) {
				document.getElementById("Layer1").innerText = "每期存入金额";
				document.getElementById("layerresult").innerText = "到期本息总额";
			} else {
				document.getElementById("Layer1").innerText = "到期本息总额";
				document.getElementById("layerresult").innerText = "每期存入金额";
			}
		}
		tbSaveTime.selectedIndex = 0;
		getrate(0);
	}

	function changeall2() {
		Cleartxt();
		var saveTime = document.getElementById("tbSaveTime");
		var rbCalcOption_0 = document.getElementById("rbCalcOption_0");
		var rbDepositWay_0 = document.getElementById("rbDepositWay_0");
		if (rbCalcOption_0.checked) {
				document.getElementById("layerresult").innerText = "到期本息总额";
			if (rbDepositWay_0.checked == true)
				document.getElementById("Layer1").innerText = "初期存入金额";
			else
				document.getElementById("Layer1").innerText = "每期存入金额";
		} else {
				document.getElementById("Layer1").innerText = "到期本息总额";
			if (rbDepositWay_0.checked == true)
				document.getElementById("layerresult").innerText = "初期存入金额";
			else
				document.getElementById("layerresult").innerText = "每期存入金额";
		}
	}

	/**动态改变年利率*/
	function getrate(num) {
		var tbSaveTime = document.getElementById("tbSaveTime");
		if (num == -1) {
			num = tbSaveTime.value - 1;
		}

		var tbYearRate = document.getElementById("tbYearRate");
		var rbDepositWay_0 = document.getElementById("rbDepositWay_0");
		if (rbDepositWay_0.checked == true) {
			document.getElementById("tbYearRate").value = data.item_0[num].rate;
		}

		var rbDepositWay_1 = document.getElementById("rbDepositWay_1");
		if (rbDepositWay_1.checked == true) {
			document.getElementById("tbYearRate").value = data.item_1[num].rate;
		}
	}

	function Cleartxt() {
		document.getElementById("tbInitSaveSum").value = "";
		document.getElementById("tbTermEndSum").value = "计算得出";
		document.getElementById("tbInterestTaxSum").value = "计算得出";
	}

	function calcu() {
		var beginDate = document.getElementById("beginDateID").value;
		if (beginDate == null || beginDate == '') {
			document.getElementById("alertbeginDate").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;请选择初始存入日期！";
			//alert("请选择初始存入日期！");
			return;
		}else{
			document.getElementById("alertbeginDate").innerHTML="";
		}

		var tbInitSaveSum = document.getElementById("tbInitSaveSum");
		if (tbInitSaveSum.value == null || tbInitSaveSum.value == '') {
			//alert("请输入" + document.getElementById("Layer1").innerText + "！");
			document.getElementById("alerttbInitSaveSum").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;请输入"+ document.getElementById("Layer1").innerText + "！";
			return;
		}else{
			document.getElementById("alerttbInitSaveSum").innerHTML="";
		}
		if (!IsNum(tbInitSaveSum)) {
			//alert("请在" + document.getElementById("Layer1").innerText
			//		+ "处输入正确的金额！");
			document.getElementById("alerttbInitSaveSum").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;请在" + document.getElementById("Layer1").innerText + "处输入正确的金额！";
			return;
		}else{
			document.getElementById("alerttbInitSaveSum").innerHTML="";
		}
		CalcDeposit();
	}
</script>
</head>

<body>
	<header>
	<h2>存款计算器</h2>
	</header>
	<div class="topOneB mar-1 topOneBradius">
		<table class="topTwo">
			<tr>
				<td><span class="">存款方式：</span></td>

			</tr>

		</table>
	</div>
	<div class="topOneB mar-1">
		<table class="topTwo">
			<tr>
				<td><input id="rbDepositWay_0" tabindex="1" type="radio"
					checked="checked" onclick="changeall()" name="rbDepositWay"
					value="1"></td>
				<td>整存整取</td>
			</tr>

		</table>
	</div>
	<div class="topOneB mar-1">
		<table class="topTwo">
			<tr>
				<td><input id="rbDepositWay_1" type="radio"
					onclick="changeall()" name="rbDepositWay" value="2"></td>
				<td>零存整取</td>
			</tr>

		</table>
	</div>
	<div class="topOneB mar-1">
		<table class="topTwo">
			<tr>
				<td><span class="">计算选项：</span></td>

			</tr>

		</table>
	</div>
	<div class="topOneB mar-1">
		<table class="topTwo">
			<tr>
				<td><input id="rbCalcOption_0" tabindex="2" type="radio"
					onclick="changeall2()" name="rbCalcOption" value="1"
					checked="checked"></td>
				<td>计算到期本息总额</td>
			</tr>

		</table>
	</div>
	<div class="topOneB mar-1">
		<table class="topTwo">
			<tr>
				<td><input id="rbCalcOption_1" type="radio"
					onclick="changeall2()" name="rbCalcOption" value="2"></td>
				<td>计算初期存入金额</td>
			</tr>

		</table>
	</div>
	<div class="topOneB mar-1">
		<table class="topTwo">
			<tr>
				<td>初始存入日期:</td>
			</tr>
			<tr>
				<td><input type="text" id="beginDateID" name="beginDate"
					value="" onFocus="WdatePicker({dateFmt:'yyyy-M-d'})">
				</td>
				<span style="font-size:15px;"><font color="#FF0000" id="alertbeginDate"></font></span>
			</tr>
		</table>
	</div>
	<div class="topOneB  mar-1">
		<table class="topTwo">
			<tr>
				<td>储蓄存期:</td>
			</tr>
			<tr>
				<td><select id="tbSaveTime" name="tbSaveTime" onchange="getrate(-1)"></select></td>
			</tr>
		</table>
	</div>
	<div class="topOneB  mar-1">
		<table class="topTwo">
			<tr>
				<td>年利率(%):</td>
			</tr>
			<tr>
				<td><input id="tbYearRate" readonly="readonly"
					name="tbYearRate" type="text"></td>
			</tr>
		</table>
	</div>
	<div class="topOneB">
		<table class="topTwo">
			<tr>
				<td><span id="Layer1">初期存入金额</span>(元):</td>
			</tr>
			<tr>
				<td><input id="tbInitSaveSum" name="tbInitSaveSum" type="text" maxlength="8"></td>
				<span style="font-size:15px;"><font color="#FF0000" id="alerttbInitSaveSum"></font></span>
			</tr>
		</table>
	</div>


	<div class="HandIn">
		<input type="button" onclick="calcu()" value="计算">
	</div>
	<div class="topOneB mar-1 topOneBradius" style="margin-top: 10px">
		<table class="topTwo ">
			<tr>
				<td><span id="layerresult">到期本息总额</span>(元):</td>
			</tr>
			<tr>
				<td><input id="tbTermEndSum" readonly="readonly"
					name="tbTermEndSum" value="计算得出" type="text"></td>
			</tr>
		</table>
	</div>
	<div class="topOneB bottomOneBradius">
		<table class="topTwo">
			<tr>
				<td>扣除利息税金额(元):</td>
			</tr>
			<tr>
				<td><input id="tbInterestTaxSum" readonly="readonly"
						name="tbInterestTaxSum" value="计算得出" type="text"></td>
			</tr>
		</table>
	</div>

</body>
</html>
