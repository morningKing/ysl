package com.ysl;

public enum UnionField55Unit {

    AC("9F26", 8, BcomDecoder.BIN, BcomDecoder.STATIC),
    CID("9F27", 1, BcomDecoder.BIN, BcomDecoder.STATIC),
    IAF("9F10", 32, BcomDecoder.BIN, BcomDecoder.VAR),
    UN("9F37", 4, BcomDecoder.BIN, BcomDecoder.STATIC),
    ATC("9F36", 2, BcomDecoder.BIN, BcomDecoder.STATIC),
    TVR("95", 5, BcomDecoder.BIN, BcomDecoder.STATIC),
    TD("9A", 3, BcomDecoder.BCD, BcomDecoder.STATIC),
    TT("9C", 1, BcomDecoder.BCD, BcomDecoder.STATIC),
    TAOAA("9F02", 6, BcomDecoder.BCD, BcomDecoder.STATIC),
    TCC("5F2A", 2, BcomDecoder.BCD, BcomDecoder.STATIC),
    TIP("82", 2, BcomDecoder.BIN, BcomDecoder.STATIC),
    TCC2("9F1A", 2, BcomDecoder.BCD, BcomDecoder.STATIC),
    AO("9F03", 6, BcomDecoder.BCD, BcomDecoder.STATIC),
    TC("9F33", 3, BcomDecoder.BIN, BcomDecoder.STATIC),
    CVMR("9F34", 3, BcomDecoder.BIN, BcomDecoder.STATIC),
    TT2("9F35", 1, BcomDecoder.BCD, BcomDecoder.STATIC),
    IFD("9F1E", 8, BcomDecoder.ASCII, BcomDecoder.STATIC),
    DF("84", 16, BcomDecoder.BIN, BcomDecoder.VAR),
    TAVN("9F09", 2, BcomDecoder.BIN, BcomDecoder.STATIC),
    TSC("9F41", 4, BcomDecoder.BCD, BcomDecoder.VAR),
    IAD("91", 16, BcomDecoder.BIN, BcomDecoder.VAR),
    IST1("71", 128, BcomDecoder.BIN, BcomDecoder.VAR),
    IST2("72", 128, BcomDecoder.BIN, BcomDecoder.VAR),
    ISR("DF31", 21, BcomDecoder.BIN, BcomDecoder.VAR),
    ECIAC("9F74", 6, BcomDecoder.ASCII, BcomDecoder.STATIC),
    CPI("9F63", 16, BcomDecoder.BIN, BcomDecoder.STATIC),
    ARC("8A", 2, BcomDecoder.ASCII, BcomDecoder.STATIC);

    public String getTag() {
        return tag;
    }

    public int getLength() {
        return length;
    }

    public int getType() {
        return type;
    }

    public int getVar() {
        return var;
    }

    UnionField55Unit(String tag, int length, int type, int var) {
        this.tag = tag;
        this.length = length;
        this.type = type;
        this.var = var;
    }

    final String tag;
    final int length;
    final int type;
    final int var;

    public static UnionField55Unit isValid(String tag) {
        for (UnionField55Unit unionField55Unit : UnionField55Unit.values()) {
            if (unionField55Unit.getTag().equals(tag)) {
                return unionField55Unit;
            }
        }
        return null;
    }

    public static String toTagString(){
        String[] tags = new String[UnionField55Unit.values().length];
        int i = 0;
        for(UnionField55Unit unionField55Unit : UnionField55Unit.values()) {
            tags[i] = unionField55Unit.getTag();
            i++;
        }
        return String.join(",",tags);
    }
}
