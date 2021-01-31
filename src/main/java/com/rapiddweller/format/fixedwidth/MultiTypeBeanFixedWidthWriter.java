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
package com.rapiddweller.format.fixedwidth;

import com.rapiddweller.common.Assert;
import com.rapiddweller.common.SystemInfo;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Writes JavaBean properties (graphs) to a file with fixed-width columns.
 * Created: 14.03.2014 16:09:37
 * @since 0.7.2
 * @author Volker Bergmann
 */

public class MultiTypeBeanFixedWidthWriter implements Closeable {
	
	private final Writer out;
	private final Map<String, FixedWidthRowTypeDescriptor> rowDescriptors;
	
	public MultiTypeBeanFixedWidthWriter(Writer out) {
		this(out, null);
	}
	
	public MultiTypeBeanFixedWidthWriter(Writer out, List<FixedWidthRowTypeDescriptor> rowDescriptors) {
		Assert.notNull(out, "Writer");
		this.out = out;
		this.rowDescriptors = new HashMap<String, FixedWidthRowTypeDescriptor>();
		if (rowDescriptors != null)
			for (FixedWidthRowTypeDescriptor rowDescriptor : rowDescriptors)
				addRowFormat(rowDescriptor.getName(), rowDescriptor);
	}
	
	public void addRowFormat(String simpleClassName, FixedWidthRowTypeDescriptor rowDescriptor) {
		this.rowDescriptors.put(simpleClassName, rowDescriptor);
	}
	
	public FixedWidthRowTypeDescriptor getRowFormat(String simpleClassName) {
		return this.rowDescriptors.get(simpleClassName);
	}
	
	public void write(Object bean) throws IOException {
		// Check preconditions
		Assert.notNull(bean, "bean");
		FixedWidthRowTypeDescriptor cellFormats = rowDescriptors.get(bean.getClass().getSimpleName());
		if (cellFormats == null)
			throw new IllegalArgumentException("Bean class not configured: " + bean.getClass().getSimpleName());
		// format row
		out.write(cellFormats.formatBean(bean));
		out.write(SystemInfo.getLineSeparator());
	}

	@Override
	public void close() throws IOException {
		this.out.close();
	}
	
}
