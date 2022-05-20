package com.example.medicalregister.http;

import com.arch.demo.network_api.ApiBase;
import com.arch.demo.network_api.utils.MD5Utils;
import com.example.lib.bean.TokenBean;
import com.example.lib.bean.UserBean;
import com.example.lib.utils.SharedPrefUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.reactivex.Observer;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 *
 * @author pqc
 * @date 2017/7/20
 */
public final class JeecgApi extends ApiBase {
    private static volatile JeecgApi instance = null;
    private static final String key="a8r7wyr!$#@#^$&^*)(*&^5qW_QW_EWQE_W@!##!$&^$#^#";
    private ApiInterface apiInterface;

    private JeecgApi() {
        super("http://boot.jeecg.com:8080/");
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static JeecgApi getInstance() {
        if (instance == null) {
            synchronized (JeecgApi.class) {
                if (instance == null) {
                    instance = new JeecgApi();
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
//    /**
//     * 获取验证码
//     * @param phone
//     * @param observer
//     */
//    public void getCodeMsg(String phone, Observer<BaseResponse<String>> observer){
//        Map<String,String> map=new HashMap<>();
//        map.put("phone",phone);
//        map.put("sign",getSigin(map));
//        ApiSubscribe(apiInterface.getCodemsg(map), observer);
//    }

    /**登录请求
     * @param username 用户名
     * @param password 密码
     * @param observer 登录回调
     *                 <BaseResponse>接口基本类对象
     *                 <String> 接口返回data数据类型
     *
     * */
//    public void login(String username, String password, Observer<BaseResponse<UserBean>> observer){
//        Map<String, String> map=new HashMap<>();
//        map.put("username",username);
//        map.put("password",password);
//        ApiSubscribe(apiInterface.login(map), observer);
//    }

    /**
     * 获取token
     * @param username
     * @param password
     * @param observer
     */
    public void token(String username, String password, Observer<TokenBean> observer){
        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
//                        .add("grant_type", "password")
//                        .add("scope", "all")
                .build();
        ApiSubscribe(apiInterface.token(requestBody), observer);
    }

    public void accountMe(Observer<UserBean> observer){
        ApiSubscribe(apiInterface.accountsMe(), observer);
    }














}
