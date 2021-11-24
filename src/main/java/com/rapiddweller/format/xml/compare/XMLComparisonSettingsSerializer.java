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

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.Encodings;
import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.common.xml.SimpleXMLWriter;
import com.rapiddweller.common.xml.XMLUtil;
import com.rapiddweller.format.compare.DiffDetailType;
import com.rapiddweller.format.compare.KeyExpression;
import com.rapiddweller.format.compare.LocalDiffType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Loads and saves {@link XMLComparisonSettings}.
 * Created: 13.06.2016 14:42:32
 * @author Volker Bergmann
 * @since 1.0.12
 */
@SuppressWarnings("static-method")
public class XMLComparisonSettingsSerializer {

  private static final String COMPARISON_SETTINGS = "comparison-settings";
  private static final String MODEL_CLASS = "modelClass";

  private static final String XML_SETTINGS = "xml-settings";
  private static final String CDATA_RELEVANT = "cdataRelevant";
  private static final String COMMENT_RELEVANT = "commentRelevant";
  private static final String ENCODING_RELEVANT = "encodingRelevant";
  private static final String NAMESPACE_RELEVANT = "namespaceRelevant";
  private static final String PROCESSING_INSTRUCTION_RELEVANT = "processingInstructionRelevant";
  private static final String WHITESPACE_RELEVANT = "whitespaceRelevant";

  private static final String TOLERATED_DIFFS = "tolerated-diffs";
  private static final String TOLERATED_DIFF = "tolerated-diff";
  private static final String LOCATOR = "locator";
  private static final String TYPE = "type";

  private static final String KEY_EXPRESSIONS = "key-expressions";
  private static final String KEY_EXPRESSION = "key-expression";
  private static final String DEFINITION = "definition";

  public void save(XMLComparisonSettings settings, OutputStream stream) {
    try (SimpleXMLWriter writer = new SimpleXMLWriter(stream, Encodings.UTF_8, true)) {
      writer.startDocument();
      writer.startElement(COMPARISON_SETTINGS, MODEL_CLASS, settings.getModel().getClass().getName());
      saveXmlElementSettings(writer, settings);
      saveToleratedDiffs(writer, settings);
      saveKeys(writer, settings);
      writer.endElement(COMPARISON_SETTINGS);
      writer.endDocument();
    } catch (SAXException e) {
      throw ExceptionFactory.getInstance().conversionFailed("Error saving settings", e);
    }
  }

  public XMLComparisonSettings load(InputStream stream) throws IOException {
    Document document = XMLUtil.parse(stream);
    Element root = document.getDocumentElement();
    assertElementName(COMPARISON_SETTINGS, root);
    XMLComparisonSettings settings = loadXmlElementSettings(root);
    loadToleratedDiffs(root, settings);
    loadKeys(root, settings);
    return settings;
  }


  // private persistor implementation --------------------------------------------------------------------------------

  private void saveXmlElementSettings(SimpleXMLWriter writer, XMLComparisonSettings settings) throws SAXException {
    Map<String, String> attrs = new HashMap<>();
    attrs.put(CDATA_RELEVANT, String.valueOf(settings.isCdataRelevant()));
    attrs.put(COMMENT_RELEVANT, String.valueOf(settings.isCommentRelevant()));
    attrs.put(ENCODING_RELEVANT, String.valueOf(settings.isEncodingRelevant()));
    attrs.put(NAMESPACE_RELEVANT, String.valueOf(settings.isNamespaceRelevant()));
    attrs.put(PROCESSING_INSTRUCTION_RELEVANT, String.valueOf(settings.isProcessingInstructionRelevant()));
    attrs.put(WHITESPACE_RELEVANT, String.valueOf(settings.isWhitespaceRelevant()));
    writer.writeElement(XML_SETTINGS, attrs);
  }

