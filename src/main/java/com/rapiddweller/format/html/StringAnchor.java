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
package com.rapiddweller.format.html;

/**
 * {@link Anchor} implementation for an arbitrary String-based URL.
 * Created: 13.06.2011 12:50:11
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class StringAnchor extends Anchor {

	public final String href;

	public StringAnchor(String href, String label) {
		super(label);
		this.href = href;
	}

	public StringAnchor(String href, String label, String target) {
		super(label, target);
		this.href = href;
	}

	public static StringAnchor createAnchorForNewWindow(String href, String label) {
		return new StringAnchor(href, label, "_blank");
	}
	
	@Override
	public String toString() {
		return "<a href='" + href + "'" + (target != null ? " target='" + target + "'" : "") + ">" + label + "</a>";
	}
	
}
