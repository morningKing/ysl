package com.ysl;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.HashMap;
import java.util.Map;

/**
 * TlvEncoder Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Mar 20, 2019</pre>
 */
public class TlvEncoderTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: encode(Map<String, String> map)
     */
    @Test
    public void testEncode() throws Exception {
        Map<String,String> map = new HashMap<>();
//        map.put("9F26","B1AAD1CB0083B502");
//        map.put("9F27","80");
//        map.put("9F10","07010103A00000040A01000000000095DE79D3");
//        map.put("9F37","86F5FD32");
        map.put("9F1E","84714622");
        System.out.println(TlvEncoder.encode(map));
//TODO: Test goes here... 
    }

    /**
     * Method: main(String[] args)
     */
    @Test
    public void testMain() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: getLenValue(int byteNum)
     */
    @Test
    public void testGetLenValue() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = TlvEncoder.getClass().getMethod("getLenValue", int.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: genBinValue(String src, UnionField55Unit unionField55Unit)
     */
    @Test
    public void testGenBinValue() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = TlvEncoder.getClass().getMethod("genBinValue", String.class, UnionField55Unit.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: genAsciiValue(String src, UnionField55Unit unionField55Unit)
     */
    @Test
    public void testGenAsciiValue() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = TlvEncoder.getClass().getMethod("genAsciiValue", String.class, UnionField55Unit.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: genBcdValue(String src, UnionField55Unit unionField55Unit)
     */
    @Test
    public void testGenBcdValue() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = TlvEncoder.getClass().getMethod("genBcdValue", String.class, UnionField55Unit.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
