/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.format.xml;

import com.rapiddweller.common.Expression;
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
public class AttrInfo<E> implements Named {

  private final String name;
  private boolean required;
  private String errorId;
  private final E defaultValue;
  private final Parser<E> parser;

  public AttrInfo(String name, boolean required, String errorId, Parser<E> parser) {
    this(name, required, errorId, parser, (String) null);
  }

  public AttrInfo(String name, boolean required, String errorId, Parser<E> parser, String defaultValue) {
    this(name, required, errorId, parser, parseDefaultValue(defaultValue, parser));
  }

  protected AttrInfo(String name, boolean required, String errorId, Parser<E> parser, E defaultValue) {
    this(name, required, errorId, parser, defaultValue, null);
  }

  /** @param dummyArg is necessary as a workaround to resolve constructor matching issues
   *                 for instances that use String for the generic parameter E */
  @SuppressWarnings("unused")
  protected AttrInfo(String name, boolean required, String errorId, Parser<E> parser, E defaultValue, String dummyArg) {
    this.name = name;
    this.required = required;
    this.errorId = errorId;
    this.defaultValue = defaultValue;
    this.parser = parser;
  }

  @Override
  public String getName() {
    return name;
  }

  public boolean isRequired() {
    return required;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public E getDefaultValue() {
    return defaultValue;
  }

  public String getErrorId() {
    return errorId;
  }

  public void setErrorId(String errorId) {
    this.errorId = errorId;
  }

  @SuppressWarnings("unchecked")
  public E parse(Element element) {
    Attr attr = element.getAttributeNode(name);
    if (attr != null) {
      if (parser != null) {
        try {
          return parseImpl(attr);
        } catch (Exception e) {
          throw ExceptionFactory.getInstance().illegalXmlAttributeValue(null, e, errorId, attr);
        }
      } else {
        return (E) attr.getValue();
      }
    } else if (required) {
      throw ExceptionFactory.getInstance().missingXmlAttribute(null, errorId, name, element);
    } else {
      return defaultValue;
    }
  }

  @SuppressWarnings({"unchecked", "raw"})
  private E parseImpl(Attr attr) {
    String value = attr.getValue();
    E result = parser.parse(value);
    if (result instanceof Expression) {
      return (E) new AttrExpression((Expression) result, attr, errorId);
    } else {
      return result;
    }
  }

  public boolean isDefinedIn(Element element) {
    return element.hasAttribute(name);
  }

  private static <T> T parseDefaultValue(String defaultValue, Parser<T> parser) {
    return (parser != null && defaultValue != null ? parser.parse(defaultValue) : null);
  }

}
