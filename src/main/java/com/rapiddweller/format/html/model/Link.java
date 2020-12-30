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
 * Represents an HTML &lt;link&gt;.
 * Created: 16.06.2014 11:13:45
 * @since 0.8.3
 * @author Volker Bergmann
 */

public class Link extends HtmlElement<Link> {
	
	public Link() {
		super("link", true);
	}
	
	public Link withRel(String rel) {
		return this.withAttribute("rel", rel);
	}
	
	public Link withType(String type) {
		return this.withAttribute("type", type);
	}
	
	public Link withHref(String href) {
		return this.withAttribute("href", href);
	}
	
}
