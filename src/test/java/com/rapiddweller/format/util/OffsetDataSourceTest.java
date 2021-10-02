package com.rapiddweller.format.util;

import com.rapiddweller.common.CollectionUtil;
import com.rapiddweller.format.DataIterator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link OffsetDataSource}.
 * @author Volker Bergmann
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class OffsetDataSourceTest {

  @Test
  public void testConstructor() {
    ArrayList<Object> source = new ArrayList<>();
    OffsetDataSource<Object> actualOffsetDataSource = new OffsetDataSource<Object>(
        new DataSourceProxy(new DataSourceProxy(
            new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<>(source, Object.class))))),
        2);
    assertEquals(2, actualOffsetDataSource.offset);
    assertTrue(actualOffsetDataSource.source instanceof DataSourceProxy);
    Class<Object> expectedType = actualOffsetDataSource.type;
    assertSame(expectedType, actualOffsetDataSource.getType());
  }

  @Test
  public void testIterator() {
    ArrayList<Object> source = new ArrayList<>();
    DataIterator<Object> actualIteratorResult = (new OffsetDataSource<Object>(new DataSourceProxy(
        new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<>(source, Object.class)))), 2))
        .iterator();
    Class<Object> expectedType = ((DataIteratorFromJavaIterator<Object>) actualIteratorResult).type;
    assertSame(expectedType, actualIteratorResult.getType());
  }

  @Test
  public void testIterator2() {
    ArrayList<Object> source = new ArrayList<>();
    DataIterator<Object> actualIteratorResult = (new OffsetDataSource<Object>(
        new DataSourceProxy(new DataSourceProxy(new DataSourceProxy(new OffsetDataSource(
            new DataSourceProxy(
                new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<>(source, Object.class)))),
            2)))),
        2)).iterator();
    Class<Object> expectedType = ((DataIteratorFromJavaIterator<Object>) actualIteratorResult).type;
    assertSame(expectedType, actualIteratorResult.getType());
  }

  @Test
  public void testToString() {
    List<Integer> source = CollectionUtil.toList(1, 2, 3, 4);
    DataSourceProxy s1 = new DataSourceProxy(new DataSourceFromIterable<>(source, Integer.class));
    DataSourceProxy s2 = new DataSourceProxy(s1);
    OffsetDataSource<Object> s4 = new OffsetDataSource<Object>(s2, 2);
    assertEquals("OffsetDataSource[2, DataSourceProxy(DataSourceProxy(DataSourceFromIterable[[1, 2, 3, 4]]))]",
        s4.toString());
  }

  @Test
  public void testToString2() {
    ArrayList<Object> source = new ArrayList<>();
    DataSourceFromIterable<Object> s0 = new DataSourceFromIterable<>(source, Object.class);
    DataSourceProxy s1 = new DataSourceProxy(s0);
    DataSourceProxy s2 = new DataSourceProxy(s1);
    OffsetDataSource s3 = new OffsetDataSource(s2, 2);
    OffsetDataSource<Object> s4 = new OffsetDataSource<Object>(s3, 2);
    assertEquals(
        "OffsetDataSource[2, OffsetDataSource[2, DataSourceProxy(DataSourceProxy(DataSourceFromIterable[[]]))]]",
        s4.toString());
  }

}

