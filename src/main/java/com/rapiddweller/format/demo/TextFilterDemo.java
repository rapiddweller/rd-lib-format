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

package com.rapiddweller.format.demo;

import com.rapiddweller.common.Filter;
import com.rapiddweller.common.ReaderLineIterator;
import com.rapiddweller.common.iterator.FilteringIterator;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

/**
 * Parses the lines of a text file and extracts the lines that match a {@link Filter} to a target file.
 * <p>
 * Created: 12.06.2007 19:32:31
 *
 * @author Volker Bergmann
 */
public class TextFilterDemo {
  private static final String FILE_NAME = "test.dat";

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   * @throws IOException the io exception
   */
  public static void main(String[] args) throws IOException {

    // creates a line based reader for the input file
    Reader reader = new FileReader(FILE_NAME);
    ReaderLineIterator src = new ReaderLineIterator(reader);

    // sets up a filtered iterator that uses the upper iterator as source
    Filter<String> filter = new LineFilter();
    FilteringIterator<String> iterator = new FilteringIterator<>(src, filter);

    // create a writer to save the rows that matched the filter
    Writer out = new BufferedWriter(new FileWriter("matches.csv"));

    // initialize counter and timer
    int matchCount = 0;
    System.out.println("Running...");
    long startMillis = System.currentTimeMillis();

    // iterate the entries
    while (iterator.hasNext()) {
      String row = iterator.next();
      out.write(row);
      matchCount++;
    }

    out.close();
    reader.close();

    // output counter and timer values
    long elapsedTime = System.currentTimeMillis() - startMillis;
    System.out.println("Processed file " + FILE_NAME + " with " + src.lineCount() + " entries " +
        "within " + elapsedTime + "ms (" + (src.lineCount() * 1000L / elapsedTime) + " entries per second)");
    System.out.println("Found " + matchCount + " matches");
  }

  /**
   * The type Line filter.
   */
  static final class LineFilter implements Filter<String> {
    @Override
    public boolean accept(String candidate) {
      return candidate.contains("|3023293310905|");
      //return true;
    }
  }
}
