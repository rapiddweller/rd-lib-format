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

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.io.FileOutputStream;

import com.rapiddweller.common.Encodings;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.format.dot.ArrowShape;
import com.rapiddweller.format.dot.DefaultDotGraphModel;
import com.rapiddweller.format.dot.DotGraph;
import com.rapiddweller.format.dot.DotNode;
import com.rapiddweller.format.dot.DotWriter;
import com.rapiddweller.format.dot.EdgeStyle;
import com.rapiddweller.format.dot.NodeShape;
import com.rapiddweller.format.dot.NodeStyle;
import com.rapiddweller.format.dot.RankDir;

import java.io.OutputStream;

import org.junit.Test;

/**
 * Tests the {@link DotWriter}.
 * Created: 24.05.2014 08:13:11
 *
 * @author Volker Bergmann
 * @since 0.8.2
 */

public class DotWriterTest {

    @Test
    public void test() throws Exception {
        DotGraph graph = DotGraph.newDirectedGraph("geo").withRankDir(RankDir.BT)
                .withNodeShape(NodeShape.record).withNodeFontSize(12).withNodeStyle(NodeStyle.filled).withNodeFillColor("yellow")
                .withArrowHead(ArrowShape.open);
        DotNode country = graph.newNode("country").withSegment("CNTY").withSegment("DE", "FR", "IT");
        DotNode state = graph.newNode("state").withEdgeTo(country).withSegment("STATE");
        DotNode city = graph.newNode("city").withEdgeTo(state);
        city.newEdgeTo(country).withStyle(EdgeStyle.dotted);
        DefaultDotGraphModel model = new DefaultDotGraphModel(graph);
        DotWriter.persist(model, new FileOutputStream("target/geo.dot"), Encodings.UTF_8);
        String actual = IOUtil.getContentOfURI("target/geo.dot");
        String expected = IOUtil.getContentOfURI("com/rapiddweller/format/dot/geo.dot");
        assertEquals(expected, actual);
    }

    @Test
    public void testAttributesWriterAdd() {
        DotWriter.AttributesWriter attributesWriter = new DotWriter.AttributesWriter();
        assertSame(attributesWriter, attributesWriter.add("Name", "value"));
    }

    @Test
    public void testAttributesWriterAdd2() {
        DotWriter.AttributesWriter attributesWriter = new DotWriter.AttributesWriter();
        assertSame(attributesWriter, attributesWriter.add("Name", null));
    }

    @Test
    public void testAttributesWriterConstructor() {
        DotWriter.AttributesWriter actualAttributesWriter = new DotWriter.AttributesWriter();
        assertTrue(actualAttributesWriter.map.isEmpty());
        assertNull(actualAttributesWriter.label);
    }

    @Test
    public void testAttributesWriterConstructor2() {
        DotWriter.AttributesWriter actualAttributesWriter = new DotWriter.AttributesWriter("Label");
        assertTrue(actualAttributesWriter.map.isEmpty());
        assertEquals("Label", actualAttributesWriter.label);
    }

    @Test
    public void testPersist() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        DefaultDotGraphModel model = new DefaultDotGraphModel(DotGraph.newDirectedGraph("Name"));
        DotWriter.persist(model, OutputStream.nullOutputStream());
    }

    @Test
    public void testPersist2() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        DefaultDotGraphModel model = new DefaultDotGraphModel(DotGraph.newDirectedGraph(""));
        DotWriter.persist(model, OutputStream.nullOutputStream());
    }

    @Test
    public void testPersist3() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        DefaultDotGraphModel model = new DefaultDotGraphModel(DotGraph.newDirectedGraph("Name"));
        DotWriter.persist(model, OutputStream.nullOutputStream(), "UTF-8");
    }

    @Test
    public void testPersist4() {
        // TODO: This test is incomplete.
        //   Reason: No meaningful assertions found.
        //   To help Diffblue Cover to find assertions, please add getters to the
        //   class under test that return fields written by the method under test.
        //   See https://diff.blue/R004

        DefaultDotGraphModel model = new DefaultDotGraphModel(DotGraph.newDirectedGraph(""));
        DotWriter.persist(model, OutputStream.nullOutputStream(), "UTF-8");
    }

    @Test
    public void testPersist5() {
        DefaultDotGraphModel model = new DefaultDotGraphModel(DotGraph.newDirectedGraph("Name"));
        assertThrows(UnsupportedOperationException.class,
                () -> DotWriter.persist(model, OutputStream.nullOutputStream(), "digraph"));
    }

}
