package com.ysl.handler;

import com.ysl.wrapper.UnitClassLoader;

public class ValueHandlerHolder {

    private static volatile Handler handler;

    public synchronized static Handler getInstance() {
        if (handler != null) {
            return handler;
        }

        UnitClassLoader unitClassLoader = new UnitClassLoader();
        unitClassLoader.load();

        ValueHandlerProxy valueHandlerProxy = new ValueHandlerProxy();
        ValueHandler valueHandler = new ValueHandler();
        valueHandlerProxy.setValueHandler(valueHandler);
        handler = (Handler) valueHandlerProxy.crateProxyObj();
        return handler;

    }
}
