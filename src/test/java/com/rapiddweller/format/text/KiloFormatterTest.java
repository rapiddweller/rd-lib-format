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
package com.rapiddweller.format.text;

import static org.junit.Assert.*;

import java.util.Locale;

import com.rapiddweller.format.text.KiloFormatter;
import org.junit.Test;

/**
 * Tests the {@link KiloFormatter}.
 * Created: 13.12.2012 14:17:17
 * @since 0.5.21
 * @author Volker Bergmann
 */
public class KiloFormatterTest {
	
	@Test
	public void testBase1000() {
		KiloFormatter formatter = new KiloFormatter(1000, Locale.US);
		assertEquals("0", formatter.format(0));
		assertEquals("1", formatter.format(1));
		assertEquals("999", formatter.format(999));
		assertEquals("1 K", formatter.format(1000));
		assertEquals("1.1 K", formatter.format(1100));
		assertEquals("1.9 K", formatter.format(1900));
		assertEquals("2 K", formatter.format(1999));
		assertEquals("2 K", formatter.format(2000));
		assertEquals("2 K", formatter.format(2001));
		assertEquals("1 M", formatter.format(1000000));
		assertEquals("1 G", formatter.format(1000000000L));
		assertEquals("1 T", formatter.format(1000000000000L));
		assertEquals("1 E", formatter.format(1000000000000000L));
		assertEquals("1000 E", formatter.format(1000000000000000000L));
	}
	
}
