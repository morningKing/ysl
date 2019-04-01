package com.ysl;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class MapDefinition {

    private static final String VAR2 = "VAR2"; //变长 2字节长度

    private static final String VAR3 = "VAR3"; //变长 3字节长度

    public static final Map<String, Field> mapField = definitionInit("src/main/resources/config_8583.properties");

    private static Map<String, Field> definitionInit(String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException("8583 config file is not found");
        }
        Map<String, Field> fieldMap = new HashMap<>();
        Map<String, String> map;
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            properties.load(fileInputStream);
            map = new HashMap(properties);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("load 8583 config error");
        }
        Set<String> keys = map.keySet();
        try {
            for (String key : keys) {
                Field field = new Field();
                field.setName(key);
                String[] vals = map.get(key).split(",");
                if (VAR2.equals(vals[1].trim())) { // 变长域 长度为2 bcd压缩成1字节
                    field.setLength(1);
                    field.setVar(BcomDecoder.VAR);
                } else if (VAR3.equals(vals[1].trim())) { //变长域 长度3 右靠bcd压缩成2字节
                    field.setLength(2);
                    field.setVar(BcomDecoder.VAR);
                } else {// 定长域
                    int tmp = Integer.parseInt(vals[1]);
                    if ("bcd".equals(vals[2].trim())) { // bcd码 半字节代表一位十进制数
                        int len = tmp % 2 == 1 ? tmp + 1 : tmp; // 16进制bcd码长度无法是奇数
                        field.setVarLength(len / 2);
                    } else {
                        field.setVarLength(tmp);
                    }
                }
                fieldMap.put(field.getName(), field);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("filed definition fail");
        }
        return fieldMap;
    }
}
