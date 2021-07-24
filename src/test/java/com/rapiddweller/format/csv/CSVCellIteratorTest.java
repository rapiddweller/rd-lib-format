package com.rapiddweller.format.csv;

import com.rapiddweller.format.DataContainer;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * The type Csv cell iterator test.
 */
public class CSVCellIteratorTest {

  /**
   * Test constructor 2.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor2() throws IOException {
    CSVCellIterator actualCsvCellIterator = new CSVCellIterator("string://", 'A', "UTF-8");
    assertEquals('A', actualCsvCellIterator.getSeparator());
    assertEquals("string://", actualCsvCellIterator.getUri());
  }


  /**
   * Test constructor 4.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor4() throws IOException {
    CSVCellIterator actualCsvCellIterator = new CSVCellIterator("string://", 'A', "UTF-8");
    assertEquals('A', actualCsvCellIterator.getSeparator());
    assertEquals("string://", actualCsvCellIterator.getUri());
  }

  /**
   * Test next.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNext() throws IOException {
    CSVCellIterator csvCellIterator = new CSVCellIterator("", 'A', "UTF-8");
    DataContainer<String> dataContainer = new DataContainer<>();
    DataContainer<String> actualNextResult = csvCellIterator.next(dataContainer);
    assertSame(dataContainer, actualNextResult);
    assertEquals("com", actualNextResult.toString());
  }

  /**
   * Test next 2.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNext2() throws IOException {
    CSVCellIterator csvCellIterator = new CSVCellIterator("", 'A', "UTF-8");
    DataContainer<String> dataContainer = new DataContainer<>("Data");
    DataContainer<String> actualNextResult = csvCellIterator.next(dataContainer);
    assertSame(dataContainer, actualNextResult);
    assertEquals("com", actualNextResult.toString());
  }

  /**
   * Test next 3.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNext3() throws IOException {
    CSVCellIterator csvCellIterator = new CSVCellIterator("", 'A', "UTF-8");
    DataContainer<String> dataContainer = new DataContainer<>();
    DataContainer<String> actualNextResult = csvCellIterator.next(dataContainer);
    assertSame(dataContainer, actualNextResult);
    assertEquals("com", actualNextResult.toString());
  }

  /**
   * Test next 4.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNext4() throws IOException {
    CSVCellIterator csvCellIterator = new CSVCellIterator("", 'A', "UTF-8");
    DataContainer<String> dataContainer = new DataContainer<>("Data");
    DataContainer<String> actualNextResult = csvCellIterator.next(dataContainer);
    assertSame(dataContainer, actualNextResult);
    assertEquals("com", actualNextResult.toString());
  }

  /**
   * Test close.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testClose() throws IOException {
    // TODO: This test is incomplete.
    //   Reason: No meaningful assertions found.
    //   To help Diffblue Cover to find assertions, please add getters to the
    //   class under test that return fields written by the method under test.
    //   See https://diff.blue/R004

    (new CSVCellIterator("", 'A', "UTF-8")).close();
  }

  /**
   * Test close 2.
   *
   * @throws IOException the io exception
   */
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

