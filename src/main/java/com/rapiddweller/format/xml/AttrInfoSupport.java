/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.format.xml;

import com.rapiddweller.common.Validator;
import com.rapiddweller.common.collection.OrderedNameMap;
import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.common.parser.Parser;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

import java.util.Collection;

/**
 * Manages {@link AttrInfo}s.<br/><br/>
 * Created: 29.11.2021 11:22:52
 * @author Volker Bergmann
 * @since 2.1.0
 */
public class AttrInfoSupport {

  private final String errorIdForIllegalAttribute;
  private final OrderedNameMap<AttrInfo<?>> map;
  private final Validator<Element> validator;

  public AttrInfoSupport(String errorIdForIllegalAttribute) {
    this(errorIdForIllegalAttribute, new AttrInfo<?>[0]);
  }

  public AttrInfoSupport(String errorIdForIllegalAttribute, AttrInfo<?>... attrInfos) {
    this(errorIdForIllegalAttribute, null, attrInfos);
  }

  public AttrInfoSupport(String errorIdForIllegalAttribute, Validator<Element> validator, AttrInfo<?>... attrInfos) {
    this.errorIdForIllegalAttribute = errorIdForIllegalAttribute;
    this.map = new OrderedNameMap<>();
    this.validator = validator;
    addAll(attrInfos);
  }

  public String getErrorIdForIllegalAttribute() {
    return errorIdForIllegalAttribute;
  }

  public void add(String name, boolean required, String errorId) {
    add(new AttrInfo<>(name, required, errorId, null));
  }

  public <T> AttrInfo<T> add(String name, boolean required, String errorId, String defaultValue, Parser<T> parser) {
    AttrInfo<T> attrInfo = new AttrInfo<>(name, required, errorId, parser, defaultValue);
    add(attrInfo);
    return attrInfo;
  }

  public void add(AttrInfo<?> attrInfo) {
    map.put(attrInfo.getName(), attrInfo);
  }

  public AttrInfo<?> get(String name) {
    return map.get(name);
  }

  public Collection<AttrInfo<?>> getAll() {
    return map.values();
  }

  public boolean hasAttribute(String name) {
    return map.containsKey(name);
  }

  public String getErrorId(String attrName) {
    AttrInfo<?> tmp = map.get(attrName);
    if (tmp == null) {
      throw ExceptionFactory.getInstance().internalError(
          "Requested info for an illegal attribute", null);
    }
    return tmp.getErrorId();
  }

  public void validate(Element element) {
    for (AttrInfo<?> info : map.values()) {
      info.parse(element);
    }
    NamedNodeMap attributes = element.getAttributes();
    for (int i = 0; i < attributes.getLength(); i++) {
      Attr attr = (Attr) attributes.item(i);
      if ("xmlns".equals(attr.getName()) || "xmlns:xsi".equals(attr.getName())
          || "xmlns:schemaLocation".equals(attr.getName()) || "xsi:schemaLocation".equals(attr.getName())) { // TODO improve this
        continue;
      }
      AttrInfo<?> info = get(attr.getName());
      if (info == null) {
        throw ExceptionFactory.getInstance().illegalXmlAttributeName(
            null, null, errorIdForIllegalAttribute, attr, null);
      }
    }
    if (validator != null && !validator.valid(element)) {
      throw ExceptionFactory.getInstance().syntaxErrorForXmlElement(
          validator.toString(), null, errorIdForIllegalAttribute, element);
    }
  }

  public void addAll(AttrInfo<?>... attrInfos) {
    for (AttrInfo<?> attrInfo : attrInfos) {
      map.put(attrInfo.getName(), attrInfo);
    }
  }

}
