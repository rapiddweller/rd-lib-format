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

import com.rapiddweller.common.ArrayUtil;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.common.exception.ExceptionFactory;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * Writes String arrays to a CSV file.
 * Created: 10.04.2013 07:47:36
 *
 * @author Volker Bergmann
 * @since 1.0.15
 */
public class CSVWriter implements Closeable {

  private static final String LF = SystemInfo.getLineSeparator();

  private final Writer out;
  private final char separator;

  public static void writeTable(String[] title, Object[][] table, File file, char separator) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(file));
      try (CSVWriter csv = new CSVWriter(writer, separator, false)) {
        for (String titleLine : title)
          csv.writeRow(new String[] {titleLine});
        for (Object[] tableRow : table) {
          csv.writeRow(tableRow);
        }
      }
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().fileCreationFailed("Creation of CSV file '" + file + "' failed", e);
    }
  }

  public static CSVWriter forFile(File file, char separator, boolean append, String... columnHeaders) {
    try {
      boolean exists = (file.exists() && file.length() > 0);
      BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
      return new CSVWriter(writer, separator, exists, columnHeaders);
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().fileCreationFailed("Creation of CSV file '" + file + "' failed", e);
    }
  }

  public CSVWriter(Writer writer, char separator, boolean append, String... columnHeaders) {
    this.separator = separator;
    this.out = writer;
    if (!append && !ArrayUtil.isEmpty(columnHeaders)) {
      writeRow(columnHeaders);
    }
  }

  public void writeEmptyRow() {
    writeRow(null);
  }

  public synchronized void writeRow(Object[] cells) {
    try {
      if (cells != null) {
        for (int i = 0; i < cells.length; i++) {
          String cellString = (cells[i] != null ? String.valueOf(cells[i]) : "");
          if (cellString.indexOf(separator) >= 0) {
            out.write('"' + cellString + '"');
          } else {
            out.write(cellString);
          }
          if (i < cells.length - 1) {
            out.write(separator);
          }
        }
      }
      out.write(LF);
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().operationFailed(e.getMessage(), e);
    }
  }

  @Override
  public synchronized void close() {
    IOUtil.close(out);
  }

}
