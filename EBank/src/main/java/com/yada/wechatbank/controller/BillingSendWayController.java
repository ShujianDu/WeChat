package com.yada.wechatbank.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yada.wechatbank.model.BillSendType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.service.BillingSendWayService;
import com.yada.wechatbank.util.Crypt;

/**
 * 账单寄送方式
 * 
 * @author zwq
 */
@Controller
@RequestMapping(value = "billingsendway")
public class BillingSendWayController extends BaseController {
	private final Logger logger = LoggerFactory
			.getLogger(this.getClass());
	private static final String LISTURL = "wechatbank_pages/BillingSendWay/list";
	private static final String EDITURL = "wechatbank_pages/BillingSendWay/edit";

	@Autowired
	private BillingSendWayService billingSendWayServiceImpl;

	/**
	 * 寄送方式查询
	 *
	 * @param model 页面model
	 */
	@RequestMapping(value = "list")
	public String list(Model model) {
		String identityType = "";
		String identityNo = "";
		List<BillSendType> list = billingSendWayServiceImpl.getBillSendType(identityType, identityNo);
		logger.debug("@ZDJSFSCX@通过identityType[{}],identityNo[{}]获取账单寄送方式集合为[{}]", identityType, identityNo, list);
		if (list == null) {
			logger.warn("@ZDJSFSCX@通过identityType[{}],identityNo[{}]获取寄送方式集合为空或没有数据", identityType, identityNo);
			return BUSYURL;
		} else {
			model.addAttribute("sendTypeList", list);
		}
		return LISTURL;
	}

	/**
	 * 进入寄送方式编辑页面
	 * @param billSendType  账单寄送方式代码
	 * @param cardNo        卡号
	 * @param model         页面model
	 */
	@RequestMapping(value = "edit")
	public String edit(Model model,String cardNo,String billSendType) {
		 model.addAttribute("cardNo", cardNo);
		 model.addAttribute("billSendType", billSendType);
		return EDITURL;
	}

	/**
	 * 寄送方式编辑保存
	 * @param request  请求
	 * @param response 响应
	 */
	@RequestMapping(value = "update")
	public void update(HttpServletRequest request,
			HttpServletResponse response) {
		String cardNo = "";
		try {
			cardNo = Crypt.decode(request.getParameter("cardNo"));
		} catch (Exception e) {
			logger.error("@WDZD@解密卡号出现异常cardNo[" + cardNo + "]" ,e);
		}
		String billSendType = request.getParameter("billSendType");
		try {
			if (cardNo != null && !"".equals(cardNo) && billSendType != null
					&& !"".equals(billSendType)) {
				boolean result = billingSendWayServiceImpl.updateBillSendType(cardNo, billSendType);
				logger.info("@ZDJSFSXG@调用核心根据cardNo[" + cardNo
						+ "]修改账单寄送方式，账单寄送方式修改结果result[" + result + "]");
				response.getWriter().write("修改成功");
			}
		} catch (IOException e) {
			logger.error("@ZDJSFSXG@获取到IOException，异常信息：" + e.getMessage());
			try {
				response.getWriter().write("修改失败");
			} catch (IOException e1) {
				logger.error("@ZDJSFSXG@获取到IOException，异常信息：" + e.getMessage());
			}
		}
	}

}
