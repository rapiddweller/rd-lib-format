package com.rapiddweller.format.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.format.DataIterator;

import java.util.ArrayList;

import org.junit.Test;

public class OffsetDataSourceTest {
    @Test
    public void testConstructor() {
        ArrayList<Object> source = new ArrayList<Object>();
        OffsetDataSource<Object> actualOffsetDataSource = new OffsetDataSource<Object>(
                new DataSourceProxy(new DataSourceProxy(
                        new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<Object>(source, Object.class))))),
                2);
        assertEquals(2, actualOffsetDataSource.offset);
        assertTrue(actualOffsetDataSource.source instanceof DataSourceProxy);
        Class<Object> expectedType = actualOffsetDataSource.type;
        assertSame(expectedType, actualOffsetDataSource.getType());
    }

    @Test
    public void testIterator() {
        ArrayList<Object> source = new ArrayList<Object>();
        DataIterator<Object> actualIteratorResult = (new OffsetDataSource<Object>(new DataSourceProxy(
                new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<Object>(source, Object.class)))), 2))
                .iterator();
        Class<Object> expectedType = ((DataIteratorFromJavaIterator<Object>) actualIteratorResult).type;
        assertSame(expectedType, actualIteratorResult.getType());
    }

    @Test
    public void testIterator2() {
        ArrayList<Object> source = new ArrayList<Object>();
        DataIterator<Object> actualIteratorResult = (new OffsetDataSource<Object>(
                new DataSourceProxy(new DataSourceProxy(new DataSourceProxy(new OffsetDataSource(
                        new DataSourceProxy(
                                new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<Object>(source, Object.class)))),
                        2)))),
                2)).iterator();
        Class<Object> expectedType = ((DataIteratorFromJavaIterator<Object>) actualIteratorResult).type;
        assertSame(expectedType, actualIteratorResult.getType());
    }

    @Test
    public void testToString() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        ArrayList<Object> source = new ArrayList<Object>();
        (new OffsetDataSource<Object>(new DataSourceProxy(
                new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<Object>(source, Object.class)))), 2))
                .toString();
    }

    @Test
    public void testToString2() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        ArrayList<Object> source = new ArrayList<Object>();
        (new OffsetDataSource<Object>(new OffsetDataSource(new DataSourceProxy(
                new DataSourceProxy(new DataSourceProxy(new DataSourceFromIterable<Object>(source, Object.class)))), 2), 2))
                .toString();
    }
}

