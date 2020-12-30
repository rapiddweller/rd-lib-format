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

import java.util.Collection;

/**
 * Default implementation of a {@link DotGraphModel}, making use of {@link DotGraph}, 
 * {@link DotNode}s and {@link DotEdge}s.
 * Created: 24.05.2014 18:17:07
 * @since 0.8.2
 * @author Volker Bergmann
 */

public class DefaultDotGraphModel implements DotGraphModel {
	
	private final DotGraph graph;
	
	public DefaultDotGraphModel(DotGraph graph) {
		this.graph = graph;
	}

	@Override
	public String getName() {
		return graph.getName();
	}

	@Override
	public boolean isDirected() {
		return graph.isDirected();
	}

	@Override
	public RankDir getRankDir() {
		return graph.getRankDir();
	}

	@Override
	public NodeShape getNodeShape() {
		return graph.getNodeShape();
	}

	@Override
	public Integer getNodeFontSize() {
		return graph.getNodeFontSize();
	}
	
	@Override
	public NodeStyle getNodeStyle() {
		return graph.getNodeStyle();
	}
	
	@Override
	public String getNodeFillColor() {
		return graph.getNodeFillColor();
	}

	@Override
	public ArrowShape getEdgeArrowHead() {
		return graph.getEdgeArrowHead();
	}

	@Override
	public ArrowShape getEdgeArrowTail() {
		return graph.getEdgeArrowTail();
	}
	
	@Override
	public EdgeStyle getEdgeStyle() {
		return graph.getEdgeStyle();
	}
	
	

	@Override
	public int getNodeCount() {
		return graph.getNodes().size();
	}

	@Override
	public Object getNode(int index) {
		return graph.getNodes().get(index);
	}

	@Override
	public String getNodeId(Object node) {
		return ((DotNode) node).getName();
	}
	
	@Override
	public boolean isNodeVertical(Object node) {
		return ((DotNode) node).isVertical();
	}

	@Override
	public String getNodeLabel(Object node) {
		return DotUtil.segmentsToLabel(getNodeSegments(node), isNodeVertical(node));
	}

	@Override
	public Collection<?> getNodeSegments(Object node) {
		return ((DotNode) node).getSegments();
	}

	@Override
	public String getNodeFillColor(Object node) {
		return ((DotNode) node).getFillColor();
	}

	@Override
	public NodeStyle getNodeStyle(Object node) {
		return ((DotNode) node).getStyle();
	}
	
	
	
	@Override
	public int getEdgeCountOfNode(Object node) {
		return ((DotNode) node).getEdges().size();
	}

	@Override
	public Object getEdgeOfNode(Object node, int index) {
		return ((DotNode) node).getEdges().get(index);
	}

	@Override
	public Object getTargetNodeOfEdge(Object sourceNode, Object edge) {
		return ((DotEdge) edge).getTo();
	}

	@Override
	public ArrowShape getEdgeArrowHead(Object edge) {
		return ((DotEdge) edge).getArrowHead();
	}

	@Override
	public String getEdgeHeadLabel(Object edge) {
		return ((DotEdge) edge).getHeadLabel();
	}

	@Override
	public ArrowShape getEdgeArrowTail(Object edge) {
		return ((DotEdge) edge).getArrowTail();
	}

	@Override
	public String getEdgeTailLabel(Object edge) {
		return ((DotEdge) edge).getTailLabel();
	}

	@Override
	public EdgeStyle getEdgeStyle(Object edge) {
		return ((DotEdge) edge).getStyle();
	}
	
}
