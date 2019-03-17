package com.ysl;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * BcomDecoder Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Mar 14, 2019</pre>
 */
public class BcomDecoderTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: parse(byte[] bytes)
     */
    @Test
    public void testParseBytes() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: parse(String str16)
     */
    @Test
    public void testParseStr16() throws Exception {
        String str16 = "00A660068800006040001804240200702406C024C0981D166258000000000253000000000000000115001726270407100001061220376258000000000253D2704201000004760000003030313932363736303030353633343233333332303739323932323031313536000000000000000024000000000000000013200000040006000016000000000000030200180010D2717312A2737E9377BC7E5C1A6F4C433546444244343032";
        BcomDecoder.parse(str16);
    }

    /**
     * Method: main(String[] args)
     */
    @Test
    public void testMain() throws Exception {
        System.out.println(Double.MAX_VALUE);
//TODO: Test goes here... 
    }


    /**
     * Method: bit2map(byte[] bytes)
     */
    @Test
    public void testBit2map() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = BcomDecoder.getClass().getMethod("bit2map", byte[].class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: checkLength(byte[] bytes)
     */
    @Test
    public void testCheckLength() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = BcomDecoder.getClass().getMethod("checkLength", byte[].class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    /**
     * Method: getField(int i)
     */
    @Test
    public void testGetField() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = BcomDecoder.getClass().getMethod("getField", int.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 
