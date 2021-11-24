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

import com.rapiddweller.common.ArrayBuilder;
import com.rapiddweller.common.CollectionUtil;
import com.rapiddweller.common.FileUtil;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.DataIterator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.rapiddweller.format.csv.CSVTokenType.CELL;
import static com.rapiddweller.format.csv.CSVTokenType.EOF;

/**
 * Gives access to content of a CSV file by String arrays
 * that represent the CSV rows as specified in RFC 4180.
 * @author Volker Bergmann
 * @see <a href="https://www.ietf.org/rfc/rfc4180.txt">https://www.ietf.org/rfc/rfc4180.txt</a>
 */
public class CSVLineIterator implements DataIterator<String[]> {

  /** The default separator to use */
  public static final char DEFAULT_SEPARATOR = ',';

  private static final String[] END_OF_FILE = null;

  private String stringRep;

  private CSVTokenizer tokenizer;

  private String[] nextLine;

  private final boolean ignoreEmptyLines;

  private int lineCount;

  private String[] headers;

  private HashMap<String, Integer> headerIndexes;

  // constructors ----------------------------------------------------------------------------------------------------

  /** Creates a parser that reads from a uri
   *  @param uri the URL to read from */
  public CSVLineIterator(String uri) {
    this(uri, DEFAULT_SEPARATOR);
  }

  /** Creates a parser that reads from a uri
   *  @param uri       the URL to read from
   *  @param separator the cell separator character */
  public CSVLineIterator(String uri, char separator) {
    this(uri, separator, false);
  }

  public CSVLineIterator(String uri, char separator, String encoding) {
    this(uri, separator, false, encoding);
  }

  public CSVLineIterator(String uri, char separator, boolean ignoreEmptyLines) {
    this(uri, separator, ignoreEmptyLines, SystemInfo.getFileEncoding());
  }

  public CSVLineIterator(String uri, char separator, boolean ignoreEmptyLines, String encoding) {
    this(IOUtil.getReaderForURI(uri, encoding), separator, ignoreEmptyLines);
    this.stringRep = uri;
  }

  /** Creates a parser that reads from a reader and used a special separator character
   *  @param reader the reader to use
   *  @param separator the separator character */
  public CSVLineIterator(Reader reader, char separator) {
    this(reader, separator, false);
  }

  public CSVLineIterator(Reader reader, char separator, boolean ignoreEmptyLines) {
    this.tokenizer = new CSVTokenizer(reader, separator);
    this.ignoreEmptyLines = ignoreEmptyLines;
    this.nextLine = parseNextLine();
    this.lineCount = 0;
    this.stringRep = reader.toString();
  }

  // interface -------------------------------------------------------------------------------------------------------

  public void parseHeaders() {
    setHeaders(nextLine);
    this.nextLine = parseNextLine();
  }

  public String[] getHeaders() {
    return this.headers;
  }

  public void setHeaders(String[] headers) {
    this.headers = Objects.requireNonNullElseGet(headers, () -> new String[0]);
    this.headerIndexes = new HashMap<>();
    for (int i = 0; i < this.headers.length; i++) {
      this.headerIndexes.put(this.headers[i], i);
    }
  }

  @Override
  public Class<String[]> getType() {
    return String[].class;
  }

  /** Parses a CSV row into an array of Strings
   *  @return an array of Strings that represents a CSV row */
  @Override
  public synchronized DataContainer<String[]> next(DataContainer<String[]> wrapper) {
    if (nextLine == null) {
      return null;
    }
    String[] result = nextLine;
    if (tokenizer != null) {
      nextLine = parseNextLine();
      lineCount++;
    } else {
      nextLine = null;
    }
    return wrapper.setData(result);
  }

  public String[] cellsByHeaders(String[] headers, String[] data) {
    String[] result = new String[headers.length];
    for (int i = 0; i < headers.length; i++) {
      result[i] = cellByHeader(headers[i], data);
    }
    return result;
  }

  public String cellByHeader(String header, String[] data) {
    Integer index = this.headerIndexes.get(header);
    return (index != null && index < data.length ? data[index] : null);
  }

  public int columnIndexOfHeader(String header) {
    return this.headerIndexes.get(header);
  }

  @Override
  public synchronized void close() {
    if (tokenizer != null) {
      tokenizer.close();
    }
    tokenizer = null;
    nextLine = null;
  }

  public synchronized int lineCount() {
    return lineCount;
  }

  public static void process(String uri, char separator, String encoding, boolean ignoreEmptyLines, CSVLineHandler lineHandler) {
    process(IOUtil.getReaderForURI(uri, encoding), separator, ignoreEmptyLines, lineHandler);
  }

  public static void process(Reader reader, char separator, boolean ignoreEmptyLines, CSVLineHandler lineHandler) {
    try (CSVLineIterator iterator = new CSVLineIterator(reader, separator, ignoreEmptyLines)) {
      DataContainer<String[]> row = new DataContainer<>();
      while ((row = iterator.next(row)) != null) {
        lineHandler.handle(row.getData());
      }
    }
  }

  public static String[][] parse(String uri, char separator, String encoding, boolean ignoreEmptyLines) {
    return parse(IOUtil.getReaderForURI(uri, encoding), separator, ignoreEmptyLines);
  }

  public static String[][] parse(File file, char separator, String encoding, boolean ignoreEmptyLines) {
    try (BufferedReader reader = FileUtil.createFileReader(file, encoding)) {
      return parse(reader, separator, ignoreEmptyLines);
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().operationFailed("Parsing of SV file '" + file + "' failed", e);
    }
  }

  public static String[][] parse(Reader reader, char separator, boolean ignoreEmptyLines) {
    final ArrayBuilder<String[]> builder = new ArrayBuilder<>(String[].class);
    process(reader, separator, ignoreEmptyLines, builder::add);
    return builder.toArray();
  }


  // private helpers -------------------------------------------------------------------------------------------------

  private String[] parseNextLine() {
    if (tokenizer == null) {
      return END_OF_FILE;
    }
    List<String> list;
    CSVTokenType tokenType;
    do {
      list = new ArrayList<>();
      while ((tokenType = tokenizer.next()) == CELL) {
        list.add(tokenizer.cell);
      }
    } while (tokenType != EOF && ignoreEmptyLines && list.isEmpty());
    if (tokenType == EOF) {
      close();
    }
    if (!list.isEmpty()) {
      String[] line = CollectionUtil.toArray(list, String.class);
      checkHeaders(line);
      return line;
    } else if (tokenType != EOF) {
      String[] line = new String[0];
      checkHeaders(line);
      if (!ignoreEmptyLines) {
        return line;
      }
    }
    return END_OF_FILE;
  }

  private void checkHeaders(String[] line) {
    if (this.headers == null) {
      setHeaders(line);
    }
  }


  // java.lang.Object overrides --------------------------------------------------------------------------------------

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[" + stringRep + "]";
  }

}
