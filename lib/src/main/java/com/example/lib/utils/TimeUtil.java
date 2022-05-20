package com.example.lib.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * 时间工具
 */
public class TimeUtil {
    public final static String FORMAT_DATE = "yyyy-MM-dd";
    public final static String FORMAT_TIME = "hh:mm";
    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd hh:mm";
    public final static String FORMAT_MONTH_DAY_TIME = "MM月dd日 hh:mm";
    public final static String FORMAT_PICTURE = "yyyyMMdd_hhmm";
    public final static String FORMAT_YEAR_MONTH_DAY = "yyyy年MM月dd日";
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat sdf = new SimpleDateFormat();
    private static final int YEAR = 365 * 24 * 60 * 60;// 年
    private static final int MONTH = 30 * 24 * 60 * 60;// 月
    private static final int DAY = 24 * 60 * 60;// 天
    private static final int HOUR = 60 * 60;// 小时
    private static final int MINUTE = 60;// 分钟

    private static final int THREEDAYS = DAY * 3;

    /**
     * 根据时间戳获取描述性时间，如3分钟前，1天前
     *
     * @param timestamp 时间戳 单位为毫秒
     * @return 时间字符串
     */
    public static String getDescriptionTimeFromTimestamp(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp * 1000) / 1000;// 与现在时间相差秒数
        String timeStr = null;
        if (timeGap > YEAR) {// 去年及以前：显示x年X月X日 X:X
            // timeStr = timeGap / YEAR + "年前";
            timeStr = getFormatTimeFromTimestamp(timestamp * 1000, FORMAT_DATE_TIME);
        } else if (timeGap > THREEDAYS && timeGap < YEAR) {// 3天以上显示X月X日 X:X
            // timeStr = timeGap
            // / MONTH + "个月前";
            timeStr = getFormatTimeFromTimestamp(timestamp * 1000, FORMAT_MONTH_DAY_TIME);
        } else if (timeGap > DAY && timeGap < THREEDAYS) {// 1天以上3天以下
            timeStr = timeGap / DAY + "天前";
        } else if (timeGap > HOUR) {// 1小时-24小时
            timeStr = timeGap / HOUR + "小时前";
        } else if (timeGap > MINUTE) {// 1分钟-59分钟
            timeStr = timeGap / MINUTE + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }

