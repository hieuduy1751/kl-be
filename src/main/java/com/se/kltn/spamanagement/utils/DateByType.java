package com.se.kltn.spamanagement.utils;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

public class DateByType {

    public static Date firstDayOfWeek() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .withHour(0)
                .withMinute(0)
                .withSecond(0);
        return Date.from(startOfWeek.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date firstDayOfMonth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.with(TemporalAdjusters.firstDayOfMonth())
                .withHour(0)
                .withMinute(0)
                .withSecond(0);
        return Date.from(startOfMonth.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date firstDayOfYear() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfYear = now.with(TemporalAdjusters.firstDayOfYear())
                .withHour(0)
                .withMinute(0)
                .withSecond(0);
        return Date.from(startOfYear.atZone(ZoneId.systemDefault()).toInstant());
    }
}
