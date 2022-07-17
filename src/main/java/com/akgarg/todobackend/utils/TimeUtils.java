package com.akgarg.todobackend.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
public class TimeUtils {

    public static long getCurrentDateTimeInMilliseconds() {
        return System.currentTimeMillis();
    }

    public static LocalDateTime getCurrentDateTimeFromMilliseconds() {
        return getCurrentDateTimeFromMilliseconds(System.currentTimeMillis());
    }

    public static LocalDateTime getCurrentDateTimeFromMilliseconds(long milliseconds) {
        Instant instant = Instant.ofEpochMilli(milliseconds);
        ZonedDateTime indianZonedTime = instant.atZone(ZoneId.of("Asia/Kolkata"));

        return indianZonedTime.toLocalDateTime();
    }

}
