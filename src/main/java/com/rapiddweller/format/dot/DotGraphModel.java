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
 * Represents a Dot graph.
 * Created: 24.05.2014 18:11:17
 *
 * @author Volker Bergmann
 * @see <a href="http://www.graphviz.org/content/dot-language">DOT language spec on graphviz.org</a>
 * @since 0.8.2
 */
public interface DotGraphModel {

  /**
   * Gets name.
   *
   * @return the name of the graph
   */
  String getName();

  /**
   * Tells if the graph is directed
   *
   * @return true if the graph is directed, otherwise false
   */
  boolean isDirected();

  /**
   * Gets rank dir.
   *
   * @return the graph's rankdir attribute
   */
  RankDir getRankDir();

  /**
   * Gets node shape.
   *
   * @return the shape of all graph nodes.
   * @see <a href="http://www.graphviz.org/doc/info/shapes.html">Node shapes on graphviz.org</a>
   */
  NodeShape getNodeShape();

  /**
   * Gets node font size.
   *
   * @return the node font size
   */
  Integer getNodeFontSize();

  /**
   * Gets edge arrow head.
   *
   * @return the arrow shape of all edge heads of the graph
   * @see <a href="http://www.graphviz.org/content/arrow-shapes">Arrow shapes on graphviz.org</a>
   */
  ArrowShape getEdgeArrowHead();

  /**
   * Gets edge arrow tail.
   *
   * @return the arrow shape of all edge tails of the graph
   * @see <a href="http://www.graphviz.org/content/arrow-shapes">Arrow shapes on graphviz.org</a>
   */
  ArrowShape getEdgeArrowTail();

  /**
   * Gets node fill color.
   *
   * @return the node fill color
   */
  String getNodeFillColor();

  /**
   * Gets node style.
   *
   * @return the node style
   */
  NodeStyle getNodeStyle();

  /**
   * Gets edge style.
   *
   * @return the edge style
   */
  EdgeStyle getEdgeStyle();


  /**
   * Gets node count.
   *
   * @return the number of nodes in the graph
   */
  int getNodeCount();

  /**
   * Gets node.
   *
   * @param index the index of the requested node
   * @return the graph node at the given index
   */
  Object getNode(int index);

  /**
   * Gets node id.
   *
   * @param node the node
   * @return the node id
   */
  String getNodeId(Object node);

  /**
   * Is node vertical boolean.
   *
   * @param node the node
   * @return the boolean
   */
  boolean isNodeVertical(Object node);

  /**
   * Gets node label.
   *
   * @param node the node
   * @return the node label
   */
  String getNodeLabel(Object node);

  /**
   * Gets node segments.
   *
   * @param node the node
   * @return the node segments
   */
  Collection<?> getNodeSegments(Object node);

  /**
   * Gets node fill color.
   *
   * @param node the node
   * @return the node fill color
   */
  String getNodeFillColor(Object node);

  /**
   * Gets node style.
   *
   * @param node the node
   * @return the node style
   */
  NodeStyle getNodeStyle(Object node);


  /**
   * Gets edge count of node.
   *
   * @param sourceNode the source node
   * @return the edge count of node
   */
  int getEdgeCountOfNode(Object sourceNode);

  /**
   * Gets edge of node.
   *
   * @param sourceNode the source node
   * @param index      the index
   * @return the edge of node
   */
  Object getEdgeOfNode(Object sourceNode, int index);

  /**
   * Gets target node of edge.
   *
   * @param sourceNode the source node
   * @param edge       the edge
   * @return the target node of edge
   */
  Object getTargetNodeOfEdge(Object sourceNode, Object edge);

  /**
   * Gets edge arrow head.
   *
   * @param edge the edge
   * @return the edge arrow head
   */
  ArrowShape getEdgeArrowHead(Object edge);

  /**
   * Gets edge head label.
   *
   * @param edge the edge
   * @return the edge head label
   */
  String getEdgeHeadLabel(Object edge);

  /**
   * Gets edge arrow tail.
   *
   * @param edge the edge
   * @return the edge arrow tail
   */
  ArrowShape getEdgeArrowTail(Object edge);

  /**
   * Gets edge tail label.
   *
   * @param edge the edge
   * @return the edge tail label
   */
  String getEdgeTailLabel(Object edge);

  /**
   * Gets edge style.
   *
   * @param edge the edge
   * @return the edge style
   */
  EdgeStyle getEdgeStyle(Object edge);

}
