package com.dmtech.app.pcst.entity;

public class UserResponse {
    private int code; //响应编码
    private String msg;	//响应文字

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

    @Override
    public String toString() {
        return "UserResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
