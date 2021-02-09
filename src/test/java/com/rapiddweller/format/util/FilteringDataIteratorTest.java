package com.rapiddweller.format.util;

import com.rapiddweller.common.filter.AcceptAllFilter;
import com.rapiddweller.common.filter.OrFilter;
import com.rapiddweller.format.DataContainer;
import org.apache.commons.collections4.map.EntrySetToMapIteratorAdapter;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * The type Filtering data iterator test.
 */
public class FilteringDataIteratorTest {
  /**
   * Test next.
   */
  @Test
    public void testNext() {
        EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>());
        DataIteratorFromJavaIterator<Object> source1 = new DataIteratorFromJavaIterator<Object>(source, Object.class);
        OrFilter<Object> orFilter = new OrFilter<Object>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<Object>(null, null, null);
        FilteringDataIterator<Object> filteringDataIterator = new FilteringDataIterator<Object>(source1,
                new OrFilter<Object>(orFilter, orFilter1, new OrFilter<Object>(null, null, null)));
        assertNull(filteringDataIterator.next(new DataContainer<Object>()));
    }

  /**
   * Test next 4.
   */
  @Test
    public void testNext4() {
        HashSet<Map.Entry<Object, Object>> entrySet = new HashSet<Map.Entry<Object, Object>>();
        entrySet.add(new AbstractMap.SimpleEntry<Object, Object>("key", "value"));
        EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(entrySet);
        DataIteratorFromJavaIterator<Object> source1 = new DataIteratorFromJavaIterator<Object>(source, Object.class);
        AcceptAllFilter<Object> acceptAllFilter = new AcceptAllFilter<Object>();
        OrFilter<Object> orFilter = new OrFilter<Object>(null, null, null);
        FilteringDataIterator<Object> filteringDataIterator = new FilteringDataIterator<Object>(source1,
                new OrFilter<Object>(acceptAllFilter, orFilter, new OrFilter<Object>(null, null, null)));
        DataContainer<Object> dataContainer = new DataContainer<Object>();
        DataContainer<Object> actualNextResult = filteringDataIterator.next(dataContainer);
        assertSame(dataContainer, actualNextResult);
        assertEquals("key", actualNextResult.toString());
        assertTrue(
                ((EntrySetToMapIteratorAdapter) ((DataIteratorFromJavaIterator<Object>) filteringDataIterator.source).source)
                        .getValue() instanceof String);
    }

  /**
   * Test next 5.
   */
  @Test
    public void testNext5() {
        HashSet<Map.Entry<Object, Object>> entrySet = new HashSet<Map.Entry<Object, Object>>();
        entrySet.add(new AbstractMap.SimpleEntry<Object, Object>("key", "value"));
        EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(entrySet);
        DataIteratorFromJavaIterator<Object> source1 = new DataIteratorFromJavaIterator<Object>(source, Object.class);
        FilteringDataIterator<Object> filteringDataIterator = new FilteringDataIterator<Object>(source1,
                new OrFilter<Object>());
        DataContainer<Object> dataContainer = new DataContainer<Object>();
        assertNull(filteringDataIterator.next(dataContainer));
        assertEquals("key", dataContainer.toString());
        assertTrue(
                ((EntrySetToMapIteratorAdapter) ((DataIteratorFromJavaIterator<Object>) filteringDataIterator.source).source)
                        .getValue() instanceof String);
    }
}

