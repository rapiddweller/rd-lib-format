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
package com.rapiddweller.formats.xml.compare;

import com.rapiddweller.formats.compare.ComparisonModel;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Parent interface for XML comparison models.
 * Created: 19.11.2015 15:14:33
 * @since 1.0.5
 * @author Volker Bergmann
 */

public interface XMLComparisonModel extends ComparisonModel {
	
	public static final String DOCUMENT = "document";
	public static final String DOCUMENT_ENCODING = "document encoding";
	public static final String ELEMENT = "element";
	public static final String ELEMENT_NAMESPACE = "element namespace";
	public static final String ELEMENT_NAME = "element name";
	public static final String ELEMENT_TEXT = "element text";
	public static final String ATTRIBUTE = "attribute value";
	public static final String TEXT = "text";
	public static final String COMMENT = "comment";
	public static final String PROCESSING_INSTRUCTION = "processing instruction";
	
	boolean isNamespaceRelevant();
	void setNamespaceRelevant(boolean namespaceRelevant);
	boolean isWhitespaceRelevant();
	void setWhitespaceRelevant(boolean whitespaceRelevant);
	boolean isCommentRelevant();
	void setCommentRelevant(boolean commentRelevant);
	boolean isCdataRelevant();
	void setCdataRelevant(boolean cdataRelevant);
	boolean isProcessingInstructionRelevant();
	void setProcessingInstructionRelevant(boolean processingInstructionRelevant);
	
	void init(Document document1, Document document2);
	Node[] childNodes(Node parent);
}
