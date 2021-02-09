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

package com.rapiddweller.format.csv;

import com.rapiddweller.common.ConversionException;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.DataIterator;

import java.io.IOException;

/**
 * Iterates through cells of a CSV file.
 * <p>
 * Created: 26.08.2006 18:52:08
 *
 * @author Volker Bergmann
 */
public class CSVCellIterator implements DataIterator<String> {

  /**
   * The source uri
   */
  private final String uri;

  private final char separator;

  /**
   * The tokenizer for CSV file access
   */
  private CSVTokenizer tokenizer;

  // constructors ----------------------------------------------------------------------------------------------------

  /**
   * Instantiates a new Csv cell iterator.
   *
   * @param uri       the uri
   * @param separator the separator
   * @param encoding  the encoding
   * @throws IOException the io exception
   */
  public CSVCellIterator(String uri, char separator, String encoding) throws IOException {
    this.uri = uri;
    this.separator = separator;
    this.tokenizer = new CSVTokenizer(uri, separator, encoding);
    skipEOLs();
  }

  // properties ------------------------------------------------------------------------------------------------------

  /**
   * Gets uri.
   *
   * @return the uri
   */
  public String getUri() {
    return uri;
  }

  /**
   * Gets separator.
   *
   * @return the separator
   */
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
    try {
      String result = tokenizer.cell;
      skipEOLs();
      if (tokenizer.ttype == CSVTokenType.EOF) {
        close();
      }
      return wrapper.setData(result);
    } catch (ConversionException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Remove.
   */
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
