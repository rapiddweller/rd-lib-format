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
import com.rapiddweller.common.exception.ScriptException;
import com.rapiddweller.format.script.AbstractScript;
import com.rapiddweller.format.script.Script;
import com.rapiddweller.format.script.ScriptUtil;
import com.rapiddweller.format.script.ScriptedDocumentWriter;

import java.io.IOException;
import java.io.Writer;

/**
 * Writes arrays as flat file columns.<br/><br/>
 * Created: 07.06.2007 13:05:38
 * @param <E> the type of the objects to write
 * @author Volker Bergmann
 */
public class ArrayFixedWidthWriter<E> extends ScriptedDocumentWriter<E[]> {

  public ArrayFixedWidthWriter(Writer out, FixedWidthRowTypeDescriptor descriptor) {
    this(out, null, (Script) null, descriptor);
  }

  public ArrayFixedWidthWriter(Writer out, String headerScriptUrl, String footerScriptUrl, FixedWidthRowTypeDescriptor descriptor) {
    this(
        out,
        (headerScriptUrl != null ? ScriptUtil.readFile(headerScriptUrl) : null),
        (footerScriptUrl != null ? ScriptUtil.readFile(footerScriptUrl) : null),
        descriptor
    );
  }

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
        throw new ScriptException("Error writing fixed width file", e);
      }
    }
  }

}
