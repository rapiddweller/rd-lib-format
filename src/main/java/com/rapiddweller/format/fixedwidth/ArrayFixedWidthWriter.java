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

package com.rapiddweller.format.fixedwidth;

import com.rapiddweller.common.Context;
import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.format.script.AbstractScript;
import com.rapiddweller.format.script.Script;
import com.rapiddweller.format.script.ScriptException;
import com.rapiddweller.format.script.ScriptUtil;
import com.rapiddweller.format.script.ScriptedDocumentWriter;

import java.io.IOException;
import java.io.Writer;

/**
 * Writes arrays as flat file columns.
 * Created: 07.06.2007 13:05:38
 *
 * @param <E> the type of the objects to write
 * @author Volker Bergmann
 */
public class ArrayFixedWidthWriter<E> extends ScriptedDocumentWriter<E[]> {

  /**
   * Instantiates a new Array fixed width writer.
   *
   * @param out        the out
   * @param descriptor the descriptor
   */
  public ArrayFixedWidthWriter(Writer out, FixedWidthRowTypeDescriptor descriptor) {
    this(out, null, (Script) null, descriptor);
  }

  /**
   * Instantiates a new Array fixed width writer.
   *
   * @param out             the out
   * @param headerScriptUrl the header script url
   * @param footerScriptUrl the footer script url
   * @param descriptor      the descriptor
   * @throws IOException the io exception
   */
  public ArrayFixedWidthWriter(Writer out, String headerScriptUrl, String footerScriptUrl, FixedWidthRowTypeDescriptor descriptor)
      throws IOException {
    this(
        out,
        (headerScriptUrl != null ? ScriptUtil.readFile(headerScriptUrl) : null),
        (footerScriptUrl != null ? ScriptUtil.readFile(footerScriptUrl) : null),
        descriptor
    );
  }

  /**
   * Instantiates a new Array fixed width writer.
   *
   * @param out          the out
   * @param headerScript the header script
   * @param footerScript the footer script
   * @param descriptors  the descriptors
   */
  public ArrayFixedWidthWriter(Writer out, Script headerScript, Script footerScript, FixedWidthRowTypeDescriptor descriptors) {
    super(
        out,
        headerScript,
        new ArrayFixedWidthScript(descriptors),
        footerScript
    );
  }

  // ArrayFlatFileScript ---------------------------------------------------------------------------------------------

  private static class ArrayFixedWidthScript extends AbstractScript {

    private final FixedWidthRowTypeDescriptor descriptor;

    /**
     * Instantiates a new Array fixed width script.
     *
     * @param descriptor the descriptor
     */
    public ArrayFixedWidthScript(FixedWidthRowTypeDescriptor descriptor) {
      this.descriptor = descriptor;
    }

    @Override
    public void execute(Context context, Writer out) throws IOException, ScriptException {
      try {
        Object[] cellsOfCurrentRow = (Object[]) context.get("part");
        out.write(descriptor.formatArray(cellsOfCurrentRow));
        out.write(SystemInfo.getLineSeparator());
      } catch (ConversionException e) {
        throw new ScriptException(e);
      }
    }
  }

}
