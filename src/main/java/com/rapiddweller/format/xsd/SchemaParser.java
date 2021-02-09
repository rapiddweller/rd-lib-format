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

package com.rapiddweller.format.xsd;

import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.xml.XMLUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Parses XML schemas and provides an representation by a {@link Schema} obejct.
 * Created: 16.05.2014 18:29:01
 *
 * @author Volker Bergmann
 * @since 0.8.2
 */
public class SchemaParser {

  private static final Logger LOGGER = LogManager.getLogger(SchemaParser.class);

  /**
   * Parse schema.
   *
   * @param document the document
   * @return the schema
   */
  public Schema parse(Document document) {
    Element root = document.getDocumentElement();
    return parseSchema(root);
  }

  private Schema parseSchema(Element root) {
    Schema schema = new Schema();
    for (Element child : XMLUtil.getChildElements(root)) {
      String childName = child.getLocalName();
      if ("annotation".equals(childName)) {
        schema.setDocumentation(parseAnnotationDocumentation(child));
      } else if ("simpleType".equals(childName)) {
        schema.addSimpleType(parseSimpleType(child));
      } else if ("complexType".equals(childName)) {
        schema.addComplexType(parseComplexType(child, schema));
      } else if ("element".equals(childName)) {
        schema.setMember(parseElement(child, schema));
      } else {
        throw new UnsupportedOperationException("Not a supported child of 'schema': " + childName);
      }
    }
    return schema;
  }

  private static String parseAnnotationDocumentation(Element element) {
    for (Element child : XMLUtil.getChildElements(element)) {
      String childName = child.getLocalName();
      if ("documentation".equals(childName)) {
        return child.getTextContent();
      }
    }
    return null;
  }

  private static SimpleType parseSimpleType(Element element) {
    SimpleType simpleType = new SimpleType(element.getAttribute("name"));
    // TODO parse simple schema type info
    return simpleType;
  }

  private ComplexType parseComplexType(Element element, Schema schema) {
    Element simpleContent = XMLUtil.getChildElement(element, false, false, "simpleContent");
    if (simpleContent != null) {
      return parseComplexTypeWithSimpleContent(element);
    }
    Element sequence = XMLUtil.getChildElement(element, false, false, "sequence");
    if (sequence != null) {
      return parseComplexTypeWithSequence(element, schema);
    }
    throw new UnsupportedOperationException("Not a supported kind of 'complexType': " + element.getAttribute("name"));
  }

  private static ComplexType parseComplexTypeWithSimpleContent(Element element) {
    String name = XMLUtil.getAttribute(element, "name", false);
    PlainComplexType type = new PlainComplexType(name);
    for (Element child : XMLUtil.getChildElements(element)) {
      String childName = child.getLocalName();
      if ("simpleContent".equals(childName)) {
        parseSimpleContent(child, type);
      } else if ("attribute".equals(childName)) {
        type.addAttribute(parseAttribute(child));
      } else {
        throw new UnsupportedOperationException("Not a supported child of '" + element.getNodeName() + "': " + childName);
      }
    }
    return type;
  }

  private static void parseSimpleContent(Element element, PlainComplexType type) {
    for (Element child : XMLUtil.getChildElements(element)) {
      String childName = child.getLocalName();
      if ("restriction".equals(childName)) {
        parseRestriction(child, type);
      } else {
        LOGGER.warn("Not a supported child of '" + element.getNodeName() + "': " + childName);
      }
    }
  }

  private static void parseRestriction(Element element, PlainComplexType type) {
    for (Element child : XMLUtil.getChildElements(element)) {
      String childName = child.getLocalName();
      if ("minLength".equals(childName)) {
        type.setMinLength(XMLUtil.getIntegerAttribute(child, "value", null));
      } else if ("maxLength".equals(childName)) {
        type.setMaxLength(XMLUtil.getIntegerAttribute(child, "value", null));
      }
    }
  }

  private ComplexType parseComplexTypeWithSequence(Element element, Schema schema) {
    String name = XMLUtil.getAttribute(element, "name", false);
    CompositeComplexType type = new CompositeComplexType(name);
    for (Element child : XMLUtil.getChildElements(element)) {
      String childName = child.getLocalName();
      if ("sequence".equals(childName)) {
        parseSequence(child, type, schema);
      } else if ("attribute".equals(childName)) {
        type.addAttribute(parseAttribute(child));
      } else {
        throw new UnsupportedOperationException("Not a supported child of '" + element.getNodeName() + "': " + childName);
      }
    }
    return type;
  }

  private void parseSequence(Element element, CompositeComplexType complexType, Schema schema) {
    for (Element child : XMLUtil.getChildElements(element)) {
      String childName = child.getLocalName();
      if ("element".equals(childName)) {
        complexType.addMember(parseElement(child, schema));
      } else {
        throw new UnsupportedOperationException("Not a supported child of 'element': " + childName);
      }
    }
  }

  private static Attribute parseAttribute(Element child) {
    String name = XMLUtil.getAttribute(child, "name", true);
    return new Attribute(name);
  }

  private ComplexMember parseElement(Element element, Schema schema) {
    String name = element.getAttribute("name");
    ComplexMember member = new ComplexMember(name, null);
    ComplexType type = null;
    for (Element child : XMLUtil.getChildElements(element)) {
      String childName = child.getLocalName();
      if ("complexType".equals(childName)) {
        type = parseComplexType(child, schema);
      } else if ("annotation".equals(childName)) {
        member.setDocumentation(parseAnnotationDocumentation(child));
      } else {
        throw new UnsupportedOperationException("Not a supported child of 'element': " + childName);
      }
    }
    if (type == null) {
      String typeName = XMLUtil.getAttribute(element, "type", true);
      type = schema.getComplexType(typeName);
    }
    member.setType(type);
    member.setMinCardinality(parseOccursAttribute(element, "minOccurs"));
    member.setMaxCardinality(parseOccursAttribute(element, "maxOccurs"));
    return member;
  }

  private static int parseOccursAttribute(Element element, String name) {
    String stringValue = element.getAttribute(name);
    if (StringUtil.isEmpty(stringValue)) {
      return 1;
    } else if ("unbounded".equals(stringValue)) {
      return -1;
    } else {
      return Integer.parseInt(stringValue);
    }
  }

}
