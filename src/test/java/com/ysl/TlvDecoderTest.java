package com.ysl;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.Iterator;
import java.util.List;

/**
 * TlvDecoder Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Apr 1, 2019</pre>
 */
public class TlvDecoderTest {

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
    public void testParse() throws Exception {
//TODO: Test goes here...
        String msg = "9F101307010103A00000040A01000000000095DE79D39F2701809F2608B1AAD1CB0083B5029F370486F5FD329F1E083834373134363232";
        List<TagLengthValue> list = TlvDecoder.parse(ByteUtil.hex2bytes(msg));
        Iterator<TagLengthValue> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().toString());
        }

    }

    /**
     * Method: format(byte[] bytes)
     */
    @Test
    public void testFormat() throws Exception {
//TODO: Test goes here... 
    }


} 
