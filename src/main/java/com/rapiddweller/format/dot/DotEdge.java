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
 *
 * @author Volker Bergmann
 * @since 0.8.2
 */
public class DotEdge {

  private final DotNode from;
  private final DotNode to;
  private ArrowShape arrowHead;
  private String headLabel;
  private ArrowShape arrowTail;
  private String tailLabel;
  private EdgeStyle style;

  /**
   * Instantiates a new Dot edge.
   *
   * @param from the from
   * @param to   the to
   */
  public DotEdge(DotNode from, DotNode to) {
    this.from = from;
    this.arrowHead = null;
    this.to = to;
    this.arrowTail = null;
    this.style = null;
  }

  /**
   * Gets from.
   *
   * @return the from
   */
  public DotNode getFrom() {
    return from;
  }

  /**
   * Gets to.
   *
   * @return the to
   */
  public DotNode getTo() {
    return to;
  }

  /**
   * Gets arrow head.
   *
   * @return the arrow head
   */
  public ArrowShape getArrowHead() {
    return arrowHead;
  }

  /**
   * With arrow head dot edge.
   *
   * @param arrowHead the arrow head
   * @return the dot edge
   */
  public DotEdge withArrowHead(ArrowShape arrowHead) {
    this.arrowHead = arrowHead;
    return this;
  }

  /**
   * Gets arrow tail.
   *
   * @return the arrow tail
   */
  public ArrowShape getArrowTail() {
    return arrowTail;
  }

  /**
   * With arrow tail dot edge.
   *
   * @param arrowTail the arrow tail
   * @return the dot edge
   */
  public DotEdge withArrowTail(ArrowShape arrowTail) {
    this.arrowTail = arrowTail;
    return this;
  }

  /**
   * Gets head label.
   *
   * @return the head label
   */
  public String getHeadLabel() {
    return headLabel;
  }

  /**
   * With head label dot edge.
   *
   * @param headLabel the head label
   * @return the dot edge
   */
  public DotEdge withHeadLabel(String headLabel) {
    this.headLabel = headLabel;
    return this;
  }

  /**
   * Gets tail label.
   *
   * @return the tail label
   */
  public String getTailLabel() {
    return tailLabel;
  }

  /**
   * With tail label dot edge.
   *
   * @param tailLabel the tail label
   * @return the dot edge
   */
  public DotEdge withTailLabel(String tailLabel) {
    this.tailLabel = tailLabel;
    return this;
  }

  /**
   * Gets style.
   *
   * @return the style
   */
  public EdgeStyle getStyle() {
    return style;
  }

  /**
   * With style dot edge.
   *
   * @param style the style
   * @return the dot edge
   */
  public DotEdge withStyle(EdgeStyle style) {
    this.style = style;
    return this;
  }

}
