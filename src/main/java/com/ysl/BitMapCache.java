package com.ysl;

import java.util.concurrent.ConcurrentHashMap;

public class BitMapCache {

    private static ConcurrentHashMap<String, String> bitMap = new ConcurrentHashMap<>();

    public static String getBitMap(byte[] bytes) {
        if (bytes.length != 8 && bytes.length != 16) {
            throw new RuntimeException("invalid bit map");
        }
        String key = ByteUtil.bytes2hex(bytes);
        String bits;
        assert key != null;
        if ((bits = bitMap.get(key)) != null) {
            return bits;
        }
        bits = ByteUtil.bytes2bin(bytes);
        bitMap.put(key, bits);
        return bits;
    }

    public static char[] initMap() {
        char[] bits = new char[64];
        for (int i = 0; i < 64; i++) {
            bits[i] = '0';
        }
        return bits;
    }

    public static String getBitMap(char[] chars) {
        StringBuilder bitmap = new StringBuilder();
        for (int i = 0; i < 64; i += 8) {
            bitmap.append(ByteUtil.dec2hex(Integer.parseInt((new String(chars, i, 8)), 2)));
        }
        return bitmap.toString();
    }

}
