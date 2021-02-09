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

import com.rapiddweller.common.NullSafeComparator;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.common.converter.XMLNode2StringConverter;
import com.rapiddweller.common.xml.XMLUtil;
import com.rapiddweller.format.compare.AggregateDiff;
import com.rapiddweller.format.compare.ArrayComparator;
import com.rapiddweller.format.compare.ArrayComparisonResult;
import com.rapiddweller.format.compare.DiffDetail;
import com.rapiddweller.format.compare.DiffDetailType;
import com.rapiddweller.format.compare.DiffFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.Iterator;

import static com.rapiddweller.format.xml.compare.XMLComparisonModel.ATTRIBUTE;
import static com.rapiddweller.format.xml.compare.XMLComparisonModel.DOCUMENT_ENCODING;
import static com.rapiddweller.format.xml.compare.XMLComparisonModel.ELEMENT_NAME;
import static com.rapiddweller.format.xml.compare.XMLComparisonModel.ELEMENT_NAMESPACE;
import static com.rapiddweller.format.xml.compare.XMLComparisonModel.ELEMENT_TEXT;
import static com.rapiddweller.format.xml.compare.XMLComparisonModel.PROCESSING_INSTRUCTION;

/**
 * Compares two XML documents.
 * Created: 16.11.2015 14:31:12
 *
 * @author Volker Bergmann
 * @since 1.0.5
 */
public class XMLComparator {

  private final XMLComparisonSettings settings;
  private final DiffFactory diffFactory;

  /**
   * Instantiates a new Xml comparator.
   */
  public XMLComparator() {
    this(new XMLComparisonSettings());
  }

  /**
   * Instantiates a new Xml comparator.
   *
   * @param settings the settings
   */
  public XMLComparator(XMLComparisonSettings settings) {
    this.settings = settings;
    this.diffFactory = new DiffFactory(new XMLNode2StringConverter());
  }

  /**
   * Assert equals.
   *
   * @param expected the expected
   * @param actual   the actual
   * @throws XPathExpressionException the x path expression exception
   */
  public void assertEquals(Document expected, Document actual) throws XPathExpressionException {
    AggregateDiff diffs = compare(expected, actual);
    if (diffs.getDetailCount() > 0) {
      String LF = SystemInfo.getLineSeparator();
      StringBuilder message = new StringBuilder("Documents do not match. Found " + diffs.getDetailCount() + " difference");
      if (diffs.getDetailCount() > 1) {
        message.append('s');
      }
      for (DiffDetail diff : diffs.getDetails()) {
        message.append(LF).append(diff);
      }
      throw new AssertionError(message);
    }
  }

  /**
   * Compare aggregate diff.
   *
   * @param uriOfExpected the uri of expected
   * @param uriOfActual   the uri of actual
   * @return the aggregate diff
   * @throws IOException              the io exception
   * @throws XPathExpressionException the x path expression exception
   */
  public AggregateDiff compare(String uriOfExpected, String uriOfActual) throws IOException, XPathExpressionException {
    Document expectedDoc = XMLUtil.parse(uriOfExpected);
    Document actualDoc = XMLUtil.parse(uriOfActual);
    return compare(expectedDoc, actualDoc);
  }

  /**
   * Compare aggregate diff.
   *
   * @param expectedDocument the expected document
   * @param actualDocument   the actual document
   * @return the aggregate diff
   * @throws XPathExpressionException the x path expression exception
   */
  public AggregateDiff compare(Document expectedDocument, Document actualDocument) throws XPathExpressionException {
    // prepare comparison
    ComparisonContext context = new ComparisonContext(settings.getToleratedDiffs(), expectedDocument, actualDocument);
    settings.getModel().init(actualDocument, expectedDocument);
    AggregateDiff diffs = new AggregateDiff(expectedDocument, actualDocument, settings);

    // check encoding
    String expectedEncoding = expectedDocument.getInputEncoding();
    String actualEncoding = actualDocument.getInputEncoding();
    if (!NullSafeComparator.equals(expectedEncoding, actualEncoding) && settings.isEncodingRelevant()) {
      diffs.addDetail(diffFactory.different(expectedEncoding, actualEncoding, DOCUMENT_ENCODING, "/", "/"));
    }

    // check element tree
    String expectedRootName = expectedDocument.getDocumentElement().getNodeName();
    String actualRootName = actualDocument.getDocumentElement().getNodeName();
    compareElements(expectedDocument.getDocumentElement(),
        actualDocument.getDocumentElement(), context, "/" + expectedRootName, "/" + actualRootName, diffs);

    return diffs;
  }


  // private helpers -------------------------------------------------------------------------------------------------

