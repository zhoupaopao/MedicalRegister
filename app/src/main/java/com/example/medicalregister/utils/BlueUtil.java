package com.example.medicalregister.utils;

/**
 * Created by Lenovo on 2019/9/27.
 */

public class BlueUtil {
    public static final String blue_weight = "";//无设备地址
//    public static final String blue_weight = "FA:20:19:07:58:84";//第一套设备(0925标签贴2)
//    public static final String blue_weight = "FA:20:19:09:67:39";//第二套设备(1001标签贴1)
//    public static final String blue_weight = "FA:20:19:09:67:40";//第三套设备（1021标签贴3）
//public static final String blue_weight = "FA:20:19:09:67:04";//第四套设备（河南测试）
public static final String blue_print = "";//无设备地址
//    public static final String blue_print = "DC:1D:30:37:F5:11";//第一套设备(0925标签贴2)
//    public static final String blue_print = "DC:1D:30:7D:E0:2B";//第二套设备(1001标签贴1)
//        public static final String blue_print = "DC:1D:30:35:43:C2";//第三套设备（1021标签贴3）
//public static final String blue_print = "DC:1D:30:37:F5:60";//第四套设备（河南测试）)

    public static final Boolean new_equipment = false ;//是新设备吗


    public static final String type_package = "package" ;//是箱子
    public static final String type_thing = "thing" ;//是袋子
    public static final String type_waste = "waste" ;//是危废

    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final int MESSAGE_SEND_WEIGHT = 101;
    public static final String DEVICE_NAME = "device_name";
    public static final String DEVICE_ADDRESS = "device_address";
    public static final String TOAST = "toast";
}
