package com.se.kltn.spamanagement.utils;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class DateByType {

    public static String firstDayOfCurrentWeek() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .withHour(0)
                .withMinute(0)
                .withSecond(0);
        Date formatDate = Date.from(startOfWeek.atZone(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        return formatter.format(formatDate);
    }

    public static String firstDayOfCurrentMonth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth())
                .withHour(0)
                .withMinute(0)
                .withSecond(0);
        Date formatDate = Date.from(startOfMonth.atZone(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        return formatter.format(formatDate);
    }

    public static String firstDayOfCurrentYear() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfYear = now.with(TemporalAdjusters.firstDayOfYear())
                .withHour(0)
                .withMinute(0)
                .withSecond(0);
        Date formatDate = Date.from(startOfYear.atZone(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        return formatter.format(formatDate);
    }

    public static String startOfDay() {
        LocalDateTime localDateTime = LocalDateTime.now()
                .withHour(0)
                .withMinute(0)
                .withSecond(0);
        Date formatDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        return formatter.format(formatDate);
    }
}
