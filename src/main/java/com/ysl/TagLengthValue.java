package com.ysl;

public class TagLengthValue {
    private String tag;
    private String len;
    private String value;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLen() {
        return len;
    }

    public void setLen(String len) {
        this.len = len;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{tag=[" + tag + "]," + "length=[" + len + "]," + "value=[" + value + "]}";
    }
}
