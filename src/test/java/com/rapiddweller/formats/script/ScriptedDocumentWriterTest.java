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

import org.junit.Test;
import com.rapiddweller.commons.SystemInfo;
import com.rapiddweller.formats.script.ConstantScript;
import com.rapiddweller.formats.script.ScriptedDocumentWriter;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Tests the {@link ScriptedDocumentWriter}.
 * Created: 16.06.2007 06:07:52
 * @since 0.1
 * @author Volker Bergmann
 */
public class ScriptedDocumentWriterTest {

    private static final String SEP = SystemInfo.getLineSeparator();

    private static final String RESULT =
            "header" + SEP + "row" + SEP + "footer";

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
