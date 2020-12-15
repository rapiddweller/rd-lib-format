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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.rapiddweller.commons.SystemInfo;
import com.rapiddweller.commons.collection.OrderedNameMap;

/**
 * Parent class for HTML element classes.
 * Created: 06.01.2014 08:13:00
 * @param <E> Generic parameter to be set by child classes to the child class itself
 * @since 0.7.1
 * @author Volker Bergmann
 */

public class HtmlElement<E extends HtmlElement<?>> extends HtmlComponent {
	
	private String tagName;
	protected boolean inline;
	protected OrderedNameMap<String> attributes;
	private List<HtmlComponent> components;
	
	public HtmlElement(String tagName, boolean inline) {
		this.tagName = tagName;
		this.inline = inline;
		this.attributes = new OrderedNameMap<String>();
		this.components = new ArrayList<HtmlComponent>();
	}
	
	public E withClass(String klass) {
		return this.withAttribute("class", klass);
	}

	public E withStyle(String style) {
		return this.withAttribute("style", style);
	}
	
	public E withTitle(String title) {
		return withAttribute("title", title);
	}
	
	public E withAlign(String align) {
		return withAttribute("align", align);
	}
	
	public E withValign(String valign) {
		return withAttribute("valign", valign);
	}
	
	@SuppressWarnings("unchecked")
	protected E withAttribute(String attributeName, String attributeValue) {
		attributes.put(attributeName, attributeValue);
		return (E) this;
	}
	
	public E addBreak() {
		return addComponent(HtmlFactory.br());
	}
	
	public E withRawTextContent(String text) {
		return withTextContent(text, false, false);
	}

	public E withTextContent(String text, boolean escape, boolean convertLinefeeds) {
		return this.withComponents(new TextComponent(text, escape, convertLinefeeds));
	}

	@SuppressWarnings("unchecked")
	public E withComponents(HtmlComponent... newComponents) {
		setComponents(newComponents);
		return (E) this;
	}

	public void setComponents(HtmlComponent... newComponents) {
		this.components.clear();
		for (HtmlComponent component : newComponents)
			addComponent(component);
	}

	public E addComponent(String textToAdd) {
		return addComponent(new TextComponent(textToAdd));
	}

	public void addComponents(HtmlComponent... components) {
		for (HtmlComponent component : components)
			addComponent(component);
	}
	
	@SuppressWarnings("unchecked")
	public E addComponent(HtmlComponent componentToAdd) {
		this.components.add(componentToAdd);
		return (E) this;
	}

	public String getTagName() {
		return tagName;
	}
	
	public boolean isInline() {
		return inline;
	}
	
	
	// rendering -------------------------------------------------------------------------------------------------------
	
	protected String formatStartTag() {
		StringBuilder builder = new StringBuilder();
		builder.append('<').append(tagName);
		if (!attributes.isEmpty())
			formatAttributes(builder);
		builder.append('>');
		if (!inline)
			builder.append(SystemInfo.getLineSeparator());
		return builder.toString();
	}

	protected String formatAtomicTag() {
		StringBuilder builder = new StringBuilder();
		builder.append('<').append(tagName);
		if (!attributes.isEmpty())
			formatAttributes(builder);
		builder.append("/>");
		if (!inline)
			builder.append(SystemInfo.getLineSeparator());
		return builder.toString();
	}

	protected void formatAttributes(StringBuilder builder) {
		for (Map.Entry<String, String> att : attributes.entrySet())
			builder.append(' ').append(att.getKey()).append("=\"").append(att.getValue()).append('"');
	}

	protected String formatComponents() {
		StringBuilder builder = new StringBuilder();
		for (HtmlComponent component : this.components)
			builder.append(component);
		return builder.toString();
	}
	
	protected String formatEndTag() {
		StringBuilder builder = new StringBuilder();
		if (!inline)
			builder.append(SystemInfo.getLineSeparator());
		builder.append("</").append(tagName).append('>');
		if (!inline)
			builder.append(SystemInfo.getLineSeparator());
		return builder.toString();
	}
	
	@Override
	public String toString() {
		if (components.isEmpty())
			return formatAtomicTag();
		else
			return formatStartTag() + formatComponents() + formatEndTag();
	}

}
