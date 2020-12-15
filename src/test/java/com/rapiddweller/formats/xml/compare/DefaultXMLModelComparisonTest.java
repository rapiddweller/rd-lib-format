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
package com.rapiddweller.formats.xml.compare;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.rapiddweller.commons.xml.XMLUtil;
import org.junit.Test;
import org.w3c.dom.Element;

/**
 * Tests the {@link DefaultXMLComparisonModel}.
 * Created: 03.06.2014 14:12:51
 * @since 1.0.5
 * @author Volker Bergmann
 */

public class DefaultXMLModelComparisonTest {

	@Test
	public void testEquals() {
		Element a1 = XMLUtil.parseStringAsElement("<a x='1' y='2'/>");
		Element a2 = XMLUtil.parseStringAsElement("<a y='2' x='1'/>");
		Element a3 = XMLUtil.parseStringAsElement("<a x='1' y='2' z='3'/>");
		Element b1 = XMLUtil.parseStringAsElement("<b x='1' y='2'/>");
		Element c1 = XMLUtil.parseStringAsElement("<c><d/></c>");
		Element c2 = XMLUtil.parseStringAsElement("<c><d/><d/></c>");
		Element c3 = XMLUtil.parseStringAsElement("<c><d/><e/></c>");
		DefaultXMLComparisonModel model = new DefaultXMLComparisonModel();
		model.init(null, null);
		assertTrue(model.equalNodes(a1, a2));
		assertFalse(model.equalNodes(a1, a3));
		assertFalse(model.equalNodes(a1, b1));
		assertFalse(model.equalNodes(c1, c2));
		assertFalse(model.equalNodes(c1, c3));
	}
	
}
