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
package com.rapiddweller.format.text;

import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.csv.CSVLineIterator;
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.Converter;
import com.rapiddweller.common.Encodings;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.converter.ThreadSafeConverter;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

/**
 * Delocalizes a String bye replacing local characters by international latin characters.
 * For example the umlaut 'ä' is replaced with 'ae'.
 * 
 * Created: 12.06.2006 18:53:55
 * @author Volker Bergmann
 */
public class DelocalizingConverter extends ThreadSafeConverter<String, String> {

    /** File that contains the character mapping */
    private static final String CONFIG_FILENAME = "com/rapiddweller/format/text/DelocalizingConverter.csv";

	/** The logger */
    private static final Logger logger = LogManager.getLogger(DelocalizingConverter.class);

    /** a Map of replacements. The key indicates the character to replace,
     * the value the character to use for replacement*/
    private Map<Character, String> replacements;

    /** Default constructor.
     * @throws IOException if reading the configuration file fails */
    public DelocalizingConverter() throws IOException {
    	super(String.class, String.class);
        init();
    }

    // Converter implementation ----------------------------------------------------------------------------------------

    /**
     * Implementation of the Converter interface.
     * @see Converter
     */
    @Override
	public String convert(String source) {
        String product = source;
        for (Map.Entry<Character, String> entry : replacements.entrySet())
            product = product.replace(String.valueOf(entry.getKey()), entry.getValue());
        return product;
    }

    // private initializers --------------------------------------------------------------------------------------------

    /**
     * Initializes the instance by reading the definition file of replacements
     * @throws IOException when file access fails.
     */
    private void init() throws IOException {
        replacements = new HashMap<Character, String>();
        CSVLineIterator iterator = null;
        try {
			iterator = new CSVLineIterator(CONFIG_FILENAME, ',', true, Encodings.UTF_8);
			DataContainer<String[]> tokens = new DataContainer<String[]>();
			while ((tokens = iterator.next(tokens)) != null)
			    addReplacements(tokens.getData());
		} finally {
			IOUtil.close(iterator);
		}
    }

    /**
     * adds a line from the replacement definition file to the replacement map.
     * @param tokens the tokens of one line in the file.
     * One line contains several replacement pairs.
     */
    private void addReplacements(String[] tokens) {
        if (tokens.length < 2)
            throw new ConfigurationError("At least two tokens needed to define a replacement");
        String replacement = tokens[tokens.length - 1];
        for (int i = 0; i < tokens.length - 1; i++) {
            String token = tokens[i];
            if (token.length() != 1)
                throw new ConfigurationError("Source token length must be 1, wrong for token: " + token);
            addReplacement(token.charAt(0), replacement);
        }
    }

    /**
     * Adds one replacement pair to the replacement map.
     * @param original the character to replace
     * @param replacement the String to use as replacement
     */
    private void addReplacement(char original, String replacement) {
        String preset = replacements.get(original);
        if (preset != null) {
            if (preset.equals(replacement))
                logger.warn("double definition of replacement: " + original + " -> " + replacement);
            else
                logger.error("ambiguous definition of replacement: " + original + " -> " + replacement + " / " + preset);
        }
        replacements.put(original, replacement);
    }

}
