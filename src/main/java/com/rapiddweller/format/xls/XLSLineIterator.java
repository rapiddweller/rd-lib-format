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
package com.rapiddweller.format.xls;

import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import com.rapiddweller.common.Converter;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.ObjectNotFoundException;
import com.rapiddweller.common.ParseException;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.converter.ArrayTypeConverter;
import com.rapiddweller.common.converter.NoOpConverter;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.DataIterator;

/**
 * Iterates the lines of a sheet in an Excel document.
 * 
 * Created at 27.01.2009 22:04:47
 * @since 0.4.8
 * @author Volker Bergmann
 */

public class XLSLineIterator implements DataIterator<Object[]> {
	
	private String emptyMarker;
	private String nullMarker;
	private boolean formatted;
	private final Converter<String, ?> stringPreprocessor;
	
	private String[] headers;
	private Iterator<Row> rowIterator;
	
	// constructors ----------------------------------------------------------------------------------------------------
	
	public XLSLineIterator(String uri) throws IOException, ParseException {
		this(uri, 0);
	}
	
	public XLSLineIterator(String uri, int sheetIndex) throws IOException, ParseException {
		this(uri, sheetIndex, false, false, null);
	}
	
    public XLSLineIterator(String uri, int sheetIndex, boolean headersIncluded, boolean formatted, Converter<String, ?> stringPreprocessor) 
    		throws IOException, ParseException {
		this(sheet(uri, sheetIndex), headersIncluded, formatted, stringPreprocessor);
	}
	
	public XLSLineIterator(String uri, String sheetName, boolean headersIncluded, boolean formatted) 
			throws IOException, ParseException {
		this(uri, sheetName, headersIncluded, formatted, null);
	}
	
	public XLSLineIterator(String uri, String sheetName, boolean headersIncluded, boolean formatted, Converter<String, ?> stringPreprocessor) 
			throws IOException, ParseException {
		this(sheet(uri, sheetName), headersIncluded, formatted, stringPreprocessor);
	}
	
    public XLSLineIterator(Sheet sheet, boolean headersIncluded, boolean formatted, Converter<String, ?> stringPreprocessor) {
    	this.emptyMarker = "'";
    	this.nullMarker = null;
		this.formatted = formatted;
		if (stringPreprocessor == null)
			stringPreprocessor = new NoOpConverter<String>();
		this.stringPreprocessor = stringPreprocessor;
		
		rowIterator = sheet.rowIterator();
		if (!rowIterator.hasNext()) {
			close();
			return;
		} else if (headersIncluded) {
			parseHeaders();
		}
    }


	// properties ------------------------------------------------------------------------------------------------------

	public String getEmptyMarker() {
		return emptyMarker;
	}
	
	public void setEmptyMarker(String emptyMarker) {
		this.emptyMarker = emptyMarker;
	}
	
	public String getNullMarker() {
		return nullMarker;
	}
	
	public void setNullMarker(String nullMarker) {
		this.nullMarker = nullMarker;
	}
	
	public boolean isFormatted() {
		return formatted;
	}
	
	public void setFormatted(boolean formatted) {
		this.formatted = formatted;
	}
	
	public String[] getHeaders() {
		return headers;
	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}
	
	// interface -------------------------------------------------------------------------------------------------------
	
	@Override
	public Class<Object[]> getType() {
		return Object[].class;
	}
	
	@Override
	public synchronized DataContainer<Object[]> next(DataContainer<Object[]> wrapper) {
		if (rowIterator == null || !rowIterator.hasNext())
			return null;
		Row row = rowIterator.next();
		int cellCount = row.getLastCellNum();
		Object[] result = new Object[cellCount];
		for (int cellnum = 0; cellnum < cellCount; cellnum++) {
			if (formatted)
				result[cellnum] = XLSUtil.resolveCellValueAsString(row.getCell(cellnum), emptyMarker, nullMarker, stringPreprocessor);
			else
				result[cellnum] = XLSUtil.resolveCellValue(row.getCell(cellnum), emptyMarker, nullMarker, stringPreprocessor);
		}
		return wrapper.setData(result);
	}

	public Object cellValueForHeader(String header, Object[] cells) {
		String trimmedHeader = StringUtil.trim(header);
		for (int i = 0; i < headers.length; i++)
			if (headers[i].equals(trimmedHeader))
				return cells[i];
		throw new ObjectNotFoundException("Undefined header: '" + trimmedHeader + "'");
	}
    

	@Override
	public synchronized void close() {
		rowIterator = null;
	}

	// helper methods --------------------------------------------------------------------------------------------------
	
    private static Sheet sheet(String uri, String sheetName) throws IOException, ParseException {
		try {
			Workbook workbook = WorkbookFactory.create(IOUtil.getInputStreamForURI(uri));
			Sheet sheet = sheetName != null ? workbook.getSheet(sheetName) : workbook.getSheetAt(0);
			if (sheet == null)
				throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in file " + uri);
			return sheet;
		} catch (InvalidFormatException e) {
			throw new ParseException("Error parsing sheet '" + sheetName + "' of " + uri, null);
		}
    }

    private static Sheet sheet(String uri, int sheetIndex) throws IOException {
		Workbook workbook;
		try {
			workbook = WorkbookFactory.create(IOUtil.getInputStreamForURI(uri));
			return workbook.getSheetAt(sheetIndex);
		} catch (InvalidFormatException e) {
			throw new ParseException("Error parsing sheet " + sheetIndex + " of " + uri, e, null, -1, -1);
		}
    }

    private void parseHeaders() {
    	DataContainer<Object[]> wrapper = new DataContainer<Object[]>();
		if (next(wrapper) != null) {
			this.headers = StringUtil.trimAll(ArrayTypeConverter.convert(wrapper.getData(), String.class));
		} else {
			this.headers = null;
			close();
		}
	}

    @Override
    public String toString() {
    	return getClass().getSimpleName() + "[" + rowIterator + "]";
    }

}
