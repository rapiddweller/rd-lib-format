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

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node in a Dot graph.
 * Created: 24.05.2014 07:36:36
 *
 * @author Volker Bergmann
 * @since 0.8.2
 */
public class DotNode implements Named {

  private final String name;
  private final boolean vertical;
  private NodeStyle style;
  private final String fillColor;
  private final List<String> segments;
  private final List<DotEdge> edges;

  /**
   * Instantiates a new Dot node.
   *
   * @param name the name
   */
  public DotNode(String name) {
    this.name = name;
    this.vertical = true;
    this.style = null;
    this.fillColor = null;
    this.segments = new ArrayList<>();
    this.edges = new ArrayList<>();
  }

  @Override
  public String getName() {
    return name;
  }

  /**
   * Is vertical boolean.
   *
   * @return the boolean
   */
  public boolean isVertical() {
    return vertical;
  }

  /**
   * Gets style.
   *
   * @return the style
   */
  public NodeStyle getStyle() {
    return style;
  }

  /**
   * With style dot node.
   *
   * @param style the style
   * @return the dot node
   */
  public DotNode withStyle(NodeStyle style) {
    this.style = style;
    return this;
  }

  /**
   * Gets fill color.
   *
   * @return the fill color
   */
  public String getFillColor() {
    return fillColor;
  }

  /**
   * New edge to dot edge.
   *
   * @param target the target
   * @return the dot edge
   */
  public DotEdge newEdgeTo(DotNode target) {
    DotEdge edge = new DotEdge(this, target);
    edges.add(edge);
    return edge;
  }

  /**
   * Gets edges.
   *
   * @return the edges
   */
  public List<DotEdge> getEdges() {
    return edges;
  }

  /**
   * With edge to dot node.
   *
   * @param target the target
   * @return the dot node
   */
  public DotNode withEdgeTo(DotNode target) {
    newEdgeTo(target);
    return this;
  }

  /**
   * Gets segments.
   *
   * @return the segments
   */
  public List<String> getSegments() {
    return segments;
  }

  /**
   * With segment dot node.
   *
   * @param lines the lines
   * @return the dot node
   */
  public DotNode withSegment(String... lines) {
    StringBuilder segment = new StringBuilder();
    for (String line : lines) {
      segment.append(line).append("\\l");
    }
    segments.add(segment.toString());
    return this;
  }

  @Override
  public String toString() {
    return getName();
  }

}
