package com.arch.demo.network_api;

import com.arch.demo.network_api.utils.MD5Utils;
import com.example.lib.bean.TokenBean;
import com.example.lib.utils.SharedPrefUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observer;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 *
 * @author pqc
 * @date 2017/7/20
 */
public final class ApiNet extends ApiBase {
    private static volatile ApiNet instance = null;
    private static final String key="a8r7wyr!$#@#^$&^*)(*&^5qW_QW_EWQE_W@!##!$&^$#^#";
    private ApiInterface apiInterface;

    private ApiNet() {
        super(SharedPrefUtil.getIpPort());
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static ApiNet getInstance() {
        if (instance == null) {
            synchronized (ApiNet.class) {
                if (instance == null) {
                    instance = new ApiNet();
                }
            }
        }
        return instance;
    }
    /**获取sign**/
    private String getSigin(Map map){
        StringBuffer sign=new StringBuffer();
        Set set=map.keySet();
        Object[] arr=set.toArray();
        Arrays.sort(arr);
        for(Object key:arr){
            sign.append((String) key);
            sign.append("=");
            sign.append(map.get(key));
            sign.append("&");
        }
        sign.append("key=");
        sign.append(key);
        return MD5Utils.encrypt(sign.toString());
    }
    //比如可以这样生成Map<String, RequestBody> requestBodyMap
    //Map<String, String> requestDataMap这里面放置上传数据的键值对。
    private static Map<String, RequestBody> getRequestBody(Map<String, String> requestDataMap) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key) == null ? "" : requestDataMap.get(key));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }

//    public void tokenByNumber(String number, Observer<TokenBean> observer){
////        RequestBody requestBody = new FormBody.Builder()
////                .add("number", number)
////                .build();
//        ApiSubscribe(apiInterface.tokenByNumber(number), observer);
//    }












}