  /**
   * Compare elements aggregate diff.
   *
   * @param expected             the expected
   * @param actual               the actual
   * @param context              the context
   * @param parentPathOfExpected the parent path of expected
   * @param parentPathOfActual   the parent path of actual
   * @param diffs                the diffs
   * @return the aggregate diff
   */
  AggregateDiff compareElements(Element expected, Element actual, ComparisonContext context, String parentPathOfExpected, String parentPathOfActual,
                                AggregateDiff diffs) {
    if (context.isExcluded(expected)) // if this is an excluded node then return without checking
    {
      return diffs;
    }
    compareElementNames(expected, actual, parentPathOfExpected, parentPathOfActual, diffs);
    compareAttributes(expected, actual, context, parentPathOfExpected, parentPathOfActual, diffs);
    compareChildNodes(expected, actual, context, parentPathOfExpected, parentPathOfActual, diffs);
    return diffs;
  }

  private void compareElementNames(Element expected, Element actual, String parentPathOfExpected, String parentPathOfActual, AggregateDiff diffs) {
    // assert equal node names
    String elementName = expected.getLocalName();
    expectEqualStrings(elementName, actual.getLocalName(), ELEMENT_NAME, parentPathOfExpected, parentPathOfActual, diffs);
    if (settings.isNamespaceRelevant()) {
      String expectedNs = StringUtil.emptyToNull(expected.getNamespaceURI());
      String actualNs = StringUtil.emptyToNull(actual.getNamespaceURI());
      if (!NullSafeComparator.equals(expectedNs, actualNs)) {
        diffs.addDetail(
            diffFactory.different(nsDescription(expectedNs), nsDescription(actualNs), ELEMENT_NAMESPACE, parentPathOfExpected, parentPathOfActual));
      }
    }
  }

  private static String nsDescription(String namespace) {
    return (namespace != null ? namespace : "none");
  }

  private void compareAttributes(Element expectedElement, Element actualElement, ComparisonContext context,
                                 String parentPathOfExpected, String parentPathOfActual, AggregateDiff diffs) {
    // assert equal attributes
    // first check that each expected attribute exists and matches...
    NamedNodeMap expectedAttributes = expectedElement.getAttributes();
    for (int i = 0; i < expectedAttributes.getLength(); i++) {
      Attr expectedAttribute = (Attr) expectedAttributes.item(i);
      if (!isXmlnsAttribute(expectedAttribute) && !context.isExcluded(expectedAttribute)) {
        expectEqualAttribute(expectedAttribute, actualElement, context, parentPathOfExpected, parentPathOfActual, diffs);
      }
    }
    // ...then check that there do not exist additional ones
    NamedNodeMap actualAttributes = actualElement.getAttributes();
    for (int i = 0; i < actualAttributes.getLength(); i++) {
      Attr actualAttribute = (Attr) actualAttributes.item(i);
      Attr expectedAttribute = (Attr) expectedAttributes.getNamedItem(actualAttribute.getNodeName());
      if (expectedAttribute == null && !isXmlnsAttribute(actualAttribute) &&
          !context.isTolerated(DiffDetailType.UNEXPECTED, expectedAttribute, actualAttribute)) {
        diffs.addDetail(diffFactory.unexpected(actualAttribute.getValue(), ATTRIBUTE, attributePath(parentPathOfActual, actualAttribute)));
      }
    }
  }

  private static boolean isXmlnsAttribute(Attr attribute) {
    String name = attribute.getName();
    return "xmlns".equals(name) || name.startsWith("xmlns:");
  }

  private void compareChildNodes(Element expected, Element actual, ComparisonContext context,
                                 String parentPathOfExpected, String parentPathOfActual, AggregateDiff diffs) {
    Node[] expectedChildNodes = settings.getModel().childNodes(expected);
    Node[] actualChildNodes = settings.getModel().childNodes(actual);
    if (expectedChildNodes.length > 0 || actualChildNodes.length > 0) {
      // if child elements exist, then compare them
      compareNodeArrays(expectedChildNodes, actualChildNodes, context, parentPathOfExpected, parentPathOfActual, diffs);
    }
    Iterator<DiffDetail> iterator = diffs.getDetails().iterator();
    while (iterator.hasNext()) {
      DiffDetail diff = iterator.next();
      if (context.isTolerated(diff.getType(), diff.getExpected(), diff.getActual())) {
        iterator.remove();
      }
    }
  }

