<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">

wx.config({
    debug: false,
    appId: '${appid}',
    timestamp: '${timestamp}', 
    nonceStr: '${noncestr}',
    signature: '${signature}',
    jsApiList: [ 'checkJsApi',
                 'onMenuShareTimeline',
                 'onMenuShareAppMessage'
] 
});

</script>