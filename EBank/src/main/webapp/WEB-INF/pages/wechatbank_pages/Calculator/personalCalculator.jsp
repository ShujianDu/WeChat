<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人所得税计算器</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/wechatbank/index.css"/>" />
<script language="javascript" ></script>
</head>
<body>
<header>
    <h2>个人所得税计算器</h2>
</header>
</div>
    <div class="topOneB mar-1 topOneBradius">
        <table class="topTwo" >
            <tr>
                <td>收入类型</td>
            </tr>
            <tr><td>
                <select id="ddlIncomeType"  name="ddlIncomeType"  onchange="Incometypechg();">
                <option value="0" selected="selected">工资、薪金所得</option>
                <option value="2">个体工商户的生产、经营所得 (年度)</option>
                <option value="3">对企事业单位的承包经营、承租经营所得 (年度)</option>
                <option value="4">劳务报酬所得</option>
                <option value="5">稿酬所得</option>
                <option value="6">特许权使用费所得</option>
                <option value="8">财产租赁所得</option>
                <option value="9">财产转让所得</option>
                <option value="7">利息、股息、红利所得</option>
                <option value="10">偶然所得</option>
                </select>
                </td> 
            </tr>
        </table>
    </div>
    <div class="topOneB  mar-1">
        <table class="topTwo">
            <tr>
                <td>税前收入(元)</td>
            </tr>
            <tr>
                <td><input type="text" id="tbIncomeBeforeTax" maxlength="9"/></td>
                <span style="font-size:15px;"><font color="#FF0000" id="alerttbIncomeBeforeTax"></font></span>
            </tr>
        </table>
    </div>
    <div class="topOneB  mar-1">
        <table class="topTwo">
            <tr>
                <td>各项社会保险费(元)</td>
            </tr>
            <tr>
                <td><input type="text" id="tbAllInsureFee" maxlength="9"/></td>
                <span style="font-size:15px;"><font color="#FF0000" id="alerttbAllInsureFee"></font></span>
            </tr>
        </table>
    </div>
    <div class="topOneB">
        <table class="topTwo">
            <tr>
                <td>起征额(元)</td>
            </tr>
            <tr>
                <td><input type="text" id="tbThresholdSum"  value="1600" maxlength="9"/></td>
                <span style="font-size:15px;"><font color="#FF0000" id="alerttbThresholdSum"></font></span>
            </tr>
        </table>
    </div>
   

<div class="HandIn">
    <input type="button" value="计算"  onclick="CalcTax();" />
</div>
<div class="topOneB mar-1 topOneBradius" style="margin-top: 10px">
        <table class="topTwo " >
            <tr>
                <td>应纳税款(元):</td>
            </tr>
            <tr>
                <td><input type="text" id="tbTax" readonly="readonly"/></td>
            </tr>
        </table>
    </div>
    <div class="topOneB bottomOneBradius">
        <table class="topTwo">
            <tr>
                <td>税后收(元):</td>
            </tr>
            <tr>
                <td><input type="text" id="tbIncomeAfterTax" readonly="readonly"/></td>
            </tr>
        </table>
    </div>
