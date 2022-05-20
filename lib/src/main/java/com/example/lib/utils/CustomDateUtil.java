package com.example.lib.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * about the time util.
 */

public class CustomDateUtil {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    /**
     * @return return the date of today ,the format is yyyy-mm-dd
     */
    public static String getNowDate() {
        Date today = new Date();
        String todayDay = dateFormat.format(today);
        return todayDay;
    }

    public static String getNowMonth(String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date today = new Date();
        String todayDay = sdf.format(today);
        return todayDay;
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
            return sdf2.format(d);
        } catch (Exception e) {            // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace();
            return "";// 打印异常信息
        }
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
        String[] tmpStr3 = tmpStr1[1].trim().replace("次日","").split(":"); //如果包含次日，把次日替换
        int start = 0;
        int end = 0;
        try {
            start = Integer.parseInt(tmpStr2[0].trim()) * 60 + Integer.parseInt(tmpStr2[1].trim());
            end = Integer.parseInt(tmpStr3[0].trim()) * 60 + Integer.parseInt(tmpStr3[1].trim());

            if (start > end) {
                end += 24 * 60;
                int zstart = start;
                int zend = end;
                if (flag) {
                    // 每半个小小时添加一次
                    for (int i = 0; i <= ((zend - zstart) / spaceTime); i++) {
                        tmp.add(String.format("%1$02d", (start + i * spaceTime) / 60 % 24) + ":"
                                + String.format("%1$02d", (start + i * spaceTime) % 60));
                    }
                }

            } else {

                for (int i = 0; i <= (end - start) / spaceTime; i++) {
                    int time = start + i * spaceTime;
                    if (time <= end) {
                        tmp.add(String.format("%1$02d", (start + i * spaceTime) / 60 % 24) + ":"
                                + String.format("%1$02d", (start + i * spaceTime) % 60));
                    }
                }
                // }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tmp;
    }

    /**
     * 获取某天是星期几,参数格式是：2012-03-12
     *
     * @param preString 前缀字符串，如：周*，星期*
     * @param pTime
     * @return 返回类型为“一”、“二”……
     */
    public static String getWeekName(String preString, String pTime) {
        String Week = preString;
        // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(dateFormat.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            Week += "日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            Week += "六";
        }

        return Week;
    }

    /**
     * 参数是秒
     *
     * @param data
     * @return
     */
    public static String getTime(String data) {
        long time = Long.parseLong(data);// 秒
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(time * 1000);
        return dateFormat.format(gc.getTime());
    }


    /**
     * 获取几个月后的某月
     *
     * @param num
     * @return
     */
    public static String getMonthAfterNum(int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(calendar.MONTH, num);
        Date temMonth = calendar.getTime(); //结果
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
        return sf.format(temMonth);
    }

    /**
     * 日期加减（默认和当前时间做比较）
     *
     * @param number
     * @throws ParseException
     */
    public static String modifyDate(int number) {

        Calendar calendar = Calendar.getInstance();
        Date tempDate = new Date();
        calendar.setTime(tempDate);
        calendar.add(Calendar.DAY_OF_YEAR, number);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(calendar.getTime());
    }

