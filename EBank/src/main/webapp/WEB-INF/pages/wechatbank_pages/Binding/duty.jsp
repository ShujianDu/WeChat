<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@include file="../../base_pages/base.jsp"%>
<%@include file="../../base_pages/wxjs.jsp"%>
<meta name="viewport"
	content="initial-scale=1, maximum-scale=1, user-scalable=no" />
<title>中国银行信用卡</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/bootstrap.min.css"/>" />
<link rel="stylesheet" type="text/css"
	href="<c:url value="/css/wechatbank/index.css"/>" />
<%@include file="../../base_pages/wxReadyFunction.jsp"%>
</head>
<body>
	<header id="top">
		<span></span>
		<h2>中国银行信用卡微信服务客户须知</h2>
		<span></span>
	</header>
	<div class="container">
		<div class="img-bg table-shadow">
			<div style="height:10px;"></div>
			<h5 style="font-weight: 300">
				<p style="margin:5px 10px; ">尊敬的用户</p>
				<p style="margin:5px 10px; ">
					1.中国银行提供的信用卡微信服务是指在客户成功注册腾讯控股有限公司微信帐号（以下简称“微信账号”），并关注“中国银行信用卡”公众帐号后，中国银行根据客户发送的指令，通过“中国银行信用卡”公众账号渠道，向客户提供的信用卡账户信息查询、挂失/解挂、办卡进度查询、周边网点查询、业务咨询等金融服务。其中，对于客户要求办理信用卡账户信息查询、挂失/解挂、修改账单寄送方式等服务时，客户需事先根据本须知的约定成功绑定相关账户。
				</p>

				<p style="margin:5px 10px; ">
					2.绑定账户是指客户通过其微信账号关注“中国银行信用卡”公众帐号后，在“中国银行信用卡”公众账号“账户绑定”界面中，输入其在信用卡申办时填写的“证件类型”、“证件号码”，以及客户在激活信用卡时设置的电话银行查询密码，经中国银行验证通过后，客户在其有效的信用卡列表中选择一张信用卡作为默认信用卡，在收到中国银行通过“中国银行信用卡”公众账号发送的客户账户绑定成功指令后即视为客户成功完成账户绑定。
							为保证客户身份的唯一性，中国银行将客户微信账号对应的身份识别码（Open
							Id）与客户名下所有有效的信用卡账户进行关联，客户可随时选择变更默认信用卡，中国银行仅验证身份识别码与相关账户的关联关系，不再验证其他身份验证要素。</p>
				<p style="margin:5px 10px; ">	3.
							信用卡账户信息查询是指客户通过特定的身份识别码向“中国银行信用卡”公众账号发出的关联账户的客户资料、授信额度、交易明细、积分余额查询等请求，中国银行仅验证身份识别码与相关账户的关联关系，不再验证其他身份验证要素。验证通过后，中国银行通过“中国银行信用卡”公众账号向客户相应微信账号反馈所查询的信息。</p>
				<p style="margin:5px 10px; ">	4.
							客户使用中国银行提供的微信服务时，应直接登录腾讯公司官方微信客户端，不要通过其他方式登录，并应关注经认证的“中国银行信用卡”公众账号。客户下载微信客户端时，应通过腾讯公司门户网站或中国银行认可的渠道下载，不要下载并使用来历不明的客户端软件。</p>
				<p style="margin:5px 10px; ">	5.
							客户应知悉并了解，由于微信客户端的特殊性，中国银行向其提供的信用卡微信服务通过微信标准的HTTPS方式加密，可能含有客户个人金融信息，客户如申请本服务，应承担可能由此产生的一定风险和责任。</p>
				<p style="margin:5px 10px; ">	6.
							客户接受信用卡微信服务，有可能产生通信服务费等第三方收取的费用，客户应直接向相关第三方缴纳。中国银行保留就信用卡微信服务单独向客户收取服务费的权利。</p>
				<p style="margin:5px 10px; ">	7.
							为保障客户的账户安全，客户应妥善保管客户的证件号码、账号、手机号码、验证码、微信账号（包括身份识别码）、微信密码等信息。如客户的手机、手机验证码、微信账号和/或密码等与本须知服务内容相关的设备/信息被他人使用或盗用，而造成客户相关信息泄漏或资金损失，中国银行不承担责任。</p>
				<p style="margin:5px 10px; ">	8.
							客户不得利用微信服务从事洗钱、欺诈等违法、违规行为，如中国银行发现客户从事违法、违规行为，中国银行有权暂停或终止向客户提供信用卡微信服务。客户的交易情况，均以中国银行计算机系统记录的资料为准，双方均承认中国银行计算机系统记录资料的真实性、准确性和合法性。如因不可抗力或其他不可归因于中国银行的原因（包括但不限于微信客户端故障或移动运营商的通信网络故障等）导致中国银行提供的信用卡微信服务延迟、错误的，中国银行不承担责任。</p>
				<p style="margin:5px 10px; ">	9.
							如因中国银行对银行系统进行升级、改造而引起的服务取消、暂停或者服务内容、项目、方式等变化，或根据法律法规、监管要求或业务发展需要修改本须知、服务内容、时间或业务规则，中国银行将通过“中国银行信用卡”公众账号提前进行公告，不再逐一通知客户。若客户拒绝此修改，有权选择取消关注信用卡微信服务。若客户在公告的业务变更后未取消关注或继续使用信用卡微信服务的，即视为客户同意并接受该变更。</p>
					</p>
		</div>
			<div class="HandIn" style="margin-top:10px;margin-bottom:20px;">
			<input class="btn btn-sm btn-default btn-block btn-shadow" type="button"  onclick="back();"
				value="返&nbsp;&nbsp;回">
			</div>
	</div>
	<script type="text/javascript">
		function back() {
			history.go(-1);
		}
	</script>

</body>
</html>