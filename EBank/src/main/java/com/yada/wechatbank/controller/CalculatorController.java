package com.yada.wechatbank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 计算器
 * Created by QinQiang on 2016/4/13.
 */
@Controller
@RequestMapping(value = "calculator")
public class CalculatorController {

    private static final String DEPOSIT_CALCULATOR_URL = "wechatbank_pages/Calculator/depositCalculator";
    private static final String LOAN_CALCULATOR_URL = "wechatbank_pages/Calculator/loanCalculator";
    private static final String FORCUR_CALCULATOR_URL = "wechatbank_pages/Calculator/forcurCalculator";
    private static final String PERSONAL_CALCULATOR_URL = "wechatbank_pages/Calculator/personalCalculator";

    /**
     * 存款计算器
     *
     * @return URL
     */
    @RequestMapping(value = "depositCalculator")
    public String depositCalculator() {
        return DEPOSIT_CALCULATOR_URL;
    }

    /**
     * 贷款计算器
     *
     * @return URL
     */
    @RequestMapping(value = "loanCalculator")
    public String loanCalculator() {
        return LOAN_CALCULATOR_URL;
    }

    /**
     * 外币兑换计算器
     *
     * @return URL
     */
    @RequestMapping(value = "forcurCalculator")
    public String forcurCalculator() {
        return FORCUR_CALCULATOR_URL;
    }

    /**
     * 个人所得税计算器
     *
     * @return URL
     */
    @RequestMapping(value = "personalCalculator")
    public String personalCalculator() {
        return PERSONAL_CALCULATOR_URL;
    }

}
