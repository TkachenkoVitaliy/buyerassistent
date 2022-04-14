package ru.tkachenko.buyerassistent.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class CurrentDate {

    private GregorianCalendar calendar = new GregorianCalendar();
    private static final DateFormat yearFormat = new SimpleDateFormat("yyyy");
    private static final DateFormat monthFormat = new SimpleDateFormat("MM");
    private static final DateFormat dayFormat = new SimpleDateFormat("dd");
    private static final DateFormat timeFormat = new SimpleDateFormat("HH-mm");
    private String year = yearFormat.format(calendar.getTime());
    private String month = monthFormat.format(calendar.getTime());
    private String day = dayFormat.format(calendar.getTime());
    private String time = timeFormat.format(calendar.getTime());

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getTime() {
        return time;
    }
}
