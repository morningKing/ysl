package com.ysl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Encoder {
    /**
     * 构造8421码长度 16进制
     *
     * @param flag true 右靠 左补0 false 左靠 右补0
     * @return
     */
    protected static String genBcdLenth(String src, int count, boolean flag) {
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
    protected static FieldEntry genBcdValue(String src, Field field) {
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
                    fieldEntry.setValue(src + "0"); //左靠右补零
                } else {
                    fieldEntry.setValue(src);
                }
                break;
            default:
                throw new IllegalArgumentException("unknown field type " + field.getVar() + " please check config_8583.properties");
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
    protected static FieldEntry genAsciiValue(String src, Field field) {
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
                    int sub = (field.getVarLength() - bytes.length);
                    ascii = new StringBuilder(
                            StringUtil.strCopy(ascii.toString(), "31", sub, true)
                    );
                } else if (bytes.length > field.getVarLength()) {
                    throw new IllegalArgumentException("illegal field length ! the max length is " + field.getVarLength() + " but " + bytes.length + " bytes given");
                }
                fieldEntry.setLength("");
                break;
            case BcomDecoder.VAR:
                fieldEntry.setLen(src.length());
                fieldEntry.setLength(genBcdLenth(src, field.getLength(), true));
                break;
            default:
                throw new IllegalArgumentException("unknown field type " + field.getVar() + " please check config_8583.properties");
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
    protected static FieldEntry getZZZValue(String src, String key, Field field) {
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
     * field index FIELD001 -> 1
     *
     * @param key
     * @return
     */
    protected static int getFieldIndex(String key) {
        String pattern = "\\d{3}";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(key);
        if (m.find()) {
            return Integer.parseInt(m.group(0));
        } else {
            return 0;
        }
    }
}
