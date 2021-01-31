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

import com.rapiddweller.common.Named;
import com.rapiddweller.common.collection.OrderedNameMap;

import java.util.List;

/**
 * Represents a Dot graph.
 * Created: 24.05.2014 06:01:34
 * @since 0.8.2
 * @author Volker Bergmann
 */

public class DotGraph implements Named {
	
	private final String name;
	private final boolean directed;
	private RankDir rankDir;
	
	private NodeShape nodeShape;
	private Integer nodeFontSize;
	private NodeStyle nodeStyle;
	private String nodeFillColor;
	
	private EdgeStyle edgeStyle;
	private ArrowShape edgeArrowHead;
	private ArrowShape edgeArrowTail;
	private final OrderedNameMap<DotNode> nodes;
	
	private DotGraph(String name, boolean directed) {
		this.name = name;
		this.directed = directed;
		this.rankDir = null;
		this.nodeShape = null;
		this.nodeFontSize = null;
		this.nodeStyle = null;
		this.nodeFillColor = null;
		this.edgeStyle = null;
		this.edgeArrowHead = null;
		this.edgeArrowTail = null;
		this.nodes = new OrderedNameMap<DotNode>();
	}
	
	public static DotGraph newDirectedGraph(String name) {
		return new DotGraph(name, true);
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public boolean isDirected() {
		return directed;
	}
	
	public RankDir getRankDir() {
		return rankDir;
	}
	
	public DotGraph withRankDir(RankDir rankDir) {
		this.rankDir = rankDir;
		return this;
	}
	
	public NodeShape getNodeShape() {
		return nodeShape;
	}
	
	public DotGraph withNodeShape(NodeShape shape) {
		this.nodeShape = shape;
		return this;
	}
	
	public Integer getNodeFontSize() {
		return nodeFontSize;
	}
	
	public DotGraph withNodeFontSize(int points) {
		this.nodeFontSize = points;
		return this;
	}
	
	public NodeStyle getNodeStyle() {
		return this.nodeStyle;
	}
	
	public DotGraph withNodeStyle(NodeStyle style) {
		this.nodeStyle = style;
		return this;
	}
	
	public String getNodeFillColor() {
		return nodeFillColor;
	}
	
	public DotGraph withNodeFillColor(String fillColor) {
		this.nodeFillColor = fillColor;
		return this;
	}
	
	
	
	public EdgeStyle getEdgeStyle() {
		return edgeStyle;
	}
	
	public DotGraph withEdgeStyle(EdgeStyle edgeStyle) {
		this.edgeStyle = edgeStyle;
		return this;
	}
	
	public ArrowShape getEdgeArrowHead() {
		return edgeArrowHead;
	}
	
	public DotGraph withArrowHead(ArrowShape arrowHead) {
		this.edgeArrowHead = arrowHead;
		return this;
	}
	
	public ArrowShape getEdgeArrowTail() {
		return edgeArrowTail;
	}
	
	public DotGraph withEdgeArrowTail(ArrowShape arrowTail) {
		this.edgeArrowTail = arrowTail;
		return this;
	}
	
	public DotNode newNode(String name) {
		DotNode node = new DotNode(name);
		nodes.put(node.getName(), node);
		return node;
	}
	
	public List<DotNode> getNodes() {
		return nodes.values();
	}

}
