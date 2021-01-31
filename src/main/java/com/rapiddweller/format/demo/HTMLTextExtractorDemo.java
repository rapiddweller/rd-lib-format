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
package com.rapiddweller.format.demo;

import com.rapiddweller.common.ConversionException;
import com.rapiddweller.common.Converter;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.format.html.HTML2XML;
import com.rapiddweller.format.text.SplitStringConverter;
import com.rapiddweller.format.xslt.XSLTTransformer;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.ParseException;

/**
 * This class demonstrates how to use the HTML2XML utility and the XSLTTransformer
 * for extracting data from a web page.
 * 
 * Created: 16.06.2007 10:08:41
 * @author Volker Bergmann
 */
public class HTMLTextExtractorDemo {
    private static final String XSLT_FILENAME = "com/rapiddweller/format/demo/HTMLTextExtractorDemo.xsl";

    public static void main(String[] args) throws IOException, ParseException, TransformerException, ConversionException {
        // Fetch the web page as string
        String html = IOUtil.getContentOfURI("http://www.yahoo.com");

        // convert the page content to XML
        String xml = HTML2XML.convert(html);

        // load the XSLT script and execute it on the XML text
        String xslt = IOUtil.getContentOfURI(XSLT_FILENAME);
        String xsltResult = XSLTTransformer.transform(xml, xslt);
        System.out.println("XSLT result: " + xsltResult);

        // split the list by its s separator token |
        Converter<String, String[]> converter = new SplitStringConverter('|');
        String[] headlines = converter.convert(xsltResult);
        System.out.println("Yahoo headlines :");
        System.out.println("- - - - - - - - -");
        for (int i = 0; i < headlines.length; i++)
            System.out.println((i+1) + ". " + headlines[i]);
    }
}
