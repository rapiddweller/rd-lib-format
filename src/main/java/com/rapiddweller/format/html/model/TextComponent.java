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
package com.rapiddweller.format.html.model;

import com.rapiddweller.format.html.util.HTMLUtil;

/**
 * Represents an HTML text component.
 * Created: 06.01.2014 08:25:00
 * @since 0.7.1
 * @author Volker Bergmann
 */

public class TextComponent extends HtmlComponent {
	
	private final String text;
	
	public TextComponent(String text) {
		this(text, true, false);
	}
	
	public TextComponent(String text, boolean escape, boolean convertLinefeeds) {
		String tmp = text;
		if (escape)
			tmp = HTMLUtil.escape(tmp);
		if (convertLinefeeds)
			tmp = HTMLUtil.convertLineFeeds(tmp);
		this.text = tmp;
	}
	
	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		return text;
	}
	
}
