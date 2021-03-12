package com.luo.user.utils;

public class ResultCode {

    public static final ResultCode SUCCESS = new ResultCode(0000, "操作成功");

    public static final ResultCode PARAM_ERROR = new ResultCode(1006,
            "参数错误或缺少必要参数");

    public static final ResultCode UAP_USER_ERROR = new ResultCode(6005,
            "获取UAP用户信息报错");
    public static final ResultCode UAP_PWD_ERROR = new ResultCode(6006,
            "输入账户密码有误");

    public static final ResultCode UAP_TICKET_ERROR = new ResultCode(6007,
            "TICKET无效");
    public static final ResultCode UAP_APP_ERROR = new ResultCode(6008,
            "获取UAP应用信息报错");
    public static final ResultCode UAP_AUTH_ERROR = new ResultCode(6009,
            "获取UAP功能权限信息报错");
    public static final ResultCode UAP_ROLE_ERROR = new ResultCode(6010,
            "获取UAP角色信息报错");
    public static final ResultCode UAP_ORG_USER_ERROR = new ResultCode(6011,
            "获取UAP机构下用户信息报错");

    public static final String REDIS_KEY = "XF-SERVICE-RECORDINGPICKUP";

    /**
     * 错误编码
     */
    private int code;

    /**
     * 错误信息
     */
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * ,./
     * 
     * @param code
     *            the code to set
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**
     * @return the code
     */
    public int getCode() {
        return code;
    }

    /**
     * @param msg
     *            the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }
}
