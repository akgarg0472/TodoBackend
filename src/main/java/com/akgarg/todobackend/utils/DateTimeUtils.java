package com.akgarg.todobackend.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Author: Akhilesh Garg
 * GitHub: <a href="https://github.com/akgarg0472">https://github.com/akgarg0472</a>
 * Date: 16-07-2022
 */
@SuppressWarnings("unused")
public class DateTimeUtils {

    private DateTimeUtils() {
    }

    public static long getCurrentDateTimeInMilliseconds() {
        return System.currentTimeMillis();
    }

    public static LocalDateTime getCurrentDateTimeFromMilliseconds() {
        return getCurrentDateTimeFromMilliseconds(System.currentTimeMillis());
    }

    public static LocalDateTime getCurrentDateTimeFromMilliseconds(final long milliseconds) {
        final var instant = Instant.ofEpochMilli(milliseconds);
        final var indianZonedTime = instant.atZone(ZoneId.of("Asia/Kolkata"));

        return indianZonedTime.toLocalDateTime();
    }

}
