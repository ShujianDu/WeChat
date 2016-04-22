<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../../base_pages/base.jsp" %>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/wechatbank/index.css"/>"/>
    <title>中国银行信用卡</title>
</head>
<body>
<header id="top">
    <span></span>
    <h2>信用卡挂失</h2>
    <span></span>
</header>
<div>
    <form action="" method="post" id="submitForm">
        <div class="topOneB mar-1 topOneBradius">
            <table class="topTwo">
                <tr>
                    <td class="td-le">卡号：</td>
                    <td>
                        <select name="cardNo" id="cardNoSelect">
                            <option value="">--请选择挂失卡--</option>
                            <c:forEach items="${cardList}" var="item" varStatus="status">
                                <option value="${fn:substringAfter(item, ',')}" name="cardNo">
                                    <c:out value="${fn:substring(item, 0, 16)}"/>
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
            </table>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span id="cardNoWarning" style="color: red; font-size: 12px;"></span>
            <table class="topTwo">
                <tr>
                    <td class="td-le">手机号：</td>
                    <td>
                        <input type="text" name="mobileNo" id="mobileNo" maxlength="11" onchange="changeWarning();"/>
                    </td>
                </tr>
            </table>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span id="mobileNoWarning" style="color: red; font-size: 12px;"></span>
            <table class="topTwo" style="margin-bottom: -10px;">
                <tr>
                    <td class="td-le" style="width:100px;">*短信验证码：</td>
                    <td style="padding-left:5px; padding-right:0;">
                        <input type="text" name="msgCode" id="code" maxlength="6" onchange="changeWarning();"/>
                    </td>
                    <td style="width:100px;padding-left:5px;">
                        <input type="button" value="获取验证码" class="HandInHui" id="msgCodeButton"
                               onclick="sendMessage();"/>
                    </td>
                </tr>
            </table>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span id="codeWarning" style="color: red; font-size: 12px;"></span>
        </div>
        <div class="topOneB  ">
            <table class="topTwo">
                <tr>
                    <td colspan="2">挂失类型:</td>
                    <td></td>
                </tr>
                <tr style="height:30px; overflow:hidden;">
                    <td style="width:40px; padding-top:0;">
                        <input type="radio" id="reportType_1" name="reportType" value="1"/></td>
                    <td style=" padding-top:0;">临时挂失</td>
                </tr>
                <tr style="height:30px; overflow:hidden;">
                    <td style="width:40px; padding-top:0;">
                        <input type="radio" id="reportType_2" name="reportType" value="2"/></td>
                    <td style=" padding-top:0;">永久挂失</td>
                </tr>
            </table>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span id="typeNoWarning" style="color: red; font-size: 12px;"></span>
        </div>
        <input type="hidden" id="openId" name="openId" value="${openId}"/> </br>
        <div class="HandIn">
            <input type="button" value="确认" onclick="submitTemp();"/>
        </div>
    </form>
</div>
<div class="topOneB mar-1 allOneBradius" style="margin-top: 10px">
    <table class="topTwo" style="margin-bottom: 10px;">
        <tr>
            <td>
                温馨提示：临时挂失后可以申请解除挂失；永久挂失后无法解除挂失，您可以拨打客服热线4006695566申请卡片补办。
            </td>
        <tr/>
    </table>
</div>
<script type="text/javascript">
    var form = $("#submitForm");
    var can = false;
    var cardNoWarning = $("#cardNoWarning");
    var typeNoWarning = $("#typeNoWarning");
    var mobileNoWarning = $("#mobileNoWarning");
    var codeWarning = $("#codeWarning");

    function submitTemp() {
        cardNoWarning.text("");
        typeNoWarning.text("");
        mobileNoWarning.text("");
        codeWarning.text("");
        if ($("#cardNoSelect").val() == "") {
            cardNoWarning.text("请选择挂失卡");
            return;
        }
        if ($("#mobileNo").val() == "") {
            mobileNoWarning.text("请输入手机号");
            return;
        }
        if ($("#code").val() == "") {
            codeWarning.text("请输入短信验证码");
            return;
        }
        if (!$("#reportType_1").is(':checked') && !$("#reportType_2").is(':checked')) {
            typeNoWarning.text("请选择挂失类型");
            return;
        }
        checkMsgCode();
        if (!can) {
            codeWarning.text("验证码错误，请重新输入！");
            return;
        }
        form.attr("action", "reprotlost.do");
        form.submit();
    }

    //发送手机验证码
    function sendMessage() {
        cardNoWarning.text("");
        mobileNoWarning.text("");
        var mobileNo = $("#mobileNo").val();
        if ($("#cardNoSelect").val() == "") {
            cardNoWarning.text("请选择挂失卡");
            return;
        }
        if (mobileNo == null || mobileNo == "") {
            mobileNoWarning.text("*手机号不能为空，请输入！");
            return false;
        }
        $("#msgCodeButton").attr("disabled", true);
        $.ajax({
            url: "getMsgCode_ajax.do",
            data: {
                mobileNo: mobileNo,
                verificationCode: encodeURI('verificationCode'),
                timestamp: new Date().getTime()
            },
            type: "post",
            dataType: "text",
            async: true,
            success: function (result) {
                $("#msgCodeButton").removeAttr("disabled");
                if (result != null && result != "") {
                    if (result == "false") {
                        mobileNoWarning.text("短信验证码发送失败，请稍候再试！");
                    } else {
                        buttonTimeOut();
                    }
                }
            }
        });
    }

    //验证短信验证码是否正确
    function checkMsgCode() {
        var code = $("#code").val();
        var mobileNo = $("#mobileNo").val();
        $.ajax({
            url: "checkMsgCode_ajax.do",
            data: {
                mobileNo: mobileNo,
                code: code,
                timestamp: new Date().getTime()
            },
            type: "post",
            dataType: "text",
            async: false,
            success: function (result) {
                if (result != null && result != "") {
                    if (result == "true") {
                        can = true;
                    } else {
                        can = false;
                    }
                }
            }
        });
    }

    //清除错误警告
    function changeWarning() {
        mobileNoWarning.text("");
        codeWarning.text("");
    }

    //获取验证码时间限制
    var i = 60;
    function buttonTimeOut() {
        var msgCodeButton = $("#msgCodeButton");
        i--;
        if (i == 0) {
            i = 60;
            msgCodeButton.removeAttr("disabled");
            msgCodeButton.val("获取验证码");
        } else {
            msgCodeButton.attr("disabled", true);
            msgCodeButton.val(i + "秒");
            setTimeout("buttonTimeOut()", 1000);
        }
    }

</script>
</body>
</html>