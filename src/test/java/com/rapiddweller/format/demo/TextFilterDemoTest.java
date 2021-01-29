package com.rapiddweller.format.demo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TextFilterDemoTest {
    @Test
    public void testLineFilterAccept() {
        assertFalse((new TextFilterDemo.LineFilter()).accept("2020-03-01"));
        assertTrue((new TextFilterDemo.LineFilter()).accept("|3023293310905|"));
        assertFalse((new TextFilterDemo.LineFilter()).accept("2020-03-01"));
        assertTrue((new TextFilterDemo.LineFilter()).accept("|3023293310905|"));
    }
}

