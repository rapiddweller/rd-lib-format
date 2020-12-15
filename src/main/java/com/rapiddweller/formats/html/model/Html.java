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
package com.rapiddweller.formats.html.model;

/**
 * Represents the &lt;html&gt; content of an HTML document.
 * Created: 06.01.2014 09:14:12
 * @since 0.7.1
 * @author Volker Bergmann
 */

public class Html extends HtmlElement<Html> {
	
	public Html(HtmlComponent... components) {
		super("html", false);
		setComponents(components);
	}
	
	public Head createHead() {
		Head head = new Head();
		addComponent(head);
		return head;
	}

	public Body createBody() {
		Body body = new Body();
		addComponent(body);
		return body;
	}
	
}
