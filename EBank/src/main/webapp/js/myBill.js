	//账单日期
	var date=$("#date").val();
	//当月份发生变化时执行方法
	$(document).ready(function(){
		    $(".pay_box").hide();
			$(".check_box .select_date .date_box li").click(function(){
			$(".check_box .select_date .date_box li").removeClass("current_mounth");
			$(this).addClass("current_mounth");
			date = $(this).val();
			messageRevealWait();
		});
	});
	//等待加载的界面显示和隐藏（卡号变化时执行方法）
	function messageRevealWait(){
		$(".wait_box").fadeIn(500);
			getBillingSummaryAjax();
		}
	//ajax获取账单
	function getBillingSummaryAjax(){
		var cardNo = $("#cardNo").val();
		var cardNoWarning = $("#cardNoWarning");
		var noBillingWarning = $("#noBillingWarning");
		cardNoWarning.text("");
		noBillingWarning.text("");
		if (cardNo == null || cardNo == "") {
			cardNoWarning.text("*请选择卡号！");
			$(".wait_box").fadeOut(500);
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
	            	$(".wait_box").fadeOut(500);
	                if (json != null && json != "") {
	                	messageReveal(json);
	                }else if(json==""){
	                	noBillingWarning.text("*很抱歉，没有查询到您当月的账单！");
	                	$(".pay_box").hide();
	                }else{
	                	window.location.href = "../error.html";
	                }
	            }
	        });
		}
	function messageReveal(json) {
		
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
		if(json[0].closingBalance==0&&json[1].closingBalance==0){
			$(".pay_box").hide();
		}else{
			$(".pay_box").show();
		}
		var n=0;
		n=document.value_debt.messagePick.selectedIndex;
		$("#pay_date").text(json[0].paymentDueDate);
		$("#periodStartDate").text(json[0].periodStartDate);
		$("#periodEndDate").text(json[0].periodEndDate);
		$("#currencyCodeReal1").text(json[0].currencyCode);
		//显示中文币种
		$("#currencyCode1").text(json[0].currencyChinaCode);
		$("#currencyCode3").text(json[0].currencyChinaCode);
		$("#closingBalance1").text(json[0].closingBalance);
		$("#minPaymentAmount1").text(json[0].minPaymentAmount);
		if(json.length>1){
			if(json[1].closingBalance!=null){
				$("#pay_date").text(json[1].periodEndDate);
			}
			$("#closingBalance2").text(json[1].closingBalance);
			$("#minPaymentAmount2").text(json[1].minPaymentAmount);
			$("#currencyCodeReal2").text(json[1].currencyCode);
			//显示中文币种
			$("#currencyCode2").text(json[1].currencyChinaCode);
			$("#currencyCode4").text(json[1].currencyChinaCode);
		if(json[1].closingBalance>0){
			$(".view_value  #value2").parent(".value").children(".valueState").show();
				$(".view_value  #value2").css("color","#e05d4f");
				$(".view_value  #value2").css("font-size","22px");
				$(".view_value  #value2").parent(".value").parent(".value_box").children("#check_dt2").show();
				$("#value2").text(json[1].closingBalance);
				$("#doubleState3").show();
				$("#doubleState4").show();
			}else if(json[1].closingBalance==0){
				$(".view_value .dollarDebt").show();
				$("#doubleState3").hide();
				$("#doubleState4").hide();
				$("#value2").text("本卡当前月未出"+json[1].currencyCode+"账单");
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
