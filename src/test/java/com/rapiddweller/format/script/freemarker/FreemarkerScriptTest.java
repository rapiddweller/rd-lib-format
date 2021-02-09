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
package com.rapiddweller.format.script.freemarker;

import com.rapiddweller.common.Context;
import com.rapiddweller.common.context.DefaultContext;
import com.rapiddweller.format.script.Script;
import com.rapiddweller.format.script.ScriptException;
import freemarker.template.Configuration;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link FreeMarkerScript}.
 * Created: 12.06.2007 17:36:30
 *
 * @author Volker Bergmann
 * @since 0.1
 */
public class FreemarkerScriptTest {

  /**
   * Test script get instance.
   *
   * @throws IOException     the io exception
   * @throws ScriptException the script exception
   */
  @Test
    public void testScriptGetInstance() throws IOException, ScriptException {
        Script script = new FreeMarkerScript("src/test/resources/com/rapiddweller/format/script/freemarker/test.ftl", new Configuration());
        Context context = new DefaultContext();
        context.set("var_dings", "XYZ");
        StringWriter writer = new StringWriter();
        script.execute(context, writer);
        assertEquals("TestXYZTest", writer.toString());
    }
	
}
