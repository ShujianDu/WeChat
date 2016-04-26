<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="../../base_pages/base.jsp"%>
<meta http-equiv="Content-Type"
	content="application/xhtml+xml; charset=utf-8" />
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<style type="text/css">
body, html {
	width: 100%;
	height: 100%;
	margin: 0;
}

#allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
}

header {
	position: absolute;
	background: #ddd;
	border-bottom: 1px solid #115B76;
	height: 37px;
	position: relative;
	box-shadow: #999 0 5px 5px;
	overflow: hidden;
	background-image: -webkit-gradient(linear, left top, left bottom, from(#fefefe
		/*{a-bup-background-start}*/), to(#ddd /*{a-bup-background-end}*/));
	/* Saf4+, Chrome */
	background-image: -webkit-linear-gradient(#fefefe /*{a-bup-background-start}*/, #ddd
		/*{a-bup-background-end}*/); /* Chrome 10+, Saf5.1+ */
	background-image: -moz-linear-gradient(#fefefe /*{a-bup-background-start}*/, #ddd
		/*{a-bup-background-end}*/); /* FF3.6 */
	background-image: -ms-linear-gradient(#fefefe /*{a-bup-background-start}*/, #ddd
		/*{a-bup-background-end}*/); /* IE10 */
	background-image: -o-linear-gradient(#fefefe /*{a-bup-background-start}*/, #ddd
		/*{a-bup-background-end}*/); /* Opera 11.10+ */
	background-image: linear-gradient(#fefefe /*{a-bup-background-start}*/, #ddd
		/*{a-bup-background-end}*/);
}

header table {
	width: 150px;
	text-align: center;
}

button {
	width: 100%;
	font-size: 14px;
	height: 30px;
	text-shadow: #fff 1px 1px 1px;
	background-image: -webkit-gradient(linear, left top, left bottom, from(#fefefe
		/*{a-bup-background-start}*/), to(#ddd /*{a-bup-background-end}*/));
	/* Saf4+, Chrome */
	background-image: -webkit-linear-gradient(#fefefe /*{a-bup-background-start}*/, #ddd
		/*{a-bup-background-end}*/); /* Chrome 10+, Saf5.1+ */
	background-image: -moz-linear-gradient(#fefefe /*{a-bup-background-start}*/, #ddd
		/*{a-bup-background-end}*/); /* FF3.6 */
	background-image: -ms-linear-gradient(#fefefe /*{a-bup-background-start}*/, #ddd
		/*{a-bup-background-end}*/); /* IE10 */
	background-image: -o-linear-gradient(#fefefe /*{a-bup-background-start}*/, #ddd
		/*{a-bup-background-end}*/); /* Opera 11.10+ */
	background-image: linear-gradient(#fefefe /*{a-bup-background-start}*/, #ddd
		/*{a-bup-background-end}*/);
	border: 1px solid #aaa;
}

.botton {
	background-image: -webkit-gradient(linear, left top, left bottom, from(#ddd
		/*{a-bup-background-start}*/), to(#ddd /*{a-bup-background-end}*/));
	/* Saf4+, Chrome */
	background-image: -webkit-linear-gradient(#ddd /*{a-bup-background-start}*/, #ddd
		/*{a-bup-background-end}*/); /* Chrome 10+, Saf5.1+ */
	background-image: -moz-linear-gradient(#ddd /*{a-bup-background-start}*/, #ddd
		/*{a-bup-background-end}*/); /* FF3.6 */
	background-image: -ms-linear-gradient(#ddd /*{a-bup-background-start}*/, #ddd
		/*{a-bup-background-end}*/); /* IE10 */
	background-image: -o-linear-gradient(#ddd /*{a-bup-background-start}*/, #ddd
		/*{a-bup-background-end}*/); /* Opera 11.10+ */
	background-image: linear-gradient(#ddd /*{a-bup-background-start}*/, #fefefe
		/*{a-bup-background-end}*/);
}

input {
	padding: 5px;
	font-size: 12px;
}

.bottom-tab td {
	width: 33.3333333333333%
}

.bottom-tab td input {
	width: 100%;
	border: 1px solid #fff;
	background: #999;
	color: #fff;
}

.bottom-tab .td input {
	width: 100%;
	border: 1px solid #666;
	background: #fff;
	color: #666;
}
</style>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=D40182b07eafd5fa0dcf52e1c139157f"></script>
<script type="text/javascript"
	src="http://developer.baidu.com/map/jsdemo/demo/convertor.js"></script>
<title>附近的中国银行</title>
</head>
<body>
	<header id="tab">
		<table style="width: 100%">
			<tr>
				<td><div align="right">
						<button onclick="hiddenRoute()" style="width: 80px" id='botton-1'
							class="botton ">地图</button>
					</div></td>
				<td><div align="left">
						<button onclick="hiddenMap()" style="width: 80px" id='botton-2'>路线</button>
					</div></td>
			</tr>
		</table>
	</header>
	<div id="allMap" style="height: 90%"></div>
	<div id="routeDiv"
		style="width: 100%; display: none; text-align: center;">
		<table style="height: 70px; width: 100%; background: #ddd;"
			class="bottom-tab">
			<tr id='tab-3'>
				<td class='td'><input type="button" id="buxing" name="buxing"
					onclick="buxingFunction()" value="步行路线" /></td>
				<td><input type="button" id="gongjiao" name="gongjiao"
					onclick="gongjiaoFunction()" value="最佳公交路线" /></td>
				<td><input type="button" id="zijia" name="zijia"
					onclick="zijiaFunction()" value="最佳自驾路线" /></td>
			</tr>
		</table>
		<div style="height: 90%; font-size: 14px; overflow: scroll;"
			id="r-result"></div>
	</div>
</body>
<script type="text/javascript">
	//微信客户经纬度坐标地点
	var longitude = "${userLongitude}";
	var latitude = "${userLatitude}";
	//设置地图层级
	var zoom=14;
	//微信客户坐标初始化
	var point = new BMap.Point(longitude, latitude);
	//实例化Google坐标参数(因微信返回坐标为Google，故在此转换)
	var ggPoint = new BMap.Point(longitude, latitude);
	//-----------------------------------------------------------
	//目标地址的坐标和初始化坐标地点40.036159704976,116.219294630122
	var targetAddressLongitude = "${longitude}";
	var targetAddressLatitude = "${latitude}";
	var targetAddressPoint = new BMap.Point(targetAddressLongitude,
			targetAddressLatitude);
	//-----------------------------------------------------------
	//查找目标地点名称
	var findAddress = "${name}";
	
	//公交路线查询参数,步行路线查询参数,自驾车查询,目标位置的提示信息,所在位置的圆,定位圆心
	var transit, walking, driving, targetAddressLabel, circle, marker;
	//---------------以上初始化数据完成--------------------------------
	//-----------------------------------------------------------
	//创建百度地图，必须指向一个DIV的ID
	var map = new BMap.Map("allMap");
	//添加当前的经纬度，14（1公里）参数为地图的缩放比例116.419, 39.915
	map.centerAndZoom(point, zoom);
	//百度地图的移动和缩放的控件
	map.addControl(new BMap.NavigationControl());
	//显示地图当前缩放尺寸
	map.addControl(new BMap.ScaleControl());
	//启用滚轮放大缩小
	//map.enableScrollWheelZoom();
	//启用双指操作缩放
	map.enablePinchToZoom();
	//将GOOGLE地图坐标转换为百度坐标
	BMap.Convertor.translate(ggPoint, 2, function(points) {
		//将Google坐标转化为百度坐标并移动到当前所在坐标
		map.panTo(points);
		//设置圆覆盖面，定位自己500M以内的位置
		circle = new BMap.Circle(points);
		point = points;
		//圆心半径代码
		circle.setRadius(500);
		//设置圆的边框透明度
		circle.setStrokeColor(0);
		//设置内圆的颜色
		circle.setFillColor("#3a6bdb");
		//将圆形覆盖物增加到地图中
		map.addOverlay(circle);
		var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png",
				new BMap.Size(23, 25), {
					offset : new BMap.Size(10, 25), // 指定定位位置  
					imageOffset : new BMap.Size(1, -50 - 10 * 25)
				// 设置图片偏移  
				});
		marker = new BMap.Marker(points, {
			icon : myIcon
		});
		// 将标注添加到地图中
		map.addOverlay(marker);
		var mylabel = new BMap.Label("您的位置", {
			offset : new BMap.Size(20, -10)
		});
		marker.setLabel(mylabel); //添加百度label


	});
	BMap.Convertor.translate(targetAddressPoint, 2, function(points) {
		map.panTo(points);
		targetAddressPoint=points;
		var markerk = new BMap.Marker(points);
		map.addOverlay(markerk);
		targetAddressLabel = new BMap.Label("【" + findAddress + "】", {
			offset : new BMap.Size(20, -10)
		});
		markerk.setLabel(targetAddressLabel); //添加百度label
	});
	
	
	//----------------加载页面后的地图呈现完成---------------------------------------------	
	//自驾车路线
	function zijiaFunction() {
		clearAddressInfo();
		//showLoad("zijia");
		targetAddressLabel.setContent("【" + findAddress + "】");
		if(!driving){
		driving = new BMap.DrivingRoute(map, {
			renderOptions : {
				map : map,
				panel: "r-result",
				autoViewport : true
			}
		});
		}
		driving.search(point, targetAddressPoint);
	}
	//公交路线
	function gongjiaoFunction() {
		clearAddressInfo();
		//showLoad("gongjiao");
		if(!transit){
		transit = new BMap.TransitRoute(map, {
			renderOptions : {
				map : map,panel:"r-result",
				autoViewport : true
			},
			policy :
			//BMAP_TRANSIT_POLICY_AVOID_SUBWAYS   
			BMAP_TRANSIT_POLICY_LEAST_TIME //最少时间。
			//BMAP_TRANSIT_POLICY_LEAST_TRANSFER	最少换乘。
			//BMAP_TRANSIT_POLICY_LEAST_WALKING	最少步行。
			//BMAP_TRANSIT_POLICY_AVOID_SUBWAYS	不乘地铁。(自 1.2 新增)
			//onSearchComplete : searchComplete
		});
		}
		transit.search(point, targetAddressPoint);
	}
	//步行路线
	function buxingFunction() {
		clearAddressInfo();
		//showLoad("buxing");
		targetAddressLabel.setContent("【" + findAddress + "】");
		if(!walking){
			walking = new BMap.WalkingRoute(map, {
				renderOptions : {
					map : map,
					panel: "r-result",
					autoViewport : true
				}
			});
		}
		walking.search(point, targetAddressPoint);
	}
	//清除自驾车，公交，步行的路线信息d
	function clearAddressInfo() {
		if (driving)
			driving.clearResults();
		if (transit)
			transit.clearResults();
		if (walking)
			walking.clearResults();
		var resultDiv=$("#r-result");
		resultDiv.text("");
		resultDiv.html("");
	}
	//隐藏路线DIV
	function hiddenRoute()
	{
		var routeObject=$("#routeDiv");
		routeObject.css('display','none');
		var mapObject=$("#allMap");
		mapObject.css('display','');
		var botton = $("#botton-1");
		botton.attr('className',botton.attr("class")+ "botton ");
		var botton1 = $("#botton-2");
		botton1.attr('className',"1");
		map.setZoom(zoom);
		setTimeout(function(){map.panTo(point);}, 100);
	}
	//隐藏地图DIV
	function hiddenMap()
	{
		var mapObject=$("#allMap");
		mapObject.css('display','none');
		var routeObject=$("#routeDiv");
		routeObject.css('display','');
		var botton = $("#botton-2");
		botton.attr('className',botton.attr("class")+ "botton ");
		var botton1 = $("#botton-1");
		botton1.attr('className',"1");
	}
	//显示等待的滚动提醒
	function showLoad(id)
	{
		var resultDiv=$("#"+id);
		resultDiv.attr("disabled", true);
		setTimeout(function(){
			resultDiv.attr("disabled", "");
		}, 30000);
	}
</script>
<script type="text/javascript">
	var tabs = $("#tab-3").find("td");
	for ( var i = 0; i < tabs.length; i++) {
		tabs[i].onclick = function() {
			change(this);
		}
	}
	function change(obj) {
		for ( var i = 0; i < tabs.length; i++)
		{
			if (tabs[i] == obj) {
				tabs[i].attr('className','td');
			}
			else {
				tabs[i].attr('className','');
			}
		}
	}
</script>
</html>