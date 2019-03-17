package com.ysl;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * MapDefinition Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>Mar 13, 2019</pre>
 */
public class MapDefinitionTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: definitionInit(String path)
     */
    @Test
    public void testDefinitionInit() throws Exception {
        Map<String,Field> map = MapDefinition.definitionInit("src/test/resources/config_8583.properties");
        Set<String> set = map.keySet();
        for(String field : set){
            System.out.println(map.get(field).getName());
        }
    }


} 
