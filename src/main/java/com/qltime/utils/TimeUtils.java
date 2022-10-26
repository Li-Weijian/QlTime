package com.qltime.utils;

import java.util.Date;

/**
 * @Auther: liweijian
 * @Date: 2019/7/13 11:14
 * @Description:
 */
public class TimeUtils {
    //long day = daysBetween(new Date(Integer.parseInt(YEAR) - 1900, Integer.parseInt(MONTH) - 1, Integer.parseInt(DAY)), new Date());
    public static long daysBetween(Date one, Date two) {
        long difference =  (one.getTime()-two.getTime())/86400000;
        return Math.abs(difference);
    }
}
