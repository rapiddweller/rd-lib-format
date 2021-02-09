package com.rapiddweller.format.csv;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

/**
 * The type Csv single colum iterator test.
 */
public class CSVSingleColumIteratorTest {

  /**
   * Test constructor 2.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor2() throws IOException {
    CSVSingleColumIterator actualCsvSingleColumIterator = new CSVSingleColumIterator("string://", 1);
    Class<?> expectedType = String.class;
    assertSame(expectedType, actualCsvSingleColumIterator.getType());
  }

  /**
   * Test constructor 3.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor3() throws IOException {
    assertThrows(IllegalArgumentException.class, () -> new CSVSingleColumIterator("", 1));
  }

  /**
   * Test constructor 4.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor4() throws IOException {
    assertThrows(IllegalArgumentException.class, () -> new CSVSingleColumIterator("string://", -1));
  }


  /**
   * Test constructor 6.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor6() throws IOException {
    CSVSingleColumIterator actualCsvSingleColumIterator = new CSVSingleColumIterator("string://", 1, 'A', true,
        "UTF-8");
    Class<?> expectedType = String.class;
    assertSame(expectedType, actualCsvSingleColumIterator.getType());
  }

  /**
   * Test constructor 7.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor7() throws IOException {
    assertThrows(IllegalArgumentException.class, () -> new CSVSingleColumIterator("", 1, 'A', true, "UTF-8"));
  }

  /**
   * Test constructor 8.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor8() throws IOException {
    assertThrows(IllegalArgumentException.class, () -> new CSVSingleColumIterator("string://", -1, 'A', true, "UTF-8"));
  }
}

