package com.akgarg.todobackend.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author Akhilesh Garg
 * @since 16-07-2022
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
        final Instant instant = Instant.ofEpochMilli(milliseconds);
        final ZonedDateTime indianZonedTime = instant.atZone(ZoneId.of("Asia/Kolkata"));

        return indianZonedTime.toLocalDateTime();
    }

}
