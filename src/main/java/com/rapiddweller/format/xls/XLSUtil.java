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

import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.Converter;
import com.rapiddweller.common.MathUtil;
import com.rapiddweller.common.converter.ToStringConverter;

import static org.apache.poi.ss.usermodel.CellType.*;

/**
 * Provides utility methods for HSSF (POI).
 * 
 * Created at 09.08.2009 07:47:52
 * @since 0.5.0
 * @author Volker Bergmann
 */

public class XLSUtil {

	private XLSUtil() { }
	
	public static Object resolveCellValue(Cell cell) {
		return resolveCellValue(cell, "'", null, null);
	}
	
	public static Object resolveCellValue(Cell cell, String emptyMarker, String nullMarker, Converter<String, ?> stringPreprocessor) {
		if (cell == null)
			return null;
		switch (cell.getCellType()) {
			case STRING: return convertString(cell, emptyMarker, nullMarker, stringPreprocessor);
			case NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell))
					return cell.getDateCellValue();
				else
					return mapNumberType(cell.getNumericCellValue());
			case BOOLEAN: return cell.getBooleanCellValue();
			case BLANK:
			case ERROR: return cell.getRichStringCellValue().getString();
			case FORMULA:
				FormulaEvaluator evaluator = createFormulaEvaluator(cell); 
				CellValue cellValue = evaluator.evaluate(cell);
				switch (cellValue.getCellType()) {
					case STRING: return convertString(cellValue, emptyMarker, stringPreprocessor);
				    case NUMERIC:
				    	if (HSSFDateUtil.isCellDateFormatted(cell))
				    		return HSSFDateUtil.getJavaDate(cellValue.getNumberValue());
				    	else
				    		return mapNumberType(cellValue.getNumberValue());
				    case BOOLEAN: return cellValue.getBooleanValue();
				    case BLANK:
				    case ERROR: return null;
				    default: throw new IllegalStateException("Unexpected cell type: " + cellValue.getCellType());
				    	// CELL_TYPE_FORMULA is not supposed to be encountered here
				}	
			default: throw new ConfigurationError("Not a supported cell type: " + cell.getCellType());
		}
	}
	
	/** Resolves a formula or a normal cell and format the result as it would be displayed in Excel.
	 * @param cell the cell to resolve
	 * @return a string representation of the cell value */
	public static String resolveCellValueAsString(Cell cell) {
		return resolveCellValueAsString(cell, "'", null, null);
	}
	
	/** Resolves a formula or a normal cell and format the result as it would be displayed in Excel
	 * @param cell the cell to resolve
	 * @param emptyMarker the string to interpret as empty field
	 * @param nullMarker the string to interpret as null value
	 * @param stringPreprocessor a preprocessor to apply to the raw field values
	 * @return a string representation of the cell value */
	public static String resolveCellValueAsString(Cell cell, String emptyMarker, String nullMarker, Converter<String, ?> stringPreprocessor) {
		if (cell == null)
			return null;
		if (cell.getCellType() == STRING) {
	    	String content = cell.getRichStringCellValue().getString();
	    	if (content != null) {
		    	if (content.equals(emptyMarker) || content.equals("'"))
		    		content = "";
		    	else if (content.equals(nullMarker))
		    		content = null;
	    	}
	    	if (stringPreprocessor != null)
	    		content = ToStringConverter.convert(stringPreprocessor.convert(content), null);
	    	return content;
		} else {
			DataFormatter formatter = new DataFormatter();
			if (cell.getCellType() == FORMULA)
				return formatter.formatCellValue(cell, createFormulaEvaluator(cell));
			else
				return formatter.formatCellValue(cell);
		}
	}

	public static void autoSizeColumns(Workbook workbook) {
		int sheetCount = workbook.getNumberOfSheets();
		for (int i = 0; i < sheetCount; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			int firstRowNum = sheet.getFirstRowNum();
			if (firstRowNum >= 0) {
				Row firstRow = sheet.getRow(firstRowNum);
				for (int cellnum = firstRow.getFirstCellNum(); cellnum < firstRow.getLastCellNum(); cellnum++)
					sheet.autoSizeColumn(cellnum);
			}
		}
	}
	
	public static boolean isEmpty(Row row) {
		if (row == null)
			return true;
		for (int i = 0; i < row.getLastCellNum(); i++)
			if (!isEmpty(row.getCell(i)))
				return false;
		return true;
	}
	
	public static boolean isEmpty(Cell cell) {
		if (cell == null)
			return true;
		if (cell.getCellType() == BLANK)
			return true;
		if (cell.getCellType() == STRING)
			return cell.getStringCellValue().isEmpty();
		return false;
	}
	
	
	// private helpers -------------------------------------------------------------------------------------------------
	
	private static FormulaEvaluator createFormulaEvaluator(Cell cell) {
		return cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
	}

	private static Number mapNumberType(double numericCellValue) {
		if (MathUtil.isIntegralValue(numericCellValue))
			return ((Double) numericCellValue).longValue();
		return numericCellValue;
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object convertString(CellValue cellValue, String emptyMarker, Converter<?, ?> stringPreprocessor) {
    	String content = cellValue.getStringValue();
    	if (content != null && (content.equals(emptyMarker) || content.equals("'")))
    		content = "";
    	return (stringPreprocessor != null ? ((Converter) stringPreprocessor).convert(content) : content);
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object convertString(Cell cell, String emptyMarker, String nullMarker, Converter<?, ?> stringPreprocessor) {
    	String content = cell.getRichStringCellValue().getString();
    	if (content != null) {
	    	if (content.equals(emptyMarker) || content.equals("'"))
	    		content = "";
	    	if (content.equals(nullMarker))
	    		content = null;
    	}
    	return (stringPreprocessor != null ? ((Converter) stringPreprocessor).convert(content) : content);
    }

	public static int getColumnCount(Sheet sheet) {
		int columnCount = 0;
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext())
			columnCount = Math.max(columnCount, rowIterator.next().getLastCellNum());
		return columnCount;
	}

}
