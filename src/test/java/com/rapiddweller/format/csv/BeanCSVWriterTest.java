/*
 * Copyright (C) 2011-2015 Volker Bergmann (volker.bergmann@bergmann-it.de).
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rapiddweller.format.csv;

import com.rapiddweller.common.DocumentWriter;
import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.format.script.ConstantScript;
import com.rapiddweller.test.TP;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link BeanCSVWriter}.
 * Created: 16.06.2007 06:07:52
 *
 * @author Volker Bergmann
 * @since 0.1
 */
public class BeanCSVWriterTest {

  private static final String SEP = SystemInfo.getLineSeparator();

  private static final String RESULT =
      "header" + SEP + "Carl;48" + SEP + "Carl;48" + SEP + "footer";

  /**
   * Test.
   *
   * @throws IOException the io exception
   */
  @Test
  public void test() throws IOException {
    StringWriter out = new StringWriter();
    DocumentWriter<TP> writer = new BeanCSVWriter<TP>(out, ';',
        new ConstantScript("header" + SEP), new ConstantScript("footer"),
        "name", "age");
    TP person = new TP();
    writer.writeElement(person);
    writer.writeElement(person);
    writer.close();
    assertEquals(RESULT, out.toString());
  }

  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    Writer out = Writer.nullWriter();
    BeanCSVWriter<Object> actualBeanCSVWriter = new BeanCSVWriter<Object>(out, 'A', Object.class);
    assertNull(actualBeanCSVWriter.getFooterScript());
    assertTrue(actualBeanCSVWriter.getHeaderScript() instanceof com.rapiddweller.format.script.ConstantScript);
  }

  /**
   * Test constructor 2.
   */
  @Test
  public void testConstructor2() {
    BeanCSVWriter<Object> actualBeanCSVWriter = new BeanCSVWriter<Object>(Writer.nullWriter(), 'A', true, "foo", "foo",
        "foo");
    assertNull(actualBeanCSVWriter.getFooterScript());
    assertTrue(actualBeanCSVWriter.getHeaderScript() instanceof com.rapiddweller.format.script.ConstantScript);
  }

  /**
   * Test constructor 3.
   */
  @Test
  public void testConstructor3() {
    BeanCSVWriter<Object> actualBeanCSVWriter = new BeanCSVWriter<Object>(Writer.nullWriter(), 'A', false, "foo", "foo",
        "foo");
    assertNull(actualBeanCSVWriter.getFooterScript());
    assertNull(actualBeanCSVWriter.getHeaderScript());
  }

  /**
   * Test constructor 4.
   */
  @Test
  public void testConstructor4() {
    BeanCSVWriter<Object> actualBeanCSVWriter = new BeanCSVWriter<Object>(Writer.nullWriter(), 'A', "foo", "foo",
        "foo");
    assertNull(actualBeanCSVWriter.getFooterScript());
    assertTrue(actualBeanCSVWriter.getHeaderScript() instanceof com.rapiddweller.format.script.ConstantScript);
  }

}
