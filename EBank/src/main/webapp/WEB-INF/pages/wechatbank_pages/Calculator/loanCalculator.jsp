<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>贷款计算器</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/wechatbank/index.css"/>" />
<script src="<c:url value="/js/jquery-1.4.1.js"/>" type="text/javascript"></script>
</head>
<body>
<header>
    <h2>贷款计算器</h2>
</header>
</div>
    <div class="topOneB mar-1 topOneBradius">
        <table class="topTwo" >
            <tr>
                <td>贷款类型:</td>
            </tr>
            <tr>
                <td>
                <select id="edType">
                	<option value="楼宇按揭">楼宇按揭</option>
                	<option value="汽车消费贷款">汽车消费贷款</option>
                	<option value="个人消费贷款">个人消费贷款</option>
                </select>
                </td>
            </tr>
        </table>
    </div>
    <div class="topOneB  mar-1">
        <table class="topTwo">
            <tr>
                <td>贷款金额(元):</td>
            </tr>
            <tr>
                <td><input type="text" id="edLastSum"  name="edLastSum" maxlength="9"/></td>
                <span style="font-size:15px;"><font color="#FF0000" id="alertedLastSum"></font></span>
            </tr>
        </table>
    </div>
    <div class="topOneB  mar-1">
        <table class="topTwo">
            <tr>
                <td>贷款期限(月):</td>
            </tr>
            <tr>
                <td><input type="text"  id="edTimes" maxlength="3"/></td>
                <span style="font-size:15px;"><font color="#FF0000" id="alertedTimes"></font></span>
            </tr>
        </table>
    </div>
    <div class="topOneB  mar-1">
        <table class="topTwo">
            <tr>
                <td>还款方式:</td>
            </tr>
            <tr>
                <td><select id="ddlPayWay" name="ddlPayWay" onchange="waychg();">
                    <option value="1" selected="selected">等额本息还款法</option>
                    <option value="2">等额本金还款法</option>
                    <option value="3">一次性还本付息法</option>
                    </select>
               </td>
            </tr>
        </table>
    </div>
    
    <div class="topOneB  mar-1">
        <table class="topTwo">
            <tr>
                <td>年利率:</td>
            </tr>
            <tr>
                <td><input type="text" id="edRate"  name="edRate" maxlength="5"/></td>
                <span style="font-size:15px;"><font color="#FF0000" id="alertedRate"></font></span>
            </tr>
        </table>
    </div>
   <div class="topOneB  ">
        <table class="topTwo">
            <tr>
                <td>偿还频率:</td>
            </tr>
      <tr>
            
        <td><input type="radio" checked="checked"  id="rbPayFreq_0" name="rbPayFreq" value="1"/></td>
        <td>每月偿还</td>
    	</tr>
      <tr>
            <td><input type="radio"   id="rbPayFreq_1" name="rbPayFreq" value="2"/></td>
        <td>每季偿还</td>
        </tr>
      <tr>
            <td><input type="radio"   id="rbPayFreq_2" name="rbPayFreq" value="3" disabled="disabled"/></td>
        <td>一次性偿还</td>
   	 </tr>
        </table>
    </div>

<div class="HandIn">
    <input type="button" value="计算"  onclick="GetPlayList(document);"/>
</div>
<div id="result">
</div>
</body>
</html>
<script language="javascript">
function waychg()
{
	var value=document.getElementById("ddlPayWay").value;
	if(value=="1"||value=="2")	
	{	    
		document.getElementById("rbPayFreq_0").disabled = false;
		document.getElementById("rbPayFreq_1").disabled = false;
		document.getElementById("rbPayFreq_2").disabled = true;
		if(document.getElementById("rbPayFreq_2").checked)
			document.getElementById("rbPayFreq_0").checked = true;
	}
	if(value=="3")	
	{
		document.getElementById("rbPayFreq_0").disabled = true;
		document.getElementById("rbPayFreq_1").disabled = true;
		document.getElementById("rbPayFreq_2").disabled = false;
		document.getElementById("rbPayFreq_2").checked = true;
	}
}



</script>
<script>
//贷款计算器

