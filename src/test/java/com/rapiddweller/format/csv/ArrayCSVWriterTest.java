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

import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.format.script.ConstantScript;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.Assert.*;

/**
 * Tests the {@link ArrayCSVWriter}.
 * <p>
 * Created: 16.06.2007 06:07:52
 *
 * @author Volker Bergmann
 * @since 0.1
 */
public class ArrayCSVWriterTest {

    private static final String SEP = SystemInfo.getLineSeparator();

    private static final String RESULT =
            "header" + SEP + "1;2;3" + SEP + "4;5;6" + SEP + "footer";

    @Test
    public void test() throws IOException {
        StringWriter out = new StringWriter();
        ArrayCSVWriter writer = new ArrayCSVWriter(out, ';',
                new ConstantScript("header" + SEP), new ConstantScript("footer"));
        writer.writeElement(new Integer[]{1, 2, 3});
        writer.writeElement(new Integer[]{4, 5, 6});
        writer.close();
        assertEquals(RESULT, out.toString());
    }

    @Test
    public void testConstructor() {
        ArrayCSVWriter actualArrayCSVWriter = new ArrayCSVWriter(Writer.nullWriter());
        assertNull(actualArrayCSVWriter.getFooterScript());
        assertNull(actualArrayCSVWriter.getHeaderScript());
    }

    @Test
    public void testConstructor2() {
        ArrayCSVWriter actualArrayCSVWriter = new ArrayCSVWriter(Writer.nullWriter(), 'A');
        assertNull(actualArrayCSVWriter.getFooterScript());
        assertNull(actualArrayCSVWriter.getHeaderScript());
    }

    @Test
    public void testConstructor3() {
        ArrayCSVWriter actualArrayCSVWriter = new ArrayCSVWriter(Writer.nullWriter(), 'A', "foo", "foo", "foo");
        assertNull(actualArrayCSVWriter.getFooterScript());
        assertTrue(actualArrayCSVWriter.getHeaderScript() instanceof com.rapiddweller.format.script.ConstantScript);
    }

}
