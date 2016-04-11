package com.yada.wechatbank.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.yada.wechatbank.service.BookingManager;
import com.yada.wx.db.service.model.Booking;
import com.yada.wx.db.service.model.NuwOrg;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yada.wechatbank.query.BookingQuery;
import com.yada.wechatbank.util.JsMapUtil;

/**
 * 信用卡预约申请Controller
 * 
 * @author zm
 * 
 */
@Controller
@RequestMapping(value = "booking")
public class BookingController {
	private final static Logger logger = LoggerFactory.getLogger(BookingController.class);
	private static final String LISTURL="wechatbank_pages/Booking/list";
	private static final String ADDRESS="wechatbank_pages/Booking/address";
	private static final String SUCCESSURL="wechatbank_pages/Booking/success";
	private static final String ERRORURL="wechatbank_pages/Booking/error";
	private static String ERROR = "wechatbank_pages/error";
	public static final String ALLERRORURL = "wechatbank_pages/error";
	@Autowired
	private BookingManager bookingManager;
	/**
	 * 进入预约办卡信息填写界面
	 * 
	 * @param bookingQuery
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list")
	public String list(
			@ModelAttribute("formBean") BookingQuery bookingQuery,
			HttpServletRequest request, Model model) {
		String openId = (String) request.getAttribute("openId");
//		// 页面分享js需要的参数
//		Map<String, String> jsMap = JsMapUtil.getJsMapConfig(request,
//				"booking/list.do","中国银行信用卡预约申请");
//		if (jsMap == null) {
//			return ERROR;
//		}
//		for (String key : jsMap.keySet()) {
//			model.addAttribute(key, jsMap.get(key));
//		}
		model.addAttribute("openId",openId);
		return LISTURL;
	}

	/**
	 * 确认提交预约办卡信息
	 * 
	 * @param bookingQuery
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "listP")
	public String listP(
			@ModelAttribute("formBean") BookingQuery bookingQuery,
			HttpServletRequest request, Model model) {
		Calendar cal = Calendar.getInstance();
		String dateStr = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		String timeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(cal
				.getTime());
		String num = bookingManager.getSequences();
		logger.info("@WYBK@调用核心获取客户ID的Sequences，获取到的Sequences["+num+"]");
		if(num==null){
			return ERRORURL;
		}
		//此处后期放入service里
		String numStr = String.format("%04d", Integer.parseInt(num));
		Booking booking = new Booking();
		//booking.setBookingId("");
		booking.setClientId("WC" + timeStr + numStr);
		booking.setClientName(bookingQuery.getClientName());
		booking.setProvId(bookingQuery.getProvId().substring(0,
				bookingQuery.getProvId().indexOf("^")));
		booking.setCityId(bookingQuery.getCityId().substring(0,
				bookingQuery.getCityId().indexOf("^")));
		booking.setAreaId(bookingQuery.getAreaId().substring(0,
				bookingQuery.getAreaId().indexOf("^")));
		booking.setPhone(bookingQuery.getAreaCode()
				+ bookingQuery.getPhoneNum());
		booking.setMobilePhone(bookingQuery.getMobilePhone());
		booking.setServiceAddr(bookingQuery.getServiceAddr());
		booking.setApplyDt(dateStr);
		boolean insertBookingRet =  bookingManager.insertBooking(booking);
		if (!insertBookingRet) {
			return ERRORURL;
		}
		return SUCCESSURL;
	}

	/**
	 * 进入省市区选择界面
	 * 
	 * @param bookingQuery
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "address")
	public String address(
			@ModelAttribute("formBean") BookingQuery bookingQuery,
			HttpServletRequest request, Model model) {
		String openId = (String) request.getAttribute("openId");
		// 页面分享js需要的参数
//		Map<String, String> jsMap = JsMapUtil.getJsMapConfig(request,
//				"booking/address.do","中国银行信用卡预约申请");
//		if (jsMap == null) {
//			return ERROR;
//		}
//		for (String key : jsMap.keySet()) {
//			model.addAttribute(key, jsMap.get(key));
//		}
		model.addAttribute("openId",openId);
		// 获取省份集合
		List<NuwOrg> provinceList = bookingManager.selectNumOrgList("");
		if(provinceList==null){
			return ALLERRORURL;
		}
		if (request.getParameter("provId") != null
				&& !"".equals(request.getParameter("provId"))) {
			String provIdstr=bookingQuery.getProvId();
			String provId = provIdstr.substring(0, provIdstr.indexOf("^"));
			List<NuwOrg> cityList = bookingManager.selectNumOrgList(provId);
			String cityIdstr=bookingQuery.getCityId();
			String cityId = cityIdstr.substring(0, cityIdstr.indexOf("^"));
			List<NuwOrg> areaList = bookingManager.selectNumOrgList(cityId);
			model.addAttribute("model", bookingQuery);
			model.addAttribute("cityList", cityList);
			model.addAttribute("areaList", areaList);
		}
		model.addAttribute("model", bookingQuery);
		model.addAttribute("provinceList", provinceList);
		return ADDRESS;
	}

	/**
	 * 确认选择的省市区
	 * 
	 * @param bookingQuery
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "addressP")
	public String addressP(
			@ModelAttribute("formBean") BookingQuery bookingQuery,
			HttpServletRequest request, Model model) {
		String openId = (String) request.getSession().getAttribute("openId");
		// 页面分享js需要的参数
//		Map<String, String> jsMap = JsMapUtil.getJsMapConfig(request,
//				"booking/list.do","中国银行信用卡预约申请");
//		if (jsMap == null) {
//			return ERROR;
//		}
//		for (String key : jsMap.keySet()) {
//			model.addAttribute(key, jsMap.get(key));
//		}
		model.addAttribute("openId",openId);
		StringBuilder areaStr = new StringBuilder();
		areaStr.append(
				bookingQuery.getProvId().substring(
						bookingQuery.getProvId().indexOf("^") + 1)).append(
				"/");
		areaStr.append(
				bookingQuery.getCityId().substring(
						bookingQuery.getCityId().indexOf("^") + 1)).append(
				"/");
		areaStr.append(bookingQuery.getAreaId().substring(
				bookingQuery.getAreaId().indexOf("^") + 1));
		model.addAttribute("model", bookingQuery);
		model.addAttribute("areaStr", areaStr);
		return LISTURL;
	}

	/**
	 * 获取城市列表AJAX
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "getOrg_ajax")
	public void getOrg_ajax(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String result = "";
		String pOrgIdstr = request.getParameter("pOrgId");
		String pOrgId = pOrgIdstr.substring(0, pOrgIdstr.indexOf("^"));
		List<NuwOrg> list = new ArrayList<NuwOrg>();
		list = bookingManager.selectNumOrgList(pOrgId);
		logger.debug("@WYBK@select num org list["+list+"]");
		if (list == null || list.size() == 0) {
			result = "exception";
		} else {
			result = JSONArray.fromObject(list).toString();
		}
		response.getWriter().print(result);
		response.getWriter().flush();
	}

	/**
	 * 判断用户输入的信息是否已存在
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "isBooking_ajax")
	public void isBooking_ajax(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String result = "";
		String bookingResult=bookingManager.isHaveBooking(request.getParameter("clientName"),request.getParameter("mobilePhone"));
		logger.info("@WYBK@调用核心根据clientName["+request.getParameter("clientName")+"],mobilePhone["+request.getParameter("mobilePhone")+"]");
		if(bookingResult==null){
			logger.debug("@WYBK@预约办卡失败mobilePhone["+request.getParameter("mobilePhone")+"]");
			result = "exception";
		}else if ("true".equals(bookingResult)) {
			result = "true";
		}
		response.getWriter().print(result);
		response.getWriter().flush();
	}
}
