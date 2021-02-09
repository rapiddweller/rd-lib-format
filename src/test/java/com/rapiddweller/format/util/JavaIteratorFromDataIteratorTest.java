package com.rapiddweller.format.util;

import org.apache.commons.collections4.map.EntrySetToMapIteratorAdapter;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * The type Java iterator from data iterator test.
 */
public class JavaIteratorFromDataIteratorTest {
  /**
   * Test has next.
   */
  @Test
  public void testHasNext() {
    EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>());
    assertFalse(
        (new JavaIteratorFromDataIterator<Object>(new DataIteratorFromJavaIterator<Object>(source, Object.class)))
            .hasNext());
  }

  /**
   * Test has next 3.
   */
  @Test
  public void testHasNext3() {
    HashSet<Map.Entry<Object, Object>> entrySet = new HashSet<Map.Entry<Object, Object>>();
    entrySet.add(new AbstractMap.SimpleEntry<Object, Object>("key", "value"));
    EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(entrySet);
    assertTrue(
        (new JavaIteratorFromDataIterator<Object>(new DataIteratorFromJavaIterator<Object>(source, Object.class)))
            .hasNext());
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

    EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>());
    (new JavaIteratorFromDataIterator<Object>(new DataIteratorFromJavaIterator<Object>(source, Object.class))).close();
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

    EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>());
    (new JavaIteratorFromDataIterator<Object>(new DataIteratorFromJavaIterator<Object>(source, Object.class)))
        .toString();
  }

  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>());
    DataIteratorFromJavaIterator<Object> dataIteratorFromJavaIterator = new DataIteratorFromJavaIterator<Object>(source,
        Object.class);
    new JavaIteratorFromDataIterator<Object>(dataIteratorFromJavaIterator);
    Class<Object> expectedType = dataIteratorFromJavaIterator.type;
    Class<Object> type = dataIteratorFromJavaIterator.getType();
    assertSame(expectedType, type);
    assertSame(Object.class, type);
    assertTrue(dataIteratorFromJavaIterator.source instanceof EntrySetToMapIteratorAdapter);
  }
}

