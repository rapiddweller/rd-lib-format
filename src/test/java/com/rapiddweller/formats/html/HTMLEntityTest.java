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
package com.rapiddweller.formats.html;

import com.rapiddweller.formats.html.HtmlEntity;

import junit.framework.TestCase;

/**
 * Tests the {@link HTMLEntityTest}.
 * Created at 02.05.2008 17:28:04
 * @since 0.4.3
 * @author Volker Bergmann
 */
public class HTMLEntityTest extends TestCase {
	
	public void testXmlCode() {
		assertEquals(38, HtmlEntity.getEntity("_&amp;_", 1).xmlCode);
	}

	public void testCharacter() {
		assertEquals('Ö', HtmlEntity.getEntity("&Ouml;", 0).xmlCode);
		assertEquals('ì', HtmlEntity.getEntity("&igrave;", 0).xmlCode);
	}

	public void testNumberCode() {
		assertEquals('Ö', HtmlEntity.getEntity("&#214;", 0).xmlCode);
	}

}
