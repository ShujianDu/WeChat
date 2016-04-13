<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>外币兑换计算器</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/wechatbank/index.css"/>" />
<script src="<c:url value="/js/jquery-1.4.1.js"/>" type="text/javascript"></script>

</head>

<body>
<header>
    <h2>外币兑换计算器</h2>
</header>

</div>
    <div class="topOneB mar-1 topOneBradius">
        <table class="topTwo" >
            <tr>
                <td>现持有货币种类:</td>
            </tr>
            <tr>
                <td>
				<select id="fromcurrency">
                <option value ="">人民币</option>
                <option value ="">美元</option>
                <option value ="">港元</option>
                <option value ="">加拿大元</option>
                <option value ="">欧元</option>
                <option value ="">日元</option>
                <option value ="">瑞士法郎</option>
                <option value ="">英镑</option>
                <option value ="">新加坡元</option>
                </select>
				</td>
            </tr>
        </table>
    </div>
    <div class="topOneB  mar-1">
        <table class="topTwo">
            <tr>
                <td>现持有货币数量(元):</td>
            </tr>
            <tr>
                <td><input  id="amount"  maxlength="9"/></td>
                <br><span  style="color: red;font-size:15px;" id="alteramount"></span></br>
            </tr>
        </table>
    </div>
    <div class="topOneB  mar-1">
        <table class="topTwo">
            <tr>
                <td>欲兑换货币种类:</td>
            </tr>
            <tr>
                <td>
				<select id="tocurrency">
                <option value ="">人民币</option>
                <option value ="">美元</option>
                <option value ="">港元</option>
                <option value ="">加拿大元</option>
                <option value ="">欧元</option>
                <option value ="">日元</option>
                <option value ="">瑞士法郎</option>
                <option value ="">英镑</option>
                <option value ="">新加坡元</option>
                </select>
				</td>
            </tr>
        </table>
    </div>
    <div class="topOneB">
        <table class="topTwo">
            <tr>
                <td>汇率:</td>
            </tr>
            <tr>
                <td><input type="text"  id="rate" maxlength="7"/></td>
                 <br/><span  style="color: red;font-size:15px;" id="alterrate" ></span>
            </tr>
        </table>
    </div>
   

<div class="HandIn">
    <input type="button" value="计算"  onclick="getResult();" />
</div>
<div class="topOneB mar-1 allOneBradius">
        <table class="topTwo " >
            <tr>
                <td>可兑换的货币数量为(元):</td>
            </tr>
            <tr>
                <td><input type="text" id="result"/></td>
            </tr>
        </table>
    </div>
    
</body>
</html>

<script language="javascript">

function getResult(){
	var reg=/^[0-9]{1,9}$/;
	var reg2=/^[0-9]{1,4}.[0-9]{1,3}$/;
	var rate=document.getElementById("rate");
	var amount=document.getElementById("amount");
	if(amount.value==""){
		document.getElementById("alteramount").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;请填写转换货币数量";
		return;
	}else if(!reg.test(amount.value)){
		document.getElementById("alteramount").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;请输入正整数";
		return;
	}else{
		document.getElementById("alteramount").innerHTML="";
	}
	
	if(rate.value==""){
		document.getElementById("alterrate").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;请输入汇率";
		return;
	}else if(!reg2.test(rate.value)){
		document.getElementById("alterrate").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;请输入1-3位小数";
		return;
	}else{
		document.getElementById("alterrate").innerHTML="";
	}
	document.getElementById("result").value=Math.round(new Number(amount.value)*parseFloat(rate.value)*100)/100;
}
</script>
