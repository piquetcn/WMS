package com.piquet.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static String formatDateTime(String dataTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(dataTime, DateTimeFormatter.ISO_DATE_TIME);
        ZonedDateTime utcDateTime = localDateTime.atZone(java.time.ZoneId.of("UTC"));
        ZonedDateTime cstDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("Asia/Shanghai"));
        String formattedDateTime = cstDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        return formattedDateTime;
    }
}
