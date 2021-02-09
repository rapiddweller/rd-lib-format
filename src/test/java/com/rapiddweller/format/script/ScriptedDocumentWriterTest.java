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

package com.rapiddweller.format.script;

import com.rapiddweller.common.SystemInfo;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link ScriptedDocumentWriter}.
 * Created: 16.06.2007 06:07:52
 *
 * @author Volker Bergmann
 * @since 0.1
 */
public class ScriptedDocumentWriterTest {

  private static final String SEP = SystemInfo.getLineSeparator();

  private static final String RESULT =
      "header" + SEP + "row" + SEP + "footer";

  /**
   * Test.
   *
   * @throws IOException the io exception
   */
  @Test
  public void test() throws IOException {
    StringWriter out = new StringWriter();
    ScriptedDocumentWriter<String> writer = new ScriptedDocumentWriter<String>(
        out,
        new ConstantScript("header" + SEP),
        new ConstantScript("row" + SEP),
        new ConstantScript("footer"));
    writer.writeElement(null);
    writer.close();
    assertEquals(RESULT, out.toString());
  }

}
