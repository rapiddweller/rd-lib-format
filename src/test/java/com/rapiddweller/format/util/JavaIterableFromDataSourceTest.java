package com.rapiddweller.format.util;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * The type Java iterable from data source test.
 */
public class JavaIterableFromDataSourceTest {
  /**
   * Test iterator.
   */
  @Test
  public void testIterator() {
    ArrayList<Object> source = new ArrayList<Object>();
    assertTrue((new JavaIterableFromDataSource<Object>(new DataSourceProxy(
        new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<Object>(source, Object.class))))))
        .iterator() instanceof JavaIteratorFromDataIterator);
  }

  /**
   * Test iterator 2.
   */
  @Test
  public void testIterator2() {
    ArrayList<Object> objectList = new ArrayList<Object>();
    objectList.add("e");
    assertTrue((new JavaIterableFromDataSource<Object>(new DataSourceProxy(
        new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<Object>(objectList, Object.class))))))
        .iterator() instanceof JavaIteratorFromDataIterator);
  }
}

