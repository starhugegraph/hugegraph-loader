package com.baidu.hugegraph.loader.test.unit;

import com.baidu.hugegraph.driver.factory.PDHugeClientFactory;
import org.junit.Test;

public class ClientTest {
    @Test
    public void testMetaHugeGraphFactory() {
        PDHugeClientFactory factory =
                new PDHugeClientFactory("");

        factory.getAutoURLs("hg", null, null).stream().forEach(System.out::println);
    }
}
