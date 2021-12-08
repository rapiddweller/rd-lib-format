/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.format.xml;

import com.rapiddweller.common.Named;
import com.rapiddweller.common.exception.ExceptionFactory;
import org.w3c.dom.Element;

/**
 * Defines the properties and requirements of an XML element's attribute.<br/><br/>
 * Created: 29.11.2021 11:12:24
 * @author Volker Bergmann
 * @since 2.1.0
 */
public class AttributeInfo implements Named {

  private final String name;
  private final boolean required;
  private final String errorId;

  public AttributeInfo(String name, boolean required, String errorId) {
    this.name = name;
    this.required = required;
    this.errorId = errorId;
  }

  @Override
  public String getName() {
    return name;
  }

  public boolean isRequired() {
    return required;
  }

  public String getErrorId() {
    return errorId;
  }

  public void validate(Element element) {
    if (required && !element.hasAttribute(name)) {
      throw ExceptionFactory.getInstance().missingXmlAttribute(null, errorId, name, element);
    }
  }

}
