package com.arch.demo.network_api;


import com.example.lib.bean.TokenBean;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author pqc
 */
public interface ApiInterface {

    /**
     * 登录
     **/
    @Headers({
            "Content-Type: application/x-www-form-urlencoded",
            "Authorization: Basic d2ViOg==",
            "Type: Basic Auth",
            "username: web",
    })
    @POST("/oauth/token?grant_type=password&scope=all")
    Observable<TokenBean> token(@Body RequestBody requestBody);

    @POST("oauth/token?client_id=web&grant_type=numbers&scope=read")
    Call<TokenBean> tokenByNumber(@Query("number") String number);


}



