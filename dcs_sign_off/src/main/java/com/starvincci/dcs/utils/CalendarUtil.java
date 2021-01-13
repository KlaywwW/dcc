package com.starvincci.dcs.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CalendarUtil {

    SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public boolean isSame(String strDate){

//        String strDate="2020-11-12 18:48:37";

        Date date=null;
        try {
            date=sf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //获取东八区时间
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        c.setTime(date);
        //获取年
        int year = c.get(Calendar.YEAR);
        //获取月份，会少一天。所以+1
        int month = c.get(Calendar.MONTH) + 1;
        //获取当前天数
        int day = c.get(Calendar.DAY_OF_MONTH);
        //获取当前小时
        int t = c.get(Calendar.HOUR_OF_DAY);
        //获取当前分钟
        int m = c.get(Calendar.MINUTE);
        //获取当前秒
        int se = c.get(Calendar.SECOND);

        SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return year==year()&&month==month()&&day==day();
    }

    /**
     * 判断是否是今天以前的时间
     * @return
     */
    public boolean ltNow(String strDate){
        Date date=null;
        try {
            date=sf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //获取东八区时间
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        c.setTime(date);
        //获取年
        int year = c.get(Calendar.YEAR);
        //获取月份，会少一天。所以+1
        int month = c.get(Calendar.MONTH) + 1;
        //获取当前天数
        int day = c.get(Calendar.DAY_OF_MONTH);
        if (year<year()){
            return true;
        }else if(year==year()&&month<month()){
            return true;
        }else if(year==year()&&month==month()&&day<=day()){
            return true;
        }
        return false;
    }

    public int year(){
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        c.getTime();
        return c.get(Calendar.YEAR);
    }
    public int month(){
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        c.getTime();
        return c.get(Calendar.MONTH)+1;
    }
    public int day(){
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        c.getTime();
        return c.get(Calendar.DAY_OF_MONTH);
    }

}
