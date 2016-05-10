
// JavaScript Document
$(function(){
		   $("input[name=accNo]").focus(function(){
										   if($(this).val()=="信用卡/借记卡"){
										   $(this).val("")
										   }
										   $(this).addClass("f24")
										   })
		   $("input[name=accNo]").blur(function(){
										  if($(this).val()==""){
										   $(this).val("信用卡/借记卡")
										   $(this).css("color","#ccc")
										   $(this).css("font-size","12px")
										   $(this).css("line-height","37px")
										   } 
										  })
										  $(this).css("color","#000")
										  $(this).css("line-height","37px")
										  
		   })		   