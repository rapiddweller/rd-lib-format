package com.rapiddweller.format.html.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * The type Html element test.
 */
public class HtmlElementTest {
  /**
   * Test with class.
   */
  @Test
  public void testWithClass() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    assertSame(htmlElement, htmlElement.withClass("Klass"));
  }

  /**
   * Test with style.
   */
  @Test
  public void testWithStyle() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    assertSame(htmlElement, htmlElement.withStyle("Style"));
  }

  /**
   * Test with title.
   */
  @Test
  public void testWithTitle() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    assertSame(htmlElement, htmlElement.withTitle("Dr"));
  }

  /**
   * Test with align.
   */
  @Test
  public void testWithAlign() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    assertSame(htmlElement, htmlElement.withAlign("Align"));
  }

  /**
   * Test with valign.
   */
  @Test
  public void testWithValign() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    assertSame(htmlElement, htmlElement.withValign("Valign"));
  }

  /**
   * Test with attribute.
   */
  @Test
  public void testWithAttribute() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    assertSame(htmlElement, htmlElement.withAttribute("Attribute Name", "Attribute Value"));
  }

  /**
   * Test add break.
   */
  @Test
  public void testAddBreak() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    assertSame(htmlElement, htmlElement.addBreak());
  }

  /**
   * Test with raw text content.
   */
  @Test
  public void testWithRawTextContent() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    assertSame(htmlElement, htmlElement.withRawTextContent("Text"));
  }

  /**
   * Test with text content.
   */
  @Test
  public void testWithTextContent() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    assertSame(htmlElement, htmlElement.withTextContent("Text", true, true));
  }

  /**
   * Test with components.
   */
  @Test
  public void testWithComponents() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    HtmlComponent htmlComponent = new HtmlComponent();
    HtmlComponent htmlComponent1 = new HtmlComponent();
    assertSame(htmlElement, htmlElement.withComponents(htmlComponent, htmlComponent1, new HtmlComponent()));
  }

  /**
   * Test set components.
   */
  @Test
  public void testSetComponents() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    HtmlComponent htmlComponent = new HtmlComponent();
    HtmlComponent htmlComponent1 = new HtmlComponent();
    htmlElement.setComponents(htmlComponent, htmlComponent1, new HtmlComponent());
    assertEquals("<Tag Name></Tag Name>", htmlElement.toString());
  }

  /**
   * Test add component.
   */
  @Test
  public void testAddComponent() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    assertSame(htmlElement, htmlElement.addComponent(new HtmlComponent()));
  }

  /**
   * Test add component 2.
   */
  @Test
  public void testAddComponent2() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    assertSame(htmlElement, htmlElement.addComponent("Text To Add"));
  }

  /**
   * Test format start tag.
   */
  @Test
  public void testFormatStartTag() {
    assertEquals("<Tag Name>", (new HtmlElement<HtmlElement<?>>("Tag Name", true)).formatStartTag());
    assertEquals("<Tag Name>\n", (new HtmlElement<HtmlElement<?>>("Tag Name", false)).formatStartTag());
  }

  /**
   * Test format atomic tag.
   */
  @Test
  public void testFormatAtomicTag() {
    assertEquals("<Tag Name/>", (new HtmlElement<HtmlElement<?>>("Tag Name", true)).formatAtomicTag());
    assertEquals("<Tag Name/>\n", (new HtmlElement<HtmlElement<?>>("Tag Name", false)).formatAtomicTag());
  }

  /**
   * Test format attributes.
   */
  @Test
  public void testFormatAttributes() {
    // TODO: This test is incomplete.
    //   Reason: No meaningful assertions found.
    //   To help Diffblue Cover to find assertions, please add getters to the
    //   class under test that return fields written by the method under test.
    //   See https://diff.blue/R004

    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    htmlElement.formatAttributes(new StringBuilder());
  }

  /**
   * Test format components.
   */
  @Test
  public void testFormatComponents() {
    assertEquals("", (new HtmlElement<HtmlElement<?>>("Tag Name", true)).formatComponents());
  }

  /**
   * Test format components 2.
   */
  @Test
  public void testFormatComponents2() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    htmlElement.addComponent(new HtmlComponent());
    assertEquals("", htmlElement.formatComponents());
  }

  /**
   * Test format components 3.
   */
  @Test
  public void testFormatComponents3() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    htmlElement.addComponent(new Break());
    assertEquals("<br/>\n", htmlElement.formatComponents());
  }

  /**
   * Test format components 4.
   */
  @Test
  public void testFormatComponents4() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    htmlElement.addComponent(new CssStyle());
    assertEquals("<style type=\"text/css\"/>\n", htmlElement.formatComponents());
  }

  /**
   * Test format components 5.
   */
  @Test
  public void testFormatComponents5() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    htmlElement.addComponent(new Div(true));
    assertEquals("<div/>", htmlElement.formatComponents());
  }

  /**
   * Test format components 6.
   */
  @Test
  public void testFormatComponents6() {
    Break resultBreak = new Break();
    resultBreak.addComponent(new HtmlComponent());

    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    htmlElement.addComponent(resultBreak);
    assertEquals("<br>\n\n</br>\n", htmlElement.formatComponents());
  }

  /**
   * Test format components 7.
   */
  @Test
  public void testFormatComponents7() {
    CssStyle cssStyle = new CssStyle();
    cssStyle.addComponent(new HtmlComponent());

    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    htmlElement.addComponent(cssStyle);
    assertEquals("<style type=\"text/css\">\n\n</style>\n", htmlElement.formatComponents());
  }

  /**
   * Test format components 8.
   */
  @Test
  public void testFormatComponents8() {
    Div div = new Div(true);
    div.addComponent(new HtmlComponent());

    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    htmlElement.addComponent(div);
    assertEquals("<div></div>", htmlElement.formatComponents());
  }

  /**
   * Test format end tag.
   */
  @Test
  public void testFormatEndTag() {
    assertEquals("</Tag Name>", (new HtmlElement<HtmlElement<?>>("Tag Name", true)).formatEndTag());
    assertEquals("\n</Tag Name>\n", (new HtmlElement<HtmlElement<?>>("Tag Name", false)).formatEndTag());
  }

  /**
   * Test to string.
   */
  @Test
  public void testToString() {
    assertEquals("<Tag Name/>", (new HtmlElement<HtmlElement<?>>("Tag Name", true)).toString());
    assertEquals("<Tag Name/>\n", (new HtmlElement<HtmlElement<?>>("Tag Name", false)).toString());
  }

  /**
   * Test to string 2.
   */
  @Test
  public void testToString2() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    htmlElement.addComponent(new HtmlComponent());
    assertEquals("<Tag Name></Tag Name>", htmlElement.toString());
  }

  /**
   * Test to string 3.
   */
  @Test
  public void testToString3() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", false);
    htmlElement.addComponent(new HtmlComponent());
    assertEquals("<Tag Name>\n\n</Tag Name>\n", htmlElement.toString());
  }

  /**
   * Test to string 4.
   */
  @Test
  public void testToString4() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    htmlElement.addComponent(new Break());
    assertEquals("<Tag Name><br/>\n</Tag Name>", htmlElement.toString());
  }

  /**
   * Test to string 5.
   */
  @Test
  public void testToString5() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    htmlElement.addComponent(new CssStyle());
    assertEquals("<Tag Name><style type=\"text/css\"/>\n</Tag Name>", htmlElement.toString());
  }

  /**
   * Test to string 6.
   */
  @Test
  public void testToString6() {
    CssStyle cssStyle = new CssStyle();
    cssStyle.addComponent(new Break());
    assertEquals("<style type=\"text/css\">\n<br/>\n\n</style>\n", cssStyle.toString());
  }

  /**
   * Test add components.
   */
  @Test
  public void testAddComponents() {
    HtmlElement<HtmlElement<?>> htmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    HtmlComponent htmlComponent = new HtmlComponent();
    HtmlComponent htmlComponent1 = new HtmlComponent();
    htmlElement.addComponents(htmlComponent, htmlComponent1, new HtmlComponent());
    assertEquals("<Tag Name></Tag Name>", htmlElement.toString());
  }

  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    HtmlElement<HtmlElement<?>> actualHtmlElement = new HtmlElement<HtmlElement<?>>("Tag Name", true);
    assertEquals("<Tag Name/>", actualHtmlElement.toString());
    assertTrue(actualHtmlElement.attributes.isEmpty());
    assertTrue(actualHtmlElement.isInline());
    assertEquals("Tag Name", actualHtmlElement.getTagName());
  }
}

