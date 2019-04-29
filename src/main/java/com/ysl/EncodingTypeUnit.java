package com.ysl;

/**
 * 描述：8583报文编码格式自定义值的转换（与config_8583.properties配置文件对比）
 * 时间：2019-04-28 15:59:22
 * author：wwd
 */
public enum EncodingTypeUnit {
    CODE_BCD(0,"bcd"),
    CODE_ASC(1,"asc"),
    CODE_ZZZ(4,"zzz"),
    CODE_TLV(3,"tlv"),
    CODE_BIN(5,"bin");
    int code;
    String msg;
    EncodingTypeUnit(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static String getCodeByMsg(int code){
        String msg = "";
        for (EncodingTypeUnit codeTypeToValue : EncodingTypeUnit.values()) {
            if(codeTypeToValue.getCode() == code){
                msg = codeTypeToValue.getMsg();
                break;
            }
        }
        return msg;
    }
    public static int getMsgByCode(String msg){
        int code = 0;
        for (EncodingTypeUnit codeTypeToValue : EncodingTypeUnit.values()) {
            if(codeTypeToValue.getMsg().equals(msg)){
                code = codeTypeToValue.getCode();
                break;
            }
        }
        return code;
    }

}
