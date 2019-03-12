package com.ysl;

import java.util.ArrayList;
import java.util.List;

public class TlvDeocder {

    public static List<TagLengthValue> parse(byte[] bytes) {
        List<TagLengthValue> list = new ArrayList<>();
        int index = 0;
        for (; ; ) {
            if (index == bytes.length - 1)
                break;
            TagLengthValue tlv = new TagLengthValue();
            int tagLength;
            if ((bytes[index] & 0x1F) == 31) {
                tagLength = 2;
            } else {
                tagLength = 1;
            }
            byte[] tag = new byte[tagLength];
            System.arraycopy(bytes, index, tag, 0, tagLength);
            tlv.setTag(ByteUtil.bytes2hex(tag));
            index += tagLength;

            byte[] keys = {bytes[index]};
            if ((keys[0] & 0xFF) >> 7 == 1) { //最左bit为0则长度为单字节，后七位bit表子域长度 0~127

            } else if ((keys[0] & 0x0F) == 1) { //最左bit为1，低四位为1 后单字节表长度 127~255
                index += 1;
                keys[0] = bytes[index];
            } else if ((keys[0] & 0x0F) == 2) { //最左bit为1，第四位为2 后双字节表长度 255~65535
                index += 1;
                keys[1] = bytes[index];
                index += 1;
                keys[2] = bytes[index];
            } else {
                throw new RuntimeException("tvl length should be limit 1 ~ 3 byte");
            }
            tlv.setLen(String.valueOf(ByteUtil.bytes2int(keys)));

            byte[] value = new byte[Integer.valueOf(tlv.getLen())];
            System.arraycopy(bytes, index, value, 0, Integer.valueOf(tlv.getLen()));
            tlv.setValue(ByteUtil.bytes2hex(value));

            list.add(tlv);
        }
        return list;
    }

    public static String format(byte[] bytes) {
        StringBuilder formatTlv = new StringBuilder();
        List<TagLengthValue> list = parse(bytes);
        for (TagLengthValue tlv : list) {
            formatTlv.append(tlv.toString());
        }
        return formatTlv.toString();
    }
}
