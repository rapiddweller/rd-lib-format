package com.rapiddweller.format.util;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import com.rapiddweller.common.filter.OrFilter;
import com.rapiddweller.common.iterator.CyclicIterator;
import com.rapiddweller.common.iterator.FilteringIterator;
import com.rapiddweller.common.iterator.JDKIteratorWrapper;
import com.rapiddweller.common.iterator.ReverseIterator;
import com.rapiddweller.format.DataContainer;

import java.util.HashSet;
import java.util.Map;

import org.apache.commons.collections4.map.EntrySetToMapIteratorAdapter;
import org.junit.Test;

public class DataIteratorProxyTest {
    @Test
    public void testConstructor() {
        EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>());
        DataIteratorFromJavaIterator<Object> dataIteratorFromJavaIterator = new DataIteratorFromJavaIterator<Object>(source,
                Object.class);
        Class<Object> expectedType = dataIteratorFromJavaIterator.type;
        assertSame(expectedType, (new DataIteratorProxy<Object>(dataIteratorFromJavaIterator)).getType());
    }

    @Test
    public void testGetType() {
        EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>());
        Class<Object> actualType = (new DataIteratorProxy<Object>(
                new DataIteratorFromJavaIterator<Object>(source, Object.class))).getType();
        assertSame(Object.class, actualType);
    }

    @Test
    public void testGetType2() {
        EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>());
        Class<Object> actualType = (new DataIteratorProxy<Object>(
                new OffsetDataIterator(new DataIteratorFromJavaIterator<Object>(source, Object.class), 2))).getType();
        assertSame(Object.class, actualType);
    }

    @Test
    public void testNext() {
        EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>());
        DataIteratorProxy<Object> dataIteratorProxy = new DataIteratorProxy<Object>(
                new DataIteratorFromJavaIterator<Object>(source, Object.class));
        assertNull(dataIteratorProxy.next(new DataContainer<Object>()));
    }

    @Test
    public void testNext2() {
        ReverseIterator<Object> realIterator = new ReverseIterator<Object>(new ReverseIterator<Object>(
                new JDKIteratorWrapper<Object>(new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>()))));
        OrFilter<Object> orFilter = new OrFilter<Object>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<Object>(null, null, null);
        FilteringIterator<Object> source = new FilteringIterator<Object>(realIterator,
                new OrFilter<Object>(orFilter, orFilter1, new OrFilter<Object>(null, null, null)));
        DataIteratorProxy<Object> dataIteratorProxy = new DataIteratorProxy<Object>(
                new DataIteratorFromJavaIterator<Object>(source, Object.class));
        assertNull(dataIteratorProxy.next(new DataContainer<Object>()));
    }


    @Test
    public void testNext4() {
        EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>());
        DataIteratorProxy<Object> dataIteratorProxy = new DataIteratorProxy<Object>(
                new OffsetDataIterator(new DataIteratorFromJavaIterator<Object>(source, Object.class), 2));
        assertNull(dataIteratorProxy.next(new DataContainer<Object>()));
    }
}

