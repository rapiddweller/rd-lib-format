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

import com.rapiddweller.common.NullSafeComparator;
import com.rapiddweller.common.ProgrammerError;
import com.rapiddweller.common.xml.XPathUtil;
import com.rapiddweller.format.compare.DiffDetailType;
import com.rapiddweller.format.compare.LocalDiffType;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Holds the information which diff types are accepted at which XPaths
 * and maps them to the related nodes of XML documents.
 * Created: 09.07.2014 10:27:00
 *
 * @author Volker Bergmann
 * @since 1.0.5
 */
public class ComparisonContext {

  private List<PathSetting> pathSettings;

  /**
   * Instantiates a new Comparison context.
   */
  public ComparisonContext() {
    try {
      init(null, null, null);
    } catch (XPathExpressionException e) {
      throw new ProgrammerError(e);
    }
  }

  /**
   * Instantiates a new Comparison context.
   *
   * @param toleratedDiffs   the tolerated diffs
   * @param expectedDocument the expected document
   * @param actualDocument   the actual document
   * @throws XPathExpressionException the x path expression exception
   */
  public ComparisonContext(Set<LocalDiffType> toleratedDiffs, Document expectedDocument, Document actualDocument)
      throws XPathExpressionException {
    init(toleratedDiffs, expectedDocument, actualDocument);
  }

  private void init(Set<LocalDiffType> toleratedDiffs, Document expectedDocument, Document actualDocument)
      throws XPathExpressionException {
    this.pathSettings = new ArrayList<>();
    if (toleratedDiffs != null) {
      for (LocalDiffType localDiffType : toleratedDiffs) {
        PathSetting pathSetting = new PathSetting(localDiffType.getLocator(), localDiffType.getType());
        pathSetting.collectAffectedNodes(expectedDocument);
        pathSetting.collectAffectedNodes(actualDocument);
        this.pathSettings.add(pathSetting);
      }
    }
  }

  /**
   * Is excluded boolean.
   *
   * @param node the node
   * @return the boolean
   */
  public boolean isExcluded(Object node) {
    for (PathSetting pathSetting : pathSettings) {
      if (pathSetting.isAffectedNode(node) && pathSetting.getDiffType() == null) {
        return true;
      }
    }
    return false;
  }

  /**
   * Is tolerated boolean.
   *
   * @param type     the type
   * @param expected the expected
   * @param actual   the actual
   * @return the boolean
   */
  public boolean isTolerated(DiffDetailType type, Object expected, Object actual) {
    for (PathSetting pathSetting : pathSettings) {
      Object diffType = pathSetting.getDiffType();
      if ((pathSetting.isAffectedNode(expected) || pathSetting.isAffectedNode(actual)) &&
          (diffType == null || diffType == type)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Is tolerated boolean.
   *
   * @param diffType the diff type
   * @param locator  the locator
   * @return the boolean
   */
  public boolean isTolerated(DiffDetailType diffType, String locator) {
    for (PathSetting pathSetting : pathSettings) {
      if (NullSafeComparator.equals(locator, pathSetting.getLocator())
          && (pathSetting.getDiffType() == null || pathSetting.getDiffType().equals(diffType))) {
        return true;
      }
    }
    return false;
  }


  // helper class ----------------------------------------------------------------------------------------------------

  /**
   * The type Path setting.
   */
  static class PathSetting {
    private final String locator;
    private final DiffDetailType diffType;
    private final List<Object> affectedNodes;

    private PathSetting(String locator, DiffDetailType type) {
      this.locator = locator;
      this.diffType = type;
      this.affectedNodes = new ArrayList<>();
    }

    /**
     * Gets locator.
     *
     * @return the locator
     */
    public String getLocator() {
      return locator;
    }

    /**
     * Gets diff type.
     *
     * @return the diff type
     */
    public Object getDiffType() {
      return diffType;
    }

    /**
     * Is affected node boolean.
     *
     * @param node the node
     * @return the boolean
     */
    public boolean isAffectedNode(Object node) {
      if (locator == null) {
        return true;
      }
      for (Object affectedNode : affectedNodes) {
        if (node != null && node.equals(affectedNode)) {
          return true;
        }
      }
      return false;
    }

    /**
     * Collect affected nodes.
     *
     * @param document the document
     * @throws XPathExpressionException the x path expression exception
     */
    public void collectAffectedNodes(Document document) throws XPathExpressionException {
      if (this.locator != null && document != null) {
        NodeList foundNodes = XPathUtil.queryNodes(document, locator);
        for (int i = 0; i < foundNodes.getLength(); i++) {
          affectedNodes.add(foundNodes.item(i));
        }
      }
    }

  }

}
