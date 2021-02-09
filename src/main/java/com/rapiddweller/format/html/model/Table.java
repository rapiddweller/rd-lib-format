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
 * Represents an HTML table.
 * Created: 06.01.2014 09:17:54
 *
 * @author Volker Bergmann
 * @since 0.7.1
 */
public class Table extends HtmlElement<Table> {

  /**
   * Instantiates a new Table.
   */
  public Table() {
    super("table", false);
  }

  /**
   * With cellspacing table.
   *
   * @param value the value
   * @return the table
   */
  public Table withCellspacing(String value) {
    return withAttribute("cellspacing", value);
  }

  /**
   * New table head table head.
   *
   * @return the table head
   */
  public TableHead newTableHead() {
    TableHead head = new TableHead();
    addComponent(head);
    return head;
  }

  /**
   * New table body table body.
   *
   * @return the table body
   */
  public TableBody newTableBody() {
    TableBody body = new TableBody();
    addComponent(body);
    return body;
  }

  /**
   * New table foot table foot.
   *
   * @return the table foot
   */
  public TableFoot newTableFoot() {
    TableFoot foot = new TableFoot();
    addComponent(foot);
    return foot;
  }

  /**
   * Add row table.
   *
   * @param row the row
   * @return the table
   */
  public Table addRow(TableRow row) {
    return addComponent(row);
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

}
