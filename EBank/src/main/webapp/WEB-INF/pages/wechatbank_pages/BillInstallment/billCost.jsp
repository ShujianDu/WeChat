<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../../base_pages/base.jsp" %>
    <meta name="viewport"
          content="initial-scale=1, maximum-scale=1, user-scalable=no"/>
    <title>中国银行信用卡</title>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/css/wechatbank/index.css"/>"/>
</head>
<body>
<header id="top">
    <span></span>

    <h2>账单分期信息</h2>
    <span></span>
</header>
<form action="installment.do" method="post" id="submitForm">

    <div class="topOneB mar-1 topOneBradius" onchange="changeWarning();">
        <table class="topTwo" style="margin-bottom: -10px;">
            <tr>
                <td>卡号：</td>
                <td align="right">${fn:substring(cardNo,0,16)}</td>
            </tr>
        </table>
    </div>
    <div class="topOneB mar-1" onchange="changeWarning();">
        <table class="topTwo" style="margin-bottom: -10px;">
            <tr>
                <td>分期币种：</td>
                <td align="right">${currencyCode }</td>
            </tr>
        </table>
    </div>
    <div class="topOneB mar-1" onchange="changeWarning();">
        <table class="topTwo" style="margin-bottom: -10px;">
            <tr>
                <td>分期金额：</td>
                <td align="right">${billActualAmount}</td>
            </tr>
        </table>
    </div>
    <div class="topOneB mar-1" onchange="changeWarning();">
        <table class="topTwo" style="margin-bottom: -10px;">
            <tr>
                <td>分期期数：</td>
                <td align="right">${billCost.installmentsNumber }</td>
            </tr>
        </table>
    </div>
    <div class="topOneB mar-1" onchange="changeWarning();">
        <table class="topTwo" style="margin-bottom: -10px;">
            <tr>
                <td>分期手续费：</td>
                <td align="right">${billCost.installmentsfee }</td>
            </tr>
        </table>
    </div>

    <div class="topOneB mar-1" onchange="changeWarning();">
        <table class="topTwo" style="margin-top:-4px;">
            <tr>
                <td>手续费收取方式：</td>
                <c:if test="${billCost.billFeeMeans eq 0}">
                    <input type="hidden" name="feeInstallmentsFlag" value="0"/>
                    <td align="right">一次性收取</td>
                </c:if>
                <c:if test="${billCost.billFeeMeans eq 1}">
                    <input type="hidden" name="feeInstallmentsFlag" value="1"/>
                    <td align="right">分期收取</td>
                </c:if>
            </tr>
        </table>
    </div>
    <div class="topOneB mar-1" onchange="changeWarning();">
        <table class="topTwo" style="margin-top:-4px;">
            <tr>
                <td>分期后首期应还金额：</td>
                <td align="right">${billCost.installmentsAlsoAmountFirst }</td>
            </tr>
        </table>
    </div>

    <div class="topOneB mar-1" onchange="changeWarning();">
        <table class="topTwo" style="margin-top:-4px;">
            <tr>
                <td>分期后其余每期应还金额：</td>
                <td align="right">${billCost.installmentsAlsoAmountEach }</td>
            </tr>
        </table>
    </div>
    <div class="topOneB mar-1" onchange="changeWarning();">
        <table class="topTwo" style="margin-top:-4px;">
            <tr>
                <td>本期账单剩余还款金额：</td>
                <td align="right">${billCost.currentBillSurplusAmount }</td>
            </tr>
        </table>
    </div>
    <div class="topOneB mar-1" onchange="changeWarning();">
        <table class="topTwo" style="margin-top:-4px;">
            <tr>
                <td>本期账单最小还款金额：</td>
                <td align="right">${billCost.currentBillMinimum }</td>
            </tr>
        </table>
    </div>

    <div class="topOneB mar-1">
        <table class="topTwo" style="margin-bottom: -10px;">
            <tr>
                <td class="td-le">手机号：</td>
                <td><input type="text" name="mobileNo" id="mobileNo"
                           maxlength="11" onchange="changeWarning();"/>
                </td>
            </tr>
        </table>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="mobileNoWarning"
                                                   style="color: red; font-size: 12px;"></span>
    </div>
    <div class="topOneB mar-1">
        <table class="topTwo">
            <tr>
                <td>验证码:</td>
                <td>
                    <table width="100%">
                        <tr>
                            <td class="paddingLeft_0"><input type="text"
                                                             maxlength="4" name="verificationCode" id="verificationCode"
                                                             onchange="changeWarning();"/></td>
                            <td width="70"><img src="../showradomcode.jsp"/></td>
                        </tr>
                    </table>

                </td>
            </tr>
        </table>

        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span
            id="verificationCodeWarning" style="color: red; font-size: 12px;"></span>
    </div>

    <div class="topOneB mar-1 bottomOneBradius"
         onchange="changeWarning();">
        <table class="topTwo" style="margin-bottom: -10px;">
            <tr>
                <td class="td-le" style="width: 100px;">*手机交易码:</td>
                <td style="padding-left: 5px; padding-right: 0;"><input
                        type="text" name="msgCode" id="code" maxlength="6"
                        onchange="changeWarning();" value="${model.msgCode}"/></td>
                <td style="width: 100px; padding-left: 5px;"><input
                        type="button" value="获取手机交易码" class="HandInHui" id="msgCodeButton"
                        onclick="sendMessage();"/></td>
            </tr>
        </table>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span id="codeWarning"
                                                   style="color: red; font-size: 12px;"></span>
    </div>
    <div class="HandIn" style="margin-top: 10px; margin-bottom: 10px;">
        <input type="submit" value="确认" onclick="return validate_form();"/>
        <input type="hidden" id="accountId" name="accountId" value="${accountId}"/>
        <input type="hidden" id="accountNo" name="accountNo" value="${accountNo}"/>
        <input type="hidden" id="currencyCode" name="currencyCode" value="${currencyCode}"/>
        <input type="hidden" id="cardNo" name="cardNo" value="${fn:substringAfter(cardNo,',')}"/>
        <input type="hidden" id="billActualAmount" name="billActualAmount" value="${billActualAmount}"/>
        <input type="hidden" id="installmentsNumber" name="installmentsNumber" value="${installmentsNumber}"/>
        <input type="hidden" id="billLowerAmount" name="billLowerAmount" value="${billLowerAmount}"/>
    </div>
