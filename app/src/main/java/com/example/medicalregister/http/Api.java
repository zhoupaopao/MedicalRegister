package com.example.medicalregister.http;

import com.arch.demo.network_api.ApiBase;
import com.arch.demo.network_api.beans.BaseListResponse;
import com.arch.demo.network_api.utils.MD5Utils;
import com.example.lib.bean.TokenBean;
import com.example.lib.bean.UserBean;
import com.example.lib.utils.SharedPrefUtil;
import com.example.medicalregister.bean.AcheveReturnLabelBean;
import com.example.medicalregister.bean.ApiStringBean;
import com.example.medicalregister.bean.DeviceUnit;
import com.example.medicalregister.bean.EmployeesBean;
import com.example.medicalregister.bean.LabelBean;
import com.example.medicalregister.bean.PlatformLoginBean;
import com.example.medicalregister.bean.UpdateBean;
import com.example.medicalregister.bean.WasteInventoryBean;
import com.example.medicalregister.bean.WasteSourceBean;

import java.util.ArrayList;
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
public final class Api extends ApiBase {
    private static volatile Api instance = null;
    private static final String key="a8r7wyr!$#@#^$&^*)(*&^5qW_QW_EWQE_W@!##!$&^$#^#";
    private ApiInterface apiInterface;

    private Api() {
        super(SharedPrefUtil.getIpPort());
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static Api getInstance() {
        if (instance == null) {
            synchronized (Api.class) {
                if (instance == null) {
                    instance = new Api();
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
                .build();
        ApiSubscribe(apiInterface.token(requestBody), observer);
    }
    public void tokenByNumber(String number, Observer<TokenBean> observer){
//        RequestBody requestBody = new FormBody.Builder()
//                .add("number", number)
//                .build();
        ApiSubscribe(apiInterface.tokenByNumber(number), observer);
    }


    public void accountMe(Observer<UserBean> observer){
        ApiSubscribe(apiInterface.accountsMe(), observer);
    }

    //只搜索袋子，同时
    public void labelMy( int offset, int limit,Observer<BaseListResponse<ArrayList<LabelBean>>> observer){
        ApiSubscribe(apiInterface.labelMy("thing","",offset,limit), observer);
    }
    public void labels(String number,Observer<LabelBean> observer){
        ApiSubscribe(apiInterface.labels(number), observer);
    }
    public void labelRoles(String number,Observer<ArrayList<PlatformLoginBean.RolesBean>> observer){
        ApiSubscribe(apiInterface.labelRoles(number), observer);
    }




    public void baseWasteSourcesNum(String number,Observer<WasteSourceBean>observer){
        ApiSubscribe(apiInterface.baseWasteSourcesNum(number),observer);
    }

    public void employees(String number,Observer<EmployeesBean>observer){
        ApiSubscribe(apiInterface.employees(number),observer);
    }
    public void update(Observer<UpdateBean>observer){
        ApiSubscribe(apiInterface.update(),observer);
    }



    public void wasteProduceRecord( String source,Observer<BaseListResponse<ArrayList<WasteInventoryBean>>> observer){
        ApiSubscribe(apiInterface.wasteProduceRecord(source,"",0,20), observer);
    }
    public void platformLogin( String openId,Observer<PlatformLoginBean> observer){
        RequestBody requestBody = new FormBody.Builder()
                .add("openid", openId)
                .build();
        ApiSubscribe(apiInterface.platformLogin(requestBody), observer);
    }

    public void heartDances(Observer<String> observer){
        ApiSubscribe(apiInterface.heartDances(), observer);
    }

    public void deviceByNumber(String deviceNumber,Observer<DeviceUnit> observer){
        ApiSubscribe(apiInterface.deviceByNumber(deviceNumber), observer);
    }

    public void achieveLabel(RequestBody requestBody,Observer<AcheveReturnLabelBean> observer){
        ApiSubscribe(apiInterface.achieveLabel(requestBody), observer);
    }
    public void batchSave(RequestBody requestBody,Observer<ApiStringBean> observer){
        ApiSubscribe(apiInterface.batchSave(requestBody), observer);
    }
    public void labelByNumber(String number,Observer<LabelBean> observer){
        ApiSubscribe(apiInterface.labelByNumber(number), observer);
    }



//    public void getProductionOrderList(int offset, int limit, Observer<BaseListResponse<ArrayList<ProductionOrderDTO>>> observer){
//        ApiSubscribe(apiInterface.getProductionOrderList(offset,limit), observer);
//    }










}
