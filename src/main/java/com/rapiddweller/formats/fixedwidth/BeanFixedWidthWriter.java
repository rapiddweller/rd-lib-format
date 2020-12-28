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
package com.rapiddweller.formats.fixedwidth;

import com.rapiddweller.commons.Context;
import com.rapiddweller.commons.ConversionException;
import com.rapiddweller.commons.Converter;
import com.rapiddweller.commons.SystemInfo;
import com.rapiddweller.commons.bean.BeanToPropertyArrayConverter;
import com.rapiddweller.commons.converter.ArrayConverter;
import com.rapiddweller.commons.converter.ConverterChain;
import com.rapiddweller.commons.converter.FormatFormatConverter;
import com.rapiddweller.commons.converter.ToStringConverter;
import com.rapiddweller.formats.script.AbstractScript;
import com.rapiddweller.formats.script.Script;
import com.rapiddweller.formats.script.ScriptException;
import com.rapiddweller.formats.script.ScriptUtil;
import com.rapiddweller.formats.script.ScriptedDocumentWriter;

import java.io.IOException;
import java.io.Writer;

/**
 * Writes JavaBeans as flat file columns.
 * Created: 07.06.2007 13:05:38
 * @param <E> the type of the objects to write
 * @author Volker Bergmann
 */
public class BeanFixedWidthWriter<E> extends ScriptedDocumentWriter<E> {

    public BeanFixedWidthWriter(Writer out, FixedWidthColumnDescriptor ... descriptors) {
        this(out, null, (Script)null, descriptors);
    }

    public BeanFixedWidthWriter(Writer out, String headerScriptUrl, String footerScriptUrl,
                              FixedWidthColumnDescriptor ... descriptors)
            throws IOException {
        this(
            out,
            (headerScriptUrl != null ? ScriptUtil.readFile(headerScriptUrl) : null),
            (footerScriptUrl != null ? ScriptUtil.readFile(footerScriptUrl) : null),
            descriptors
        );
    }

    public BeanFixedWidthWriter(Writer out, Script headerScript, Script footerScript,
                              FixedWidthColumnDescriptor ... descriptors) {
        super( out, headerScript, new BeanFixedWidthScript(descriptors), footerScript);
    }

    // BeanFlatFileScript ----------------------------------------------------------------------------------------------

    private static class BeanFixedWidthScript extends AbstractScript {

        private final Converter<Object, String[]> converter;

        @SuppressWarnings({ "unchecked", "rawtypes" })
        public BeanFixedWidthScript(FixedWidthColumnDescriptor[] descriptors) {
            int length = descriptors.length;
            String[] propertyNames = new String[length];
            Converter[] propertyConverters = new Converter[length];
            for (int i = 0; i < length; i++) {
                FixedWidthColumnDescriptor descriptor = descriptors[i];
                propertyNames[i] = descriptor.getName();
                propertyConverters[i] = new ConverterChain(
                    new ToStringConverter(),
                    new FormatFormatConverter(String.class, 
                            descriptor.getFormat(),
                            true
                            )
                );
            }
            this.converter = new ConverterChain(
                new BeanToPropertyArrayConverter(propertyNames),
                new ArrayConverter(Object.class, String.class, propertyConverters)
            );
        }

        @Override
        public void execute(Context context, Writer out) throws IOException, ScriptException {
            try {
                String[] cells = converter.convert(context.get("part"));
                for (int i = 0; i < cells.length; i++)
                    out.write(cells[i]);
                out.write(SystemInfo.getLineSeparator());
            } catch (ConversionException e) {
                throw new ScriptException(e);
            }
        }
    }
}
