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

import com.rapiddweller.common.ReaderLineIterator;
import com.rapiddweller.common.TimeUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the {@link MultiTypeArrayFixedWidthWriter}.
 * Created: 13.03.2014 13:10:27
 *
 * @author Volker Bergmann
 * @since 0.7.2
 */
public class MultiTypeArrayFixedWidthWriterTest {

  /**
   * Test default formats.
   *
   * @throws Exception the exception
   */
  @Test
  public void testDefaultFormats() throws Exception {
    String fileName = "target" + File.separator + getClass().getSimpleName() + ".fcw";
    FileWriter out = new FileWriter(fileName);
    MultiTypeArrayFixedWidthWriter writer = new MultiTypeArrayFixedWidthWriter(out);
    FixedWidthRowTypeDescriptor f1 = FixedWidthUtil.parseArrayColumnsSpec("8,3r0,10,5", "t1", "", Locale.US);
    writer.addRowFormat(f1);
    FixedWidthRowTypeDescriptor f2 = FixedWidthUtil.parseArrayColumnsSpec("6,5r0,15", "t2", "", Locale.US);
    writer.addRowFormat(f2);
    writer.write("t1", "Alice", 23, TimeUtil.date(2014, Calendar.JANUARY, 1), 1.23);
    writer.write("t2", "Bob", 34, TimeUtil.date(2014, Calendar.FEBRUARY, 28));
    writer.close();
    ReaderLineIterator iterator = new ReaderLineIterator(new FileReader(fileName));
    assertTrue(iterator.hasNext());
    assertEquals("Alice   0232014-01-011.23 ", iterator.next());
    assertTrue(iterator.hasNext());
    assertEquals("Bob   000342014-02-28     ", iterator.next());
    assertFalse(iterator.hasNext());
    iterator.close();
  }

}
