package com.rapiddweller.format.util;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class JavaIterableFromDataSourceTest {
    @Test
    public void testIterator() {
        ArrayList<Object> source = new ArrayList<Object>();
        assertTrue((new JavaIterableFromDataSource<Object>(new DataSourceProxy(
                new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<Object>(source, Object.class))))))
                .iterator() instanceof JavaIteratorFromDataIterator);
    }

    @Test
    public void testIterator2() {
        ArrayList<Object> objectList = new ArrayList<Object>();
        objectList.add("e");
        assertTrue((new JavaIterableFromDataSource<Object>(new DataSourceProxy(
                new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<Object>(objectList, Object.class))))))
                .iterator() instanceof JavaIteratorFromDataIterator);
    }
}

