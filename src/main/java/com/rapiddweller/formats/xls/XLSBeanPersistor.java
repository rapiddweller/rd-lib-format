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

package com.rapiddweller.formats.xls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import com.rapiddweller.commons.Assert;
import com.rapiddweller.commons.Consumer;
import com.rapiddweller.commons.IOUtil;
import com.rapiddweller.formats.DataContainer;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Loads and saves JavaBeans from and in Excel documents.<br>
 * Created: 25.12.2015 14:51:09
 * @since 1.0.7
 * @author Volker Bergmann
 */
public class XLSBeanPersistor<E> {
	
	protected Logger logger = LogManager.getLogger(getClass());
	
	private final Class<E> beanClass;
	private final List<PropFormat> beanProperties;
	
	public XLSBeanPersistor(Class<E> beanClass, String... propertyNames) {
		this.beanClass = beanClass;
		this.beanProperties = new ArrayList<PropFormat>();
		for (String propertyName : propertyNames)
			this.addProperty(propertyName);
	}
	
	protected PropFormat addProperty(String name) {
		PropFormat propFormat = new PropFormat(name);
		this.beanProperties.add(propFormat);
		return propFormat;
	}

	protected void load(File file, Consumer<E> consumer) throws IOException, InvalidFormatException {
		XLSJavaBeanIterator<E> mapper = null;
		try {
			mapper = new XLSJavaBeanIterator<E>(file.getAbsolutePath(), null, false, beanClass);
			DataContainer<E> wrapper = new DataContainer<E>();
			while (mapper.next(wrapper) != null)
				if (wrapper.getData() != null)
					consumer.consume(wrapper.getData());
		} finally {
			IOUtil.close(mapper);
		}
	}
	
	protected void save(File file, String sheetName, Iterator<E> beanIterator) throws IOException {
		// check preconditions
		Assert.notNull(file, "file");
		Assert.notNull(sheetName, "sheetName");
		Assert.notNull(beanIterator, "beanIterator");
		// save
		BeanXLSWriter<E> out = null;
		try {
			out = new BeanXLSWriter<E>(new FileOutputStream(file), sheetName, beanProperties);
			while (beanIterator.hasNext())
				out.save(beanIterator.next());
		} finally {
			IOUtil.close(out);
		}
	}
	
}
