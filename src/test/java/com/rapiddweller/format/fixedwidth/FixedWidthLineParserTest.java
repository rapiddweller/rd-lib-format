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
package com.rapiddweller.format.fixedwidth;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.Arrays;

import com.rapiddweller.common.format.Alignment;
import com.rapiddweller.common.format.PadFormat;
import org.junit.Test;

/**
 * Tests the {@link FixedWidthLineParser}.
 * Created: 22.02.2010 08:19:12
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class FixedWidthLineParserTest {

	private static final PadFormat[] FORMATS = new PadFormat[] {
            new PadFormat("", 6, Alignment.LEFT, ' '),
            new PadFormat("", 3, Alignment.RIGHT, '0'),
    };
    
    private static final FixedWidthLineParser PARSER = new FixedWidthLineParser(FORMATS);

    @Test
    public void testProcessingEmptyLines() throws Exception {
        check("Alice 023", "Alice", "23");
        check("Bob   034", "Bob", "34");
        check("Charly045","Charly", "45");
        check("Dieter-01", "Dieter", "-1");
    }

	private static void check(String line, String expectedName, String expectedAge) throws ParseException {
		assertTrue(Arrays.equals(new String[] { expectedName, expectedAge }, 
				PARSER.parse(line)));
    }

}
