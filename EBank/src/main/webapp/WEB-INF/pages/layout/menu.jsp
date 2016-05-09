<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <%@ include file="../base_pages/base.jsp" %>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/myMenu.css"/>"/>
    <title>我的额度</title>
</head>
<body>
<input type="hidden" id="currentMenuId" value="${sessionScope.menuId}">
<!--menu-->
<div class="menu_box">
    <a id="1" href="${ctx}/billingsummary/list.do" class="current_menu">我的账单</a>
    <a id="2" href="${ctx}/balance/list.do">我的额度</a>
    <a id="3" href="${ctx}/points/list.do">我的积分</a>
    <a id="4" href="${ctx}/menu/moreMenu.do?menuId=4">更多功能</a>
</div>
<div id="myMenuContent"></div>
<script type=text/javascript>
    $(document).ready(function () {
        var currentId = $("#currentMenuId").val();
        if (currentId == null || currentId == "") {
            currentId = 4;
        }
        $(".menu_box a").removeClass("current_menu");
        $("#" + currentId + "").addClass("current_menu");
    });
</script>
</body>
</html>