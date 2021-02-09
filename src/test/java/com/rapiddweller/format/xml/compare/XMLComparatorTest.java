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
package com.rapiddweller.format.xml.compare;

import com.rapiddweller.common.converter.XMLNode2StringConverter;
import com.rapiddweller.common.xml.XMLUtil;
import com.rapiddweller.common.xml.XPathUtil;
import com.rapiddweller.format.compare.AggregateDiff;
import com.rapiddweller.format.compare.DiffDetail;
import com.rapiddweller.format.compare.DiffFactory;
import org.junit.Test;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link XmlContentImpl}
 *
 * @author Volker Bergmann
 * @since 1.0.5
 */
@SuppressWarnings("javadoc")
public class XMLComparatorTest {

    private static final String RESOURCE_PATH = XMLComparatorTest.class.getPackage().getName().replace('.', '/') + "/";
    
    private static final String SIMPLE_XML_PATH = RESOURCE_PATH + "simple.xml";
	private static final String SIMPLE_XML_ISO_8859_1_PATH = RESOURCE_PATH + "simple-iso-8859-1.xml";
    private static final String COMMENTED_XML_PATH = RESOURCE_PATH + "commented.xml";
    private static final String ESCAPED_XML_PATH = RESOURCE_PATH + "escaped.xml";
    private static final String NS_XZ_PATH = RESOURCE_PATH + "namespace_xz.xml";
    private static final String NS_XPZP_PATH = RESOURCE_PATH + "namespace_xpzp.xml";
    private static final String NS_ZX_PATH = RESOURCE_PATH + "namespace_zx.xml";
    private static final String NS_NONE_PATH = RESOURCE_PATH + "namespace_none.xml";

	private final DiffFactory diffFactory = new DiffFactory(new XMLNode2StringConverter());

