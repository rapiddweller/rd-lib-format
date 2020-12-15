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
 * Represents a Dot graph.
 * Created: 24.05.2014 18:11:17
 * @see <a href="http://www.graphviz.org/content/dot-language">DOT language spec on graphviz.org</a>
 * @since 0.8.2
 * @author Volker Bergmann
 */

public interface DotGraphModel {
	
	/** @return the name of the graph */
	String getName();
	
	/** Tells if the graph is directed 
	 * @return true if the graph is directed, otherwise false */
	boolean isDirected();
	
	/** @return the graph's rankdir attribute */
	RankDir getRankDir();
	
	/** @return the shape of all graph nodes.
	 *  @see <a href="http://www.graphviz.org/doc/info/shapes.html">Node shapes on graphviz.org</a> */
	NodeShape getNodeShape();
	
	Integer getNodeFontSize();
	
	/** @return the arrow shape of all edge heads of the graph
	 *  @see <a href="http://www.graphviz.org/content/arrow-shapes">Arrow shapes on graphviz.org</a> */
	ArrowShape getEdgeArrowHead();
	
	/** @return the arrow shape of all edge tails of the graph
	 *  @see <a href="http://www.graphviz.org/content/arrow-shapes">Arrow shapes on graphviz.org</a> */
	ArrowShape getEdgeArrowTail();
	
	String getNodeFillColor();
	
	NodeStyle getNodeStyle();
	
	EdgeStyle getEdgeStyle();
	
	
	
	/** @return the number of nodes in the graph */
	int getNodeCount();
	
	/** @param index the index of the requested node
	 * @return the graph node at the given index */
	Object getNode(int index);
	
	String getNodeId(Object node);
	
	boolean isNodeVertical(Object node);
	
	String getNodeLabel(Object node);
	
	Collection<?> getNodeSegments(Object node);
	
	String getNodeFillColor(Object node);
	
	NodeStyle getNodeStyle(Object node);
	
	
	
	int getEdgeCountOfNode(Object sourceNode);
	
	Object getEdgeOfNode(Object sourceNode, int index);
	
	Object getTargetNodeOfEdge(Object sourceNode, Object edge);
	
	ArrowShape getEdgeArrowHead(Object edge);
	
	String getEdgeHeadLabel(Object edge);
	
	ArrowShape getEdgeArrowTail(Object edge);
	
	String getEdgeTailLabel(Object edge);
	
	EdgeStyle getEdgeStyle(Object edge);
	
}
