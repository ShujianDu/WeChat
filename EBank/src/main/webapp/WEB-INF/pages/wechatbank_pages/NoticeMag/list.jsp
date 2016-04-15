<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@include file="../../base_pages/base.jsp" %>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/wechatbank/index.css"/>"/>
    <title>中国银行信用卡</title>
    <script type="text/javascript">
        var checkApiFlag = "${checkApiFlag}";
        wx.ready(function () {
            wx.checkJsApi({
                jsApiList: ['chooseImage'],
                success: function (res) {
                    isOk = true;
                    if ('true' == checkApiFlag) {
                        alert(res);
                    }
                }
            });

            wx.onMenuShareTimeline({
                title: '中国银行信用卡推送通知管理', // 分享标题
                link: '${linkUrl}', // 分享链接
                imgUrl: '${imgUrl}', // 分享图标
                success: function () {
                    alert('成功分享！');
                },
                cancel: function () {
                    alert('取消分享');
                }
            });
            wx.onMenuShareAppMessage({
                title: '中国银行信用卡推送通知管理', // 分享标题
                desc: '中国银行信用卡推送通知管理', // 分享描述
                link: '${linkUrl}', // 分享链接
                imgUrl: '${imgUrl}', // 分享图标
                type: '', // 分享类型,music、video或link，不填默认为link
                dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
                success: function () {
                    alert('成功分享！');
                },
                cancel: function () {
                    alert('取消分享');
                }
            });
        });
        function validate_form() {
            if (confirm('您是否确认变更推送通知提醒?')) {
                $("#submitButton").attr("disabled", true);
                $("#form1").submit();
            }
        }
    </script>
</head>

<body>
<header>
    <span></span>
    <h2>推送通知管理</h2>
    <span></span>
</header>
<form action="update.do" id="form1" method="post" id="submitForm">
    <div class="mg-tb-20">
        <c:if test="${msg ne null }">
        <div id="bindWarning" class="topOneB mar-1 allOneBradius bcS"
             style="display: block;">
            <table class="topTwo">
                <tr>
                    <td align="center" style="color: red;">${msg}</td>
                </tr>
            </table>
        </div>
        </c:if>
        <div class="topOneB mar-1 topOneBradius" style="border-radius: 10px;">
            <table style="font-size:12px;">
                <tr class="topTwos">
                    <td class="td-le">动账通知提醒</td>
                    <c:if test="${dhNotice ne '1' }">
                        <td class="tt_poen">开：<input type="radio" id="dhNoticeOpen" name="dhNotice" value="1"></td>
                        <td class="tt_poen">关：<input type="radio" id="dhNoticeClose" name="dhNotice" value="0"
                                                     checked="checked"></td>
                    </c:if>
                    <c:if test="${dhNotice eq '1' }">
                        <td class="tt_poen">开：<input type="radio" id="dhNoticeOpen" name="dhNotice" value="1"
                                                     checked="checked"></td>
                        <td class="tt_poen">关：<input type="radio" id="dhNoticeClose" name="dhNotice" value="0"></td>
                    </c:if>
                </tr>
            </table>
        </div>
        <div class="topOneB mar-1 topOneBradius topOneBradius" style="border-radius: 10px; margin-top:5px;">
            <table>
                <tr class="topTwos">
                    <td class="td-le">账单推送提醒</td>
                    <c:if test="${zdNotice ne '1' }">
                        <td align="right">开：<input type="radio" id="zdNoticeOpen" name="zdNotice" value="1"></td>
                        <td align="right">关：<input type="radio" id="zdNoticeClose" name="zdNotice" value="0"
                                                   checked="checked"></td>
                    </c:if>
                    <c:if test="${zdNotice eq '1' }">
                        <td align="right">开：<input type="radio" id="zdNoticeOpen" name="zdNotice" value="1"
                                                   checked="checked"></td>
                        <td align="right">关：<input type="radio" id="zdNoticeClose" name="zdNotice" value="0"></td>
                    </c:if>
                </tr>
            </table>
        </div>
        <div class="topOneB mar-1 topOneBradius topOneBradius" style="border-radius: 10px; margin-top:5px;">
            <table>
                <tr class="topTwos">
                    <td class="td-le">到期还款提醒</td>
                    <c:if test="${hkNotice ne '1' }">
                        <td align="right">开：<input type="radio" id="hkNoticeOpen" name="hkNotice" value="1"></td>
                        <td align="right">关：<input type="radio" id="hkNoticeClose" name="hkNotice" value="0"
                                                   checked="checked"></td>
                    </c:if>
                    <c:if test="${hkNotice eq '1' }">
                        <td align="right">开：<input type="radio" id="hkNoticeOpen" name="hkNotice" value="1"
                                                   checked="checked"></td>
                        <td align="right">关：<input type="radio" id="hkNoticeClose" name="hkNotice" value="0"></td>
                    </c:if>
                </tr>
            </table>
        </div>
        <div class="HandIn" style="margin: 20px 10px;">
            <input type="button" id="submitButton" value="提交" onclick="validate_form()"
                   style="font-size:2.8em; height:35px;"/>
        </div>
</form>
</body>
</html>