//����С��Ļ�ֻ�������ʽ����һ����������ʽ����
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
			
//����С��Ļ�ֻ��������ʽ,��һ�������Ǿ����Ƿ�����paddingLeft,�ڶ����������������
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

//�첽�ֻ�֧����һ���ֻ������Ӧ���ſ�ʱ����̬�ı俨�������Ŀ��
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