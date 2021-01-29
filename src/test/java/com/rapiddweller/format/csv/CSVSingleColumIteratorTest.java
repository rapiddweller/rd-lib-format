package com.rapiddweller.format.csv;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

import java.io.IOException;

import org.junit.Test;

public class CSVSingleColumIteratorTest {

    @Test
    public void testConstructor2() throws IOException {
        CSVSingleColumIterator actualCsvSingleColumIterator = new CSVSingleColumIterator("string://", 1);
        Class<?> expectedType = String.class;
        assertSame(expectedType, actualCsvSingleColumIterator.getType());
    }

    @Test
    public void testConstructor3() throws IOException {
        assertThrows(IllegalArgumentException.class, () -> new CSVSingleColumIterator("", 1));
    }

    @Test
    public void testConstructor4() throws IOException {
        assertThrows(IllegalArgumentException.class, () -> new CSVSingleColumIterator("string://", -1));
    }


    @Test
    public void testConstructor6() throws IOException {
        CSVSingleColumIterator actualCsvSingleColumIterator = new CSVSingleColumIterator("string://", 1, 'A', true,
                "UTF-8");
        Class<?> expectedType = String.class;
        assertSame(expectedType, actualCsvSingleColumIterator.getType());
    }

    @Test
    public void testConstructor7() throws IOException {
        assertThrows(IllegalArgumentException.class, () -> new CSVSingleColumIterator("", 1, 'A', true, "UTF-8"));
    }

    @Test
    public void testConstructor8() throws IOException {
        assertThrows(IllegalArgumentException.class, () -> new CSVSingleColumIterator("string://", -1, 'A', true, "UTF-8"));
    }
}

