package com.you.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * @author you
 * @ClassName: LocalDateTimeUtil
 * @Description: 封装jdk8的LocalDateTime工具类
 * @date 2019年7月5日
 */

@Component
public class LocalDateTimeUtil {
    private static Logger logger = LoggerFactory.getLogger(LocalDateTimeUtil.class);

    /**
     * @param @param  number
     * @param @return 参数
     * @return long    返回类型
     * @throws
     * @Title: getTimestamp
     * @Description: LocalDateTime获取当前时间戳
     */
    public Long getTimestamp(int number) {
        Long timestamp;
        if (number == 10) {
            // 获取10位秒数
            timestamp = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        } else {
            // 获取13位毫秒数
            timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        }
        return timestamp;
    }

    /**
     * @param @return 参数
     * @return String    返回类型
     * @throws
     * @Title: getCurrentTime
     * @Description: 获取当前的时间 格式是  yyyy-MM-dd HH:mm:ss
     */
    public String getCurrentTime() {
        // 获取当前时间
        LocalDateTime localDateTime = LocalDateTime.now();
        // 设置要转换成的时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(localDateTime);
    }
    
    /**
     * @param @return 当前日期
     * @return String    返回类型
     * @throws
     * @Title: getCurrentDateTime
     * @Description: 获取当前的日期 格式是  yyyy-MM-dd
     */
    public String getCurrentDateTime() {
        LocalDate  localDate = LocalDate.now();
        // 设置要转换成的时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(localDate);
    }


    /**
     * @param @param  timestamp
     * @param @return 参数
     * @return String    返回类型
     * @throws
     * @Title: timestampToDatetime
     * @Description: 将时间戳转换成时间字符串(包括10位和13位)
     */
    public String timestampToDatetime(String timestamp) {
        Instant instant;
        if (timestamp.length() == 13) {
            // 13位时间戳
            long time = new Long(timestamp);
            instant = Instant.ofEpochMilli(time);
        } else {
            // 10位时间戳
            long time = new Long(timestamp);
            instant = Instant.ofEpochMilli(time * 1000L);
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        // 设置要转换成的时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(localDateTime);
    }


    /**
     * @param @return 参数
     * @return String    返回类型
     * @throws
     * @Title: getBeforeDateByLocalDate
     * @Description: 获取当前时间的前几天的日期
     */
    public String getBeforeDateByLocalDate(int interval) {
        // 设置要转换成的时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 获取前一天的日期
        LocalDate before_day = LocalDate.now().minusDays(interval);
        // 转成string格式
        return formatter.format(before_day);
    }

    /**
     * @param @param  interval 时间间隔
     * @param @return 参数
     * @return String    返回类型
     * @throws
     * @Title: getBeforeDateMinTime
     * @Description: 获取前一天的零点时间
     */
    public String getBeforeDateMinTime(int interval) {
        // 获取前一天的日期
        LocalDate before_day = LocalDate.now().minusDays(interval);
        // 设置要转换成的时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 当天零点
        LocalDateTime today_start = LocalDateTime.of(before_day, LocalTime.MIN);
        // 转成string格式
        return formatter.format(today_start);
    }


    /**
     * @param @param  interval 时间间隔
     * @param @return 参数
     * @return String    返回类型
     * @throws
     * @Title: getBeforeDateMaxTime
     * @Description: 获取前一天的结束时间
     */
    public String getBeforeDateMaxTime(int interval) {
        // 获取前一天的日期
        LocalDate before_day = LocalDate.now().minusDays(interval);
        // 设置要转换成的时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 当天的结束时间
        LocalDateTime today_end = LocalDateTime.of(before_day, LocalTime.MAX);
        // 转成string格式
        return formatter.format(today_end);
    }

    /**
     * @param @param  interval 时间间隔
     * @param @return 参数
     * @return String    返回类型
     * @throws
     * @Title: getBeforeDateMaxTime
     * @Description: 获取当前时间往后多久
     */
    public String getAfterDateTime(int interval) {
        LocalDate after_day = LocalDate.now().plusDays(interval);
        // 设置要转换成的时间格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd　HH:mm:ss");
        LocalDateTime today_end = LocalDateTime.of(after_day, LocalTime.now());
        // 转成string格式
        return formatter.format(today_end);
    }

    /**
     * @param localTime 当前时间
     * @param endTime   过期时间
     * @return 是否超时 1:超时 0:未超时
     * @author chenshuo
     * 判断是否超时
     */
    public int whetherOverTime(String localTime, String endTime) {
        int number = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(sdf.parse(endTime));
            int day1 = cal1.get(Calendar.DAY_OF_YEAR);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(sdf.parse(localTime));
            int day2 = cal2.get(Calendar.DAY_OF_YEAR);
            if (day2 > day1) {
                number = 1;
            }
        } catch (ParseException e) {
            logger.error("事件是否超时判断超出异常：" + e);
        }
        return number;
    }
}
