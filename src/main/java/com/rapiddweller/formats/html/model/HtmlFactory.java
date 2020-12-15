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
 * Creates common HTML elements in utility methods.
 * Created: 06.01.2014 08:11:27
 * @since 0.7.1
 * @author Volker Bergmann
 */

public class HtmlFactory {
	
	private HtmlFactory() { }
	
	public static Bold bold(String content) {
		return new Bold(content);
	}
	
	public static Bold bold(HtmlComponent content) {
		return new Bold(content);
	}
	
	public static Font font(String content) {
		return new Font(content);
	}
	
	public static Font font(HtmlComponent content) {
		return new Font(content);
	}
	
	public static Anchor urlAnchor(String label, String url) {
		return new Anchor(label).withHref(url);
	}
	
	@SuppressWarnings("rawtypes")
	public static HtmlElement<?> br() {
		return new HtmlElement("br", true);
	}
	
}
