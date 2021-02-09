package com.rapiddweller.format.xls;

import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * The type Xls bean persistor test.
 */
public class XLSBeanPersistorTest {

  /**
   * Test load.
   *
   * @throws IOException the io exception
   */
  @Ignore
  @Test
  public void testLoad() throws IOException {

    XLSBeanPersistor<Object> xlsBeanPersistor = new XLSBeanPersistor<>(Object.class, "foo", "foo", "foo");
    xlsBeanPersistor.load(Paths.get("target/test", "test.txt").toFile(), null);
  }
}

