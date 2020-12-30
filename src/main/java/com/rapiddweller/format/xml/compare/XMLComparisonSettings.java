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

import com.rapiddweller.format.compare.ComparisonSettings;

/**
 * Provides settings and models for the comparison of XML documents.
 * Created: 19.11.2015 15:11:03
 * @since 1.0.5
 * @author Volker Bergmann
 */

public class XMLComparisonSettings extends ComparisonSettings {

    private boolean encodingRelevant;

	public XMLComparisonSettings() {
		this(new DefaultXMLComparisonModel());
	}

	public XMLComparisonSettings(XMLComparisonModel model) {
		super(model);
		setEncodingRelevant(false);
        setNamespaceRelevant(false);
        setWhitespaceRelevant(false);
        setCdataRelevant(true);
        setProcessingInstructionRelevant(false);
	}

	@Override
	public XMLComparisonModel getModel() {
		return (XMLComparisonModel) super.getModel();
	}
	
	public void setModel(XMLComparisonModel model) {
		this.model = model;
	}
	
    public boolean isEncodingRelevant() {
		return encodingRelevant;
	}

	public void setEncodingRelevant(boolean encodingRelevant) {
		this.encodingRelevant = encodingRelevant;
	}

	/** Tells if namespaces are relevant
     * @return true if namespace is relevant, otherwise false */
    public boolean isNamespaceRelevant() {
        return getModel().isNamespaceRelevant();
    }

    /** Sets if namespaces are relevant
     * @param namespaceRelevant true if namespace shall be relevant, otherwise false */
    public void setNamespaceRelevant(boolean namespaceRelevant) {
        getModel().setNamespaceRelevant(namespaceRelevant);
    }

    /** Tells if whitespace is relevant
     * @return true if whitespace is relevant, otherwise false */
    public boolean isWhitespaceRelevant() {
        return getModel().isWhitespaceRelevant();
    }

    /** Sets if whitespace is relevant
     * @param whitespaceRelevant true if whitespace shall be relevant, otherwise false */
    public void setWhitespaceRelevant(boolean whitespaceRelevant) {
    	getModel().setWhitespaceRelevant(whitespaceRelevant);
    }

    /** Tells if comments are relevant
     * @return true if whitespace is relevant, otherwise false */
    public boolean isCommentRelevant() {
        return getModel().isCommentRelevant();
    }

    /** Sets if comments are relevant
     * @param commentRelevant true if whitespace shall be relevant, otherwise false */
    public void setCommentRelevant(boolean commentRelevant) {
    	getModel().setCommentRelevant(commentRelevant);
    }

    /** Tells if CDATA is relevant
     * @return true if CDATA is relevant, otherwise false */
    public boolean isCdataRelevant() {
        return getModel().isCdataRelevant();
    }

    /** Sets if CDATA is relevant
     * @param cdataRelevant true if CDATA shall be relevant, otherwise false */
    public void setCdataRelevant(boolean cdataRelevant) {
    	getModel().setCdataRelevant(cdataRelevant);
    }

    /** Tells if processing instructions are relevant
     * @return true if processing instructions are relevant, otherwise false */
    public boolean isProcessingInstructionRelevant() {
        return getModel().isProcessingInstructionRelevant();
    }

    /** Sets if processing instructions are relevant
     * @param processingInstructionRelevant true if processing instructions shall be relevant, otherwise false */
    public void setProcessingInstructionRelevant(boolean processingInstructionRelevant) {
    	getModel().setProcessingInstructionRelevant(processingInstructionRelevant);
    }

	public XMLComparisonSettings withCommentRelevant(boolean commentRelevant) {
		setCommentRelevant(commentRelevant);
		return this;
	}

}
