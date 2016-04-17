	//账单日期
	var date=$("#date").val();
	//当月份发生变化时执行方法
	$(document).ready(function(){
			$(".check_box .select_date .date_box li").click(function(){
			$(".check_box .select_date .date_box li").removeClass("current_mounth");
			$(this).addClass("current_mounth");
			m=$(this).index();
			date = $(this).val();
			alert(date);
			messageRevealWait();
		});
	});
	//等待加载的界面显示和隐藏（卡号变化时执行方法）
	function messageRevealWait(){
		$(".wait_box").fadeIn(500);
		setTimeout(function(){
			getBillingSummaryAjax();
			},4000);
		setTimeout(function(){
				$(".wait_box").fadeOut(500);
			},2000);
		}
	//ajax获取账单
	function getBillingSummaryAjax(){
		alert(date);
		var cardNo = $("#cardNo").val();
		var cardNoWarning = $("#cardNoWarning");
		var noBillingWarning = $("#noBillingWarning");
		cardNoWarning.text("");
		noBillingWarning.text("");
		if (cardNo == null || cardNo == "") {
			cardNoWarning.text("*请选择卡号！");
            return;
        }
		 $.ajax({
	            url: "getBillingSummary.do",
	            data: {
	            	cardNo: cardNo,
	                date: date
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
	function messageReveal(json) {
		if(json[0].closingBalance>0){
			$(".view_value  #value1").parent(".value").children(".valueState").show();
				$(".view_value  #value1").css("color","#e05d4f");
				$(".view_value  #value1").css("font-size","22px");
				$(".view_value  #value1").parent(".value").parent(".value_box").children("#check_dt1").show();
			document.getElementById("value1").innerText = json[0].closingBalance;
			}else if(json[0].closingBalance==0){
				$(".view_value .RMBdebt").show();
				document.getElementById("value1").innerText="本卡当前月未出"+json[0].currencyCode+"账单";
				$(".view_value  #value1").parent(".value").children(".valueState").hide();
				$(".view_value  #value1").css("color","#999999");
				$(".view_value  #value1").css("font-size","14px");
				$(".view_value  #value1").parent(".value").parent(".value_box").children("#check_dt1").hide();
			}else{
				$(".view_value .RMBdebt").hide();
				
			}
		//分是否为双币单币卡
		if(json.length>1){
			$(".view_value").show();
			$(".value_box").eq(0).show();
			$("value_box").eq(1).show();
			$(".pay_box .pay_value  .doubleState1").show();
			$(".pay_box .pay_value  .doubleState").show();
		}else if(json.length==1){
			$(".view_value").show();
			$(".value_box").eq(0).show();
			$(".value_box").eq(1).hide();
			$(".pay_box .pay_value  .doubleState1").show();
			$(".pay_box .pay_value  .doubleState").hide();
	
			}else{
				$(".view_value").hide();
				$(".value_box").eq(0).hide();
				$(".value_box").eq(1).hide();
				$(".pay_box .pay_value  .doubleState1").hide();
				$(".pay_box .pay_value  .doubleState").hide();
				}
		var n=0;
		n=document.value_debt.messagePick.selectedIndex;
		document.getElementById("pay_date").innerText = json[0].paymentDueDate;
		document.getElementById("periodEndDate").innerText = json[0].periodEndDate;
		document.getElementById("currencyCode1").innerText = json[0].currencyCode;
		document.getElementById("currencyCode3").innerText = json[0].currencyCode;
		document.getElementById("closingBalance1").innerText = json[0].closingBalance;
		document.getElementById("currencyCode5").innerText = json[0].currencyCode;
		document.getElementById("minPaymentAmount1").innerText = json[0].minPaymentAmount;
		if(json.length>1){
			document.getElementById("closingBalance2").innerText = json[1].closingBalance;
			document.getElementById("minPaymentAmount2").innerText = json[1].minPaymentAmount;
			document.getElementById("currencyCode2").innerText = json[1].currencyCode;
			document.getElementById("currencyCode4").innerText = json[1].currencyCode;
			document.getElementById("currencyCode4").innerText = json[1].currencyCode;
			document.getElementById("currencyCode6").innerText = json[1].currencyCode;
		if(json[1].closingBalance>0){
			$(".view_value  #value2").parent(".value").children(".valueState").show();
				$(".view_value  #value2").css("color","#e05d4f");
				$(".view_value  #value2").css("font-size","22px");
				$(".view_value  #value2").parent(".value").parent(".value_box").children("#check_dt2").show();
				document.getElementById("value2").innerText = json[1].closingBalance;
			}else if(json[1].closingBalance==0){
				$(".view_value .dollarDebt").show();
				document.getElementById("value2").innerText="本卡当前月未出"+json[1].currencyCode+"账单";
				$(".view_value  #value2").parent(".value").children(".valueState").hide();
				$(".view_value  #value2").css("color","#999999");
				$(".view_value  #value2").css("font-size","14px");
				$(".view_value  #value2").parent(".value").parent(".value_box").children("#check_dt2").hide();
			}else{
				$(".view_value .dollarDebt").hide();
			}
		}
	}
	
$(function(){
	//选择时间月份
		var n=0;
		$(".check_box .select_date .left").click(function(){
			if(n<$(".check_box .select_date .date_box li").length-8){
				n=n+4;
				$(".check_box .select_date .date_box ul").animate({marginRight:-1*$(".check_box .select_date .date_box li").width()*n},1000);
				$(".check_box .select_date .left").removeClass("left2").addClass("left1");
				$(".check_box .select_date .right").removeClass("right2").addClass("right1");
			}else{
				$(".check_box .select_date .left").removeClass("left1").addClass("left2");
				$(".check_box .select_date .right").removeClass("right2").addClass("right1");
				$(".check_box .select_date .date_box ul").animate({marginRight:-1*$(".check_box .select_date .date_box li").width()*8},1000);
				n=8;
				}
		});
		$(".check_box .select_date .right").click(function(){
			if(n>4){
				n=n-4;
				$(".check_box .select_date .date_box ul").animate({marginRight:-1*$(".check_box .select_date .date_box li").width()*n},1000);
				$(".check_box .select_date .left").removeClass("left2").addClass("left1");
				$(".check_box .select_date .right").removeClass("right2").addClass("right");
			}else{
				$(".check_box .select_date .left").removeClass("left2").addClass("left1");
				$(".check_box .select_date .right").removeClass("right1").addClass("right2");
				$(".check_box .select_date .date_box ul").animate({marginRight:0},1000);
				n=0;
				}
		});
})
