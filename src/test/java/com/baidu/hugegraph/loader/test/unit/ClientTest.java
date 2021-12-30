package com.baidu.hugegraph.loader.test.unit;

import com.baidu.hugegraph.driver.factory.MetaHugeClientFactory;
import org.junit.Test;

public class ClientTest {
    @Test
    public void testMetaHugeGraphFactory() {
        MetaHugeClientFactory factory =
                MetaHugeClientFactory.connect(null, new String[]{"http://localhost:2379"});

        factory.listGraphSpaces("hg").stream().forEach(System.out::println);
    }
}
