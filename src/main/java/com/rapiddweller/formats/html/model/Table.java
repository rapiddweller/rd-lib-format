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
package com.rapiddweller.formats.html.model;

/**
 * Represents an HTML table.
 * Created: 06.01.2014 09:17:54
 * @since 0.7.1
 * @author Volker Bergmann
 */

public class Table extends HtmlElement<Table> {

	public Table() {
		super("table", false);
	}
	
	public Table withCellspacing(String value) {
		return withAttribute("cellspacing", value);
	}

	public TableHead newTableHead() {
		TableHead head = new TableHead();
		addComponent(head);
		return head;
	}
	
	public TableBody newTableBody() {
		TableBody body = new TableBody();
		addComponent(body);
		return body;
	}

	public TableFoot newTableFoot() {
		TableFoot foot = new TableFoot();
		addComponent(foot);
		return foot;
	}

	public Table addRow(TableRow row) {
		return addComponent(row);
	}

	public TableRow newRow() {
		TableRow row = new TableRow();
		addRow(row);
		return row;
	}

}
