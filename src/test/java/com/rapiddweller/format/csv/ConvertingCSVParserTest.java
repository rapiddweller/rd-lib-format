package com.rapiddweller.format.csv;

import com.rapiddweller.common.converter.ToArrayConverter;
import com.rapiddweller.format.DataContainer;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * The type Converting csv parser test.
 */
public class ConvertingCSVParserTest {
  /**
   * Test get type.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testGetType() throws IOException {
    assertEquals("[Ljava.lang.Object;",
        (new ConvertingCSVParser<Object>("", new ToArrayConverter())).getType().getName());
  }

  /**
   * Test next.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNext() throws IOException {
    ConvertingCSVParser<Object> convertingCSVParser = new ConvertingCSVParser<Object>("", new ToArrayConverter());
    DataContainer<Object> dataContainer = new DataContainer<Object>();
    DataContainer<Object> actualNextResult = convertingCSVParser.next(dataContainer);
    assertSame(dataContainer, actualNextResult);
    assertTrue(actualNextResult.getData() instanceof Object[]);
  }

  /**
   * Test next 2.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNext2() throws IOException {
    ConvertingCSVParser<Object> convertingCSVParser = new ConvertingCSVParser<Object>("", new ToArrayConverter());
    DataContainer<Object> dataContainer = new DataContainer<Object>("data");
    DataContainer<Object> actualNextResult = convertingCSVParser.next(dataContainer);
    assertSame(dataContainer, actualNextResult);
    assertTrue(actualNextResult.getData() instanceof Object[]);
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

    (new ConvertingCSVParser<Object>("", new ToArrayConverter())).close();
  }


  /**
   * Test parse 2.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testParse2() throws IOException {
    assertTrue(ConvertingCSVParser.parse("string://", new ToArrayConverter()).isEmpty());
  }

  /**
   * Test parse 3.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testParse3() throws IOException {
    assertEquals(2, ConvertingCSVParser.parse("file:", new ToArrayConverter()).size());
  }

  /**
   * Test parse 4.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testParse4() throws IOException {
    assertEquals(2, ConvertingCSVParser.parse("file:", new ToArrayConverter(Object.class)).size());
  }


  /**
   * Test parse 6.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testParse6() throws IOException {
    ToArrayConverter rowConverter = new ToArrayConverter();
    ArrayList<Object> objectList = new ArrayList<Object>();
    List<Object> actualParseResult = ConvertingCSVParser.parse("string://", rowConverter, objectList);
    assertSame(objectList, actualParseResult);
    assertTrue(actualParseResult.isEmpty());
  }

  /**
   * Test parse 7.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testParse7() throws IOException {
    ToArrayConverter rowConverter = new ToArrayConverter();
    ArrayList<Object> objectList = new ArrayList<Object>();
    List<Object> actualParseResult = ConvertingCSVParser.parse("file:", rowConverter, objectList);
    assertSame(objectList, actualParseResult);
    assertEquals(2, actualParseResult.size());
  }

  /**
   * Test constructor 2.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor2() throws IOException {
    assertEquals("[Ljava.lang.Object;",
        (new ConvertingCSVParser<Object>("string://", new ToArrayConverter())).getType().getName());
  }
}

