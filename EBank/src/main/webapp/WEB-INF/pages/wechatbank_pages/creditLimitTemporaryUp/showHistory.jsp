<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!doctype html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name=" format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <title>中国银行信用卡</title>
    <%@include file="../../base_pages/base.jsp" %>
    <script src="<c:url value="/js/UpgradeQuery.js"/>" type="text/javascript"></script>
    <link href="<c:url value="/css/UpgradeQuery.css"/>" rel="stylesheet" type="text/css">
</head>

<body>
<!--UpgradeQuery-->
<div class="UpgradeQuery_content">
    <div class="UpgradeQuery_title">提升额度</div>
    <!--第一种卡信息-->
    <c:forEach items="${limitUpHistorys}" var="item" varStatus="status">
        <div class="dynamic_box">
            <div class="productName"><span class="name">信用卡号</span><span class="dynamicData"
                    >${fn:substring(cardNo, 0, 4)}********${fn:substring(cardNo, 12, 16)}</span>
            </div>
            <div class="productName"><span class="name">生效日期</span><span class="dynamicData"
                    >${item.eosStarLimitDate}</span>
            </div>
            <div class="productName"><span class="name">失效日期</span><span class="dynamicData"
                    >${item.eosEndLimitDate}</span>
            </div>
            <div class="productName"><span class="name">临时增额</span><span class="dynamicData"
                    >${item.eosLimit}元</span>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
