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
package com.rapiddweller.formats.dot;

import java.util.ArrayList;
import java.util.List;

import com.rapiddweller.commons.Named;

/**
 * Represents a node in a Dot graph.
 * Created: 24.05.2014 07:36:36
 * @since 0.8.2
 * @author Volker Bergmann
 */

public class DotNode implements Named {
	
	private final String name;
	private final boolean vertical;
	private NodeStyle style;
	private final String fillColor;
	private final List<String> segments;
	private final List<DotEdge> edges;

	public DotNode(String name) {
		this.name = name;
		this.vertical = true;
		this.style = null;
		this.fillColor = null;
		this.segments = new ArrayList<String>();
		this.edges = new ArrayList<DotEdge>();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public boolean isVertical() {
		return vertical;
	}
	
	public NodeStyle getStyle() {
		return style;
	}
	
	public DotNode withStyle(NodeStyle style) {
		this.style = style;
		return this;
	}
	
	public String getFillColor() {
		return fillColor;
	}
	
	public DotEdge newEdgeTo(DotNode target) {
		DotEdge edge = new DotEdge(this, target);
		edges.add(edge);
		return edge;
	}
	
	public List<DotEdge> getEdges() {
		return edges;
	}

	public DotNode withEdgeTo(DotNode target) {
		newEdgeTo(target);
		return this;
	}
	
	public List<String> getSegments() {
		return segments;
	}
	
	public DotNode withSegment(String... lines) {
		StringBuilder segment = new StringBuilder();
		for (String line : lines)
			segment.append(line).append("\\l");
		segments.add(segment.toString());
		return this;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
}
