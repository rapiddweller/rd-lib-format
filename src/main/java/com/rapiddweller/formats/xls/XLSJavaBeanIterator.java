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

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import com.rapiddweller.commons.ArrayUtil;
import com.rapiddweller.commons.Assert;
import com.rapiddweller.commons.BeanUtil;
import com.rapiddweller.commons.ConfigurationError;
import com.rapiddweller.commons.Converter;
import com.rapiddweller.commons.IOUtil;
import com.rapiddweller.commons.StringUtil;
import com.rapiddweller.commons.context.DefaultContext;
import com.rapiddweller.commons.converter.PropertyArray2JavaBeanConverter;
import com.rapiddweller.commons.converter.util.ClassProvider;
import com.rapiddweller.commons.converter.util.ConstantClassProvider;
import com.rapiddweller.commons.converter.util.ReferenceResolver;
import com.rapiddweller.formats.DataContainer;
import com.rapiddweller.formats.script.ScriptConverterForStrings;
import com.rapiddweller.formats.util.ConvertingDataIterator;

/**
 * Iterates XLS sheets and provides each row as JavaBean instance.
 * Created: 18.09.2014 15:49:49
 * @since 1.0.0
 * @author Volker Bergmann
 */

public class XLSJavaBeanIterator<E> extends ConvertingDataIterator<Object[], E> {
	
	private String uri;
	private boolean formatted;
	
	public XLSJavaBeanIterator(String uri, String sheetName, boolean formatted, Class<?> beanClass) throws IOException, InvalidFormatException {
		this(uri, sheetName, formatted, null, "", new ConstantClassProvider<Object>(beanClass));
	}

	@SuppressWarnings("unchecked")
	public XLSJavaBeanIterator(String uri, String sheetName, boolean formatted, String nullMarker, String emptyMarker, ClassProvider<Object> beanClassProvider) 
			throws IOException, InvalidFormatException {
		super(null, null);
		this.uri = uri;
		this.formatted = formatted;
		Converter<String, ?> scriptConverter = new ScriptConverterForStrings(new DefaultContext());
		XLSLineIterator iterator = new XLSLineIterator(uri, sheetName, true, formatted, scriptConverter);
		iterator.setNullMarker(nullMarker);
		iterator.setEmptyMarker(emptyMarker);
		String[] headers = parseHeaders(uri, sheetName, iterator);
		this.source = iterator;
		this.converter = (Converter<Object[], E>) new PropertyArray2JavaBeanConverter(beanClassProvider, headers, new RefResolver());
	}

	public static <T> List<T> parseAll(String uri, String sheetName, boolean formatted, Class<T> type) 
			throws InvalidFormatException, IOException {
		XLSJavaBeanIterator<T> iterator = null;
		List<T> result = new ArrayList<T>();
		try {
			iterator = new XLSJavaBeanIterator<T>(uri, sheetName, formatted, type);
			DataContainer<T> container = new DataContainer<T>();
			while (iterator.next(container) != null)
				result.add(container.getData());
		} finally {
			IOUtil.close(iterator);
		}
		return result;
	}

	public static Class<?> getFeatureComponentType(Class<?> ownerClass, String featureName) {
    	// try JavaBean property
        PropertyDescriptor propertyDescriptor = BeanUtil.getPropertyDescriptor(ownerClass, featureName);
        if (propertyDescriptor != null) {
        	Method readMethod = propertyDescriptor.getReadMethod();
        	Class<?> returnType = readMethod.getReturnType();
			if (Collection.class.isAssignableFrom(returnType)) {
				ParameterizedType genericReturnType = (ParameterizedType) readMethod.getGenericReturnType();
				Type componentType = genericReturnType.getActualTypeArguments()[0];
				return (Class<?>) componentType;
        	} else {
        		return returnType;
        	}
        } else {
        	// try attribute
        	Field field = BeanUtil.getField(ownerClass, featureName);
        	if (field != null) {
    			if (Collection.class.isAssignableFrom(field.getType())) {
	        		ParameterizedType genericReturnType = (ParameterizedType) field.getGenericType();
	    			Type componentType = genericReturnType.getActualTypeArguments()[0];
	    			return (Class<?>) componentType;
    			} else {
    				return field.getType();
    			}
        	} else {
                throw new ConfigurationError("Feature '" + featureName + "' not found in class " + ownerClass.getName());
        	}
        }
	}


	// private helpers -------------------------------------------------------------------------------------------------

	private static String[] parseHeaders(String uri, String sheetName, XLSLineIterator iterator) {
		// get headers
		String[] headers = iterator.getHeaders();
		Assert.notEmpty(headers, "Empty XLS sheet '" + sheetName + "' in document " + uri);
		// normalize headers
		for (int i = 0; i < headers.length; i++)
			headers[i] = StringUtil.trimmedEmptyToNull(headers[i]);
		// determine trailing empty headers
		int headerCount = headers.length;
		while (headers[headerCount - 1] == null && headerCount > 0)
			headerCount--;
		// verify the regular headers
		if (headerCount == 0)
			throw new IllegalArgumentException("No headers in XLS sheet '" + sheetName + "' of document " + uri);
		for (int i = 0; i < headerCount; i++)
			Assert.notNull(headers[i], "Empty header in column #" + i + " of sheet '" + sheetName + "' of file '" + uri + "'");
		// remove trailing empty headers
		return ArrayUtil.copyOfRange(headers, 0, headerCount);
	}
	
	class RefResolver implements ReferenceResolver {
		@Override
		public Object resolveReferences(Object value, Object target, String localFeatureName) {
			if (value instanceof String) {
				String text = (String) value;
				if (text.startsWith("tab:")) {
					String targetSheetName = text.substring("tab:".length());
					try {
						Class<?> targetType = getFeatureComponentType(target.getClass(), localFeatureName);
						return parseAll(uri, targetSheetName, formatted, targetType );
					} catch (Exception e) {
						throw new RuntimeException("Error parsing XLS sheet '" + targetSheetName + "' of " + uri, e);
					}
				}
			}
			return value;
		}
	}

}
