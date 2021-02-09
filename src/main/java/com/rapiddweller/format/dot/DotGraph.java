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
 *
 * @author Volker Bergmann
 * @since 0.8.2
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

  /**
   * New directed graph dot graph.
   *
   * @param name the name
   * @return the dot graph
   */
  public static DotGraph newDirectedGraph(String name) {
    return new DotGraph(name, true);
  }

  @Override
  public String getName() {
    return name;
  }

  /**
   * Is directed boolean.
   *
   * @return the boolean
   */
  public boolean isDirected() {
    return directed;
  }

  /**
   * Gets rank dir.
   *
   * @return the rank dir
   */
  public RankDir getRankDir() {
    return rankDir;
  }

  /**
   * With rank dir dot graph.
   *
   * @param rankDir the rank dir
   * @return the dot graph
   */
  public DotGraph withRankDir(RankDir rankDir) {
    this.rankDir = rankDir;
    return this;
  }

  /**
   * Gets node shape.
   *
   * @return the node shape
   */
  public NodeShape getNodeShape() {
    return nodeShape;
  }

  /**
   * With node shape dot graph.
   *
   * @param shape the shape
   * @return the dot graph
   */
  public DotGraph withNodeShape(NodeShape shape) {
    this.nodeShape = shape;
    return this;
  }

  /**
   * Gets node font size.
   *
   * @return the node font size
   */
  public Integer getNodeFontSize() {
    return nodeFontSize;
  }

  /**
   * With node font size dot graph.
   *
   * @param points the points
   * @return the dot graph
   */
  public DotGraph withNodeFontSize(int points) {
    this.nodeFontSize = points;
    return this;
  }

  /**
   * Gets node style.
   *
   * @return the node style
   */
  public NodeStyle getNodeStyle() {
    return this.nodeStyle;
  }

  /**
   * With node style dot graph.
   *
   * @param style the style
   * @return the dot graph
   */
  public DotGraph withNodeStyle(NodeStyle style) {
    this.nodeStyle = style;
    return this;
  }

  /**
   * Gets node fill color.
   *
   * @return the node fill color
   */
  public String getNodeFillColor() {
    return nodeFillColor;
  }

  /**
   * With node fill color dot graph.
   *
   * @param fillColor the fill color
   * @return the dot graph
   */
  public DotGraph withNodeFillColor(String fillColor) {
    this.nodeFillColor = fillColor;
    return this;
  }


  /**
   * Gets edge style.
   *
   * @return the edge style
   */
  public EdgeStyle getEdgeStyle() {
    return edgeStyle;
  }

  /**
   * With edge style dot graph.
   *
   * @param edgeStyle the edge style
   * @return the dot graph
   */
  public DotGraph withEdgeStyle(EdgeStyle edgeStyle) {
    this.edgeStyle = edgeStyle;
    return this;
  }

  /**
   * Gets edge arrow head.
   *
   * @return the edge arrow head
   */
  public ArrowShape getEdgeArrowHead() {
    return edgeArrowHead;
  }

  /**
   * With arrow head dot graph.
   *
   * @param arrowHead the arrow head
   * @return the dot graph
   */
  public DotGraph withArrowHead(ArrowShape arrowHead) {
    this.edgeArrowHead = arrowHead;
    return this;
  }

  /**
   * Gets edge arrow tail.
   *
   * @return the edge arrow tail
   */
  public ArrowShape getEdgeArrowTail() {
    return edgeArrowTail;
  }

  /**
   * With edge arrow tail dot graph.
   *
   * @param arrowTail the arrow tail
   * @return the dot graph
   */
  public DotGraph withEdgeArrowTail(ArrowShape arrowTail) {
    this.edgeArrowTail = arrowTail;
    return this;
  }

  /**
   * New node dot node.
   *
   * @param name the name
   * @return the dot node
   */
  public DotNode newNode(String name) {
    DotNode node = new DotNode(name);
    nodes.put(node.getName(), node);
    return node;
  }

  /**
   * Gets nodes.
   *
   * @return the nodes
   */
  public List<DotNode> getNodes() {
    return nodes.values();
  }

}
