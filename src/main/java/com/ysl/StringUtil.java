package com.ysl;

public class StringUtil {

    /**
     * 定长字符串缺省补全
     * @param str 源字符串
     * @param cp 补全的字符串
     * @param count 补全次数
     * @param flag true 右补全 false 左补全
     * @return
     */
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

    /**
     * 判断字符串null
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || str.equals("");
    }
}
