package com.ysl;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.HashMap;
import java.util.Map;

/**
 * BcomEncoder Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Mar 19, 2019</pre>
 */
public class BcomEncoderTest {

    private long l;

    @Before
    public void before() throws Exception {
        l = System.currentTimeMillis();
    }

    @After
    public void after() throws Exception {
        l = System.currentTimeMillis() - l;
        System.out.println("运行了" + l + "毫秒");
    }

    /**
     * Method: main(String[] args)
     */
    @Test
    public void testMain() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: encode(Map<String, String> map)
     */
    @Test
    public void testEncode() throws Exception {
//TODO: Test goes here...
        Map<String,String> map = new HashMap<>();
//        map.put("FIELD042","423332079292201");
//        map.put("FIELD041","76000563");
//        map.put("FIELD002","6258000000000253");
//        map.put("FIELD003","000000");
//        map.put("FIELD048","999");
//        map.put("FIELD004","000000000115");
//        map.put("FIELD011","001726");
//        map.put("FIELD014","2704");
//        map.put("FIELD035","6258000000000253132704201000004760000");
//        map.put("FIELD038","001926");
//        map.put("FIELD049","156");
//        map.put("FIELD060","2000000400060");
//        map.put("FIELD061","0000000000000302");
//        map.put("FIELD062","金杰是我aa!@#");
        System.out.println(BcomEncoder.encode(map));
/* 
try { 
   Method method = BcomEncoder.getClass().getMethod("encode", Map<String,.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: genBcdLenth(String src, int count, boolean flag)
     */
    @Test
    public void testGenBcdLenth() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = BcomEncoder.getClass().getMethod("genBcdLenth", String.class, int.class, boolean.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: genBcdValue(String src, Field field)
     */
    @Test
    public void testGenBcdValue() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = BcomEncoder.getClass().getMethod("genBcdValue", String.class, Field.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: genAsciiValue(String src, Field field)
     */
    @Test
    public void testGenAsciiValue() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = BcomEncoder.getClass().getMethod("genAsciiValue", String.class, Field.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getZZZValue(String src, String key, Field field)
     */
    @Test
    public void testGetZZZValue() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = BcomEncoder.getClass().getMethod("getZZZValue", String.class, String.class, Field.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getFieldIndex(String key)
     */
    @Test
    public void testGetFieldIndex() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = BcomEncoder.getClass().getMethod("getFieldIndex", String.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
