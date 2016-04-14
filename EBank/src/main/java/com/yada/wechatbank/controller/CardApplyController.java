package com.yada.wechatbank.controller;

import com.alibaba.fastjson.JSON;
import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.model.CardApplyList;
import com.yada.wechatbank.query.CardApplyQuery;
import com.yada.wechatbank.service.CardApplyService;
import com.yada.wechatbank.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


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

    @ResponseBody
    @RequestMapping(value = "getJson")
    public String getJson(HttpServletRequest request) {
        try {
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null)
                System.out.print(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<>();
        map.put("key4", "value4");
        map.put("秦强", "value4");
        String str = JSON.toJSONString(map);
        return str;
    }


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
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     */
    @RequestMapping(value = "getMsgCode_ajax")
    public void getMsgCode_ajax(HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        String result = "true";
        String mobilNo = request.getParameter("mobilNo");
        response.getWriter().print(result);
        response.getWriter().flush();
    }

    /**
     * 验证短信验证码
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @throws IOException
     */
    @RequestMapping(value = "checkMsgCode_ajax")
    public void checkMsgCode_ajax(HttpServletRequest request,
                                  HttpServletResponse response) throws IOException {
        String result = "true";
        String code = request.getParameter("code");
        response.getWriter().print(result);
        response.getWriter().flush();
    }

}
