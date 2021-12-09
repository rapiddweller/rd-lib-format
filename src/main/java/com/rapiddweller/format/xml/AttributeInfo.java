/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.format.xml;

import com.rapiddweller.common.Named;
import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.common.parser.Parser;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

/**
 * Defines the properties and requirements of an XML element's attribute.<br/><br/>
 * Created: 29.11.2021 11:12:24
 * @author Volker Bergmann
 * @since 2.1.0
 */
public class AttributeInfo<E> implements Named {

  private final String name;
  private final boolean required;
  private final String errorId;
  private final String defaultValue;
  private final Parser<E> parser;

  public AttributeInfo(String name, boolean required, String errorId, String defaultValue, Parser<E> parser) {
    this.name = name;
    this.required = required;
    this.errorId = errorId;
    this.defaultValue = defaultValue;
    this.parser = parser;
    if (parser != null && defaultValue != null) {
      parser.parse(defaultValue); // check if the defaultValue is legal
    }
  }

  @Override
  public String getName() {
    return name;
  }

  public boolean isRequired() {
    return required;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public String getErrorId() {
    return errorId;
  }

  public E parse(Element element) {
    Attr attr = element.getAttributeNode(name);
    if (attr != null) {
      String value = attr.getValue();
      if (parser != null) {
        try {
          return parser.parse(value);
        } catch (Exception e) {
          throw ExceptionFactory.getInstance().illegalXmlAttributeValue(null, e, errorId, attr);
        }
      } else {
        return (E) value;
      }
    } else if (required) {
      throw ExceptionFactory.getInstance().missingXmlAttribute(null, errorId, name, element);
    } else if (defaultValue != null) {
      return parser.parse(defaultValue);
    } else {
      return null;
    }
  }

  public boolean isDefinedIn(Element element) {
    return element.hasAttribute(name);
  }

}
