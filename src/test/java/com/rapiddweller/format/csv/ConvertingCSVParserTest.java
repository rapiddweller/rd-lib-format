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
 * Tests the {@link ConvertingCSVParser}.<br/><br/>
 * @author Volker Bergmann
 */
@SuppressWarnings("unchecked")
public class ConvertingCSVParserTest {

  @Test
  public void testGetType() throws IOException {
    assertEquals("[Ljava.lang.Object;",
        (new ConvertingCSVParser<Object>("", new ToArrayConverter())).getType().getName());
  }

  @Test
  public void testNext() throws IOException {
    ConvertingCSVParser<Object> convertingCSVParser = new ConvertingCSVParser<Object>("", new ToArrayConverter());
    DataContainer<Object> dataContainer = new DataContainer<>();
    DataContainer<Object> actualNextResult = convertingCSVParser.next(dataContainer);
    assertSame(dataContainer, actualNextResult);
    assertTrue(actualNextResult.getData() instanceof Object[]);
  }

  @Test
  public void testNext2() throws IOException {
    ConvertingCSVParser<Object> convertingCSVParser = new ConvertingCSVParser<Object>("", new ToArrayConverter());
    DataContainer<Object> dataContainer = new DataContainer<>("data");
    DataContainer<Object> actualNextResult = convertingCSVParser.next(dataContainer);
    assertSame(dataContainer, actualNextResult);
    assertTrue(actualNextResult.getData() instanceof Object[]);
  }

  @Test
  public void testParse2() throws IOException {
    assertTrue(ConvertingCSVParser.parse("string://", new ToArrayConverter()).isEmpty());
  }

  @Test
  public void testParse3() throws IOException {
    assertEquals(1, ConvertingCSVParser.parse("file:", new ToArrayConverter()).size());
  }

  @Test
  public void testParse4() throws IOException {
    assertEquals(1, ConvertingCSVParser.parse("file:", new ToArrayConverter(Object.class)).size());
  }


  @Test
  public void testParse6() throws IOException {
    ToArrayConverter rowConverter = new ToArrayConverter();
    ArrayList<Object> objectList = new ArrayList<>();
    List<Object> actualParseResult = ConvertingCSVParser.parse("string://", rowConverter, objectList);
    assertSame(objectList, actualParseResult);
    assertTrue(actualParseResult.isEmpty());
  }

  @Test
  public void testParse7() throws IOException {
    ToArrayConverter rowConverter = new ToArrayConverter();
    ArrayList<Object> objectList = new ArrayList<>();
    List<Object> actualParseResult = ConvertingCSVParser.parse("file:", rowConverter, objectList);
    assertSame(objectList, actualParseResult);
    assertEquals(1, actualParseResult.size());
  }

  @Test
  public void testConstructor2() throws IOException {
    assertEquals("[Ljava.lang.Object;",
        (new ConvertingCSVParser<Object>("string://", new ToArrayConverter())).getType().getName());
  }

}

