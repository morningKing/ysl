package com.ysl;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BcomEncoder extends Encoder{

    static String encode(Map<String, String> map) {
        if (!(map instanceof TreeMap)) {
            map = new TreeMap<>(map);
        }
        Set<String> set = map.keySet();
        if (set.size() == 0)
            throw new IllegalArgumentException("value map is invalid");

        StringBuilder body = new StringBuilder();

        char[] bitmap = BitMapCache.initMap();

        for (String key : set) {
            Field field = MapDefinition.mapField.get(key);
            if (field == null) {
                throw new IllegalArgumentException("map field is invalid");
            }
            if (map.get(key) == null || map.get(key).equals("")) {
                throw new RuntimeException("illegal field value in " + field.getName());
            }

            bitmap[getFieldIndex(key) - 1] = '1'; //构造位图

            FieldEntry fieldEntry = new FieldEntry();
            switch (field.getType()) { //类型
                case BcomDecoder.ASCII:
                    fieldEntry = genAsciiValue(map.get(key), field); //ascii码 长度是字节数
                    break;
                case BcomDecoder.BCD:
                    fieldEntry = genBcdValue(map.get(key), field); //bcd码 长度字节数的一半
                    break;
                case BcomDecoder.TLV:
                    fieldEntry = getTlvValue(map.get(key), field);
                    break;
                case BcomDecoder.ZZZ:
                    fieldEntry = getZZZValue(map.get(key), key, field); //接收字符串(字节字符/非16进制)
                    break;
                case BcomDecoder.BIN:

                    break;
                default:
                    throw new RuntimeException("unknown value type index = " + field.getName() + " type = " + field.getType());
            }

            body.append(fieldEntry.getLength()); //拼接长度
            body.append(fieldEntry.getValue()); //拼接内容
        }
        String bits = BitMapCache.getBitMap(bitmap);
        body.insert(0, bits);
        return body.toString();
    }

    /**
     * @param src
     * @return
     */
    private static FieldEntry getTlvValue(String src, Field field) {
        FieldEntry fieldEntry = new FieldEntry();
        fieldEntry.setLen(src.length());
        String count = String.valueOf(src.length() / 2);
        fieldEntry.setLength(StringUtil.strCopy(count, "0", 4 - count.length(), false));
        fieldEntry.setValue(src);
        return fieldEntry;
    }

}
