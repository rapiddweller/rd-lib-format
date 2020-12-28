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

import com.rapiddweller.commons.format.PadFormat;
import com.rapiddweller.formats.DataIterator;
import com.rapiddweller.formats.util.AbstractDataSource;

import java.io.IOException;

/**
 * Creates Iterators that iterate through the lines of a flat file and returns each line as array of Strings.
 * 
 * Created: 27.08.2007 19:16:26
 * @author Volker Bergmann
 */
public class FixedWidthLineSource extends AbstractDataSource<String[]> {

    private final String uri;
    private final PadFormat[] formats;
    private final boolean ignoreEmptyLines;
    private final String encoding;
    private final String lineFilter;

    public FixedWidthLineSource(String uri, PadFormat[] formats, boolean ignoreEmptyLines, String encoding, String lineFilter) {
    	super(String[].class);
        this.uri = uri;
        this.formats = formats.clone();
        this.ignoreEmptyLines = ignoreEmptyLines;
        this.encoding = encoding;
        this.lineFilter = lineFilter;
    }

    @Override
	public DataIterator<String[]> iterator() {
        try {
            return new FixedWidthLineIterator(uri, formats, ignoreEmptyLines, encoding, lineFilter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
