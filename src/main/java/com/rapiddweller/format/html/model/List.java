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

/**
 * Parent class for HTML lists.
 * Created: 09.07.2014 13:51:04
 * @since 0.8.4
 * @author Volker Bergmann
 */

public abstract class List extends HtmlElement<List> {

	public List(String tagName) {
		super(tagName, false);
	}

	public void addAsListItem(String text) {
		addAsListItem(new TextComponent(text));
	}

	public void addAsListItem(HtmlComponent component) {
		addComponent(new ListItem(component));
	}

}
