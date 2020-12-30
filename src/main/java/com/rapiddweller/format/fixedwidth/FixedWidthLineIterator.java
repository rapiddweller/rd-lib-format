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

import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.ReaderLineIterator;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.common.format.PadFormat;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.DataIterator;

import java.io.IOException;
import java.io.Reader;
import java.text.ParseException;
import java.util.regex.Pattern;

/**
 * Tests the FlatFileLineIterator.
 * 
 * Created: 27.08.2007 06:43:50
 * @author Volker Bergmann
 */
public class FixedWidthLineIterator implements DataIterator<String[]> {

    private final boolean ignoreEmptyLines;

    private ReaderLineIterator lineIterator;
    private final FixedWidthLineParser parser;
    private int lineCount;
    private final Pattern lineFilter;

    // constructors ----------------------------------------------------------------------------------------------------

    public FixedWidthLineIterator(String uri, PadFormat[] formats) throws IOException {
        this(uri, formats, false, SystemInfo.getFileEncoding(), null);
    }

    public FixedWidthLineIterator(String uri, PadFormat[] formats, boolean ignoreEmptyLines, String encoding, 
    		String lineFilter) throws IOException {
        this(IOUtil.getReaderForURI(uri, encoding), formats, ignoreEmptyLines, lineFilter);
    }

    public FixedWidthLineIterator(Reader reader, PadFormat[] formats) {
        this(reader, formats, false, null);
    }

    public FixedWidthLineIterator(Reader reader, PadFormat[] formats, boolean ignoreEmptyLines, String lineFilter) {
        this.lineIterator = new ReaderLineIterator(reader);
        parser = new FixedWidthLineParser(formats);
        this.ignoreEmptyLines = ignoreEmptyLines;
        this.lineCount = 0;
        this.lineFilter = (lineFilter != null ? Pattern.compile(lineFilter) : null);
    }

    // interface -------------------------------------------------------------------------------------------------------

    @Override
	public Class<String[]> getType() {
    	return String[].class;
    }
    
    /**
     * Parses a CSV row into an array of Strings
     * @return an array of Strings that represents a CSV row
     */
	@Override
	public DataContainer<String[]> next(DataContainer<String[]> wrapper) {
        String[] result = fetchNextLine();
        if (result != null) {
            lineCount++;
            return wrapper.setData(result);
        } else 
        	return null;
    }

    /** Closes the source */
    @Override
	public void close() {
        if (lineIterator != null)
            lineIterator.close();
        lineIterator = null;
    }
    
    /** Returns the number of lines iterated so far. 
     * @return the number of lines iterated so far */
    public int lineCount() {
        return lineCount;
    }

    // private helpers -------------------------------------------------------------------------------------------------

    private String[] fetchNextLine() {
        try {
            if (lineIterator == null)
                return null;
            String line = null;
            boolean success = false;
            
            // fetch next appropriate lines skipping empty lines if they shall be ignored
            while (lineIterator.hasNext()) {
                lineCount++;
                line = lineIterator.next();
                if ((line.length() > 0 || !ignoreEmptyLines) 
                		&& (lineFilter == null || lineFilter.matcher(line).matches())) {
                    success = true;
                    break;
                }
            }
            if (!success) {
                close();
                return null;
            } else if (StringUtil.isEmpty(line)) {
                return new String[0];
            } else {
            	return parser.parse(line);
            }
        } catch (ParseException e) {
            throw new RuntimeException("Unexpected error. ", e);
        }
    }

}
