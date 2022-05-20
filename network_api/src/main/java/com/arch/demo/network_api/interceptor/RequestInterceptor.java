package com.arch.demo.network_api.interceptor;
import android.content.SharedPreferences;
import android.text.TextUtils;


import com.arch.demo.network_api.INetworkRequestInfo;
import com.example.lib.utils.EmptyUtil;
import com.example.lib.utils.SharedPrefUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class RequestInterceptor implements Interceptor {
    private INetworkRequestInfo mNetworkRequestInfo;
    public RequestInterceptor(INetworkRequestInfo networkRequestInfo) {
        this.mNetworkRequestInfo = networkRequestInfo;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request()
                .newBuilder();
        if(mNetworkRequestInfo != null) {
            for(String key:mNetworkRequestInfo.getRequestHeaderMap().keySet()){
                if(!TextUtils.isEmpty(mNetworkRequestInfo.getRequestHeaderMap().get(key))) {
                    builder.addHeader(key, mNetworkRequestInfo.getRequestHeaderMap().get(key));
                }
            }
        }
        String token= SharedPrefUtil.getString("token");
//        String token= SharedPrefUtil.getString(SharedPrefUtil.Token);
        if (!EmptyUtil.isEmpty(token)){
            builder.addHeader("token",token);
        }
        String cookie= SharedPrefUtil.getString(SharedPrefUtil.USER_COOKIE);
        if (!EmptyUtil.isEmpty(cookie)){
            builder.addHeader("Cookie",cookie);
        }

        return chain.proceed(builder.build());
    }
}