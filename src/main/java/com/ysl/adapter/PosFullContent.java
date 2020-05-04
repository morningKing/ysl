package com.ysl.adapter;

import java.util.Map;

public class PosFullContent {

    @Field(unit = 4)
    private long amt;

    @Field(unit = 3)
    private String processCode;

    @Field(unit = 7)
    private String datetime;

    @Field(unit = 55)
    private Map pboc;

    public long getAmt() {
        return amt;
    }

    public void setAmt(long amt) {
        this.amt = amt;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Map getPboc() {
        return pboc;
    }

    public void setPboc(Map pboc) {
        this.pboc = pboc;
    }
}
