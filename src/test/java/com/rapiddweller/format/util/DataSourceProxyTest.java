package com.rapiddweller.format.util;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.format.DataIterator;

import java.util.ArrayList;

import org.junit.Test;

public class DataSourceProxyTest {
    @Test
    public void testConstructor() {
        ArrayList<Object> source = new ArrayList<Object>();
        DataSourceProxy<Object> actualDataSourceProxy = new DataSourceProxy<Object>(new DataSourceProxy(new DataSourceProxy(
                new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<Object>(source, Object.class))))));
        assertTrue(actualDataSourceProxy.source instanceof DataSourceProxy);
        Class<Object> expectedType = actualDataSourceProxy.type;
        assertSame(expectedType, actualDataSourceProxy.getType());
    }

    @Test
    public void testIterator() {
        ArrayList<Object> source = new ArrayList<Object>();
        DataIterator<Object> actualIteratorResult = (new DataSourceProxy<Object>(new DataSourceProxy(
                new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<Object>(source, Object.class))))))
                .iterator();
        Class<Object> expectedType = ((DataIteratorFromJavaIterator<Object>) actualIteratorResult).type;
        assertSame(expectedType, actualIteratorResult.getType());
    }
}

