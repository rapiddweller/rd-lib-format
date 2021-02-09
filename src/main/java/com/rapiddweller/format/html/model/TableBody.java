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
 * Represents an HTML table body (&lt;tbody&gt;).
 * Created: 09.07.2014 14:24:37
 *
 * @author Volker Bergmann
 * @since 0.8.4
 */
public class TableBody extends HtmlElement<TableBody> {

  /**
   * Instantiates a new Table body.
   */
  public TableBody() {
    super("tbody", false);
  }

  /**
   * New row table row.
   *
   * @return the table row
   */
  public TableRow newRow() {
    TableRow row = new TableRow();
    addRow(row);
    return row;
  }

  /**
   * Add row table body.
   *
   * @param row the row
   * @return the table body
   */
  public TableBody addRow(TableRow row) {
    return addComponent(row);
  }

}
