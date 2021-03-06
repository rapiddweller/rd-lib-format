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

import com.rapiddweller.common.ArrayBuilder;
import com.rapiddweller.common.ArrayFormat;
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.DataIterator;

import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for CSV processing.
 * <p>
 * Created: 07.06.2007 07:44:28
 *
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class CSVUtil {

  /**
   * Parse header string [ ].
   *
   * @param uri       the uri
   * @param separator the separator
   * @param encoding  the encoding
   * @return the string [ ]
   */
  public static String[] parseHeader(String uri, char separator, String encoding) {
    DataIterator<String[]> cellIterator = null;
    try {
      cellIterator = new CSVLineIterator(uri, separator, true, encoding);
      DataContainer<String[]> tmp = cellIterator.next(new DataContainer<String[]>());
      if (tmp != null) {
        return tmp.getData();
      } else {
        throw new ConfigurationError("empty CSV file");
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      IOUtil.close(cellIterator);
    }
  }

  /**
   * Parse rows string [ ] [ ].
   *
   * @param url       the url
   * @param separator the separator
   * @return the string [ ] [ ]
   * @throws IOException the io exception
   */
  public static String[][] parseRows(String url, char separator) throws IOException {
    return parseRows(url, separator, SystemInfo.getFileEncoding());
  }

  /**
   * Parse rows string [ ] [ ].
   *
   * @param url       the url
   * @param separator the separator
   * @param encoding  the encoding
   * @return the string [ ] [ ]
   * @throws IOException the io exception
   */
  public static String[][] parseRows(String url, char separator, String encoding) throws IOException {
    List<String[]> lines = new ArrayList<String[]>();
    CSVLineIterator iterator = new CSVLineIterator(url, separator, encoding);
    DataContainer<String[]> container = new DataContainer<String[]>();
    while ((container = iterator.next(container)) != null) {
      lines.add(container.getData());
    }
    iterator.close();
    String[][] result = new String[lines.size()][];
    return lines.toArray(result);
  }

  /**
   * Parse csv row string [ ].
   *
   * @param text the text
   * @return the string [ ]
   */
  public static String[] parseCSVRow(String text) {
    ArrayBuilder<String> builder = new ArrayBuilder<String>(String.class);
    CSVTokenizer tokenizer = new CSVTokenizer(new StringReader(text));
    try {
      CSVTokenType type;
      while ((type = tokenizer.next()) != CSVTokenType.EOL && type != CSVTokenType.EOF) {
        builder.add(tokenizer.cell);
      }
      return builder.toArray();
    } catch (IOException e) {
      throw new RuntimeException("Error parsing CSV row: " + text, e);
    } finally {
      IOUtil.close(tokenizer);
    }
  }

  /**
   * Write row.
   *
   * @param out       the out
   * @param separator the separator
   * @param cells     the cells
   * @throws IOException the io exception
   */
  public static void writeRow(Writer out, char separator, String... cells) throws IOException {
    if (cells.length > 0) {
      out.write(renderCell(cells[0], separator));
    }
    for (int i = 1; i < cells.length; i++) {
      out.write(separator);
      out.write(renderCell(cells[i], separator));
    }
    out.write(SystemInfo.getLineSeparator());
  }

  /**
   * Render cell string.
   *
   * @param text      the text
   * @param separator the separator
   * @return the string
   */
  public static String renderCell(String text, char separator) {
    if (text == null) {
      return "";
    }
    if (text.indexOf(separator) < 0 && text.indexOf('"') < 0) {
      return text;
    }
    text = text.replace("\"", "\"\"");
    return '"' + text + '"';
  }

  /**
   * Format header with line feed string.
   *
   * @param separator     the separator
   * @param propertyNames the property names
   * @return the string
   */
  public static String formatHeaderWithLineFeed(char separator, String... propertyNames) {
    return ArrayFormat.format(String.valueOf(separator), propertyNames) + SystemInfo.getLineSeparator();
  }

}
