package com.weirdo.security.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

        /**
         * 时间戳转换成日期格式字符串
         * @param seconds 精确到秒的字符串
         * @return
         */
        public static String timeStampToDate(String seconds) {
            if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
                return "";
            }
            String format = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(new Date(Long.valueOf(seconds+"000")));
        }
        /**
         * 日期格式字符串转换成时间戳
         * @param date_str 字符串日期
         * @param format 如：yyyy-MM-dd HH:mm:ss
         * @return
         */
        public static String dateToTimeStamp(String date_str,String format){
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return String.valueOf(sdf.parse(date_str).getTime()/1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        /**
         * 取得当前时间戳（精确到秒）
         * @return
         */
        public static Long timeStamp(){
            long time = System.currentTimeMillis();
            Long t = time/1000;
            return t;
        }

}
