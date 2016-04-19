package com.yada.wechatbank.controller;

import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.model.CardApplyList;
import com.yada.wechatbank.query.CardApplyQuery;
import com.yada.wechatbank.service.CardApplyService;
import com.yada.wechatbank.service.SmsService;
import com.yada.wechatbank.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * 预约办卡进度查询
 * Created by QinQiang on 2016/4/11.
 */
@Controller
@RequestMapping(value = "cardapply")
public class CardApplyController extends BaseController {

    private static final String LIST_URL = "wechatbank_pages/CardApply/list";
    private static final String DETAIL_URL = "wechatbank_pages/CardApply/detail";

    @Autowired
    private CardApplyService cardApplyService;
    @Autowired
    private SmsService smsService;

    /**
     * 初始化申请进度查询页面
     *
     * @param request HttpServletRequest
     * @return URL
     */
    @RequestMapping(value = "list")
    public String list(HttpServletRequest request) {
        // TODO QQ 添加分享转发代码
        TokenUtil.addToken(request);
        return LIST_URL;
    }

    /**
     * 预约办卡进度查询
     *
     * @param cardApplyQuery 查询条件
     * @param model          Model
     * @return URL
     */
    @RequestMapping(value = "listP")
    public String listP(@ModelAttribute("formBean") CardApplyQuery cardApplyQuery, Model model) {
        String name = cardApplyQuery.getName();
        String identityType = cardApplyQuery.getIdentityType();
        String identityNo = cardApplyQuery.getIdentityNo();
        int currPage = cardApplyQuery.getCurrPage();
        int nextPage = currPage + 1;

        // 检查查询要素
        if (name == null || name.isEmpty() || identityType == null ||
                identityType.isEmpty() || identityNo == null || identityNo.isEmpty()) {
            model.addAttribute("cardApplyList", new CardApplyList());
            return DETAIL_URL;
        }

        // 预约办卡进度查询
        CardApplyList cardApplyList = cardApplyService.getCrdCardSchedule(name, identityType, identityNo, nextPage);

        model.addAttribute("model", cardApplyQuery);
        model.addAttribute("cardApplyList", cardApplyList);

        return DETAIL_URL;
    }

    /**
     * 获取短信验证码
     *
     * @return String
     */
    @RequestMapping(value = "getMsgCode_ajax")
    @ResponseBody
    public String getMsgCode_ajax() {
        String result = "true";
        smsService.sendCardApplySMS("", "","");
        return result;
    }

    /**
     * 验证短信验证码
     *
     * @return String
     */
    @RequestMapping(value = "checkMsgCode_ajax")
    @ResponseBody
    public String checkMsgCode_ajax() {
        // TODO QQ 完善验证短信验证码
        String result = "true";
        return result;
    }

}
