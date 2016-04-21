// JavaScript Document

$(function(){

	var height=parseInt(document.documentElement.clientHeight)-211;
	
	window.onload=function(){if($(".PointDetails_content").height()<height){
	
	$(".PointDetails_content").height(parseInt(document.documentElement.clientHeight)-211);
	}}
})
