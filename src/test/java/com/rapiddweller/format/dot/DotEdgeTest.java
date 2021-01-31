package com.rapiddweller.format.dot;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class DotEdgeTest {
    @Test
    public void testWithArrowHead() {
        DotNode from = new DotNode("Name");
        DotEdge dotEdge = new DotEdge(from, new DotNode("Name"));
        DotEdge actualWithArrowHeadResult = dotEdge.withArrowHead(ArrowShape.none);
        assertSame(dotEdge, actualWithArrowHeadResult);
        assertEquals(ArrowShape.none, actualWithArrowHeadResult.getArrowHead());
    }

    @Test
    public void testWithArrowTail() {
        DotNode from = new DotNode("Name");
        DotEdge dotEdge = new DotEdge(from, new DotNode("Name"));
        DotEdge actualWithArrowTailResult = dotEdge.withArrowTail(ArrowShape.none);
        assertSame(dotEdge, actualWithArrowTailResult);
        assertEquals(ArrowShape.none, actualWithArrowTailResult.getArrowTail());
    }

    @Test
    public void testWithHeadLabel() {
        DotNode from = new DotNode("Name");
        DotEdge dotEdge = new DotEdge(from, new DotNode("Name"));
        DotEdge actualWithHeadLabelResult = dotEdge.withHeadLabel("Head Label");
        assertSame(dotEdge, actualWithHeadLabelResult);
        assertEquals("Head Label", actualWithHeadLabelResult.getHeadLabel());
    }

    @Test
    public void testWithTailLabel() {
        DotNode from = new DotNode("Name");
        DotEdge dotEdge = new DotEdge(from, new DotNode("Name"));
        DotEdge actualWithTailLabelResult = dotEdge.withTailLabel("Tail Label");
        assertSame(dotEdge, actualWithTailLabelResult);
        assertEquals("Tail Label", actualWithTailLabelResult.getTailLabel());
    }

    @Test
    public void testWithStyle() {
        DotNode from = new DotNode("Name");
        DotEdge dotEdge = new DotEdge(from, new DotNode("Name"));
        DotEdge actualWithStyleResult = dotEdge.withStyle(EdgeStyle.solid);
        assertSame(dotEdge, actualWithStyleResult);
        assertEquals(EdgeStyle.solid, actualWithStyleResult.getStyle());
    }
}

