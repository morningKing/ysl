package com.ysl;
/**
 * 描述：解析8583报文时字符串的处理
 * 时间：2019-04-28 13:55:11
 * author: jinjie
 */
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
     * 位图操作
     * <p>
     * 把位图的字节数组转化成二进制字符串
     *
     * @param
     * @return
     */
    public static String get16BitMapStr(byte[] bitMap16) {
        String bitMap128 = "";
        // 16位图转2进制位图128位字符串
        for (int i = 0; i < bitMap16.length; i++) {
            int bc = bitMap16[i];
            bc = (bc < 0) ? (bc + 256) : bc;
            String bitnaryStr = Integer.toBinaryString(bc);// 二进制字符串
            // 左补零，保证是8位
            String rightBitnaryStr = strCopy("0", Math.abs(8 - bitnaryStr.length())) + bitnaryStr;// 位图二进制字符串
            // 先去除多余的零，然后组装128域二进制字符串
            bitMap128 += rightBitnaryStr;


        }
        return bitMap128;
    }

    /**
     * 复制字符
     *
     * @param str
     * @param count
     * @return
     */
    public static String strCopy(String str, int count) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
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
