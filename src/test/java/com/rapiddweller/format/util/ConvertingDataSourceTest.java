package com.rapiddweller.format.util;

import com.rapiddweller.common.converter.ToCollectionConverter;
import com.rapiddweller.format.DataIterator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * The type Converting data source test.
 */
public class ConvertingDataSourceTest {
  /**
   * Test get type.
   */
  @Test
  public void testGetType() {
    ArrayList<Object> source = new ArrayList<Object>();
    DataSourceProxy source1 = new DataSourceProxy(
        new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<Object>(source, Object.class))));
    Class<Object> actualType = (new ConvertingDataSource<Object, Object>(source1, new ToCollectionConverter()))
        .getType();
    assertSame(List.class, actualType);
  }

  /**
   * Test iterator.
   */
  @Test
  public void testIterator() {
    ArrayList<Object> source = new ArrayList<Object>();
    DataSourceProxy dataSourceProxy = new DataSourceProxy(
        new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<Object>(source, Object.class))));
    ConvertingDataSource<Object, Object> convertingDataSource = new ConvertingDataSource<Object, Object>(
        dataSourceProxy, new ToCollectionConverter());
    DataIterator<Object> actualIteratorResult = convertingDataSource.iterator();
    Class<Object> expectedType = convertingDataSource.type;
    assertSame(expectedType, actualIteratorResult.getType());
    DataIterator<Object> dataIterator = ((ConvertingDataIterator) actualIteratorResult).source;
    assertTrue(dataIterator instanceof DataIteratorFromJavaIterator);
    Class<Object> expectedType1 = dataSourceProxy.type;
    assertSame(expectedType1, dataIterator.getType());
  }

  /**
   * Test iterator 2.
   */
  @Test
  public void testIterator2() {
    ArrayList<Object> source = new ArrayList<Object>();
    DataSourceProxy source1 = new DataSourceProxy(
        new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<Object>(source, Object.class))));
    ConvertingDataSource convertingDataSource = new ConvertingDataSource(source1, new ToCollectionConverter());
    DataSourceProxy source2 = new DataSourceProxy(new DataSourceProxy(new DataSourceProxy(convertingDataSource)));
    ConvertingDataSource<Object, Object> convertingDataSource1 = new ConvertingDataSource<Object, Object>(source2,
        new ToCollectionConverter());
    DataIterator<Object> actualIteratorResult = convertingDataSource1.iterator();
    Class<Object> expectedType = convertingDataSource1.type;
    assertSame(expectedType, actualIteratorResult.getType());
    DataIterator<Object> dataIterator = ((ConvertingDataIterator) actualIteratorResult).source;
    assertTrue(dataIterator instanceof ConvertingDataIterator);
    DataIterator<Object> dataIterator1 = ((ConvertingDataIterator) dataIterator).source;
    assertTrue(dataIterator1 instanceof DataIteratorFromJavaIterator);
    assertSame(convertingDataSource.converter, ((ConvertingDataIterator) dataIterator).converter);
    Class<Object> expectedType1 = ((ConvertingDataIterator) actualIteratorResult).converter.getSourceType();
    assertSame(expectedType1, dataIterator1.getType());
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

    ArrayList<Object> source = new ArrayList<Object>();
    DataSourceProxy source1 = new DataSourceProxy(
        new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<Object>(source, Object.class))));
    (new ConvertingDataSource<Object, Object>(source1, new ToCollectionConverter())).close();
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

    ArrayList<Object> source = new ArrayList<Object>();
    DataSourceProxy source1 = new DataSourceProxy(
        new DataSourceProxy(new DataSourceFromIterable<Object>(source, Object.class)));
    (new ConvertingDataSource<Object, Object>(source1, new ToCollectionConverter())).close();
  }

  /**
   * Test close 3.
   */
  @Test
  public void testClose3() {
    // TODO: This test is incomplete.
    //   Reason: No meaningful assertions found.
    //   To help Diffblue Cover to find assertions, please add getters to the
    //   class under test that return fields written by the method under test.
    //   See https://diff.blue/R004

    ArrayList<Object> source = new ArrayList<Object>();
    DataSourceProxy source1 = new DataSourceProxy(
        new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<Object>(source, Object.class))));
    DataSourceProxy source2 = new DataSourceProxy(
        new DataSourceProxy(new DataSourceProxy(new ConvertingDataSource(source1, new ToCollectionConverter()))));
    (new ConvertingDataSource<Object, Object>(source2, new ToCollectionConverter())).close();
  }

  /**
   * Test to string.
   */
  @Test
  public void testToString() {
    // TODO: This test is incomplete.
    //   Reason: No meaningful assertions found.
    //   To help Diffblue Cover to find assertions, please add getters to the
    //   class under test that return fields written by the method under test.
    //   See https://diff.blue/R004

    ArrayList<Object> source = new ArrayList<Object>();
    DataSourceProxy source1 = new DataSourceProxy(
        new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<Object>(source, Object.class))));
    (new ConvertingDataSource<Object, Object>(source1, new ToCollectionConverter())).toString();
  }

  /**
   * Test to string 2.
   */
  @Test
  public void testToString2() {
    // TODO: This test is incomplete.
    //   Reason: No meaningful assertions found.
    //   To help Diffblue Cover to find assertions, please add getters to the
    //   class under test that return fields written by the method under test.
    //   See https://diff.blue/R004

    ArrayList<Object> source = new ArrayList<Object>();
    DataSourceProxy source1 = new DataSourceProxy(
        new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<Object>(source, Object.class))));
    ConvertingDataSource source2 = new ConvertingDataSource(source1, new ToCollectionConverter());
    (new ConvertingDataSource<Object, Object>(source2, new ToCollectionConverter())).toString();
  }

  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    ArrayList<Object> source = new ArrayList<Object>();
    DataSourceProxy source1 = new DataSourceProxy(new DataSourceProxy(
        new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<Object>(source, Object.class)))));
    ConvertingDataSource<Object, Object> actualConvertingDataSource = new ConvertingDataSource<Object, Object>(source1,
        new ToCollectionConverter());
    Class<Object> expectedType = actualConvertingDataSource.type;
    assertSame(expectedType, actualConvertingDataSource.getType());
    assertTrue(actualConvertingDataSource.source instanceof DataSourceProxy);
  }
}

