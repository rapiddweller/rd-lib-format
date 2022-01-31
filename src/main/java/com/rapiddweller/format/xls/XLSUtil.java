/*
 * Copyright (C) 2011-2022 Volker Bergmann (volker.bergmann@bergmann-it.de).
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
import com.rapiddweller.common.JavaTimeUtil;
import com.rapiddweller.common.MathUtil;
import com.rapiddweller.common.converter.ToStringConverter;
import com.rapiddweller.common.exception.ExceptionFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import static org.apache.poi.ss.usermodel.CellType.BLANK;
import static org.apache.poi.ss.usermodel.CellType.FORMULA;
import static org.apache.poi.ss.usermodel.CellType.STRING;

/**
 * Provides utility methods for XSSF (POI).<br/><br/>
 * Created at 09.08.2009 07:47:52
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class XLSUtil {

  private XLSUtil() {
  }

  public static Object resolveCellValue(Cell cell) {
    return resolveCellValue(cell, "'", null, null);
  }

  public static Object resolveCellValue(Cell cell, String emptyMarker, String nullMarker, Converter<String, ?> stringPreprocessor) {
    if (cell == null) {
      return null;
    }
    switch (cell.getCellType()) {
      case STRING:
        return convertString(cell, emptyMarker, nullMarker, stringPreprocessor);
      case NUMERIC:
        if (DateUtil.isCellDateFormatted(cell)) {
          return cell.getDateCellValue();
        } else {
          return mapNumberType(cell.getNumericCellValue());
        }
      case BOOLEAN:
        return cell.getBooleanCellValue();
      case BLANK:
      case ERROR:
        return cell.getRichStringCellValue().getString();
      case FORMULA:
        FormulaEvaluator evaluator = createFormulaEvaluator(cell);
        CellValue cellValue = evaluator.evaluate(cell);
        switch (cellValue.getCellType()) {
          case STRING:
            return convertString(cellValue, emptyMarker, stringPreprocessor);
          case NUMERIC:
            if (DateUtil.isCellDateFormatted(cell)) {
              return DateUtil.getJavaDate(cellValue.getNumberValue());
            } else {
              return mapNumberType(cellValue.getNumberValue());
            }
          case BOOLEAN:
            return cellValue.getBooleanValue();
          case BLANK:
          case ERROR:
            return null;
          default:
            throw ExceptionFactory.getInstance().parsingError("Unexpected cell type: " + cellValue.getCellType());
            // CELL_TYPE_FORMULA is not supposed to be encountered here
        }
      default:
        throw ExceptionFactory.getInstance().configurationError("Not a supported cell type: " + cell.getCellType());
    }
  }

  /** Resolves a formula or a normal cell and format the result as it would be displayed in Excel.
   *  @param cell the cell to resolve
   *  @return a string representation of the cell value */
  public static String resolveCellValueAsString(Cell cell) {
    return resolveCellValueAsString(cell, "'", null, null);
  }

  /** Resolves a formula or a normal cell and format the result as it would be displayed in Excel
   *  @param cell               the cell to resolve
   *  @param emptyMarker        the string to interpret as empty field
   *  @param nullMarker         the string to interpret as null value
   *  @param stringPreprocessor a preprocessor to apply to the raw field values
   *  @return a string representation of the cell value */
  public static String resolveCellValueAsString(Cell cell, String emptyMarker, String nullMarker, Converter<String, ?> stringPreprocessor) {
    if (cell == null) {
      return null;
    }
    if (cell.getCellType() == STRING) {
      String content = cell.getRichStringCellValue().getString();
      if (content != null) {
        if (content.equals(emptyMarker) || content.equals("'")) {
          content = "";
        } else if (content.equals(nullMarker)) {
          content = null;
        }
      }
      if (stringPreprocessor != null) {
        content = ToStringConverter.convert(stringPreprocessor.convert(content), null);
      }
      return content;
    } else {
      DataFormatter formatter = new DataFormatter();
      if (cell.getCellType() == FORMULA) {
        return formatter.formatCellValue(cell, createFormulaEvaluator(cell));
      } else {
        return formatter.formatCellValue(cell);
      }
    }
  }

  public static void autoSizeColumns(Workbook workbook) {
    int sheetCount = workbook.getNumberOfSheets();
    for (int i = 0; i < sheetCount; i++) {
      Sheet sheet = workbook.getSheetAt(i);
      int firstRowNum = sheet.getFirstRowNum();
      if (firstRowNum >= 0) {
        Row firstRow = sheet.getRow(firstRowNum);
        for (int cellnum = firstRow.getFirstCellNum(); cellnum < firstRow.getLastCellNum(); cellnum++) {
          sheet.autoSizeColumn(cellnum);
        }
      }
    }
  }

  public static boolean isEmpty(Row row) {
    if (row == null) {
      return true;
    }
    for (int i = 0; i < row.getLastCellNum(); i++) {
      if (!isEmpty(row.getCell(i))) {
        return false;
      }
    }
    return true;
  }

  public static boolean isEmpty(Cell cell) {
    if (cell == null) {
      return true;
    }
    if (cell.getCellType() == BLANK) {
      return true;
    }
    if (cell.getCellType() == STRING) {
      return cell.getStringCellValue().isEmpty();
    }
    return false;
  }

  public static Sheet getOrCreateSheet(String label, Workbook workbook, String... headers) {
    Sheet result = workbook.getSheet(label);
    if (result == null) {
      result = workbook.createSheet(label);
      if (headers != null && headers.length > 0) {
        Row row = result.createRow(0);
        for (int i = 0; i < headers.length; i++)
          row.createCell(i).setCellValue(headers[i]);
      }
    }
    return result;
  }

  public static Row addRow(Object[] cells, Sheet sheet) {
    Row row = sheet.createRow(sheet.getLastRowNum() + 1);
    for (int i = 0; i < cells.length; i++)
      createCell(row, i, cells[i]);
    return row;
  }

  private static Cell createCell(Row row, int colnum, Object value) {
    Cell cell = row.createCell(colnum);
    if (value != null) {
      if (value instanceof String) {
        cell.setCellValue((String) value);
      } else if (value instanceof Number) {
        cell.setCellValue(((Number) value).doubleValue());
      } else if (value instanceof LocalDate) {
        cell.setCellValue(JavaTimeUtil.toDate((LocalDate) value));
      } else if (value instanceof LocalDateTime) {
        cell.setCellValue(JavaTimeUtil.toDate((LocalDateTime) value));
      } else if (value instanceof ZonedDateTime) {
        cell.setCellValue(JavaTimeUtil.toDate((ZonedDateTime) value));
      } else if (value instanceof Boolean) {
        cell.setCellValue((Boolean) value);
      } else if (value instanceof Date) {
        cell.setCellValue((Date) value);
      } else if (value instanceof Calendar) {
        cell.setCellValue((Calendar) value);
      } else {
        cell.setCellValue(ToStringConverter.convert(value, null));
      }
    }
    return cell;
  }

  public static Row addRow(String[] cells, Sheet sheet) {
    Row row = sheet.createRow(sheet.getLastRowNum() + 1);
    for (int i = 0; i < cells.length; i++)
      row.createCell(i).setCellValue(cells[i]);
    return row;
  }

  public static void formatColumsWithHeader(String header, Workbook workbook, String format) {
    CellStyle cellStyle = workbook.createCellStyle();
    CreationHelper createHelper = workbook.getCreationHelper();
    short formatCode = createHelper.createDataFormat().getFormat(format);
    cellStyle.setDataFormat(formatCode);
    for (int iS = 0; iS < workbook.getNumberOfSheets(); iS++) {
      Sheet sheet = workbook.getSheetAt(iS);
      Row headerRow = sheet.getRow(0);
      for (int iC = 0; iC <= headerRow.getLastCellNum(); iC++) {
        Cell headerCell = headerRow.getCell(iC);
        if (headerCell != null && header.equals(headerCell.getStringCellValue())) {
          for (int iR = 1; iR <= sheet.getLastRowNum(); iR++) {
            Cell dataCell = sheet.getRow(iR).getCell(iC);
            dataCell.setCellStyle(cellStyle);
          }
        }
      }
    }
  }

  public static void saveAs(String fileName, Workbook workbook) {
    try {
      FileOutputStream out = new FileOutputStream(fileName);
      workbook.write(out);
      IOUtil.close(out);
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().fileCreationFailed(e.getMessage(), e);
    }
  }


  // private helpers -------------------------------------------------------------------------------------------------

  private static FormulaEvaluator createFormulaEvaluator(Cell cell) {
    return cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
  }

  private static Number mapNumberType(double numericCellValue) {
    if (MathUtil.isIntegralValue(numericCellValue)) {
      return ((Double) numericCellValue).longValue();
    }
    return numericCellValue;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private static Object convertString(CellValue cellValue, String emptyMarker, Converter<?, ?> stringPreprocessor) {
    String content = cellValue.getStringValue();
    if (content != null && (content.equals(emptyMarker) || content.equals("'"))) {
      content = "";
    }
    return (stringPreprocessor != null ? ((Converter) stringPreprocessor).convert(content) : content);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private static Object convertString(Cell cell, String emptyMarker, String nullMarker, Converter<?, ?> stringPreprocessor) {
    String content = cell.getRichStringCellValue().getString();
    if (content != null) {
      if (content.equals(emptyMarker) || content.equals("'")) {
        content = "";
      }
      if (content.equals(nullMarker)) {
        content = null;
      }
    }
    return (stringPreprocessor != null ? ((Converter) stringPreprocessor).convert(content) : content);
  }

  public static int getColumnCount(Sheet sheet) {
    int columnCount = 0;
    Iterator<Row> rowIterator = sheet.rowIterator();
    while (rowIterator.hasNext()) {
      columnCount = Math.max(columnCount, rowIterator.next().getLastCellNum());
    }
    return columnCount;
  }

}
