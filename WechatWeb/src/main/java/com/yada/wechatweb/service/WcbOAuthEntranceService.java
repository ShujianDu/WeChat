package com.yada.wechatweb.service;

import com.yada.wx.db.service.dao.AuthInfoDao;
import com.yada.wx.db.service.model.AuthInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 微信AUTH2.0授权service
 */
@Service
@Transactional
public class WcbOAuthEntranceService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //随机数长度
    private final int randomLength = 8;

    @Autowired
    private AuthInfoDao authInfoDao;

    public String saveAuthCode(String openId) {
        String createDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String code = generateRandomCode(randomLength);
        AuthInfo authInfo = new AuthInfo();
        authInfo.setAuthCode(code);
        authInfo.setCreatTime(createDate);
        authInfo.setOpenId(openId);
        logger.info("保存authInfo[{}]数据",authInfo.toString());
        authInfoDao.saveAndFlush(authInfo);
        return code;
    }

    /**
     * 随机数生成
     *
     * @param length
     * @return 数字字母组合随机数
     */
    private String generateRandomCode(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

}
