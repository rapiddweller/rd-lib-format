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

import com.rapiddweller.common.SystemInfo;

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

  /**
   * For file csv writer.
   *
   * @param file          the file
   * @param separator     the separator
   * @param append        the append
   * @param columnHeaders the column headers
   * @return the csv writer
   * @throws IOException the io exception
   */
  public static CSVWriter forFile(File file, char separator, boolean append, String... columnHeaders) throws IOException {
    boolean exists = (file.exists() && file.length() > 0);
    BufferedWriter writer = new BufferedWriter(new FileWriter(file, append));
    return new CSVWriter(writer, separator, exists, columnHeaders);
  }

  /**
   * Instantiates a new Csv writer.
   *
   * @param writer        the writer
   * @param separator     the separator
   * @param append        the append
   * @param columnHeaders the column headers
   * @throws IOException the io exception
   */
  public CSVWriter(Writer writer, char separator, boolean append, String... columnHeaders) throws IOException {
    this.separator = separator;
    this.out = writer;
    if (!append) {
      writeRow(columnHeaders);
    }
  }

  /**
   * Write row.
   *
   * @param cells the cells
   * @throws IOException the io exception
   */
  public synchronized void writeRow(String[] cells) throws IOException {
    for (int i = 0; i < cells.length; i++) {
      String cell = cells[i];
      if (cell.indexOf(separator) >= 0) {
        out.write('"' + cell + '"');
      } else {
        out.write(cell);
      }
      if (i < cells.length - 1) {
        out.write(separator);
      }
    }
    out.write(LF);
  }

  @Override
  public synchronized void close() throws IOException {
    out.close();
  }

}
