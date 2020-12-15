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
package com.rapiddweller.formats.util;

/**
 * Provides general utility methods for data files.
 * Created: 05.06.2013 18:17:20
 * @since 0.6.17
 * @author Volker Bergmann
 */

public class DataFileUtil {
	
	public static boolean isPlainTextDocument(String fileName) {
		return hasSuffixIgnoreCase(".txt", fileName);
	}

	public static boolean isDbUnitDocument(String fileName) {
		return hasSuffixIgnoreCase(".dbunit.xml", fileName);
	}
	
	public static boolean isXmlDocument(String fileName) {
		return hasSuffixIgnoreCase(".xml", fileName);
	}
	
	public static boolean isExcelOrCsvDocument(String fileName) {
		return isExcelDocument(fileName) || isCsvDocument(fileName);
	}
	
	public static boolean isExcelDocument(String fileName) {
		return isBinaryExcelDocument(fileName) || isXmlExcelDocument(fileName);
	}
	
	public static boolean isBinaryExcelDocument(String fileName) {
		return hasSuffixIgnoreCase(".xls", fileName);
	}

	public static boolean isXmlExcelDocument(String fileName) {
		return hasSuffixIgnoreCase(".xlsx", fileName);
	}
	
	public static boolean isCsvDocument(String fileName) {
		return hasSuffixIgnoreCase(".csv", fileName);
	}
	
	public static boolean isFixedColumnWidthFile(String fileName) {
		return hasSuffixIgnoreCase(".fcw", fileName);
	}

	public static boolean hasSuffixIgnoreCase(String suffix, String fileName) {
		return fileName.toLowerCase().endsWith(suffix);
	}

}
