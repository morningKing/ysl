package com.ysl;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class MapDefinition {

    private static final String VAR2 = "VAR2";

    private static final String VAR3 = "VAR3";

    public static List<Field> definitionInit(String path) {
        File file = new File(path);
        if (file.exists()) {
            throw new RuntimeException("8583 config file is not found");
        }
        List<Field> list = new ArrayList<>(); //域表
        Map<String, String> map;
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            properties.load(fileInputStream);
            map = new HashMap(properties);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("");
        }
        Set<String> keys = map.keySet();
        try {
            for (String key : keys) {
                Field field = new Field();
                String[] vals = map.get(key).split(",");
                if (VAR2.equals(vals[1])) { // 变长域 长度为2
                    field.setLength(2);
                    field.setVar(BcomDecoder.VAR);
                } else if (VAR3.equals(vals[1])) { //变长域 长度3
                    field.setLength(3);
                    field.setVar(BcomDecoder.VAR);
                } else {// 定长域
                    field.setVarLength(Integer.parseInt(vals[1]));
                }

                if("true".equals(vals[2])){
                    field.setType(BcomDecoder.BCD);
                }else if("false".equals(vals[2])){
                    field.setType(BcomDecoder.ASCII);
                }else{
                    field.setType(BcomDecoder.TLV);
                }

                list.add(field);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("");
        }
        return list;
    }
}
