package com.ysl.wrapper;

import com.ysl.TlvDecoder;

@UnitWrapper(unit = 55)
public class Unit55Wrapper implements Wrapper {
    @Override
    public String handle(byte[] bytes) {
        return TlvDecoder.format(bytes);
    }
}
