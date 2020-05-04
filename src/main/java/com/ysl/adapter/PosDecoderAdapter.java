package com.ysl.adapter;

import com.ysl.MapDefinition;
import com.ysl.PosDecoder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PosDecoderAdapter implements DecoderAdapter {

    private static PosDecoderAdapter posDecoderAdapter = new PosDecoderAdapter();

    private PosDecoderAdapter() {

    }

    public static PosDecoderAdapter getPosDecoderAdapter() {
        return posDecoderAdapter;
    }

    @Override
    public <T> T parse(byte[] bytes, Class<T> clz) {
        T obj = null;
        Map<String, String> map = PosDecoder.getInstance().parse(bytes);
        try {
            obj = clz.newInstance();
            for (Field field : clz.getFields()) {
                for (Annotation annotation : field.getAnnotations()) {
                    if (annotation instanceof com.ysl.adapter.Field) {
                        putFiledValue(field, map, annotation, obj);
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public <T> T parse(String content, Class<T> clz) {
        T obj = null;
        Map<String, String> map = PosDecoder.getInstance().parse(content);
        try {
            obj = clz.newInstance();

            for (Field field : clz.getDeclaredFields()) {
                field.setAccessible(true);
                for (Annotation annotation : field.getAnnotations()) {
                    if (annotation instanceof com.ysl.adapter.Field) {
                        putFiledValue(field, map, annotation, obj);
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }

    private void putFiledValue(Field field, Map<String, String> map, Annotation annotation, Object obj) throws IllegalAccessException {
        String typeName = field.getGenericType().getTypeName();
        String value = map.get(MapDefinition.getField(((com.ysl.adapter.Field) annotation).unit()).getName());
        if ("long".equals(typeName) || "Long".equals(typeName)) {
            field.set(obj, Long.parseLong(value));
        }
        if ("int".equals(typeName) || "Integer".equals(typeName)) {
            field.set(obj, Integer.parseInt(value));
        }
        if ("float".equals(typeName) || "Float".equals(typeName)) {
            field.set(obj, Float.valueOf(value));
        }
        if ("double".equals(typeName) || "Double".equals(typeName)) {
            field.set(obj, Double.valueOf(value));
        }
        if ("java.util.Map".equals(typeName)) {
            Pattern tagPattern = Pattern.compile("tag=\\[\\w+\\]");
            Matcher tagMatcher = tagPattern.matcher(value);
            Map<String, String> tlvMap = new LinkedHashMap<>();
            while (tagMatcher.find()) {
                tlvMap.put(tagMatcher.group().replace("tag=[", "").replace("]", ""), null);
            }
            Iterator<String> iterator = tlvMap.keySet().iterator();
            if (!iterator.hasNext()) {
                throw new RuntimeException("unknown tlv data transfer error :" + field.getName());
            }
            Pattern valuePattern = Pattern.compile("value=\\[\\w+\\]");
            Matcher valueMatcher = valuePattern.matcher(value);
            while (valueMatcher.find()) {
                tlvMap.put(iterator.next(), valueMatcher.group().replace("value=[", "").replace("]", ""));
            }
            field.set(obj, tlvMap);
        }
    }
}
