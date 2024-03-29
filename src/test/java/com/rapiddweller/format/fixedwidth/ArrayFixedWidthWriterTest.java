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

package com.rapiddweller.format.fixedwidth;

import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.common.format.Alignment;
import com.rapiddweller.format.script.ConstantScript;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link ArrayFixedWidthWriter}.
 * Created: 16.06.2007 06:07:52
 *
 * @author Volker Bergmann
 * @since 0.1
 */
public class ArrayFixedWidthWriterTest {

  private static final String SEP = SystemInfo.getLineSeparator();

  private static final String RESULT =
      "header" + SEP + "1   23" + SEP + "14 156" + SEP + "footer";

  /**
   * Test.
   *
   * @throws IOException the io exception
   */
  @Test
  public void test() throws IOException {
    StringWriter out = new StringWriter();
    ArrayFixedWidthWriter<Integer> writer = new ArrayFixedWidthWriter<>(
        out, new ConstantScript("header" + SEP), new ConstantScript("footer"),
        new FixedWidthRowTypeDescriptor("default", new FixedWidthColumnDescriptor[] {
            new FixedWidthColumnDescriptor(2, Alignment.LEFT),
            new FixedWidthColumnDescriptor(3, Alignment.RIGHT),
            new FixedWidthColumnDescriptor(1, Alignment.LEFT)
        })
    );
    writer.writeElement(new Integer[] {1, 2, 3});
    writer.writeElement(new Integer[] {14, 15, 6});
    writer.close();
    assertEquals(RESULT, out.toString());
  }

}
