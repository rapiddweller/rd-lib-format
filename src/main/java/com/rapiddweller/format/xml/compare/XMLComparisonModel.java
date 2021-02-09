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

package com.rapiddweller.format.xml.compare;

import com.rapiddweller.format.compare.ComparisonModel;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Parent interface for XML comparison models.
 * Created: 19.11.2015 15:14:33
 *
 * @author Volker Bergmann
 * @since 1.0.5
 */
public interface XMLComparisonModel extends ComparisonModel {

  /**
   * The constant DOCUMENT.
   */
  String DOCUMENT = "document";
  /**
   * The constant DOCUMENT_ENCODING.
   */
  String DOCUMENT_ENCODING = "document encoding";
  /**
   * The constant ELEMENT.
   */
  String ELEMENT = "element";
  /**
   * The constant ELEMENT_NAMESPACE.
   */
  String ELEMENT_NAMESPACE = "element namespace";
  /**
   * The constant ELEMENT_NAME.
   */
  String ELEMENT_NAME = "element name";
  /**
   * The constant ELEMENT_TEXT.
   */
  String ELEMENT_TEXT = "element text";
  /**
   * The constant ATTRIBUTE.
   */
  String ATTRIBUTE = "attribute value";
  /**
   * The constant TEXT.
   */
  String TEXT = "text";
  /**
   * The constant COMMENT.
   */
  String COMMENT = "comment";
  /**
   * The constant PROCESSING_INSTRUCTION.
   */
  String PROCESSING_INSTRUCTION = "processing instruction";

  /**
   * Is namespace relevant boolean.
   *
   * @return the boolean
   */
  boolean isNamespaceRelevant();

  /**
   * Sets namespace relevant.
   *
   * @param namespaceRelevant the namespace relevant
   */
  void setNamespaceRelevant(boolean namespaceRelevant);

  /**
   * Is whitespace relevant boolean.
   *
   * @return the boolean
   */
  boolean isWhitespaceRelevant();

  /**
   * Sets whitespace relevant.
   *
   * @param whitespaceRelevant the whitespace relevant
   */
  void setWhitespaceRelevant(boolean whitespaceRelevant);

  /**
   * Is comment relevant boolean.
   *
   * @return the boolean
   */
  boolean isCommentRelevant();

  /**
   * Sets comment relevant.
   *
   * @param commentRelevant the comment relevant
   */
  void setCommentRelevant(boolean commentRelevant);

  /**
   * Is cdata relevant boolean.
   *
   * @return the boolean
   */
  boolean isCdataRelevant();

  /**
   * Sets cdata relevant.
   *
   * @param cdataRelevant the cdata relevant
   */
  void setCdataRelevant(boolean cdataRelevant);

  /**
   * Is processing instruction relevant boolean.
   *
   * @return the boolean
   */
  boolean isProcessingInstructionRelevant();

  /**
   * Sets processing instruction relevant.
   *
   * @param processingInstructionRelevant the processing instruction relevant
   */
  void setProcessingInstructionRelevant(boolean processingInstructionRelevant);

  /**
   * Init.
   *
   * @param document1 the document 1
   * @param document2 the document 2
   */
  void init(Document document1, Document document2);

  /**
   * Child nodes node [ ].
   *
   * @param parent the parent
   * @return the node [ ]
   */
  Node[] childNodes(Node parent);
}
