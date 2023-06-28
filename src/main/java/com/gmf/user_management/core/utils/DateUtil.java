package com.gmf.user_management.core.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {
    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String localDateTimeToString(LocalDateTime localDateTime) {

        if (localDateTime == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
        // Format the LocalDateTime object to a string
        return localDateTime.format(formatter);
    }
}
