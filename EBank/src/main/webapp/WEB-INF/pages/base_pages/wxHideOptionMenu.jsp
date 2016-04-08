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
		wx.hideOptionMenu();
	});
</script>