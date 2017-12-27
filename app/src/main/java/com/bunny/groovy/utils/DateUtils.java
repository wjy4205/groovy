package com.bunny.groovy.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/****************************************
 * 功能说明:  
 *
 * Author: Created by bayin on 2017/12/20.
 ****************************************/

public class DateUtils {

    //获取小时
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(calendar.HOUR_OF_DAY);
    }

    //获取分钟
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(calendar.MINUTE);
    }

    //获取周
    public static int getWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //获取周
    public static int getWeek(int year, int moth, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, moth - 1, day);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    //获取年
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(calendar.YEAR);
    }

    //获取月
    public static int getMoth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(calendar.MONTH) + 1;
    }

    //获取日
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(calendar.DATE);
    }

    public static Date getDate(int year, int moth, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, moth - 1, day, hour, minute);
        return calendar.getTime();
    }

    public static Date getDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        return calendar.getTime();
    }

    /**
     * @param date 日期
     * @param time 时间
     * @return yyyy-MM-dd HH:mm
     */
    public static String getFormatTime(Date date, String time) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String[] split = time.split(":");
        if (split.length == 2) {
            date.setHours(Integer.parseInt(split[0]));
            date.setMinutes(Integer.parseInt(split[1]));
        }
        return dateFormat.format(date);
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }


    public static String[] getWeekStartEndDate(){
        String[] startEnd = new String[2];
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        System.out.println("今天周"+day);
        int dd = calendar.get(Calendar.DATE);
        System.out.println("今天"+dd+"号");
        return startEnd;
    }

    public static void main(String[] args) {
        getWeekStartEndDate();
    }
}
