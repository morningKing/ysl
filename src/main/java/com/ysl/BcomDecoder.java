package com.ysl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BcomDecoder{

    public static final int VAR = 1;
    public static final int STATIC = 0;

    public static final int ASCII = 1;
    public static final int BCD = 0;
    public static final int TLV = 3;

    private static List<Field> list = new ArrayList<>();

    static {

    }

    public static Map<String, String> parse(byte[] bytes) {
        if (checkLength(bytes))
            return null;
        Map<String, String> map = new TreeMap<>();
        /**
         * len(2byte) tpdu(10byte) head(12byte) body
         */
        byte[] tpdu = new byte[10];
        System.arraycopy(bytes, 2, tpdu, 0, 10);
        byte[] head = new byte[12];
        System.arraycopy(bytes, 12, head, 0, 12);
        byte[] body = new byte[bytes.length - 24];
        System.arraycopy(bytes, 24, body, 0, bytes.length - 24);

        int index = 0;
        byte[] msi = new byte[2];
        System.arraycopy(body, index, msi, 0, 2);
        map.put("mti", ByteUtil.bytes2hex(msi));
        index += 2;

        //todo 先默认位图为8字节
        byte[] bitmap = new byte[8];
        System.arraycopy(body, index, bitmap, 0, 8);
        index += 8;
        char[] bits = bit2map(bitmap).toCharArray();
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] != 49)
                continue;
            //todo 默认设置一个域
            Field field = new Field();
            if (field == null)
                throw new RuntimeException("field error");

            byte[] value;
            if (field.getVar() == VAR) { //变长域
                byte[] len = new byte[field.getLength()]; //长度字节数
                System.arraycopy(body, index, len, 0, field.getLength());
                value = new byte[Integer.parseInt(ByteUtil.bytes2bcd(len))]; //value值字节数
                index += field.getLength();
            } else if (field.getVar() == STATIC) { //定长域
                value = new byte[field.getVarLength()];
            } else {
                throw new RuntimeException("");
            }

            System.arraycopy(body, index, value, 0, value.length);
            index += value.length;
            String fieldValue;
            switch (field.getType()) { //value 类型
                case ASCII:
                    fieldValue = ByteUtil.bytes2ascii(value);
                    break;
                case BCD:
                    fieldValue = String.valueOf(ByteUtil.bytes2bcd(value));
                    break;
                case TLV:
                    fieldValue = TlvDecoder.format(value);
                    break;
                default:
                    throw new RuntimeException("");
            }
            map.put("Filed" + i, fieldValue);
        }
        return map;
    }

    public static Map<String, String> parse(String str16) {
        byte[] bytes = ByteUtil.hex2bytes(str16);
        return parse(bytes);
    }

    /**
     * 解析位图
     *
     * @param bytes
     * @return
     */
    private static String bit2map(byte[] bytes) {
        if (bytes.length != 8 && bytes.length != 16) {
            throw new RuntimeException("invalid bit map");
        }
        return ByteUtil.bytes2bin(bytes);
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
    private static boolean checkLength(byte[] bytes) {
        byte[] head = new byte[2];
        System.arraycopy(bytes, 0, head, 0, 2);
        return ByteUtil.bytes2int(head) == (bytes.length - 2);
    }

    public static void main(String[] args) {
        /**
         * 0166
         * 00000001 01100110
         * 2+4+32+64+128=230
         */
        String str16 = "00A660068800006040001804240200702406C024C0981D166258000000000253000000000000000115001726270407100001061220376258000000000253D2704201000004760000003030313932363736303030353633343233333332303739323932323031313536000000000000000024000000000000000013200000040006000016000000000000030200180010D2717312A2737E9377BC7E5C1A6F4C433546444244343032";
        byte[] bytes = ByteUtil.hex2bytes(str16);
        byte[] head = new byte[2];
        System.arraycopy(bytes, 0, head, 0, 2);
        System.out.println(ByteUtil.bytes2int(head));
        System.out.println(bytes.length - 2);
        if (checkLength(bytes)) {
            System.out.println("length true");
        } else {
            System.out.println("length false");
        }
    }

}
