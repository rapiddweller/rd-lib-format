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
package com.rapiddweller.formats.csv;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.StringWriter;
import java.io.IOException;

import com.rapiddweller.formats.csv.ArrayCSVWriter;
import com.rapiddweller.formats.script.ConstantScript;
import com.rapiddweller.commons.SystemInfo;

/**
 * Tests the {@link ArrayCSVWriter}.
 * 
 * Created: 16.06.2007 06:07:52
 * @since 0.1
 * @author Volker Bergmann
 */
public class ArrayCSVWriterTest {

    private static final String SEP = SystemInfo.getLineSeparator();

    private static String RESULT =
            "header" + SEP + "1;2;3" + SEP + "4;5;6" + SEP + "footer";

    @Test
    public void test() throws IOException {
        StringWriter out = new StringWriter();
        ArrayCSVWriter writer = new ArrayCSVWriter(out, ';',
                new ConstantScript("header" + SEP), new ConstantScript("footer"));
        writer.writeElement(new Integer[] { 1, 2, 3 });
        writer.writeElement(new Integer[] { 4, 5, 6 });
        writer.close();
        assertEquals(RESULT, out.toString());
    }
    
}
