/*
 * Copyright (C) 2011-2021 Volker Bergmann (volker.bergmann@bergmann-it.de).
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

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.bean.NamedMutatorFactory;
import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.common.mutator.NamedMutator;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.DataIterator;
import com.rapiddweller.format.util.ThreadLocalDataContainer;

import java.io.IOException;
import java.io.Reader;

/**
 * Reads a CSV file and maps its columns to JavaBeans.<br/><br/>
 * Created: 21.07.2006 15:07:36
 * @param <E> the type of the objects to write
 * @author Volker Bergmann
 */
public class CSVToJavaBeanMapper<E> implements DataIterator<E> {

  private CSVLineIterator iterator;
  private Class<E> type;
  private String emptyValue;

  private NamedMutator[] mutators;
  private int classIndex;
  private final ThreadLocalDataContainer<String[]> dataContainer = new ThreadLocalDataContainer<>();


  // constructors ----------------------------------------------------------------------------------------------------

  public CSVToJavaBeanMapper(Reader reader, Class<E> type) throws IOException {
    this(reader, type, ',', null);
  }

  public CSVToJavaBeanMapper(Reader reader, Class<E> type, char separator, String emptyValue) {
    CSVLineIterator it = new CSVLineIterator(reader, separator, true);
    DataContainer<String[]> tmp = it.next(dataContainer.get());
    if (tmp != null) {
      String[] attributeNames = tmp.getData();
      init(it, type, emptyValue, attributeNames);
    }
  }

  public CSVToJavaBeanMapper(Reader reader, Class<E> type, char separator, String emptyValue, String[] attributeNames) {
    CSVLineIterator it = new CSVLineIterator(reader, separator, true);
    init(it, type, emptyValue, attributeNames);
  }


  // DataIterator interface implementation ---------------------------------------------------------------------------

  @Override
  public Class<E> getType() {
    return type;
  }

  @SuppressWarnings("unchecked")
  @Override
  public DataContainer<E> next(DataContainer<E> wrapper) throws ConfigurationError {
    int i = 0;
    String value = null;
    try {
      DataContainer<String[]> tmp = nextRaw(dataContainer.get());
      if (tmp == null) {
        return null;
      }
      String[] line = tmp.getData();
      if (line.length == 0) {
        return null;
      }
      Class<E> beanClass = (classIndex >= 0 ? (Class<E>) BeanUtil.forName(line[classIndex]) : type);
      E bean = BeanUtil.newInstance(beanClass);
      int columns = Math.min(line.length, mutators.length);
      for (i = 0; i < columns; i++) {
        if (i != classIndex && mutators[i] != null) {
          value = line[i];
          if (value != null && value.length() == 0) {
            value = emptyValue;
          }
          mutators[i].setValue(bean, value);
        }
      }
      return wrapper.setData(bean);
    } catch (Exception e) {
      throw ExceptionFactory.getInstance().configurationError("Failed to set property '" +
          mutators[i].getName() + "' to '" + value + "' on class " + type, e);
    }
  }

  public DataContainer<String[]> nextRaw(DataContainer<String[]> wrapper) {
    if (iterator == null) {
      return null; // the file was empty and thus the iterator not initialized in init()
    }
    return iterator.next(wrapper);
  }

  @Override
  public void close() {
    IOUtil.close(iterator);
  }


  // further public methods ------------------------------------------------------------------------------------------

  public void skip() {
    iterator.next(dataContainer.get());
  }


  // private helpers -------------------------------------------------------------------------------------------------

  private void init(CSVLineIterator iterator, Class<E> type, String emptyValue, String[] attributeNames) {
    this.iterator = iterator;
    this.type = type;
    this.emptyValue = emptyValue;
    this.mutators = new NamedMutator[attributeNames.length];
    this.classIndex = -1;
    for (int i = 0; i < attributeNames.length; i++) {
      String attributeName = attributeNames[i];
      if (attributeName == null) {
        mutators[i] = null;
      } else if ("class".equals(attributeName)) {
        mutators[i] = null;
        this.classIndex = i;
      } else {
        mutators[i] = NamedMutatorFactory.create(type, attributeName, false, true);
      }
    }
  }

}