/// 根据属性PayWay（还款方式）计算还款表
/// <param name="PlayList">还款表</param>
function GetPlayList(oDocument)
{
var LastSum=oDocument.all.edLastSum.value;			//贷款金额
var BeginYear=0;
var LoanTimes=oDocument.all.edTimes.value;//贷款期限       
var Rate=oDocument.all.edRate.value;//年利率    parseFloat()/100
var PayWay=oDocument.all.ddlPayWay.value;//还款方式
var reg=/^[0-9]{1,9}$/;
var reg2=/^[0-9]+(.[0-9]{1,3})$/;
if(LastSum==""){
	document.getElementById("alertedLastSum").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;请输入贷款金额";
	return;
} 
if(!reg.test(LastSum)){
	document.getElementById("alertedLastSum").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;请输入正整数";
	return;	
}else{
	document.getElementById("alertedLastSum").innerHTML="";
}
if(LoanTimes==""){
	document.getElementById("alertedTimes").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;请输入贷款期限";
	return;	
}
if(!reg.test(LoanTimes)){
	document.getElementById("alertedTimes").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;请输入正整数";
	return;	
}else{
	document.getElementById("alertedTimes").innerHTML="";
	LoanTimes=parseInt(LoanTimes);
}
if(Rate==""){
	document.getElementById("alertedRate").innerHTML="请输入贷款利率";
	return;
}
if(!reg2.test(Rate)){
	document.getElementById("alertedRate").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;请输入1-3位小数";
	return;	
}else{
	document.getElementById("alertedRate").innerHTML="";
	Rate=parseFloat(Rate)/100;
}

if (oDocument.all.rbPayFreq_0.checked)
	PayFreq=1;																				//偿还频率
else
{
	if (oDocument.all.rbPayFreq_1.checked)
		PayFreq=3;
	else
		PayFreq=1;
}

if(PayFreq==3){
	if(LoanTimes%3!=0){
		document.getElementById("alertedTimes").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;当按季度查询时,贷款月数为3的倍数";
		return;
	}else{
		document.getElementById("alertedTimes").innerHTML="";
	}
}


	var result=0;
	var LoanTimesTemp=0;
	LoanTimesTemp=LoanTimes/PayFreq;
	PlayList=new Array();
	var nowdate=new Date();
	var addnowdate=new Date();
	
	addnowdate.setMonth(addnowdate.getMonth()+LoanTimes)
	switch (PayWay)
	{
		case "1"://等额还款																//贷款金额//当前时间//产生的记录数//年利率//偿还频率
			result=CalcLoanPay(LastSum,nowdate,LoanTimesTemp,Rate,PayFreq);
			break;
		case "2"://等本还款															//贷款金额//当前时间//产生的记录数//年利率//偿还频率
			result=ECorpus(LastSum,nowdate,LoanTimesTemp,Rate,PayFreq);
			break;
		case "3"://一次性还本付息													//贷款金额//当前时间//偿还时间//年利率
			result=CalcPayOnce(LastSum,nowdate,addnowdate,Rate);
			break;
	}
	//return result;
}

//删除节点
function remove(){	
		var result = document.getElementById("result");	
		var children = result.childNodes;	
		for(var i = children.length-1; i >= 0; i--)
			{		
			result.removeChild(children[i]);	
			}
		}

