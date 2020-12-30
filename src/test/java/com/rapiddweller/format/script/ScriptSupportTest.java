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

import com.rapiddweller.common.Context;
import com.rapiddweller.common.context.DefaultContext;
import org.junit.Ignore;
import org.junit.Test;
import org.graalvm.polyglot.Value;
import static org.junit.Assert.assertEquals;

/**
 * Test the ScriptSupport class.
 * Created: 27.01.2008 17:38:51
 * @author Volker Bergmann
 */
public class ScriptSupportTest {

	@Test
    public void testRender() {
        assertEquals("xyz", ScriptUtil.evaluate("xyz", null));
        assertEquals("xyz${var}xyz", ScriptUtil.evaluate("xyz${var}xyz", null));
        Context context = new DefaultContext();
        context.set("var", "!!!");
        assertEquals("xyz!!!xyz", ScriptUtil.evaluate("{xyz${var}xyz}", context));
        assertEquals("xyz!!!xyz", ScriptUtil.evaluate("{ftl:xyz${var}xyz}", context));
    }

    @Test
    public void GraalJsTest() {
        Context context = new DefaultContext();
        context.set("i", 5);
        assertEquals(14, ScriptUtil.evaluate("{js:4+5+i}", context));
        assertEquals("The number is 0\n" +
                "The number is 1\n" +
                "The number is 2\n" +
                "The number is 3\n" +
                "The number is 4\n", ScriptUtil.evaluate("{js:var text = \"\";\n" +
                "var a;\n" +
                "for (a = 0; a < 5; a++) {\n" +
                "  text += \"The number is \" + a + \"\\n\";\n" +
                "}}", context));
    }

    @Ignore
    @Test
    public void GraalPythonTest() {
        Context context = new DefaultContext();
        context.set("i", 5);

        System.out.println("Evaluate Python calc ...");
        Integer IntResultExpected = 14;
        assertEquals(IntResultExpected, ScriptUtil.evaluate("{py:4+5+i}", context));

        String StringResultExpected = "this is my number 5 ...";
        System.out.println("Evaluate Python fstring ...");
        ScriptUtil.evaluate("{py: ", context);
        assertEquals(StringResultExpected, ScriptUtil.evaluate("{py:f'this is my number {i} ...'}", context));
    }
	
}
