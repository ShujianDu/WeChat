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
    <form action="listP.do" method="post" id="submitForm">
        <div class="topOneB mar-1 topOneBradius">
            <table class="topTwo" style="margin-bottom: -10px;">
                <tr>
                    <td class="td-le">姓名：</td>
                    <td>
                        <input type="text" name="name" id="name" maxlength="25" onchange="changeWarning();" value=""/>
                    </td>
                </tr>
            </table>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span id="nameWarning" style="color: red; font-size: 12px;"></span>
        </div>
        <div class="topOneB mar-1">
            <table class="topTwo" style="margin-bottom: -10px;">
                <tr>
                    <td class="td-le">证件类型：</td>
                    <td>
                        <select name="identityType" id="identityType" onchange="changeWarning();">
                            <option value="">--请选择证件类型--</option>
                            <option value="1">身份证</option>
                            <option value="2">护照</option>
                            <option value="3">军人身份证</option>
                            <option value="4">香港居民来往内地通行证</option>
                            <option value="5">台湾居民往来大陆通行证</option>
                            <option value="I">武装警察身份证</option>
                            <option value="J">澳门居民来往内地通行证</option>
                            <option value="8">其它</option>
                        </select>
                    </td>
                </tr>
            </table>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span id="typeWarning" style="color: red; font-size: 12px;"></span>
        </div>
        <div class="topOneB mar-1">
            <table class="topTwo" style="margin-bottom: -10px;">
                <tr>
                    <td class="td-le">证件号：</td>
                    <td>
                        <input type="text" name="identityNo" id="identityNo" maxlength="18" onchange="changeWarning();"
                               value="${model.identityNo}"/>
                    </td>
                </tr>
            </table>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span id="idNumWarning" style="color: red; font-size: 12px;"></span>
        </div>

        <div class="topOneB mar-1">
            <table class="topTwo" style="margin-bottom: -10px;">
                <tr>
                    <td class="td-le">手机号：</td>
                    <td>
                        <input type="text" name="mobileNo" id="mobileNo" maxlength="11" onchange="changeWarning();"/>
                    </td>
                </tr>
            </table>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span id="mobileNoWarning" style="color: red; font-size: 12px;"></span>
        </div>
        <div class="topOneB mar-1">
            <table class="topTwo">
                <tr>
                    <td class="td-le">验证码：</td>
                    <td>
                        <table width="100%">
                            <tr>
                                <td class="paddingLeft_0">
                                    <input type="text" maxlength="4" name="verificationCode" id="verificationCode"
                                           onchange="changeWarning();"/>
                                </td>
                                <td width="70"><img src="../showradomcode.jsp"/></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span id="verificationCodeWarning" style="color: red; font-size: 12px;"></span>
        </div>

        <div class="topOneB mar-1 bottomOneBradius" onchange="changeWarning();">
            <table class="topTwo" style="margin-bottom: -10px;">
                <tr>
                    <td class="td-le" style="width: 100px;">*手机交易码:</td>
                    <td style="padding-left: 5px; padding-right: 0;">
                        <input type="text" name="msgCode" id="code" maxlength="6"
                               onchange="changeWarning();" value="${model.msgCode}"/>
                    </td>
                    <td style="width: 160px; padding-left: 5px;">
                        <input type="button" value="获取手机交易码" style="padding-left:10px;padding-right:10px;" class="HandInHui" id="msgCodeButton"
                               onclick="sendMessage();"/>
                    </td>
                </tr>
            </table>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span id="codeWarning" style="color: red; font-size: 12px;"></span>
        </div>
        <div class="HandIn" style="margin-top: 20px;">
            <input type="submit" value="提交" onclick="return validate_form();"/>
        </div>
        <input type="hidden" id="keycode" value="${keycode}">
    </form>
</div>
<div id="sending"
     style="position: absolute; position: fixed; height: 100%; background: #fff; width: 100%; top: 0; left: 0; visibility: hidden; filter: alpha(opacity = 50); opacity: 0.50;">
    <img src="<c:url value="/images/wechatbank/5-121204193R5-50.gif"/>"
         style="position: absolute; top: 50%; left: 50%; margin-left: -16px; margin-top: -16px;"/>
