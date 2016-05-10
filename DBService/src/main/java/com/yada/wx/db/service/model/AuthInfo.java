package com.yada.wx.db.service.model;

import javax.persistence.*;


/**
 * 授权外部使用openId进行免登陆
 * @author Tx
 */
@Entity(name = "T_B_AUTH_INFO")
public class AuthInfo {

    @Id
    @GeneratedValue(generator = "T_B_AUTHINFO_SEQ")
    @SequenceGenerator(name = "T_B_AUTHINFO_SEQ", sequenceName = "SEQ_T_B_AUTH_INFO")
    @Column(name = "ID", columnDefinition = "VARCHAR2(32)", nullable = false)
    private Long id; // ID

    @Column(name = "OPENID", columnDefinition = "VARCHAR2(32)")
    private String openId; // OPENID

    @Column(name = "CREAT_TIME", columnDefinition = "VARCHAR2(16 CHAR)")
    private String creatTime; // 创建时间

    @Column(name = "AUTH_CODE", columnDefinition = "VARCHAR2(8)")
    private String authCode; // 授权码

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

}
