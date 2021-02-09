package com.rapiddweller.format.csv;

import com.rapiddweller.format.DataIterator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * The type Csv source test.
 */
public class CSVSourceTest {

  /**
   * Test iterator 2.
   */
  @Test
  public void testIterator2() {
    DataIterator<String[]> actualIteratorResult = (new CSVSource("string://", 'A', "UTF-8", true, true)).iterator();
    assertEquals("CSVLineIterator[string://]", actualIteratorResult.toString());
    assertEquals(0, ((CSVLineIterator) actualIteratorResult).lineCount());
  }

  /**
   * Test iterator 3.
   */
  @Test
  public void testIterator3() {
    assertThrows(RuntimeException.class, () -> (new CSVSource("://", 'A', "UTF-8", true, true)).iterator());
  }

  /**
   * Test iterator 4.
   */
  @Test
  public void testIterator4() {
    assertEquals("[Ljava.lang.String;",
        (new CSVSource("string://", 'A', "UTF-8", true, false)).iterator().getType().getName());
  }


  /**
   * Test iterator 6.
   */
  @Test
  public void testIterator6() {
    DataIterator<String[]> actualIteratorResult = (new CSVSource("string://", 'A', "UTF-8", true, true)).iterator();
    assertEquals("CSVLineIterator[string://]", actualIteratorResult.toString());
    assertEquals(0, ((CSVLineIterator) actualIteratorResult).lineCount());
  }

  /**
   * Test iterator 7.
   */
  @Test
  public void testIterator7() {
    assertThrows(RuntimeException.class, () -> (new CSVSource("://", 'A', "UTF-8", true, true)).iterator());
  }

  /**
   * Test iterator 8.
   */
  @Test
  public void testIterator8() {
    assertEquals("[Ljava.lang.String;",
        (new CSVSource("string://", 'A', "UTF-8", true, false)).iterator().getType().getName());
  }

  /**
   * Test close.
   */
  @Test
  public void testClose() {
    // TODO: This test is incomplete.
    //   Reason: No meaningful assertions found.
    //   To help Diffblue Cover to find assertions, please add getters to the
    //   class under test that return fields written by the method under test.
    //   See https://diff.blue/R004

    (new CSVSource("Uri", 'A', "UTF-8", true, true)).close();
  }

  /**
   * Test close 2.
   */
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

