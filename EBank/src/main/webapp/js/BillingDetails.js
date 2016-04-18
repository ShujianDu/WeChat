function getMore(){
	var cardNo = $("#cardNo").val();
	var queryType = $("#queryType").val();
	var startnum = $("#startnum").val();
	var periodStartDate = $("#periodStartDate").val();
	var periodEndDate = $("#periodEndDate").val();
	var currencyCode = $("#currencyCode").val();
	var noBillingWarning = $("#noBillingWarning");
	noBillingWarning.text("");
	 $.ajax({
            url: "getMoreBillingDetail.do",
            data: {
            	cardNo: cardNo,
            	queryType: queryType,
            	startnum: startnum,
            	periodStartDate: periodStartDate,
            	periodEndDate: periodEndDate,
            	currencyCode: currencyCode
            },
            type: "post",
            dataType: "json",
            async: false,
            success: function (json) {
                if (json != null && json != "") {
                	// TODO 账单明细处理
                	messageReveal(json);
                }else if(json==""){
                	noBillingWarning.text("*没有更多的账单了！");
                }else{
                	window.location.href = "../error.html";
                }
            }
        });
}

$(function(){
	$(".BillingDetails_content").height(parseInt(document.documentElement.clientHeight)-211);
	
	var showMoreNChildren = function ($children, n) {
                    //显示某jquery元素下的前n个隐藏的子元素
                    var $hiddenChildren = $children.filter(":hidden");
                    var cnt = $hiddenChildren.length;
                    for ( var i = 0; i < n && i < cnt ; i++) {
                        $hiddenChildren.eq(i).show();
                    }
                    return cnt-n;//返回还剩余的隐藏子元素的数量
                }
 		if($(".showMoreNChildren li").length<4){$("#readMore").hide();}
                //对页中现有的class=showMorehandle的元素，在之后添加显示更多条，并绑定点击行为
                $(".showMoreNChildren").each(function () {
                    var pagesize = $(this).attr("pagesize") || 10;
                    var $children = $(this).children();
                    if ($children.length > pagesize) {
                        for (var i = pagesize; i < $children.length; i++) {
                            $children.eq(i).hide();
                        }
          
                        $("#readMore").insertAfter($(this)).click(function () {
                            if (showMoreNChildren($children, pagesize) <= 0) {
                                //如果目标元素已经没有隐藏的子元素了，就隐藏“点击更多的按钮条”
                                $(this).hide();
                            };
                        });
                    }
                });
})
