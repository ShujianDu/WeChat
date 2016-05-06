<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../../base_pages/base.jsp" %>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name=" format-detection" content="telephone=no"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
    <title>中国银行信用卡</title>
    <link href="<c:url value="/css/indexLogin.css"/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/css/DefaultStyle.css"/>" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
    </script>
</head>

<div class="container">
    <img src="../images/logoda.png" class="logo">
    <form action="list.do" method="post" id="submitForm">
        <ul>
            <li class="input_box">
                <span class="input_name">证件类型</span>
                <select id="identityType" name="identityType" class="control_card">
                    <option value="">请选择</option>
                    <option value="01"
                            <c:if test="${'01' eq model.identityType}">selected</c:if>>居民身份证
                    </option>
                    <option value="03"
                            <c:if test="${'03' eq model.identityType}">selected</c:if>>护照
                    </option>
                    <option value="05"
                            <c:if test="${'05' eq model.identityType}">selected</c:if>>军人身份证
                    </option>
                    <option value="06"
                            <c:if test="${'06' eq model.identityType}">selected</c:if>>武装警察身份证
                    </option>
                    <option value="04"
                            <c:if test="${'04' eq model.identityType}">selected</c:if>>户口簿
                    </option>
                    <option value="47"
                            <c:if test="${'47' eq model.identityType}">selected</c:if>>港澳居民来往内地通行证（香港）
                    </option>
                    <option value="48"
                            <c:if test="${'48' eq model.identityType}">selected</c:if>>港澳居民来往内地通行证（澳门）
                    </option>
                    <option value="49"
                            <c:if test="${'49' eq model.identityType}">selected</c:if>>台湾居民往来大陆通行证
                    </option>
                    <option value="08"
                            <c:if test="${'08' eq model.identityType}">selected</c:if>>外交人员身份证
                    </option>
                    <option value="09"
                            <c:if test="${'09' eq model.identityType}">selected</c:if>>外国人居留许可证
                    </option>
                    <option value="10"
                            <c:if test="${'10' eq model.identityType}">selected</c:if>>边民出入境通行证
                    </option>
                    <option value="11"
                            <c:if test="${'11' eq model.identityType}">selected</c:if>>其他
                    </option>
                </select>
            </li>
            <li class="input_box">
                <span class="input_name">证件号码</span>
                <input class="input_content" placeholder="请输入证件号码" type="text"
                       name="username" id="username"
                       maxlength="18" onchange="changeWarning();"
                       value="${username}"/>
            </li>
            <li class="input_box">
                <span class="input_name">查询密码</span>
                <input class="input_content" placeholder="请输入查询密码" type="password"
                       name="password" id="password"
                       maxlength="6" onchange="changeWarning();"/>
            </li>
            <li class="input_box">
                <span class="input_name">附加验证码</span>
                <input class="input_content" type="text" placeholder="请输入附加验证码" maxlength="4"
                       name="verification" id="verificationCode"
                       onchange="changeWarning();"/>
                <span class="yama"><img src="../showradomcode.jsp"/></span>
            </li>
            <li class="input_box">
                <span class="input_name">短信验证码</span>
                <input class="input_content" type="text" placeholder="请输入手机验证码"
                       id="mobileCode" name="mobileCode"/>
                <input type="button" class="input_yama" name="getMobileCodeButton"
                       id="getMobileCodeButton" value="获取验证码" onclick="getMobileCodeFunction()">
            </li>
            </ul>
            <div style="min-height:20px;padding-top: 2px">
                <c:if test="${null ne message}">
                    <span id="bindWarning" class="help-block fontsize text-center fontred">${message}</span>
                </c:if>
                <span id="spanWarning" class="help-block fontsize text-center fontred"></span>
            </div>
            <div class="HandIn">
                <input  type="submit" value="登&nbsp;&nbsp;录" id="loginBtn" onclick="return validate_form();"/>
            </div>
    </form>
</div>
<div style="margin-top: 30px;"></div>
<div id="sending"
     style="position: absolute; position: fixed; height: 100%; background: #fff; width: 100%; top: 0; left: 0; visibility: hidden; filter: alpha(opacity = 50); opacity: 0.50;">
    <img src="<c:url value="/images/wechatbank/5-121204193R5-50.gif"/>"
         style="position: absolute; top: 50%; left: 50%; margin-left: -16px; margin-top: -16px;"/>
