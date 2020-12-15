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

import com.rapiddweller.commons.IOUtil;
import com.rapiddweller.commons.ArrayBuilder;
import com.rapiddweller.commons.CollectionUtil;
import com.rapiddweller.commons.SystemInfo;
import com.rapiddweller.formats.DataContainer;
import com.rapiddweller.formats.DataIterator;
import static com.rapiddweller.formats.csv.CSVTokenType.*;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * Gives access to content of a CSV file by String arrays
 * that represent the CSV rows as specified in RFC 4180. 
 * @see <a href="https://www.ietf.org/rfc/rfc4180.txt">https://www.ietf.org/rfc/rfc4180.txt</a>
 * 
 * @author Volker Bergmann
 */
public class CSVLineIterator implements DataIterator<String[]> {

    /** The default separator to use */
    public static final char DEFAULT_SEPARATOR = ',';

    private String stringRep;
    
    private CSVTokenizer tokenizer;

    private String[] nextLine;

    private boolean ignoreEmptyLines;

    private int lineCount;
    
    private String[] headers;

	private HashMap<String, Integer> headerIndexes;
    
    // constructors ----------------------------------------------------------------------------------------------------

    /**
     * Creates a parser that reads from a uri
     * @param uri the URL to read from
     * @throws IOException if uri access failed
     */
    public CSVLineIterator(String uri) throws IOException {
        this(uri, DEFAULT_SEPARATOR);
    }

    /**
     * Creates a parser that reads from a uri
     * @param uri the URL to read from
     * @param separator the cell separator character
     * @throws IOException if stream access fails
     */
    public CSVLineIterator(String uri, char separator) throws IOException {
        this(uri, separator, false);
    }

    public CSVLineIterator(String uri, char separator, String encoding) throws IOException {
        this(uri, separator, false, encoding);
    }

    public CSVLineIterator(String uri, char separator, boolean ignoreEmptyLines) throws IOException {
        this(uri, separator, ignoreEmptyLines, SystemInfo.getFileEncoding());
    }

    public CSVLineIterator(String uri, char separator, boolean ignoreEmptyLines, String encoding) throws IOException {
        this(IOUtil.getReaderForURI(uri, encoding), separator, ignoreEmptyLines);
        this.stringRep = uri;
    }

    /**
     * Creates a parser that reads from a reader and used a special separator character
     * @param reader the reader to use
     * @param separator the separator character
     * @throws IOException if reader access fails
     */
    public CSVLineIterator(Reader reader, char separator) throws IOException {
        this(reader, separator, false);
    }

    public CSVLineIterator(Reader reader, char separator, boolean ignoreEmptyLines) throws IOException {
        this.tokenizer = new CSVTokenizer(reader, separator);
        this.ignoreEmptyLines = ignoreEmptyLines;
        this.nextLine = parseNextLine();
        this.lineCount = 0;
        this.stringRep = reader.toString();
    }

    // interface -------------------------------------------------------------------------------------------------------

	public void parseHeaders() throws IOException {
		setHeaders(nextLine);
        this.nextLine = parseNextLine();
	}
    
	public String[] getHeaders() {
		return this.headers;
	}

	public void setHeaders(String[] headers) {
		if (headers != null)
			this.headers = headers;
		else
			this.headers = new String[0];
		this.headerIndexes = new HashMap<String, Integer>();
		for (int i = 0; i < this.headers.length; i++)
			this.headerIndexes.put(this.headers[i], i);
	}
    
    @Override
	public Class<String[]> getType() {
    	return String[].class;
    }
    
    /**
     * Parses a CSV row into an array of Strings
     * @return an array of Strings that represents a CSV row
     */
	@Override
	public synchronized DataContainer<String[]> next(DataContainer<String[]> wrapper) {
    	if (nextLine == null)
    		return null;
        try {
            String[] result = nextLine;
            if (tokenizer != null) {
                nextLine = parseNextLine();
                lineCount++;
            } else
                nextLine = null;
            return wrapper.setData(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
	
	public String[] cellsByHeaders(String[] headers, String[] data) {
		String[] result = new String[headers.length];
		for (int i = 0; i < headers.length; i++)
			result[i] = cellByHeader(headers[i], data);
		return result;
	}

	public String cellByHeader(String header, String[] data) {
		Integer index = this.headerIndexes.get(header);
		return (index != null && index < data.length ? data[index] : null);
	}
    
	public int columnIndexOfHeader(String header) {
		return this.headerIndexes.get(header);
	}

    /** Closes the source */
    @Override
	public synchronized void close() {
        if (tokenizer != null)
            tokenizer.close();
        tokenizer = null;
        nextLine = null;
    }

    public synchronized int lineCount() {
        return lineCount;
    }

    public static void process(String uri, char separator, String encoding, boolean ignoreEmptyLines, CSVLineHandler lineHandler) throws IOException {
        process(IOUtil.getReaderForURI(uri, encoding), separator, ignoreEmptyLines, lineHandler);
    }
    
    public static void process(Reader reader, char separator, boolean ignoreEmptyLines, CSVLineHandler lineHandler) throws IOException {
        CSVLineIterator iterator = null;
        try {
            iterator = new CSVLineIterator(reader, separator, ignoreEmptyLines);
            DataContainer<String[]> row = new DataContainer<String[]>();
            while ((row = iterator.next(row)) != null)
                lineHandler.handle(row.getData());
        } finally {
            if (iterator != null)
                iterator.close();
        }
    }
    
    public static String[][] parse(String uri, char separator, String encoding, boolean ignoreEmptyLines) throws IOException {
    	return parse(IOUtil.getReaderForURI(uri, encoding), separator, ignoreEmptyLines);
    }
    
    public static String[][] parse(Reader reader, char separator, boolean ignoreEmptyLines) throws IOException {
    	final ArrayBuilder<String[]> builder = new ArrayBuilder<String[]>(String[].class);
    	CSVLineHandler handler = new CSVLineHandler() {
			@Override
			public void handle(String[] cells) {
				builder.add(cells);
			}
		};
    	process(reader, separator, ignoreEmptyLines, handler);
    	return builder.toArray();
    }
    
    
    // private helpers -------------------------------------------------------------------------------------------------

    private String[] parseNextLine() throws IOException {
        if (tokenizer == null)
            return null;
        List<String> list;
        CSVTokenType tokenType;
        do {
            list = new ArrayList<String>();
            while ((tokenType = tokenizer.next()) == CELL)
            	list.add(tokenizer.cell);
        } while (tokenType != EOF && ignoreEmptyLines && list.size() == 0);
        if (tokenType == EOF)
            close();
        if (list.size() > 0) {
            String[] line = CollectionUtil.toArray(list, String.class);
           	checkHeaders(line);
			return line;
        } else if (tokenType != EOF) {
        	String[] line = new String[0];
           	checkHeaders(line);
        	if (!ignoreEmptyLines)
        		return line;
        }
        return null;
    }
    
    private void checkHeaders(String[] line) {
		if (this.headers == null) {
			setHeaders(line);
		}
	}

    
    
    // java.lang.Object overrides --------------------------------------------------------------------------------------

	@Override
    public String toString() {
    	return getClass().getSimpleName() + "[" + stringRep + "]";
    }

}