  /**
   * Test diff identical.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_identical() throws Exception {
        Document doc = parseSimpleXmlUtf8();
        assertTrue(new XMLComparator().compare(doc, doc).isEmpty());
    }

    // test encoding -----------------------------------------------------------

  /**
   * Test diff unexpected encoding.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_unexpectedEncoding() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = parseSimpleXmlIso_8859_1();
        XMLComparisonSettings settings = new XMLComparisonSettings();
        settings.setEncodingRelevant(true);
		AggregateDiff diff = new XMLComparator(settings).compare(expected, actual);
        assertEquals(1, diff.getDetails().size());
        assertEquals(diffFactory.different("utf-8", "iso-8859-1", "document encoding", "/", "/"), diff.getDetails().get(0));
    }

  /**
   * Test diff unexpected encoding tolerated.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_unexpectedEncodingTolerated() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = parseSimpleXmlIso_8859_1();
        XMLComparisonSettings settings = new XMLComparisonSettings();
        settings.setEncodingRelevant(false);
		AggregateDiff diff = new XMLComparator(settings).compare(expected, actual);
        assertTrue("Unexpected diff", diff.isEmpty());
    }

  /**
   * Test diff special elements ignored.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_specialElementsIgnored() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = XMLUtil.parse(RESOURCE_PATH + "special.xml");
        XMLComparisonSettings settings = new XMLComparisonSettings();
        settings.setProcessingInstructionRelevant(false);
        settings.setCdataRelevant(false);
		AggregateDiff diff = new XMLComparator(settings).compare(expected, actual);
        assertTrue("Unexpected diff", diff.isEmpty());
    }

  /**
   * Test diff unexpected processing instruction.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_unexpectedProcessingInstruction() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = XMLUtil.parse(RESOURCE_PATH + "special.xml");
        ProcessingInstruction pi = (ProcessingInstruction) XPathUtil.queryNode(actual, "/root/processing-instruction('procint')");
        XMLComparisonSettings settings = new XMLComparisonSettings();
        settings.setProcessingInstructionRelevant(true);
        settings.setCdataRelevant(false);
		AggregateDiff diff = new XMLComparator(settings).compare(expected, actual);
        assertEquals(1, diff.getDetails().size());
        assertEquals(diffFactory.unexpected(pi, "processing instruction", "/root/?procint"), diff.getDetails().get(0));
    }

  /**
   * Test diff missing processing instruction.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_missingProcessingInstruction() throws Exception {
    	Document expected = XMLUtil.parse(RESOURCE_PATH + "special.xml");
        Document actual = parseSimpleXmlUtf8();
        ProcessingInstruction pi = (ProcessingInstruction) XPathUtil.queryNode(expected, "/root/processing-instruction('procint')");
        XMLComparisonSettings settings = new XMLComparisonSettings();
        settings.setProcessingInstructionRelevant(true);
        settings.setCdataRelevant(false);
		AggregateDiff diff = new XMLComparator(settings).compare(expected, actual);
        assertEquals(1, diff.getDetails().size());
        assertEquals(diffFactory.missing(pi, "processing instruction", "/root/?procint"), diff.getDetails().get(0));
    }

  /**
   * Test diff cdata relevant.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_cdataRelevant() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = XMLUtil.parse(RESOURCE_PATH + "special.xml");
        Text text = (Text) XPathUtil.queryNode(expected, "/root/node/text()"); 
        CDATASection cdata = (CDATASection) XPathUtil.queryNode(actual, "/root/node/text()"); 
        XMLComparisonSettings settings = new XMLComparisonSettings();
        settings.setProcessingInstructionRelevant(false);
        settings.setCdataRelevant(true);
		AggregateDiff diff = new XMLComparator(settings).compare(expected, actual);
        assertEquals(1, diff.getDetails().size());
        assertEquals(diffFactory.different(text, cdata, "element text", "/root/node/text()", "/root/node/text()"), diff.getDetails().get(0));
    }

  /**
   * Test diff cdata irrelevant.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_cdataIrrelevant() throws Exception {
    	Document expected = XMLUtil.parse(RESOURCE_PATH + "special.xml");
        Document actual = parseSimpleXmlUtf8();
        XMLComparisonSettings settings = new XMLComparisonSettings();
        settings.setProcessingInstructionRelevant(false);
        settings.setCdataRelevant(false);
		AggregateDiff diff = new XMLComparator(settings).compare(expected, actual);
        assertTrue("Unexpected diff", diff.isEmpty());
    }
    
    // test attribute diffs ----------------------------------------------------

  /**
   * Test diff same comment.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_sameComment() throws Exception {
        Document expected = parseXmlWithComment();
        Document actual = parseXmlWithComment();
        XMLComparator comparator = new XMLComparator(new XMLComparisonSettings().withCommentRelevant(true));
		AggregateDiff diff = comparator.compare(expected, actual);
        assertEquals(0, diff.getDetails().size());
    }

  /**
   * Test diff other comment.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_otherComment() throws Exception {
        Document expected = parseXmlWithComment();
        Node expectedComment = expected.getDocumentElement().getChildNodes().item(1);
        Document actual = parseXmlWithComment();
        Node actualComment = actual.getDocumentElement().getChildNodes().item(1);
		actualComment.setTextContent("new comment");
        AggregateDiff diff = new XMLComparator(new XMLComparisonSettings().withCommentRelevant(true)).compare(expected, actual);
        assertEquals(1, diff.getDetails().size());
        assertEquals(diffFactory.different(expectedComment, actualComment, "comment", "/root/comment()", "/root/comment()"), diff.getDetails().get(0));
    }

  /**
   * Test diff other comment irrelevant.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_otherCommentIrrelevant() throws Exception {
        Document expected = parseXmlWithComment();
        Document actual = parseXmlWithComment();
        Node node = actual.getDocumentElement().getChildNodes().item(1);
        assertTrue(node instanceof Comment);
		node.setTextContent("new comment");
        AggregateDiff diff = new XMLComparator(new XMLComparisonSettings().withCommentRelevant(false)).compare(expected, actual);
        assertTrue("Unexpected diff", diff.isEmpty());
    }

  /**
   * Test diff unexpected comment.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_unexpectedComment() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = parseXmlWithComment();
        Node actualComment = actual.getDocumentElement().getChildNodes().item(1);
        AggregateDiff diff = new XMLComparator(new XMLComparisonSettings().withCommentRelevant(true)).compare(expected, actual);
        assertEquals(1, diff.getDetails().size());
        assertEquals(diffFactory.unexpected(actualComment, "comment", "/root/comment()"), diff.getDetails().get(0));
    }

  /**
   * Test diff unexpected comment irrelevant.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_UnexpectedCommentIrrelevant() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = parseXmlWithComment();
        AggregateDiff diff = new XMLComparator(new XMLComparisonSettings().withCommentRelevant(false)).compare(expected, actual);
        assertTrue("Unexpected diff", diff.isEmpty());
    }

  /**
   * Test diff missing comment.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_missingComment() throws Exception {
        Document expected = parseXmlWithComment();
        Comment comment = (Comment) expected.getDocumentElement().getChildNodes().item(1);
        Document actual = parseSimpleXmlUtf8();
        AggregateDiff diff = new XMLComparator(new XMLComparisonSettings().withCommentRelevant(true)).compare(expected, actual);
        assertEquals(1, diff.getDetails().size());
        assertEquals(diffFactory.missing(comment, "comment", "/root/comment()"), diff.getDetails().get(0));
    }

  /**
   * Test diff missing comment irrelevant.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_missingCommentIrrelevant() throws Exception {
        Document expected = parseXmlWithComment();
        Document actual = parseSimpleXmlUtf8();
        AggregateDiff diff = new XMLComparator(new XMLComparisonSettings().withCommentRelevant(false)).compare(expected, actual);
        assertTrue("Unexpected diff", diff.isEmpty());
    }

    // comment diffs ------------------------------------------------------

  /**
   * Test diff other attribute val.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_otherAttributeVal() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = parseSimpleXmlUtf8();
        actual.getDocumentElement().setAttribute("att", "val2");
        AggregateDiff diff = new XMLComparator().compare(expected, actual);
        assertEquals(1, diff.getDetails().size());
        assertEquals(diffFactory.different("val", "val2", "attribute", "/root/@att", "/root/@att"), diff.getDetails().get(0));
    }

  /**
   * Test diff other attribute and text escaped.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_otherAttributeAndTextEscaped() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = XMLUtil.parse(ESCAPED_XML_PATH);
        AggregateDiff diff = new XMLComparator().compare(expected, actual);
        assertEquals(2, diff.getDetails().size());
        // verify attribute diff
        assertEquals(diffFactory.different("val", "<@att&>", "attribute", "/root/@att", "/root/@att"), diff.getDetails().get(0));
        // verify text diff
        Text expectedText = (Text) XPathUtil.queryNode(expected, "/root/node/text()");
        assertEquals("text", expectedText.getTextContent());
        Text actualText = (Text) XPathUtil.queryNode(actual, "/root/node/text()");
        assertEquals("<text&>", actualText.getTextContent());
        assertEquals(diffFactory.different(expectedText, actualText, "element text", "/root/node/text()", "/root/node/text()"), diff.getDetails().get(1));
    }

  /**
   * Test diff other attribute val tolerated.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_otherAttributeValTolerated() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = parseSimpleXmlUtf8();
        actual.getDocumentElement().setAttribute("att", "val2");
        XMLComparisonSettings settings = new XMLComparisonSettings();
        settings.tolerateDifferentAt("/root/@att");
        AggregateDiff diff = new XMLComparator(settings).compare(expected, actual);
        assertTrue("Unexpected diff", diff.isEmpty());
    }

  /**
   * Test diff unexpected attribute.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_unexpectedAttribute() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = parseSimpleXmlUtf8();
        actual.getDocumentElement().setAttribute("att2", "val2");
        AggregateDiff diff = new XMLComparator().compare(expected, actual);
        assertEquals(1, diff.getDetails().size());
        assertEquals(diffFactory.unexpected("val2", "attribute", "/root/@att2"), diff.getDetails().get(0));
    }

  /**
   * Test diff unexpected attribute tolerated.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_UnexpectedAttributeTolerated() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = parseSimpleXmlUtf8();
        actual.getDocumentElement().setAttribute("att2", "val2");
        XMLComparisonSettings settings = new XMLComparisonSettings();
        settings.tolerateUnexpectedAt("/root/@att2");
        AggregateDiff diff = new XMLComparator(settings).compare(expected, actual);
        assertTrue("Unexpected diff", diff.isEmpty());
    }

  /**
   * Test diff missing attribute.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_missingAttribute() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = parseSimpleXmlUtf8();
        actual.getDocumentElement().removeAttribute("att");
        AggregateDiff diff = new XMLComparator().compare(expected, actual);
        assertEquals(1, diff.getDetails().size());
        assertEquals(diffFactory.missing("val", "attribute", "/root/@att"), diff.getDetails().get(0));
    }

  /**
   * Test diff missing attribute tolerated.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_missingAttributeTolerated() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = parseSimpleXmlUtf8();
        actual.getDocumentElement().removeAttribute("att");
        XMLComparisonSettings settings = new XMLComparisonSettings();
        settings.tolerateMissingAt("/root/@att");
        AggregateDiff diff = new XMLComparator(settings).compare(expected, actual);
        assertTrue("Unexpected diff", diff.isEmpty());
    }

    
    
    // test element diffs ------------------------------------------------------

  /**
   * Test diff other element text.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_otherElementText() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = parseSimpleXmlUtf8();
        node(actual).setTextContent("otherText");
        AggregateDiff diff = new XMLComparator().compare(expected, actual);
        assertEquals(1, diff.getDetails().size());
        Node expectedText = XPathUtil.queryNode(expected, "/root/node/text()");
		Node actualText = XPathUtil.queryNode(actual, "/root/node/text()");
		DiffDetail expectedDiff = diffFactory.different(expectedText, actualText, "element text", "/root/node/text()", "/root/node/text()");
        System.out.println(expectedDiff);
        assertEquals(expectedDiff, diff.getDetails().get(0));
    }

  /**
   * Test diff other element text tolerated.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_otherElementTextTolerated() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = parseSimpleXmlUtf8();
        node(actual).setTextContent("otherText");
        XMLComparisonSettings settings = new XMLComparisonSettings();
        settings.tolerateDifferentAt("/root/node/text()");
        AggregateDiff diff = new XMLComparator(settings).compare(expected, actual);
        assertTrue("Unexpected diff: " + diff, diff.isEmpty());
    }

  /**
   * Test diff missing element.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_missingElement() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = parseSimpleXmlUtf8();
        actual.getDocumentElement().removeChild(node(actual));
        AggregateDiff diff = new XMLComparator().compare(expected, actual);
        assertEquals(1, diff.getDetails().size());
        DiffDetail expectedDiff = diffFactory.missing(node(expected), "list element", "/root/node");
        DiffDetail actualDiff = diff.getDetails().get(0);
        assertEquals(expectedDiff, actualDiff);
    }

  /**
   * Test diff missing element tolerated.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_missingElementTolerated() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = parseSimpleXmlUtf8();
        actual.getDocumentElement().removeChild(node(actual));
        XMLComparisonSettings settings = new XMLComparisonSettings();
        settings.tolerateMissingAt("/root/node");
        AggregateDiff diff = new XMLComparator(settings).compare(expected, actual);
        assertTrue("Unexpected diff: " + diff, diff.isEmpty());
    }

  /**
   * Test diff additional element.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_additionalElement() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = parseSimpleXmlUtf8();
        Element node2 = actual.createElement("node2");
        actual.getDocumentElement().appendChild(node2);
        AggregateDiff diff = new XMLComparator().compare(expected, actual);
        assertEquals(1, diff.getDetails().size());
        DiffDetail expectedDiff = diffFactory.unexpected(node2, "list element", "/root/node2");
        DiffDetail actualDiff = diff.getDetails().get(0);
        assertEquals(expectedDiff, actualDiff);
    }

  /**
   * Test diff additional element tolerated.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_additionalElementTolerated() throws Exception {
        Document expected = parseSimpleXmlUtf8();
        Document actual = parseSimpleXmlUtf8();
        Element node2 = actual.createElement("node2");
        actual.getDocumentElement().appendChild(node2);
        XMLComparisonSettings settings = new XMLComparisonSettings();
        settings.tolerateUnexpectedAt("/root/node2");
        AggregateDiff diff = new XMLComparator(settings).compare(expected, actual);
        assertTrue("Unexpected diff", diff.isEmpty());
    }

  /**
   * Test diff list element moved.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_listElementMoved() throws Exception {
        Document expected = XMLUtil.parse(RESOURCE_PATH + "list_1_alice_2_bob.xml");
        Element alice = XMLUtil.getChildElements(expected.getDocumentElement(), false, "item")[0];
        Document actual = XMLUtil.parse(RESOURCE_PATH + "list_2_bob_1_alice.xml");
        AggregateDiff diff = new XMLComparator().compare(expected, actual);
        assertEquals(1, diff.getDetails().size());
        DiffDetail expectedDiff = diffFactory.moved(alice, "list element", "/list/item[1]", "/list/item[2]");
        DiffDetail actualDiff = diff.getDetails().get(0);
        assertEquals(expectedDiff, actualDiff);
    }

  /**
   * Test diff list element moved tolerated.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_listElementMovedTolerated() throws Exception {
        Document expected = XMLUtil.parse(RESOURCE_PATH + "list_1_alice_2_bob.xml");
        Document actual = XMLUtil.parse(RESOURCE_PATH + "list_2_bob_1_alice.xml");
        XMLComparisonSettings settings = new XMLComparisonSettings();
        settings.tolerateMovedAt("/list/item");
        AggregateDiff diff = new XMLComparator(settings).compare(expected, actual);
        assertTrue("Unexpected diff", diff.isEmpty());
    }

  /**
   * Test diff list moved content kept item no.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_listMovedContentKeptItemNo() throws Exception {
        Document expected = XMLUtil.parse(RESOURCE_PATH + "list_1_alice_2_bob.xml");
        Document actual = XMLUtil.parse(RESOURCE_PATH + "list_1_bob_2_alice.xml");
        XMLComparisonSettings settings = new XMLComparisonSettings();
        settings.addKeyExpression("//item", "@no");
        AggregateDiff diff = new XMLComparator(settings).compare(expected, actual);
        assertEquals(2, diff.getDetails().size());
        Node alice1 = XPathUtil.queryNode(expected, "/list/item[1]/text()");
        Node alice2 = XPathUtil.queryNode(actual, "/list/item[2]/text()");
        Node bob2 = XPathUtil.queryNode(expected, "/list/item[2]/text()");
        Node bob1 = XPathUtil.queryNode(actual, "/list/item[1]/text()");
        DiffDetail expectedDiff1 = diffFactory.different(alice1, bob1, "element text", "/list/item[1]/text()", "/list/item[1]/text()");
        DiffDetail actualDiff1 = diff.getDetails().get(0);
        assertEquals(expectedDiff1, actualDiff1);
        DiffDetail expectedDiff2 = diffFactory.different(bob2, alice2, "element text", "/list/item[2]/text()", "/list/item[2]/text()");
        DiffDetail actualDiff2 = diff.getDetails().get(1);
        assertEquals(expectedDiff2, actualDiff2);
    }

  /**
   * Test diff list moved item no kept content.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_listMovedItemNoKeptContent() throws Exception {
        Document expected = XMLUtil.parse(RESOURCE_PATH + "list_1_alice_2_bob.xml");
        Document actual = XMLUtil.parse(RESOURCE_PATH + "list_2_alice_1_bob.xml");
        XMLComparisonSettings settings = new XMLComparisonSettings();
        settings.addKeyExpression("//item", "text()");
        AggregateDiff diff = new XMLComparator(settings).compare(expected, actual);
        assertEquals(2, diff.getDetails().size());
        DiffDetail expectedDiff2 = diffFactory.different("1", "2", "attribute", "/list/item[1]/@no", "/list/item[1]/@no");
        assertEquals(expectedDiff2, diff.getDetails().get(0));
        DiffDetail expectedDiff3 = diffFactory.different("2", "1", "attribute", "/list/item[2]/@no", "/list/item[2]/@no");
        assertEquals(expectedDiff3, diff.getDetails().get(1));
    }

  /**
   * Test diff list tolerate moved content ignoring item no.
   *
   * @throws Exception the exception
   */
  @Test
    public void testDiff_listTolerateMovedContentIgnoringItemNo() throws Exception {
        Document expected = XMLUtil.parse(RESOURCE_PATH + "list_1_alice_2_bob.xml");
        Document actual = XMLUtil.parse(RESOURCE_PATH + "list_1_bob_2_alice.xml");
        XMLComparisonSettings settings = new XMLComparisonSettings();
        settings.addKeyExpression("//item", "text()");
        settings.tolerateMovedAt("//item");
        settings.tolerateDifferentAt("//item/@no");
        AggregateDiff diff = new XMLComparator(settings).compare(expected, actual);
        assertTrue("Unexpected diff: " + diff, diff.isEmpty());
    }