</div>
<script type="text/javascript">
    var isClicked = false;
    function validate_form() {
        if (isClicked == false) {
            var identityType = $("#identityType").val();
            var idNumber = $("#username").val();
            var passwordQuery = $("#password").val();
            var verificationCode = $("#verificationCode").val();
            var mobileCode = $("#mobileCode").val();
            var spanWarning = $("#spanWarning");
            if (identityType == null || identityType == "") {
                spanWarning.text("证件类型不能为空，请选择！");
                return false;
            }
            if (idNumber == null || idNumber == "") {
                spanWarning.text("证件号不能为空，请输入！");
                return false;
            }
            if (passwordQuery == null || passwordQuery == "") {
                spanWarning.text("查询密码不能为空，请输入！");
                return false;
            }
            var mobileCodeValue = mobileCode.replace(/\s/gi, '');
            if (mobileCodeValue == null || mobileCodeValue == "") {
                spanWarning.text("手机验证码不能为空，请输入！");
                return false;
            }
            if (verificationCode == null
                    || verificationCode == "") {
                spanWarning.text("验证码不能为空，请输入！");
                return false;
            }
            var pswReg = /(^\d{6}$)/;
            if (pswReg.test(passwordQuery()) == false) {
                spanWarning.text("查询密码格式不正确，请重新输入！");
                return false;
            }
            var sending = $("#sending");
            sending.css("visibility", "visible");
            isClicked = true;
            return true;
        } else {
            return false;
        }

    }

    function changeWarning() {
        $("#spanWarning").text("");
        $("#bindWarning").css("display", "none");
    }

    function getMobileCodeFunction() {
        var idType = $("#identityType").val();
        var identityNo = $("#username").val();
        var verificationCode = $("#verificationCode").val();
        var spanWarning = $("#spanWarning");
        if (idType == null || idType == "") {
            spanWarning.text("请选择证件类型");
            return false;
        }
        if (identityNo == null || identityNo == "") {
            spanWarning.text("请输入证件号码");
            return false;
        }

        if (verificationCode == null || verificationCode == "") {
            spanWarning.text("附加验证码不能为空，请输入！");
            return false;
        }
        failureSendSMS = "";
        buttonTimeOut();
        $.ajax({
            url: "getSMSCode_ajax.do",
            data: {
                idType: idType,
                identityNo: identityNo,
                verificationCode: encodeURI(verificationCode),
                timestamp: new Date().getTime()
            },
            type: "post",
            dataType: "text",
            async: true,
            success: function (result) {
                if (result != null && result != "") {
                    var resultIndex = result.indexOf(",")
                    if (resultIndex != -1) {
                        result = result.substring(0, resultIndex);
                    }
                    if (result == "exception" || result == "false") {
                        failureSendSMS = "false";
                        i = 0;
                        spanWarning.text("证件号错误，请核对证件号！");
                    } else if (result == "locked") {
                        window.location.href = "./locked.do";
                    } else if (result == "errorCode") {
                        spanWarning.text("您填写的验证码有误，请重新输入！");
                    } else {
                        buttonTimeOut();
                    }
                }
            }
        });
    }

    var i = 60;
    var failureSendSMS = "";
    function buttonTimeOut() {
        var msgCodeButton = $("#getMobileCodeButton");
        i--;
        if (i <= 0) {
            i = 60;
            if (failureSendSMS == "") {
                msgCodeButton.attr("disabled", false);
                msgCodeButton.val("重新获取")
            }
        } else {
            msgCodeButton.attr("disabled", true);
            msgCodeButton.val(i + "秒");
            setTimeout("buttonTimeOut()", 1000);
        }
    }

    var j = 30;
    function disabledButton() {
        var smsCodeButton = $("#getMobileCodeButton");
        j--;
        if (j <= 0) {
            j = 30;
            smsCodeButton.attr("disabled", false);
            smsCodeButton.val("获取验证码");
        } else {
            smsCodeButton.attr("disabled", true);
            smsCodeButton.val(j + "秒")
            setTimeout("disabledButton()", 1000);
        }
    }
</script>
</body>
</html>
