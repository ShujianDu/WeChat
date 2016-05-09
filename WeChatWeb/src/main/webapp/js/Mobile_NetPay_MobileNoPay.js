//设置小屏幕手机标题样式，第一个参数是样式名称
function setTitleClass()
{
	var argumentsLength = arguments.length;
	if(argumentsLength > 0)
	{
		for(var i = 0 ; i < argumentsLength ; i++)
		{
			var Obj = document.getElementById(arguments[i]);
			if(Obj != null)
			{
				Obj.className = arguments[0];
			}
		}
	}
}
			
//设置小屏幕手机输入框样式,第一个参数是决定是否设置paddingLeft,第二个参数是输入框宽度
function setWidth()
{
	var argumentsLength = arguments.length;
	if(argumentsLength > 2)
	{
		for(var i = 2 ; i < argumentsLength ; i++)
		{
			var Obj = document.getElementById(arguments[i]);
			if(Obj != null)
			{
				Obj.style.width = arguments[1] + "px";
				if(arguments[0] == "set")
				{
					Obj.style.paddingLeft = "10px";
				}
			}
		}
	}
}

//异步手机支付当一个手机号码对应多张卡时，动态改变卡号输入框的宽度
function ChangCardNoInputWidth()
{
	var BaseInputBoxWidth = document.body.clientWidth - 125;
	if(BaseInputBoxWidth < 170)
	{
		BaseInputBoxWidth = 170;
	}
	var objInputCardNo = document.getElementById("txtCardNo");
	if(objInputCardNo != null)
	{
		objInputCardNo.style.margin = "0px";
		var objInputPassword = document.getElementById("txtPassword");
		if( objInputPassword != null)
		{
			var objWidth = BaseInputBoxWidth - objInputPassword.getAttribute("adjustment");
			objInputCardNo.style.width = objWidth + "px";
		}
	}
}