</div>
<script type="text/javascript">
    var isClicked = false;
    var can = false;

    // 表单验证
    function validate_form() {
        if (isClicked == false) {
            var nameValue = $("#name").val();
            var typeVaule = $("#identityType").val();
            var identityNoValue = $("#identityNo").val();
            var nameWarning = $("#nameWarning");
            var typeWarning = $("#typeWarning");
            var idNumWarning = $("#idNumWarning");
            var codeWarning = $("#codeWarning");
            var code = $("#code").val();

            if (nameValue == null || nameValue == "") {
                nameWarning.text("*姓名不能为空，请输入！");
                return false;
            }
            if (typeVaule == null || typeVaule == "") {
                typeWarning.text("*证件类型不能为空，请选择！");
                return false;
            }
            if (identityNoValue == null || identityNoValue == "") {
                idNumWarning.text("*证件号不能为空，请输入！");
                return false;
            }
            if (code == null || code == "") {
                codeWarning.text("请填写短信验证码！");
                return false;
            }
            var msgCode = /(^\d{6}$)/;
            if (msgCode.test(code) == false) {
                codeWarning.text("短信验证码格式不正确，请重新输入！");
                return false;
            }
            var sending = $("#sending");
            sending.css("visibility", "visible");
            isClicked = true;
            //验证短信码是否正确
            check();
            if (!can) {
                codeWarning.text("短信验证码错误，请您重新输入！");
                sending.css("visibility", "hidden");
                isClicked = false;
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    // 清空警告
    function changeWarning() {
        $("#nameWarning").text("");
        $("#typeWarning").text("");
        $("#idNumWarning").text("");
    }

    // 倒计时
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

    // 发送短信验证码
    function sendMessage() {
        var keycode = $("#keycode").val();
        var identityNo = $("#identityNo").val();
        var identityType = $("#identityType").val();
        var mobileNo = $("#mobileNo").val();
        var verificationCode = $("#verificationCode").val();

        var typeWarning = $("#typeWarning");
        var idNumWarning = $("#idNumWarning");
        var mobileNoWarning = $("#mobileNoWarning");
        var verificationCodeWarning = $("#verificationCodeWarning");
        var codeWarning = $("#codeWarning");
        typeWarning.text("");
        idNumWarning.text("");
        verificationCodeWarning.text("");
        mobileNoWarning.text("");
        codeWarning.text("");

        if (identityType == null || identityType == "") {
            typeWarning.text("*证件类型不能为空，请选择！");
            return false;
        }
        if (identityNo == null || identityNo == "") {
            idNumWarning.text("*证件号不能为空，请输入！");
            return false;
        }
        if (mobileNo == null || mobileNo == "") {
            mobileNoWarning.text("*手机号不能为空，请输入！");
            return false;
        }
        if (verificationCode == null || verificationCode == "") {
            verificationCodeWarning.text("*验证码不能为空，请输入！");
            return false;
        }
        $("#msgCodeButton").attr("disabled", true);
        $.ajax({
            url: "getMsgCode_ajax.do",
            data: {
                identityType: identityType,
                identityNo: identityNo,
                mobileNo: mobileNo,
                verificationCode: encodeURI(verificationCode),
                keycode: keycode,
                timestamp: new Date().getTime()
            },
            type: "post",
            dataType: "text",
            async: true,
            success: function (result) {
                $("#msgCodeButton").removeAttr("disabled");
                if (result != null && result != "") {
                    var resultIndex = result.indexOf(",");
                    if (resultIndex != -1) {
                        var resultOne = result.substring(resultIndex + 1);
                        $("#keycode").val(resultOne);
                        result = result.substring(0, resultIndex);
                    }
                    if (result == "exception") {
                        window.location.href = "../error.html";
                    } else if (result == "false"){
                        codeWarning.text("短信验证码发送失败，请稍候再试！");
                    }else if (result == "errorCode") {
                        verificationCodeWarning.text("您填写的验证码有误，请重新输入！");
                    } else if (result == "wrongMobileNo") {
                        mobileNoWarning.text("您填写的手机号有误，请重新输入!");
                    } else {
                        buttonTimeOut();
                    }
                }
            }
        });
    }

    // 验证短信验证码
    function check() {
        var identityNo = $("#identityNo").val();
        var code = $("#code").val();
        var mobileNo = $("#mobileNo").val();

        $.ajax({
            url: "checkMsgCode_ajax.do",
            data: {
                identityNo: identityNo,
                mobileNo: mobileNo,
                code: code,
                timestamp: new Date().getTime()
            },
            type: "post",
            dataType: "text",
            async: false,
            success: function (result) {
                if (result != null && result != "") {
                    if (result == "exception") {
                        window.location.href = "../error.html";
                        can = false;
                    } else if (result == "true") {
                        can = true;
                    } else {
                        can = false;
                    }
                }
            }
        });
    }
</script>
</body>
</html>

