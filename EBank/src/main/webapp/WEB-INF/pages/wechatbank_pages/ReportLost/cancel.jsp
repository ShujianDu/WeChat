<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../../base_pages/base.jsp" %>
    <title>中国银行信用卡</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/wechatbank/index.css"/>"/>
</head>
<body>
<header id="top">
    <span></span>
    <h2>解除临时挂失</h2>
    <span></span>
</header>
<div style="margin-top: 10px;">
    <form action="doCancel.do" method="post" id="submitForm">
        <div class="topOneB mar-1 allOneBradius">
            <table class="topTwo" style="margin-bottom: -10px;">
                <tr>
                    <td class="td-le">*卡号：</td>
                    <td>
                        <select name="cardNo" id="cardNo">
                            <option value="">--请选择解挂失卡--</option>
                            <c:forEach items="${cardList}" var="item">
                                <option value="${fn:substringAfter(item, ',')}">${fn:substring(item, 0, 16)}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan='2'>
                        <span id="cardNoWarning"
                              style="color: red; font-size: 12px; padding-bottom:10px;display:block;"></span></td>
                    <td></td>

                </tr>
            </table>

        </div>
        <div class="topOneB mar-1 allOneBradius" style="margin-bottom: 10px;  margin-top:20px;">
            <table class="topTwo" style="margin-bottom: 10px;">
                <tr>
                    <td>
                        温馨提示：临时挂失解除后，您的信用卡将恢复原有功能，请谨慎操作。
                    </td>
                <tr/>
            </table>
        </div>
        <div class="HandIn" style="margin-top: 10px; margin-bottom: 10px;">
            <input type="submit" value="确认" onclick="return check()"/>
            <input type="hidden" id="openId" name="openId" value="${openId}">
        </div>
    </form>

</div>

</body>
</html>
<script type="text/javascript">
    function check() {
        document.getElementById("cardNoWarning").innerHTML = "";
        var cardNo = document.getElementById("cardNo").value;
        if (cardNo == "") {
            document.getElementById("cardNoWarning").innerHTML = "请选择解挂失卡号";
            return false;
        }
        return true;
    }
</script>