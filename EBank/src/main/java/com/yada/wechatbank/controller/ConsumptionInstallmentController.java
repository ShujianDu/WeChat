package com.yada.wechatbank.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.model.ConsumptionInstallments;
import com.yada.wechatbank.service.ConsumptionInstallmentService;
import com.yada.wechatbank.util.Crypt;

/**
 * 消费分期
 *
 * @author liangtieluan
 */
@Controller
@RequestMapping(value = "consumptionInstallment")
public class ConsumptionInstallmentController extends BaseController {
	@Autowired
	private ConsumptionInstallmentService consumptionInstallmentServiceImpl;
	private final Logger logger = LoggerFactory.getLogger(ConsumptionInstallmentController.class);
	private static final String LISTURL = "wechatbank_pages/ConsumptionInstallment/list";
	private static final String SHOWURL = "wechatbank_pages/ConsumptionInstallment/show";
	private static final String COSTURL = "wechatbank_pages/ConsumptionInstallment/cost";
	private static final String SUCCESS = "wechatbank_pages/ConsumptionInstallment/success";
	private static final String FAIL = "wechatbank_pages/ConsumptionInstallment/fail";

	private static final String STARTNUM = "1";
	private static final String SELECTNUM = "10";
	private static final int ONEPAGE = 10;

	/**
	 * 跳转到选择卡号页面
	 *
	 * @param request
	 *            request
	 * @param model
	 *            model
	 * @return 跳转地址
	 */
	@RequestMapping(value = "list")
	public String list(HttpServletRequest request, Model model) {
		String identityNo = getIdentityNo(request);
		String identityType = getIdentityType(request);
		// 获取加密后的卡列表，传至页面用
		List<String> cardList = consumptionInstallmentServiceImpl.selectCardNoList(identityType, identityNo);
		if (cardList == null) {
			return BUSYURL;
		} else if (cardList.size() == 0) {
			return NOCARDURL;
		} else {
			try {
				Crypt.cardNoCrypt(cardList);
			} catch (Exception e) {
				e.printStackTrace();
				return BUSYURL;
			}
			model.addAttribute("cardList", cardList);
			return LISTURL;
		}
	}

	/**
	 * 查询能够分期的消费信息
	 *
	 * @param request
	 *            request
	 * @param cardNo
	 *            卡号
	 * @param currencyCode
	 *            币种
	 * @param model
	 *            model
	 * @return 跳转地址
	 */
	@RequestMapping(value = "listP")
	public String listP(HttpServletRequest request, String cardNo, String currencyCode, Model model) {
		String identityNo = getIdentityNo(request);
		String identityType = getIdentityType(request);
		// 获取加密后的卡列表，传至页面用
		List<String> cardList = consumptionInstallmentServiceImpl.selectCardNoList(identityType, identityNo);
		if (cardList == null) {
			return BUSYURL;
		} else if (cardList.size() == 0) {
			return NOCARDURL;
		} else {
			try {
				Crypt.cardNoCrypt(cardList);
			} catch (Exception e) {
				e.printStackTrace();
				return BUSYURL;
			}
			model.addAttribute("cardList", cardList);
			try {
				cardNo = Crypt.decode(cardNo);
			} catch (Exception e) {
				logger.info("@XFFQ@解密卡号出现异常" + e);
				return BUSYURL;
			}
		}
		// 获取可分期消费信息集合
		Map<String, Object> map = consumptionInstallmentServiceImpl.queryConsumptionInstallments(cardNo, currencyCode, STARTNUM, SELECTNUM);
		logger.info("@XFFQ@调用核心根据cardNo[" + cardNo + "],currencyCode[" + currencyCode + ",STARTNUM[" + STARTNUM + "]SELECTNUM[" + SELECTNUM
				+ "]获取可分期交易，获取到的交易列表map[" + map + "]");
		if (map == null || map.get("isFollowUp") == null || map.get("consumptionInstallmentsList") == null) {
			return BUSYURL;
		}
		String isFollowUp = (String) map.get("isFollowUp");
		@SuppressWarnings("unchecked")
		List<ConsumptionInstallments> consumptionInstallmentsList = (List<ConsumptionInstallments>) map.get("consumptionInstallmentsList");
		try {
			cardNo = Crypt.encode(cardNo);
		} catch (Exception e) {
			logger.info("@XFFQ@加密卡号出现异常" + e);
			return BUSYURL;
		}
		// 页面判断是否有下一页
		model.addAttribute("isFollowUp", isFollowUp);
		model.addAttribute("pageList", consumptionInstallmentsList);
		// 获取更多查询开始条数
		model.addAttribute("startnum", Integer.parseInt(SELECTNUM) + 1);
		model.addAttribute("cardNo", cardNo);
		model.addAttribute("onepage", ONEPAGE);
		return LISTURL;
	}
}
