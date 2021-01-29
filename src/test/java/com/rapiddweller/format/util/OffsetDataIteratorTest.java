package com.rapiddweller.format.util;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.common.filter.OrFilter;
import com.rapiddweller.common.iterator.CyclicIterator;
import com.rapiddweller.common.iterator.FilteringIterator;
import com.rapiddweller.common.iterator.JDKIteratorWrapper;
import com.rapiddweller.common.iterator.ReverseIterator;

import java.util.HashSet;
import java.util.Map;

import org.apache.commons.collections4.map.EntrySetToMapIteratorAdapter;
import org.junit.Test;

public class OffsetDataIteratorTest {
    @Test
    public void testConstructor() {
        EntrySetToMapIteratorAdapter source = new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>());
        DataIteratorFromJavaIterator<Object> dataIteratorFromJavaIterator = new DataIteratorFromJavaIterator<Object>(source,
                Object.class);
        Class<Object> expectedType = dataIteratorFromJavaIterator.type;
        assertSame(expectedType, (new OffsetDataIterator<Object>(dataIteratorFromJavaIterator, 2)).getType());
    }

    @Test
    public void testConstructor2() {
        ReverseIterator<Object> realIterator = new ReverseIterator<Object>(new ReverseIterator<Object>(
                new JDKIteratorWrapper<Object>(new EntrySetToMapIteratorAdapter(new HashSet<Map.Entry<Object, Object>>()))));
        OrFilter<Object> orFilter = new OrFilter<Object>(null, null, null);
        OrFilter<Object> orFilter1 = new OrFilter<Object>(null, null, null);
        FilteringIterator<Object> source = new FilteringIterator<Object>(realIterator,
                new OrFilter<Object>(orFilter, orFilter1, new OrFilter<Object>(null, null, null)));
        DataIteratorFromJavaIterator<Object> dataIteratorFromJavaIterator = new DataIteratorFromJavaIterator<Object>(source,
                Object.class);
        Class<Object> expectedType = dataIteratorFromJavaIterator.type;
        assertSame(expectedType, (new OffsetDataIterator<Object>(dataIteratorFromJavaIterator, 2)).getType());
    }

}

