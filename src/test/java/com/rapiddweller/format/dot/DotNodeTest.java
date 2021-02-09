package com.rapiddweller.format.dot;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * The type Dot node test.
 */
public class DotNodeTest {
  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    DotNode actualDotNode = new DotNode("Name");
    assertNull(actualDotNode.getStyle());
    assertNull(actualDotNode.getFillColor());
    assertTrue(actualDotNode.isVertical());
    assertEquals("Name", actualDotNode.toString());
  }

  /**
   * Test with style.
   */
  @Test
  public void testWithStyle() {
    DotNode dotNode = new DotNode("Name");
    DotNode actualWithStyleResult = dotNode.withStyle(NodeStyle.solid);
    assertSame(dotNode, actualWithStyleResult);
    assertEquals(NodeStyle.solid, actualWithStyleResult.getStyle());
  }

  /**
   * Test new edge to.
   */
  @Test
  public void testNewEdgeTo() {
    DotNode dotNode = new DotNode("Name");
    DotNode dotNode1 = new DotNode("Name");
    DotEdge actualNewEdgeToResult = dotNode.newEdgeTo(dotNode1);
    assertNull(actualNewEdgeToResult.getStyle());
    assertNull(actualNewEdgeToResult.getArrowHead());
    DotNode from = actualNewEdgeToResult.getFrom();
    assertSame(dotNode, from);
    assertNull(actualNewEdgeToResult.getArrowTail());
    assertSame(dotNode1, actualNewEdgeToResult.getTo());
    assertEquals(1, from.getEdges().size());
  }

  /**
   * Test with edge to.
   */
  @Test
  public void testWithEdgeTo() {
    DotNode dotNode = new DotNode("Name");
    assertSame(dotNode, dotNode.withEdgeTo(new DotNode("Name")));
  }

  /**
   * Test with segment.
   */
  @Test
  public void testWithSegment() {
    DotNode dotNode = new DotNode("Name");
    assertSame(dotNode, dotNode.withSegment("foo", "foo", "foo"));
  }

  /**
   * Test to string.
   */
  @Test
  public void testToString() {
    assertEquals("Name", (new DotNode("Name")).toString());
  }
}

