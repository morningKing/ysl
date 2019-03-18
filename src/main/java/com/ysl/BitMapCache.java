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
        if ((bits = bitMap.get(key)) != null) {
            return bits;
        }
        bits = ByteUtil.bytes2bin(bytes);
        bitMap.put(key, bits);
        return bits;
    }
}
