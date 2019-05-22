package com.ysl;

import java.util.Map;
import java.util.TreeMap;

public class BcomDecoder {

    public static final int VAR = 1;
    public static final int STATIC = 0;

    public static final int ASCII = 1;
    public static final int BCD = 0;
    public static final int TLV = 3;
    public static final int ZZZ = 4;
    public static final int BIN = 5;

    public static final int SEC = 35;
    public static final int THRD = 36;

    static Map<String, String> parse(byte[] body) {

        Map<String, String> map = new TreeMap<>();

        int index = 0;
        byte[] msi = new byte[2];
        System.arraycopy(body, index, msi, 0, 2);
        map.put("mti", ByteUtil.bytes2hex(msi));
        index += 2;

        byte[] bitmap = new byte[8]; //位图解析
        System.arraycopy(body, index, bitmap, 0, 8);
        index += 8;
        char[] bits = BitMapCache.getBitMap(bitmap).toCharArray();
        map.put("bitmap", new String(bits));
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] != 49)
                continue;

            Field field = getField(i + 1); //域属性
            if (field == null)
                throw new RuntimeException("field error index = " + (i + 1));

            byte[] value;
            int varLength = 0;
            boolean flag = false;
            if (field.getVar() == VAR) { //变长域
                byte[] len = new byte[field.getLength()]; //长度字节数
                if ((i + 1) == SEC || (i + 1) == THRD) { //第二磁道和第三磁道特殊处理
                    index += 1; //忽略数据开始标志位
                }
                System.arraycopy(body, index, len, 0, field.getLength());
                flag = isJiOu(len);
                varLength = getVarLength(len, field.getType());
                value = new byte[varLength]; //value值字节数 由于一个字节为2位16进制数，所以长度要为字符串的一半
                index += field.getLength();
            } else if (field.getVar() == STATIC) { //定长域
                value = new byte[field.getVarLength()];
            } else {
                throw new RuntimeException("unknow field type index = " + (i + 1));
            }

            System.arraycopy(body, index, value, 0, value.length);
            index += value.length;
            String fieldValue;
            switch (field.getType()) { //value 类型
                case ASCII:
                    fieldValue = getAsciiValue(value);
                    break;
                case BCD:
                    fieldValue = getBcdValue(value,flag);
                    break;
                case TLV:
                    fieldValue = getTlvValue(value);
                    break;
                case ZZZ:
                    fieldValue = getTrackValue(value, (i + 1));
                    break;
                case BIN:
                    fieldValue = getBinValue(value);
                    break;
                default:
                    throw new RuntimeException("unknow value type index = " + (i + 1));
            }
            map.put(field.getName(), fieldValue);
        }
        return map;
    }

    static Map<String, String> parse(String str16) {
        byte[] bytes = ByteUtil.hex2bytes(str16);
        return parse(bytes);
    }

    /**
     * 校验长度
     * 第一个字节是正数
     * 11FF
     * 00010001 11111111
     * {17,-1}
     * 2^0+2^1+...+2^8 + 2^12 = 4607
     * <p>
     * 1+16=17
     *
     * @param bytes
     * @return
     */
    public static boolean checkLength(byte[] bytes) {
        byte[] head = new byte[2];
        System.arraycopy(bytes, 0, head, 0, 2);
        return ByteUtil.bytes2int(head) == (bytes.length - 2);
    }

    /**
     * 根据索引值得到field属性
     *
     * @param i
     * @return
     */
    private static Field getField(int i) {
        String k = String.valueOf(i);
        String key;
        if (k.length() < 3) {
            key = "FIELD" + StringUtil.strCopy(k, "0", 3 - k.length(), false);
        } else {
            key = "FIELD" + k;
        }
        return MapDefinition.mapField.get(key);
    }

    /**
     * 得到bcd value
     *
     * @param bytes
     * @param flag
     * @return
     */
    private static String getBcdValue(byte[] bytes, boolean flag) {
        if (!flag) //定长域 或者 非补零变长
            return ByteUtil.bytes2bcd(bytes);
        String bcd = ByteUtil.bytes2bcd(bytes);
        return bcd.substring(0, bcd.length() - 1);
    }

    /**
     * 一磁 二磁 三磁数据
     *
     * @param bytes
     * @return
     */
    private static String getTrackValue(byte[] bytes, int index) {
        String track = ByteUtil.bytes2hex(bytes);
        if (track != null && !track.equals("") && index == SEC) {
            return track.replace("D", "=").replace("d", "=");
        }
        return track;
    }

    /**
     * tlv 数据
     *
     * @param bytes
     * @return
     */
    private static String getTlvValue(byte[] bytes) {
        return TlvDecoder.format(bytes);
    }

    /**
     * ascii数据
     *
     * @param bytes
     * @return
     */
    private static String getAsciiValue(byte[] bytes) {
        return ByteUtil.bytes2ascii(bytes);
    }

    /**
     * 二进制数据
     *
     * @param bytes
     * @return
     */
    private static String getBinValue(byte[] bytes) {
        return ByteUtil.bytes2hex(bytes);
    }

    /**
     * 变长域length 字节数
     *
     * @param bytes
     * @return
     */
    private static int getVarLength(byte[] bytes, int type) {
        int varLength = Integer.parseInt(ByteUtil.bytes2bcd(bytes));
        if (BCD == type || ZZZ == type || BIN == type) { // bcd码和第三磁道
            varLength = varLength % 2 == 1 ? varLength + 1 : varLength; //如果长度是奇数，则左补0，长度加1
            return varLength / 2; //字节数 = 字符数 / 2
        } else {
            return varLength;
        }
    }

    private static boolean isJiOu(byte[] bytes) {
        int varLength = Integer.parseInt(ByteUtil.bytes2bcd(bytes));
        if(varLength % 2 == 1) { //true 奇数
            return true;
        } else {
            return false; // false 偶数
        }
    }

    public static void main(String[] args) {
    }
}
