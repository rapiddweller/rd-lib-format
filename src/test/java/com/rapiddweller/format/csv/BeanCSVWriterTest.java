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

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import com.rapiddweller.common.DocumentWriter;
import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.format.csv.BeanCSVWriter;
import com.rapiddweller.format.script.ConstantScript;
import com.rapiddweller.test.TP;

/**
 * Tests the {@link BeanCSVWriter}.
 * Created: 16.06.2007 06:07:52
 * @since 0.1
 * @author Volker Bergmann
 */
public class BeanCSVWriterTest {

    private static final String SEP = SystemInfo.getLineSeparator();

    private static final String RESULT =
            "header" + SEP + "Carl;48" + SEP + "Carl;48" + SEP + "footer";

    @Test
    public void test() throws IOException {
        StringWriter out = new StringWriter();
        DocumentWriter<TP> writer = new BeanCSVWriter<TP>(out, ';',
                new ConstantScript("header" + SEP), new ConstantScript("footer"),
                "name", "age");
        TP person = new TP();
        writer.writeElement(person);
        writer.writeElement(person);
        writer.close();
        assertEquals(RESULT, out.toString());
    }

}
