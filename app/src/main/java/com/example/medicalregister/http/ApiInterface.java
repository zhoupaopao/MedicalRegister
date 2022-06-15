package com.example.medicalregister.http;


import com.arch.demo.network_api.beans.BaseListResponse;
import com.example.lib.bean.StationSlotDTO;
import com.example.lib.bean.TokenBean;
import com.example.lib.bean.UserBean;
import com.example.lib.bean.WorkstationDTO;
import com.example.medicalregister.bean.AcheveReturnLabelBean;
import com.example.medicalregister.bean.ApiStringBean;
import com.example.medicalregister.bean.DeviceUnit;
import com.example.medicalregister.bean.EmployeesBean;
import com.example.medicalregister.bean.LabelBean;
import com.example.medicalregister.bean.PlatformLoginBean;
import com.example.medicalregister.bean.UpdateBean;
import com.example.medicalregister.bean.WasteInventoryBean;
import com.example.medicalregister.bean.WasteSourceBean;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.RequestBody;
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

    @GET("/accounts/me")
    Observable<UserBean> accountsMe();


    //查询工作站/{type}
    @GET("api/base/stations")
    Observable<ArrayList<WorkstationDTO>> workStations();

    @GET("api/base/slots/stationNumber/available/{stationNumber}")
    Observable<ArrayList<StationSlotDTO>> getStationSlots(@Path("stationNumber") String stationNumber);

//    @GET("containers/number/empty/{number}")
@GET("api/base/slots/stationNumber/{stationNumber}")
Observable<ArrayList<StationSlotDTO>> getAllStationSlots(@Path("stationNumber") String stationNumber);

    @GET("label/labels/my")
    Observable<BaseListResponse<ArrayList<LabelBean>>> labelMy(@Query("params[type]") String batch,//周转箱
                                                               @Query("params[keywords]") String search,//模糊搜索
                                                               @Query("offset") int offset,
                                                               @Query("limit") int limit
    );

    @GET("label/labels/{number}")
    Observable<LabelBean> labels(@Path("number") String number);

    @GET("label/labelRoles/{number}")
    Observable<ArrayList<PlatformLoginBean.RolesBean>> labelRoles(@Path("number") String number);

    @GET("waste/base/wasteSources/num/{number}")
    Observable<WasteSourceBean> baseWasteSourcesNum(@Path("number") String number);

    @GET("platform/employees/number/{num}")
    Observable<EmployeesBean> employees(@Path("num") String num);


    @GET("label/updateVersions/1")
    Observable<UpdateBean> update();

    //搜索危废类型列表
    @GET("waste/record/wasteInventories/findByWasteName")
    Observable<BaseListResponse<ArrayList<WasteInventoryBean>>> wasteProduceRecord(@Query("params[source]") String source,
                                                                                   @Query("params[keywords]") String wasteName,
                                                                                   @Query("offset") int offset,
                                                                                   @Query("limit") int limit
    );

    @POST("oauth/token?client_id=web&grant_type=numbers&scope=read")
    Observable<TokenBean> tokenByNumber(@Query("number") String number);

    //1.3，登录租户，获取用户角色，判断是否有权限

    @POST("platform/login")
    Observable<PlatformLoginBean> platformLogin(@Body RequestBody requestBody);

//获取录入设备的信息
    @GET("label/deviceUnit/deviceUnits/both/{deviceNumber}")
    Observable<DeviceUnit> deviceByNumber(@Path("deviceNumber") String deviceNumber);

// 创建单个标签

    @POST("label/labels")
    Observable<AcheveReturnLabelBean> achieveLabel(@Body RequestBody requestBody);

    //6.4扫描条码获取条码信息，如果条码的状态为2或者3，则不能使用

    @GET("label/labels/{number}")
    Observable<LabelBean> labelByNumber(@Path("number") String number);

    //保存登记
    @POST("waste/record/wasteProduceRecord/batchSave")
    Observable<ApiStringBean> batchSave(@Body RequestBody requestBody);

    @GET("label/heart/heartDances")
    Observable<String> heartDances();
}



