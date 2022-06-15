package com.example.medicalregister.utils;

public class WordUtils {
    public static final String type_package = "package" ;//是箱子
    public static final String type_thing = "thing" ;//是袋子
    public static final String type_waste = "waste" ;//是危废

    public static final String type_Sku = "sku" ;//是危废

    public static final String type_add = "add" ;//列表请求使用
    public static final String type_delete = "delete" ;//列表请求使用
    public static final String type_refresh = "refresh" ;//列表请求使用
    public static final String type_Online = "online" ;//在线模式AGV
    public static final String type_Offline = "offline" ;//离线模式AGV


    public static boolean isDownload = false;

    public static boolean isRunnable=false;
//    public static String topicUpdate = "device_update/LuRuZhongDuan/";//发布心跳
public static String topicUpdate = "device_update";//发布心跳

    public static String topicGet = "device_get/LuRuZhongDuan/";//接受设置
//    public static String Print_state = "1";//打印机状态
//    public static String BlueWeight_State = "1";//蓝牙模块状态
//    public static String Interval_State = "60";//发送间隔
//    public static String D_state="1";//设备禁用，启用0是禁用，1是启用
//    private static String TIME

}
