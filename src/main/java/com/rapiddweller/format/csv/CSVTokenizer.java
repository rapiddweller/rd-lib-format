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

import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

import static com.rapiddweller.format.csv.CSVTokenType.CELL;
import static com.rapiddweller.format.csv.CSVTokenType.EOF;
import static com.rapiddweller.format.csv.CSVTokenType.EOL;

/**
 * Parses a CSV file token by token as specified in RFC 4180.
 * It returns parsed values as CSVTokensType of type CELL, EOL and EOF.
 * The current cell content is accessible by the public attribute 'cell'.<br/><br/>
 * Created: 26.08.2006 17:19:35
 * @author Volker Bergmann
 * @see CSVTokenType
 */
public class CSVTokenizer implements Closeable {

  /** The default separator to use */
  public static final char DEFAULT_SEPARATOR = ',';

  private static final char DEFAULT_LINE_COMMENT = '#';

  /** The source to read from */
  private PushbackReader reader;

  /** The actual separator */
  private final char separator;

  /** The token at the cursor position */
  public CSVTokenType ttype;

  /** The Last type. */
  public CSVTokenType lastType;

  /** String representation of the cell at the cursor position.
   *  If the cursor is at a EOL/EOF position, this is null. */
  public String cell;

  /** The Line. */
  public int line;

  // constructors ----------------------------------------------------------------------------------------------------

  /** Creates a tokenizer that reads from a URL.
   *  @param uri the URL to read from
   *  @if stream access fails */
  public CSVTokenizer(String uri) {
    this(uri, DEFAULT_SEPARATOR);
  }

  /** Creates a tokenizer that reads from a uri.
   *  @param uri       the uri to read from
   *  @param separator character used for separating CSV cells
   *  @if stream access fails */
  public CSVTokenizer(String uri, char separator) {
    this(uri, separator, SystemInfo.getFileEncoding());
  }

  /** Instantiates a new Csv tokenizer.
   *  @param uri       the uri
   *  @param separator the separator
   *  @param encoding  the encoding
   *  @the io exception */
  public CSVTokenizer(String uri, char separator, String encoding) {
    this(IOUtil.getReaderForURI(uri, encoding), separator);
  }

  /** Creates a tokenizer that reads from a java.io.Reader.
   * @param reader the reader to use as input */
  public CSVTokenizer(Reader reader) {
    this(reader, DEFAULT_SEPARATOR);
  }

  /** Creates a tokenizer that reads from a java.io.Reader.
   *  @param reader    the reader to use as input
   *  @param separator character used for separating CSV cells */
  public CSVTokenizer(Reader reader, char separator) {
    this.reader = new PushbackReader(new BufferedReader(reader));
    this.separator = separator;
    this.line = 1;
  }

  // interface -------------------------------------------------------------------------------------------------------

  /** Returns the next token.
   *  @return the next token */
  public CSVTokenType next() {
    this.lastType = this.ttype;
    if (reader == null) // if closed, return EOF
    {
      return setState(EOF, null);
    }
    if (lastType == EOL) { // on line separator increase line count
      line++;
    }
    int c = read();
    if (c == -1) { // if end of file is reached, close and signal EOF
      close();
      return setState(EOF, null);
    } else if (this.lastType == EOL && c == DEFAULT_LINE_COMMENT) {
      skipLine();
      return next();
    } else if (c == separator && lastType == CELL) {
      c = read();
    }
    if (c == -1) { // if end of file is reached, close and signal EOF
      close();
      return setState(CELL, null);
    }
    if (c == separator) {
      unread(c);
      return setState(CELL, null);
    } else if (c == '\r') { // handle \n or \r
      if ((c = read()) != '\n') {
        unread(c);
      }
      return setState(EOL, null);
    } else if (c == '\n') { // handle \n
      return setState(EOL, null);
    } else if (c == '"') {
      unread(c);
      return parseQuotes();
    } else {
      return parseSimpleCell(c);
    }
  }

  /** Skip line.
   * @the io exception */
  public void skipLine() {
    int c;
    // go to end of line
    while ((c = read()) != -1 && c != '\r' && c != '\n') {
      // skip EOL characters
    }
    switch (c) {
      case -1: case '\n':
        return;
      case '\r':
        int c2 = read();
        if (c2 != '\n') {
          unread(c2);
        }
        return;
      default:
        throw new IllegalStateException();
    }
  }

  /** Closes the source */
  @Override
  public void close() {
    if (reader != null) {
      IOUtil.close(reader);
    }
    reader = null;
  }

  /** Last ttype csv token type.
   *  @return the csv token type */
  public CSVTokenType lastTtype() {
    return lastType;
  }

  // private helpers -------------------------------------------------------------------------------------------------

  /** Sets the state of the tokenizer to the given tokenType and cell content.
   *  @param tokenType the tokenType to use
   *  @param cell      the cell content
   *  @return the token type */
  private CSVTokenType setState(CSVTokenType tokenType, String cell) {
    this.cell = cell;
    this.ttype = tokenType;
    return this.ttype;
  }

  private void unread(int c) {
    try {
      reader.unread(c);
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().internalError("Failed to unread", e);
    }
  }

  private int read() {
    try {
      return reader.read();
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().internalError("Failed to read", e);
    }
  }

  private CSVTokenType parseSimpleCell(int c) {
    StringBuilder buffer = new StringBuilder().append((char) c);
    boolean escapeMode = false;
    while ((c = read()) != -1 && c != '\r' && c != '\n') {
      if (escapeMode) {
        c = unescape((char) c);
        escapeMode = false;
      } else if (c == '\\') {
        escapeMode = true;
        continue;
      }
      if (c == separator) {
        unread(c);
        return setState(CELL, buffer.toString());
      }
      buffer.append((char) c);
    }
    if (c == '\r' || c == '\n') {
      unread(c);
    }
    return setState(CELL, buffer.toString());
  }

  private static char unescape(char c) { // this is more efficient than StringUtil.unescape(String)
    switch (c) {
      case 't':
        return '\t';
      case 'r':
        return '\r';
      case 'n':
        return '\n';
      default:
        return c;
    }
  }

  private CSVTokenType parseQuotes() {
    read(); // skip leading quote
    StringBuilder buffer = new StringBuilder();
    int c;
    boolean escapeMode = false;
    boolean done;
    do {
      while ((c = read()) != -1 && c != '"') {
        if (escapeMode) {
          c = unescape((char) c);
          escapeMode = false;
        } else if (c == '\\') {
          escapeMode = true;
          continue;
        }
        buffer.append((char) c);
      }
      if (c == '"') {
        c = read();
        if (c == '"') {
          // escaped quote
          buffer.append('"');
          done = false;
        } else {
          done = true;
        }
      } else {
        done = true;
      }
    } while (!done);
    if (c == '\r' || c == '\n' || c == separator) {
      unread(c);
    }
    return setState(CELL, buffer.toString());
  }

}
