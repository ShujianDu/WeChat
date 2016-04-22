<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <%@include file="../../base_pages/base.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <link href="<c:url value="/css/myPoint.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/css/DefaultStyle.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/css/BlackTip.css"/>" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<c:url value="/js/myPoint.js"/>"></script>
    <title>我的积分</title>
</head>
<script type="text/javascript">
    function pointsDetail() {
        window.location.href = "../points/pointsDetail.do";
    }
</script>

<body>
<div class="IntegrationContainer">
    <c:if test="${pointsBalance eq null}">
        <div id="Integration_box">
            <div class="myPoint_box">
                <div class="myPoint_view">
                    <img src="../images/points/no.png" class="myPointImg"/>
                    <span class="myPointNull">很抱歉！没有查询到您的信用卡积分。详情请咨询中国银行信用卡24小时客服热线4006695566。感谢您对中行信用卡的支持和厚爱！</span>

                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${pointsBalance ne null}">
        <div id="Integration_box">
            <div class="myPoint_box">
                <div class="myPoint_view">
                    <span class="pointTitle">当前账户的有效积分</span>
                    <!--显示积分-->
                    <span class="help_box"><img src="../images/points/help2.png" class="help"></span>
                    <div class="pointValueBox"><span class="pointValue">${pointsBalance.totalPoint}</span>分</div>
                    <input type="button" class="check_dt" id="check_dt1" onclick="pointsDetail();"/>
                </div>
                <input class="IntegrateDate" type="button" value="积分兑换" onclick="">
            </div>
            <!--black_box-->
            <div class="black_box">
                <div class="tip_box">
                    <span class="tip_close"></span>
                    <span class="tip_title">提示</span>
                    <span class="tip_content">您可通过中国银行网站www.boc.cn、或通过电话等方式将您使用信用卡所累计的积分兑换为礼品</span>
                    <span class="tip_close2">我知道了</span>
                </div>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>