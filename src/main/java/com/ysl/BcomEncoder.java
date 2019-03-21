package com.ysl;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BcomEncoder {

    static String encode(Map<String, String> map) {
        if (!(map instanceof TreeMap)) {
            map = new TreeMap<>(map);
        }
        Set<String> set = map.keySet();
        if (set.size() == 0)
            throw new IllegalArgumentException("value map is invalid");

        StringBuilder body = new StringBuilder();

        char[] bitmap = BitMapCache.initMap();

        for (String key : set) {
            Field field = MapDefinition.mapField.get(key);
            if (field == null) {
                throw new IllegalArgumentException("map field is invalid");
            }
            if (map.get(key) == null || map.get(key).equals("")) {
                throw new RuntimeException("illegal field value in " + field.getName());
            }

            bitmap[getFieldIndex(key) - 1] = '1'; //构造位图

            FieldEntry fieldEntry = new FieldEntry();
            switch (field.getType()) { //类型
                case BcomDecoder.ASCII:
                    fieldEntry = genAsciiValue(map.get(key), field); //ascii码 长度是字节数
                    break;
                case BcomDecoder.BCD:
                    fieldEntry = genBcdValue(map.get(key), field); //bcd码 长度字节数的一半
                    break;
                case BcomDecoder.TLV:
                    fieldEntry = getTlvValue(map.get(key), field);
                    break;
                case BcomDecoder.ZZZ:
                    fieldEntry = getZZZValue(map.get(key), key, field); //接收字符串(字节字符/非16进制)
                    break;
                case BcomDecoder.BIN:

                    break;
                default:
                    throw new RuntimeException("unknow value type index = " + field.getName() + " type = " + field.getType());
            }

            body.append(fieldEntry.getLength()); //拼接长度
            body.append(fieldEntry.getValue()); //拼接内容
        }
        String bits = BitMapCache.getBitMap(bitmap);
        body.insert(0, bits);
        return body.toString();
    }

    /**
     * 构造8421码长度 16进制
     *
     * @param flag true 右靠 左补0 false 左靠 右补0
     * @return
     */
    private static String genBcdLenth(String src, int count, boolean flag) {
        if (count != 1 && count != 2) {
            throw new IllegalArgumentException("invalid bcd var length, var length must have 1~2 byte ");
        }
        int num = count == 1 ? 99 : 9999;
        if (src.length() > num) {
            throw new IllegalArgumentException("invalid bcd length source length is " + src.length() + " but max field len is " + (count * 2));
        }
        String len = String.valueOf(src.getBytes().length); //长度位数
        return StringUtil.strCopy(len, "0", count * 2 - len.length(), !flag);
    }

    /**
     * @param src
     * @param field
     * @return
     */
    private static FieldEntry genBcdValue(String src, Field field) {
        FieldEntry fieldEntry = new FieldEntry();

        switch (field.getVar()) {
            case BcomDecoder.STATIC:
                if (src.length() > (field.getVarLength() * 2))
                    throw new IllegalArgumentException("invalid bcd length source length is " + src.length() + " but field len is " + field.getVarLength());
                int sub = field.getVarLength() - src.length();
                fieldEntry.setValue(StringUtil.strCopy(src, "0", sub, false)); //定长 数字域 右靠齐 左补0
                fieldEntry.setLength("");
                fieldEntry.setLen(field.getVarLength());
                break;
            case BcomDecoder.VAR:
                fieldEntry.setLen(src.length());
                fieldEntry.setLength(genBcdLenth(src, field.getLength(), true));
                if (src.length() % 2 == 1) {
                    fieldEntry.setValue("0" + src);
                } else {
                    fieldEntry.setValue(src);
                }
        }
        return fieldEntry;
    }

    /**
     * ascii value
     * 非数字域
     *
     * @param src
     * @return
     */
    private static FieldEntry genAsciiValue(String src, Field field) {
        FieldEntry fieldEntry = new FieldEntry();
        StringBuilder ascii = new StringBuilder();
        byte[] bytes = src.getBytes();
        for (byte b : bytes) {
            ascii.append(ByteUtil.dec2hex(b & 0xFF)); //先转int 再转16进制
        }

        switch (field.getVar()) { // ascii码长度为实际字节长度
            case BcomDecoder.STATIC:
                fieldEntry.setLen(field.getVarLength());
                if (bytes.length < field.getVarLength()) { //定长 非数字域 左靠齐 右补0
                    int sub = (field.getVarLength() - bytes.length) * 2;
                    ascii = new StringBuilder(
                            StringUtil.strCopy(ascii.toString(), "0", sub, true)
                    );
                }
                fieldEntry.setLength("");
                break;
            case BcomDecoder.VAR:
                fieldEntry.setLen(src.length());
                fieldEntry.setLength(genBcdLenth(src, field.getLength(), true));
                break;
            default:
                throw new IllegalArgumentException("");
        }

        fieldEntry.setValue(ascii.toString());
        return fieldEntry;
    }

    /**
     * 磁道数据 一磁 二磁
     * 变长 字符
     *
     * @return
     */
    private static FieldEntry getZZZValue(String src, String key, Field field) {
        int index = getFieldIndex(key);
        if (index == 0) {
            throw new IllegalArgumentException("");
        }
        FieldEntry fieldEntry = new FieldEntry();
        if (index == BcomDecoder.SEC && (src.length() > 37)) {
            throw new IllegalArgumentException("second track length " + src.length() + " is longer than 37 bytes");
        }
        if (index == BcomDecoder.THRD && (src.length() > 104)) {
            throw new IllegalArgumentException("");
        }
        if (src.length() % 2 == 1) {
            fieldEntry.setValue(src + "0"); //实际长度为奇数，域补0（1位byte代表2位16进制数）
        } else {
            fieldEntry.setValue(src);
        }
        fieldEntry.setLength("20" + genBcdLenth(src, field.getLength(), true));//二磁三磁开始标志位
        fieldEntry.setLen(src.length());
        return fieldEntry;
    }

    /**
     * @param src
     * @return
     */
    private static FieldEntry getTlvValue(String src, Field field) {
        FieldEntry fieldEntry = new FieldEntry();
        fieldEntry.setLen(src.length());
        String count = String.valueOf(src.length() / 2);
        fieldEntry.setLength(StringUtil.strCopy(count, "0", 4 - count.length(), false));
        return null;
    }

    /**
     * field index FIELD001 -> 1
     *
     * @param key
     * @return
     */
    private static int getFieldIndex(String key) {
        String pattern = "\\d{3}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(key);
        if (m.find()) {
            return Integer.parseInt(m.group(0));
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        String str = "9F2608B1AAD1CB0083B5029F2701809F101307010103A00000040A01000000000095DE79D39F370486F5FD329F36020289950500000000009A031902279C01009F02060000000000025F2A02015682027C009F1A0201569F3303E0F0C89F3501228408A0000003330101029F090200209F6310303432333333313000000000000000009F1E0838343731343637339F0306000000000000";
        System.out.println(genBcdLenth(str, 2, true));
    }

}