    /**
     * 日期加减
     *
     * @param date   格式 yyyy-mm-dd
     * @param number
     * @throws ParseException
     */
    public static String modifyDate(String date, int number)
            throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date tempDate = dateFormat.parse(date);
        calendar.setTime(tempDate);
        calendar.add(Calendar.DAY_OF_YEAR, number);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(calendar.getTime());
    }


    /**
     * 获取当天 前后的日期，index=1为明天，index=-1 为昨天
     */

    public static String getLaterDay(int index, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String string = new String();
        Calendar c = Calendar.getInstance();
        Date dNow = new Date();
        c.setTime(dNow);
        c.add(Calendar.DATE, index);
        Date newDate = c.getTime();
        string = sdf.format(newDate);
        return string;
    }


    /**
     * @param tempDate1
     * @param tempDate2
     * @return
     */
    public static int dateCompare(String tempDate1, String tempDate2) {
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

        return result;
    }

    /**
     * 判断日期是否在当前日期之前
     *
     * @return
     */
    public static boolean dateIsBeforeNowDate(String tempDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
     * 是否是今天
     *
     * @param strDate "yyyy-MM-dd"格式的时间
     * @return
     */
    public static boolean isTodayStr(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dNow = sdf.parse(strDate);
            return isToday(dNow);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 是否是今天
     *
     * @param date
     * @return
     */
    public static boolean isToday(final Date date) {
        return isSomeDay(date, new Date());
    }

    /**
     * 是否是指定日期
     *
     * @param date
     * @param day
     * @return
     */
    public static boolean isSomeDay(final Date date, final Date day) {
        return date.getTime() >= dayBegin(day).getTime()
                && date.getTime() <= dayEnd(day).getTime();
    }

    /**
     * 获取指定时间的那天 00:00:00.000 的时间
     *
     * @param date
     * @return
     */
    public static Date dayBegin(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定时间的那天 23:59:59.999 的时间
     *
     * @param date
     * @return
     */
    public static Date dayEnd(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }


    /**
     * 两个时间比较，并计算相差天数
     *
     * @param tempTime1
     * @param tempTime2
     * @return days相差天数
     */
    public static long diffTime2OtherDay(String tempTime1, String tempTime2) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // tempTime = sdf.format(tempTime);
        long days = 0;
        try {
            Date d1 = sdf.parse(tempTime1);
            Date dnow = sdf.parse(tempTime2);
            long diff = d1.getTime() - dnow.getTime();
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;

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
     * 判断日期1是否在日期2之前
     *
     * @return
     */
    public static boolean dateIsBeforeDate(String tempDate1, String tempDate2) {
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
     * 时间是否在某个时间段内
     *
     * @param tempTime 待比较的时间
     * @param time1    时间段开始时间
     * @param time2    时间段结束时间
     * @return 时间是否在某个时间段内
     */
    public static boolean isInDate(String tempTime, String time1, String time2) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar calTemp = Calendar.getInstance();
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        try {
            calTemp.setTime(sdf.parse(tempTime));
            cal1.setTime(sdf.parse(time1));
            cal2.setTime(sdf.parse(time2));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (calTemp.compareTo(cal1) < 0 || calTemp.compareTo(cal2) > 0) {
            return false;
        }
        return true;
    }


    /**
     * 获取{某个时间}的前一天时间
     *
     * @param tempDate yyyy-MM-dd格式的
     * @return
     */
    public static String getBeforeDay(String tempDate) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(tempDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        //使用roll方法进行向前回滚
        //cl.roll(Calendar.DATE, -1);
        //使用set方法直接进行设置
        int day = cl.get(Calendar.DATE);
        cl.set(Calendar.DATE, day - 1);

        return sdf.format(cl.getTime());
    }

    /**
     * 获取{某个时间}的后一天时间
     *
     * @param tempDate yyyy-MM-dd格式的
     * @return
     */
    public static String getAfterDay(String tempDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(tempDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        //使用roll方法进行向前回滚
        //cl.roll(Calendar.DATE, -1);
        //使用set方法直接进行设置
        int day = cl.get(Calendar.DATE);
        cl.set(Calendar.DATE, day + 1);
        return sdf.format(cl.getTime());
    }

    /**
     * 获取今天的后{N}天日期
     *
     * @param
     * @return yyyy-MM-dd
     */
    public static String getAfterNDay(int N) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        Date date = new Date();
//        try {
//            date = sdf.parse(tempDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        //使用roll方法进行向前回滚
        //cl.roll(Calendar.DATE, -1);
        //使用set方法直接进行设置
        int day = cl.get(Calendar.DATE);
        cl.set(Calendar.DATE, day + N);
        return sdf.format(cl.getTime());
    }
}
