package com.example.lib.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;


import com.example.lib.base.BaseApplication;
import com.example.lib.bean.StationSlotDTO;
import com.example.lib.bean.TokenBean;
import com.example.lib.bean.UserBean;
import com.example.lib.bean.WorkstationDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * SharedPreferences工具类
 */
public class SharedPrefUtil {

    private static SharedPreferences sp;

    public static final String ip = "ip";
    public static final String port = "port";
    public static final String USER_INFO="userInfo";
    public static final String USER_Phone="phone";
    public static final String USER_NAME="username";
    public static final String USER_PASSWORD="userpassword";
    public static final String USER_Sex="sex";
    public static final String USER_Token="token";
    public static final String USER_Bean="userbean";
    public static final String EMPLOYEE_Bean="employeebean";
    public static final String USER_COOKIE="COOKIE";
    public static final String WORKSTATION="workstation";
    public static final String USER_Sig="userSig"; //IM登录需要的 用户标识
    public static final String USER_ID="userID";
    public static final String SMALL_MODE_OPENED ="small_mode";
    public static final String LAST_ROOM_ID = "roomId";
    public static final String IDS = "IDS";
//    public static final String TOKEN = "IDS";
    public static final String MINE_ROOM_ID = "my_room_id";
    public static final String MINE_ROOM_HASHCODE = "my_room_hascode";
    public static final String BASE_URL_ONLINE ="change_server_onlie" ;
    protected static Application sApplication;

    private static SharedPrefUtil sInstance;

    public static SharedPrefUtil getInstance() {
        if (sInstance == null) {
            synchronized (SharedPrefUtil.class) {
                if (sInstance == null) {
                    sInstance = new SharedPrefUtil();
                }
            }
        }
        return sInstance;
    }

    public static void init(Application application) {
        sApplication = application;
    }

    private static SharedPreferences getSp() {
        if (sp == null) {
            Context context= BaseApplication.getAppContext();
            sp = BaseApplication.getAppContext().getSharedPreferences("emr_SharedPref", Context.MODE_PRIVATE);
            Log.i("getSp: ", "getSp: ");
        }
        return sp;
    }
    public static void putTokenBean(TokenBean bean) {
        putObjectT(USER_Token, bean);
    }
    public static String getHeaderToken() {
        TokenBean tokenBean = getObjectT(USER_Token);
        if (tokenBean != null) {
            return tokenBean.getToken_type() + " " + tokenBean.getAccess_token();
        } else {
            return "";
        }

    }

    public static void putToken(String token) {
        putString(USER_Token, token);
    }
    public static String getToken() {
      return getString(USER_Token);
    }

