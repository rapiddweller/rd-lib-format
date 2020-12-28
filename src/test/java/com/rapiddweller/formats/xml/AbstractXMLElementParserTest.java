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
package com.rapiddweller.formats.xml;

import static org.junit.Assert.*;

import com.rapiddweller.commons.CollectionUtil;
import com.rapiddweller.formats.xml.AbstractXMLElementParser;
import com.rapiddweller.formats.xml.ParseContext;
import org.junit.Test;
import org.w3c.dom.Element;

/**
 * Tests the {@link AbstractXMLElementParser}.
 * Created: 14.12.2012 20:24:54
 * @since 0.6.15
 * @author Volker Bergmann
 */
public class AbstractXMLElementParserTest {
	
	@Test
	public void testRenderUnsupportedAttributesMessage() {
		MyXMLElementParser parser = new MyXMLElementParser();
		StringBuilder message = parser.renderUnsupportedAttributesMessage("att1");
		String expectedMessage = "attribute 'att1' is not supported. The attributes supported by <elem> are: " +
				"req1, req2, opt1, opt2";
		assertEquals(expectedMessage, message.toString());
	}
	
	static class MyXMLElementParser extends AbstractXMLElementParser<Object> {

		public MyXMLElementParser() {
			super("elem", CollectionUtil.toSortedSet("req1", "req2"),
					CollectionUtil.toSortedSet("opt1", "opt2"));
		}

		@Override
		protected Object doParse(Element element, Object[] parentPath, ParseContext<Object> context) {
			return null;
		}
		
	}
	
}
