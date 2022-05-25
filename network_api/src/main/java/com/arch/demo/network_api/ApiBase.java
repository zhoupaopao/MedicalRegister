package com.arch.demo.network_api;

import android.util.Log;

import com.arch.demo.network_api.beans.LoginNetBean;
import com.example.lib.base.BaseApplication;
import com.example.lib.bean.TokenBean;
import com.example.lib.utils.SharedPrefUtil;
import com.example.lib.utils.StringUtils;
import com.example.lib.utils.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.arch.demo.network_api.errorhandler.AppDataErrorHandler;
import com.arch.demo.network_api.errorhandler.HttpErrorHandler;
import com.arch.demo.network_api.interceptor.RequestInterceptor;
import com.arch.demo.network_api.interceptor.ResponseInterceptor;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import io.reactivex.Observable;

import io.reactivex.Observer;

/**
 * Created by pqc
 */
public abstract class ApiBase {
    protected Retrofit retrofit;
    protected static INetworkRequestInfo networkRequestInfo;
    private static ErrorTransformer sErrorTransformer = new ErrorTransformer();
    private static RequestInterceptor sHttpsRequestInterceptor;
    private static ResponseInterceptor sHttpsResponseInterceptor;

    protected ApiBase(String baseUrl) {
        retrofit = new Retrofit
                .Builder()
                .client(getOkHttpClient())
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static <T> T createApi(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SharedPrefUtil.getIpPort())
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                //注册自定义的工厂类
//                .addConverterFactory(ResponseConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

//    public static Retrofit creatApi(String baseUrl) {
//        retrofit = new Retrofit
//                .Builder()
//                .client(getOkHttpClient())
//                .baseUrl(baseUrl)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create()).build();
//        return retrofit;
//    }

    public static void setNetworkRequestInfo(INetworkRequestInfo requestInfo) {
        networkRequestInfo = requestInfo;
        sHttpsRequestInterceptor = new RequestInterceptor(requestInfo);
        sHttpsResponseInterceptor = new ResponseInterceptor();
    }

    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS);

        /*可以统一添加网络参数到请求头*/
//        okHttpClient.addInterceptor(sHttpsRequestInterceptor);
        /*网络请求返回的时候的数据处理*/
        okHttpClient.addInterceptor(sHttpsResponseInterceptor);
        okHttpClient.addInterceptor(new AuthorizationInterceptor());
        okHttpClient.addInterceptor(new TokenInterceptor());
//        okHttpClient.authenticator((route, response) -> {
////            String credential = Credentials.basic("web", "");
////            if (credential.equals(response.request().header("Authorization"))) {
////                return null; // If we already failed with these credentials, don't retry.
////            }
//            String credential=refresh_Token();
//            if(credential==null){
//                return null;
//            }
//            return response.request().newBuilder()
//                    .header("requestId",getMyUUID())
//                    .header("Authorization", credential)
//                    .addHeader("Connection", "close")
//                    .build();
////                                return null;
//        }
//        ).retryOnConnectionFailure(false);
        setLoggingLevel(okHttpClient);
        OkHttpClient httpClient = okHttpClient.build();
        httpClient.dispatcher().setMaxRequestsPerHost(20);
        return httpClient;
    }

    /**
     * 拦截器使用，修改Authorization
     */
    public static class TokenInterceptor implements Interceptor {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            okhttp3.Response response = chain.proceed(request);

            if (isTokenExpired(response)&& !StringUtils.isEmpty(SharedPrefUtil.getString("employeeLabel"))) {//根据和服务端的约定判断token过期
                //同步请求方式，获取最新的Token
                String newSession = refresh_Token();
                //使用新的Token，创建新的请求
                Request newRequest = chain.request()
                        .newBuilder()
                        .removeHeader("Authorization")
                        .header("Authorization", newSession)
                        .build();
                //重新请求
                return chain.proceed(newRequest);
            }
            return response;
        }
    }

    public static String refresh_Token(){
        String re_tok="";
        final Call<TokenBean> categories = ApiBase.createApi(ApiInterface.class)
                .tokenByNumber(SharedPrefUtil.getString("employeeLabel"));
        try {
            // 4. call对象执行同步请求，请求数据在子线程中执行
            retrofit2.Response<TokenBean> execute = categories.execute();
            //为了防止刷新失败使用的
            if(execute.code()!=200){
                throw new Exception("token刷新失败");
            };
            TokenBean mBean = execute.body();//new Gson().fromJson(execute.body().toString(), TokenBean.class);
            SharedPrefUtil.putTokenBean(mBean);
            //额外请求一下login接口
            RequestBody requestBody = new FormBody.Builder()
                    .add("openid", SharedPrefUtil.getOpenid())
                    .build();
            Call<LoginNetBean> categories1 = ApiBase.createApi(ApiInterface.class)
                    .platformLogin(requestBody);
            retrofit2.Response<LoginNetBean> execute1 = categories1.execute();
            LoginNetBean loginNetBean=execute1.body();


            re_tok=mBean.getToken_type() + " " + mBean.getAccess_token();
        } catch (Exception e) {
            //退出登录
            BaseApplication.doReLogin();
            e.printStackTrace();
        }
        Log.i("refresh_Token: ",re_tok);
        return re_tok;
//        return "";
    }
    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private static boolean isTokenExpired(okhttp3.Response response) {
        if (response.code() == 403||response.code() == 401) {
            return true;
        }
        return false;
    }

    /**
     * 拦截器使用，修改Authorization
     */
    private static class AuthorizationInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            String credential = Credentials.basic("web", "");
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader("Authorization")
                    .header("Authorization", SharedPrefUtil.getHeaderToken())
//                    .header("Authorization",credential)
                    .header("requestId",getMyUUID())
                    .header("X-Sign",getMyUUID())
                    .header("X-TIMESTAMP", TimeUtil.getNYRSFM())

                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }

    private static String getMyUUID() {
        UUID uuid = UUID.randomUUID();

        String uniqueId = uuid.toString();

        Log.d("debug", "----->UUID" + uuid);

        return uniqueId;

    }

    private static void setLoggingLevel(OkHttpClient.Builder builder) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        //BODY打印信息,NONE不打印信息
        logging.setLevel(networkRequestInfo.isDebug() ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        builder.addInterceptor(logging);
    }

    /**
     * 封装线程管理和订阅的过程
     */
    protected void ApiSubscribe(Observable observable, Observer observer) {
        observable.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .unsubscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .compose(sErrorTransformer)
                .subscribe(observer);
    }



    /**
     * 处理错误的变换
     * 网络请求的错误处理，其中网络错误分为两类：
     * 1、http请求相关的错误，例如：404，403，socket timeout等等；
     * 2、http请求正常，但是返回的应用数据里提示发生了异常，表明服务器已经接收到了来自客户端的请求，但是由于
     * 某些原因，服务器没有正常处理完请求，可能是缺少参数，或者其他原因；
     */
    private static class ErrorTransformer<T> implements ObservableTransformer {

        @Override
        public ObservableSource apply(io.reactivex.Observable upstream) {
            //onErrorResumeNext当发生错误的时候，由另外一个Observable来代替当前的Observable并继续发射数据
            return (io.reactivex.Observable<T>) upstream
                    .map(new AppDataErrorHandler())/*返回的数据统一错误处理*/
                    .onErrorResumeNext(new HttpErrorHandler<T>());/*Http 错误处理**/
        }
    }
}
