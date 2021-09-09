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

package com.rapiddweller.format.xml;

import com.rapiddweller.common.ArrayFormat;
import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.CollectionUtil;
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.ParseUtil;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.SyntaxError;
import com.rapiddweller.common.xml.XMLUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.w3c.dom.Element;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Abstract implementation of the {@link XMLElementParser} interface.
 * Created: 05.12.2010 10:46:38
 *
 * @param <E> the type of the objects to provide
 * @author Volker Bergmann
 * @since 0.5.4
 */
public abstract class AbstractXMLElementParser<E> implements XMLElementParser<E> {

  /**
   * The Logger.
   */
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  /**
   * The Element name.
   */
  protected final String elementName;
  /**
   * The Supported parent types.
   */
  protected final Set<Class<?>> supportedParentTypes;
  /**
   * The Required attributes.
   */
  protected Set<String> requiredAttributes;
  /**
   * The Optional attributes.
   */
  protected Set<String> optionalAttributes;

  /**
   * Instantiates a new Abstract xml element parser.
   *
   * @param elementName          the element name
   * @param requiredAttributes   the required attributes
   * @param optionalAttributes   the optional attributes
   * @param supportedParentTypes the supported parent types
   */
  public AbstractXMLElementParser(String elementName,
                                  Set<String> requiredAttributes,
                                  Set<String> optionalAttributes,
                                  Class<?>... supportedParentTypes) {
    this.elementName = elementName;
    this.requiredAttributes = (requiredAttributes != null ? requiredAttributes : Collections.emptySet());
    this.optionalAttributes = (optionalAttributes != null ? optionalAttributes : Collections.emptySet());
    this.supportedParentTypes = CollectionUtil.toSet(supportedParentTypes);
  }

  @Override
  public boolean supports(Element element, E[] parentPath) {
    if (!this.elementName.equals(element.getNodeName())) {
      return false;
    }
    return CollectionUtil.isEmpty(this.supportedParentTypes) || parentPath == null ||
        this.supportedParentTypes.contains(ArrayUtil.lastElementOf(parentPath).getClass());
  }

  @Override
  public final E parse(Element element, E[] parentPath, com.rapiddweller.format.xml.ParseContext<E> context) {
    checkAttributeSupport(element);
    return doParse(element, parentPath, context);
  }

  /**
   * Do parse e.
   *
   * @param element    the element
   * @param parentPath the parent path
   * @param context    the context
   * @return the e
   */
  protected abstract E doParse(Element element, E[] parentPath, ParseContext<E> context);

  /**
   * Check attribute support.
   *
   * @param element the element
   */
  protected void checkAttributeSupport(Element element) {
    for (String attribute : XMLUtil.getAttributes(element).keySet()) {
      if (!requiredAttributes.contains(attribute) && !optionalAttributes.contains(attribute)) {
        unsupportedAttribute(element, attribute);
      }
    }
    for (String requiredAttribute : requiredAttributes) {
      if (StringUtil.isEmpty(element.getAttribute(requiredAttribute))) {
        syntaxError("Required attribute '" + requiredAttribute + "' is missing", element);
      }
    }
  }

  /**
   * Check supported attributes.
   *
   * @param element             the element
   * @param supportedAttributes the supported attributes
   */
  protected void checkSupportedAttributes(Element element, String... supportedAttributes) {
    for (String actualAttribute : XMLUtil.getAttributes(element).keySet()) {
      if (!ArrayUtil.contains(actualAttribute, supportedAttributes)) {
        unsupportedAttribute(element, actualAttribute);
      }
    }
  }

  /**
   * Assert element name.
   *
   * @param expectedName the expected name
   * @param element      the element
   */
  protected void assertElementName(String expectedName, Element element) {
    if (!element.getNodeName().equals(expectedName)) {
      throw new RuntimeException("Expected element name '" + expectedName + "', " +
          "found: '" + element.getNodeName());
    }
  }

  /**
   * Exclude attributes.
   *
   * @param element        the element
   * @param attributeNames the attribute names
   */
  protected void excludeAttributes(Element element, String... attributeNames) {
    String usedAttribute = null;
    for (String attributeName : attributeNames) {
      if (!StringUtil.isEmpty(element.getAttribute(attributeName))) {
        if (usedAttribute == null) {
          usedAttribute = attributeName;
        } else {
          syntaxError("The attributes '" + usedAttribute + "' and '" + attributeName + "' " +
              "exclude each other", element);
        }
      }
    }
  }

  /**
   * Assert at least one attribute is set.
   *
   * @param element        the element
   * @param attributeNames the attribute names
   */
  protected void assertAtLeastOneAttributeIsSet(Element element, String... attributeNames) {
    boolean ok = false;
    for (String attributeName : attributeNames) {
      if (!StringUtil.isEmpty(element.getAttribute(attributeName))) {
        ok = true;
      }
    }
    if (!ok) {
      throw createSyntaxError("At least one of these attributes must be set: " + ArrayFormat.format(attributeNames), element);
    }
  }

  /**
   * Assert attribute is set.
   *
   * @param element       the element
   * @param attributeName the attribute name
   */
  protected void assertAttributeIsSet(Element element, String attributeName) {
    if (StringUtil.isEmpty(element.getAttribute(attributeName))) {
      throw createSyntaxError("Attribute '" + attributeName + "' is missing", element);
    }
  }

