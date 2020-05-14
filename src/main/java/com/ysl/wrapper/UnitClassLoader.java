package com.ysl.wrapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;

public class UnitClassLoader {

    private static final String WRAPPER_PATH = "com.ysl.wrapper";

    public void load() {
        try {
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(WRAPPER_PATH.replace(".", "/"));
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                String packagePath = url.getPath().replaceAll("%20", " ");
                System.out.println(packagePath);
                File[] files = new File(packagePath).listFiles(file -> (file.isFile() && file.getName().endsWith(".class")));
                assert files != null;
                for (File f : files) {
                    Class clz = Class.forName(WRAPPER_PATH + "." + f.getName().replace(".class", ""));
                    if (!Arrays.stream(clz.getAnnotations()).noneMatch(annotation -> (annotation instanceof UnitWrapper))) {
                        UnitWrapper wrapper = (UnitWrapper) clz.getAnnotation(UnitWrapper.class);
                        try {
                            WrapperHolder.wrappers[wrapper.unit() - 1] = (Wrapper) clz.newInstance();
                        } catch (InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
