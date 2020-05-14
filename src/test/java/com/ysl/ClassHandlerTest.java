package com.ysl;


import com.ysl.handler.Handler;
import com.ysl.handler.ValueArg;
import com.ysl.handler.ValueHandler;
import com.ysl.handler.ValueHandlerProxy;
import com.ysl.wrapper.UnitWrapper;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.stream.Collectors;

public class ClassHandlerTest {

    @Test
    public void srClass() throws IOException, ClassNotFoundException {
        Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources("com.ysl.handler".replace(".", "/"));
        while(urls.hasMoreElements()) {
            URL url = urls.nextElement();
            String packagePath = url.getPath().replaceAll("%20", " ");
            System.out.println(packagePath);
            File[] files = new File(packagePath).listFiles(file -> (file.isFile() && file.getName().endsWith(".class")));
            for(File f : files) {
                Class clz = Class.forName("com.ysl.handler." + f.getName().replace(".class",""));
                if(Arrays.stream(clz.getAnnotations()).filter(annotation -> {return (annotation instanceof UnitWrapper);}).collect(Collectors.toList()).isEmpty()){
                    continue;
                } else {
                    UnitWrapper wrapper = (UnitWrapper) clz.getAnnotation(UnitWrapper.class);
                    System.out.println(wrapper.unit());
                }
            }
        }
    }

    @Test
    public void proxy(){
        ValueHandlerProxy valueHandlerProxy = new ValueHandlerProxy();
        ValueHandler valueHandler = new ValueHandler();
        valueHandlerProxy.setValueHandler(valueHandler);
        Handler proxy = (Handler) valueHandlerProxy.crateProxyObj();
        ValueArg arg = new ValueArg();
    }
}
