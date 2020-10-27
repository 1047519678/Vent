package cn.mvc.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {
    public static Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
    public static int year = c.get(Calendar.YEAR);
    public static int month = c.get(Calendar.MONTH) + 1;
    public static int date = c.get(Calendar.DATE);
    public static int hour = c.get(Calendar.HOUR_OF_DAY);
    public static int minute = c.get(Calendar.MINUTE);
    public static int second = c.get(Calendar.SECOND);


    public static String GetDateTime(String format){
        String Datetime = "";
        if("yyyy-MM-dd HH:mm:ss".equals(format)) {
            Datetime = year + "-";
            if (month < 10) {
                Datetime = Datetime + "0" + month + "-";
            } else {
                Datetime = Datetime + month + "-";
            }
            if (date < 10) {
                Datetime = Datetime + "0" + date + " ";
            } else {
                Datetime = Datetime + date + " ";
            }
            if (hour < 10) {
                Datetime = Datetime + "0" + hour + ":";
            } else {
                Datetime = Datetime + hour + ":";
            }
            if (minute < 10) {
                Datetime = Datetime + "0" + minute + ":";
            } else {
                Datetime = Datetime + minute + ":";
            }
            if (second < 10) {
                Datetime = Datetime + "0" + second;
            } else {
                Datetime = Datetime + second;
            }
        }else if("yyyy/MM/dd HH:mm:ss".equals(format)){
            Datetime = year + "/";
            if (month < 10) {
                Datetime = Datetime + "0" + month + "/";
            } else {
                Datetime = Datetime + month + "/";
            }
            if (date < 10) {
                Datetime = Datetime + "0" + date + " ";
            } else {
                Datetime = Datetime + date + " ";
            }
            if (hour < 10) {
                Datetime = Datetime + "0" + hour + ":";
            } else {
                Datetime = Datetime + hour + ":";
            }
            if (minute < 10) {
                Datetime = Datetime + "0" + minute + ":";
            } else {
                Datetime = Datetime + minute + ":";
            }
            if (second < 10) {
                Datetime = Datetime + "0" + second;
            } else {
                Datetime = Datetime + second;
            }
        }else if("yyyy/MM/dd".equals(format)){
            Datetime = year + "/";
            if (month < 10) {
                Datetime = Datetime + "0" + month + "/";
            } else {
                Datetime = Datetime + month + "/";
            }
            if (date < 10) {
                Datetime = Datetime + "0" + date;
            } else {
                Datetime = Datetime + date;
            }
        }else if("yyyy-MM-dd".equals(format)){
            Datetime = year + "-";
            if (month < 10) {
                Datetime = Datetime + "0" + month + "-";
            } else {
                Datetime = Datetime + month + "-";
            }
            if (date < 10) {
                Datetime = Datetime + "0" + date;
            } else {
                Datetime = Datetime + date;
            }
        }else if("yyyyMMdd".equals(format)){
            Datetime = "" + year;
            if (month < 10) {
                Datetime =  Datetime + "0" + month;
            } else {
                Datetime = Datetime + month;
            }
            if (date < 10) {
                Datetime = Datetime + "0" + date;
            } else {
                Datetime = Datetime + date;
            }
        }
        return Datetime;
    }

    public static String getDateTime2(String format){
        return new SimpleDateFormat(format).format(new Date());
    }

    public static int GetYear(){
        return year;
    }

    public static int GetMonth(){
        return month;
    }

    public static int GetDate(){
        return date;
    }

    public static int GetHour(){
        return hour;
    }

    public static int GetMinute(){
        return minute;
    }

    public static int GetSecond(){
        return second;
    }


    public  static Map<String,Object> getKanBanDate(Date nowdate) throws ParseException {
        //String date = "";
        Map<String,Object> newdate = new HashMap<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = new GregorianCalendar();
        cal.setTime(nowdate);
        for (int i = 2;i<=7;i++){
            cal.add(Calendar.DAY_OF_MONTH,1);
            String date = df.format(cal.getTime());
            String key = "xdatenewweek" + i;
            newdate.put(key,date);
        }
        //添加周
        cal.add(Calendar.WEEK_OF_YEAR,-1);
        int week =  c.get(Calendar.WEEK_OF_YEAR);
        newdate.put("xdateweek1",week+"周");
        newdate.put("xdateweek2",week-1+"周");
        newdate.put("xdateweek3",week-2+"周");

        //添加月
        cal.add(Calendar.MONTH,-1);
        int month =  c.get(Calendar.MONTH);
        newdate.put("xdatemonth3",month-3+"月");
        newdate.put("xdatemonth2",month-2+"月");
        newdate.put("xdatemonth1",month-1+"月");
        return  newdate;
    }


    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static String getStartEndOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 当前日期为本周第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 获取本周开始日期
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String weekBegin = sdf.format(cal.getTime());
        // 获取本周结束时间
        cal.add(Calendar.DATE, 6);
        String weekEnd = sdf.format(cal.getTime());
        return weekBegin + "," + weekEnd;
    }

    public static Map<String,Object> findDates(Date dBegin, Date dEnd) {
        Map<String,Object> map = new HashMap<>();
        int i = 2;
        Calendar c =  new GregorianCalendar();
        int week = c.get(Calendar.WEEK_OF_YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int week1 = 0;
        int week2 = 0;
        int week3 = 0;
        Calendar calBegin = Calendar.getInstance();

        // 获取当前周全部日期。
        map.put("xdatenewweek1",sdf.format(dBegin));
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(dEnd);
        while (dEnd.after(calBegin.getTime()))
        {
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            map.put("xdatenewweek" + i,sdf.format(calBegin.getTime()));
            i++;
        }

        // 获取前三周，跨年处理。
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(4);
        //c.setTime (sdf.parse("2020-12-31"));
        c.setTime (new Date());

        if(month == 1 && week>6){
            week1 = week-1;
            week2 = week-2;
            week3 = week-3;
        }else if(month == 12 && week==1){
            week1 = 52;
            week2 = week1-1;
            week3 = week1-2;
        }else{
            week1 = week-1;
            week2 = week-2;
            week3 = week-3;
        }
        map.put("xdateweek1",week1+"周");
        map.put("xdateweek2",week2+"周");
        map.put("xdateweek3",week3+"周");

        c.add(Calendar.MONTH,-1);
        int montha =  c.get(Calendar.MONTH);
        map.put("xdatemonth3",montha-2+"月");
        map.put("xdatemonth2",montha-1+"月");
        map.put("xdatemonth1",montha+"月");
        return map;
    }



    public static void main(String[] args) throws ParseException {

        Calendar cal = Calendar.getInstance();
        // 循环一次减一周
        cal.add(Calendar.DAY_OF_MONTH, -7);
        String weekDate = DateUtils.getStartEndOfWeek(cal.getTime());
        System.out.println("获取上一周日期，用于计算上一周的开始与结束日期：" + sdf.format(cal.getTime()));
        System.out.println("上一周的开始与结束日期：" + weekDate);

        // 获取传入日期当前月的开始与结束时间
        Calendar monthDate = Calendar.getInstance();
        monthDate.setTime(new Date());
        monthDate.set(Calendar.DAY_OF_MONTH, 1);
        String monthStart = sdf.format(monthDate.getTime());
        monthDate.roll(Calendar.DAY_OF_MONTH, -1);
        String monthEnd = sdf.format(monthDate.getTime());
        System.out.println("传入日期当前月的开始时间：" + monthStart + ",结束时间:" + monthEnd);

        String weekDate1 = DateUtils.getStartEndOfWeek(new Date());
        String[] array =weekDate1.split(",");
        String start_time=array[0];
        String end_time=array[1];

        Date dBegin = sdf.parse(start_time);
        Date dEnd = sdf.parse(end_time);
        Map<String,Object> map = DateUtils.findDates(dBegin,dEnd);
        System.out.println(map);
    }
}
