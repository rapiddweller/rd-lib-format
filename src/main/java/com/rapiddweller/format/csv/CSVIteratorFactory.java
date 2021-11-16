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

import com.rapiddweller.format.DataIterator;

import java.io.IOException;

/**
 * Factory for all kinds of CSV iterators.
 * Created: 14.10.2009 11:40:31
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class CSVIteratorFactory {

  private CSVIteratorFactory() {
  }

  public static DataIterator<String> createCSVCellIterator(
      String uri, char separator, String encoding) throws IOException {
    return new CSVCellIterator(uri, separator, encoding);
  }

  public static DataIterator<String> createCSVVellIteratorForColumn(
      String uri, int column, char separator, boolean ignoreEmptyLines, String encoding) throws IOException {
    return new CSVSingleColumIterator(uri, column, separator, ignoreEmptyLines, encoding);
  }

  public static DataIterator<String[]> createCSVLineIterator(
      String uri, char separator, boolean ignoreEmptyLines, String encoding) throws IOException {
    return new CSVLineIterator(uri, separator, ignoreEmptyLines, encoding);
  }

}