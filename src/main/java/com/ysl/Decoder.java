package com.ysl;

public abstract class Decoder {

    public static final int SEC = 35;
    public static final int THRD = 36;
    /**
     * 得到bcd value
     *
     * @param bytes
     * @param flag
     * @return
     */
    protected static String getBcdValue(byte[] bytes, boolean flag) {
        if (!flag) //定长域 或者 非补零变长
            return ByteUtil.bytes2bcd(bytes);
        String bcd = ByteUtil.bytes2bcd(bytes);
        return bcd.substring(0, bcd.length() - 1);
    }

    /**
     * 一磁 二磁 三磁数据
     *
     * @param bytes
     * @return
     */
    protected static String getTrackValue(byte[] bytes, int index) {
        String track = ByteUtil.bytes2hex(bytes);
        if (track != null && !track.equals("") && index == SEC) {
            return track.replace("D", "=").replace("d", "=");
        }
        return track;
    }

    /**
     * tlv 数据
     *
     * @param bytes
     * @return
     */
    protected static String getTlvValue(byte[] bytes) {
        return TlvDecoder.format(bytes);
    }

    /**
     * ascii数据
     *
     * @param bytes
     * @return
     */
    protected static String getAsciiValue(byte[] bytes) {
        return ByteUtil.bytes2ascii(bytes);
    }

    /**
     * 二进制数据
     *
     * @param bytes
     * @return
     */
    protected static String getBinValue(byte[] bytes) {
        return ByteUtil.bytes2hex(bytes);
    }
}
