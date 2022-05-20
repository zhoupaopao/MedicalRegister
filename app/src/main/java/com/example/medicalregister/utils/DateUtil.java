package com.example.medicalregister.utils;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaochengxiao on 2017/12/18.
 */

public class DateUtil {

    public static String millisecond2Date(String millSecond, String formatStr) {
        if (millSecond == null || millSecond.isEmpty() || millSecond.equals("null")) {
            return "";
        }
        if (formatStr == null || formatStr.isEmpty()) {
            formatStr = "yyyy/MM/dd HH:mm:ss";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(new Date(Long.valueOf(millSecond)));
    }

    public static String millisecond2Date(Long millSecond) {
        return millisecond2Date(millSecond, "");
    }

    //讲string转成instant,这个string是标准时间格式
    public static String getInstantByStr(String type, String tempTime)  {
        if(tempTime==null||tempTime.equals("")){
            return "";
        }
        Date date= null;
        try {
            date = parse(tempTime,type);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateTime dt = new DateTime(date);
//        Instant instant=dt.toInstant().plus(TimeUnit.HOURS.toMillis(8));
        Instant instant=dt.toInstant();
        String newtime=instant.toString();
        return newtime;
    }

    public static Date parse(String millSecond, String formatStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.parse(millSecond);
    }
    //将instant变成字符串标准格式
    public static String getStringByInstant(String type, String tempTime) {
        if(tempTime==null||tempTime.equals("")){
            return "";
        }
        DateTime dt = new DateTime(tempTime);
        Instant instant;
        if(type.equals("yyyy-MM-dd")){
            instant=dt.toInstant().plus(TimeUnit.HOURS.toMillis(8));
//            instant=dt.toInstant();
        }else{
//            instant=dt.toInstant();
            instant=dt.toInstant().plus(TimeUnit.HOURS.toMillis(8));
        }
//        Instant instant=dt.toInstant().plus(TimeUnit.HOURS.toMillis(8));;
//        Instant instant=dt.toInstant();
        DateTimeFormatter forPattern = DateTimeFormat.forPattern(type);
        String newtime=instant.toString(forPattern);
        return newtime;
    }
    public static String millisecond2Date(Long millSecond, String formatStr) {
        if (millSecond == null) {
            return "";
        }
        if (formatStr == null || formatStr.isEmpty()) {
            formatStr = "yyyy/MM/dd HH:mm:ss";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(new Date(millSecond));
    }

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds   精确到秒的字符串
     * @param formatStr
     * @return
     */
    public static String timeSecond2Date(String seconds, String formatStr) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        return millisecond2Date(seconds + "000", formatStr);
    }


    public static long getDiffTime(long tempTime) {
        return new Date().getTime() - tempTime;
    }


}
