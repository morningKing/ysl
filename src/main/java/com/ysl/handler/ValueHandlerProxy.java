package com.ysl.handler;

import com.ysl.wrapper.WrapperHolder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ValueHandlerProxy implements InvocationHandler {

    private Object valueHandler;

    public void setValueHandler(ValueHandler valueHandler) {
        this.valueHandler = valueHandler;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("handle")) {
            if (WrapperHolder.wrappers[((ValueArg) args[0]).getIndex()] != null) {
                return WrapperHolder.wrappers[((ValueArg) args[0]).getIndex()].handle(((ValueArg) args[0]).getBytes());
            }
            return method.invoke(valueHandler, args);
        }
        return method.invoke(valueHandler, args);
    }

    public Object crateProxyObj() {
        return Proxy.newProxyInstance(valueHandler.getClass().getClassLoader(), valueHandler.getClass().getInterfaces(), this);
    }
}
