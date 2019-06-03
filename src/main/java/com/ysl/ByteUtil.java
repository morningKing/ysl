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
        StringBuilder bin = new StringBuilder();
        for (int i = 0; i <= bytes.length - 1; i++) {
            int b = bytes[i];
            if (b < 0) {
                b += 256; // 补码 负数 取模
            }
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.toBinaryString(b));
            while (sb.length() < 8) {
                sb = sb.insert(0, "0");
            }
            bin.append(sb);
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

    /**
     * 12 34 hex
     * 0001 0010 0011 0100 原码
     * 18,52
     * 1234 8421码
     * 解析8421 bcd码 压缩版本
     * 数字域，右靠齐，左边多余位填零；
     * @param bytes
     * @return
     */
    public static String bytes2bcd(byte[] bytes) {
        StringBuffer bcd = new StringBuffer(bytes.length * 2);
        for (int i = 0; i<bytes.length;i++){
            bcd.append((byte)((bytes[i] & 0xF0) >>> 4));
            bcd.append((byte)(bytes[i] & 0x0F));
        }
        if(bcd.length() > (bytes.length * 2)) {
            throw new RuntimeException("Illegal bcd byte code " + printByte(bytes));
        }
        return bcd.toString();
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

    public static String printByte(byte[] bytes){
        String[] bytess = new String[bytes.length];
        for(int i = 0; i<bytes.length;i++) {
            bytess[i] = String.valueOf((int)bytes[i]);
        }
        return String.join(",",bytess);
    }


    public static void main(String[] args) {
    }
}
