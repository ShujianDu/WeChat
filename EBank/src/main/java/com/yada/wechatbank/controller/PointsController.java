package com.yada.wechatbank.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.yada.wechatbank.model.PointsDetail;
import com.yada.wechatbank.model.PointsValidates;
import com.yada.wechatbank.query.PointsQuery;
import com.yada.wechatbank.service.PointsService;
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
@RequestMapping(value = "points")
public class PointsController extends BaseController {
	private final static Logger logger = LoggerFactory.getLogger(PointsController.class);
	private static final String LISTURL = "wechatbank_pages/JiFen/list";
	private static final String VALIDATEURL = "wechatbank_pages/JiFen/validate";
	public static final String ERROR = "wechatbank_pages/error";

	@Autowired
	private PointsService pointsServiceImpl;
	@Autowired
	private ValidateTime validateTime;

	/**
	 * 积分明细列表
	 * 
	 * @param pointsQuery 积分查询实体
	 * @param request HttpServletRequest
	 * @param model Model
	 */
	@RequestMapping(value = "list")
	public String list(@ModelAttribute("formBean") PointsQuery pointsQuery, HttpServletRequest request, Model model) {
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
		List<PointsDetail> pointsDetailList = pointsServiceImpl.getPointsDetail(getIdentityNo(request),getIdentityType(request));
		List<List<PointsDetail>> newList = new ArrayList<>();
		// RMI返回值为空或没有数据
		if (pointsDetailList == null || pointsDetailList.size()==0) {
			return BUSYURL;
		} else if (pointsDetailList.size() > 0 && pointsDetailList.get(0).getCardNo() != null && pointsDetailList.get(0).getId() != null) {
			newList = pointsServiceImpl.getList(pointsDetailList);
		}
		model.addAttribute("newList", newList);
		model.addAttribute("model", pointsQuery);
		return LISTURL;
	}

	/**
	 * 积分到期日
	 * 
	 * @param pointsQuery 积分查询实体
	 * @param request HttpServletRequest
	 * @param model Model
	 */
	@RequestMapping(value = "validate")
	public String validate(@ModelAttribute("formBean") PointsQuery pointsQuery, HttpServletRequest request, Model model) {
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
		List<PointsDetail> pointsDetailList = pointsServiceImpl.getPointsDetail(getIdentityNo(request),getIdentityType(request));
		List<List<PointsDetail>> newList = new ArrayList<>();
		// RMI返回值为空或没有数据
		if (pointsDetailList == null) {
			return BUSYURL;
		} else if (pointsDetailList.size() > 0 && pointsDetailList.get(0).getCardNo() != null && pointsDetailList.get(0).getId() != null) {
			newList = pointsServiceImpl.getList(pointsDetailList);
		}
		PointsDetail pointsDetail = newList.get(numberP).get(number);
		// 调用RMI 获取积分到期日明细列表
		List<PointsValidates> pointsValidatesList = pointsServiceImpl.getPointsValidates(pointsDetail.getCardNo());
		// RMI返回值为空或没有数据
		if (pointsValidatesList == null) {
			return BUSYURL;
		}
		model.addAttribute("pointsValidatesList", pointsValidatesList);
		return VALIDATEURL;
	}

}
