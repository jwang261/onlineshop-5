package com.jwang261.common.exception;

/**
 * @author jwang261
 * @date 2020/8/26 2:00 PM
 */

public enum BizCodeEnum {

    UNKNOWN_EXCEPTION(10000, "Unknown Exception"),
    VALID_EXCEPTION(10001, "Not Valid Argument"),
    PRODUCT_UP_EXCEPTION(11000, "Product Up Exception");


    private int code;
    private String msg;
    BizCodeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
