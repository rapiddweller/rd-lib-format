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

import com.rapiddweller.common.IOUtil;

import java.io.IOException;

/**
 * Represents an HTML &lt;head&gt;.
 * Created: 16.06.2014 11:06:23
 * @since 0.8.3
 * @author Volker Bergmann
 */

public class Head extends HtmlElement<Head> {
	
	public Head() {
		super("head", false);
	}
	
	@Override
	public Head withTitle(String title) {
		return this.withAttribute("title", title);
	}

	public Head withCssStyleSheet(String cssPath) {
		Link link = new Link().withRel("stylesheet").withType("text/css").withHref(cssPath);
		return this.addComponent(link);
	}
	
	public Head withInlineCssStyleSheet(String cssFilePath) {
		try {
			String content = IOUtil.getContentOfURI(cssFilePath);
			CssStyle style = new CssStyle().withRawTextContent(content);
			return this.addComponent(style);
		} catch (IOException e) {
			throw new RuntimeException("Error inlining css file: " + cssFilePath, e);
		}
	}
	
}
