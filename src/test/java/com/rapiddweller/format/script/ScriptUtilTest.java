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

import com.rapiddweller.common.exception.ParseException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link ScriptUtil} class.
 * Created: 09.08.2010 16:11:36
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class ScriptUtilTest {

  @Test
  public void testCombineScriptableParts() {
    ScriptUtil.addFactory("xyz", new XyzScriptFactory());
    assertEquals("", ScriptUtil.combineScriptableParts());
    assertEquals("ABCDEF", ScriptUtil.combineScriptableParts("AB", "CD", "EF"));
    assertEquals("{ABCDEF}", ScriptUtil.combineScriptableParts("{ABC}", "DEF"));
    assertEquals("{ABCDEF}", ScriptUtil.combineScriptableParts("{ABC}", "{DEF}"));
    assertEquals("{ABCDEF}", ScriptUtil.combineScriptableParts("ABC", "{DEF}"));
    assertEquals("{ABCDEF}", ScriptUtil.combineScriptableParts("ABC", "{ftl:DEF}"));
    assertEquals("{SELECT * FROM TT WHERE ID = $n}",
        ScriptUtil.combineScriptableParts("SELECT * FROM TT", "{ftl: WHERE ID = $n}"));
    assertEquals("{xyz:'SELECT * FROM TT' + ' WHERE ID = ' + n}",
        ScriptUtil.combineScriptableParts("SELECT * FROM TT", "{xyz:' WHERE ID = ' + n}"));
  }

  @Test
  public void testDescribe() {
    ScriptDescriptor[] descriptors = ScriptUtil.describe("alpha", "{ftl:${n}}");
    assertEquals(2, descriptors.length);
    checkDescriptor(descriptors[0], null, "alpha", ScriptLevel.NONE);
    checkDescriptor(descriptors[1], "ftl", "${n}", ScriptLevel.SCRIPT);
  }

  @Test
  public void testGetCommonScriptEngine() {
    assertNull(ScriptUtil.getCommonScriptEngine("alpha", "123", "\n"));
    assertEquals("ftl", ScriptUtil.getCommonScriptEngine("alpha", "{ftl:${n}}", "\n"));
    assertEquals("ftl", ScriptUtil.getCommonScriptEngine("{ftl:alpha}", "{ftl:${n}}", "{ftl:\n}"));
  }

  @Test
  public void testIsScript() {
    assertTrue(ScriptUtil.isScript("{''}"));
    assertTrue(ScriptUtil.isScript(" { '' } "));
    assertFalse(ScriptUtil.isScript("{sdfw"));
    assertFalse(ScriptUtil.isScript("sdfw}"));
  }

  public class XyzScriptFactory implements ScriptFactory {
    @Override
    public Script parseText(String text) throws ParseException {
      return null;
    }

    @Override
    public Script readFile(String uri) throws ParseException {
      return null;
    }

    @Override
    public boolean isTemplateEngine() {
      return false;
    }
  }

  private static void checkDescriptor(ScriptDescriptor scriptDescriptor,
                                      String scriptEngine, String text, ScriptLevel level) {
    assertEquals(scriptEngine, scriptDescriptor.scriptEngine);
    assertEquals(text, scriptDescriptor.text);
    assertEquals(level, scriptDescriptor.level);
  }

}
