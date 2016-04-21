package com.yada.wechatbank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 附近中行网点
 * 
 * @author liangtieluan
 *
 */
@Controller
@RequestMapping(value = "chinabankinfo")
public class ChinabankInfoController {
	@RequestMapping(value = "baiduMap")
	public String baiduMap(Model model, String longitude, String latitude, String userLongitude, String userLatitude, String name) {
		model.addAttribute("longitude", longitude);
		model.addAttribute("latitude", latitude);
		model.addAttribute("userLongitude", userLongitude);
		model.addAttribute("userLatitude", userLatitude);
		model.addAttribute("name", name);
		return "wechatbank_pages/ChinabankInfo/baiduMap";
	}
}