  /**
   * Test entity inclusion.
   *
   * @throws Exception the exception
   */
  @Test
    public void testEntityInclusion() throws Exception { // TODO implement tests
    	Document doc = XMLUtil.parse(RESOURCE_PATH + "container.xml");
    	System.out.println(XMLUtil.format(doc));
    }

  /**
   * Test namespace not relevant.
   *
   * @throws Exception the exception
   */
  @Test
    public void testNamespaceNotRelevant() throws Exception {
    	Document xzDoc = XMLUtil.parse(NS_XZ_PATH);
    	Document zxDoc = XMLUtil.parse(NS_ZX_PATH);
    	Document simpleDoc = XMLUtil.parse(NS_NONE_PATH);
    	XMLComparisonSettings settings = new XMLComparisonSettings();
    	settings.setNamespaceRelevant(false);
		XMLComparator comparator = new XMLComparator(settings);
		AggregateDiff diff = comparator.compare(simpleDoc, xzDoc);
		assertTrue(diff.isEmpty());
		assertTrue(comparator.compare(xzDoc, zxDoc).isEmpty());
		assertTrue(comparator.compare(zxDoc, simpleDoc).isEmpty());
    }

  /**
   * Test same namespaces different prefix.
   *
   * @throws Exception the exception
   */
  @Test
    public void testSameNamespacesDifferentPrefix() throws Exception {
    	Document xzDoc = XMLUtil.parse(NS_XZ_PATH);
    	Document pDoc = XMLUtil.parse(NS_XPZP_PATH);
    	XMLComparisonSettings settings = new XMLComparisonSettings();
    	settings.setNamespaceRelevant(true);
		XMLComparator comparator = new XMLComparator(settings);
		AggregateDiff diff = comparator.compare(xzDoc, pDoc);
		assertTrue(diff.isEmpty());
    }

