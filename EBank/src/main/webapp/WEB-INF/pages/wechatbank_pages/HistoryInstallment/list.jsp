	<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>中国银行信用卡</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/css/wechatbank/index.css"/>" />
<script type="text/javascript">
	function show(index) {
		$("#"+index).css("display","block");
		$("#show"+index).css("display","none");
	}
	$(window).load(function() {
		var cardSelect = $("#cardNoSelect");
		for ( var i = 0; i < cardSelect.length; i++) {
			if (cardSelect[i].val == "${cardNo}") {
				cardSelect[i].attr("selected",true);
			}
		}
		var listlength=${fn:length(pageList)};
		var isFollowUp=${isFollowUp};
		if(listlength == 10){
			$("#moreButton").css("display","block");
		}else{
			if(isFollowUp == "true") {
				$("#moreButton").css("display","block");
			}
			$("#moreButton").css("display","none");
		}
	});
</script>
<%@include file="../../base_pages/wxReadyFunction.jsp"%>
</head>

<body>
<header>
    <span></span>
    <h2>分期历史查询</h2>
    <span></span>
</header>
	<div class="mg-tb-20">
		<form action="listP.do" method="post" id="submitForm" name="submitForm">
			<div class="topOneB mar-1 topOneBradius">
				<table class="topTwo">
					<tr>
						<td width="40">卡号:</td>
						<td>
						<select name="cardNo" id="cardNoSelect">
							<c:forEach items="${cardListCrypt}" var="item" varStatus="status">
								<c:if test="${cardNo== fn:substring(item,17,66)}">
                                <option selected="selected" value="${fn:substring(item,17,66)}">
                                    <c:out value="${fn:substring(item, 0, 16)}" />
                                </option>
                            </c:if>
                            <c:if test="${cardNo!= fn:substring(item,17,66)}">
                                <option  value="${fn:substring(item,17,66)}">
                                    <c:out value="${fn:substring(item, 0, 16)}" />
                                </option>
                            </c:if>
							</c:forEach>
						</select>
						</td>
					</tr>
				</table>
			</div>
			<div class="topOneB mar-1 bottomOneBradius" >
				<table class="topTwo">
					<tr>
						<td width="70">币种:</td>
						<td>
							<select name="currencyCodeChinese" id="dateSelect">
								<option value="CNY">人民币</option>
							</select>
						</td>
					</tr>
				</table>
			</div>
			<div class="HandIn" style="margin-top: 20px;">
				<input type="submit" value="确定" onclick="return validate_form();" /> 
			</div>
		</form>
	</div>
	<div style="margin-top: 20px; margin-bottom: 10px;">
		<c:if test="${pageList ne null}">
			<c:if test="${fn:length(pageList) eq 0}">
				<div class="topOneB mar-1 allOneBradius">
					<table class="topTwo mb-10">
						<tr>
							<td>
								<span class="black">很抱歉！没有符合的交易记录。详情请咨询中国银行信用卡24小时客服热线4006695566。感谢您对中行信用卡的支持和厚爱！ </span>
							</td>
						</tr>
					</table>
				</div>
			</c:if>
			<c:if test="${fn:length(pageList) gt 0}">
				<c:forEach items="${pageList}" var="item" varStatus="status">
					<div class="topOneB mar-1 topOneBradius">
						<table class="topTwo">
							<tr>
								<td class="td-le">卡号：</td>
								<td align="right"><c:out value="${fn:substring(item.cardNo,0,4)}" />********<c:out value="${fn:substring(item.cardNo,fn:length(item.cardNo)-4,fn:length(item.cardNo))}" /></td>
							</tr>
						</table>
					</div>
					<div class="topOneB mar-1">
						<table class="topTwo">
							<tr>
								<td class="td-le">分期日期：</td>
								<td align="right"><c:out value="${item.instalmentOriginalTransactionDate}" /></td>
							</tr>
						</table>
					</div>

					<div class="topOneB mar-1">
						<table class="topTwo">
							<tr>
								<td class="td-le">分期描述：</td>
								<td align="right"><c:out value="${item.instalmentRuleDescription}" /></td>
							</tr>
						</table>
					</div>
					<div class="topOneB mar-1">
						<table class="topTwo">
							<tr>
								<td class="td-le">分期币种：</td>
								<td align="right"><c:out value="${item.currencyCode}" /></td>
							</tr>
						</table>
					</div>
					<div class="topOneB mar-1">
						<table class="topTwo">
							<tr>
								<td class="td-le">分期金额：</td>
								<td align="right"><c:out value="${item.instalmentOriginalAmount}" /></td>
							</tr>
						</table>
					</div>
					<div class="topOneB mar-1">
						<table class="topTwo">
							<tr>
								<td class="td-le">期数：</td>
								<td align="right"><c:out value="${item.instalmentOriginalNumber}" /></td>
							</tr>
						</table>
					</div>
					<div class="topOneB mar-1">
						<table class="topTwo">
							<tr>
								<td class="td-le">完成日期：</td>
								<td align="right"><c:out value="${item.instalmentCompleteDate}" /></td>
							</tr>
						</table>
					</div>
					<!--修改-->
					<div class="topOneB mar-1 showHide_BOXhide" id="show${status.index}">
						<table class="topTwo">
							<input class="showHide_BOX"  TYPE="BUTTON" VALUE="查看详细" onclick="show(${status.index});"/>
						</table>
					</div>
					<div id="${status.index}" style="display: none">
						<div class="topOneB mar-1">
							<table class="topTwo" style="margin-bottom: 10px;">
								<tr>
									<td>分期手续费收取方式：</td>
									<td align="right"><c:if test="${item.instFeeFlag=='1'}">分期收取</c:if>
										<c:if test="${item.instFeeFlag=='0'}">一次性收取</c:if></td>
								</tr>
							</table>
						</div>
						<div class="topOneB mar-1">
							<table class="topTwo" style="margin-bottom: 10px;">
								<tr>
									<td>首次入帐金额：</td>
									<td align="right"><c:out value="${item.instalmentFirstPostingAmount}"/></td>
								</tr>
							</table>
						</div>
						<div class="topOneB mar-1">
							<table class="topTwo" style="margin-bottom: 10px;">
								<tr>
									<td>下次入帐金额：</td>
									<td align="right"><c:out value="${item.instalmentNextPostingAmount}"/></td>
								</tr>
							</table>
						</div>
						<div class="topOneB mar-1">
							<table class="topTwo" style="margin-bottom: 10px;">
								<tr>
									<td>已入账期数：</td>
									<td align="right"><c:out value="${item.instalmentPostedNumber}"/></td>
								</tr>
							</table>
						</div>
						<div class="topOneB mar-1">
							<table class="topTwo" style="margin-bottom: 10px;">
								<tr>
									<td>已入账金额：</td>
									<td align="right"><c:out value="${item.instalmentReversalAmount}"/></td>
								</tr>
							</table>
						</div>
						<div class="topOneB mar-1">
							<table class="topTwo" style="margin-bottom: 10px;">
								<tr>
									<td>剩余未入账期数：</td>
									<td align="right"><c:out value="${item.instalmentOutstandingNumber}"/></td>
								</tr>
							</table>
						</div>
						<div class="topOneB mar-1">
							<table class="topTwo" style="margin-bottom: 10px;">
								<tr>
									<td>剩余未入账金额：</td>
									<td align="right"><c:out value="${item.instalmentOutstandingAmount}"/></td>
								</tr>
							</table>
						</div>
						<div class="topOneB mar-1 bottomOneBradius">
							<table class="topTwo" style="margin-bottom: 10px;">
								<tr>
									<td>下次入帐日期：</td>
									<td align="right"><c:out value="${item.instalmentNextPostingDate}"/></td>
								</tr>
							</table>
						</div>
					</div>
					<div style="margin-top: 10px;">
					</div>
				</c:forEach>
			</c:if>
		</c:if>
	</div>
	<div id="addViewDiv"></div>
	<div style="margin: 20px 10px;">
		<input type="button" value="查看更多" class="HandInHui" style="width: 100%;display: none;margin: 10px auto;border:1px solid #999" id="moreButton" onclick="getMore();" />
		<input id="isFollowUp" name="isFollowUp" value="${isFollowUp}" type="hidden"/>
		<input id="nextGCSStartIndex" name="nextGCSStartIndex" value="${nextGCSStartIndex}" type="hidden"/>
	</div>
	<div id="sending"
		style="position: absolute; position: fixed; height: 100%; background: #fff; width: 100%; top: 0; left: 0; visibility: hidden; filter: alpha(opacity =  50); opacity: 0.50;">
		<img src="<c:url value="/images/wechatbank/5-121204193R5-50.gif"/>"
			style="position: absolute; top: 50%; left: 50%; margin-left: -16px; margin-top: -16px;" />
	</div>
	<script type="text/javascript">
		var isClicked = false;
		var page = 0;
		function validate_form() {
			if (isClicked == false) {
				var sending = $("#sending");
				sending.css("visibility", "visible");
				isClicked = true;
				return true;
			} else {
				return false;
			}
		}
		function getMore() {
			page++;
			if(isClicked==false){
				var sending = $("#sending");
				var isFollowUp = $("#isFollowUp").val();
				var nextGCSStartIndex = $("#nextGCSStartIndex").val();
				sending.css("visibility", "visible");
				isClicked = true;
				$.ajax({
					url: "ajax_getMore.do",
					data: {
						cardNo: ${cardNo},
						isFollowUp: isFollowUp,
						nextGCSStartIndex: nextGCSStartIndex,
						timestamp: new Date().getTime()
					},
					type: "post",
					dataType: "text",
					async: false,
					success: function (result) {
						if (result == null || result == "null") {
							$("#moreButton").css("display","none");
							$("#isFollowUp").val("false");
						} else if (result == "exception") {
							window.location.href = "../error.html";
						} else {
							var list = eval("(" + result + ")");
							if(list.length>10){
								$("#moreButton").css("display","none");
								$("#isFollowUp").val("false");
							}else{
								$("#nextGCSStartIndex").val(nextGCSStartIndex+10);
							}
							if (list.length > 0 && list[0].instalmentOriginalAmount != "") {
								for (var i = 0; i < list.length; i++) {
									var cardNo = list[i].cardNo;
									var cardNoLength = cardNo.length;
									cardNo = cardNo.substring(0, 4) + "********" + cardNo.substring(cardNoLength - 4, cardNoLength);
									//增加HTML对象，并附加到容器最后
									var newDiv = "<div class='topOneB mar-1 topOneBradius'>" +
											"<table class='topTwo'>" +
											"<tr>" +
											"<td class='td-le'>交易日期：</td>" +
											"<td align='right'>" + list[i].instalmentOriginalTransactionDate + "</td>" +
											"</tr>" +
											"</table>" +
											"</div>" +
											"<div class='topOneB mar-1'>" +
											"<table class='topTwo'>" +
											"<tr>" +
											"<td class='td-le'>卡号：</td>" +
											"<td align='right'>" + cardNo + "</td>" +
											"</tr>" +
											"</table>" +
											"</div>" +
											"<div class='topOneB mar-1'>" +
											"<table class='topTwo'>" +
											"<tr>" +
											"<td class='td-le'>分期描述：</td>" +
											"<td align='right'>" + list[i].instalmentRuleDescription + "</td>" +
											"</tr>" +
											"</table>" +
											"</div>" +
											"<div class='topOneB mar-1'>" +
											"<table class='topTwo'>" +
											"<tr>" +
											"<td class='td-le'>分期币种：</td>" +
											"<td align='right'>" + list[i].currencyCode + "</td>" +
											"</tr>" +
											"</table>" +
											"</div>" +
											"<div class='topOneB mar-1'>" +
											"<table class='topTwo'>" +
											"<tr>" +
											"<td class='td-le'>分期金额：</td>" +
											"<td align='right'>" + list[i].instalmentOriginalAmount + "</td>" +
											"</tr>" +
											"</table>" +
											"</div>" +
											"<div class='topOneB mar-1'>" +
											"<table class='topTwo'>" +
											"<tr>" +
											"<td class='td-le'>期数：</td>" +
											"<td align='right'>" + list[i].instalmentOriginalNumber + "</td>" +
											"</tr>" +
											"</table>" +
											"</div>" +
											"<div class='topOneB mar-1'>" +
											"<table class='topTwo'>" +
											"<tr>" +
											"<td class='td-le'>完成日期：</td>" +
											"<td align='right'>" + list[i].instalmentCompleteDate + "</td>" +
											"</tr>" +
											"</table>" +
											"</div>" +
											"<div class='topOneB mar-1 showHide_BOXhide' id='show"+(page*10+i)+"'>"+
											"<table class='topTwo'>"+
											"<input class='showHide_BOX'  TYPE='BUTTON' VALUE='查看详细' onclick='show("+(page*10+i)+");'/>"+
											"</table>"+
											"</div>"+
											"<div id='"+(page*10+i)+"' style='display: none'>"+
											"<div class='topOneB mar-1'>" +
											"<table class='topTwo'>" +
											"<tr>" +
											"<td class='td-le'>分期手续费收取方式：</td>" +
											"<td align='right'><c:if test='${list[i].instFeeFlag==1}'>分期收取</c:if>"+
											"<c:if test='${list[i].instFeeFlag==0}'>一次性收取</c:if></td>"+
											"</tr>" +
											"</table>" +
											"</div>" +
											"<div class='topOneB mar-1'>" +
											"<table class='topTwo'>" +
											"<tr>" +
											"<td class='td-le'>首次入帐金额：</td>" +
											"<td align='right'>" + list[i].instalmentFirstPostingAmount + "</td>" +
											"</tr>" +
											"</table>" +
											"</div>" +
											"<div class='topOneB mar-1'>" +
											"<table class='topTwo'>" +
											"<tr>" +
											"<td class='td-le'>下次入帐金额：</td>" +
											"<td align='right'>" + list[i].instalmentNextPostingAmount + "</td>" +
											"</tr>" +
											"</table>" +
											"</div>" +
											"<div class='topOneB mar-1'>" +
											"<table class='topTwo'>" +
											"<tr>" +
											"<td class='td-le'>已入账期数：</td>" +
											"<td align='right'>" + list[i].instalmentPostedNumber + "</td>" +
											"</tr>" +
											"</table>" +
											"</div>" +
											"<div class='topOneB mar-1'>" +
											"<table class='topTwo'>" +
											"<tr>" +
											"<td class='td-le'>已入账金额：</td>" +
											"<td align='right'>" + list[i].instalmentReversalAmount + "</td>" +
											"</tr>" +
											"</table>" +
											"</div>" +
											"<div class='topOneB mar-1'>" +
											"<table class='topTwo'>" +
											"<tr>" +
											"<td class='td-le'>剩余未入账期数：</td>" +
											"<td align='right'>" + list[i].instalmentOutstandingNumber + "</td>" +
											"</tr>" +
											"</table>" +
											"</div>" +
											"<div class='topOneB mar-1'>" +
											"<table class='topTwo'>" +
											"<tr>" +
											"<td class='td-le'>剩余未入账金额：</td>" +
											"<td align='right'>" + list[i].instalmentOutstandingAmount + "</td>" +
											"</tr>" +
											"</table>" +
											"</div>" +
											"<div class='topOneB mar-1'>" +
											"<table class='topTwo'>" +
											"<tr>" +
											"<td class='td-le'>下次入帐日期：</td>" +
											"<td align='right'>" + list[i].instalmentNextPostingDate + "</td>" +
											"</tr>" +
											"</table>" +
											"</div>" +
											"</div>"+
									"<div style='margin-top: 10px;'></div>";
									//$("#addViewDiv").insertAdjacentHTML("beforeEnd",newDiv);
									document.getElementById("addViewDiv").insertAdjacentHTML(
											"beforeEnd", newDiv);
								}
								if (list.length == 10) {
									$("#moreButton").css("display","block");
								} else {
									$("#moreButton").css("display","none");
								}
							} else {
								$("#moreButton").css("display","none");
							}
						}
						sending.css("visibility","hidden");
						isClicked = false;
					}
				});
			}
		}
	</script>
</body>
</html>