  private void saveToleratedDiffs(SimpleXMLWriter writer, XMLComparisonSettings settings) throws SAXException {
    writer.startElement(TOLERATED_DIFFS);
    for (LocalDiffType toleratedDiff : settings.getToleratedDiffs()) {
      Map<String, String> atts = new HashMap<>();
      atts.put(LOCATOR, toleratedDiff.getLocator());
      atts.put(TYPE, toleratedDiff.getType().toString());
      writer.writeElement(TOLERATED_DIFF, atts);
    }
    writer.endElement(TOLERATED_DIFFS);
  }

  private void saveKeys(SimpleXMLWriter writer, XMLComparisonSettings settings) throws SAXException {
    writer.startElement(KEY_EXPRESSIONS);
    for (KeyExpression keyDefinition : settings.getModel().getKeyExpressions()) {
      Map<String, String> atts = new HashMap<>();
      atts.put(LOCATOR, keyDefinition.getLocator());
      atts.put(DEFINITION, keyDefinition.getExpression());
      writer.writeElement(KEY_EXPRESSION, atts);
    }
    writer.endElement(KEY_EXPRESSIONS);
  }

  // private loader methods ------------------------------------------------------------------------------------------

  private XMLComparisonSettings loadXmlElementSettings(Element root) {
    Element settingsElement = (Element) root.getElementsByTagName(XML_SETTINGS).item(0);
    String modelClassName = XMLUtil.getAttribute(settingsElement, MODEL_CLASS, false);
    if (modelClassName == null) {
      modelClassName = DefaultXMLComparisonModel.class.getName();
    }
    XMLComparisonModel model = (XMLComparisonModel) BeanUtil.newInstance(modelClassName);
    XMLComparisonSettings settings = new XMLComparisonSettings(model);
    settings.setCdataRelevant(XMLUtil.getBooleanAttribute(settingsElement, CDATA_RELEVANT, true));
    settings.setCommentRelevant(XMLUtil.getBooleanAttribute(settingsElement, COMMENT_RELEVANT, true));
    settings.setEncodingRelevant(XMLUtil.getBooleanAttribute(settingsElement, ENCODING_RELEVANT, true));
    settings.setNamespaceRelevant(XMLUtil.getBooleanAttribute(settingsElement, NAMESPACE_RELEVANT, true));
    settings.setProcessingInstructionRelevant(XMLUtil.getBooleanAttribute(settingsElement, PROCESSING_INSTRUCTION_RELEVANT, true));
    settings.setWhitespaceRelevant(XMLUtil.getBooleanAttribute(settingsElement, WHITESPACE_RELEVANT, true));
    return settings;
  }

  private void loadToleratedDiffs(Element root, XMLComparisonSettings settings) {
    Element tolerationsRoot = (Element) root.getElementsByTagName(TOLERATED_DIFFS).item(0);
    NodeList tolerationsElements = tolerationsRoot.getElementsByTagName(TOLERATED_DIFF);
    for (int i = 0; i < tolerationsElements.getLength(); i++) {
      Element element = (Element) tolerationsElements.item(i);
      String locator = XMLUtil.getAttribute(element, LOCATOR, true);
      DiffDetailType type = DiffDetailType.valueOf(XMLUtil.getAttribute(element, TYPE, true));
      settings.addToleratedDiff(new LocalDiffType(type, locator));
    }
  }

  private void loadKeys(Element root, XMLComparisonSettings settings) {
    Element keysRoot = (Element) root.getElementsByTagName(KEY_EXPRESSIONS).item(0);
    NodeList keyElements = keysRoot.getElementsByTagName(KEY_EXPRESSION);
    for (int i = 0; i < keyElements.getLength(); i++) {
      Element element = (Element) keyElements.item(i);
      String locator = XMLUtil.getAttribute(element, LOCATOR, true);
      String definition = XMLUtil.getAttribute(element, DEFINITION, true);
      settings.addKeyExpression(locator, definition);
    }
  }

  private void assertElementName(String name, Element element) {
    if (!name.equals(element.getNodeName())) {
      String message = "Expected element <" + name + ">, but found <" + element.getNodeName() + ">";
      throw ExceptionFactory.getInstance().syntaxErrorForText(XMLUtil.formatShort(element), message);
    }
  }

}
