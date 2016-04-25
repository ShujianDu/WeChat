var json;
//等待加载的界面显示和隐藏
function messageRevealWait() {
    $(".wait_box").fadeIn(500);
    setTimeout(function () {
        messageReveal();
    }, 2000);
    setTimeout(function () {
        $(".wait_box").fadeOut(500);
    }, 3000);
}
//清空元素并恢复初始状态
function clearAndInit() {
    $("#state").text("账户");
    $("#credit_value").text("0.00");
    $("#no_value").text("0.00");
    $("#credit0").text("0.00");
    $("#credit1").text("0.00");
    $("#credit2").text("0.00");
    $("#currencyChinaCode div").remove();
    var doughnutData = [
        {
            value: 1,
            color:"#ed6139",
        },
        {
            value: 1000000,
            color: "#0ca8f5",
        },
    ];
    var ctx = $("#chart-area").get(0).getContext("2d");
    window.myDoughnut = new Chart(ctx).Doughnut(doughnutData, {responsive: true});
}

//额度事件,随着币种和银行卡号的变化而变化
function messageReveal() {
    clearAndInit();
    //获取卡号
    var cardNo = $("#cardNo").val();
    if (cardNo == null || cardNo == "") {
        return;
    }
    //时间戳
    var timestamp = new Date().getTime();
    //ajax查询账单
    $.ajax({
        url: "getCardNoBalance_Ajax.do",
        data: {
            cardNo: cardNo,
            timestamp: timestamp
        },
        type: "get",
        dataType: "json",
        async: false,
        success: function (data) {

            if (data == "exception") {
                $(".error_box").fadeIn(500);
                return;
            }
            if(data == ""){
                $(".error_box").fadeIn(500);
                return;
            }
            json = eval(data);
            //设置显示币种，设置其他信息
            //设置币种显示
            $(json).each(function (idx) {
                var newDiv;
                if (idx == 0) {
                    newDiv = "<div class='radioButton'><input type='radio' name='chinaCode' value='" + idx + "' checked='checked' onclick='radioReveal(this.value)' class='credit_select'/>" + json[idx].currencyChinaCode + "账户</div>";
                } else {
                    newDiv = "<div class='radioButton'><input type='radio' name='chinaCode' value='" + idx + "' onclick='radioReveal(this.value)' class='credit_select'/>" + json[idx].currencyChinaCode + "账户</div>";
                }
                $("#currencyChinaCode").append(newDiv);
                var chinaCode = $("input[name=chinaCode]");
                if (chinaCode[idx].checked) {
                    showInfo(idx);
                }
            });
        }
    });
}
function showInfo(idx) {
    /*概率*/
    if (json[idx].wholeCreditLimit - json[idx].periodAvailableCreditLimit >= 0) {
        var doughnutData = [
            {
                value: json[idx].wholeCreditLimit - json[idx].periodAvailableCreditLimit,
                color: "#ed6139"
            },
            {
                value: json[idx].periodAvailableCreditLimit,
                color: "#0ca8f5"
            },
        ];
        var ctx = $("#chart-area").get(0).getContext("2d");
        window.myDoughnut = new Chart(ctx).Doughnut(doughnutData, {responsive: true});
    } else {
        var doughnutData = [
            {
                value: 1,
                color:"#ed6139",
            },
            {
                value: 1000000,
                color: "#0ca8f5",
            },
        ];
        var ctx = $("#chart-area").get(0).getContext("2d");
        window.myDoughnut = new Chart(ctx).Doughnut(doughnutData, {responsive: true});
    }
    $("#state").text(json[idx].currencyChinaCode + "账户");
    $("#credit_value").text(json[idx].wholeCreditLimit);
    $("#no_value").text(json[idx].periodAvailableCreditLimit);
    $("#credit0").text(json[idx].wholeCreditLimit);
    $("#credit1").text(json[idx].preCashAdvanceCreditLimit);
    $("#credit2").text(json[idx].periodAvailableCreditLimit);
}


//币种radio改变
function radioReveal(idx) {
    showInfo(idx);
}

$(function () {
    //black显示和隐藏
    $(".credit_box .help").click(function () {
        $(".black_box").fadeIn(500);
    });
    $(".black_box .tip_close").click(function () {
        $(".black_box").fadeOut(500);
    });
    $(".black_box .tip_close2").click(function () {
        $(".black_box").fadeOut(500);
    });

    $(".error_box .tip_close").click(function () {
        $(".error_box").fadeOut(500);
    });
    $(".error_box .tip_close2").click(function () {
        $(".error_box").fadeOut(500);
    });

});