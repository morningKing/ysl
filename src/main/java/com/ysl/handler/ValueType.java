package com.ysl.handler;

public enum ValueType {
    ASCII(1), BCD(0), TLV(3), ZZZ(4), BIN(5);
    int value;

    ValueType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ValueType getType(int value) {
        for(ValueType vt : values()) {
            if(vt.getValue() == value) {
                return vt;
            }
        }
        return null;
    }
}
