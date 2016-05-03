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
    <link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="<c:url value="/css/bootstrap-slider.min.css"/>" type="text/css">
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/bootstrap-datetimepicker.min.css"/>" />
    <link href="<c:url value="/css/new_index.css"/>" rel="stylesheet" type="text/css">

    <script src="<c:url value="/js/jquery-2.2.3.min.js"/>"></script>
    <script src="<c:url value="/js/bootstrap-slider.min.js"/>"></script>
    <script type="text/javascript"
            src="../js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
    <script type="text/javascript"
            src="../js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
    <style>
        .UpgradeQuery_title{font-size:14px;text-align:center;line-height:40px;color:#999999;background-color:#eeeeee;}

        </style>
</head>
<body>
<style>
    .list-group-item{font-size:14px;}
    </style>
<form action="" method="post" name="submitForm" id="submitForm">
<div class="UpgradeLine_box">
 <div class="UpgradeQuery_title">提升额度</div>
    <div class="container" style="margin-top:10px;">
            <table class="table table-shadow table-bg bg" style="height:36px;">
                <tr>
                    <th style="font-size:15px;line-height:36px;">信用卡号</th>
                    <th class="text-right" style="font-size:15px;line-height:36px;">${fn:substring(cardNo, 0, 16)}</th>
                </tr>
            </table>

    <div class="well well-bg nopd">
        <div class="container" style="width: 100%;">
            <span id="ex6CurrentSliderValLabel" class="text-primary"
                  style="display:block; width: 100%;text-align:
                      center;padding-bottom: 20px;font-size:18px;">
                    +
                    <span id="ex6SliderVal" class="text-red" style="font-size:20px;">0</span>
                </span>
            <input id="ex6" type="text"
                   data-slider-min="0"
                   name="eosPreAddLimit"
                   data-slider-max="${amounts.amount }"
                   data-slider-step="100"
                   data-slider-value="0" style="width:100%;">

            <p>
                <span class="pull-left text-primary">拖动提升</span> <span
                    id="amountWarning" style="color: red; font-size: 12px;"></span><span
                    class="pull-right text-primary">提升${amounts.amount }</span>
            </p>
        </div>
        <div style="height: 30px"></div>
        <ul class="list-group nobd">
           <%-- class="form-control form_datetime index-date"--%>
            <li class="list-group-item" ><span style="font-size:14px;" >生效日期</span> <span class="pull-right" style="font-size:14px;">${effectiveDate }</span>
            </li>
            <li class="list-group-item"><span style="font-size:14px;">失效日期</span> <input size="16"
                                                                 type="text" value="" readonly
                                                                 name="eosEndlimitdate"
                                                                 id="datetimeEnd"
                                                                 placeholder="请选择失效日期" class="pull-right" style="text-align:right;font-size:14px;"> <span
                    id="datetimeEndWarning"
                    style="color: red; font-size: 14px;"></span></li>
            <li class="list-group-item text-center">
                    <span
                            class="pull-left" style="margin-left:8px;font-size:14px;">手机验证码</span>
                <input
                        id="msgCode" class="form-inline"
                        placeholder="输入验证码" maxlength="6" onchange="changeWarning();"  style="font-size:14px;text-align:center;border: none;background-color: transparent;margin-top:-2px;">
                <input
                        class="btn btn-xs pull-right" id="msgCodeButton" value="发送验证码"
                        type="button" onclick="sendMessage();" style="float:right;font-size:14px;background-color:#fafafa;border:1px solid #eeeeee;line-height: 28px;margin-top:-8px;width:84px;color:#666666;"> <span id="codeWarning"   style="color: red; font-size: 12px;"></span></li>
        </ul>
    </div>

        <input type="button" id="tijiao" class="btn btn-block btn-default btn-sm btn-bank"
               value="提&nbsp;&nbsp;交" onclick="submitTemp();" style="background-color:#e05d4f;color:white;height:40px;margin-top:20px;margin-bottom:30px;font-size:15px;">

    </div>
    </div>
    <input type="hidden" id="cardNo" name="cardNo" value="${fn:substringAfter(cardNo, ',')}">
    <input type="hidden" id="cardStyle" name="cardStyle" value="${amounts.cardStyle}">
    <input type="hidden" id="issuingBranchId" name="issuingBranchId" value="${amounts.issuingBranchId}">
    <input type="hidden" id="pmtCreditLimit" name="pmtCreditLimit" value="${amounts.pmtCreditLimit}">
    <input type="hidden" id="minExpirationDate" name="minExpirationDate" value="${minExpirationDate}">
    <input type="hidden" id="eosStarLimitDate" name="eosStarLimitDate" value="${eosStarLimitDate}">
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
</script>
<script type="text/javascript">

    //提交提升额度表单
    var can = false;
    var form = $("#submitForm");
    function submitTemp() {
        $("#amountWarning").text ("");
        $("#datetimeEndWarning").text ("");
        checkMsgCode();
        if ($("#ex6").val() == "0") {
            $("#amountWarning").text("请选择提升额度");
            return false;
        }
        if ($("#datetimeEnd").val() == "") {
            $("#datetimeEndWarning").text ("请选择失效日期");
            return false;
        }
        if ($("#msgCode").val() == "") {
            $("#codeWarning").text ("请输入手机验证码");
            return false;
        }
        if (!can) {
            $("#codeWarning").text ("验证码错误，请重新输入！");
            return false;
        }
        form.attr("action","temporaryUp.do");
        form.submit();
    }

    //提升额度历史查询
    function showHistory() {
        form.attr("action","showHistory.do");
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
                date: new Date().getTime(),
                code: code
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
        $("#codeWarning").text ("");
    }
    //获取验证码时间限制
    var i = 60;
    function buttonTimeOut() {
        var msgCodeButton = $("#msgCodeButton");
        i--;
        if (i == 0) {
            i = 60;
            msgCodeButton.attr("disabled", false);
            msgCodeButton.val("获取验证码");
        } else {
            msgCodeButton.attr("disabled", true);
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
