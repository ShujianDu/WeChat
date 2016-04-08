<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript">
	var checkApiFlag = "${checkApiFlag}";
	wx.ready(function(){
		wx.checkJsApi({
		    jsApiList: ['chooseImage'], 
		    success: function(res) {
		      isOk=true;
		      if('true'==checkApiFlag){
		    	  alert(res);
				}
		      }
		    });
	 
		wx.onMenuShareTimeline({
		    title: '${jsTitle}', // 分享标题
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
		    title: '${jsTitle}', // 分享标题
		    desc: '${jsTitle}', // 分享描述
		    link: '${linkUrl}', // 分享链接
		    imgUrl: 'http://wcbt2.bjyada.com/wechatebank/images/shareLogo.jpg', // 分享图标
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
</script>