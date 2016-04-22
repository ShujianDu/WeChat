<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../../base_pages/base.jsp" %>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/wechatbank/index.css"/>"/>
    <title>中国银行信用卡</title>
</head>

<body>
<header>
    <span></span>
    <h2>信用卡办理进度</h2>
    <span></span>
</header>
<div class="mg-tb-30">
    <c:if test="${cardApplyList.cardApplies eq null}">
        <div class="topOneB mar-1 allOneBradius">
            <table class="topTwo mb-10">
                <tr>
                    <td>
                        <span class="">很抱歉！没有查询到您的申请进度。详情请咨询中国银行信用卡24小时客服热线4006695566。感谢您对中行信用卡的支持和厚爱！</span>
                    </td>
                </tr>
            </table>
        </div>
    </c:if>
    <c:if test="${fn:length(cardApplyList.cardApplies) gt 0}">
        <c:forEach items="${cardApplyList.cardApplies}" var="item" varStatus="status">
            <div class="topOneB mar-1 topOneBradius bcS">
                <table class="topTwo">
                    <tr>
                        <td class="td-le">申请编号:</td>
                        <td align="right"><c:out value="${item.applyNo}"/></td>
                    </tr>
                </table>
            </div>
            <div class="topOneB mar-1">
                <table class="topTwo">
                    <tr>
                        <td class="td-le">卡产品:</td>
                        <td align="right"><c:out value="${item.pdnDes}"/></td>
                    </tr>
                </table>
            </div>
            <div class="topOneB mar-1">
                <table class="topTwo">
                    <tr>
                        <td class="td-le">通过日期:</td>
                        <td align="right"><c:out value="${item.passDate}"/></td>
                    </tr>
                </table>
            </div>
            <div class="topOneB mar-1 bottomOneBradius">
                <table class="topTwo">
                    <tr>
                        <td class="td-le">阶段描述:</td>
                        <td align="right"><c:out value="${item.phaseDesc}"/></td>
                    </tr>
                </table>
            </div>
            <div style="margin-top: 10px;">
            </div>
        </c:forEach>
    </c:if>
    <c:if test="${cardApplyList.hasNext eq false}">
        <div style="border:1px solid #000;">
            <table class="topTwo">
                <tr>
                    <td style="font-size:14px;text-align:center; padding-top:3px;">最后一页</td>
                </tr>
            </table>
        </div>
    </c:if>
    <c:if test="${cardApplyList.hasNext eq true}">
        <form action="listP.do" method="post" id="submitForm">
            <div style="margin-top: 10px;">
                <table class="topTwo">
                    <tr>
                        <td><input type="submit" class="HandInHui" value="下一页" onclick="showSending()"/></td>
                    </tr>
                </table>
            </div>
            <input type="hidden" id="name" name="name" value="${model.name}">
            <input type="hidden" id="identityType" name="identityType" value="${model.identityType}">
            <input type="hidden" id="identityNo" name="identityNo" value="${model.identityNo}">
            <input type="hidden" id="currPage" name="currPage" value="${currPage}">
        </form>
    </c:if>
</div>
<div id="sending"
     style="position: absolute; position: fixed; height: 100%; background: #fff; width: 100%; top: 0; left: 0; visibility: hidden; filter: alpha(opacity = 50); opacity: 0.50;">
    <img src="<c:url value="/images/wechatbank/5-121204193R5-50.gif"/>"
         style="position: absolute; top: 50%; left: 50%; margin-left: -16px; margin-top: -16px;"/>
</div>
<script type="text/javascript">
    function showSending() {
        $("#sending").css("visibility", "visible");
    }
</script>
</body>
</html>
