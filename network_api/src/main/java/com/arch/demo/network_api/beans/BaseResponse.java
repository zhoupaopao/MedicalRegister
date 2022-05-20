package com.arch.demo.network_api.beans;


import com.example.lib.utils.EmptyUtil;

/**
 *
 * @author pqc
 */
public class BaseResponse<T> {
    private String code;
    private String msg;
    private T body;
    public boolean isSuccess(){
        return code.equals("0");
    }

    public String getErr() {
        return code;
    }

    public void setErr(String err) {
        this.code = err;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return body;
    }

    public void setData(T data) {
        this.body = data;
    }

    public boolean hasData() {
        return !EmptyUtil.isEmpty(body);
    }
}
