// JavaScript Document
$(function(){

		var height=parseInt(document.documentElement.clientHeight)-175;
		if($(".IntegrationContainer").height()<height){
			$(".IntegrationContainer").height(parseInt(document.documentElement.clientHeight)-175);
		}
	//black显示和隐藏
		$(".myPoint_box .myPoint_view .help").click(function(){
			$(".black_box").fadeIn(500);	
		})
		$(".black_box .tip_close").click(function(){
			$(".black_box").fadeOut(500);	
			})
			$(".black_box .tip_close2").click(function(){
			$(".black_box").fadeOut(500);	
		})

})