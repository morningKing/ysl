package com.ysl;

public class ByteUtil {

    public static String bytes2hex(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 十六进制转byte数组 一个十六进制数是4bit 字符'A'是8bit
     * A0
     * 0000 1010 0000 0000 原码
     * 1010 0000 <<4
     * 0000 0000 |
     * 1010 0000 原码
     * 1110 0000 补码
     * -（64+32）= -96
     * @param hexStr
     * @return
     */
    public static byte[] hex2bytes(String hexStr) {
        if (hexStr == null || hexStr.equals("") ||
                hexStr.length() % 2 == 1) {
            throw new RuntimeException("invalid 8583 message");
        }
        hexStr = hexStr.toUpperCase();
        int length = hexStr.length() / 2;
        char[] hexChars = hexStr.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 打印十六进制字符串
     * @param b
     */
    public static void printHexString(byte[] b) {
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase());
        }
    }

    /**
     * 字节数组转二进制字符串
     * @param bytes
     * @return
     */
    public static String bytes2bin(byte[] bytes) {
        StringBuffer bin = new StringBuffer();
        for (int i = 0; i <= bytes.length - 1; i++) {
            int b = bytes[i];
            if (b < 0) {
                b += 256; // 补码 负数 取模
            }
            bin.append(Integer.toBinaryString(b));
            while (bin.length() < 8) {
                bin = bin.insert(0, "0");
            }
        }
        return bin.toString();
    }

    /**
     * 十进制转十六进制
     * @param dec
     * @return
     */
    public static String dec2hex(int dec) {
        String decs = Integer.toHexString(dec);
        while (decs.length() % 2 == 1) {
            return "0" + decs;
        }
        return decs;
    }

    /**
     * short -2^15 ~ 2^15-1
     * -32768 ~ 32767
     * 由于第一位是符号位，所以不可能有大于 0111111111111111的short值
     *
     * @param bytes
     * @return
     */
    public static short bytes2short(byte[] bytes) {
        return (short) (0xFF00 & bytes[0] << 8 | 0xFF & bytes[1]);
    }

    public static int bytes2int(byte[] bytes) {
        String bin = bytes2bin(bytes);
        return Integer.parseInt(bin, 2);
    }

    public static String bytes2tlv(byte[] bytes){

        return null;
    }

    /**
     * 12 34 hex
     * 0001 0010 0011 0100 原码
     * 18,52
     * 1234 8421码
     * 解析8421 bcd码 压缩版本
     *
     * @param bytes
     * @return
     */
    public static String bytes2bcd(byte[] bytes) {
        String bcd = "";
        String hex = bytes2hex(bytes);
        if(hex != null && !hex.equals(""))
            bcd = String.valueOf(Long.parseLong(hex));
        return bcd;
    }

    /**
     * 数字 1
     * 31 hex
     * 1 dec
     * 0011 0001 原码
     * 49 byte
     * <p>
     * 符号 &
     * 26 hex
     * 38 dec
     * 0010 0110 原码
     * 38 byte
     *
     * @param bytes
     * @return
     */
    public static String bytes2ascii(byte[] bytes) {
        StringBuilder ascii = new StringBuilder();
        for (byte b : bytes) {
            ascii.append((char) (b & 0xFF));
        }
        return ascii.toString();
    }

    public static void main(String[] args) {
//        int i;
//        System.out.println(i = ("1".getBytes()[0] & 0xFF));
//        System.out.println(Integer.toBinaryString(i));
        /**
         * 十进制转16进制
         */
//        System.out.println(Integer.toHexString(i));
        /**
         * test. 负数补码
         * 00011010 1A 2 + 8 + 16 = 26
         * 11111111 FF -1 补码表示 10000001
         * 10100000 A0 反码 11011111 补码 11100000 2^6+2^5 = 96
         * 两位16进制->1字节byte
         */
//        System.out.println(hex2bytes("FF1A")[0]);
//        System.out.println(hex2bytes("FF1A")[1]);
//        System.out.println(hex2bytes("A0")[0]);
        /**
         * 16进制数位数
         */
//        System.out.println((byte) "0123456789ABCDEF".indexOf('F'));
        /**
         * test. 字节数组转二进制字符串
         */
        //        byte[] bytes = {26};
//        System.out.println(bytes2bin(bytes));
        /**
         * test. 十进制转十六进制
         */
//        System.out.println(dec2hex(328));
        /**
         * test. 十进制转byte数组
         */
//        byte[] bytes1 = hex2bytes(dec2hex(328));
//        for (byte b:
//             bytes1) {
//            System.out.println(b);
//        }
//        System.out.println(bytes2short(bytes1));
        /**
         * test. byte数组转十进制字符串
         */
//        byte[] bytes2 = {-1,-1};
//        System.out.println(bytes2short(bytes2));

        /**
         * ffff
         * 1111 1111 1111 1111
         * 1+2+4+...+2^15 = 65535
         * 0148
         * 0000 0001 0100 1000
         * 8 + 2^6 + 2^8  = 328
         *
         */
//        String hex = "FFFF";
//        System.out.println(Integer.parseInt(hex,16));

        /**
         * 第一个字节是负数
         * A000
         * 10100000 00000000
         * 11100000 00000000
         * {-96,0}
         *
         * FF01
         * 11111111 00000001
         * {-1,1} 65281
         * 10000001 00000001
         *
         *
         * FF01
         *
         * 10000001 00000001
         * {-1,1}
         *  -255 + 1
         *
         * 第一个字节是正数
         * 11FF
         * 00010001 11111111
         * {17,-1}
         * 2^0+2^1+...+2^8 + 2^12 = 4607
         *
         * 1+16=17
         */
//        byte[] bytes3 = {-1, -1};
//        System.out.println(bytes2short(bytes3));
//
//        byte[] bytes4 = {17, -1};
//        System.out.println(bytes2short(bytes4));
//
//        byte[] bytes5 = {-96, -1};
//        System.out.println(bytes2short(bytes5));
//
//        System.out.println(Integer.parseInt("1111111111111111", 2));

        /**
         * 有一位是符号位，所以FFFF的最大值不可能为65535
         */
//        System.out.println(Short.MAX_VALUE);
//        System.out.println(Short.MIN_VALUE);

        /**
         * A0FF
         * 10100000 11111111
         * 2^7+1 + 2^13 + 2^14 + 2 ^15
         */
//        System.out.println(bytes2int(bytes5));

//        System.out.println('1' == 49);
//        System.out.println(Integer.toBinaryString(32767));
//        byte[] byte6 = {-96};
//        bytes2bcd(byte6);

        /**
         * test. 测试解析8421码
         */
//        System.out.println(bytes2bcd(hex2bytes("A1")));
//        System.out.println(hex2bytes("26")[0]);
        /**
         * test. 测试解析ascii码
         */
//        System.out.println(bytes2ascii(hex2bytes("2631")));

//        byte[] bytes7 = new byte[5];
//        System.out.println(bytes7.length);
//        System.out.println(hex2bytes("34")[0]);
        System.out.println(bytes2bcd(hex2bytes("011022")));

//        System.out.println(Long.MAX_VALUE);
    }
}