    public static String getDay(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(s);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            if (cal.get(Calendar.DAY_OF_WEEK) == 2) {
                return "周一";
            } else if (cal.get(Calendar.DAY_OF_WEEK) == 3) {
                return "周二";
            } else if (cal.get(Calendar.DAY_OF_WEEK) == 4) {
                return "周三";
            } else if (cal.get(Calendar.DAY_OF_WEEK) == 5) {
                return "周四";
            } else if (cal.get(Calendar.DAY_OF_WEEK) == 6) {
                return "周五";
            } else if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
                return "周六";
            } else if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
                return "周日";
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "-1";

    }

    public static String getDay(String year, String m, String d) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = sdf.parse(year + "-" + m + "-" + d);

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            if (cal.get(Calendar.DAY_OF_WEEK) == 2) {
                return "周一";
            } else if (cal.get(Calendar.DAY_OF_WEEK) == 3) {
                return "周二";
            } else if (cal.get(Calendar.DAY_OF_WEEK) == 4) {
                return "周三";
            } else if (cal.get(Calendar.DAY_OF_WEEK) == 5) {
                return "周四";
            } else if (cal.get(Calendar.DAY_OF_WEEK) == 6) {
                return "周五";
            } else if (cal.get(Calendar.DAY_OF_WEEK) == 7) {
                return "周六";
            } else if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
                return "周日";
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "-1";

    }

    /**
     * 将时间unix转换为int类型
     *
     * @param timeString
     * @param format
     * @return
     */
    public static long DateToLong(String timeString, String format) {

        long time = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(timeString);
            time = date.getTime() - System.currentTimeMillis();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 根据时间戳获取指定格式的时间，如2011-11-30 08:40
     *
     * @param timestamp 时间戳 单位为毫秒
     * @param format    指定格式 如果为null或空串则使用默认格式"yyyy-MM-dd HH:MM"
     * @return
     */
    public static String getFormatTimeFromTimestamp(long timestamp, String format) {
        if (format == null || format.trim().equals("")) {
            sdf.applyPattern(FORMAT_DATE);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int year = Integer.valueOf(sdf.format(new Date(timestamp)).substring(0, 4));
            if (currentYear == year) {// 如果为今年则不显示年份
                sdf.applyPattern(FORMAT_MONTH_DAY_TIME);
            } else {
                sdf.applyPattern(FORMAT_DATE_TIME);
            }
        } else {
            sdf.applyPattern(format);
        }
        Date date = new Date(timestamp);
        return sdf.format(date);
    }

    /**
     * 根据时间戳获取时间字符串，并根据指定的时间分割数partionSeconds来自动判断返回描述性时间还是指定格式的时间
     *
     * @param timestamp      时间戳 单位是毫秒
     * @param partionSeconds 时间分割线，当现在时间与指定的时间戳的秒数差大于这个分割线时则返回指定格式时间，否则返回描述性时间
     * @param format
     * @return
     */
    public static String getMixTimeFromTimestamp(long timestamp, long partionSeconds, String format) {
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
        if (timeGap <= partionSeconds) {
            return getDescriptionTimeFromTimestamp(timestamp);
        } else {
            return getFormatTimeFromTimestamp(timestamp, format);
        }
    }

    /**
     * 获取当前日期的指定格式的字符串
     *
     * @param format 指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
     * @return
     */
    public static String getCurrentTime(String format) {
        if (format == null || format.trim().equals("")) {
            sdf.applyPattern(FORMAT_DATE_TIME);
        } else {
            sdf.applyPattern(format);
        }
        return sdf.format(new Date());
    }

    /**
     * 获取当前时间后N分钟的时间
     *
     * @param seconds 后推时间转换成毫秒数
     * @return
     */
    public static String getTimeAfterNow(int seconds) {
        long curren = System.currentTimeMillis();
        curren += seconds;
        Date da = new Date(curren);
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(da);
    }

    /**
     * 获取年月日时分秒的拼接
     */
    public static String getNYRSFM() {
        long curren = System.currentTimeMillis();
        Date da = new Date(curren);
        Calendar cal = Calendar.getInstance();
        cal.setTime(da);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day=cal.get(Calendar.DATE);
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        int minute=cal.get(Calendar.MINUTE);
        int second=cal.get(Calendar.SECOND);
        return ""+year+month+day+hour+minute+second;
    }
    /**
     * 将日期字符串以指定格式转换为Date
     *
     * @param timeStr 日期字符串
     * @param format  指定的日期格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
     * @return
     */
    public static Date getTimeFromString(String timeStr, String format) {
        if (format == null || format.trim().equals("")) {
            sdf.applyPattern(FORMAT_DATE_TIME);
        } else {
            sdf.applyPattern(format);
        }
        try {
            return sdf.parse(timeStr);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 将Date以指定格式转换为日期时间字符串
     *
     * @param date   日期
     * @param format 指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
     * @return
     */
    public static String getStringFromTime(Date date, String format) {
        if (format == null || format.trim().equals("")) {
            sdf.applyPattern(FORMAT_DATE_TIME);
        } else {
            sdf.applyPattern(format);
        }
        return sdf.format(date);
    }

    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_YEAR_MONTH_DAY);
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;

    }

    /**
     * 比较两个时间的先后顺序
     *
     * @param time1
     * @param time2
     * @return 1：dt1 在dt2前 -1： dt1在dt2后 0： 相等
     */
    public static int compare2Times(String time1, String time2) {

        String t1 = "2016-01-01 " + time1 + ":00";
        String t2 = "2016-01-01 " + time2 + ":00";
        try {
            Date date1 = parse(t1);
            Date date2 = parse(t2);
            if (date1.getTime() < date2.getTime()) {

                return 1;
            } else if (date1.getTime() > date2.getTime()) {

                return -1;
            } else {// 相等
                return 0;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public static Boolean compare2Times02(String time1, String time2) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        try {
            c1.setTime(df.parse(time1));
            c2.setTime(df.parse(time2));
        } catch (ParseException e) {
            System.err.println("格式不正确");
        }
        int result = c1.compareTo(c2);
        if (result == 0) {
            return false;
        } else if (result < 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 使用预设格式将字符串转为Date
     */
    public static Date parse(String strDate) throws ParseException {
        return EmptyUtil.isEmpty(strDate) ? null : parse(strDate, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 使用参数Format将字符串转为Date
     */
    public static Date parse(String strDate, String pattern) throws ParseException {
        return EmptyUtil.isEmpty(strDate) ? null : new SimpleDateFormat(pattern).parse(strDate);
    }

    /**
     * 判断日期是否在当前日期之前
     *
     * @return
     */
    public static boolean dateIsBeforeNowDate(String tempDate, String format) {
        // tempDate = tempDate + " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String nowTime = sdf.format(new Date());

        Calendar cal1 = Calendar.getInstance();
        Calendar cNow = Calendar.getInstance();
        try {
            cal1.setTime(sdf.parse(tempDate));
            cNow.setTime(sdf.parse(nowTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = cal1.compareTo(cNow);
        return result < 0;
    }

    /**
     * 判断日期1是否在日期2之前
     *
     * @return
     */
    public static boolean dateIsBeforeDate(String tempDate1, String tempDate2) {
        // tempDate = tempDate + " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        try {
            cal1.setTime(sdf.parse(tempDate1));
            cal2.setTime(sdf.parse(tempDate2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = cal1.compareTo(cal2);
        return result < 0;
    }

    /**
     * 两个String 日期相差天数
     *
     * @return
     */
    public static int betweenDate(String tempDate1, String tempDate2, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        int day1 = 0;
        int day2 = 0;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        try {
            cal1.setTime(sdf.parse(tempDate1));
            cal2.setTime(sdf.parse(tempDate2));
            day1 = cal1.get(Calendar.DAY_OF_YEAR);
            day2 = cal2.get(Calendar.DAY_OF_YEAR);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day2 - day1;
    }

    /**
     * 判断日期1是否在日期2之前 指定格式
     *
     * @return
     */
    public static boolean dateIsBeforeDate(String tempDate1, String tempDate2, String format) {
        // tempDate = tempDate + " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        try {
            cal1.setTime(sdf.parse(tempDate1));
            cal2.setTime(sdf.parse(tempDate2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = cal1.compareTo(cal2);
        return result < 0;
    }

    /**
     * 判断日期1是否在日期2之前 指定格式(含日期相同)
     *
     * @return
     */
    public static boolean dateIsBeforeOrEqualDate(String tempDate1, String tempDate2, String format) {
        // tempDate = tempDate + " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        try {
            cal1.setTime(sdf.parse(tempDate1));
            cal2.setTime(sdf.parse(tempDate2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = cal1.compareTo(cal2);
        return result <= 0;
    }

    /**
     * 某个时间和当前时间比较
     *
     * @return result:1：某时间 在 当前时间 之前 -1：某时间 在 当前时间 之后 0： 相等
     */
    public static int TimeCompareNow(String tempTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String nowTime = sdf.format(new Date());
        // tempTime = sdf.format(tempTime);

        Calendar cal1 = Calendar.getInstance();
        Calendar cNow = Calendar.getInstance();

        try {
            cal1.setTime(sdf.parse(tempTime));
            cNow.setTime(sdf.parse(nowTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = cal1.compareTo(cNow);
        return result;

    }

    /**
     * 某个时间和当前时间比较
     *
     * @return result:1：某时间 在 当前时间 之前 -1：某时间 在 当前时间 之后 0： 相等
     */
    public static int TimeCompareNowFormat(String tempTime, String s) {

        SimpleDateFormat sdf = new SimpleDateFormat(s);
        String nowTime = sdf.format(new Date());
        // tempTime = sdf.format(tempTime);

        Calendar cal1 = Calendar.getInstance();
        Calendar cNow = Calendar.getInstance();

        try {
            cal1.setTime(sdf.parse(tempTime));
            cNow.setTime(sdf.parse(nowTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = cal1.compareTo(cNow);
        return result;

    }

    /**
     * 时间和当前时间比较，并计算相差天数
     *
     * @param tempTime
     * @return days相差天数
     */
    public static long diffTime2Now(String tempTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm：ss");
        String nowTime = sdf.format(new Date());
        // tempTime = sdf.format(tempTime);
        long days = 0;
        try {
            Date d1 = sdf.parse(tempTime);
            Date dnow = sdf.parse(nowTime);

            long diff = d1.getTime() - dnow.getTime();
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;

    }

    /**
     * 时间和当前时间比较，并计算相差天数
     *
     * @param tempTime
     * @return days相差天数
     */
    public static long diffTime2Today(String tempTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String nowTime = sdf.format(new Date());
        // tempTime = sdf.format(tempTime);
        long days = 0;
        try {
            Date d1 = sdf.parse(tempTime);
            Date dnow = sdf.parse(nowTime);

            long diff = d1.getTime() - dnow.getTime();
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    public static String formatDayTime(long time) {
        String dayTime = "";
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final Date date = new Date(time);
        dayTime = sdf.format(date);
        return dayTime;
    }

    public static String formatMinTime(long time) {
        String dayTime = "";
        final SimpleDateFormat sdf = new SimpleDateFormat("HH-mm");
        final Date date = new Date(time);
        dayTime = sdf.format(date);
        return dayTime;
    }

    public static String formatAllTime(long time) {
        String dayTime = "";
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Date date = new Date(time);
        dayTime = sdf.format(date);
        return dayTime;
    }

    public static String formatTimeforfly(String time) {
        String dayTime = "";
        DateFormat df1 = new SimpleDateFormat("yyyy-M-d");
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date data = null;
        try {
            data = df1.parse(time);
            dayTime = sdf.format(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dayTime.toString();
    }

    public static String formatTime(String time) {
        String dayTime = "";
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        Date data = null;
        try {
            data = df1.parse(time);
            dayTime = sdf.format(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dayTime.toString();
    }

    public static String formatTimeYMD(String time) {
        String dayTime = "";
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date data = null;
        try {
            data = df1.parse(time);
            dayTime = sdf.format(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dayTime.toString();
    }

    public static String formatTimeFly(String time) {
        String dayTime = "";
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date data = null;
        try {
            data = df1.parse(time);
            dayTime = sdf.format(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dayTime.toString();
    }

    /**
     * 比较时间 具体到天
     *
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static boolean compareTime(long DATE1, String DATE2) {
        boolean flag = false;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(formatDayTime(DATE1));
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() < dt2.getTime()) {
                flag = true;
            } else if (dt1.getTime() >= dt2.getTime()) {
                flag = false;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return flag;
    }

    /**
     * 比较时间 具体到天
     *
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static boolean compareTimeforHotel(String DATE1, String DATE2) {
        boolean flag = false;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() < dt2.getTime()) {
                flag = true;
            } else if (dt1.getTime() >= dt2.getTime()) {
                flag = false;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据时间返回欢迎语句 凌晨：0-5 早上：5-11 中午：11-13 下午：13-17 傍晚：17-19 晚上：19-24
     */
    public static String showTime(long data) {
        String str = "";
        final Date date = new Date(data);
        int currentHour = date.getHours();
        if (currentHour >= 0 && currentHour < 5) {
            str = "亲，注意身体哦";
        } else if (currentHour >= 5 && currentHour < 11) {
            str = "亲，早上好";
        } else if (currentHour >= 11 && currentHour < 13) {
            str = "亲，中午好";
        } else if (currentHour >= 13 && currentHour < 17) {
            str = "亲，下午好";
        } else if (currentHour >= 17 && currentHour < 19) {
            str = "亲，饿了吧";
        } else if (currentHour >= 19 && currentHour < 24) {
            if (currentHour > 22) {
                str = "亲，该休息了哦";
            }
            str = "亲，晚上好";
        }

        return str;
    }

    public static boolean compareMinTime(String DATE1, String DATE2) {
        boolean flag = false;
        DateFormat df = new SimpleDateFormat("HH-mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() < dt2.getTime()) {
                flag = true;
            } else if (dt1.getTime() >= dt2.getTime()) {
                flag = false;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return flag;
    }

    /**
     * 转成分段时间
     *
     * @param str
     * @return
     */
    public static ArrayList<String> getShowTimeArray(String str, int spaceTime, boolean flag) {
        ArrayList<String> tmp = new ArrayList<String>();
        String[] tmpStr1 = str.trim().split("-");
        String[] tmpStr2 = tmpStr1[0].trim().split(":");
        String[] tmpStr3 = tmpStr1[1].trim().split(":");
        int start = 0;
        int end = 0;
        try {
            start = Integer.parseInt(tmpStr2[0].trim()) * 60 + Integer.parseInt(tmpStr2[1].trim());
            end = Integer.parseInt(tmpStr3[0].trim()) * 60 + Integer.parseInt(tmpStr3[1].trim());

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (start >= end) {
            end += 24 * 60;
            int zstart = 0;
            int zend = end;
            if (flag) {
                // 每半个小小时添加一次
                for (int i = 0; i <= (zend - zstart) / spaceTime; i++) {
                    tmp.add(String.format("%1$02d", (start + i * spaceTime) / 60 % 24) + ":"
                            + String.format("%1$02d", (start + i * spaceTime) % 60));
                }
            }
            for (int i = 0; i <= (24 - start) / spaceTime; i++) {
                tmp.add(String.format("%1$02d", (start + i * spaceTime) / 60 % 24) + ":"
                        + String.format("%1$02d", (start + i * spaceTime) % 60));
            }
        } else {
            // if(end == 24 * 60){
            // end = 23*60+30;
            // // 每半个小小时添加一次
            // for (int i = 0; i <= (end - start) / spaceTime; i++) {
            // tmp.add(String.format("%1$02d", (start + i * spaceTime) / 60 %
            // 24) + ":" + String.format("%1$02d", (start + i * spaceTime) %
            // 60));
            // }
            // }else {
            // 每半个小小时添加一次
            for (int i = 0; i <= (end - start) / spaceTime; i++) {
                int time = start + i * spaceTime;
                if (time < end) {
                    tmp.add(String.format("%1$02d", (start + i * spaceTime) / 60 % 24) + ":"
                            + String.format("%1$02d", (start + i * spaceTime) % 60));
                }
            }
            // }
        }
        return tmp;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowTime() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
        String hehe = dateFormat.format(now);
        return hehe;
    }

    public static String getAddDay(int day) {
        Date now = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(now);
        calendar.add(calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
        now = calendar.getTime(); // 这个时间就是日期往后推一天的结果
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");// 可以方便地修改日期格式
        String hehe = dateFormat.format(now);
        return hehe;
    }

    public static String getAddDayFormat(int day, String s) {
        Date now = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(now);
        calendar.add(calendar.DATE, day);// 把日期往后增加一天.整数往后推,负数往前移动
        now = calendar.getTime(); // 这个时间就是日期往后推一天的结果
        SimpleDateFormat dateFormat = new SimpleDateFormat(s);// 可以方便地修改日期格式
        String hehe = dateFormat.format(now);
        return hehe;
    }

    /*对年月字符型日期的加减
    * nowString 待处理的日期 2016年08月
    * i  0加一月 1减一月
    * */
    public static String doMonthOfDate(String nowString, int i, String forma) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(forma);

            Date beginDate = format.parse(nowString);
            Calendar rightNow = Calendar.getInstance();
            rightNow.setTime(beginDate);
            if (i == 0) {
                rightNow.add(Calendar.MONTH, 1);

            } else {
                rightNow.add(Calendar.MONTH, -1);

            }
            return format.format(rightNow.getTime());

        } catch (ParseException e) {
            e.printStackTrace();
            return nowString;
        }
    }

    /**
     * 将毫秒数换算成x天x时x分x秒x毫秒
     *
     * @param ms
     * @return
     */
    public static String[] msToFormat(long ms) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        String strDay = day < 10 ? "0" + day : "" + day;
        String strHour = hour < 10 ? "0" + hour : "" + hour;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
        String[] strs = new String[5];
        strs[0] = strDay;
        strs[1] = strHour;
        strs[2] = strMinute;
        strs[3] = strSecond;
        strs[4] = strMilliSecond;
        // return strDay + " " + strHour + ":" + strMinute + ":" + strSecond +
        // " " + strMilliSecond;
        return strs;
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 字符串的日期格式的计算()
     *
     * @return between_days (结果为i,但是返回i+1,即包含选择日期的收尾)
     */
    public static int daysBetween(String smdate, String bdate, String format) {
        long between_days = 0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Calendar cal = Calendar.getInstance();

            cal.setTime(sdf.parse(smdate));

            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            between_days = (time2 - time1) / (1000 * 3600 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(String.valueOf(between_days)) + 1;
    }

    /**
     * 判段日期格式，日期是否存在
     *
     * @param str
     * @param formats
     * @return
     */
    public static boolean isValidDate(String str, String formats) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat(formats);
        try {
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }


    //获取当前的星期
    public static String getNowWeek() {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

            Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];

    }



//    //获取农历的日期
//    public static String getNlDate(){
//        String year = convertNlYear(getNlMinYear());
//        String month = convertNlMoeth(getNlMinMonth());
//        String day = convertNlDay(getNlMinDay());
//        return year+ " 年　" + month + "月　" + day;
//    }

    public static String getDateWithWeek(String tempDate) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(tempDate);
            if (CustomDateUtil.isToday(date)) {
                return tempDate + " " + "今天";
            } else {
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
                if (w < 0)
                    w = 0;
                return tempDate + " " + weekDays[w];
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tempDate;
    }

    /**
     * 将String型格式化,比如想要将2011-11-11格式化成2011年11月11日,就StringPattern("2011-11-11","yyyy-MM-dd","yyyy年MM月dd日").
     *
     * @param date       String 想要格式化的日期
     * @param oldPattern String 想要格式化的日期的现有格式
     * @param newPattern String 想要格式化成什么格式
     * @return String
     */
    public static String StringPattern(String date, String oldPattern, String newPattern) {
        if (date == null || oldPattern == null || newPattern == null)
            return "";
        if (TextUtils.isEmpty(date)) {
            return "";
        }
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern);        // 实例化模板对象
        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern);        // 实例化模板对象
        Date d = null;
        try {
            d = sdf1.parse(date);   // 将给定的字符串中的日期提取出来
        } catch (Exception e) {            // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace();       // 打印异常信息
        }
        return sdf2.format(d);
    }


    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);

        return result;
    }

    /**
     * 获取未来 第 past 天的日期
     *
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);

        return result;
    }

    /**
     * 获取未来 第 past 天的日期
     *
     * @param past
     * @return
     */
    public static String getFetureDate(String dateString, int past) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = java.sql.Date.valueOf(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);

        return result;
    }


    /**
     * 判断日期是否在指定日期之前  <0在此之前 true
     *
     * @return
     */
    public static boolean dateIsBeforeSupDate(String tempDate, String supDate) {
//        tempDate = tempDate + " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String nowTime = sdf.format(new Date());

        Calendar cal1 = Calendar.getInstance();
        Calendar cNow = Calendar.getInstance();
        try {
            cal1.setTime(sdf.parse(tempDate));
            cNow.setTime(sdf.parse(supDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = cal1.compareTo(cNow);
        return result <= 0;
    }

    /****
     * 传入具体日期 ，返回具体日期减一个月。
     *
     * @param date
     *            日期(2014-04-20)
     * @return 2014-04+i-20
     * @throws ParseException
     */
    public static String addMonth(String date, int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = new Date();
        try {
            dt = sdf.parse(date);
        } catch (Exception e) {

        }
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);

        rightNow.add(Calendar.MONTH, i);
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);

        return reStr;
    }

    /****
     * 传入具体日期 ，返回具体日期减一个月。
     *
     * @return 2014-04+i-20
     * @throws ParseException
     */
    public static String newDateaddMonth(int i) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date dt = new Date();

        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(dt);

        rightNow.add(Calendar.MONTH, i);
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);

        return reStr;
    }


    /**
     * 判断某日期是否在未来{$month}个月之内
     *
     * @param tempDate 待比较的时间
     * @param month    比较未来月份
     * @return
     */
    public static boolean dateIsInMonth(String tempDate, int month) {
        return dateIsBeforeSupDate(tempDate,TimeUtil.addMonth(CustomDateUtil.modifyDate(0), month));
    }
}
