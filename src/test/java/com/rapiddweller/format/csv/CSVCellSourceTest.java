package com.rapiddweller.format.csv;

import com.rapiddweller.common.ConfigurationError;
import org.junit.Test;

import static org.junit.Assert.*;

public class CSVCellSourceTest {
    @Test
    public void testConstructor() {
        CSVCellSource actualCsvCellSource = new CSVCellSource();
        Class<?> expectedType = String.class;
        assertSame(expectedType, actualCsvCellSource.getType());
        assertEquals("CSVCellSource[null, ',']", actualCsvCellSource.toString());
    }

    @Test
    public void testConstructor2() {
        CSVCellSource actualCsvCellSource = new CSVCellSource("Uri", 'A');
        Class<?> expectedType = String.class;
        assertSame(expectedType, actualCsvCellSource.getType());
        assertEquals("CSVCellSource[Uri, 'A']", actualCsvCellSource.toString());
    }

    @Test
    public void testSetUri() {
        CSVCellSource csvCellSource = new CSVCellSource();
        csvCellSource.setUri("Uri");
        assertEquals("CSVCellSource[Uri, ',']", csvCellSource.toString());
    }

    @Test
    public void testIterator() {
        assertThrows(ConfigurationError.class, () -> (new CSVCellSource()).iterator());
        assertThrows(ConfigurationError.class, () -> (new CSVCellSource("Uri", 'A')).iterator());
    }

    @Test
    public void testIterator2() {
        CSVCellSource csvCellSource = new CSVCellSource();
        csvCellSource.setUri("string://");
        assertEquals(',', ((CSVCellIterator) csvCellSource.iterator()).getSeparator());
        assertEquals("string://", ((CSVCellIterator) csvCellSource.iterator()).getUri());
    }

    @Test
    public void testToString() {
        assertEquals("CSVCellSource[null, ',']", (new CSVCellSource()).toString());
    }
}

