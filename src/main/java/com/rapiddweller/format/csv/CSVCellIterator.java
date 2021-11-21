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

import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.DataIterator;

import java.io.IOException;

/**
 * Iterates through cells of a CSV file.<br/><br/>
 * Created: 26.08.2006 18:52:08
 * @author Volker Bergmann
 */
public class CSVCellIterator implements DataIterator<String> {


  private final String uri;

  private final char separator;

  /** The tokenizer for CSV file access */
  private CSVTokenizer tokenizer;

  // constructors ----------------------------------------------------------------------------------------------------

  public CSVCellIterator(String uri, char separator, String encoding) throws IOException {
    this.uri = uri;
    this.separator = separator;
    this.tokenizer = new CSVTokenizer(uri, separator, encoding);
    skipEOLs();
  }

  // properties ------------------------------------------------------------------------------------------------------

  public String getUri() {
    return uri;
  }

  public char getSeparator() {
    return separator;
  }

  // Iterator implementation -----------------------------------------------------------------------------------------

  @Override
  public Class<String> getType() {
    return String.class;
  }

  @Override
  public DataContainer<String> next(DataContainer<String> wrapper) {
    if (tokenizer == null) {
      return null;
    }
    String result = tokenizer.cell;
    skipEOLs();
    if (tokenizer.ttype == CSVTokenType.EOF) {
      close();
    }
    return wrapper.setData(result);
  }

  public void remove() {
    throw new UnsupportedOperationException("Operation not supported: remove()");
  }

  @Override
  public void close() {
    if (tokenizer != null) {
      tokenizer.close();
      tokenizer = null;
    }
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private void skipEOLs() {
    try {
      do {
        tokenizer.next();
      } while (tokenizer.ttype == CSVTokenType.EOL);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

}
