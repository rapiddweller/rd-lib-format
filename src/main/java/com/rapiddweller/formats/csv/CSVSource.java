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

import java.io.IOException;

import com.rapiddweller.formats.DataIterator;
import com.rapiddweller.formats.DataSource;
import com.rapiddweller.formats.util.OrthogonalArrayIterator;

/**
 * {@link DataSource} implementation that provides for 
 * row-wise or column-wise iteration through CSV files.
 * Created: 23.11.2011 11:14:54
 * @since 0.6.4
 * @author Volker Bergmann
 */
public class CSVSource implements DataSource<String[]> {

    /** The default separator to use */
    public static final char DEFAULT_SEPARATOR = ',';

    protected String uri;
    protected char separator;
    protected String encoding;
    protected boolean ignoreEmptyLines;

	private final boolean rowBased;
	
    // constructors ----------------------------------------------------------------------------------------------------

    public CSVSource(String uri, char separator, String encoding, boolean ignoreEmptyLines, boolean rowBased) {
		this.uri = uri;
		this.separator = separator;
		this.encoding = encoding;
		this.ignoreEmptyLines = ignoreEmptyLines;
		this.rowBased = rowBased;
	}

    // interface -------------------------------------------------------------------------------------------------------

    @Override
	public Class<String[]> getType() {
    	return String[].class;
    }
    
	@Override
	public DataIterator<String[]> iterator() {
		try {
			DataIterator<String[]> result = new CSVLineIterator(uri, separator, ignoreEmptyLines, encoding);
			if (!rowBased)
				result = new OrthogonalArrayIterator<String>(result);
			return result;
		} catch (IOException e) {
			throw new RuntimeException("Error creating iterator for " + uri, e);
		}
	}
	
    @Override
	public void close() {
    	// nothing to do
    }
    
}
