package com.ysl;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.HashMap;

/**
 * PosEncoder Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Mar 21, 2019</pre>
 */
public class PosEncoderTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getInstance()
     */
    @Test
    public void testGetInstance() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: encode(Map<String, String> map)
     */
    @Test
    public void testEncode() throws Exception {
        HashMap<String,String> map = new HashMap<>();
        map.put("tpdu","6006880000");
        map.put("mti","0200");
        map.put("head","604000180424");
        map.put("FIELD042","423332079292201");
        map.put("FIELD041","76000563");
        map.put("FIELD002","6258000000000253");
        map.put("FIELD003","000000");
        map.put("FIELD048","999");
        map.put("FIELD004","000000000115");
        map.put("FIELD011","001726");
        map.put("FIELD014","2704");
        map.put("FIELD035","6258000000000253132704201000004760000");
        map.put("FIELD038","001926");
        map.put("FIELD049","156");
        map.put("FIELD060","2000000400060");
        map.put("FIELD061","0000000000000302");
        map.put("FIELD062","金杰是我aa!@#");
        System.out.println(PosEncoder.getInstance().encode(map));;
//TODO: Test goes here... 
    }


} 
