<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name=" format-detection" content= "telephone=no" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
	<link href="css/adjustLine.css" rel="stylesheet" type="text/css">
	<script src="js/jquery-2.2.3.min.js" type="text/javascript"></script>
	<script src="js/adjustLine.js" type="text/javascript"></script>
	<script >
		$(function(){

			$(".UpgradeProgress").click(function(){

				$(".wait_box").fadeIn(500);
				setTimeout(function(){
					$(".adjustLine_box").load('UpgradeQuery.html');
				},0);
				setTimeout(function(){
					$(".wait_box").fadeOut(500);
				},2000);
			});
			$(".ApplyAmount").click(function(){
				$(".wait_box").fadeIn(500);
				setTimeout(function(){
					$(".adjustLine_box").load('UpgradeLine.html');
				},0);
				setTimeout(function(){
					$(".wait_box").fadeOut(500);
				},2000);
			});
		});
		function showHistory() {
			document.getElementById("submitForm").action = "showHistory.do";
			document.getElementById("submitForm").submit();
		}
	</script>
	<title>调整额度</title>


</head>

<body>
<!--adjustLine-->
<div class="adjustLine_box">
	<div class="adjustLine_content">
		<div class="adjustLine_title">调整额度</div>
		<form action="show.do" method="post" name="submitForm" id="submitForm">
			<select class="control_card" name="cardNo" id="cardNoSelect">
				<c:forEach items="${cardList}" var="item" varStatus="status">
					<option class="" value="${fn:substringAfter(item, ',')}"
							name="cardNo">
						<c:out value="${fn:substring(item, 0, 16)}" />
					</option>
				</c:forEach>
			</select>
		</form>
		<input type="submit" value="申请提升临时额度" class="ApplyAmount"/>
		<input type="button" value="查询额度提升进度" class="UpgradeProgress" onclick="showHistory();"/>
	</div>
</div>
</body>



</html>
