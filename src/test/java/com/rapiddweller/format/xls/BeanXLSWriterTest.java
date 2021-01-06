package com.rapiddweller.format.xls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.OutputStream;
import java.util.ArrayList;

import org.junit.Test;

public class BeanXLSWriterTest {
    @Test
    public void testConstructor() {
        assertEquals("BeanXLSWriter",
                (new BeanXLSWriter<Object>(OutputStream.nullOutputStream(), "Sheet Name")).toString());
    }

    @Test
    public void testToString() {
        assertEquals("BeanXLSWriter",
                (new BeanXLSWriter<Object>(OutputStream.nullOutputStream(), "Sheet Name")).toString());
    }

    @Test
    public void testConstructor2() {
        OutputStream out = OutputStream.nullOutputStream();
        ArrayList<PropFormat> propFormatList = new ArrayList<PropFormat>();
        new BeanXLSWriter<Object>(out, "Sheet Name", propFormatList);
        assertTrue(propFormatList.isEmpty());
    }
}

