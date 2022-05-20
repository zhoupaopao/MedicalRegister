package com.arch.demo.network_api.errorhandler;

import com.arch.demo.network_api.beans.BaseResponse;

import io.reactivex.functions.Function;


/**
 * HandleFuc处理以下网络错误：
 * 1、应用数据的错误会抛RuntimeException；
 * @author pqc
 */
public class AppDataErrorHandler implements Function<BaseResponse,BaseResponse> {
    @Override
    public BaseResponse apply(BaseResponse response) throws Exception {
        //response中code码不会0 出现错误
        if (response instanceof BaseResponse && response.getErr().equals("0")){
            return response;
        }else if (response instanceof BaseResponse && response.getErr().equals("10002")){
            return response;
        }else {
            throw new RuntimeException(response.getMsg() != null ? response.getMsg() : "");
        }
//        if (response instanceof BaseResponse && !response.getErr().equals("0")) {
//            throw new RuntimeException(response.getMsg() != null ? response.getMsg() : "");
//        }
//        return response;
    }
}