//---------------------公用部分-----------------------------------------
/// 等额付款的现金支出（还款计划）数据生成  
/// <param name="document">还款表</param>
/// <param name="LastSum">债务本金（贷款总额）</param>
/// <param name="BeginYear">债务开始年份（贷款日期）</param>
/// <param name="times">债务的期限（贷款期限（月份数））</param>
/// <param name="Rate">债务的年利率</param>
/// <param name="Freq">债务的偿还频率</param>
function CalcLoanPay(LastSum,BeginYear,times,Rate,Freq)//贷款金额//当前时间//产生的记录数//年利率//偿还频率
{
var root = document.getElementById("result");
var elem;
var node;
var i, m;
var fTotalInterest = new Number(0), fTotalSum = new Number(0);
//清空xml中的数据
remove();
i=0;
var r,M,Inter,C,dtemp;
r=Rate*Freq/12;
dtemp=Math.pow((1+r),times);
if (dtemp!=1)
M=(LastSum*r*dtemp/(dtemp-1));
else
M=LastSum/times;

table=document.createElement("table");
//table.className="siteTable";
table.style.cssText="margin:0 20px; font-size:12px;border:1px solid #aaa;border-collapse:collapse;";

row=document.createElement("tr")
cell=document.createElement("td");
width = "100"
cell.innerHTML="期次";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";     
row.appendChild(cell);


cell=document.createElement("td");
width = "100"
cell.innerHTML="还款时间";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");
width = "100"
cell.innerHTML="偿还利息";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");
width = "100"
cell.innerHTML="偿还本金";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");
width = "100"
cell.innerHTML="偿还本息";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");
width = "100"
cell.innerHTML="剩余本金";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

table.appendChild(row);

for (i=0;i<times;i++) 
{
Inter=LastSum*r;
C=M-Inter;
mRecTimes=i+1;
mRecYear=new Date(BeginYear.getFullYear(), BeginYear.getMonth() + 1, BeginYear.getDate());
mRecYear.setMonth(mRecYear.getMonth()+(i*Freq));
LastSum=LastSum-C;
mRecCorpus=C;
mRecRateSum=Inter;
mRecLeavCorpus=LastSum;

//-----绑定数据到表格-----
// 还款期次Times
// 还款时间Year
// 偿还的本金Corpus 
// 偿还的 利息RateSum
// 剩余的本金LeavCorpus

row=document.createElement("tr")
cell=document.createElement("td");
cell.width = "100"
cell.innerHTML=mRecTimes;
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);


cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML=mRecYear.getFullYear() + "-" + (mRecYear.getMonth() + 1) + "-" + mRecYear.getDate();
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML=(Math.round(mRecRateSum * 100) / 100).toString();
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
fTotalInterest += mRecRateSum;//new Number(node.lastChild.text);
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML=Math.round(mRecCorpus * 100) / 100;
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
fTotalSum += mRecCorpus;//new Number(node.lastChild.text);
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML=Math.round((mRecRateSum + mRecCorpus) * 100) / 100;
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML=Math.round(mRecLeavCorpus * 100) / 100;
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);
table.appendChild(row);		
}
row=document.createElement("tr");//创建一行 

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
cell.innerHTML="合计";
row.appendChild(cell);


cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML = (Math.round(fTotalInterest * 100) / 100).toString();
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML = Math.round(fTotalSum * 100) / 100;
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML = Math.round((fTotalInterest + fTotalSum) * 100) / 100;
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

table.appendChild(row);
root.appendChild(table);
}

/// 等本付款的现金支出（还款计划）数据生成（本金还款法） 
/// <param name="document">还款表</param> 
/// <param name="LastSum">债务本金（贷款总额）</param>
/// <param name="BeginYear">债务开始年份（贷款日期）</param>
/// <param name="times">债务的期限（贷款期限（月份数））</param>
/// <param name="Rate">债务的年利率</param>
/// <param name="Freq">债务的偿还频率</param>
function ECorpus(LastSum,BeginYear,times,Rate,Freq)//贷款金额//当前时间//产生的记录数//年利率//偿还频率
{
var r,Inter,c;
var i=0,result=0;
var TotalLoan=LastSum;

var root = document.getElementById("result");
var elem;
var node;
var i, m;
var fTotalInterest = new Number(0), fTotalSum = new Number(0);
//清空xml中的数据
remove();
r=Rate*Freq/12;
if (times!=0)
c=LastSum/times;
else
c=0;
table=document.createElement("table");
//table.className="siteTable";
table.style.cssText="margin:0 20px; font-size:12px;border:1px solid #aaa;border-collapse:collapse;";

row=document.createElement("tr")
cell=document.createElement("td");
width = "100"
cell.innerHTML="期次";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);


cell=document.createElement("td");
width = "100"
cell.innerHTML="还款时间";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");
width = "100"
cell.innerHTML="偿还利息";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");
width = "100"
cell.innerHTML="偿还本金";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");
width = "100"
cell.innerHTML="偿还本息";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");
width = "100"
cell.innerHTML="剩余本金";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

