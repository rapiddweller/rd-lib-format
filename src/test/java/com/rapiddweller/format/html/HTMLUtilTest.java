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

package com.rapiddweller.format.html;

import com.rapiddweller.common.CollectionUtil;
import com.rapiddweller.format.html.util.HTMLUtil;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Tests the {@link HTMLUtil} class.
 * Created: 22.11.2010 08:09:08
 *
 * @author Volker Bergmann
 * @since 0.5.4
 */
public class HTMLUtilTest {

  /**
   * Test.
   */
  @Test
  public void test() {
    @SuppressWarnings("unchecked")
    Map<String, String> expected = CollectionUtil.buildMap("loc", "60", "f", "test.info");
    Map<String, String> actual = HTMLUtil.parseCGIParameters("http://myhost.com/info.php?loc=60&f=test.info");
    assertEquals(expected, actual);
  }

}
