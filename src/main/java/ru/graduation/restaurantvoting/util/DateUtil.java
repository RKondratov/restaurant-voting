package ru.graduation.restaurantvoting.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@UtilityClass
public class DateUtil {
    public static Date getTomorrowStartDate() {
        return new Date(getTodayStartDate().getTime() + (1000 * 60 * 60 * 24));
    }

    public static Date getTodayStartDate() {
        LocalDateTime localDateTime = LocalDate.now().atStartOfDay();
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}