</form>
<div class="topOneB mar-1 allOneBradius" style="margin-bottom: 10px">
    <table class="topTwo" style="margin-bottom: 10px;">
        <tr>
            <td>
                温馨提示：提交分期申请前请仔细核对信息，分期提交成功后将不能对分期的期数和金额进行修改；申请分期成功后：1、手续费一次性收取，您分期的首期金额与手续费将计入最近一期账单；2、手续费分次收取，您的分期手续费将分次进入每期账单。分期业务详细介绍请的登录中国银行官网查询。
            </td>
        <tr/>
    </table>
</div>
<div id="sending"
     style="position: absolute; position: fixed; height: 100%; background: #fff; width: 100%; top: 0; left: 0; visibility: hidden; filter: alpha(opacity =     50); opacity: 0.50;">
    <img src="<c:url value="/images/wechatbank/5-121204193R5-50.gif"/>"
         style="position: absolute; top: 50%; left: 50%; margin-left: -16px; margin-top: -16px;"/>
</div>
<div id="timeoutdiv1"
     style="position: absolute; position: fixed; height: 100%; background: #fff; width: 100%; top: 0; left: 0; display: none; filter: alpha(opacity =     70); opacity: 0.70;">
</div>
<div id="timeoutdiv2" class="topOneB mar-1 allOneBradius bcS"
     style="position: absolute; top: 50%; left: 50%; width: 280px; margin-left: -140px; margin-top: -16px; margin-bottom: 10px; display: none;">
    <table class="topTwo" style="margin-bottom: 10px;">
        <tr>
            <td style="font-size: 16px;">该页面已失效，请返回重新操作！</td>
        <tr/>
    </table>
</div>
<script type="text/javascript">
    var isClicked = false;
    var can = false;
    function validate_form() {
        if (isClicked == false) {
            var code = $("#code");
            var codeWarning = $("#codeWarning");
            if (code.val()== null || code.val() == "") {
                $("#codeWarning").text ("请填写短信验证码！");
                return false;
            }
            var msgCode = /(^\d{6}$)/;
            if (msgCode.test(code.val()) == false) {
                codeWarning.text ("短信验证码格式不正确，请重新输入！");
                return false;
            }
            var sending = $("#sending");
            sending.css("visibility","visible");
            isClicked = true;
            //验证短信码是否正确
            check();
            if (!can) {
                codeWarning.text ("短信验证码错误，请您重新输入！");
                sending.css("visibility","hidden");
                isClicked = false;
                return false;
            }
            return true;
        }
    }

    function changeWarning() {
        $("#codeWarning").text ("");;
    }
    var i = 60;
    function buttonTimeOut() {
        var msgCodeButton = $("#msgCodeButton");
        i--;
        if (i == 0) {
            i = 60;
            msgCodeButton.attr("disabled" ,false);
            msgCodeButton.val("获取验证码");
        } else {
            msgCodeButton.attr("disabled" ,true);
            msgCodeButton.val(i + "秒");
            setTimeout("buttonTimeOut()", 1000);
        }
    }

    function sendMessage() {
        $("#verificationCodeWarning").text ("");
        $("#mobileNoWarning").text("");
        var mobileNo = $("#mobileNo").val();
        var verificationCode = $("#verificationCode").val();
        if (mobileNo == null || mobileNo == "") {
            $("#mobileNoWarning").text("*手机号不能为空，请输入！");
            return false;
        }
        if (verificationCode == null || verificationCode == "") {
            $("#verificationCodeWarning").text("*验证码不能为空，请输入！");
            return false;
        }
        $.ajax({
            url: "getMsgCode_ajax.do",
            data: {
                verificationCode: encodeURI(verificationCode),
                mobileNo: mobileNo,
                date: new Date().getTime()
            },
            type: "post",
            dataType: "text",
            async: true,
            success: function (result) {
                if (result != null && result != "") {
                    if (result == "exception" || result == "false") {
                        window.location.href = "../error.html";
                    } else if (result == "errorCode" ) {
                        $("#verificationCodeWarning").text("您填写的验证码有误，请重新输入!");
                    }else if ( result == "wrongMobilNo") {
                        $("#mobileNoWarning").text("您填写的手机号有误，请重新输入");
                    } else {
                        buttonTimeOut();
                    }
                }
            }

        });
    }

        function check() {
            var code = $("#code").val();
            var mobileNo = $("#mobileNo").val();
            $.ajax({
                url: "checkMagCode_ajax.do",
                data: {
                    code: code,
                    mobileNo: mobileNo,
                    date: new Date().getTime()
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
</script>
</body>
</html>