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

package com.rapiddweller.format.xsd;

import com.rapiddweller.common.xml.XMLUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link SchemaParser}.
 * Created: 16.05.2014 18:39:35
 *
 * @author Volker Bergmann
 * @since 0.8.2
 */
public class SchemaParserTest {

  private static final String EXPECTED_SCHEMA_DOC = "\n" +
      "            Created: Exported from EDISIM 6.12.1 10/16/2013 15:43:17.713\n" +
      "            Type: UN\n" +
      "            VRI: D 03A\n" +
      "            Desc: UN/EDIFACT Draft Messages and Directories Version D.03A - publ. Jun. 2003\n" +
      "        ";

  /**
   * Test.
   *
   * @throws Exception the exception
   */
  @Test
  public void test() throws Exception {
    Schema schema = new SchemaParser().parse(XMLUtil.parse("com/rapiddweller/format/xsd/D03A_IFTDGN.xsd"));
    schema.printContent();
    assertEquals(EXPECTED_SCHEMA_DOC, schema.getDocumentation());
  }

}
