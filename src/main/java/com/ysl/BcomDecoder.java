package com.ysl;

import com.ysl.handler.Handler;
import com.ysl.handler.ValueArg;
import com.ysl.handler.ValueHandlerHolder;

import java.util.Map;
import java.util.TreeMap;

public class BcomDecoder extends Decoder {

    public static final int VAR = 1;
    public static final int STATIC = 0;

    public static final int ASCII = 1;
    public static final int BCD = 0;
    public static final int TLV = 3;
    public static final int ZZZ = 4;
    public static final int BIN = 5;

    private static Handler handler = ValueHandlerHolder.getInstance();

    public Map<String, String> parse(byte[] body) {

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

        ValueArg valueArg = new ValueArg();

        for (int i = 0; i < bits.length; i++) {
            if (bits[i] != 49)
                continue;

            Field field = MapDefinition.getField(i + 1); //域属性
            if (field == null)
                throw new RuntimeException("field error index = " + (i + 1));

            byte[] value;
            int varLength;
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

            valueArg.setBytes(value);
            valueArg.setFlag(flag);
            valueArg.setIndex(i);
            valueArg.setType(field.getType());
            map.put(field.getName(), handler.handle(valueArg));
        }
        valueArg = null;
        return map;
    }

     public Map<String, String> parse(String str16) {
        byte[] bytes = ByteUtil.hex2bytes(str16);
        return this.parse(bytes);
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
        return varLength % 2 == 1;
    }

}
