package com.xck.model.req;

public class ReqResponse {

    public static final int NO_ILLEGAL_PATH = 100;
    public static final int NO_DIR = 101;
    public static final int NO_FILE = 102;
    public static final int UPLOAD_FAIL = 103;
    public static final int DIR_OPERATE_ERROR = 104;
    public static final int LOGIN_FAIL = 105;
    public static final int TOKEN_ERR = 106;
    public static final int REQ_PARAM_ERR = 107;
    public static final int OPER_ERR = 108;

    /* 错误描述 */
    private String desc;
    /* 错误码 */
    private int errCode;
    /* 数据 */
    private Object data;

    public static ReqResponse error(int errCode) {
        return error(errCode, null);
    }

    public static ReqResponse paramError(String addMsg) {
        return error(REQ_PARAM_ERR, addMsg);
    }

    public static ReqResponse error(int errCode, String addMsg) {
        ReqResponse reqResponse = new ReqResponse();
        String desc = "未知错误";
        reqResponse.setErrCode(errCode);
        switch (errCode) {
            case NO_ILLEGAL_PATH:desc = "非法访问路径";break;
            case NO_DIR:desc = "非目录";break;
            case NO_FILE:desc = "非文件";break;
            case UPLOAD_FAIL:desc = "上传失败";break;
            case DIR_OPERATE_ERROR:desc = "操作文件失败";break;
            case LOGIN_FAIL:desc = "登录失败";break;
            case TOKEN_ERR:desc = "token鉴权失败";break;
            case REQ_PARAM_ERR:desc = "请求参数不合法";break;
            case OPER_ERR:desc = "操作失败";break;
        }

        if (addMsg != null) {
            desc += (", " + addMsg);
        }
        reqResponse.setDesc(desc);
        return reqResponse;
    }

    public static ReqResponse success(Object data) {
        return success("成功", data);
    }

    public static ReqResponse success(String desc) {
        return success(desc, null);
    }

    public static ReqResponse success(String desc, Object data) {
        ReqResponse reqResponse = new ReqResponse();
        reqResponse.setData(data);
        reqResponse.setDesc(desc);
        reqResponse.setErrCode(0);
        return reqResponse;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
