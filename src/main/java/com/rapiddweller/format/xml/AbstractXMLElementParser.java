/*
 * Copyright (C) 2011-2021 Volker Bergmann (volker.bergmann@bergmann-it.de).
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

package com.rapiddweller.format.xml;

import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.Assert;
import com.rapiddweller.common.CollectionUtil;
import com.rapiddweller.common.ParseUtil;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.common.xml.XMLUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import java.util.HashSet;
import java.util.Set;

/**
 * Abstract implementation of the {@link XMLElementParser} interface.
 * Created: 05.12.2010 10:46:38
 * @param <E> the type of the objects to provide
 * @author Volker Bergmann
 * @since 0.5.4
 */
public abstract class AbstractXMLElementParser<E> implements XMLElementParser<E> {

  private static final Set<String> STD_XML_ROOT_ATTRIBUTES = CollectionUtil.toSet(
      "xmlns", "xmlns:xsi", "xsi:schemaLocation"
  );

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  protected final String elementName;
  protected final Set<Class<?>> supportedParentTypes;
  protected AttrInfoSupport attrSupport;

  protected AbstractXMLElementParser(String elementName, AttrInfoSupport attrSupport,
                                     Class<?>... supportedParentTypes) {
    this.elementName = elementName;
    this.attrSupport = attrSupport;
    for (Class<?> c : supportedParentTypes) {
      Assert.notNull(c, "supportedParentType");
    }
    this.supportedParentTypes = (ArrayUtil.isEmpty(supportedParentTypes) ?
        new HashSet<>() : CollectionUtil.toSet(supportedParentTypes));
  }

  @Override
  public boolean supportsElementName(String elementName) {
    return (this.elementName != null && this.elementName.equals(elementName));
  }

  @Override
  public boolean supports(Element element, Element[] parentXmlPath, E[] parentComponentPath) {
    if (!supportsElementName(element.getNodeName())) {
      return false;
    }
    if (!CollectionUtil.isEmpty(this.supportedParentTypes) && parentComponentPath != null) {
      E parentComponent = ArrayUtil.lastElementOf(parentComponentPath);
      if (parentComponent == null) {
        return supportedParentTypes.isEmpty();
      } else {
        return this.supportedParentTypes.contains(parentComponent.getClass());
      }
    }
    return true;
  }

  @Override
  public final E parse(Element element, Element[] parentXmlPath, E[] parentPath, ParseContext<E> context) {
    checkAttributeSupport(element, ArrayUtil.isEmpty(parentXmlPath));
    return doParse(element, parentXmlPath, parentPath, context);
  }

  protected abstract E doParse(Element element, Element[] parentXmlPath, E[] parentPath, ParseContext<E> context);

  protected Object parent(E[] parentPath) {
    if (ArrayUtil.isEmpty(parentPath)) {
      return null;
    } else {
      return ArrayUtil.lastElementOf(parentPath);
    }
  }


  protected void checkAttributeSupport(Element element, boolean ignoreStandardXmlRootElements) {
    // Check each attribute of the element if it is allowed
    NamedNodeMap attributes = element.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      Attr attr = (Attr) attributes.item(i);
      String attrName = attr.getName();
      if (!isStandardXmlRootAttribute(attrName) || !ignoreStandardXmlRootElements) {
        AttrInfo<?> attrInfo = attrSupport.get(attrName);
        if (attrInfo == null) {
          illegalAttribute(attr);
        }
      }
    }
    // Check if each required attribute is set
    for (AttrInfo<?> attrInfo : attrSupport.getAll()) {
      String attrName = attrInfo.getName();
      if (attrInfo.isRequired() && StringUtil.isEmpty(element.getAttribute(attrName))) {
        attributeIsMissing(element, attrName);
        return;
      }
    }
    attrSupport.validate(element);
  }

  // static helper methods for child classes -------------------------------------------------------------------------

  protected static boolean isStandardXmlRootAttribute(String key) {
    return (STD_XML_ROOT_ATTRIBUTES.contains(key) || key.contains(":"));
  }

  protected void checkSupportedAttributes(Element element, String... supportedAttributes) {
    NamedNodeMap attributes = element.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      Attr attr = (Attr) attributes.item(i);
      if (!ArrayUtil.contains(attr.getName(), supportedAttributes)) {
        illegalAttribute(attr);
      }
    }
  }

  protected static String parseRequiredName(Element element) {
    String name = parseOptionalName(element);
    if (StringUtil.isEmpty(name)) {
      throw ExceptionFactory.getInstance().syntaxErrorForXmlElement(
          "'name' attribute is missing", element);
    }
    return name;
  }

  protected static String parseOptionalName(Element element) {
    return getOptionalAttribute("name", element);
  }

  protected static Integer parseOptionalInteger(String attributeName, Element element) {
    return parseOptionalInteger(attributeName, element, null);
  }

  protected static Integer parseOptionalInteger(String attributeName, Element element, Integer defaultValue) {
    String spec = getOptionalAttribute(attributeName, element);
    return (spec != null ? Integer.parseInt(spec) : defaultValue);
  }

  protected static Boolean parseOptionalBoolean(String attributeName, Element element) {
    String spec = getOptionalAttribute(attributeName, element);
    return (spec != null ? ParseUtil.parseBoolean(spec) : null);
  }

  protected static Long parseOptionalLong(String attributeName, Element element) {
    String spec = getOptionalAttribute(attributeName, element);
    return (spec != null ? Long.parseLong(spec) : null);
  }

  public static String getRequiredAttribute(String name, Element element) {
    String value = getOptionalAttribute(name, element);
    if (value == null) {
      throw ExceptionFactory.getInstance().syntaxErrorForXmlElement(
          "'" + name + "' attribute expected", element);
    }
    return value;
  }

  protected static String getOptionalAttribute(String name, Element element) {
    return StringUtil.emptyToNull(element.getAttribute(name));
  }

  protected void attributeIsMissing(Element element, String requiredAttribute) {
    throw ExceptionFactory.getInstance().missingXmlAttribute(
        null, attrSupport.getErrorId(requiredAttribute), requiredAttribute, element);
  }

  protected void illegalAttribute(Attr attribute) {
    throw ExceptionFactory.getInstance().illegalXmlAttributeName(
        null, null, attrSupport.getErrorIdForIllegalAttribute(), attribute, attrSupport);
  }

  protected static Element getUniqueChild(Element parent, String childName, boolean required) {
    Element[] children = XMLUtil.getChildElements(parent, false, childName);
    if (children.length > 1) {
      throw ExceptionFactory.getInstance().syntaxErrorForXmlElement(
          "Multiple <" + childName + "> elements", parent);
    }
    if (children.length == 0) {
      if (required) {
        throw ExceptionFactory.getInstance().syntaxErrorForXmlElement(
            "Required child element <" + childName + "> is missing", parent);
      } else {
        return null;
      }
    }
    return children[0];
  }

  protected static void assertOnlyTheseChildNames(Element ifElement, String... allowedChildNames) {
    for (Element child : XMLUtil.getChildElements(ifElement)) {
      String childName = child.getNodeName();
      if (!ArrayUtil.contains(childName, allowedChildNames)) {
        throw ExceptionFactory.getInstance().syntaxErrorForXmlElement("Illegal child element", child);
      }
    }
  }

}
