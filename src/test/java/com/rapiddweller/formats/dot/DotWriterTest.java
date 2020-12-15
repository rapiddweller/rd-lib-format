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

import static org.junit.Assert.*;

import java.io.FileOutputStream;

import com.rapiddweller.commons.Encodings;
import com.rapiddweller.commons.IOUtil;
import com.rapiddweller.formats.dot.ArrowShape;
import com.rapiddweller.formats.dot.DefaultDotGraphModel;
import com.rapiddweller.formats.dot.DotGraph;
import com.rapiddweller.formats.dot.DotNode;
import com.rapiddweller.formats.dot.DotWriter;
import com.rapiddweller.formats.dot.EdgeStyle;
import com.rapiddweller.formats.dot.NodeShape;
import com.rapiddweller.formats.dot.NodeStyle;
import com.rapiddweller.formats.dot.RankDir;
import org.junit.Test;

/**
 * Tests the {@link DotWriter}.
 * Created: 24.05.2014 08:13:11
 * @since 0.8.2
 * @author Volker Bergmann
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
		String expected = IOUtil.getContentOfURI("com/rapiddweller/formats/dot/geo.dot");
		assertEquals(expected, actual);
	}
	
}
