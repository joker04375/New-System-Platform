package net.maku.college.response;

import java.io.Serializable;

public enum ResponseEnum implements Serializable {
    REQUEST_OK(200,"请求完成"),
    REQUEST_FAIL(1001,"请求失败");


    private int code;
    private String msg;

    ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
