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

import java.util.Collection;

/**
 * Abstract implementation of the {@link DotGraphModel}.
 * Created: 25.05.2014 06:29:29
 * @since 0.8.2
 * @author Volker Bergmann
 */

public abstract class AbstractDotGraphModel implements DotGraphModel {
	
	private String name;
	private boolean directed;
	private RankDir rankDir;
	private NodeShape nodeShape;
	private Integer nodeFontSize;
	private NodeStyle nodeStyle;
	private String nodeFillColor;
	private ArrowShape arrowHead;
	private ArrowShape arrowTail;
	private EdgeStyle edgeStyle;
	
	
	public AbstractDotGraphModel() {
		this.name = null;
		this.directed = true;
		this.rankDir = null;
		this.nodeShape = null;
		this.nodeFontSize = null;
		this.nodeStyle = null;
		this.nodeFillColor = null;
		this.arrowHead = null;
		this.arrowTail = null;
		this.edgeStyle = null;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public AbstractDotGraphModel setName(String name) {
		this.name = name;
		return this;
	}
	
	@Override
	public boolean isDirected() {
		return directed;
	}
	
	public AbstractDotGraphModel withDirected() {
		this.directed = true;
		return this;
	}
	
	public AbstractDotGraphModel withUndirected() {
		this.directed = false;
		return this;
	}
	
	@Override
	public RankDir getRankDir() {
		return rankDir;
	}
	
	public AbstractDotGraphModel withRankDir(RankDir rankDir) {
		this.rankDir = rankDir;
		return this;
	}
	
	@Override
	public NodeShape getNodeShape() {
		return nodeShape;
	}
	
	public AbstractDotGraphModel withNodeShape(NodeShape nodeShape) {
		this.nodeShape = nodeShape;
		return this;
	}
	
	@Override
	public Integer getNodeFontSize() {
		return nodeFontSize;
	}
	
	public AbstractDotGraphModel withNodeFontSize(int fontSize) {
		this.nodeFontSize = fontSize;
		return this;
	}
	
	@Override
	public NodeStyle getNodeStyle() {
		return nodeStyle;
	}
	
	public AbstractDotGraphModel withNodeStyle(NodeStyle nodeStyle) {
		this.nodeStyle = nodeStyle;
		return this;
	}
	
	@Override
	public String getNodeFillColor() {
		return nodeFillColor;
	}
	
	public AbstractDotGraphModel withNodeFillColor(String color) {
		this.nodeFillColor = color;
		return this;
	}
	
	
	
	@Override
	public ArrowShape getEdgeArrowHead() {
		return arrowHead;
	}
	
	public AbstractDotGraphModel withEdgeArrowHead(ArrowShape arrowHead) {
		this.arrowHead = arrowHead;
		return this;
	}
	
	@Override
	public ArrowShape getEdgeArrowTail() {
		return arrowTail;
	}
	
	public AbstractDotGraphModel withEdgeArrowTail(ArrowShape arrowTail) {
		this.arrowTail = arrowTail;
		return this;
	}
	
	@Override
	public EdgeStyle getEdgeStyle() {
		return edgeStyle;
	}
	
	public AbstractDotGraphModel withEdgeStyle(EdgeStyle edgeStyle) {
		this.edgeStyle = edgeStyle;
		return this;
	}
	
	
	
	@Override
	public boolean isNodeVertical(Object node) {
		return true;
	}
	
	@Override
	public String getNodeLabel(Object node) {
		return DotUtil.segmentsToLabel(getNodeSegments(node), isNodeVertical(node));
	}
	
	@Override
	public Collection<?> getNodeSegments(Object node) {
		return null;
	}
	
	@Override
	public NodeStyle getNodeStyle(Object node) {
		return null;
	}
	
	@Override
	public String getNodeFillColor(Object node) {
		return null;
	}
	
	
	
	@Override
	public ArrowShape getEdgeArrowHead(Object edge) {
		return null;
	}
	
	@Override
	public String getEdgeHeadLabel(Object edge) {
		return null;
	}
	
	@Override
	public ArrowShape getEdgeArrowTail(Object edge) {
		return null;
	}
	
	@Override
	public String getEdgeTailLabel(Object edge) {
		return null;
	}
	
	@Override
	public EdgeStyle getEdgeStyle(Object edge) {
		return null;
	}
	
}
