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

package com.rapiddweller.format.html.model;

/**
 * Represents an HTML table row.
 * Created: 06.01.2014 09:19:03
 *
 * @author Volker Bergmann
 * @since 0.7.1
 */
public class TableRow extends HtmlElement<TableRow> {

  /**
   * Instantiates a new Table row.
   */
  public TableRow() {
    super("tr", false);
  }

  /**
   * Add cell table row.
   *
   * @param cell the cell
   * @return the table row
   */
  public TableRow addCell(TableCell cell) {
    return addComponent(cell);
  }

  /**
   * New cell table cell.
   *
   * @param text the text
   * @return the table cell
   */
  public TableCell newCell(String text) {
    return newCell(new TextComponent(text));
  }

  /**
   * New cell table cell.
   *
   * @param components the components
   * @return the table cell
   */
  public TableCell newCell(HtmlComponent... components) {
    TableCell cell = new TableCell(components);
    addCell(cell);
    return cell;
  }

}