  private void compareNodeArrays(Node[] expectedNodes, Node[] actualNodes,
                                 ComparisonContext context, String parentPathOfExpected, String parentPathOfActual, AggregateDiff diffs) {
    ArrayComparisonResult result =
        ArrayComparator.compare(expectedNodes, actualNodes, settings.getModel(), parentPathOfExpected, parentPathOfActual, diffFactory);
    for (DiffDetail diff : result.getDiffs()) {
      if (diff.getType() == DiffDetailType.DIFFERENT && diff.getExpected() instanceof Element && diff.getActual() instanceof Element) {
        // if two elements differ in general, dive deeper in the comparison
        compareElements((Element) diff.getExpected(), (Element) diff.getActual(), context, String.valueOf(diff.getLocatorOfExpected()),
            String.valueOf(diff.getLocatorOfActual()), diffs);
      } else if (diff.getType() == DiffDetailType.DIFFERENT && diff.getExpected() instanceof Text && diff.getActual() instanceof Text) {
        handleTextDiff(diff, diffs, context);
      } else if (diff.getExpected() instanceof ProcessingInstruction || diff.getActual() instanceof ProcessingInstruction) {
        handleProcesingInstructionDiff(diff, diffs, context);
      } else {
        // normal div treatment
        diffs.addDetail(diff);
      }
    }
  }

  private void handleTextDiff(DiffDetail diff, AggregateDiff diffs, ComparisonContext context) {
    // special handling for text nodes
    String locatorOfActual = StringUtil.removeSuffixIfPresent("/#text", diff.getLocatorOfActual());
    String locatorOfExpected = StringUtil.removeSuffixIfPresent("/#text", diff.getLocatorOfExpected());
    if (!context.isTolerated(DiffDetailType.DIFFERENT, locatorOfActual)) {
      diffs.addDetail(diffFactory.different(diff.getExpected(), diff.getActual(), ELEMENT_TEXT, locatorOfExpected, locatorOfActual));
    }
  }

  private void handleProcesingInstructionDiff(DiffDetail diff, AggregateDiff diffs, ComparisonContext context) {
    // special handling for processing instructions
    ProcessingInstruction expectedPI = (ProcessingInstruction) diff.getExpected();
    ProcessingInstruction actualPI = (ProcessingInstruction) diff.getActual();
    String locatorOfExpected = procIntLocator(StringUtil.removeSuffixIfPresent("/procint", diff.getLocatorOfExpected()), expectedPI);
    String locatorOfActual = procIntLocator(StringUtil.removeSuffixIfPresent("/procint", diff.getLocatorOfActual()), actualPI);
    if (!context.isTolerated(diff.getType(), locatorOfExpected) && !context.isTolerated(diff.getType(), locatorOfActual)) {
      diffs.addDetail(diffFactory.genericDiff(expectedPI, actualPI, PROCESSING_INSTRUCTION, diff.getType(), locatorOfExpected, locatorOfActual));
    }
  }

  private void expectEqualStrings(String expectedValue, String actualValue, String type, String locatorOfExpected, String locatorOfActual,
                                  AggregateDiff diffs) {
    if (!NullSafeComparator.equals(expectedValue, actualValue)) {
      diffs.addDetail(diffFactory.different(expectedValue, actualValue, type, locatorOfExpected, locatorOfActual));
    }
  }

  private void expectEqualAttribute(Attr expectedAttribute, Element actualElement, ComparisonContext context, String parentPathOfExpected,
                                    String parentPathOfActual, AggregateDiff diffs) {
    String attributeName = expectedAttribute.getName();
    Attr actualAttribute = actualElement.getAttributeNode(attributeName);
    String expectedAttValue = expectedAttribute.getValue();
    if (actualAttribute == null) {
      if (!context.isTolerated(DiffDetailType.MISSING, expectedAttribute, null)) {
        diffs.addDetail(diffFactory.missing(expectedAttValue, ATTRIBUTE, attributePath(parentPathOfExpected, expectedAttribute)));
      }
    } else {
      String actualAttValue = actualAttribute.getValue();
      if (!expectedAttValue.equals(actualAttValue) && !context.isTolerated(DiffDetailType.DIFFERENT, expectedAttribute, actualAttribute)) {
        String locatorOfExpected = attributePath(parentPathOfExpected, expectedAttribute);
        String locatorOfActual = attributePath(parentPathOfActual, actualAttribute);
        diffs.addDetail(diffFactory.different(expectedAttValue, actualAttValue, ATTRIBUTE, locatorOfExpected, locatorOfActual));
      }
    }
  }

  private static String attributePath(String parentPath, Attr attribute) {
    return parentPath + "/@" + attribute.getName();
  }

  private static String procIntLocator(String parentPath, ProcessingInstruction pi) {
    return (parentPath != null && pi != null ? parentPath + "/?" + pi.getTarget() : null);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[" + settings + "]";
  }

}
