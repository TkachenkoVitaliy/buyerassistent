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

    public static String removeFractionalPartWithZero(String text) {
        if (text == null) return null;
        if (!text.contains(".")) return text;
        final String DELIMITER = "x";

        String[] measures = text.split("x");
        for (int i = 0; i < measures.length; i++) {
            if(measures[i].contains(".")) {
                char[] charArray = measures[i].toCharArray();
                int endIndex = charArray.length - 1;
                while(charArray[endIndex] == '0') {
                    endIndex--;
                }
                measures[i] = measures[i].substring(0, endIndex+1);
                if(measures[i].endsWith(".")) {
                    measures[i] = measures[i].substring(0, measures[i].length()-1);
                }
            }
        }
        String result = convertStringArrayToString(measures, DELIMITER);
        return result;
    }

    private static String convertStringArrayToString(String[] strArr, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String str : strArr)
            sb.append(str).append(delimiter);
        return sb.substring(0, sb.length() - 1);
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
