package com.rapiddweller.format.xls;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Ignore;
import org.junit.Test;

public class XLSBeanPersistorTest {

    @Ignore
    @Test
    public void testLoad() throws IOException, InvalidFormatException {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        XLSBeanPersistor<Object> xlsBeanPersistor = new XLSBeanPersistor<>(Object.class, "foo", "foo", "foo");
        xlsBeanPersistor.load(Paths.get("target/test", "test.txt").toFile(), null);
    }
}

