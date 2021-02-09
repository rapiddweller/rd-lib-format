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
import com.rapiddweller.common.ObjectNotFoundException;
import com.rapiddweller.common.ParseException;
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
import java.util.Iterator;

/**
 * Iterates the lines of a sheet in an Excel document.
 * <p>
 * Created at 27.01.2009 22:04:47
 *
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

  /**
   * Instantiates a new Xls line iterator.
   *
   * @param uri the uri
   * @throws IOException    the io exception
   * @throws ParseException the parse exception
   */
  public XLSLineIterator(String uri) throws IOException, ParseException {
    this(uri, 0);
  }

  /**
   * Instantiates a new Xls line iterator.
   *
   * @param uri        the uri
   * @param sheetIndex the sheet index
   * @throws IOException    the io exception
   * @throws ParseException the parse exception
   */
  public XLSLineIterator(String uri, int sheetIndex) throws IOException, ParseException {
    this(uri, sheetIndex, false, false, null);
  }

  /**
   * Instantiates a new Xls line iterator.
   *
   * @param uri                the uri
   * @param sheetIndex         the sheet index
   * @param headersIncluded    the headers included
   * @param formatted          the formatted
   * @param stringPreprocessor the string preprocessor
   * @throws IOException    the io exception
   * @throws ParseException the parse exception
   */
  public XLSLineIterator(String uri, int sheetIndex, boolean headersIncluded, boolean formatted, Converter<String, ?> stringPreprocessor)
      throws IOException, ParseException {
    this(sheet(uri, sheetIndex), headersIncluded, formatted, stringPreprocessor);
  }

  /**
   * Instantiates a new Xls line iterator.
   *
   * @param uri             the uri
   * @param sheetName       the sheet name
   * @param headersIncluded the headers included
   * @param formatted       the formatted
   * @throws IOException    the io exception
   * @throws ParseException the parse exception
   */
  public XLSLineIterator(String uri, String sheetName, boolean headersIncluded, boolean formatted)
      throws IOException, ParseException {
    this(uri, sheetName, headersIncluded, formatted, null);
  }

  /**
   * Instantiates a new Xls line iterator.
   *
   * @param uri                the uri
   * @param sheetName          the sheet name
   * @param headersIncluded    the headers included
   * @param formatted          the formatted
   * @param stringPreprocessor the string preprocessor
   * @throws IOException    the io exception
   * @throws ParseException the parse exception
   */
  public XLSLineIterator(String uri, String sheetName, boolean headersIncluded, boolean formatted, Converter<String, ?> stringPreprocessor)
      throws IOException, ParseException {
    this(sheet(uri, sheetName), headersIncluded, formatted, stringPreprocessor);
  }

  /**
   * Instantiates a new Xls line iterator.
   *
   * @param sheet              the sheet
   * @param headersIncluded    the headers included
   * @param formatted          the formatted
   * @param stringPreprocessor the string preprocessor
   */
  public XLSLineIterator(Sheet sheet, boolean headersIncluded, boolean formatted, Converter<String, ?> stringPreprocessor) {
    this.emptyMarker = "'";
    this.nullMarker = null;
    this.formatted = formatted;
    if (stringPreprocessor == null) {
      stringPreprocessor = new NoOpConverter<String>();
    }
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

  /**
   * Gets empty marker.
   *
   * @return the empty marker
   */
  public String getEmptyMarker() {
    return emptyMarker;
  }

  /**
   * Sets empty marker.
   *
   * @param emptyMarker the empty marker
   */
  public void setEmptyMarker(String emptyMarker) {
    this.emptyMarker = emptyMarker;
  }

  /**
   * Gets null marker.
   *
   * @return the null marker
   */
  public String getNullMarker() {
    return nullMarker;
  }

  /**
   * Sets null marker.
   *
   * @param nullMarker the null marker
   */
  public void setNullMarker(String nullMarker) {
    this.nullMarker = nullMarker;
  }

  /**
   * Is formatted boolean.
   *
   * @return the boolean
   */
  public boolean isFormatted() {
    return formatted;
  }

  /**
   * Sets formatted.
   *
   * @param formatted the formatted
   */
  public void setFormatted(boolean formatted) {
    this.formatted = formatted;
  }

  /**
   * Get headers string [ ].
   *
   * @return the string [ ]
   */
  public String[] getHeaders() {
    return headers;
  }

  /**
   * Sets headers.
   *
   * @param headers the headers
   */
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

  /**
   * Cell value for header object.
   *
   * @param header the header
   * @param cells  the cells
   * @return the object
   */
  public Object cellValueForHeader(String header, Object[] cells) {
    String trimmedHeader = StringUtil.trim(header);
    for (int i = 0; i < headers.length; i++) {
      if (headers[i].equals(trimmedHeader)) {
        return cells[i];
      }
    }
    throw new ObjectNotFoundException("Undefined header: '" + trimmedHeader + "'");
  }


  @Override
  public synchronized void close() {
    rowIterator = null;
  }

  // helper methods --------------------------------------------------------------------------------------------------

  private static Sheet sheet(String uri, String sheetName) throws IOException, ParseException {
    Workbook workbook = WorkbookFactory.create(IOUtil.getInputStreamForURI(uri));
    Sheet sheet = sheetName != null ? workbook.getSheet(sheetName) : workbook.getSheetAt(0);
    if (sheet == null) {
      throw new IllegalArgumentException("Sheet '" + sheetName + "' not found in file " + uri);
    }
    return sheet;
  }

  private static Sheet sheet(String uri, int sheetIndex) throws IOException {
    Workbook workbook;
    workbook = WorkbookFactory.create(IOUtil.getInputStreamForURI(uri));
    return workbook.getSheetAt(sheetIndex);
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