  /**
   * Test no namespace vs namespace.
   *
   * @throws Exception the exception
   */
  @Test
    public void testNoNamespaceVsNamespace() throws Exception {
    	Document simpleDoc = XMLUtil.parse(NS_NONE_PATH);
    	Document xzDoc = XMLUtil.parse(NS_XZ_PATH);
    	XMLComparisonSettings settings = new XMLComparisonSettings();
    	settings.setNamespaceRelevant(true);
		XMLComparator comparator = new XMLComparator(settings);
		AggregateDiff diff = comparator.compare(simpleDoc, xzDoc);
		assertEquals(2, diff.getDetailCount());
		assertEquals(diffFactory.different("none", "http://databene.org/formats/xml/compare/xx", "element namespace", "/root/node[1]", "/root/xx:node"), diff.getDetail(0));
		assertEquals(diffFactory.different("none", "http://databene.org/formats/xml/compare/zz", "element namespace", "/root/node[2]", "/root/zz:node"), diff.getDetail(1));
    }

  /**
   * Test namespaces different.
   *
   * @throws Exception the exception
   */
  @Test
    public void testNamespacesDifferent() throws Exception {
    	Document xzDoc = XMLUtil.parse(NS_XZ_PATH);
    	Document zxDoc = XMLUtil.parse(NS_ZX_PATH);
    	XMLComparisonSettings settings = new XMLComparisonSettings();
    	settings.setNamespaceRelevant(true);
		XMLComparator comparator = new XMLComparator(settings);
		AggregateDiff diff = comparator.compare(xzDoc, zxDoc);
		assertEquals(2, diff.getDetailCount());
		DiffDetail d1 = diffFactory.different("http://databene.org/formats/xml/compare/xx", "http://databene.org/formats/xml/compare/zz", "element namespace", "/root/xx:node", "/root/zz:node");
		assertEquals(d1, diff.getDetail(0));
		assertEquals(diffFactory.different("http://databene.org/formats/xml/compare/zz", "http://databene.org/formats/xml/compare/xx", "element namespace", "/root/zz:node", "/root/xx:node"), diff.getDetail(1));
    }

    // private helpers ---------------------------------------------------------

    private static Document parseSimpleXmlUtf8() throws IOException {
		return XMLUtil.parse(SIMPLE_XML_PATH);
    }

    private static Document parseSimpleXmlIso_8859_1() throws IOException {
		return XMLUtil.parse(SIMPLE_XML_ISO_8859_1_PATH);
    }

    private static Document parseXmlWithComment() throws IOException {
		return XMLUtil.parse(COMMENTED_XML_PATH);
    }

    private static Element node(Document doc) {
        Element rootElement = doc.getDocumentElement();
        return (Element) rootElement.getElementsByTagName("node").item(0);
    }

}
