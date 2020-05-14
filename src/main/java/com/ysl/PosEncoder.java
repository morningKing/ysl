package com.ysl;

import java.util.Map;

public class PosEncoder {

    private PosEncoder() {
    }

    private static class PosEncoderHolder {
        private static PosEncoder posEncoder = new PosEncoder();
    }

    public static PosEncoder getInstance() {
        return PosEncoderHolder.posEncoder;
    }

    public static String encode(Map<String, String> map) {
        if (StringUtil.isEmpty(map.get("mti"))) {
            throw new IllegalArgumentException("Message type identify is null !");
        }
        if (ByteUtil.hex2bytes(map.get("mti")).length != 2) {
            throw new IllegalArgumentException("Message type must be 2 bytes but " + ByteUtil.hex2bytes(map.get("mti")).length + " given");
        }
        if (StringUtil.isEmpty(map.get("tpdu"))) {
            throw new IllegalArgumentException("Tpdu is null !");
        }
        if (ByteUtil.hex2bytes(map.get("tpdu")).length != 5) {
            throw new IllegalArgumentException("Tpdu must be 5 bytes but " + ByteUtil.hex2bytes(map.get("tpdu")).length + " given");
        }
        if (StringUtil.isEmpty(map.get("head"))) {
            throw new IllegalArgumentException("message head is null !");
        }
        if (ByteUtil.hex2bytes(map.get("head")).length != 6) {
            throw new IllegalArgumentException("Head must be 6 bytes but " + ByteUtil.hex2bytes(map.get("head")).length + " given");
        }
        String mth = map.get("tpdu") + map.get("head") + map.get("mti");
        map.remove("mti");
        map.remove("tpdu");
        map.remove("head");
        String content = mth + BcomEncoder.encode(map);
        String length = ByteUtil.dec2hex(content.length() / 2);
        length = StringUtil.strCopy(length, "0", 4 - length.length(), false);
        return length + content;
    }
}
