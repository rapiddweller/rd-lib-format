package com.rapiddweller.format.demo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CSVFilterDemoTest {
    @Test
    public void testRowFilterAccept() {
        assertFalse((new CSVFilterDemo.RowFilter()).accept(new String[]{"foo", "foo", "foo"}));
        assertTrue((new CSVFilterDemo.RowFilter()).accept(new String[]{"foo", "3023293310905", "foo"}));
        assertFalse((new CSVFilterDemo.RowFilter()).accept(new String[]{"2020-03-01"}));
        assertFalse((new CSVFilterDemo.RowFilter()).accept(new String[]{"foo", "foo", "foo"}));
        assertTrue((new CSVFilterDemo.RowFilter()).accept(new String[]{"foo", "3023293310905", "foo"}));
        assertFalse((new CSVFilterDemo.RowFilter()).accept(new String[]{"2020-03-01"}));
    }
}