table.appendChild(row);
for (i=0;i<times;i++)
{
RecTimes=i+1;
RecYear=new Date(BeginYear.getFullYear(), BeginYear.getMonth() + 1, BeginYear.getDate());
RecYear.setMonth(RecYear.getMonth()+(i*Freq));
Inter=LastSum*r;
LastSum=LastSum-c;
RecCorpus=c;
RecRateSum=Inter;
RecLeavCorpus=LastSum;

//-----绑定数据到表格-----
// 还款期次Times
// 还款时间Year
// 偿还的本金Corpus 
// 偿还的 利息RateSum
// 剩余的本金LeavCorpus	

row=document.createElement("tr")//创建一行 

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML=RecTimes;
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML = RecYear.getFullYear() + "-" + (RecYear.getMonth() + 1) + "-" + RecYear.getDate();
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML= (Math.round(RecRateSum * 100) / 100).toString();
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
fTotalInterest += RecRateSum;//new Number(node.lastChild.text);
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元
cell.width = "100"
cell.innerHTML = Math.round(RecCorpus * 100) / 100;
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
fTotalSum += RecCorpus;//new Number(node.lastChild.text);
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML = Math.round((RecRateSum + RecCorpus) * 100) / 100;
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML = Math.round(RecLeavCorpus * 100) / 100;
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);
table.appendChild(row);
}
row=document.createElement("tr");//创建一行 

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML="合计";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);


cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML = (Math.round(fTotalInterest * 100) / 100).toString();
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML = Math.round(fTotalSum * 100) / 100;
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML = Math.round((fTotalInterest + fTotalSum) * 100) / 100;
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

table.appendChild(row);
root.appendChild(table);
}

///一次性还本付息的现金支出（还款计划）数据生成 
/// <param name="document">还款表</param>
/// <param name="LastSum">债务本金（贷款总额）</param>
/// <param name="BeginYear"> 债务开始年份（贷款日期）</param>
/// <param name="EndYear">债务结束年份（贷款日期）</param>
/// <param name="Rate">债务的年利率</param>
function CalcPayOnce(LastSum,BeginYear,EndYear,Rate)//贷款金额//当前时间//偿还时间//年利率
{
var result=0;
var r;
var days;
	
var root = document.getElementById("result");
var elem;
var node;
var i, m;
//清空xml中的数据
remove();
r=Rate;
days=GetDayLen(EndYear,BeginYear);//计算债务期限（天数）
RecYear=EndYear;
RecTimes =1;
RecCorpus=new Number(LastSum);
RecRateSum=RecCorpus*r/360*days;
RecLeavCorpus=0;

//-----绑定数据到表格-----	
// 还款期次Times
// 还款时间Year
// 偿还的本金Corpus 
// 偿还的 利息RateSum
// 剩余的本金LeavCorpus
table=document.createElement("table");
table.className="siteTable";
table.style.cssText="margin:0 20px; font-size:12px;border:1px solid #aaa;border-collapse:collapse;";

row=document.createElement("tr")
cell=document.createElement("td");
width = "100"
cell.innerHTML="期次";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);


cell=document.createElement("td");
width = "100"
cell.innerHTML="还款时间";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");
width = "100"
cell.innerHTML="偿还利息";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");
width = "100"
cell.innerHTML="偿还本金";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");
width = "100"
cell.innerHTML="偿还本息";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");
width = "100"
cell.innerHTML="剩余本金";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

table.appendChild(row);

row=document.createElement("tr");//创建一行 

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML="合计";
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML=RecYear.getFullYear() + "-" + (RecYear.getMonth() + 1) + "-" + RecYear.getDate();
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML = (Math.round(RecRateSum * 100) / 100).toString();
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML = Math.round(RecCorpus * 100) / 100;
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML = Math.round((RecRateSum + RecCorpus) * 100) / 100;
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

cell=document.createElement("td");//创建一个单元 
cell.width = "100"
cell.innerHTML = Math.round(RecLeavCorpus * 100) / 100;
cell.style.cssText="border:1px solid #aaa;padding:2px 5px 2px 5px;";
row.appendChild(cell);

table.appendChild(row);
root.appendChild(table);
}
	
/// 计算段后计息天数
/// <param name="StartDate">债务结束日期</param>
/// <param name="StandDate">债务开始日期或者标准日期（1999年11月1日）</param>
/// <returns></returns>
function GetDayLen(StartDate,StandDate)
{
//以每月30天算一月，一年为360天
return (StartDate.getYear()-StandDate.getYear())*360+(StartDate.getMonth()-StandDate.getMonth())*30+(StartDate.getDate()-StandDate.getDate());
}
</script>
