package com.rapiddweller.format.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import com.rapiddweller.format.DataContainer;

import java.io.IOException;

import org.junit.Test;

public class CSVCellIteratorTest {

    @Test
    public void testConstructor2() throws IOException {
        CSVCellIterator actualCsvCellIterator = new CSVCellIterator("string://", 'A', "UTF-8");
        assertEquals('A', actualCsvCellIterator.getSeparator());
        assertEquals("string://", actualCsvCellIterator.getUri());
    }


    @Test
    public void testConstructor4() throws IOException {
        CSVCellIterator actualCsvCellIterator = new CSVCellIterator("string://", 'A', "UTF-8");
        assertEquals('A', actualCsvCellIterator.getSeparator());
        assertEquals("string://", actualCsvCellIterator.getUri());
    }

    @Test
    public void testNext() throws IOException {
        CSVCellIterator csvCellIterator = new CSVCellIterator("", 'A', "UTF-8");
        DataContainer<String> dataContainer = new DataContainer<String>();
        DataContainer<String> actualNextResult = csvCellIterator.next(dataContainer);
        assertSame(dataContainer, actualNextResult);
        assertEquals("com", actualNextResult.toString());
    }

    @Test
    public void testNext2() throws IOException {
        CSVCellIterator csvCellIterator = new CSVCellIterator("", 'A', "UTF-8");
        DataContainer<String> dataContainer = new DataContainer<String>("Data");
        DataContainer<String> actualNextResult = csvCellIterator.next(dataContainer);
        assertSame(dataContainer, actualNextResult);
        assertEquals("com", actualNextResult.toString());
    }

    @Test
    public void testNext3() throws IOException {
        CSVCellIterator csvCellIterator = new CSVCellIterator("", 'A', "UTF-8");
        DataContainer<String> dataContainer = new DataContainer<String>();
        DataContainer<String> actualNextResult = csvCellIterator.next(dataContainer);
        assertSame(dataContainer, actualNextResult);
        assertEquals("com", actualNextResult.toString());
    }

    @Test
    public void testNext4() throws IOException {
        CSVCellIterator csvCellIterator = new CSVCellIterator("", 'A', "UTF-8");
        DataContainer<String> dataContainer = new DataContainer<String>("Data");
        DataContainer<String> actualNextResult = csvCellIterator.next(dataContainer);
        assertSame(dataContainer, actualNextResult);
        assertEquals("com", actualNextResult.toString());
    }

    @Test
    public void testClose() throws IOException {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        (new CSVCellIterator("", 'A', "UTF-8")).close();
    }

    @Test
    public void testClose2() throws IOException {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        (new CSVCellIterator("", 'A', "UTF-8")).close();
    }
}

