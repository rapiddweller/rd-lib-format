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

package com.rapiddweller.format.util;

/**
 * Provides general utility methods for data files.
 * Created: 05.06.2013 18:17:20
 *
 * @author Volker Bergmann
 * @since 0.6.17
 */
public class DataFileUtil {

  /**
   * Is plain text document boolean.
   *
   * @param fileName the file name
   * @return the boolean
   */
  public static boolean isPlainTextDocument(String fileName) {
    return hasSuffixIgnoreCase(".txt", fileName);
  }

  /**
   * Is db unit document boolean.
   *
   * @param fileName the file name
   * @return the boolean
   */
  public static boolean isDbUnitDocument(String fileName) {
    return hasSuffixIgnoreCase(".dbunit.xml", fileName);
  }

  /**
   * Is xml document boolean.
   *
   * @param fileName the file name
   * @return the boolean
   */
  public static boolean isXmlDocument(String fileName) {
    return hasSuffixIgnoreCase(".xml", fileName);
  }

  /**
   * Is excel or csv document boolean.
   *
   * @param fileName the file name
   * @return the boolean
   */
  public static boolean isExcelOrCsvDocument(String fileName) {
    return isExcelDocument(fileName) || isCsvDocument(fileName);
  }

  /**
   * Is excel document boolean.
   *
   * @param fileName the file name
   * @return the boolean
   */
  public static boolean isExcelDocument(String fileName) {
    return isBinaryExcelDocument(fileName) || isXmlExcelDocument(fileName);
  }

  /**
   * Is binary excel document boolean.
   *
   * @param fileName the file name
   * @return the boolean
   */
  public static boolean isBinaryExcelDocument(String fileName) {
    return hasSuffixIgnoreCase(".xls", fileName);
  }

  /**
   * Is xml excel document boolean.
   *
   * @param fileName the file name
   * @return the boolean
   */
  public static boolean isXmlExcelDocument(String fileName) {
    return hasSuffixIgnoreCase(".xlsx", fileName);
  }

  /**
   * Is csv document boolean.
   *
   * @param fileName the file name
   * @return the boolean
   */
  public static boolean isCsvDocument(String fileName) {
    return hasSuffixIgnoreCase(".csv", fileName);
  }

  /**
   * Is fixed column width file boolean.
   *
   * @param fileName the file name
   * @return the boolean
   */
  public static boolean isFixedColumnWidthFile(String fileName) {
    return hasSuffixIgnoreCase(".fcw", fileName);
  }

  /**
   * Has suffix ignore case boolean.
   *
   * @param suffix   the suffix
   * @param fileName the file name
   * @return the boolean
   */
  public static boolean hasSuffixIgnoreCase(String suffix, String fileName) {
    return fileName.toLowerCase().endsWith(suffix);
  }

}
