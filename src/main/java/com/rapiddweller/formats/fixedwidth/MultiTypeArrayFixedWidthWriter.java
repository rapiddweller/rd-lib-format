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

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rapiddweller.commons.Assert;
import com.rapiddweller.commons.SystemInfo;

/**
 * Writes data arrays to fixed-width files supporting different row types and formats.
 * Created: 13.03.2014 12:37:54
 * @since 0.7.2
 * @author Volker Bergmann
 */

public class MultiTypeArrayFixedWidthWriter implements Closeable {
	
	private final Writer out;
	private Map<String, FixedWidthRowTypeDescriptor> rowDescriptors;
	
	public MultiTypeArrayFixedWidthWriter(Writer out) {
		this(out, null);
	}
	
	public MultiTypeArrayFixedWidthWriter(Writer out, List<FixedWidthRowTypeDescriptor> rowDescriptors) {
		Assert.notNull(out, "Writer");
		this.out = out;
		this.rowDescriptors = new HashMap<String, FixedWidthRowTypeDescriptor>();
		if (rowDescriptors != null)
			for (FixedWidthRowTypeDescriptor rowDescriptor : rowDescriptors)
				addRowFormat(rowDescriptor);
	}
	
	public void addRowFormat(FixedWidthRowTypeDescriptor rowDescriptor) {
		this.rowDescriptors.put(rowDescriptor.getName(), rowDescriptor);
	}
	
	public void write(String rowTypeName, Object... values) throws IOException {
		// Check preconditions
		Assert.notNull(values, "array");
		FixedWidthRowTypeDescriptor rowType = rowDescriptors.get(rowTypeName);
		if (rowType == null)
			throw new IllegalArgumentException("Illegal row type: " + rowTypeName);
		// format array
		out.write(rowType.formatArray(values));
		out.write(SystemInfo.getLineSeparator());
	}

	@Override
	public void close() throws IOException {
		this.out.close();
	}
	
}
