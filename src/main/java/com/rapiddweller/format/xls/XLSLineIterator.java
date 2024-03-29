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

import com.rapiddweller.common.Converter;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.converter.ArrayTypeConverter;
import com.rapiddweller.common.converter.NoOpConverter;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.DataIterator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Iterates the lines of a sheet in an Excel document.<br/><br/>
 * Created at 27.01.2009 22:04:47
 * @author Volker Bergmann
 * @since 0.4.8
 */
public class XLSLineIterator implements DataIterator<Object[]> {

  private String emptyMarker;
  private String nullMarker;
  private boolean formatted;
  private final Converter<String, ?> stringPreprocessor;

  private String[] headers;
  private Iterator<Row> rowIterator;

  // constructors ----------------------------------------------------------------------------------------------------

  public XLSLineIterator(String uri) {
    this(uri, 0);
  }

  public XLSLineIterator(String uri, int sheetIndex) {
    this(uri, sheetIndex, false, false, null);
  }

  public XLSLineIterator(String uri, int sheetIndex, boolean headersIncluded, boolean formatted,
                         Converter<String, ?> stringPreprocessor) {
    this(sheet(uri, sheetIndex), headersIncluded, formatted, stringPreprocessor);
  }

  public XLSLineIterator(String uri, String sheetName, boolean headersIncluded, boolean formatted) {
    this(uri, sheetName, headersIncluded, formatted, null);
  }

  public XLSLineIterator(String uri, String sheetName, boolean headersIncluded, boolean formatted, Converter<String, ?> stringPreprocessor) {
    this(sheet(uri, sheetName), headersIncluded, formatted, stringPreprocessor);
  }

  public XLSLineIterator(Sheet sheet, boolean headersIncluded, boolean formatted, Converter<String, ?> stringPreprocessor) {
    this.emptyMarker = "'";
    this.nullMarker = null;
    this.formatted = formatted;
    if (stringPreprocessor == null) {
      stringPreprocessor = new NoOpConverter<>();
    }
    this.stringPreprocessor = stringPreprocessor;

    rowIterator = sheet.rowIterator();
    if (!rowIterator.hasNext()) {
      close();
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
    if (rowIterator == null || !rowIterator.hasNext()) {
      return null;
    }
    Row row = rowIterator.next();
    int cellCount = row.getLastCellNum();
    Object[] result = new Object[cellCount];
    for (int cellnum = 0; cellnum < cellCount; cellnum++) {
      if (formatted) {
        result[cellnum] = XLSUtil.resolveCellValueAsString(row.getCell(cellnum), emptyMarker, nullMarker, stringPreprocessor);
      } else {
        result[cellnum] = XLSUtil.resolveCellValue(row.getCell(cellnum), emptyMarker, nullMarker, stringPreprocessor);
      }
    }
    return wrapper.setData(result);
  }

  public Object cellValueForHeader(String header, Object[] cells) {
    String trimmedHeader = StringUtil.trim(header);
    for (int i = 0; i < headers.length; i++) {
      if (headers[i].equals(trimmedHeader)) {
        return cells[i];
      }
    }
    throw ExceptionFactory.getInstance().objectNotFound("Undefined header: '" + trimmedHeader + "'");
  }


  @Override
  public synchronized void close() {
    rowIterator = null;
  }

  // helper methods --------------------------------------------------------------------------------------------------

  private static Sheet sheet(String uri, String sheetName) {
    Workbook workbook = readWorkbook(uri);
    Sheet sheet = sheetName != null ? workbook.getSheet(sheetName) : workbook.getSheetAt(0);
    if (sheet != null) {
      return sheet;
    } else {
      throw ExceptionFactory.getInstance().sheetNotFound(uri, sheetName);
    }
  }

  private static Sheet sheet(String uri, int sheetIndex) {
    Workbook workbook = readWorkbook(uri);
    return workbook.getSheetAt(sheetIndex);
  }

  private void parseHeaders() {
    DataContainer<Object[]> wrapper = new DataContainer<>();
    if (next(wrapper) != null) {
      this.headers = StringUtil.trimAll(ArrayTypeConverter.convert(wrapper.getData(), String.class));
    } else {
      this.headers = null;
      close();
    }
  }

  private static Workbook readWorkbook(String uri) {
    InputStream in = IOUtil.getInputStreamForURI(uri);
    try {
      return WorkbookFactory.create(in);
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().parsingError("Failed to read " + uri, e);
    }
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[" + rowIterator + "]";
  }

}
