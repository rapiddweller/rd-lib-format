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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPathExpressionException;

import com.rapiddweller.common.NullSafeComparator;
import com.rapiddweller.common.ProgrammerError;
import com.rapiddweller.common.xml.XPathUtil;
import com.rapiddweller.format.compare.DiffDetailType;
import com.rapiddweller.format.compare.LocalDiffType;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * Holds the information which diff types are accepted at which XPaths 
 * and maps them to the related nodes of XML documents.
 * Created: 09.07.2014 10:27:00
 * @since 1.0.5
 * @author Volker Bergmann
 */

public class ComparisonContext {
	
	private List<PathSetting> pathSettings;
	
	public ComparisonContext() {
		try {
			init(null, null, null);
		} catch (XPathExpressionException e) {
			throw new ProgrammerError(e);
		}
	}

	public ComparisonContext(Set<LocalDiffType> toleratedDiffs, Document expectedDocument, Document actualDocument) 
			throws XPathExpressionException {
		init(toleratedDiffs, expectedDocument, actualDocument);
	}

	private void init(Set<LocalDiffType> toleratedDiffs, Document expectedDocument, Document actualDocument)
			throws XPathExpressionException {
		this.pathSettings = new ArrayList<PathSetting>();
		if (toleratedDiffs != null) {
			for (LocalDiffType localDiffType : toleratedDiffs) {
				PathSetting pathSetting = new PathSetting(localDiffType.getLocator(), localDiffType.getType());
				pathSetting.collectAffectedNodes(expectedDocument);
				pathSetting.collectAffectedNodes(actualDocument);
				this.pathSettings.add(pathSetting);
			}
		}
	}

	public boolean isExcluded(Object node) {
		for (PathSetting pathSetting : pathSettings)
			if (pathSetting.isAffectedNode(node) && pathSetting.getDiffType() == null)
				return true;
		return false;
	}

	public boolean isTolerated(DiffDetailType type, Object expected, Object actual) {
		for (PathSetting pathSetting : pathSettings) {
			Object diffType = pathSetting.getDiffType();
			if ((pathSetting.isAffectedNode(expected) || pathSetting.isAffectedNode(actual)) && 
					(diffType == null || diffType == type))
				return true;
		}
		return false;
	}

	public boolean isTolerated(DiffDetailType diffType, String locator) {
		for (PathSetting pathSetting : pathSettings) {
			if (NullSafeComparator.equals(locator, pathSetting.getLocator()) 
					&& (pathSetting.getDiffType() == null || pathSetting.getDiffType() == diffType))
				return true;
		}
		return false;
	}


	// helper class ----------------------------------------------------------------------------------------------------

	static class PathSetting {
		private final String locator;
		private final DiffDetailType diffType;
		private final List<Object> affectedNodes;
		
		private PathSetting(String locator, DiffDetailType type) {
			this.locator = locator;
			this.diffType = type;
			this.affectedNodes = new ArrayList<Object>();
		}
		
		public String getLocator() {
			return locator;
		}
		
		public Object getDiffType() {
			return diffType;
		}

		public boolean isAffectedNode(Object node) {
			if (locator == null)
				return true;
			for (Object affectedNode : affectedNodes)
				if (node == affectedNode)
					return true;
			return false;
		}

		public void collectAffectedNodes(Document document) throws XPathExpressionException {
			if (this.locator != null && document != null) {
				NodeList foundNodes = XPathUtil.queryNodes(document, locator);
				for (int i = 0; i < foundNodes.getLength(); i++)
					affectedNodes.add(foundNodes.item(i));
			}
		}

	}

}
