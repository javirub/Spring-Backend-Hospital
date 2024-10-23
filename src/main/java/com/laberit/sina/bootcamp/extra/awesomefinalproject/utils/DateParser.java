package com.laberit.sina.bootcamp.extra.awesomefinalproject.utils;

import java.time.LocalDateTime;

public class DateParser {
    public static LocalDateTime parseDate(String date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.parse(date);
    }
}
