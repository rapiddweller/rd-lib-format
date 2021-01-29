package com.rapiddweller.format.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import com.rapiddweller.format.DataIterator;
import org.junit.Test;

public class CSVSourceTest {

    @Test
    public void testIterator2() {
        DataIterator<String[]> actualIteratorResult = (new CSVSource("string://", 'A', "UTF-8", true, true)).iterator();
        assertEquals("CSVLineIterator[string://]", actualIteratorResult.toString());
        assertEquals(0, ((CSVLineIterator) actualIteratorResult).lineCount());
    }

    @Test
    public void testIterator3() {
        assertThrows(RuntimeException.class, () -> (new CSVSource("://", 'A', "UTF-8", true, true)).iterator());
    }

    @Test
    public void testIterator4() {
        assertEquals("[Ljava.lang.String;",
                (new CSVSource("string://", 'A', "UTF-8", true, false)).iterator().getType().getName());
    }


    @Test
    public void testIterator6() {
        DataIterator<String[]> actualIteratorResult = (new CSVSource("string://", 'A', "UTF-8", true, true)).iterator();
        assertEquals("CSVLineIterator[string://]", actualIteratorResult.toString());
        assertEquals(0, ((CSVLineIterator) actualIteratorResult).lineCount());
    }

    @Test
    public void testIterator7() {
        assertThrows(RuntimeException.class, () -> (new CSVSource("://", 'A', "UTF-8", true, true)).iterator());
    }

    @Test
    public void testIterator8() {
        assertEquals("[Ljava.lang.String;",
                (new CSVSource("string://", 'A', "UTF-8", true, false)).iterator().getType().getName());
    }

    @Test
    public void testClose() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        (new CSVSource("Uri", 'A', "UTF-8", true, true)).close();
    }

    @Test
    public void testClose2() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        (new CSVSource("Uri", 'A', "UTF-8", true, true)).close();
    }
}

