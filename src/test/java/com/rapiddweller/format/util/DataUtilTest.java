package com.rapiddweller.format.util;

import org.apache.commons.collections4.map.EntrySetToMapIteratorAdapter;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * The type Data util test.
 */
public class DataUtilTest {
  /**
   * Test next not null data.
   */
  @Test
    public void testNextNotNullData() {
        EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>());
        assertThrows(IllegalArgumentException.class,
                () -> DataUtil.nextNotNullData(new DataIteratorFromJavaIterator<Object>(source, Object.class)));
    }

  /**
   * Test next not null data 3.
   */
  @Test
    public void testNextNotNullData3() {
        HashSet<Map.Entry<Object, Object>> entrySet = new HashSet<Map.Entry<Object, Object>>();
        entrySet.add(new AbstractMap.SimpleEntry<Object, Object>("key", "value"));
        EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(entrySet);
        DataIteratorFromJavaIterator<Object> dataIteratorFromJavaIterator = new DataIteratorFromJavaIterator<Object>(source,
                Object.class);
        assertEquals("key", DataUtil.nextNotNullData(dataIteratorFromJavaIterator));
        assertTrue(((EntrySetToMapIteratorAdapter) dataIteratorFromJavaIterator.source).getValue() instanceof String);
    }
}

