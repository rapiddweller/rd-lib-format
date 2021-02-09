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

import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.format.PadFormat;

import java.text.ParseException;

/**
 * Parses a line of a flat file.
 * Created: 22.02.2010 08:06:41
 *
 * @author Volker Bergmann
 * @since 0.5.0
 */
public class FixedWidthLineParser {

  private final PadFormat[] formats;

  /**
   * Instantiates a new Fixed width line parser.
   *
   * @param formats the formats
   */
  public FixedWidthLineParser(PadFormat[] formats) {
    this.formats = formats.clone();
  }

  /**
   * Parse string [ ].
   *
   * @param line the line
   * @return the string [ ]
   * @throws ParseException the parse exception
   */
  public String[] parse(String line) throws ParseException {
    String[] cells = new String[formats.length];
    int offset = 0;
    if (StringUtil.isEmpty(line)) {
      return new String[0];
    } else {
      for (int i = 0; i < formats.length; i++) {
        PadFormat format = formats[i];
        String cell = line.substring(offset, Math.min(offset + format.getLength(), line.length()));
        cells[i] = (String) format.parseObject(cell);
        offset += format.getLength();
      }
      return cells;
    }
  }

}
