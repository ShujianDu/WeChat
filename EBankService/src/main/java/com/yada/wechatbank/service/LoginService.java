package com.yada.wechatbank.service;

/**
 * Created by echo on 16/5/5.
 */
public interface LoginService {
    String getMobileNo(String idType,String idNum);

    boolean isLocked(String idType,String idNum);


}
