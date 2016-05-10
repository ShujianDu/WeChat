//动态改变各输入栏的宽度（用于同步支付）
function Syn_InitInputWidth()
{
	var menuObj = parent.GetMenuObj();
	if(menuObj != null)
	{
		var BaseInputBoxWidth = menuObj.scrollWidth - 125;
		if(BaseInputBoxWidth < 150)
		{
			BaseInputBoxWidth = 150;
		}
		var NumbOfArguments = arguments.length;
		if(NumbOfArguments > 0)
		{
			for(var i = 0; i < NumbOfArguments; i++)
			{			
				var Obj = document.getElementById(arguments[i]);
				if(Obj != null)
				{
					var ObjWidth = BaseInputBoxWidth - Obj.getAttribute("adjustment");
					if(arguments[i] == "txtMonth" || arguments[i] == "txtYear")
					{
						ObjWidth = ObjWidth * 0.5;
					}
					Obj.style.width = ObjWidth + "px";
				}
			}
		}
	}
}

//动态改变各输入栏的宽度（用于异步支付）
function Asyn_InitInputWidth()
{
	var BaseInputBoxWidth = document.body.clientWidth - 125;
	if(BaseInputBoxWidth < 160)
	{
		BaseInputBoxWidth = 160;
	}
	var NumbOfArguments = arguments.length;
	if(NumbOfArguments > 0)
	{
		for(var i = 0; i < NumbOfArguments; i++)
		{			
			var Obj = document.getElementById(arguments[i]);
			if(Obj != null)
			{
				var ObjWidth = BaseInputBoxWidth - Obj.getAttribute("adjustment");
				if(arguments[i] == "txtMonth" || arguments[i] == "txtYear")
				{
					ObjWidth = ObjWidth * 0.5;
				}
				Obj.style.width = ObjWidth + "px";
			}
		}
	}
}

//动态改变支付区域的高度
function iframeAutoHeight()
{
	var objIframe = document.getElementById("mainWorkArea");
	if (objIframe != null)
	{ 
		if (objIframe.contentDocument && objIframe.contentDocument.body.offsetHeight)
		{ 
			objIframe.height = objIframe.contentDocument.body.offsetHeight + 35; 
		} 
		else if (objIframe.Document && objIframe.Document.body.scrollHeight)
		{ 
			objIframe.height = objIframe.Document.body.scrollHeight + 35;
		}
		else
		{
			objIframe.height = 430;
		}
	}
}

//控制订单信息中日期、订单号和币种的收缩
function SpdOrShkBill()
{
	var objSpdOrShk = document.getElementById("SpdOrShk");
	var objDottedLine = document.getElementById("DottedLine");
	var objDateOrderCurrency = document.getElementById("DateOrderCurrency");
	if(objSpdOrShk.className == "spread")
	{
		objDottedLine.style.display = "none";
		objDateOrderCurrency.style.display = "none";
		objSpdOrShk.className = "shrink";
	}
	else
	{
		objDottedLine.style.display = "block";
		objDateOrderCurrency.style.display = "block";
		objSpdOrShk.className = "spread";
	}
}

//决断用户输入的金额格式是否正确
function beAmount(str)
{
   if ((str == null) || (str.length == 0))
   {
   	return false;
   }

   var chCurrent;
   var dotFounded = false;
   var dotIndex=-1;
   var lenBeforeDot = str.length;
   var lenAfterDot = 0;

   for (i=0; i<str.length; i++)
   {
   	chCurrent = str.charAt(i);
   	if (!beDigit(chCurrent))
   	{
   		if (chCurrent == '.')
   		{
   			if (dotFounded)
   			{
   				return false;
   			}
   			dotFounded = true;
   			dotIndex = i;
   			if ((dotIndex == 0) || (dotIndex == str.length-1))
   			{
   				return false;
   			}
   			lenBeforeDot = dotIndex;
   			lenAfterDot = str.length - dotIndex -1;
   		}
   		else
   		{
   			return false;
   		}
   	}
   }
   if ((lenBeforeDot > 11) || (lenAfterDot >2))
   {
   	return false;
   	}
   	return true;
}
//判断字符cCheck是否为数字
function beDigit(cCheck)
{
    return (('0'<=cCheck) && (cCheck<='9'));
}

//判断字符串numb是否全为数字字符
function isAllDigit(numb)
{
	for(var i=0 ; i < numb.length ; i++)
	{
		if(!beDigit(numb.substring(i , i + 1 )))
		{
			return false;
		}
	}
	return true;
}

//判断用户输入的信用卡月份是否有效
function isValidMonth(month)
{
	if(!isAllDigit(month))
	{
		return false;
	}
	if(month < 0 || month > 12)
	{
		return false;
	}
	return true;
}


//异常定义
function ErrException(msg,obj){
	return {ErrMsg:msg, ErrObj:obj};
}

// 用'*'掩盖帐号部分数字
function MaskAccountNo(acctNo)
{
	var MaskAcctNo = "";
	if (acctNo.length == 0)
	{
		return "";
	}
	if (acctNo.length == 16)
	{
		MaskAcctNo = acctNo.substring(0,6);
		MaskAcctNo += "******";
		MaskAcctNo += acctNo.substring(12,16);
	}
	else if (acctNo.length == 8)
	{
		MaskAcctNo = acctNo.substring(0,4);
		MaskAcctNo += "****";
	}
	else if (acctNo.length == 12)
	{
		MaskAcctNo = acctNo.substring(0,8);
		MaskAcctNo += "****";
	}
	else if (acctNo.length == 15)
	{
		MaskAcctNo = acctNo.substring(0,6);
		MaskAcctNo += "*****";
		MaskAcctNo += acctNo.substring(11,15);
	}
	else if (acctNo.length <= 4) 
	{
		MaskAcctNo = acctNo;
	}
	else
	{
		MaskAcctNo = acctNo.substring(0, acctNo.length - 4);
		MaskAcctNo += "****";
	}
	return MaskAcctNo;
}