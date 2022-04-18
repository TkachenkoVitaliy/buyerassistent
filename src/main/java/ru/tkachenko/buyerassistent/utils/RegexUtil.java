package ru.tkachenko.buyerassistent.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public static String replaceDelimiter(String text) {
        if (text == null) return null;
        return text.replace('x', '*');
    }

    public static String replaceDotToComma(String text) {
        if (text == null) return null;
        return text.replace('.', ',');
    }

    public static String replaceDelimiterAndDot(String text) {
        if (text == null) return null;
        return text.replace('x','*').replace('.', ',');
    }

    public static String removeFractionalPartWithZero(String text) {
        if (text == null) return null;
        return text.replace(".0", "");
    }

    public static String doubleToString(double value) {
        String text = String.valueOf(value);
        return removeFractionalPartWithZero(text);
    }

    public static String findRegexInTextAndRemoveUnnecessary(String sourceText, String regex, String unnecessaryString) {
        String result = null;

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sourceText);
        if(matcher.find()) {
            result = matcher.group().replace(unnecessaryString, "");
        }
        return result;
    }

}
