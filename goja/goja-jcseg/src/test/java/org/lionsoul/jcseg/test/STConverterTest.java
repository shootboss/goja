package org.lionsoul.jcseg.test;

import org.junit.Test;
import org.lionsoul.jcseg.util.STConverter;

public class STConverterTest {

    @Test
    public void test() {

        String str = "Jcseg中文分词器, java是一门很好的编程语言.";
        System.out.println("str = " + str);

        String tra = STConverter.SimToTraditional(str);
        System.out.println("Simplified to traditional: " + tra);

        String sim = STConverter.TraToSimplified(tra);
        System.out.println("Traditional to simplified: " + sim);
    }
}
