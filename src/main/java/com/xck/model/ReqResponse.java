package com.xck.model;

public class ReqResponse {

    private String desc;
    private int errCode;

    public ReqResponse(String desc, int errCode) {
        this.desc = desc;
        this.errCode = errCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
