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

import com.rapiddweller.common.Context;
import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.Converter;
import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.common.converter.ToStringConverter;
import com.rapiddweller.format.script.AbstractScript;
import com.rapiddweller.format.script.ConstantScript;
import com.rapiddweller.format.script.Script;
import com.rapiddweller.format.script.ScriptException;
import com.rapiddweller.format.script.ScriptUtil;
import com.rapiddweller.format.script.ScriptedDocumentWriter;

import java.io.IOException;
import java.io.Writer;

/**
 * Writes arrays as CSV rows.
 * <p>
 * Created: 06.06.2007 19:35:29
 *
 * @author Volker Bergmann
 */
public class ArrayCSVWriter extends ScriptedDocumentWriter<Object[]> {

  /**
   * Instantiates a new Array csv writer.
   *
   * @param out the out
   */
  public ArrayCSVWriter(Writer out) {
    this(out, ',');
  }

  /**
   * Instantiates a new Array csv writer.
   *
   * @param out       the out
   * @param separator the separator
   */
  public ArrayCSVWriter(Writer out, char separator) {
    this(out, separator, null, (Script) null);
  }

  /**
   * Instantiates a new Array csv writer.
   *
   * @param out         the out
   * @param separator   the separator
   * @param columnHeads the column heads
   */
  public ArrayCSVWriter(Writer out, char separator, String... columnHeads) {
    this(
        out,
        separator,
        new ConstantScript(CSVUtil.formatHeaderWithLineFeed(separator, columnHeads)),
        null);
  }

  /**
   * Instantiates a new Array csv writer.
   *
   * @param out             the out
   * @param separator       the separator
   * @param headerScriptUrl the header script url
   * @param footerScriptUrl the footer script url
   * @throws IOException the io exception
   */
  public ArrayCSVWriter(Writer out, char separator, String headerScriptUrl, String footerScriptUrl)
      throws IOException {
    this(
        out,
        separator,
        (headerScriptUrl != null ? ScriptUtil.readFile(headerScriptUrl) : null),
        (footerScriptUrl != null ? ScriptUtil.readFile(footerScriptUrl) : null)
    );
  }

  /**
   * Instantiates a new Array csv writer.
   *
   * @param out          the out
   * @param separator    the separator
   * @param headerScript the header script
   * @param footerScript the footer script
   */
  public ArrayCSVWriter(Writer out, char separator, Script headerScript, Script footerScript) {
    super(out, headerScript, new ArrayCSVScript(separator), footerScript);
  }

  // ArrayCSVScript ---------------------------------------------------------------------------------------------

  private static class ArrayCSVScript extends AbstractScript {

    private final Converter<Object, String> converter;
    private final char separator;

    /**
     * Instantiates a new Array csv script.
     *
     * @param separator the separator
     */
    public ArrayCSVScript(char separator) {
      this.separator = separator;
      this.converter = new ToStringConverter();
    }

    @Override
    public void execute(Context context, Writer out) throws IOException, ScriptException {
      try {
        Object[] cellsOfCurrentRow = (Object[]) context.get("part");
        String text = converter.convert(cellsOfCurrentRow[0]);
        out.write(CSVUtil.renderCell(text, separator));
        for (int i = 1; i < cellsOfCurrentRow.length; i++) {
          out.write(separator);
          out.write(converter.convert(cellsOfCurrentRow[i]));
        }
        out.write(SystemInfo.getLineSeparator());
      } catch (ConversionException e) {
        throw new ScriptException(e);
      }
    }
  }

}
