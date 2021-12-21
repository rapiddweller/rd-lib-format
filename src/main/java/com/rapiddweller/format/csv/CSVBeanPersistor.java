/* (c) Copyright 2013-2021 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.format.csv;

import com.rapiddweller.common.Consumer;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.format.DataContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Write and reads JavaBeans to and from CSV files.<br/><br/>
 * Created: 04.08.2013 07:11:00
 * @author Volker Bergmann
 */
public class CSVBeanPersistor<E> {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public static<T> List<T> loadAsList(String uri, Class<T> type, char separator, String encoding) {
		List<T> result = new ArrayList<>();
		CSVBeanPersistor<T> persistor = new CSVBeanPersistor<>(type, separator);
		Reader reader = null;
		try {
			reader = IOUtil.getReaderForURI(uri, encoding);
			persistor.load(reader, t -> result.add(t));
		} finally {
			IOUtil.close(reader);
		}
		return result;
	}

	// instance attributes -------------------------------------------------------------------------------------------
	
	private final Class<E> beanClass;
	private final String[] beanProperties;
	private final char separator;

	public CSVBeanPersistor(Class<E> beanClass, String... beanProperties) {
		this(beanClass, ';', beanProperties);
	}
	
	public CSVBeanPersistor(Class<E> beanClass, char separator, String... beanProperties) {
		this.beanClass = beanClass;
		this.separator = separator;
		this.beanProperties = beanProperties;
	}

	protected void load(File file, Consumer<E> consumer) throws IOException {
		try (FileReader reader = new FileReader(file)) {
			load(reader, consumer);
		}
	}

	protected void load(Reader reader, Consumer<E> consumer) {
		try (CSVToJavaBeanMapper<E> mapper = new CSVToJavaBeanMapper<>(reader, beanClass, separator, null)) {
			DataContainer<E> wrapper = new DataContainer<>();
			while (mapper.next(wrapper) != null) {
				consumer.consume(wrapper.getData());
			}
		}
	}

	protected void save(File file, Iterator<E> beanIterator) throws IOException {
		try (BeanCSVWriter<E> out = new BeanCSVWriter<>(new FileWriter(file), separator, beanProperties)) {
			while (beanIterator.hasNext()) {
				out.writeElement(beanIterator.next());
			}
		}
	}

}
