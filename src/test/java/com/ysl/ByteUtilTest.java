package com.ysl;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * ByteUtil Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Mar 13, 2019</pre>
 */
public class ByteUtilTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: bytes2hex(byte[] src)
     */
    @Test
    public void testBytes2hex() throws Exception {
        byte[] bytes = {-1, -96}; //FFA0
        System.out.println(ByteUtil.bytes2hex(bytes));
    }

    /**
     * Method: hex2bytes(String hexStr)
     */
    @Test
    public void testHex2bytes() throws Exception {
        /**
         * test. 负数补码
         * 00011010 1A 2 + 8 + 16 = 26
         * 11111111 FF -1 补码表示 10000001
         * 10100000 A0 反码 11011111 补码 11100000 2^6+2^5 = 96
         * 两位16进制->1字节byte
         */
        System.out.println(ByteUtil.hex2bytes("FF1A")[0]);
        System.out.println(ByteUtil.hex2bytes("FF1A")[1]);
        System.out.println(ByteUtil.hex2bytes("A0")[0]);
    }

    /**
     * Method: printHexString(byte[] b)
     */
    @Test
    public void testPrintHexString() throws Exception {
        byte[] bytes = {-1, -1, 26, -96};
        ByteUtil.printHexString(bytes);
    }

    /**
     * Method: bytes2bin(byte[] bytes)
     */
    @Test
    public void testBytes2bin() throws Exception {
        byte[] bytes = {26};
        System.out.println(ByteUtil.bytes2bin(bytes));
    }

    /**
     * Method: dec2hex(int dec)
     */
    @Test
    public void testDec2hex() throws Exception {
//        System.out.println(ByteUtil.dec2hex(328));
        System.out.println(ByteUtil.dec2hex(323));
    }

    /**
     * Method: bytes2short(byte[] bytes)
     */
    @Test
    public void testBytes2short() throws Exception {
        byte[] bytes4 = {17, -1};
        System.out.println(Short.MIN_VALUE);
        System.out.println(Short.MAX_VALUE);// 2^15-1~-2^15
        System.out.println(ByteUtil.bytes2short(bytes4));
    }

    /**
     * Method: bytes2int(byte[] bytes)
     */
    @Test
    public void testBytes2int() throws Exception {
        /**
         * A0FF
         * 10100000 11111111
         * 2^8-1 2^13 2^15
         */
        byte[] bytes5 = {-96, -1};
        System.out.println(ByteUtil.bytes2int(bytes5));
    }

    /**
     * Method: bytes2tlv(byte[] bytes)
     */
    @Test
    public void testBytes2tlv() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: bytes2bcd(byte[] bytes)
     */
    @Test
    public void testBytes2bcd() throws Exception {
        /**
         * 21
         * 0010 0001
         * 21
         * 1+32=33
         */
        System.out.println(ByteUtil.bytes2bcd(ByteUtil.hex2bytes("21")));
    }

    /**
     * Method: bytes2ascii(byte[] bytes)
     */
    @Test
    public void testBytes2ascii() throws Exception {
        System.out.println(ByteUtil.bytes2ascii(ByteUtil.hex2bytes("2631")));
    }

    /**
     * Method: main(String[] args)
     */
    @Test
    public void testMain() throws Exception {

    }
} 
