package com.ysl;

import com.ysl.adapter.PosDecoderAdapter;
import com.ysl.adapter.PosFullContent;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PosDecoderAdapterTest {
    @Test
    public void testParse() {
        PosFullContent posFullContent = PosDecoderAdapter.getPosDecoderAdapter().parse("00eb600688000060400018042402003020048000c082320000000000000011111234569300034a3030303032333034353434323430303734323130303931353600559F101307010103A00000040A01000000000095DE79D39F2701809F2608B1AAD1CB0083B5029F370486F5FD329F1E0838343731343632320097313131313131313131313131313131313131313131313131313131313131313131313131313131313131313131313131313131313131313131313131313131313131313131333233333431333332323231393138353031353237333830323530300013200000040006000006313131313230", PosFullContent.class);
        System.out.println(posFullContent.getAmt());
        System.out.println(posFullContent.getPboc().toString());
    }

    @Test
    public void testPattern(){
        String s = "{tag=[9f10],length=[19],value=[07010103a00000040a01000000000095de79d3]}{tag=[9f27],length=[1],value=[80]}{tag=[9f26],length=[8],value=[b1aad1cb0083b502]}{tag=[9f37],length=[4],value=[86f5fd32]}{tag=[9f1e],length=[8],value=[3834373134363232]}";

        Pattern pattern = Pattern.compile("tag=\\[\\w+\\]");
        Pattern pattern1 = Pattern.compile("value=\\[\\w+\\]");
        Matcher matcher = pattern.matcher(s);
        Matcher matcher1 = pattern1.matcher(s);
        System.out.println(matcher.groupCount());

        Map<String, String> map = new LinkedHashMap<>();
        while (matcher.find()) {
            map.put(matcher.group().replace("tag=[", "").replace("]", ""), null);
        }
        Iterator<String> iterator = map.keySet().iterator();
        while (matcher1.find()) {
            map.put(iterator.next(), matcher1.group().replace("value=[", "").replace("]", ""));
        }
        System.out.println(map.toString());
    }
}
