package com.rapiddweller.format.util;

import com.rapiddweller.format.DataIterator;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * The type Data source from iterable test.
 */
public class DataSourceFromIterableTest {
  /**
   * Test iterator.
   */
  @Test
  public void testIterator() {
    ArrayList<Object> source = new ArrayList<Object>();
    DataIterator<Object> actualIteratorResult = (new DataSourceFromIterable<Object>(source, Object.class)).iterator();
    Class<Object> expectedType = ((DataIteratorFromJavaIterator<Object>) actualIteratorResult).type;
    assertSame(expectedType, actualIteratorResult.getType());
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
    (new DataSourceFromIterable<Object>(source, Object.class)).close();
  }

  /**
   * Test to string.
   */
  @Test
  public void testToString() {
    ArrayList<Object> source = new ArrayList<Object>();
    assertEquals("DataSourceFromIterable[[]]", (new DataSourceFromIterable<Object>(source, Object.class)).toString());
  }
}

