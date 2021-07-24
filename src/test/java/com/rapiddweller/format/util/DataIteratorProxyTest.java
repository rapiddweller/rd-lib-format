package com.rapiddweller.format.util;

import com.rapiddweller.common.filter.OrFilter;
import com.rapiddweller.common.iterator.FilteringIterator;
import com.rapiddweller.common.iterator.JDKIteratorWrapper;
import com.rapiddweller.common.iterator.ReverseIterator;
import com.rapiddweller.format.DataContainer;
import org.apache.commons.collections4.map.EntrySetToMapIteratorAdapter;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * The type Data iterator proxy test.
 */
public class DataIteratorProxyTest {
  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>());
    DataIteratorFromJavaIterator<Object> dataIteratorFromJavaIterator = new DataIteratorFromJavaIterator<Object>(source,
        Object.class);
    Class<Object> expectedType = dataIteratorFromJavaIterator.type;
    assertSame(expectedType, (new DataIteratorProxy<>(dataIteratorFromJavaIterator)).getType());
  }

  /**
   * Test get type.
   */
  @Test
  public void testGetType() {
    EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>());
    Class<Object> actualType = (new DataIteratorProxy<>(
        new DataIteratorFromJavaIterator<Object>(source, Object.class))).getType();
    assertSame(Object.class, actualType);
  }

  /**
   * Test get type 2.
   */
  @Test
  public void testGetType2() {
    EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>());
    Class<Object> actualType = (new DataIteratorProxy<Object>(
        new OffsetDataIterator(new DataIteratorFromJavaIterator<Object>(source, Object.class), 2))).getType();
    assertSame(Object.class, actualType);
  }

  /**
   * Test next.
   */
  @Test
  public void testNext() {
    EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>());
    DataIteratorProxy<Object> dataIteratorProxy = new DataIteratorProxy<>(
        new DataIteratorFromJavaIterator<Object>(source, Object.class));
    assertNull(dataIteratorProxy.next(new DataContainer<>()));
  }

  /**
   * Test next 2.
   */
  @Test
  public void testNext2() {
    ReverseIterator<Object> realIterator = new ReverseIterator<>(new ReverseIterator<>(
        new JDKIteratorWrapper<Object>(new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>()))));
    OrFilter<Object> orFilter = new OrFilter<>(null, null, null);
    OrFilter<Object> orFilter1 = new OrFilter<>(null, null, null);
    FilteringIterator<Object> source = new FilteringIterator<>(realIterator,
        new OrFilter<>(orFilter, orFilter1, new OrFilter<>(null, null, null)));
    DataIteratorProxy<Object> dataIteratorProxy = new DataIteratorProxy<>(
        new DataIteratorFromJavaIterator<>(source, Object.class));
    assertNull(dataIteratorProxy.next(new DataContainer<>()));
  }


  /**
   * Test next 4.
   */
  @Test
  public void testNext4() {
    EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>());
    DataIteratorProxy<Object> dataIteratorProxy = new DataIteratorProxy<Object>(
        new OffsetDataIterator(new DataIteratorFromJavaIterator<Object>(source, Object.class), 2));
    assertNull(dataIteratorProxy.next(new DataContainer<>()));
  }
}

