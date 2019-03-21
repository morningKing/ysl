package com.ysl;

public class StringUtil {

    public static String strCopy(String str, String cp, int count, boolean flag) {
        StringBuilder stringBuilder = new StringBuilder();
        while (stringBuilder.length() < count) {
            stringBuilder.append(cp);
        }
        if (flag) {
            return str + stringBuilder.toString(); //左靠右补cp
        } else {
            return stringBuilder.toString() + str; //右靠左补cp
        }
    }

    public static boolean isEmpty(String str) {
        return str == null || str.equals("");
    }
}
