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
package com.rapiddweller.formats.fixedwidth;

import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;

import com.rapiddweller.commons.BeanUtil;
import com.rapiddweller.commons.format.PadFormat;
import com.rapiddweller.formats.DataContainer;
import com.rapiddweller.formats.util.DataIteratorAdapter;

/**
 * Iterates fixed-width column files.
 * Created: 20.12.2011 18:13:28
 * @param <E> the type of the objects to provide
 * @since 0.6.6
 * @author Volker Bergmann
 */
public class FixedWidthBeanIterator<E> extends DataIteratorAdapter<String[], E> {

	private Class<E> beanClass;
	private FixedWidthColumnDescriptor[] columnDescriptors;
	private Locale locale;
	
	public FixedWidthBeanIterator(String uri, String encoding, Class<E> beanClass, String columnFormats) 
			throws IOException, ParseException {
		this(uri, encoding, beanClass, columnFormats, "");
	}

	public FixedWidthBeanIterator(String uri, String encoding, Class<E> beanClass, String columnFormats, String nullString) 
			throws IOException, ParseException {
		super(null);
		this.locale = Locale.US;
		this.beanClass = beanClass;
		FixedWidthRowTypeDescriptor rowDescriptor = FixedWidthUtil.parseBeanColumnsSpec(columnFormats, beanClass.getSimpleName(), nullString, locale);
		this.columnDescriptors = rowDescriptor.getColumnDescriptors();
		PadFormat[] formats = BeanUtil.extractProperties(this.columnDescriptors, "format", PadFormat.class);
		source = new FixedWidthLineIterator(uri, formats);
	}

	@Override
	public Class<E> getType() {
		return beanClass;
	}

	@Override
	public DataContainer<E> next(DataContainer<E> container) {
		DataContainer<String[]> wrapper = nextOfSource();
		if (wrapper == null)
			return null;
		String[] cells = wrapper.getData();
		E result = BeanUtil.newInstance(beanClass);
		for (int i = 0; i < columnDescriptors.length; i++)
			BeanUtil.setPropertyValue(result, columnDescriptors[i].getName(), cells[i], true, true);
		return container.setData(result);
	}

}
