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

/**
 * The type Data iterator from java iterator test.
 */
public class DataIteratorFromJavaIteratorTest {
  /**
   * Test next.
   */
  @Test
    public void testNext() {
        EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>());
        DataIteratorFromJavaIterator<Object> dataIteratorFromJavaIterator = new DataIteratorFromJavaIterator<Object>(source,
                Object.class);
        assertNull(dataIteratorFromJavaIterator.next(new DataContainer<Object>()));
    }

  /**
   * Test next 2.
   */
  @Test
    public void testNext2() {
        ReverseIterator<Object> realIterator = new ReverseIterator<Object>(new ReverseIterator<Object>(
                new JDKIteratorWrapper<Object>(new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>()))));
        OrFilter<Object> orFilter = new OrFilter<Object>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<Object>(null, null, null);
        FilteringIterator<Object> source = new FilteringIterator<Object>(realIterator,
                new OrFilter<Object>(orFilter, orFilter1, new OrFilter<Object>(null, null, null)));
        DataIteratorFromJavaIterator<Object> dataIteratorFromJavaIterator = new DataIteratorFromJavaIterator<Object>(source,
                Object.class);
        assertNull(dataIteratorFromJavaIterator.next(new DataContainer<Object>()));
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
        (new DataIteratorFromJavaIterator<Object>(source, Object.class)).close();
    }
}

