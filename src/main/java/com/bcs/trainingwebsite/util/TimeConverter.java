package com.bcs.trainingwebsite.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeConverter {
    public static String localTimeToString(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
        return time.format(formatter);
    }
    public static LocalTime stringToLocalTime(String timeString) {
        return LocalTime.parse(timeString);
    }
}
