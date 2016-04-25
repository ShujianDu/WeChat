<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>中国银行信用卡</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="initial-scale=1,
        maximum-scale=1, user-scalable=no" />
    <title></title>
    <%@include file="../../base_pages/base.jsp" %>
    <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="css/bootstrap-slider.min.css" type="text/css">
    <link href="css/index.css" rel="stylesheet" type="text/css">
    <script src="js/jquery-2.2.3.min.js"></script>
    <script src="js/bootstrap-slider.min.js"></script>
</head>
<body>
<form action="" method="post" name="submitForm" id="submitForm">
<div class="UpgradeLine_box">
 <div class="UpgradeQuery_title">提升额度</div>
    <div class="container">
            <table class="table table-shadow table-bg bg">
                <tr>
                    <th>信用卡号</th>
                    <th class="text-right">${fn:substring(cardNo, 0, 16)}</th>
                </tr>
            </table>
    </div>
    <div class="well well-bg nopd">
        <div class="container">
            <span id="ex6CurrentSliderValLabel" class="text-primary"
                  style="display:block; width: 100%;text-align:
                      center;padding-bottom: 20px;font-size:16px;">
                    +
                    <span id="ex6SliderVal" class="text-red">0</span>
                </span>
            <input id="ex6" type="text"
                   data-slider-min="0"
                   name="eosPreAddLimit"
                   data-slider-max="${amount }"
                   data-slider-step="100"
                   data-slider-value="0" style="width: 100%">

            <p>
                <span class="pull-left text-primary">拖动提升</span> <span
                    id="amountWarning" style="color: red; font-size: 12px;"></span><span
                    class="pull-right text-primary">提升${amount }</span>
            </p>
        </div>
        <div style="height: 30px"></div>
        <ul class="list-group nobd">
            <li class="list-group-item"><span>生效日期</span> <input size="16"
                                                                 type="text" value="${effectiveDate }" readonly
                                                                 name="eosStarLimitDate"
                                                                 class="form-control form_datetime index-date"
                                                                 id="datetimeStart">
            </li>
            <li class="list-group-item"><span>失效日期</span> <input size="16"
                                                                 type="text" value="" readonly
                                                                 name="eosEndlimitdate"
                                                                 class="form-control form_datetime index-date"
                                                                 id="datetimeEnd"
                                                                 placeholder="请选择失效日期"> <span
                    id="datetimeEndWarning"
                    style="color: red; font-size: 12px;"></span></li>
            <li class="list-group-item text-center">
                    <span
                            class="pull-left" style="margin-left: 8px">手机验证码</span>
                <input
                        id="msgCode" class="form-inline" style="width: 80px"
                        placeholder="输入验证码" maxlength="6" onchange="changeWarning();">
                <input
                        class="btn btn-xs pull-right" id="msgCodeButton" value="发送验证码"
                        type="button" onclick="sendMessage();"> <span
                    id="codeWarning" style="border:1px solid #eeeeee;margin-top:-8px;"></span></li>
        </ul>
    </div>
    <div class="container">
        <input type="button" id="tijiao" class="btn btn-block btn-default btn-sm btn-bank"
               value="提&nbsp;&nbsp;交" onclick="submitTemp();" style="background-color:#e05d4f;color:white;height:40px;margin-top:20px;margin-bottom:30px;font-size:14px;">
    </div>
    </div>
    <input type="hidden" id="cardNo" name="cardNo" value="${fn:substringAfter(cardNo, ',')}">
    <input type="hidden" id="cardStyle" name="cardStyle" value="${cardStyle}">
    <input type="hidden" id="issuingBranchId" name="issuingBranchId" value="${issuingBranchId}">
    <input type="hidden" id="pmtCreditLimit" name="pmtCreditLimit" value="${pmtCreditLimit}">
    <input type="hidden" id="minExpirationDate" name="minExpirationDate" value="${minExpirationDate}">
    <input type="hidden" id="maxExpirationDate" name="maxExpirationDate" value="${maxExpirationDate}">
</form>
<p class="text-center" style="margin-top: 8px">
    <a href="#" class="a-link" onclick="showHistory();">额度调整历史查询</a>
</p>
<script>
    /*滑块*/
    $("#ex6").slider();
    $("#ex6").on('slide', function (slideEvt) {
        $("#ex6SliderVal").text(slideEvt.value);
    });
    $("#tijiao").click(function(){

        $(".wait_box").fadeIn(500);
        setTimeout(function(){
            $(".UpgradeLine_box").load('UpgradeYES.html');
        },600);
        setTimeout(function(){
            $(".wait_box").fadeOut(500);
        },2000);
    });
</script>
<script type="text/javascript">
    //提升额度历史查询
    function showHistory() {
        $("#submitForm").action = "showHistory.do";
        $("#submitForm").submit();
    }
    //提交提升额度表单

    var can = false;
    var form = $("#submitForm");
    function submitTemp() {
        $("#amountWarning").text ("");
        $("#datetimeEndWarning").text ("");
        checkMsgCode();
        if ($("#ex6").val() == "0") {
            $("#amountWarning").text ("请选择提升额度");
            return;
        }
        if ($("#datetimeEnd").val() == "") {
            $("#datetimeEndWarning").text ("请选择失效日期");
            return;
        }
        if ($("#msgCode").val() == "") {
            $("#codeWarning").text ("请输入手机验证码");
            return;
        }
        if (!can) {
            $("#codeWarning").text ("验证码错误，请重新输入！");
            return;
        }
        form.action = "temporaryUp.do";
        form.submit();
    }

    //发送手机验证码
    function sendMessage() {
        $("#codeWarning").text ("");
        $.ajax({
            url: "getMsgCode_ajax.do",
            data: {
                date: new Date().getTime()
            },
            type: "post",
            dataType: "text",
            async: true,
            success: function (result) {
                if (result != null && result != "") {
                    if ( result == "false") {
                        window.location.href = "../error.html";
                    } else {
                        buttonTimeOut();
                    }
                }
            }
        });
    }
    //验证短信验证码是否正确
    function checkMsgCode() {
        var code = $("#msgCode").val();
        $.ajax({
            url: "checkMagCode_ajax.do",
            data: {
                date: new Date().getTime()
            },
            type: "post",
            dataType: "text",
            async: true,
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
        $("#codeWarning").text ("");
    }
    //获取验证码时间限制
    var i = 60;
    function buttonTimeOut() {
        var msgCodeButton = $("#msgCodeButton");
        i--;
        if (i == 0) {
            i = 60;
            msgCodeButton.disabled = "";
            msgCodeButton.val("获取验证码");
        } else {
            msgCodeButton.disabled = "disabled";
            msgCodeButton.val(i + "秒");
            setTimeout("buttonTimeOut()", 1000);
        }
    }
    /*日期*/
    $("#datetimeEnd").datetimepicker({
        format: "yyyy-mm-dd",
        language: "zh-CN",
        autoclose: true,
        minView: "month",
        maxView: "decade",
        pickerPosition: "bottom-left"
    }).on(
            "click",
            function (ev) {
                $("#datetimeEnd").datetimepicker("setStartDate",
                        $("#minExpirationDate").val());
                $("#datetimeEnd").datetimepicker("setEndDate",
                        $("#maxExpirationDate").val());
            });
</script>
</body>
</html>
