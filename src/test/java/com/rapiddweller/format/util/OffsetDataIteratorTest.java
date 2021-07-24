package com.rapiddweller.format.util;

import com.rapiddweller.common.filter.OrFilter;
import com.rapiddweller.common.iterator.FilteringIterator;
import com.rapiddweller.common.iterator.JDKIteratorWrapper;
import com.rapiddweller.common.iterator.ReverseIterator;
import org.apache.commons.collections4.map.EntrySetToMapIteratorAdapter;
import org.junit.Test;

import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.assertSame;

/**
 * The type Offset data iterator test.
 */
public class OffsetDataIteratorTest {
  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>());
    DataIteratorFromJavaIterator<Object> dataIteratorFromJavaIterator = new DataIteratorFromJavaIterator<Object>(source,
        Object.class);
    Class<Object> expectedType = dataIteratorFromJavaIterator.type;
    assertSame(expectedType, (new OffsetDataIterator<>(dataIteratorFromJavaIterator, 2)).getType());
  }

  /**
   * Test constructor 2.
   */
  @Test
  public void testConstructor2() {
    ReverseIterator<Object> realIterator = new ReverseIterator<>(new ReverseIterator<>(
        new JDKIteratorWrapper<Object>(new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>()))));
    OrFilter<Object> orFilter = new OrFilter<>(null, null, null);
    OrFilter<Object> orFilter1 = new OrFilter<>(null, null, null);
    FilteringIterator<Object> source = new FilteringIterator<>(realIterator,
        new OrFilter<>(orFilter, orFilter1, new OrFilter<>(null, null, null)));
    DataIteratorFromJavaIterator<Object> dataIteratorFromJavaIterator = new DataIteratorFromJavaIterator<>(source,
        Object.class);
    Class<Object> expectedType = dataIteratorFromJavaIterator.type;
    assertSame(expectedType, (new OffsetDataIterator<>(dataIteratorFromJavaIterator, 2)).getType());
  }

}

