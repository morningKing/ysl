package com.ysl;
/**
 * 描述：config_8583.properties对应的实体
 * 时间：2019-04-28 13:55:11
 * author: jinjie
 */
public class Field {
    private int var;
    private int length;
    private int type;
    private String name;
    private int varLength; //字节数

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVarLength() {
        return varLength;
    }

    public void setVarLength(int varLength) {
        this.varLength = varLength;
    }

    public int getVar() {
        return var;
    }

    public void setVar(int var) {
        this.var = var;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
