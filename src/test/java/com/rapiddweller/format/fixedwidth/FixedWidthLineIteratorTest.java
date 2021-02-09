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

import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.common.format.Alignment;
import com.rapiddweller.common.format.PadFormat;
import com.rapiddweller.format.DataContainer;
import org.junit.Test;

import java.io.StringReader;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link FixedWidthLineIterator}.
 * <p>
 * Created: 27.08.2007 07:20:05
 *
 * @author Volker Bergmann
 */
public class FixedWidthLineIteratorTest {

  private static final String SEP = SystemInfo.getLineSeparator();

  /**
   * Test processing empty lines.
   *
   * @throws Exception the exception
   */
  @Test
  public void testProcessingEmptyLines() throws Exception {
    FixedWidthLineIterator iterator = createIterator(true);
    DataContainer<String[]> container = new DataContainer<String[]>();
    assertTrue(Arrays.equals(new String[] {"Alice", "23"}, iterator.next(container).getData()));
    assertTrue(Arrays.equals(new String[] {"Bob", "34"}, iterator.next(container).getData()));
    assertTrue(Arrays.equals(new String[] {"Charly", "45"}, iterator.next(container).getData()));
    assertTrue(Arrays.equals(new String[] {"Dieter", "-1"}, iterator.next(container).getData()));
  }

  /**
   * Test ignoring empty lines.
   *
   * @throws Exception the exception
   */
  @Test
  public void testIgnoringEmptyLines() throws Exception {
    FixedWidthLineIterator iterator = createIterator(false);
    DataContainer<String[]> container = new DataContainer<String[]>();
    assertTrue(Arrays.equals(new String[] {"Alice", "23"}, iterator.next(container).getData()));
    assertTrue(Arrays.equals(new String[] {"Bob", "34"}, iterator.next(container).getData()));
    assertTrue(Arrays.equals(new String[] {}, iterator.next(container).getData()));
    assertTrue(Arrays.equals(new String[] {"Charly", "45"}, iterator.next(container).getData()));
    assertTrue(Arrays.equals(new String[] {"Dieter", "-1"}, iterator.next(container).getData()));
  }

  // helper ----------------------------------------------------------------------------------------------------------

  private static FixedWidthLineIterator createIterator(boolean ignoreEmptyLines) {
    PadFormat[] formats = new PadFormat[] {
        new PadFormat("", 6, Alignment.LEFT, ' '),
        new PadFormat("", 3, Alignment.RIGHT, '0'),
    };
    StringReader reader = new StringReader(
        "Alice 023" + SEP +
            "Bob   034" + SEP +
            "" + SEP +
            "Charly045" + SEP +
            "Dieter-01"
    );
    FixedWidthLineIterator iterator = new FixedWidthLineIterator(reader, formats, ignoreEmptyLines, null);
    return iterator;
  }

}
