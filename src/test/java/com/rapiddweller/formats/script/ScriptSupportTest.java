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
package com.rapiddweller.formats.script;

import static org.junit.Assert.*;

import com.rapiddweller.commons.Context;
import com.rapiddweller.commons.context.DefaultContext;
import com.rapiddweller.formats.script.ScriptUtil;
import org.junit.Test;

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
	
}
