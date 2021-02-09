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

import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.bean.PropertyGraphAccessor;
import com.rapiddweller.common.converter.ToStringConverter;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.ExtendedColor;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Saves JavaBeans in MS Excel(TM) files.
 * Created: 27.12.2015 07:40:49
 *
 * @param <E> the type parameter
 * @author Volker Bergmann
 * @since 1.0.7
 */
public class BeanXLSWriter<E> implements Closeable, CreationHelper {

  // attributes ------------------------------------------------------------------------------------------------------

  private final OutputStream out;
  private final String sheetName;
  private final List<PropFormat> beanProperties;

  private Workbook workbook;


  // constructors ----------------------------------------------------------------------------------------------------

  /**
   * Instantiates a new Bean xls writer.
   *
   * @param out       the out
   * @param sheetName the sheet name
   */
  public BeanXLSWriter(OutputStream out, String sheetName) {
    this(out, sheetName, null);
  }

  /**
   * Instantiates a new Bean xls writer.
   *
   * @param out            the out
   * @param sheetName      the sheet name
   * @param beanProperties the bean properties
   */
  public BeanXLSWriter(OutputStream out, String sheetName, List<PropFormat> beanProperties) {
    this.out = out;
    this.sheetName = sheetName;
    this.beanProperties = (beanProperties != null ? new ArrayList<PropFormat>(beanProperties) : new ArrayList<PropFormat>());
  }

  /**
   * Add property.
   *
   * @param property the property
   */
  public void addProperty(PropFormat property) {
    this.beanProperties.add(property);
  }

  /**
   * Save.
   *
   * @param bean the bean
   */
  public void save(E bean) {
    Sheet sheet = getOrCreateSheet(bean, sheetName);
    Row row = sheet.createRow(sheet.getLastRowNum() + 1);
    for (int i = 0; i < beanProperties.size(); i++) {
      PropFormat prop = beanProperties.get(i);
      Object propValue = PropertyGraphAccessor.getPropertyGraph(prop.getName(), bean);
      render(propValue, row, i);
    }
  }

  @Override
  public void close() {
    try {
      if (workbook == null) {
        createWorkbook(); // if no data was added, create an empty Excel document
      } else {
        XLSUtil.autoSizeColumns(workbook);
      }
      // Write the output
      workbook.write(out);
    } catch (FileNotFoundException e) {
      throw new ConfigurationError(e);
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      IOUtil.close(out);
    }
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private Sheet getOrCreateSheet(E bean, String sheetName) {
    // create file
    if (workbook == null) {
      createWorkbook();
    }
    Sheet sheet = workbook.getSheet(sheetName);
    if (sheet == null) {
      sheet = workbook.createSheet(sheetName);
      writeHeaderRow(bean, sheet);
    }
    return sheet;
  }

  private void createWorkbook() {
    this.workbook = new XSSFWorkbook();
  }

  private void writeHeaderRow(E bean, Sheet sheet) {
    Row headerRow = sheet.createRow(0);
    for (int i = 0; i < beanProperties.size(); i++) {
      PropFormat prop = beanProperties.get(i);
      // write column header
      String componentName = prop.getName();
      headerRow.createCell(i).setCellValue(createRichTextString(componentName));
      // apply pattern
      if (prop.getPattern() != null) {
        DataFormat dataFormat = workbook.createDataFormat();
        CellStyle columnStyle = workbook.createCellStyle();
        columnStyle.setDataFormat(dataFormat.getFormat(prop.getPattern()));
        sheet.setDefaultColumnStyle(i, columnStyle);
      }
    }
  }

  private static void render(Object propValue, Row row, int column) {
    Cell cell = row.createCell(column);
    if (propValue instanceof Number) {
      cell.setCellValue(((Number) propValue).doubleValue());
    } else if (propValue instanceof Date) {
      cell.setCellValue((Date) propValue);
    } else if (propValue instanceof Boolean) {
      cell.setCellValue((Boolean) propValue);
    } else {
      String s = ToStringConverter.convert(propValue, null);
      cell.setCellValue((s));
    }
  }

  // java.lang.Object overrides --------------------------------------------------------------------------------------

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

  @Override
  public RichTextString createRichTextString(String s) {
    return null;
  }

  @Override
  public DataFormat createDataFormat() {
    return null;
  }

  @Override
  public Hyperlink createHyperlink(HyperlinkType hyperlinkType) {
    return null;
  }

  @Override
  public FormulaEvaluator createFormulaEvaluator() {
    return null;
  }

  @Override
  public ExtendedColor createExtendedColor() {
    return null;
  }

  @Override
  public ClientAnchor createClientAnchor() {
    return null;
  }

  @Override
  public AreaReference createAreaReference(String s) {
    return null;
  }

  @Override
  public AreaReference createAreaReference(CellReference cellReference, CellReference cellReference1) {
    return null;
  }
}
