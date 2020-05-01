package com.ysl.handler;

import java.util.Objects;

public class ValueHandler extends AbstractHandler implements Handler {

    @Override
    public String handle(ValueArg arg) {
        String fieldValue;
        switch (Objects.requireNonNull(ValueType.getType(arg.getType()))) { //value 类型
            case ASCII:
                fieldValue = getAsciiValue(arg.getBytes());
                break;
            case BCD:
                fieldValue = getBcdValue(arg.getBytes(), arg.getFlag());
                break;
//            case TLV:
//                fieldValue = getTlvValue(arg.getBytes());
//                break;
            case ZZZ:
                fieldValue = getTrackValue(arg.getBytes(),arg.getIndex() + 1);
                break;
            case BIN:
                fieldValue = getBinValue(arg.getBytes());
                break;
            default:
                throw new RuntimeException("unknow value type index = " + (arg.getIndex() + 1));
        }
        return fieldValue;
    }

    public void test(){
        System.out.println("hello world");
    }

}
