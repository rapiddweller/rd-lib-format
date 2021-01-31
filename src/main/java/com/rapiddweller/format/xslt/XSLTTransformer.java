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
package com.rapiddweller.format.xslt;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * Performs XSL transformations on XML strings and streams.
 * 
 * Created: 26.01.2007 08:31:09
 */
public class XSLTTransformer {

    private static final Map<String, Transformer> transformers = new HashMap<String, Transformer>();

    private static Transformer getTransformer(String xsltString) throws TransformerConfigurationException {
        Transformer transformer = transformers.get(xsltString);
        if (transformer == null) {
            Source xsltSource = new StreamSource(new StringReader(xsltString));
            transformer = TransformerFactory.newInstance().newTransformer(xsltSource);
            transformers.put(xsltString, transformer);
        }
        return transformer;
    }

    public static String transform(String xmlString, String xsltString) throws TransformerException {
        Reader source = new StringReader(xmlString);
        StringWriter writer = new StringWriter();
        transform(source, xsltString, writer);
        return writer.getBuffer().toString();
    }

    public static void transform(Reader reader, String xsltString, Writer writer) throws TransformerException {
        Transformer transformer = getTransformer(xsltString);
        transformer.transform(new StreamSource(reader), new StreamResult(writer));
    }

}
