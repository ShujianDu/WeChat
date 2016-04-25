<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>中国银行信用卡</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/wechatbank/index.css"/>" />
</head>
<body onload="init();">
<header>
    <span></span>
    <h2>账单寄送方式</h2>
    <span></span>
</header>
<div style="margin-top: 10px;">
<form action="" method="get" id="submitForm">
	<input type="hidden"  id="cardNo" value="${fn:substringAfter(bsw.cardNo, ',')}" name="cardNo"/>
	<input type="hidden"  id="billSendType" value="${bsw.billSendType}" name="billSendType"/>

	<div style="margin-top: 20px; margin-bottom: 10px;">
		<div class="topOneB mar-1 allOneBradius bcS">
			<table class="topTwo">
				<tr>
					<td><span class="">卡号:<c:out value="${fn:substring(bsw.cardNo,0,16)}" />
					</tr>
			</table>
		</div>
	</div>

	<div class="allOneB topOneB mar-1 allOneBradius">
	<table class="topTwo">
		
		<tr>
		<td><c:out value="账单寄送方式:" />&nbsp;<span style="font-size:15px;"><font color="#FF0000" id="alertmsg"></font></span></td>
		</tr>
		
		<tr style="display: display" id="paper">
			<td><input type="checkbox" name="items" value="1"/></td>
			<td><c:out value="纸质对账单" />&nbsp;</td>
		</tr>
		<tr>
		<td><input type="checkbox" name="items" value="2"/></td>
		<td><c:out value="手机对账单" />&nbsp;</td>
		</tr>
		<tr>
		<td><input type="checkbox" name="items" value="3"/></td>
		<td><c:out value="电子对账单" />&nbsp;</td>
		</tr>
	</table>
	</div>
	<div class="HandIn" style="margin-top: 10px;margin-bottom: 10px;">
		<input type="button" value="提交" onclick="update();" />
	</div>
</form>
</div>
</body>
</html>
<script>
var can=false;
var arr1='1';//纸质对账单
var arr2='13';//纸质对账单、电子对账单
var arr3='3';//电子对账单
var arr4='4';//不发送对账单
var arr5='2';//短信对账单
var arr6='23';//短信对账单、电子对账单
var arr7='12';//纸质对账单、短信对账单
var arr8='123';//纸质对账单、短信对账单、电子对账单

var arr9='1235';//纸质、email和短信、推入式
var arr10='35';//email和推入式
var arr11='25';//短信、推入式
var arr12='235';//email和短信、推入式
var arr13='15';//纸质、推入式
var arr14='135';//纸质、email和推入式
var arr15='125';//纸质、短信、推入式


//新添加
var arr17='1256';//	纸质、短信、推入式、彩信                      9
var arr18='6';//彩信														A
var arr16='5';//推入式													C
var arr19='2356';//短信、EMAIL、彩信、推入式			D
var arr20='36';//email和彩信										E
var arr21='26';//彩信、短信											H
var arr22='236';//email和彩信、短信 							K
var arr23='356';//email和彩信、推入式							L
var arr24='256';//彩信、短信、推入式							N
var arr25='12356';//纸质和email、短信、彩信、推入式	R
var arr26='136';//纸质、email和彩信							S
var arr27='126';//纸质、彩信、短信								V
var arr28='156';//纸质、彩信、推入式							W
var arr29='1236';//纸质、EMAIL、短信、彩信				Y
var arr30='1356';//纸质、EMAIL、彩信、推入式			Z
var arr31='16';//纸质、彩信											O
var arr32='56';//彩信、推入式										I


var arr=['1','13','3','4','4','3','1','13','123','23','2','23','12','123','12',      '12','4', '2','23','3','4','3','23','2','123','13','1','12','13','123','1','2'];
var res=['0','1', '2','3','B','F','P','T',  '8', 'G', 'J','M', 'Q', 'U',  'X',       '9', 'A', 'C','D', 'E','H','K','L', 'N','R',  'S', 'V','W', 'Y', 'Z',  'O','I'];
var begin="";
var result="";
var value="";

//提交账单寄送方式修改
function update(){
	confirm();
	if(can){
	var billSendType=result;
	var cardNo=$("#cardNo").val();

		$.ajax({
			url: "update.do",
			data: {
				cardNo: cardNo,
				billSendType: billSendType,
			},
			type: "post",
			dataType: "text",
			async: false,
			success: function (result) {
				if (result == "修改成功") {
					window.location.href = 'success.do';
				} else {
					$("#alertmsg").text (result);
				}
			}
			});
	}
}

//展示账单寄送方式
function init(){
	var items = $("input[name=items]");
	var bst=$("#billSendType").val();
	from(bst);
	for(var i=0;i<begin.length;i++){
	var n=begin.charAt(i);
	if(begin.indexOf("1") < 0){//如果不包含1，则代表无纸质账单，则不显示纸质账单行，20150319日肖宇要求修改
		$("#paper").css("display","none");
	}
	for(var j=0;j<items.length;j++){
		if(n==items[j].value){
			items[j].checked=true;
			}
		}
	}
}
function to(value){
	if(value==""){
		result="3";
	}
	for(var i=0;i<arr.length;i++){
		if(value==arr[i]){
			result=res[i];
			break;
		}
	}
}
function from(value){
	for(var i=0;i<res.length;i++){
		if(value==res[i]){
			begin=arr[i];
			break;
		}
	}
	if(begin==""){
		begin="4"
	}
}

//验证账单寄送方式修改
function confirm(){
	value="";
	result="";
	var items = $("input[name=items]");
	if(items.length!=0){
	for(var i=0;i<items.length;i++){
	if(items[i].checked){ //判断复选框是否选中    
		value=value+items[i].value; //值的拼凑 .. 具体处理看你的需要,
		}  
	  }
	}
	to(value);
	if(result!=""){
		$("#alertmsg").text("");
	can=true;
	return true;
	}else{
		$("#alertmsg").text("不支持该种寄送方式");
	can=false;
	return false;
	}
}

//去除提示信息
function changeWarning() {
	$("#codeWarning").text("");
}
</script>

