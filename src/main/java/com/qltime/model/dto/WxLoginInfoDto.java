package com.qltime.model.dto;
import java.io.Serializable;

public class WxLoginInfoDto implements Serializable {

    private static final long serialVersionUID = -4112017093673173808L;

    private String code;
    private String appid;
    private String sessionKey;
    private String signature;
    private String rawData;
    private String encryptedData;
    private String iv;
    private String openId;
    private String phone;
    private String sceneCode;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSceneCode() {
        return sceneCode;
    }

    public void setSceneCode(String sceneCode) {
        this.sceneCode = sceneCode;
    }

    @Override
    public String toString() {
        return "WxLoginInfoDto{" +
                "code='" + code + '\'' +
                ", appid='" + appid + '\'' +
                ", sessionKey='" + sessionKey + '\'' +
                ", signature='" + signature + '\'' +
                ", rawData='" + rawData + '\'' +
                ", encryptedData='" + encryptedData + '\'' +
                ", iv='" + iv + '\'' +
                ", openId='" + openId + '\'' +
                ", phone='" + phone + '\'' +
                ", sceneCode='" + sceneCode + '\'' +
                '}';
    }
}
