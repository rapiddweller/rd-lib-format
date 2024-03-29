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

import com.rapiddweller.common.Assert;
import com.rapiddweller.common.Consumer;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.format.DataContainer;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Loads and saves JavaBeans from and in Excel documents.<br>
 * Created: 25.12.2015 14:51:09
 *
 * @param <E> the type parameter
 * @author Volker Bergmann
 * @since 1.0.7
 */
public class XLSBeanPersistor<E> {

  /**
   * The Logger.
   */
  protected Logger logger = LoggerFactory.getLogger(getClass());

  private final Class<E> beanClass;
  private final List<PropFormat> beanProperties;

  /**
   * Instantiates a new Xls bean persistor.
   *
   * @param beanClass     the bean class
   * @param propertyNames the property names
   */
  public XLSBeanPersistor(Class<E> beanClass, String... propertyNames) {
    this.beanClass = beanClass;
    this.beanProperties = new ArrayList<>();
    for (String propertyName : propertyNames) {
      this.addProperty(propertyName);
    }
  }

  /**
   * Add property prop format.
   *
   * @param name the name
   * @return the prop format
   */
  protected PropFormat addProperty(String name) {
    PropFormat propFormat = new PropFormat(name);
    this.beanProperties.add(propFormat);
    return propFormat;
  }

  /**
   * Load.
   *
   * @param file     the file
   * @param consumer the consumer
   * @throws IOException the io exception
   */
  protected void load(File file, Consumer<E> consumer) throws IOException {
    XLSJavaBeanIterator<E> mapper = null;
    try {
      mapper = new XLSJavaBeanIterator<>(file.getAbsolutePath(), null, false, beanClass);
      DataContainer<E> wrapper = new DataContainer<>();
      while (mapper.next(wrapper) != null) {
        if (wrapper.getData() != null) {
          consumer.consume(wrapper.getData());
        }
      }
    } finally {
      IOUtil.close(mapper);
    }
  }

  /**
   * Save.
   *
   * @param file         the file
   * @param sheetName    the sheet name
   * @param beanIterator the bean iterator
   * @throws IOException the io exception
   */
  protected void save(File file, String sheetName, Iterator<E> beanIterator) throws IOException {
    // check preconditions
    Assert.notNull(file, "file");
    Assert.notNull(sheetName, "sheetName");
    Assert.notNull(beanIterator, "beanIterator");
    // save
    BeanXLSWriter<E> out = null;
    try {
      out = new BeanXLSWriter<>(new FileOutputStream(file), sheetName, beanProperties);
      while (beanIterator.hasNext()) {
        out.save(beanIterator.next());
      }
    } finally {
      IOUtil.close(out);
    }
  }

}
