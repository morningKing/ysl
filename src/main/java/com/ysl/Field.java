package com.ysl;

public class Field {
    private int var;
    private int length;
    private int type;
    private String name;

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

    private int varLength;

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
