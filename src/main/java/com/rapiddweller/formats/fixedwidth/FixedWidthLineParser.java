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

import java.text.ParseException;

import com.rapiddweller.commons.StringUtil;
import com.rapiddweller.commons.format.PadFormat;

/**
 * Parses a line of a flat file.
 * Created: 22.02.2010 08:06:41
 * @since 0.5.0
 * @author Volker Bergmann
 */
public class FixedWidthLineParser {

	private final PadFormat[] formats;

    public FixedWidthLineParser(PadFormat[] formats) {
	    this.formats = formats.clone();
    }

    public String[] parse(String line) throws ParseException {
        String[] cells = new String[formats.length];
        int offset = 0;
        if (StringUtil.isEmpty(line))
            return new String[0];
        else {
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
