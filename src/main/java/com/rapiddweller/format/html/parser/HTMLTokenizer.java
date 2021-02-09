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

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

/**
 * Abstraction of an HTML tokenizer.
 * <p>
 * Created: 15.06.2007 05:53:19
 *
 * @author Volker Bergmann
 */
public interface HTMLTokenizer {

  /**
   * The constant END.
   */
  int END = -1;
  /**
   * The constant DOCUMENT_TYPE.
   */
  int DOCUMENT_TYPE = 0;
  /**
   * The constant TEXT.
   */
  int TEXT = 1;
  /**
   * The constant START_TAG.
   */
  int START_TAG = 2;
  /**
   * The constant END_TAG.
   */
  int END_TAG = 3;
  /**
   * The constant CLOSED_TAG.
   */
  int CLOSED_TAG = 4;
  /**
   * The constant COMMENT.
   */
  int COMMENT = 5;
  /**
   * The constant PROCESSING_INSTRUCTION.
   */
  int PROCESSING_INSTRUCTION = 6;
  /**
   * The constant SCRIPT.
   */
  int SCRIPT = 7;

  /**
   * Next token int.
   *
   * @return the int
   * @throws IOException    the io exception
   * @throws ParseException the parse exception
   */
  int nextToken() throws IOException, ParseException;

  /**
   * Token type int.
   *
   * @return the int
   */
  int tokenType();

  /**
   * Name string.
   *
   * @return the string
   */
  String name();

  /**
   * Text string.
   *
   * @return the string
   */
  String text();

  /**
   * Attributes map.
   *
   * @return the map
   */
  Map<String, String> attributes();
}
