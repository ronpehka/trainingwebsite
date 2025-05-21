package com.bcs.trainingwebsite.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateConverter {

    public static String localDateToString(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(formatter);
    }
    public static LocalDate stringToLocaldate(String dateStr){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateStr,formatter);
    }
}