</body>
</html>
</script><script language="javascript">		
function Incometypechg()
	{
		if (this.document.all.ddlIncomeType.selectedIndex==0)
		{			    
			this.document.all.tbThresholdSum.disabled = false;
			this.document.all.tbAllInsureFee.disabled = false;
			this.document.all.tbThresholdSum.value = "1600";
			this.document.all.tbAllInsureFee.value = "0";
		}
		else
		{
			this.document.all.tbThresholdSum.disabled = true;
			this.document.all.tbAllInsureFee.disabled = true;
			this.document.all.tbThresholdSum.value = "不填";
			this.document.all.tbAllInsureFee.value = "不填";
		}	
	}
	window.attachEvent("onload",Incometypechg);

	
	function CalcTax()
	{
		IncomeType = document.getElementById("ddlIncomeType").value;		 //收入类型
		IncomeBeforeTax = document.getElementById("tbIncomeBeforeTax").value;//税前收入
		var reg=/^[0-9]{1,9}$/;
		if(IncomeBeforeTax==""){
			document.getElementById("alerttbIncomeBeforeTax").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;请输入税前收入";
			return;
		}
		if(!reg.test(IncomeBeforeTax)){
			document.getElementById("alerttbIncomeBeforeTax").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;请输入正整数";
			return;
		}else{
			document.getElementById("alerttbIncomeBeforeTax").innerHTML="";
		}
		  
		if (IncomeType==0)
		{	
			AllInsureFee = document.getElementById("tbAllInsureFee").value;//各项社会保险费
			ThresholdSum = document.getElementById("tbThresholdSum").value;//起征额
			if(AllInsureFee==""){
				document.getElementById("alerttbAllInsureFee").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;请输入各项社会保险费";
				return;
			}else if(!reg.test(AllInsureFee)){
				document.getElementById("alerttbAllInsureFee").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;请输入正整数";	
				return;
			}else if(parseInt(AllInsureFee)>parseInt(IncomeBeforeTax)){
				document.getElementById("alerttbAllInsureFee").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;各项社会保险费超出税前收入!";	
			}else{
				document.getElementById("alerttbAllInsureFee").innerHTML="";
			}
			if(ThresholdSum==""){
				document.getElementById("alerttbThresholdSum").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;请输入起征额";
				return;
			}else if(!reg.test(ThresholdSum)){
				document.getElementById("alerttbThresholdSum").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;请输入正整数";
				return;
			}else if(parseInt(ThresholdSum)>parseInt(IncomeBeforeTax)){
				document.getElementById("alerttbThresholdSum").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;起征额超出税前收入!";	
			}else{
				document.getElementById("alerttbThresholdSum").innerHTML="";
			}
			
			if((parseInt(ThresholdSum)+parseInt(AllInsureFee))>parseInt(IncomeBeforeTax)){
				document.getElementById("alerttbThresholdSum").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp;应缴税款总额超出税前收入!";	
			}else{
				document.getElementById("alerttbThresholdSum").innerHTML="";
			}
		}
		sum= new Array(500,2000,5000,20000,40000,60000,80000,100000,100000);
		rate= new Array(5,10,15,20,25,30,35,40,45);
		tax= new Array(0,25,125,375,1375,3375,6375,10375,15375);

		sum2= new Array(5000,10000,30000,50000,50000);
		rate2= new Array(5,10,20,30,35);
		tax2= new Array(0,250,1250,4250,6750); 

		sum4= new Array(20000,50000,50000);
		rate4= new Array(20,30,40);
		tax4= new Array(0,2000,7000); 
		
		
		
		var Sums=0; // 计算得出：应纳税款。（元，正数，保留两位小数）
		var j;		// 计算得出：税后收入。（元，正数，保留两位小数）
	  
		
		switch (IncomeType) 
		{   
			case "0":  //工资、薪金所得（1）
				Sums=IncomeBeforeTax-AllInsureFee-ThresholdSum;
				j=GetRate(Sums,sum);
				//( 月收入额－Point）×适用税率－速算扣除数
				Tax=Sums*rate[j]/100-tax[j];
				break;
			case "2"://个体工商户的生产、经营所得（年度）（2)
				ThresholdSum=0;
				Sums=IncomeBeforeTax-ThresholdSum*12;
				j=GetRate(Sums,sum2);
				//=（全年收入总额－成本、费用以及损失）×适用税率－速算扣除数
				Tax=Sums*rate2[j]/100-tax2[j];
				break;
			case "3"://对企事业单位的承包经营、承租经营所得（年度）（3）
				//ThresholdSum=800;
				Sums=IncomeBeforeTax;//-ThresholdSum*12;
				j=GetRate(Sums,sum2);
				//=（全年收入总额－成本、费用以及损失）×适用税率－速算扣除数
				Tax=Sums*rate2[j]/100-tax2[j];
				break;
			case "4"://劳务报酬所得（4）
				if (IncomeBeforeTax<=4000) //每次收入不足4000元的
				{
					Tax=(IncomeBeforeTax-800)*0.2;
				}
				else//每次收入超过4000元以上的
				{	 
					Sums=IncomeBeforeTax*0.8;
					j=GetRate(Sums,sum4);
					//=（全年收入总额－成本、费用以及损失）×适用税率－速算扣除数
					Tax=Sums*rate4[j]/100-tax4[j];
				}
				break;
			case "5"://稿酬所得（5）
				if (IncomeBeforeTax<=4000)//每次收入不足4000元的
					Tax=(IncomeBeforeTax-800)*0.2*(1-0.3);
				else Tax=IncomeBeforeTax*(1-0.2)*0.2*(1-0.3);
				break;
				
			
			case "6"://特需权使用费所得（6）
			
				if (IncomeBeforeTax<=4000)//每次收入不足4000元的
					Tax=(IncomeBeforeTax-800)*0.2;
				else Tax=IncomeBeforeTax*(1-0.2)*0.2;
				break;	
			case "7"://利息、股息、红利所得（7）
				Tax=IncomeBeforeTax*0.2;
				
				break;
			case "8"://财产租赁所得（8）
		
			   if (IncomeBeforeTax<=4000)//每次收入不足4000元的
					
					 Tax=(IncomeBeforeTax-800)*0.2;
				else 
				
				Tax=IncomeBeforeTax*(1-0.2)*0.2;
				
				break;
			case "9"://财产转让所得（9）
			    Tax=IncomeBeforeTax*0.2;
				break;
			
			case "10"://偶然所得（10）
				Tax=IncomeBeforeTax*0.2;
				break;	
				}
		
		
		if(Tax<0)
			Tax=0;
		else
	        Tax=Tax.toFixed(2);
		if(IncomeType==0)
			IncomeAfterTax=IncomeBeforeTax-AllInsureFee-Tax;
		else
			IncomeAfterTax=IncomeBeforeTax-Tax;
		if(IncomeAfterTax<0)
			IncomeAfterTax=0;
		else
			IncomeAfterTax=IncomeAfterTax.toFixed(2);
		
		document.getElementById("tbTax").value=Tax;
		document.getElementById("tbIncomeAfterTax").value=IncomeAfterTax;
	}

	function GetRate(Sums,List)
	{
		var i=0;
		for (i=0;i<List.length;i++)
		{
			if(Sums<List[i])
				break;
		}
		if (i == List.length) i = List.length - 1;
		return i;
	}
</script>
