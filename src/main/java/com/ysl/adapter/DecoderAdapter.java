package com.ysl.adapter;

public interface DecoderAdapter {
    <T> T parse(byte[] bytes, Class<T> clz);

    <T> T parse(String content, Class<T> clz);
}