  /**
   * Assert attribute is not set.
   *
   * @param element       the element
   * @param attributeName the attribute name
   */
  protected void assertAttributeIsNotSet(Element element, String attributeName) {
    if (!StringUtil.isEmpty(element.getAttribute(attributeName))) {
      throw createSyntaxError("Attributes '" + attributeName + "' must not be set", element);
    }
  }

  /**
   * Parent object.
   *
   * @param parentPath the parent path
   * @return the object
   */
  protected Object parent(E[] parentPath) {
    if (ArrayUtil.isEmpty(parentPath)) {
      return null;
    } else {
      return ArrayUtil.lastElementOf(parentPath);
    }
  }

  /**
   * Parse required name string.
   *
   * @param element the element
   * @return the string
   */
  protected String parseRequiredName(Element element) {
    String name = parseOptionalName(element);
    if (StringUtil.isEmpty(name)) {
      throw createSyntaxError("'name' attribute is missing", element);
    }
    return name;
  }

  /**
   * Parse optional name string.
   *
   * @param element the element
   * @return the string
   */
  protected String parseOptionalName(Element element) {
    return getOptionalAttribute("name", element);
  }

  /**
   * Parse optional integer integer.
   *
   * @param attributeName the attribute name
   * @param element       the element
   * @return the integer
   */
  protected Integer parseOptionalInteger(String attributeName, Element element) {
    String spec = getOptionalAttribute(attributeName, element);
    return (spec != null ? Integer.parseInt(spec) : null);
  }

  /**
   * Parse optional boolean boolean.
   *
   * @param attributeName the attribute name
   * @param element       the element
   * @return the boolean
   */
  protected Boolean parseOptionalBoolean(String attributeName, Element element) {
    String spec = getOptionalAttribute(attributeName, element);
    return (spec != null ? ParseUtil.parseBoolean(spec) : null);
  }

  /**
   * Parse optional long long.
   *
   * @param attributeName the attribute name
   * @param element       the element
   * @return the long
   */
  protected Long parseOptionalLong(String attributeName, Element element) {
    String spec = getOptionalAttribute(attributeName, element);
    return (spec != null ? Long.parseLong(spec) : null);
  }

  /**
   * Gets required attribute.
   *
   * @param name    the name
   * @param element the element
   * @return the required attribute
   */
  public String getRequiredAttribute(String name, Element element) {
    String value = getOptionalAttribute(name, element);
    if (value == null) {
      syntaxError("'" + name + "' attribute expected", element);
    }
    return value;
  }

  /**
   * Gets optional attribute.
   *
   * @param name    the name
   * @param element the element
   * @return the optional attribute
   */
  protected String getOptionalAttribute(String name, Element element) {
    return StringUtil.emptyToNull(element.getAttribute(name));
  }

  /**
   * Check attributes.
   *
   * @param element             the element
   * @param supportedAttributes the supported attributes
   */
  protected void checkAttributes(Element element, Set<String> supportedAttributes) {
    for (Map.Entry<String, String> attribute : XMLUtil.getAttributes(element).entrySet()) {
      if (!supportedAttributes.contains(attribute.getKey())) {
        throw new ConfigurationError("Not a supported import attribute: " + attribute.getKey());
      }
    }
  }

  /**
   * Create syntax error syntax error.
   *
   * @param message the message
   * @param element the element
   * @return the syntax error
   */
  protected static SyntaxError createSyntaxError(String message, Element element) {
    return new SyntaxError("Syntax error: " + message, XMLUtil.format(element));
  }

  /**
   * Create syntax error syntax error.
   *
   * @param message the message
   * @param element the element
   * @param cause   the cause
   * @return the syntax error
   */
  protected static SyntaxError createSyntaxError(String message, Element element, Exception cause) {
    return new SyntaxError("Syntax error: " + message, cause, XMLUtil.format(element), -1, -1);
  }

  /**
   * Syntax error.
   *
   * @param message the message
   * @param element the element
   */
  protected static void syntaxError(String message, Element element) {
    throw new SyntaxError("Syntax error: " + message, XMLUtil.format(element));
  }

  private void unsupportedAttribute(Element element, String attribute) {
    StringBuilder message = renderUnsupportedAttributesMessage(attribute);
    throw createSyntaxError(message.toString(), element);
  }

  /**
   * Render unsupported attributes message string builder.
   *
   * @param attribute the attribute
   * @return the string builder
   */
  StringBuilder renderUnsupportedAttributesMessage(String attribute) {
    StringBuilder message = new StringBuilder("attribute '").append(attribute).append("' is not supported. ");
    message.append("The attributes supported by <" + elementName + "> are: ");
    boolean first = true;
    first = listAttributes(requiredAttributes, message, first);
    listAttributes(optionalAttributes, message, first);
    return message;
  }

  private static boolean listAttributes(Set<String> supportedAttributes, StringBuilder message, boolean first) {
    for (String supportedAttribute : supportedAttributes) {
      if (first) {
        first = false;
      } else {
        message.append(", ");
      }
      message.append(supportedAttribute);
    }
    return first;
  }

  /**
   * Syntax warning.
   *
   * @param message the message
   * @param element the element
   */
  protected void syntaxWarning(String message, Element element) {
    logger.warn("Syntax warning: " + message + " in " + XMLUtil.format(element));
  }

}
