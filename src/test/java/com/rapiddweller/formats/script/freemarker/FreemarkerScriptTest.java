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
package com.rapiddweller.formats.script.freemarker;

import static org.junit.Assert.assertEquals;

import java.io.StringWriter;
import java.io.IOException;

import com.rapiddweller.commons.Context;
import com.rapiddweller.commons.context.DefaultContext;
import com.rapiddweller.formats.script.Script;
import com.rapiddweller.formats.script.ScriptException;
import com.rapiddweller.formats.script.freemarker.FreeMarkerScript;
import org.junit.Test;

import freemarker.template.Configuration;

/**
 * Tests the {@link FreeMarkerScript}.
 * Created: 12.06.2007 17:36:30
 * @since 0.1
 * @author Volker Bergmann
 */
public class FreemarkerScriptTest {

	@Test
    public void testScriptGetInstance() throws IOException, ScriptException {
        Script script = new FreeMarkerScript("src/test/resources/com/rapiddweller/formats/script/freemarker/test.ftl", new Configuration());
        Context context = new DefaultContext();
        context.set("var_dings", "XYZ");
        StringWriter writer = new StringWriter();
        script.execute(context, writer);
        assertEquals("TestXYZTest", writer.toString());
    }
	
}
