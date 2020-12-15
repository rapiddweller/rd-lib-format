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
package com.rapiddweller.formats.csv;

import com.rapiddweller.commons.Context;
import com.rapiddweller.commons.ConversionException;
import com.rapiddweller.commons.Converter;
import com.rapiddweller.commons.SystemInfo;
import com.rapiddweller.commons.converter.ToStringConverter;
import com.rapiddweller.formats.script.AbstractScript;
import com.rapiddweller.formats.script.ConstantScript;
import com.rapiddweller.formats.script.Script;
import com.rapiddweller.formats.script.ScriptException;
import com.rapiddweller.formats.script.ScriptUtil;
import com.rapiddweller.formats.script.ScriptedDocumentWriter;

import java.io.Writer;
import java.io.IOException;

/**
 * Writes arrays as CSV rows.
 * 
 * Created: 06.06.2007 19:35:29
 * @author Volker Bergmann
 */
public class ArrayCSVWriter extends ScriptedDocumentWriter<Object[]> {

    public ArrayCSVWriter(Writer out) {
        this(out, ',');
    }

    public ArrayCSVWriter(Writer out, char separator) {
        this(out, separator, (Script)null, (Script)null);
    }

    public ArrayCSVWriter(Writer out, char separator, String ... columnHeads) {
        this(
                out,
                separator,
                new ConstantScript(CSVUtil.formatHeaderWithLineFeed(separator, columnHeads)),
                null);
    }

    public ArrayCSVWriter(Writer out, char separator, String headerScriptUrl, String footerScriptUrl)
            throws IOException {
        this(
            out,
            separator,
            (headerScriptUrl != null ? ScriptUtil.readFile(headerScriptUrl) : null),
            (footerScriptUrl != null ? ScriptUtil.readFile(footerScriptUrl) : null)
        );
    }

    public ArrayCSVWriter(Writer out, char separator, Script headerScript, Script footerScript) {
        super(out, headerScript, new ArrayCSVScript(separator), footerScript);
    }

    // ArrayCSVScript ---------------------------------------------------------------------------------------------

    private static class ArrayCSVScript extends AbstractScript {

        private Converter<Object, String> converter;
        private char separator;

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
