/*
 * Copyright (C) 2011-2021 Volker Bergmann (volker.bergmann@bergmann-it.de).
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

package com.rapiddweller.format.csv;

/**
 * Enumeration of the tokens of a CSV file.<br/><br/>
 * Created: 26.08.2006 17:34:11
 * @author Volker Bergmann
 */
public enum CSVTokenType {

  /** indicates a value from a cell in the CSV source */
  CELL,

  /** Indicates the end of a line */
  EOL,

  /** Indicates the end of the file */
  EOF
}
