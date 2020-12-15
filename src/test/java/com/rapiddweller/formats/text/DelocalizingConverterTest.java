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
package com.rapiddweller.formats.text;

import java.io.IOException;

import com.rapiddweller.formats.text.DelocalizingConverter;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the {@link DelocalizingConverter}.
 * Created: 03.09.2006 19:29:56
 * @since 0.1
 * @author Volker Bergmann
 */
public class DelocalizingConverterTest {

	@Test
    public void testConversion() throws IOException {
        checkConversion("Abc", "Abc");
        checkConversion("ÄÖÜäöüß", "AeOeUeaeoeuess");
        checkConversion("áàâa", "aaaa");
        checkConversion("éèêe", "eeee");
        checkConversion("íìîi", "iiii");
        checkConversion("óòôo", "oooo");
        checkConversion("úùûu", "uuuu");
    }

    private static void checkConversion(String source, String expectedResult) throws IOException {
        DelocalizingConverter converter = new DelocalizingConverter();
        String result = converter.convert(source);
        assertEquals("Delocalization failed. ", expectedResult, result);
    }

}
