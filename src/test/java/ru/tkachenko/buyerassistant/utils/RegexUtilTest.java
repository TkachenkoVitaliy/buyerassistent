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
        String expectedCase1 = "1,5x1250x2500";
        String expectedCase2 = "1,5*1250*2500";
        String expectedCase3 = "1.5x1250x2500";
        String expectedCase4 = "1.5*1250*2500";
        String expectedCase5 = "4*1500*6000";
        String expectedCase6 = "4*1500*6000";
        String expectedCase7 = "4,000*1500*6000";
        String expectedCase8 = "4x1500x6000";
        String expectedCase9 = "4,000x1500x6000";

        Assertions.assertEquals(expectedCase1, RegexUtil.removeFractionalPartWithZero(CASE_1));
        Assertions.assertEquals(expectedCase2, RegexUtil.removeFractionalPartWithZero(CASE_2));
        Assertions.assertEquals(expectedCase3, RegexUtil.removeFractionalPartWithZero(CASE_3));
        Assertions.assertEquals(expectedCase4, RegexUtil.removeFractionalPartWithZero(CASE_4));
        Assertions.assertEquals(expectedCase5, RegexUtil.removeFractionalPartWithZero(CASE_5));
        Assertions.assertEquals(expectedCase6, RegexUtil.removeFractionalPartWithZero(CASE_6));
        Assertions.assertEquals(expectedCase7, RegexUtil.removeFractionalPartWithZero(CASE_7));
        Assertions.assertEquals(expectedCase8, RegexUtil.removeFractionalPartWithZero(CASE_8));
        Assertions.assertEquals(expectedCase9, RegexUtil.removeFractionalPartWithZero(CASE_9));
        Assertions.assertNull(RegexUtil.removeFractionalPartWithZero(nullCase));
    }

}