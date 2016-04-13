package com.yada.wechatbank.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.yada.wechatbank.model.JifenDetail;
import com.yada.wechatbank.model.JifenValidate;
import com.yada.wechatbank.query.JiFenQuery;
import com.yada.wechatbank.service.JifenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.shiro.ValidateTime;
import com.yada.wechatbank.util.JsMapUtil;

/**
 * 积分明细Controller
 * 
 * @author zm
 * 
 */
@Controller
@RequestMapping(value = "jifen")
public class JifenController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(JifenController.class);
	private static final String LISTURL = "wechatbank_pages/JiFen/list";
	private static final String VALIDATEURL = "wechatbank_pages/JiFen/validate";
	public static final String ERROR = "wechatbank_pages/error";

	@Autowired
	private JifenService jifenServiceImpl;
	@Autowired
	private ValidateTime validateTime;

	/**
	 * 积分明细列表
	 * 
	 * @param jiFenQuery 积分查询实体
	 * @param request HttpServletRequest
	 * @param model Model
	 */
	@RequestMapping(value = "list")
	public String list(@ModelAttribute("formBean") JiFenQuery jiFenQuery, HttpServletRequest request, Model model) {
		// 页面分享js需要的参数
		Map<String, String> jsMap = JsMapUtil.getJsMapConfig(request,
				"jifen/list.do","中国银行信用卡积分查询");
		if (jsMap == null) {
			return ERROR;
		}
		for (String key : jsMap.keySet()) {
			model.addAttribute(key, jsMap.get(key));
		}
		// 调用RMI 获取积分明细列表
		List<JifenDetail> jiFenDetailList = jifenServiceImpl.getJifenDetail(getIdentityNo(request),getIdentityType(request));
		List<List<JifenDetail>> newList = new ArrayList<List<JifenDetail>>();
		// RMI返回值为空或没有数据
		if (jiFenDetailList == null) {
			return BUSYURL;
		} else if (jiFenDetailList.size() > 0 && jiFenDetailList.get(0).getCardNo() != null && jiFenDetailList.get(0).getId() != null) {
			newList = jifenServiceImpl.getList(jiFenDetailList);
		}
		model.addAttribute("newList", newList);
		model.addAttribute("model", jiFenQuery);
		return LISTURL;
	}

	/**
	 * 积分到期日
	 * 
	 * @param jiFenQuery 积分查询实体
	 * @param request HttpServletRequest
	 * @param model Model
	 */
	@RequestMapping(value = "validate")
	public String validate(@ModelAttribute("formBean") JiFenQuery jiFenQuery, HttpServletRequest request, Model model) {
		// 页面分享js需要的参数
		Map<String, String> jsMap = JsMapUtil.getJsMapConfig(request,
				"jifen/list.do","中国银行信用卡积分查询");
		if (jsMap == null) {
			return ERROR;
		}
		for (String key : jsMap.keySet()) {
			model.addAttribute(key, jsMap.get(key));
		}
		Integer numberP = Integer.parseInt(request.getParameter("numberP"));
		Integer number = Integer.parseInt(request.getParameter("number"));
		// 调用RMI 获取积分明细列表
		List<JifenDetail> jiFenDetailList = jifenServiceImpl.getJifenDetail(getIdentityNo(request),getIdentityType(request));
		List<List<JifenDetail>> newList = new ArrayList<List<JifenDetail>>();
		// RMI返回值为空或没有数据
		if (jiFenDetailList == null) {
			return BUSYURL;
		} else if (jiFenDetailList.size() > 0 && jiFenDetailList.get(0).getCardNo() != null && jiFenDetailList.get(0).getId() != null) {
			newList = jifenServiceImpl.getList(jiFenDetailList);
		}
		JifenDetail jifenDetail = newList.get(numberP).get(number);
		// 调用RMI 获取积分到期日明细列表
		List<JifenValidate> jiFenValidateList = jifenServiceImpl.getJifenValidates(jifenDetail.getCardNo());
		// RMI返回值为空或没有数据
		if (jiFenValidateList == null) {
			return BUSYURL;
		}
		model.addAttribute("jiFenValidateList", jiFenValidateList);
		return VALIDATEURL;
	}

}
