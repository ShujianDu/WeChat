package com.yada.wechatbank.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yada.wechatbank.base.BaseController;
import com.yada.wechatbank.service.BookingService;
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
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 信用卡预约申请Controller
 * 
 * @author zm
 * 
 */
@Controller
@RequestMapping(value = "booking")
public class BookingController extends BaseController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String LISTURL="wechatbank_pages/Booking/list";
	private static final String ADDRESS="wechatbank_pages/Booking/address";
	private static final String SUCCESSURL="wechatbank_pages/Booking/success";
	private static final String ERRORURL="wechatbank_pages/Booking/error";
	@Autowired
	private BookingService bookingServiceImpl;
	/**
	 * 进入预约办卡信息填写界面
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
	 */
	@RequestMapping(value = "listP")
	public String listP(
			@ModelAttribute("formBean") BookingQuery bookingQuery) {
		Calendar cal = Calendar.getInstance();
		String dateStr = new SimpleDateFormat("yyyyMMdd").format(cal.getTime());
		String timeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(cal
				.getTime());
		String num = bookingServiceImpl.getSequences();
		if(num==null){
			return ERRORURL;
		}
		String numStr = String.format("%04d", Integer.parseInt(num));
		Booking booking = new Booking();
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
		boolean insertBookingRet =  bookingServiceImpl.insertBooking(booking);
		if (!insertBookingRet) {
			return ERRORURL;
		}
		return SUCCESSURL;
	}

	/**
	 * 进入省市区选择界面
	 *
	 */
	@RequestMapping(value = "address")
	public String address(
			@ModelAttribute("formBean") BookingQuery bookingQuery,
			HttpServletRequest request, Model model) {
		// 页面分享js需要的参数
//		Map<String, String> jsMap = JsMapUtil.getJsMapConfig(request,
//				"booking/address.do","中国银行信用卡预约申请");
//		if (jsMap == null) {
//			return ERROR;
//		}
//		for (String key : jsMap.keySet()) {
//			model.addAttribute(key, jsMap.get(key));
//		}
		// 获取省份集合
		List<NuwOrg> provinceList = bookingServiceImpl.selectNumOrgList("");
		if(provinceList==null){
			return ERROR;
		}
		if (request.getParameter("provId") != null
				&& !"".equals(request.getParameter("provId"))) {
			String provIdstr=bookingQuery.getProvId();
			String provId = provIdstr.substring(0, provIdstr.indexOf("^"));
			List<NuwOrg> cityList = bookingServiceImpl.selectNumOrgList(provId);
			String cityIdstr=bookingQuery.getCityId();
			String cityId = cityIdstr.substring(0, cityIdstr.indexOf("^"));
			List<NuwOrg> areaList = bookingServiceImpl.selectNumOrgList(cityId);
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
	 */
	@RequestMapping(value = "getOrg_ajax")
	@ResponseBody
	public String getOrg_ajax(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String result = "";
		String pOrgIdstr = request.getParameter("pOrgId");
		String pOrgId = pOrgIdstr.substring(0, pOrgIdstr.indexOf("^"));
		List<NuwOrg> list = bookingServiceImpl.selectNumOrgList(pOrgId);
		if (list == null || list.size() == 0) {
			result = "exception";
		} else {
			result = JSONArray.fromObject(list).toString();
		}
		return result;
	}

	/**
	 * 判断是否已经预约
     */
	@RequestMapping(value = "isBooking_ajax")
	@ResponseBody
	public String isBooking_ajax(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String result = "";
		String bookingResult=bookingServiceImpl.isHaveBooking(request.getParameter("clientName"),request.getParameter("mobilePhone"));
		if(bookingResult==null){
			result = "exception";
		}else if ("true".equals(bookingResult)) {
			result = "true";
		}
		return result;
	}
}