    public static String getUserId() {
       return getString(USER_ID);
    }
    public static void putUserId(String userId) {
        putString(USER_ID, userId);
    }
    /**
     * 存入字符串
     *
     * @param key   字符串的键
     * @param value 字符串的值
     */
    public static void putString(String key, String value) {
        SharedPreferences preferences = getSp();
        //存入数据
        Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取字符串
     *
     * @param key 字符串的键
     * @return 得到的字符串
     */
    public static String getString(String key) {
        SharedPreferences preferences = getSp();
        return preferences.getString(key, "");
    }

    public static int getInt(String key) {
        SharedPreferences preferences = getSp();
        return preferences.getInt(key, 0);
    }

    /**
     * 获取字符串
     *
     * @param key   字符串的键
     * @param value 字符串的默认值
     * @return 得到的字符串
     */
    public static String getString(String key, String value) {
        SharedPreferences preferences = getSp();
        return preferences.getString(key, value);
    }

    /**
     * 保存布尔值
     *
     * @param key   键
     * @param value 值
     */
    public static void putBoolean(String key, boolean value) {
        SharedPreferences sp = getSp();
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获取布尔值
     *
     * @param key      键
     * @param defValue 默认值
     * @return 返回保存的值
     */
    public static boolean getBoolean(String key, boolean defValue) {
        SharedPreferences sp = getSp();
        return sp.getBoolean(key, defValue);
    }

    /**
     * 保存long值
     *
     * @param key   键
     * @param value 值
     */
    public static void putLong(String key, long value) {
        SharedPreferences sp = getSp();
        Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * 获取long值
     *
     * @param key      键
     * @param defValue 默认值
     * @return 保存的值
     */
    public static long getLong(String key, long defValue) {
        SharedPreferences sp = getSp();
        return sp.getLong(key, defValue);
    }

    /**
     * 保存int值
     *
     * @param key   键
     * @param value 值
     */
    public static void putInt(String key, int value) {
        SharedPreferences sp = getSp();
        Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 获取long值
     *
     * @param key      键
     * @param defValue 默认值
     * @return 保存的值
     */
    public static int getInt(String key, int defValue) {
        SharedPreferences sp = getSp();
        return sp.getInt(key, defValue);
    }

    /**
     * 保存对象
     *
     * @param key 键
     * @param obj 要保存的对象（Serializable的子类）
     * @param <T> 泛型定义
     */
    public static <T extends Serializable> void putObjectT(String key, T obj) {
        try {
            putObject(key, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象
     *
     * @param key 键
     * @param <T> 指定泛型
     * @return 泛型对象
     */
    public static <T extends Serializable> T getObjectT(String key) {
        try {
            return (T) getObject(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存储List集合
     *
     * @param key  存储的键
     * @param list 存储的集合
     */
    public static void putList(String key, List<? extends Serializable> list) {
        try {
            putObject(key, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取List集合
     *
     * @param key 键
     * @param <E> 指定泛型
     * @return List集合
     */
    public static <E extends Serializable> List<E> getList(String key) {
        try {
            return (List<E>) getObject(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存储Map集合
     *
     * @param key 键
     * @param map 存储的集合
     * @param <K> 指定Map的键
     * @param <V> 指定Map的值
     */
    public static <K extends Serializable, V extends Serializable> void putMap(
            String key, Map<K, V> map) {
        try {
            putObject(key, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <K extends Serializable, V extends Serializable> Map<K, V> getMap(
            String key) {
        try {
            return (Map<K, V>) getObject(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void putWorkStation(WorkstationDTO bean) {
        putObjectT(WORKSTATION, bean);
    }

    public static WorkstationDTO getWorkStation() {
        return getObjectT("workstation");
    }

    /**
     * 存储对象
     */
    public static void putObject(String key, Object obj)
            throws IOException {
        if (obj == null) {//判断对象是否为空
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        // 将对象放到OutputStream中
        // 将对象转换成byte数组，并将其进行base64编码
        String objectStr = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
        baos.close();
        oos.close();

        putString(key, objectStr);
    }

    /**
     * 获取对象
     */
    public static Object getObject(String key) {
        String wordBase64 = getString(key);
        // 将base64格式字符串还原成byte数组
        if (TextUtils.isEmpty(wordBase64)) { //不可少，否则在下面会报java.io.StreamCorruptedException
            return null;
        }
        byte[] objBytes = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将byte数组转换成product对象
        Object obj = null;
        try {
            obj = ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            bais.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }


    /**
     * 是否第一次进入
     */
    public static boolean isFirstIn() {
        return getSp().getBoolean("isFirstIn", true);
    }

    /**
     * isFirstIn
     * 设置已经进入过app
     */
    public static void setHasIn() {
        Editor e = getSp().edit();
        e.putBoolean("isFirstIn", false);
        e.commit();
    }


    public static void setBaseUrl(String baseUrl) {
        putObjectT("kdUserBaseUrl", baseUrl);
    }

//    public static String getBaseUrl() {
//        String baseUrl = getObjectT("kdUserBaseUrl");
//        if (StringUtil.isEmpty(baseUrl)) {
//            baseUrl = Constants.Server.online;
//        }
//        return baseUrl;
//    }
public static void putStationSlotDTO(StationSlotDTO bean) {
    putObjectT("stationSlotDTO", bean);
}

    public static StationSlotDTO getStationSlotDTO() {
        return getObjectT("stationSlotDTO");
    }

    public static void putRoleType(String roleType) {
        putObjectT("roleType", roleType);
    }

    public static String getRoleType() {
        return getObjectT("roleType");
    }
    public static void putPassword(String roleType) {
        putString(USER_PASSWORD, roleType);
    }

    public static String getPassword() {
        return getString(USER_PASSWORD);
    }
    public static void putUsername(String roleType) {
        putString(USER_NAME, roleType);
    }

    public static String getUsername() {
        return getString(USER_NAME);
    }

    public static void putUserBean(UserBean bean) {
        putObjectT(USER_Bean, bean);
    }
    public static String getOpenid(){
        return getUserBean().getTenants().get(0).getOpenid();
    }

    public static String getTenantName(){
        return getUserBean().getTenants().get(0).getName();
    }


    public static UserBean getUserBean() {
        return getObjectT(USER_Bean);
    }
    public static void putRemPassword(Boolean roleType) {
        putBoolean("remPassword", roleType);
    }

    public static Boolean getRemPassword() {
        return getBoolean("remPassword",false);
    }
    public static void putAutoLogin(Boolean roleType) {
        putBoolean("autologin", roleType);
    }

    public static String getIpPort() {
//        http://114.115.146.11:8080/
        String iopf="http://"+getString(ip)+":"+getString(port)+"/";
        return "http://"+getString(ip)+":"+getString(port)+"/";
    }
    public static int getNowPos() {
        return getInt("oldpos", 0);
    }
    public static void putNowPos(int pos) {
        putInt("oldpos", pos);
    }

    public static void removeNowPos() {
        Editor e = getSp().edit();
        e.remove("oldpos");
        e.commit();
    }
    public static void removeString(String key) {
        SharedPreferences preferences = getSp();
        //存入数据
        Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public static void removePassword() {
        Editor e = getSp().edit();
        e.remove(USER_PASSWORD);
        e.commit();
    }
    public static void removeAutoLogin(){
        Editor e = getSp().edit();
        e.remove("autologin");
        e.commit();
    }
    public static void exitLogin() {
        Editor e = getSp().edit();
//        e.remove(USER_Token);
        e.remove(USER_Bean);
        e.remove(USER_PASSWORD);
        e.remove("workstation");
        e.commit();
    }

}
