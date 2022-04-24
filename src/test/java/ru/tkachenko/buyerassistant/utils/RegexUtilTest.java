package ru.tkachenko.buyerassistant.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class RegexUtilTest {

    public static final String CASE_1 = "1,5x1250x2500";
    public static final String CASE_2 = "1,5*1250*2500";
    public static final String CASE_3 = "1.5x1250x2500";
    public static final String CASE_4 = "1.5*1250*2500";
    public static final String CASE_5 = "4.0*1500*6000";
    public static final String CASE_6 = "4.000*1500.000*6000.000";
    public static final String CASE_7 = "4,000*1500*6000";
    public static final String CASE_8 = "4.000x1500x6000";
    public static final String CASE_9 = "4,000x1500x6000";
    public static final String nullCase = null;

//    String expectedCase1 = "1,5x1250x2500";
//    String expectedCase2 = "1,5*1250*2500";
//    String expectedCase3 = "1.5x1250x2500";
//    String expectedCase4 = "1.5*1250*2500";
//    String expectedCase5 = "4.0*1500*6000";
//    String expectedCase6 = "4.000*1500.000*6000.000";
//    String expectedCase7 = "4,000*1500*6000";
//    String expectedCase8 = "4.000x1500x6000";
//    String expectedCase9 = "4,000x1500x6000";

    @Test
    void replaceDelimiter() {
        String expectedCase1 = "1,5*1250*2500";
        String expectedCase2 = "1,5*1250*2500";
        String expectedCase3 = "1.5*1250*2500";
        String expectedCase4 = "1.5*1250*2500";
        String expectedCase5 = "4.0*1500*6000";
        String expectedCase6 = "4.000*1500.000*6000.000";
        String expectedCase7 = "4,000*1500*6000";
        String expectedCase8 = "4.000*1500*6000";
        String expectedCase9 = "4,000*1500*6000";

        Assertions.assertEquals(expectedCase1, RegexUtil.replaceDelimiter(CASE_1));
        Assertions.assertEquals(expectedCase2, RegexUtil.replaceDelimiter(CASE_2));
        Assertions.assertEquals(expectedCase3, RegexUtil.replaceDelimiter(CASE_3));
        Assertions.assertEquals(expectedCase4, RegexUtil.replaceDelimiter(CASE_4));
        Assertions.assertEquals(expectedCase5, RegexUtil.replaceDelimiter(CASE_5));
        Assertions.assertEquals(expectedCase6, RegexUtil.replaceDelimiter(CASE_6));
        Assertions.assertEquals(expectedCase7, RegexUtil.replaceDelimiter(CASE_7));
        Assertions.assertEquals(expectedCase8, RegexUtil.replaceDelimiter(CASE_8));
        Assertions.assertEquals(expectedCase9, RegexUtil.replaceDelimiter(CASE_9));
        Assertions.assertNull(RegexUtil.replaceDelimiter(nullCase));
    }

    @Test
    void replaceDotToComma() {
        String expectedCase1 = "1,5x1250x2500";
        String expectedCase2 = "1,5*1250*2500";
        String expectedCase3 = "1,5x1250x2500";
        String expectedCase4 = "1,5*1250*2500";
        String expectedCase5 = "4,0*1500*6000";
        String expectedCase6 = "4,000*1500,000*6000,000";
        String expectedCase7 = "4,000*1500*6000";
        String expectedCase8 = "4,000x1500x6000";
        String expectedCase9 = "4,000x1500x6000";

        Assertions.assertEquals(expectedCase1, RegexUtil.replaceDotToComma(CASE_1));
        Assertions.assertEquals(expectedCase2, RegexUtil.replaceDotToComma(CASE_2));
        Assertions.assertEquals(expectedCase3, RegexUtil.replaceDotToComma(CASE_3));
        Assertions.assertEquals(expectedCase4, RegexUtil.replaceDotToComma(CASE_4));
        Assertions.assertEquals(expectedCase5, RegexUtil.replaceDotToComma(CASE_5));
        Assertions.assertEquals(expectedCase6, RegexUtil.replaceDotToComma(CASE_6));
        Assertions.assertEquals(expectedCase7, RegexUtil.replaceDotToComma(CASE_7));
        Assertions.assertEquals(expectedCase8, RegexUtil.replaceDotToComma(CASE_8));
        Assertions.assertEquals(expectedCase9, RegexUtil.replaceDotToComma(CASE_9));
        Assertions.assertNull(RegexUtil.replaceDotToComma(nullCase));
    }

    @Test
    void replaceDelimiterAndDot() {
        String expectedCase1 = "1,5*1250*2500";
        String expectedCase2 = "1,5*1250*2500";
        String expectedCase3 = "1,5*1250*2500";
        String expectedCase4 = "1,5*1250*2500";
        String expectedCase5 = "4,0*1500*6000";
        String expectedCase6 = "4,000*1500,000*6000,000";
        String expectedCase7 = "4,000*1500*6000";
        String expectedCase8 = "4,000*1500*6000";
        String expectedCase9 = "4,000*1500*6000";

        Assertions.assertEquals(expectedCase1, RegexUtil.replaceDelimiterAndDot(CASE_1));
        Assertions.assertEquals(expectedCase2, RegexUtil.replaceDelimiterAndDot(CASE_2));
        Assertions.assertEquals(expectedCase3, RegexUtil.replaceDelimiterAndDot(CASE_3));
        Assertions.assertEquals(expectedCase4, RegexUtil.replaceDelimiterAndDot(CASE_4));
        Assertions.assertEquals(expectedCase5, RegexUtil.replaceDelimiterAndDot(CASE_5));
        Assertions.assertEquals(expectedCase6, RegexUtil.replaceDelimiterAndDot(CASE_6));
        Assertions.assertEquals(expectedCase7, RegexUtil.replaceDelimiterAndDot(CASE_7));
        Assertions.assertEquals(expectedCase8, RegexUtil.replaceDelimiterAndDot(CASE_8));
        Assertions.assertEquals(expectedCase9, RegexUtil.replaceDelimiterAndDot(CASE_9));
        Assertions.assertNull(RegexUtil.replaceDelimiterAndDot(nullCase));
    }

    @Test
    void removeFractionalPartWithZero() {
        String testCase1 = "1.050x1250x2500";
        String testCase2 = "1.050x1250.000x2500.000";
        String testCase3 = "1.000x1250.000x2500.000";
        String testCase4 = "10x1500x6000";
        String testCase5 = "1,050x1250,000x2500,000";
        String testCase6 = "1.050x1250,000x2500";

        String expectedCase1 = "1.05x1250x2500";
        String expectedCase2 = "1.05x1250x2500";
        String expectedCase3 = "1x1250x2500";
        String expectedCase4 = "10x1500x6000";
        String expectedCase5 = "1,05x1250x2500";
        String expectedCase6 = "1.05x1250x2500";

        Assertions.assertEquals(expectedCase1, RegexUtil.removeFractionalPartWithZero(testCase1));
        Assertions.assertEquals(expectedCase2, RegexUtil.removeFractionalPartWithZero(testCase2));
        Assertions.assertEquals(expectedCase3, RegexUtil.removeFractionalPartWithZero(testCase3));
        Assertions.assertEquals(expectedCase4, RegexUtil.removeFractionalPartWithZero(testCase4));
        Assertions.assertEquals(expectedCase5, RegexUtil.removeFractionalPartWithZero(testCase5));
        Assertions.assertEquals(expectedCase6, RegexUtil.removeFractionalPartWithZero(testCase6));
        Assertions.assertNull(RegexUtil.removeFractionalPartWithZero(nullCase));
    }

    @Test
    void doubleToString() {
        double testCase1 = 2.0;
        double testCase2 = 1.150;
        double testCase3 = 1.050;
        double testCase4 = 4;

        String expectedCase1 = "2";
        String expectedCase2 = "1.15";
        String expectedCase3 = "1.05";
        String expectedCase4 = "4";

        Assertions.assertEquals(expectedCase1, RegexUtil.doubleToString(testCase1));
        Assertions.assertEquals(expectedCase2, RegexUtil.doubleToString(testCase2));
        Assertions.assertEquals(expectedCase3, RegexUtil.doubleToString(testCase3));
        Assertions.assertEquals(expectedCase4, RegexUtil.doubleToString(testCase4));
    }

    @Test
    void findRegexInTextAndRemoveUnnecessary() {
        String testCase1 = "Какой-то текст;\nШирина полки=10;\nЕще какой-то текст";
        final String firstMeasureRegex = "(Ширина полки=[0-9]{1,3})";
        final String firstMeasureRemovedString = "Ширина полки=";

        String expectedCase1 = "10";

        Assertions.assertEquals(expectedCase1, RegexUtil.findRegexInTextAndRemoveUnnecessary(testCase1,
                firstMeasureRegex, firstMeasureRemovedString));
    }

}