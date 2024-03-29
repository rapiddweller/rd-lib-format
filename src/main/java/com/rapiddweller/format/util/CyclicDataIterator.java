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

import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.DataIterator;

import java.io.IOException;

/**
 * Allows repeated iteration through a {@link DataIterator}.<br/><br/>
 * Created: 22.05.2012 08:58:16
 * @param <E> the type of data to iterate
 * @author Volker Bergmann
 * @since 0.6.9
 */
public class CyclicDataIterator<E> extends DataIteratorProxy<E> {

  protected Creator<E> creator;

  public CyclicDataIterator(Creator<E> creator) throws IOException {
    super(creator.create());
    this.creator = creator;
  }

  @Override
  public synchronized DataContainer<E> next(DataContainer<E> wrapper) {
    DataContainer<E> result = super.next(wrapper);
    if (result == null) {
      reset();
      result = super.next(wrapper);
    }
    return result;
  }

  public synchronized void reset() {
    IOUtil.close(source);
    try {
      source = creator.create();
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().internalError("Error creating DataIterator", e);
    }
  }

  public interface Creator<E> {
    DataIterator<E> create() throws IOException;
  }

}
