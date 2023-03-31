package com.dmtech.app.pcst.entity;
//服务器端对用户登录注册的响应
public class UserResponse {
    //响应代码：
    //0 - 验证通过，-1 - 用户不存在，-2 - 密码错误，
    //1 - 注册成功，-3 - 账户已存在，-4 - 注册失败
    private int code;
    //响应文本消息
    private String msg;

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
