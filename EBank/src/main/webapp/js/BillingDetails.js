//ajax获取账单
	function getMore(){
		var cardNo=$("#cardNo").val();
		var queryType=$("#queryType").val();
		var startnum=$("#startnum").val();
		var periodStartDate=$("#periodStartDate").val();
		var periodEndDate=$("#periodEndDateAjax").val();
		var currencyCodeReal=$("#currencyCodeReal").val();
		var noBillingWarning = $("#noBillingWarning");
		noBillingWarning.text("");
		 $.ajax({
	            url: "getMoreBillingDetail.do",
	            data: {
	            	cardNo: cardNo,
	            	queryType: queryType,
	            	startnum:startnum,
	            	periodStartDate:periodStartDate,
	            	periodEndDate:periodEndDate,
	            	currencyCode:currencyCodeReal
	            },
	            type: "post",
	            dataType: "json",
	            async: false,
	            success: function (json) {
	                if (json != null && json != "") {
	                	messageReveal(json);
	                }else if(json==""){
	                	noBillingWarning.text("*很抱歉，没有查询到您当月的账单！");
	                }else{
	                	window.location.href = "../error.html";
	                }
	            }
	        });
		}
	//获取更多按钮样式
	$("#readMore").css({width:'80%',height:'36px',textAlign:'center',lineHeight:'36px',fontSize:'16px',backgroundColor:'#e05d4f',marginLeft:'10%',color:'white',borderRadius:'8px',marginTop:'20px'});
	//处理ajax请求返回的数据
	function messageReveal(json) {
		var onepage = $("#onepage").val();
		//如果数据小于十条，获取更多按钮隐藏
		if(json.length<onepage){$("#readMore").hide();}
		for(var i=0;i<json.length;i++){
			var debitCreditCode;
			if(json[i].debitCreditCode=="CRED"){
				debitCreditCode="存入";
			}else if(json[i].debitCreditCode=="DEBT"){
				debitCreditCode="支出";
			}
			var newDiv = "<li class='Billing_box'>" +
			"<span class='BillingName'>"+json[i].transactionDescription+"</span>" +
			"<span class='BillingStage'>"+debitCreditCode+"</span>" +
			"<span class='periodEndDate'>"+json[i].transactionDate+"</span>" +
			"<span class='currencyCode'><span >"+json[i].currencyCode+"</span>" +
			"<span >"+json[i].transactionAmount+"</span></span></li>";
			$("#showMoreNChildren").append(newDiv);
			$(".BillingName").css({fontSize:'14px',color:'#666666',float:'left',marginLeft:'16px',lineHeight:'40px'});
			$(".BillingStage").css({fontSize:'14px',color:'#999999',float:'right',marginRight:'16px',lineHeight:'40px'});
			$(".periodEndDate").css({fontSize:'12px',color:'#999999',float:'left',marginLeft:'16px',lineHeight:'20px',clear:'left'});
			$(".currencyCode").css({fontSize:'14px',color:'#666666',float:'right',marginRight:'16px',lineHeight:'20px',clear:'right'});
			$(".currencyCode span").css({fontSize:'14px',color:'#666666'});
		}
}