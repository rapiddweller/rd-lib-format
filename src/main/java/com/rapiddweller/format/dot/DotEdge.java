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
package com.rapiddweller.format.dot;

/**
 * Represents an edge in a Dot graph.
 * Created: 24.05.2014 08:07:52
 * @since 0.8.2
 * @author Volker Bergmann
 */

public class DotEdge {
	
	private final DotNode from;
	private final DotNode to;
	private ArrowShape arrowHead;
	private String headLabel;
	private ArrowShape arrowTail;
	private String tailLabel;
	private EdgeStyle style;
	
	public DotEdge(DotNode from, DotNode to) {
		this.from = from;
		this.arrowHead = null;
		this.to = to;
		this.arrowTail = null;
		this.style = null;
	}
	
	public DotNode getFrom() {
		return from;
	}
	
	public DotNode getTo() {
		return to;
	}
	
	public ArrowShape getArrowHead() {
		return arrowHead;
	}
	
	public DotEdge withArrowHead(ArrowShape arrowHead) {
		this.arrowHead = arrowHead;
		return this;
	}

	public ArrowShape getArrowTail() {
		return arrowTail;
	}
	
	public DotEdge withArrowTail(ArrowShape arrowTail) {
		this.arrowTail = arrowTail;
		return this;
	}
	
	public String getHeadLabel() {
		return headLabel;
	}
	
	public DotEdge withHeadLabel(String headLabel) {
		this.headLabel = headLabel;
		return this;
	}
	
	public String getTailLabel() {
		return tailLabel;
	}
	
	public DotEdge withTailLabel(String tailLabel) {
		this.tailLabel = tailLabel;
		return this;
	}
	
	public EdgeStyle getStyle() {
		return style;
	}
	
	public DotEdge withStyle(EdgeStyle style) {
		this.style = style;
		return this;
	}
	
}
