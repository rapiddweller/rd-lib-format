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
package com.rapiddweller.format.html.parser;

import java.util.Map;
import java.io.IOException;
import java.text.ParseException;

/**
 * Abstraction of an HTML tokenizer.
 * 
 * Created: 15.06.2007 05:53:19
 * @author Volker Bergmann
 */
public interface HTMLTokenizer {

    int END                    = -1;
    int DOCUMENT_TYPE          =  0;
    int TEXT                   =  1;
    int START_TAG              =  2;
    int END_TAG                =  3;
    int CLOSED_TAG             =  4;
    int COMMENT                =  5;
    int PROCESSING_INSTRUCTION =  6;
    int SCRIPT                 =  7;

    int nextToken() throws IOException, ParseException;
    int tokenType();
    String name();
    String text();
    Map<String, String> attributes();
}
