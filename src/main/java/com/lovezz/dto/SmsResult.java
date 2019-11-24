package com.lovezz.dto;

/**
 * @Auther: liweijian
 * @Date: 2019/11/24 16:26
 * @Description:
 */
public class SmsResult {


    /**
     * result : 0
     * errmsg : OK
     * ext :
     * sid : 2056:285899859615745837318422926
     * fee : 2
     */

    private int result;
    private String errmsg;
    private String ext;
    private String sid;
    private int fee;



    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}
