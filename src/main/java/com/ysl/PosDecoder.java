package com.ysl;

import java.util.Map;
import java.util.TreeMap;

public class PosDecoder {

    private static volatile PosDecoder posDecoder;

    private PosDecoder() {
    }

    /**
     * 双检锁维护单例
     *
     * @return
     */
    public static PosDecoder getInstance() {
        if (posDecoder == null) {
            synchronized (PosDecoder.class) {
                if (posDecoder == null) {
                    posDecoder = new PosDecoder();
                }
            }
        }
        return posDecoder;
    }

    /**
     * len(2byte) tpdu(10byte) head(12byte) body
     * tpdu bcd 压缩用5字节表示
     * head bcd 压缩用6字节表示
     * 2+5+6
     */
    public Map<String, String> parse(byte[] bytes) {
        if (bytes == null || bytes.length == 0
                || !BcomDecoder.checkLength(bytes)) {
            throw new RuntimeException("8583 msg length is invalid");
        }
        Map<String, String> map = new TreeMap<>();
        byte[] tpdu = new byte[5];
        System.arraycopy(bytes, 2, tpdu, 0, 5);
        byte[] head = new byte[6];
        System.arraycopy(bytes, 7, head, 0, 6);
        byte[] body = new byte[bytes.length - 13];
        System.arraycopy(bytes, 13, body, 0, bytes.length - 13);
        Map<String, String> bodyMap = BcomDecoder.parse(body);

        map.putAll(bodyMap);
        return map;
    }

    public Map<String, String> parse(String str16) {
        byte[] bytes = ByteUtil.hex2bytes(str16);
        return posDecoder.parse(bytes);
    }
}
