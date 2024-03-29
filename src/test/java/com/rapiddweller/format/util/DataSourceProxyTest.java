package com.rapiddweller.format.util;

import com.rapiddweller.format.DataIterator;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * The type Data source proxy test.
 */
public class DataSourceProxyTest {
  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    ArrayList<Object> source = new ArrayList<>();
    DataSourceProxy<Object> actualDataSourceProxy = new DataSourceProxy<Object>(new DataSourceProxy(new DataSourceProxy(
        new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<>(source, Object.class))))));
    assertTrue(actualDataSourceProxy.source instanceof DataSourceProxy);
    Class<Object> expectedType = actualDataSourceProxy.type;
    assertSame(expectedType, actualDataSourceProxy.getType());
  }

  /**
   * Test iterator.
   */
  @Test
  public void testIterator() {
    ArrayList<Object> source = new ArrayList<>();
    DataIterator<Object> actualIteratorResult = (new DataSourceProxy<Object>(new DataSourceProxy(
        new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<>(source, Object.class))))))
        .iterator();
    Class<Object> expectedType = ((DataIteratorFromJavaIterator<Object>) actualIteratorResult).type;
    assertSame(expectedType, actualIteratorResult.getType());
  }
}

