package com.yada.wechatweb.base;

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
     * 验证字符串不能为空和双引
     * @param params 参数集合
     * @return true/false
     */
    public Boolean verificationString(String... params)
    {
        for(String param:params)
        {
            if(param==null||"".equals(param))
            {
                return true;
            }
        }
        return false;
    }

}
