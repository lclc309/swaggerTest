package com.lclc.test.util;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.stereotype.Service;

// 操作时间的工具类
@Service
public class TimeUtil {

    public static Date now() {

        return DateTime.now().toDate();
    }

    // 获取i分钟之前的时间
    public static Date getPreMin(int i) {

        return getDateTimePreMin(i).toDate();
    }

    // 获取date i分钟之前的时间
    public static Date getPreMin(Date date, int i) {

        DateTime now = new DateTime(date);
        return now.plusMinutes(0 - i).toDate();
    }

    // 增加分钟
    public static Date addMinutes(Date date, int min) {

        DateTime now = new DateTime(date);
        return now.plusMinutes(min).toDate();
    }

    public static Date addDay(Date date, int day) {

        DateTime now = new DateTime(date);

        return now.plusDays(day).toDate();
    }

    public static long betweenDays(Date startDate, Date endDate) {

        return (endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24);
    }

    public static long betweenMin(Date startDate, Date endDate) {

        return (endDate.getTime() - startDate.getTime()) / (1000 * 60);
    }

    public static DateTime getDateTimePreMin(int i) {

        return DateTime.now().plusMinutes(0 - i);
    }

    public static Date parseTime(String reportTime, String formatter) {

        return DateTime.parse(reportTime, DateTimeFormat.forPattern(formatter)).toDate();
    }

    public static String time2String(Date date, String pattern) {

        return new DateTime(date).toString(pattern);
    }

    /**
     * 比较timeOne是否大于timeTwo
     * 
     * @param timeOne
     * @param timeTwo
     * @return
     */
    public static Boolean compare(Date timeOne, Date timeTwo) {

        return timeOne.getTime() > timeTwo.getTime();
    }

    /**
     * 比较timeOne是否大于等于timeTwo
     * 
     * @param timeOne
     * @param timeTwo
     * @return
     */
    public static Boolean compareEqual(Date timeOne, Date timeTwo) {

        return timeOne.getTime() >= timeTwo.getTime();
    }

    // 获得本月第一天0点时间
    public static Date getTimesMonthmorning() {

        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    // 获得本月最后一天24点时间
    public static Date getTimesMonthnight() {

        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return cal.getTime();
    }
}
