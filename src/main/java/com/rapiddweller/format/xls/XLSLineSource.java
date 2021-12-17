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

import com.rapiddweller.common.ExceptionUtil;
import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.format.DataIterator;
import com.rapiddweller.format.util.AbstractDataSource;

import java.io.FileNotFoundException;
import java.util.Iterator;

/**
 * {@link Iterable} implementation which creates {@link Iterator}s
 * that provide lines of XLS files as array objects.
 * Created: 19.07.2011 08:36:18
 * @author Volker Bergmann
 * @since 0.6.5
 */
public class XLSLineSource extends AbstractDataSource<Object[]> {

  private final String uri;
  private final String sheetName;
  private final boolean formatted;

  public XLSLineSource(String uri) {
    this(uri, null, false);
  }

  public XLSLineSource(String uri, String sheetName, boolean formatted) {
    super(Object[].class);
    this.uri = uri;
    this.sheetName = sheetName;
    this.formatted = formatted;
  }

  @Override
  public DataIterator<Object[]> iterator() {
    return new XLSLineIterator(uri, sheetName, false, formatted, null);
  }

}
