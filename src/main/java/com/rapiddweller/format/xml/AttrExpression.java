/* (c) Copyright 2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.format.xml;

import com.rapiddweller.common.Context;
import com.rapiddweller.common.Expression;
import com.rapiddweller.common.exception.ExceptionFactory;
import org.w3c.dom.Attr;

/**
 * Expression in an XML attribute.<br/><br/>
 * Created: 17.12.2021 07:12:28
 * @author Volker Bergmann
 * @since 1.1.5
 */
public class AttrExpression<E> implements Expression<E> {

  private final Expression<E> realExpression;
  private final Attr attr;
  private final String errorId;

  public AttrExpression(Expression<E> realExpression, Attr attr, String errorId) {
    this.realExpression = realExpression;
    this.attr = attr;
    this.errorId = errorId;
  }

  @Override
  public E evaluate(Context context) {
    try {
      return realExpression.evaluate(context);
    } catch (Exception e) {
      throw ExceptionFactory.getInstance().illegalXmlAttributeValue(
          "Error resolving expression '" + realExpression + "'", e, errorId, attr);
    }
  }

  @Override
  public boolean isConstant() {
    return realExpression.isConstant();
  }

}
