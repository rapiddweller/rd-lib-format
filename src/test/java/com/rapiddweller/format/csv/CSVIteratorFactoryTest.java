package com.rapiddweller.format.csv;

import com.rapiddweller.format.DataIterator;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The type Csv iterator factory test.
 */
public class CSVIteratorFactoryTest {


  /**
   * Test create csv cell iterator 2.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testCreateCSVCellIterator2() throws IOException {
    assertEquals('A',
        ((CSVCellIterator) CSVIteratorFactory.createCSVCellIterator("string://", 'A', "UTF-8")).getSeparator());
    assertEquals("string://",
        ((CSVCellIterator) CSVIteratorFactory.createCSVCellIterator("string://", 'A', "UTF-8")).getUri());
  }


  /**
   * Test create csv vell iterator for column 2.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testCreateCSVVellIteratorForColumn2() throws IOException {
    assertTrue(CSVIteratorFactory.createCSVVellIteratorForColumn("string://", 1, 'A', true,
        "UTF-8") instanceof CSVSingleColumIterator);
  }


  /**
   * Test create csv line iterator 2.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testCreateCSVLineIterator2() throws IOException {
    DataIterator<String[]> actualCreateCSVLineIteratorResult = CSVIteratorFactory.createCSVLineIterator("string://",
        'A', true, "UTF-8");
    assertEquals("CSVLineIterator[string://]", actualCreateCSVLineIteratorResult.toString());
    assertEquals(0, ((CSVLineIterator) actualCreateCSVLineIteratorResult).lineCount());
  }
}

