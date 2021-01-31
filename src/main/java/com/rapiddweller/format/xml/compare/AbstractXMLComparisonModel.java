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

import org.w3c.dom.*;

/**
 * Abstract implementation of the {@link XMLComparisonModel} interface.
 * Created: 20.11.2015 17:12:36
 * @since 1.0.5
 * @author Volker Bergmann
 */

public abstract class AbstractXMLComparisonModel implements XMLComparisonModel {

    protected boolean namespaceRelevant;

    /** Indicates if white space is relevant. */
    protected boolean whitespaceRelevant;

    protected boolean cdataRelevant;

    protected boolean processingInstructionRelevant;

    protected boolean commentRelevant;
	
    @Override
    public boolean isNamespaceRelevant() {
    	return namespaceRelevant;
    }
    
	@Override
	public void setNamespaceRelevant(boolean naespaceRelevant) {
		this.namespaceRelevant = naespaceRelevant;
	}
	
	@Override
	public boolean isWhitespaceRelevant() {
		return whitespaceRelevant;
	}

	@Override
	public void setWhitespaceRelevant(boolean whitespaceRelevant) {
		this.whitespaceRelevant = whitespaceRelevant;
	}

	@Override
	public boolean isCdataRelevant() {
		return cdataRelevant;
	}

	@Override
	public void setCdataRelevant(boolean cdataRelevant) {
		this.cdataRelevant = cdataRelevant;
	}

	@Override
	public boolean isProcessingInstructionRelevant() {
		return processingInstructionRelevant;
	}

	@Override
	public void setProcessingInstructionRelevant(boolean processingInstructionRelevant) {
		this.processingInstructionRelevant = processingInstructionRelevant;
	}

	@Override
	public boolean isCommentRelevant() {
		return commentRelevant;
	}

	@Override
	public void setCommentRelevant(boolean commentRelevant) {
		this.commentRelevant = commentRelevant;
	}

	@Override
	public String classifierOf(Object object) {
		if (object instanceof Document)
			return DOCUMENT;
		else if (object instanceof Element)
			return ELEMENT;
		else if (object instanceof Comment)
			return COMMENT;
		else if (object instanceof Text)
			return TEXT;
		else if (object instanceof ProcessingInstruction)
			return PROCESSING_INSTRUCTION;
		throw new UnsupportedOperationException("Not a supported type: " + object.getClass());
	}

}
