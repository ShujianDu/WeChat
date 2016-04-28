package com.yada.wechatbank.base;

import javax.servlet.http.HttpServletRequest;

import com.yada.wechatbank.util.IdTypeUtil;
import org.springframework.stereotype.Controller;

/**
 * controller公共元素
 *
 * @author liangtieluan
 */
@Controller
public class BaseController {
    // 权限过期跳转URL
    public static final String TIMEOUTURL = "wechatbank_pages/timeOut";
    // 数据查询异常跳转URL
    public static final String BUSYURL = "wechatbank_pages/busy";
    // 无信用卡信息跳转URL
    public static final String NOCARDURL = "wechatbank_pages/nocard";
    // 错误页面
    public static final String ERROR = "wechatbank_pages/error";

    /**
     * 从session中获取证件号
     *
     * @param request HttpServletRequest
     * @return
     */
    public String getIdentityNo(HttpServletRequest request) {
        String identityNo = (String) request.getSession().getAttribute("identityNo");
        return identityNo;
    }

    /**
     * 从session中获取证件类型
     *
     * @param request HttpServletRequest
     * @return String
     */
    public String getIdentityType(HttpServletRequest request) {
        String identityType = (String) request.getSession().getAttribute("identityType");
        return identityType;
    }

    /**
     * 获得GCS的证件类型
     * @param request HttpServletRequest
     * @return String
     */
    public String getGcsIdentityType(HttpServletRequest request) {
        String identityType = getIdentityType(request);
        return IdTypeUtil.numIdTypeTransformToECode(identityType);
    }

}
