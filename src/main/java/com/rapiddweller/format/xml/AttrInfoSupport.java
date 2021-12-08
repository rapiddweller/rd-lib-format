/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.format.xml;

import com.rapiddweller.common.collection.OrderedNameMap;
import com.rapiddweller.common.exception.ExceptionFactory;
import org.w3c.dom.Element;

import java.util.Collection;

/**
 * Manages {@link AttributeInfo}s.<br/><br/>
 * Created: 29.11.2021 11:22:52
 * @author Volker Bergmann
 * @since 2.1.0
 */
public class AttrInfoSupport {

  private final String errorIdForIllegalAttribute;
  private final OrderedNameMap<AttributeInfo> map;

  public AttrInfoSupport(String errorIdForIllegalAttribute) {
    this.errorIdForIllegalAttribute = errorIdForIllegalAttribute;
    this.map = new OrderedNameMap<>();
  }

  public String getErrorIdForIllegalAttribute() {
    return errorIdForIllegalAttribute;
  }

  public void add(String name, boolean required, String errorId) {
    map.put(name, new AttributeInfo(name, required, errorId));
  }

  public AttributeInfo get(String name) {
    return map.get(name);
  }

  public Collection<AttributeInfo> getAll() {
    return map.values();
  }

  public String getErrorId(String attrName) {
    AttributeInfo tmp = map.get(attrName);
    if (tmp == null) {
      throw ExceptionFactory.getInstance().internalError(
          "Requested info for an illegal attribute", null);
    }
    return tmp.getErrorId();
  }

  public void validate(Element element) {
    for (AttributeInfo info : map.values()) {
      info.validate(element);
    }
  }

}
