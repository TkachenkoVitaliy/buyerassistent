package ru.tkachenko.buyerassistant.utils;

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

    //TODO rewrite for n.000 or n.00 or 1.500 etc...
    public static String removeFractionalPartWithZero(String text) {
        if (text == null) return null;
        Pattern pattern = Pattern.compile("[.][1-9]{0,2}0{1,10}");
        Matcher matcher = pattern.matcher(text);
        //TODO возможно проще разбивать по разделителю и собирать сроку заного из массива строк
//        ([.]{1}0{1,4})|(0{1,3})
//        [.].{0,2}[0]{1,10}
//        ([.]{1}[0]{1,10})
//        return text.replace(".0", "");
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
