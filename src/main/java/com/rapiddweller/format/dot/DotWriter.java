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

import com.rapiddweller.common.Encodings;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.collection.OrderedNameMap;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Writes Dot files.
 * Created: 24.05.2014 09:29:45
 *
 * @author Volker Bergmann
 * @since 0.8.2
 */
public class DotWriter {

  /**
   * Persist.
   *
   * @param model the model
   * @param os    the os
   */
  public static void persist(DotGraphModel model, OutputStream os) {
    persist(model, os, Encodings.UTF_8);
  }

  /**
   * Persist.
   *
   * @param model    the model
   * @param os       the os
   * @param encoding the encoding
   */
  public static void persist(DotGraphModel model, OutputStream os, String encoding) {
    PrintWriter out = null;
    try {
      out = new PrintWriter(new OutputStreamWriter(os, encoding));
      // header
      out.print(model.isDirected() ? "digraph" : "graph");
      if (!StringUtil.isEmpty(model.getName())) {
        out.print(" " + model.getName());
      }
      out.println(" {");

      // graph attributes
      new AttributesWriter("graph")
          .add("charset", encoding)
          .add("rankdir", model.getRankDir())
          .write(out);

      // node attributes
      new AttributesWriter("node")
          .add("shape", model.getNodeShape())
          .add("fontsize", model.getNodeFontSize())
          .add("style", model.getNodeStyle())
          .add("fillcolor", DotUtil.normalizeColor(model.getNodeFillColor()))
          .write(out);

      // edge attributes
      new AttributesWriter("edge")
          .add("arrowhead", model.getEdgeArrowHead())
          .add("arrowtail", model.getEdgeArrowTail())
          .add("style", model.getEdgeStyle())
          .write(out);

      // persist nodes
      for (int i = 0; i < model.getNodeCount(); i++) {
        Object node = model.getNode(i);
        writeNode(node, model, out);
      }

      // persist edges
      for (int i = 0; i < model.getNodeCount(); i++) {
        Object node = model.getNode(i);
        writeEdges(node, model, out);
      }

      // end
      out.println("}");
    } catch (UnsupportedEncodingException e) {
      throw new UnsupportedOperationException("Not a supported encoding: " + encoding, e);
    } finally {
      IOUtil.close(out);
    }
  }

  private static void writeNode(Object node, DotGraphModel model, PrintWriter out) {
    out.print("\t" + model.getNodeId(node));
    String label = model.getNodeLabel(node);
    if (label != null) {
      label = '"' + label + '"';
    }
    new AttributesWriter()
        .add("label", label)
        .add("fillcolor", DotUtil.normalizeColor(model.getNodeFillColor(node)))
        .write(out);
  }

  private static void writeEdges(Object node, DotGraphModel model, PrintWriter out) {
    for (int i = 0; i < model.getEdgeCountOfNode(node); i++) {
      Object edge = model.getEdgeOfNode(node, i);
      writeEdge(node, edge, model, out);
    }
  }

  private static void writeEdge(Object sourceNode, Object edge, DotGraphModel model, PrintWriter out) {
    Object target = model.getTargetNodeOfEdge(sourceNode, edge);
    out.print("\t" + model.getNodeId(sourceNode) + " " + (model.isDirected() ? "->" : "--") + " " + model.getNodeId(target));
    new AttributesWriter()
        .add("arrowhead", model.getEdgeArrowHead(edge))
        .add("headlabel", model.getEdgeHeadLabel(edge))
        .add("arrowtail", model.getEdgeArrowTail(edge))
        .add("taillabel", model.getEdgeTailLabel(edge))
        .add("style", model.getEdgeStyle(edge))
        .write(out);
  }

  /**
   * The type Attributes writer.
   */
  static class AttributesWriter {
    /**
     * The Label.
     */
    String label;
    /**
     * The Map.
     */
    OrderedNameMap<Object> map;

    /**
     * Instantiates a new Attributes writer.
     */
    public AttributesWriter() {
      this(null);
    }

    /**
     * Instantiates a new Attributes writer.
     *
     * @param label the label
     */
    public AttributesWriter(String label) {
      this.label = label;
      this.map = new OrderedNameMap<>();
    }

    /**
     * Add attributes writer.
     *
     * @param name  the name
     * @param value the value
     * @return the attributes writer
     */
    public AttributesWriter add(String name, Object value) {
      if (value != null) {
        map.put(name, value);
      }
      return this;
    }

    /**
     * Write.
     *
     * @param out the out
     */
    public void write(PrintWriter out) {
      if (!map.isEmpty()) {
        if (label != null) {
          out.print("\t" + label);
        }
        out.print(" [");
        boolean first = true;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
          if (!first) {
            out.print(", ");
          }
          out.print(entry.getKey() + "=" + entry.getValue());
          first = false;
        }
        out.print("]");
      }
      if (label == null || !map.isEmpty()) {
        out.println(";");
      }
    }
  }

}
