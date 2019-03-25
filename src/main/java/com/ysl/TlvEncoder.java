package com.ysl;

import java.util.Map;
import java.util.Set;


public class TlvEncoder {
    public static String encode(Map<String, String> map) {
        Set<String> set = map.keySet();
        StringBuilder unionValue = new StringBuilder();
        for (String key : set) {
            UnionField55Unit unionField55Unit;
            if ((unionField55Unit = UnionField55Unit.isValid(key)) == null)
                throw new IllegalArgumentException("illegal tlv tag " + key + " tags in [" + UnionField55Unit.toTagString() + "]");
            switch (unionField55Unit.getType()) {
                case BcomDecoder.BCD:
                    unionValue.append(genBcdValue(map.get(key), unionField55Unit));
                    break;
                case BcomDecoder.ASCII:
                    unionValue.append(genAsciiValue(map.get(key), unionField55Unit));
                    break;
                case BcomDecoder.BIN:
                    unionValue.append(genBinValue(map.get(key), unionField55Unit));
                    break;
                default:
                    throw new IllegalArgumentException("unknown tlv value type " + unionField55Unit.getType());
            }
        }
        return unionValue.toString();
    }

    /**
     * @param byteNum
     * @return
     */
    private static String getLenValue(int byteNum) {
        if (byteNum >= 1 && byteNum <= 127) {
            return ByteUtil.dec2hex(byteNum); // 01111111 后7bit位表长度
        }
        if (byteNum > 127 && byteNum <= 255) {
            return "FF" + ByteUtil.dec2hex(byteNum); //10000001 后单字节表长度
        }
        if (byteNum > 255 && byteNum <= 65535) {
            return "FE" + ByteUtil.dec2hex(byteNum); //10000010 后双字节表长度
        }
        return "";
    }

    /**
     * 16进制bin值
     *
     * @param src
     * @param unionField55Unit
     * @return
     */
    private static String genBinValue(String src, UnionField55Unit unionField55Unit) {
        String length;
        switch (unionField55Unit.getVar()) {
            case BcomDecoder.STATIC:
                if (src.length() != (unionField55Unit.getLength() * 2))
                    throw new IllegalArgumentException("the tlv field " + unionField55Unit.getTag() + " needs " + unionField55Unit.getLength() + " bytes but " + src.length()/2 + " given");
                break;
            case BcomDecoder.VAR:
                if (src.length() > (unionField55Unit.getLength() * 2))
                    throw new IllegalArgumentException("illegal field length ! the max length is " + unionField55Unit.getLength() * 2 + " but " + src.length() + " chars given");
                break;
            default:
                throw new IllegalArgumentException("unknown tlv field type " + unionField55Unit.getType());
        }
        length = getLenValue(src.length() / 2);
        return unionField55Unit.getTag() + length + src;
    }

    /**
     * ascii值
     *
     * @param src
     * @param unionField55Unit
     * @return
     */
    private static String genAsciiValue(String src, UnionField55Unit unionField55Unit) {
        byte[] bytes = src.getBytes();
        switch (unionField55Unit.getVar()) {
            case BcomDecoder.STATIC:
                if (bytes.length != unionField55Unit.getLength())
                    throw new IllegalArgumentException("the tlv field " + unionField55Unit.getTag() + " needs " + unionField55Unit.getLength() + " bytes but " + bytes.length + " bytes given");
                break;
            case BcomDecoder.VAR:
                if (bytes.length > unionField55Unit.getLength())
                    throw new IllegalArgumentException("illegal field length ! the max length is " + unionField55Unit.getLength() + " but " + bytes.length + " bytes given");
                break;
            default:
                throw new IllegalArgumentException();
        }
        return unionField55Unit.getTag() + getLenValue(bytes.length) + ByteUtil.bytes2hex(bytes);
    }

    /**
     * bcd值
     *
     * @param src
     * @param unionField55Unit
     * @return
     */
    private static String genBcdValue(String src, UnionField55Unit unionField55Unit) {

        switch (unionField55Unit.getVar()) {
            case BcomDecoder.STATIC:
                if (src.length() != (unionField55Unit.getLength() * 2))
                    throw new IllegalArgumentException("the tlv field " + unionField55Unit.getTag() + " needs " + unionField55Unit.getLength() * 2 + " chars but " + src.length() + " given");
                break;
            case BcomDecoder.VAR:
                if (src.length() > (unionField55Unit.getLength() * 2))
                    throw new IllegalArgumentException("illegal field length ! the max length is " + unionField55Unit.getLength() * 2 + " but " + src.length() + " chars given");
                break;
            default:
                throw new IllegalArgumentException();
        }
        return unionField55Unit.getTag() + getLenValue(src.length() / 2) + src;
    }
}
