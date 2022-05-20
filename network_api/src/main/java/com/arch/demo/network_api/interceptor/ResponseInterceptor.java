package com.arch.demo.network_api.interceptor;

import android.text.TextUtils;


import com.example.lib.utils.SharedPrefUtil;

import java.io.IOException;
import java.net.SocketTimeoutException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class ResponseInterceptor implements Interceptor {

    public ResponseInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startRequestTime = System.currentTimeMillis();
        String url = request.url().toString();
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (SocketTimeoutException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
        if (url.contains("commonApi/transport/login")){
            Headers headers = response.headers();
            String s = headers.get("Set-Cookie");
            if (!TextUtils.isEmpty(s) && s.contains(";")) {
                String[] split = s.split(";");
                SharedPrefUtil.putString(SharedPrefUtil.USER_COOKIE,split[0]);
//                PreferencesUtil.USER_COOKIE = split[0];
            }
        }

        String rawJson = response.body() == null ? "" : response.body().string();
        return response.newBuilder().body(ResponseBody.create(response.body().contentType(), rawJson)).build();
    }
}
