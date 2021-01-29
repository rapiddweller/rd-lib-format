package com.rapiddweller.format.dot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Test;

public class DotUtilTest {
    @Test
    public void testNormalizeId() {
        assertEquals("42", DotUtil.normalizeId("42"));
        assertEquals("\"", DotUtil.normalizeId("\""));
        assertEquals("\" \"", DotUtil.normalizeId(" "));
        assertEquals("\"-\"", DotUtil.normalizeId("-"));
    }

    @Test
    public void testNormalizeColor() {
        assertEquals("Color", DotUtil.normalizeColor("Color"));
        assertEquals("\"#\"", DotUtil.normalizeColor("#"));
        assertNull(DotUtil.normalizeColor(""));
    }


    @Test
    public void testSegmentsToLabel() {
        assertNull(DotUtil.segmentsToLabel(new ArrayList<>(), true));
    }

    @Test
    public void testFormatLines() {
        assertEquals("Lines\\l", DotUtil.formatLines("Lines"));
    }

    @Test
    public void testAddLine() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        DotUtil.addLine("Line", new StringBuilder());
    }

    @Test
    public void testAddLine2() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        DotUtil.addLine("\\l", new StringBuilder(3));
    }
}

