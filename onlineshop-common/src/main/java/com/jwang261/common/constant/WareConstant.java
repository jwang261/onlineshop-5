package com.jwang261.common.constant;

/**
 * @author jwang261
 * @date 2020/8/29 10:31 PM
 */
public class WareConstant {

    public enum PurchaseStatusEnum{
        CREATED(0, "新建"),ASSIGNED(1, "已分配")
        ,RECEIVED(2, "已领取"),FINISHED(3, "已完成")
        ,HAS_ERROR(4,"有异常")
        ;
        private int code;
        private String msg;

        PurchaseStatusEnum(int code, String msg) {
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
    public enum PurchaseDetailEnum{
        CREATED(0, "新建"),ASSIGNED(1, "已分配")
        ,BUYING(2, "正在采购"),FINISHED(3, "已完成")
        ,HAS_ERROR(4,"采购失败")
        ;
        private int code;
        private String msg;

        PurchaseDetailEnum(int code, String msg) {
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
}
