/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.format.xml;

import com.rapiddweller.common.Validator;
import com.rapiddweller.common.collection.OrderedNameMap;
import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.common.parser.Parser;
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
  private final OrderedNameMap<AttributeInfo<?>> map;
  private final Validator<Element> validator;

  public AttrInfoSupport(String errorIdForIllegalAttribute, AttributeInfo<?>... attrInfos) {
    this(errorIdForIllegalAttribute, null, attrInfos);
  }

  public AttrInfoSupport(String errorIdForIllegalAttribute, Validator<Element> validator, AttributeInfo<?>... attrInfos) {
    this.errorIdForIllegalAttribute = errorIdForIllegalAttribute;
    this.map = new OrderedNameMap<>();
    this.validator = validator;
    addAll(attrInfos);
  }

  public String getErrorIdForIllegalAttribute() {
    return errorIdForIllegalAttribute;
  }

  public void add(String name, boolean required, String errorId) {
    map.put(name, new AttributeInfo<>(name, required, errorId, null, null));
  }

  public <T> AttributeInfo<T> add(String name, boolean required, String errorId, String defaultValue, Parser<T> parser) {
    AttributeInfo<T> attributeInfo = new AttributeInfo<>(name, required, errorId, defaultValue, parser);
    map.put(name, attributeInfo);
    return attributeInfo;
  }

  public AttributeInfo<?> get(String name) {
    return map.get(name);
  }

  public Collection<AttributeInfo<?>> getAll() {
    return map.values();
  }

  public boolean hasAttribute(String name) {
    return map.containsKey(name);
  }

  public String getErrorId(String attrName) {
    AttributeInfo<?> tmp = map.get(attrName);
    if (tmp == null) {
      throw ExceptionFactory.getInstance().internalError(
          "Requested info for an illegal attribute", null);
    }
    return tmp.getErrorId();
  }

  public void validate(Element element) {
    for (AttributeInfo<?> info : map.values()) {
      info.parse(element);
    }
    if (validator != null && !validator.valid(element)) {
      throw ExceptionFactory.getInstance().syntaxErrorForXmlElement(
          validator.toString(), null, errorIdForIllegalAttribute, element);
    }
  }

  public void addAll(AttributeInfo<?>... attrInfos) {
    for (AttributeInfo<?> attrInfo : attrInfos) {
      map.put(attrInfo.getName(), attrInfo);
    }
  }

}
