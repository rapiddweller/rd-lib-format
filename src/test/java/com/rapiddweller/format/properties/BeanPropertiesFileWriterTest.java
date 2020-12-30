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
package com.rapiddweller.format.properties;

import static org.junit.Assert.*;

import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import com.rapiddweller.common.DocumentWriter;
import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.format.script.ConstantScript;
import com.rapiddweller.test.TP;

/**
 * Tests the BeanPropertiesFileWriter.
 * 
 * Created: 16.06.2007 06:07:52
 * @since 0.1
 * @author Volker Bergmann
 */
public class BeanPropertiesFileWriterTest {

    private static final String SEP = SystemInfo.getLineSeparator();

    private static final String UNPREFIXED_RESULT =
            "# header" + SEP + "class=com.rapiddweller.test.TP" + SEP + "name=Carl" + SEP + "age=48" + SEP + "# footer";

    private static final String PREFIXED_RESULT =
            "# header" + SEP +
            "person1.class=com.rapiddweller.test.TP" + SEP + "person1.name=Carl" + SEP + "person1.age=48" + SEP +
            "person2.class=com.rapiddweller.test.TP" + SEP + "person2.name=Carl" + SEP + "person2.age=48" + SEP +
            "# footer";

    @Test
    public void testEscaping() throws IOException {
        StringWriter out = new StringWriter();
        DocumentWriter<TP> writer = new BeanPropertiesFileWriter<TP>(
                out, null, (String)null, null, new String[] { "class", "name", "age" }
        );
        TP person = new TP("Al\\f");
        writer.writeElement(person);
        writer.close();
        assertEquals("class=com.rapiddweller.test.TP" + SEP + "name=Al\\\\f" + SEP + "age=48" + SEP, out.toString());
    }

    @Test
    public void testUnprefixed() throws IOException {
        StringWriter out = new StringWriter();
        DocumentWriter<TP> writer = new BeanPropertiesFileWriter<TP>(
                out,
                null,
                new ConstantScript("# header" + SEP),
                new ConstantScript("# footer"),
                "class", "name", "age");
        TP person = new TP();
        writer.writeElement(person);
        writer.close();
        assertEquals(UNPREFIXED_RESULT, out.toString());
    }

    @Test
    public void testPrefixed() throws IOException {
        StringWriter out = new StringWriter();
        DocumentWriter<TP> writer = new BeanPropertiesFileWriter<TP>(
                out,
                "person{0}.",
                new ConstantScript("# header" + SEP),
                new ConstantScript("# footer"),
                "class", "name", "age");
        TP person = new TP();
        writer.writeElement(person);
        writer.writeElement(person);
        writer.close();
        assertEquals(PREFIXED_RESULT, out.toString());
    }

}
