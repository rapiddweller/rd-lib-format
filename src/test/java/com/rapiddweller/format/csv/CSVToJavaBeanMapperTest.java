package com.rapiddweller.format.csv;

import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.format.DataContainer;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;

/**
 * The type Csv to java bean mapper test.
 */
public class CSVToJavaBeanMapperTest {
  /**
   * Test next.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNext() throws IOException {
    Reader reader = Reader.nullReader();
    CSVToJavaBeanMapper<Object> csvToJavaBeanMapper = new CSVToJavaBeanMapper<Object>(reader, Object.class);
    assertNull(csvToJavaBeanMapper.next(new DataContainer<Object>()));
  }

  /**
   * Test next 2.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNext2() throws IOException {
    StringReader reader = new StringReader("S");
    CSVToJavaBeanMapper<Object> csvToJavaBeanMapper = new CSVToJavaBeanMapper<Object>(reader, Object.class);
    assertNull(csvToJavaBeanMapper.next(new DataContainer<Object>()));
  }

  /**
   * Test next 3.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNext3() throws IOException {
    StringReader reader = new StringReader("S");
    CSVToJavaBeanMapper<Object> csvToJavaBeanMapper = new CSVToJavaBeanMapper<Object>(reader, Object.class, 'A',
        "Empty Value", new String[] {"foo", "foo", "foo"});
    DataContainer<Object> dataContainer = new DataContainer<Object>();
    assertSame(dataContainer, csvToJavaBeanMapper.next(dataContainer));
  }

  /**
   * Test next 4.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNext4() throws IOException {
    CSVToJavaBeanMapper<Object> csvToJavaBeanMapper = new CSVToJavaBeanMapper<Object>(new StringReader("S"), null, 'A',
        "Empty Value", new String[] {"foo", "foo", "foo"});
    assertThrows(ConfigurationError.class, () -> csvToJavaBeanMapper.next(new DataContainer<Object>()));
  }

  /**
   * Test next 5.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNext5() throws IOException {
    StringReader reader = new StringReader("S");
    CSVToJavaBeanMapper<Object> csvToJavaBeanMapper = new CSVToJavaBeanMapper<Object>(reader, Object.class, 'A',
        "Empty Value", new String[] {null, "foo", "foo"});
    DataContainer<Object> dataContainer = new DataContainer<Object>();
    assertSame(dataContainer, csvToJavaBeanMapper.next(dataContainer));
  }

  /**
   * Test next 6.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNext6() throws IOException {
    StringReader reader = new StringReader("S");
    CSVToJavaBeanMapper<Object> csvToJavaBeanMapper = new CSVToJavaBeanMapper<Object>(reader, Object.class, 'A',
        "Empty Value", new String[] {"foo", "class", "foo"});
    assertThrows(ConfigurationError.class, () -> csvToJavaBeanMapper.next(new DataContainer<Object>()));
  }

  /**
   * Test next 7.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNext7() throws IOException {
    StringReader reader = new StringReader("S");
    assertThrows(ConfigurationError.class, () -> (new CSVToJavaBeanMapper<Object>(reader, Object.class, 'A',
        "Empty Value", new String[] {"foo", "foo", "foo"})).next(null));
  }

  /**
   * Test next 8.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNext8() throws IOException {
    StringReader reader = new StringReader("S");
    assertThrows(ArrayIndexOutOfBoundsException.class,
        () -> (new CSVToJavaBeanMapper<Object>(reader, Object.class, 'A', "Empty Value", new String[] {})).next(null));
  }

  /**
   * Test next raw.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNextRaw() throws IOException {
    Reader reader = Reader.nullReader();
    CSVToJavaBeanMapper<Object> csvToJavaBeanMapper = new CSVToJavaBeanMapper<Object>(reader, Object.class);
    assertNull(csvToJavaBeanMapper.nextRaw(new DataContainer<String[]>()));
  }

  /**
   * Test next raw 2.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testNextRaw2() throws IOException {
    StringReader reader = new StringReader("S");
    CSVToJavaBeanMapper<Object> csvToJavaBeanMapper = new CSVToJavaBeanMapper<Object>(reader, Object.class);
    assertNull(csvToJavaBeanMapper.nextRaw(new DataContainer<String[]>()));
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

    Reader reader = Reader.nullReader();
    (new CSVToJavaBeanMapper<Object>(reader, Object.class)).close();
  }

  /**
   * Test skip.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testSkip() throws IOException {
    // TODO: This test is incomplete.
    //   Reason: No meaningful assertions found.
    //   To help Diffblue Cover to find assertions, please add getters to the
    //   class under test that return fields written by the method under test.
    //   See https://diff.blue/R004

    StringReader reader = new StringReader("S");
    (new CSVToJavaBeanMapper<Object>(reader, Object.class)).skip();
  }

  /**
   * Test constructor.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor() throws IOException {
    Reader reader = Reader.nullReader();
    assertNull((new CSVToJavaBeanMapper<Object>(reader, Object.class)).getType());
  }

  /**
   * Test constructor 10.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor10() throws IOException {
    Reader reader = Reader.nullReader();
    CSVToJavaBeanMapper<Object> actualCsvToJavaBeanMapper = new CSVToJavaBeanMapper<Object>(reader, Object.class, 'A',
        "Empty Value", new String[] {"class", "foo", "foo"});
    Class<?> expectedType = Object.class;
    assertSame(expectedType, actualCsvToJavaBeanMapper.getType());
  }

  /**
   * Test constructor 11.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor11() throws IOException {
    Reader reader = Reader.nullReader();
    CSVToJavaBeanMapper<Object> actualCsvToJavaBeanMapper = new CSVToJavaBeanMapper<Object>(reader, Object.class, 'A',
        "Empty Value", new String[] {null, "foo", "foo"});
    Class<?> expectedType = Object.class;
    assertSame(expectedType, actualCsvToJavaBeanMapper.getType());
  }

  /**
   * Test constructor 2.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor2() throws IOException {
    StringReader reader = new StringReader("S");
    CSVToJavaBeanMapper<Object> actualCsvToJavaBeanMapper = new CSVToJavaBeanMapper<Object>(reader, Object.class);
    Class<?> expectedType = Object.class;
    assertSame(expectedType, actualCsvToJavaBeanMapper.getType());
  }

  /**
   * Test constructor 3.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor3() throws IOException {
    StringReader reader = new StringReader("class");
    CSVToJavaBeanMapper<Object> actualCsvToJavaBeanMapper = new CSVToJavaBeanMapper<Object>(reader, Object.class);
    Class<?> expectedType = Object.class;
    assertSame(expectedType, actualCsvToJavaBeanMapper.getType());
  }

  /**
   * Test constructor 4.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor4() throws IOException {
    Reader reader = Reader.nullReader();
    assertNull((new CSVToJavaBeanMapper<Object>(reader, Object.class, 'A', "Empty Value")).getType());
  }

  /**
   * Test constructor 5.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor5() throws IOException {
    StringReader reader = new StringReader("S");
    CSVToJavaBeanMapper<Object> actualCsvToJavaBeanMapper = new CSVToJavaBeanMapper<Object>(reader, Object.class, 'A',
        "Empty Value");
    Class<?> expectedType = Object.class;
    assertSame(expectedType, actualCsvToJavaBeanMapper.getType());
  }

  /**
   * Test constructor 6.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor6() throws IOException {
    StringReader reader = new StringReader("class");
    CSVToJavaBeanMapper<Object> actualCsvToJavaBeanMapper = new CSVToJavaBeanMapper<Object>(reader, Object.class, 'A',
        "Empty Value");
    Class<?> expectedType = Object.class;
    assertSame(expectedType, actualCsvToJavaBeanMapper.getType());
  }

  /**
   * Test constructor 7.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor7() throws IOException {
    StringReader reader = new StringReader("S");
    CSVToJavaBeanMapper<Object> actualCsvToJavaBeanMapper = new CSVToJavaBeanMapper<Object>(reader, Object.class, 'S',
        "Empty Value");
    Class<?> expectedType = Object.class;
    assertSame(expectedType, actualCsvToJavaBeanMapper.getType());
  }

  /**
   * Test constructor 8.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor8() throws IOException {
    Reader reader = Reader.nullReader();
    CSVToJavaBeanMapper<Object> actualCsvToJavaBeanMapper = new CSVToJavaBeanMapper<Object>(reader, Object.class, 'A',
        "Empty Value", new String[] {"foo", "foo", "foo"});
    Class<?> expectedType = Object.class;
    assertSame(expectedType, actualCsvToJavaBeanMapper.getType());
  }

  /**
   * Test constructor 9.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testConstructor9() throws IOException {
    StringReader reader = new StringReader("S");
    CSVToJavaBeanMapper<Object> actualCsvToJavaBeanMapper = new CSVToJavaBeanMapper<Object>(reader, Object.class, 'A',
        "Empty Value", new String[] {"foo", "foo", "foo"});
    Class<?> expectedType = Object.class;
    assertSame(expectedType, actualCsvToJavaBeanMapper.getType());
  }
}

