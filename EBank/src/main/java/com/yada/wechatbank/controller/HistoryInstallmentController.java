package com.yada.wechatbank.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yada.wechatbank.model.HistoryInstallment;
import com.yada.wechatbank.service.HistoryInstallmentService;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.util.Crypt;
import com.yada.wechatbank.util.JsMapUtil;

/**
 * 历史分期付款查询
 *
 * @author zm
 *
 */
@Controller
@RequestMapping(value = "historyInstalment")
public class HistoryInstallmentController extends BaseController {
	private final static Logger logger = LoggerFactory
			.getLogger(HistoryInstallmentController.class);
	private static final String LISTURL = "wechatbank_pages/HistoryInstallment/list";
	private static final String SHOWURL = "wechatbank_pages/HistoryInstallment/show";
	private static String JSERROR = "wechatbank_pages/error";
	private static final String STARTNUM = "1";
	private static final String SELECTNUM = "100";
	private static final int ONEPAGE = 10;

	@Autowired
	private HistoryInstallmentService historyInstallmentServiceImpl;

	@RequestMapping(value = "list")
	public String list(HttpServletRequest request, String openId, Model model) {
		// 页面分享js需要的参数
//		Map<String, String> jsMap = JsMapUtil.getJsMapConfig(request,
//				"billinstallment/list.do","中国银行信用卡分期业务");
//		if (jsMap == null) {
//			return JSERROR;
//		}
//		for (String key : jsMap.keySet()) {
//			model.addAttribute(key, jsMap.get(key));
//		}
		// 调用RMI 获取卡片列表（OpenId）
		List<String> cardList = historyInstallmentServiceImpl.selectCardNOs(getIdentityNo(request),getIdentityType(request));
		// RMI返回值为空或没有数据
		if (cardList == null) {
			return BUSYURL;
		} else if (cardList.size() == 0) {
			return NOCARDURL;
		}
		model.addAttribute("cardList", cardList);
		model.addAttribute("openId", openId);
		return LISTURL;
	}

	@RequestMapping(value = "listP")
	public String listP(HttpServletRequest request, String openId,
			String cardNo, Model model) {
		// 页面分享js需要的参数
		Map<String, String> jsMap = JsMapUtil.getJsMapConfig(request,
				"billinstallment/list.do","中国银行信用卡分期业务");
		if (jsMap == null) {
			return JSERROR;
		}
		for (String key : jsMap.keySet()) {
			model.addAttribute(key, jsMap.get(key));
		}
		//调用后台获取卡列表
		List<String> cardList  = historyInstallmentServiceImpl.selectCardNOs(getIdentityNo(request),getIdentityType(request));
		model.addAttribute("cardList", cardList);
		// 调用RMI查询历史分期付款
		Map<String, Object> map = null;
		try {
			map = historyInstallmentServiceImpl.queryHistoryInstallment(Crypt.decode(cardNo), STARTNUM,
					SELECTNUM);
		} catch (Exception e) {

		}
		if (map == null || map.get("isFollowUp") == null
				|| map.get("list") == null) {
			return BUSYURL;
		}
		List<HistoryInstallment> list = (List<HistoryInstallment>) map.get("list");
		List<HistoryInstallment> historyInstallmentList = new ArrayList<HistoryInstallment>();
		// RMI返回值为空或没有数据
		if (list == null || list.size()==0) {
			return BUSYURL;
		} else if (list.size() > 0) {
			for (int i = 0; i < ONEPAGE; i++) {
				historyInstallmentList.add(list.get(i));
			}
		}
		model.addAttribute("pageList", historyInstallmentList);
		model.addAttribute("cardNo", cardNo);
		model.addAttribute("openId", openId);
		return LISTURL;
	}

	@RequestMapping(value = "show")
	public String show(HttpServletRequest request,
			String number, String cardNo,Model model) {
		// 页面分享js需要的参数
		Map<String, String> jsMap = JsMapUtil.getJsMapConfig(request,
				"billinstallment/list.do","中国银行信用卡分期业务");
		if (jsMap == null) {
			return JSERROR;
		}
		for (String key : jsMap.keySet()) {
			model.addAttribute(key, jsMap.get(key));
		}
		int index = Integer.parseInt(number);
		List<HistoryInstallment> list = new ArrayList<HistoryInstallment>();
		HistoryInstallment historyInstallment = null;
		//调用后台查询分期历史
		Map<String, Object> map = historyInstallmentServiceImpl.queryHistoryInstallment(cardNo, index
				+ "", "1");
		if (map == null || map.get("isFollowUp") == null
				|| map.get("list") == null) {
			return JSERROR;
		}else {
			list = (List<HistoryInstallment>) map.get("list");
			if (list==null || list.size()==0){
				return JSERROR;
			}else {
				historyInstallment = list.get(0);
			}
		}
		model.addAttribute("model", historyInstallment);
		return SHOWURL;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "getMore_ajax")
	public void ajax_getMore(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//返回结果
		String result = "";
		List<HistoryInstallment> historyInstallmentList = new ArrayList<HistoryInstallment>();
		List<HistoryInstallment> list = new ArrayList<HistoryInstallment>();
		String cardNo = "";
		try {
			cardNo = Crypt.decode(request.getParameter("cardNo"));
		} catch (Exception e) {
			logger.info("@WDZD@cardNo crypt error,cardNo[" + cardNo + "]:" + e);
		}
		//TODO 获取当前应该从第几个开始查
		Integer index = null;
		//调用后台查询分期历史
		Map<String, Object> map = historyInstallmentServiceImpl.queryHistoryInstallment(cardNo, index
				+ "", SELECTNUM);
		if (map == null || map.get("isFollowUp") == null
				|| map.get("list") == null) {
			result = "exception";
		} else {
			list = (List<HistoryInstallment>) map.get("list");
			if (list==null || list.size() == 0) {
				result = "null";
			} else if (list.size() > 0) {
				for (int i = index; i < index
						+ ONEPAGE; i++) {
					historyInstallmentList.add(list.get(i));
				}
				result = JSONArray.fromObject(
						historyInstallmentList).toString();
			}
		}
		response.getWriter().print(result);
		response.getWriter().flush();

	}
}
