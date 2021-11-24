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
import com.rapiddweller.common.ArrayFormat;
import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.DataIterator;

import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for CSV processing.<br/><br/>
 * Created: 07.06.2007 07:44:28
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class CSVUtil {

  private CSVUtil() {
    // private constructor to prevent instantiation of this utility class
  }

  public static String[] parseHeader(String uri, char separator, String encoding) {
    try (DataIterator<String[]> cellIterator = new CSVLineIterator(uri, separator, true, encoding)) {
      DataContainer<String[]> tmp = cellIterator.next(new DataContainer<>());
      if (tmp != null) {
        return tmp.getData();
      } else {
        throw ExceptionFactory.getInstance().configurationError("empty CSV file");
      }
    }
  }

  public static String[][] parseRows(String url, char separator) {
    return parseRows(url, separator, SystemInfo.getFileEncoding());
  }

  public static String[][] parseRows(String url, char separator, String encoding) {
    List<String[]> lines = new ArrayList<>();
    CSVLineIterator iterator = new CSVLineIterator(url, separator, encoding);
    DataContainer<String[]> container = new DataContainer<>();
    while ((container = iterator.next(container)) != null) {
      lines.add(container.getData());
    }
    iterator.close();
    String[][] result = new String[lines.size()][];
    return lines.toArray(result);
  }

  public static String[] parseCSVRow(String text) {
    ArrayBuilder<String> builder = new ArrayBuilder<>(String.class);
    try (CSVTokenizer tokenizer = new CSVTokenizer(new StringReader(text))) {
      CSVTokenType type;
      while ((type = tokenizer.next()) != CSVTokenType.EOL && type != CSVTokenType.EOF) {
        builder.add(tokenizer.cell);
      }
      return builder.toArray();
    }
  }

  public static void writeRow(Writer out, char separator, String... cells) {
    try {
      if (cells.length > 0) {
        out.write(renderCell(cells[0], separator, true));
      }
      for (int i = 1; i < cells.length; i++) {
        out.write(separator);
        out.write(renderCell(cells[i], separator, true));
      }
      out.write(SystemInfo.getLineSeparator());
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().fileAccessException("Failed to write CSV row", e);
    }
  }

  public static String renderCell(String text, char separator, boolean quoteEmpty) {
    if (text == null) {
      return "";
    } else if (text.length() == 0) {
      if (quoteEmpty) {
        return "\"\"";
      } else {
        return "";
      }
    } else if (text.indexOf(separator) < 0 && text.indexOf('"') < 0) {
      return text;
    } else {
      text = text.replace("\"", "\"\"");
      return '"' + text + '"';
    }
  }

  public static String formatHeaderWithLineFeed(char separator, String... propertyNames) {
    return ArrayFormat.format(String.valueOf(separator), propertyNames) + SystemInfo.getLineSeparator();
  }

}
