package ru.tkachenko.buyerassistent.utils;

import java.util.GregorianCalendar;

public class TimerUtil {
    private final Long startTimeInMillis;

    public TimerUtil() {
        this.startTimeInMillis = new GregorianCalendar().getTimeInMillis();
    }

    public void consoleLogTime(String operationName) {
        Long endTimeInMillis = new GregorianCalendar().getTimeInMillis();
        Long timeInSeconds = endTimeInMillis - startTimeInMillis;
        System.out.println(operationName + " duration is - " + timeInSeconds + " milliseconds");
    }
}
