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

package com.rapiddweller.format.csv;

import com.rapiddweller.common.Converter;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.DataIterator;
import com.rapiddweller.format.util.ThreadLocalDataContainer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses CSV files and converts the row to the desired target type.
 * Created at 25.04.2008 18:49:50
 *
 * @param <E> the type of the objects to provide
 * @author Volker Bergmann
 * @since 0.4.2
 */
public class ConvertingCSVParser<E> implements DataIterator<E> {

  private final Converter<String[], E> rowConverter;
  private final CSVLineIterator source;
  private final ThreadLocalDataContainer<String[]> dataContainer = new ThreadLocalDataContainer<String[]>();

  /**
   * Instantiates a new Converting csv parser.
   *
   * @param uri          the uri
   * @param rowConverter the row converter
   * @throws IOException the io exception
   */
  public ConvertingCSVParser(String uri, Converter<String[], E> rowConverter) throws IOException {
    this.source = new CSVLineIterator(uri);
    this.rowConverter = rowConverter;
  }

  @Override
  public Class<E> getType() {
    return rowConverter.getTargetType();
  }

  @Override
  public DataContainer<E> next(DataContainer<E> wrapper) {
    DataContainer<String[]> tmp = source.next(dataContainer.get());
    if (tmp == null) {
      return null;
    }
    return wrapper.setData(rowConverter.convert(tmp.getData()));
  }

  @Override
  public void close() {
    source.close();
  }

  /**
   * Parse list.
   *
   * @param <T>          the type parameter
   * @param uri          the uri
   * @param rowConverter the row converter
   * @return the list
   * @throws IOException the io exception
   */
  public static <T> List<T> parse(String uri, Converter<String[], T> rowConverter) throws IOException {
    return parse(uri, rowConverter, new ArrayList<T>());
  }

  /**
   * Parse list.
   *
   * @param <T>          the type parameter
   * @param uri          the uri
   * @param rowConverter the row converter
   * @param list         the list
   * @return the list
   * @throws IOException the io exception
   */
  public static <T> List<T> parse(String uri, Converter<String[], T> rowConverter, List<T> list) throws IOException {
    ConvertingCSVParser<T> parser = null;
    try {
      parser = new ConvertingCSVParser<T>(uri, rowConverter);
      DataContainer<T> container = new DataContainer<T>();
      while ((container = parser.next(container)) != null) {
        list.add(container.getData());
      }
      return list;
    } finally {
      IOUtil.close(parser);
    }
  }

}
