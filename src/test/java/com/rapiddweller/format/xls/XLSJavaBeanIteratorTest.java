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

package com.rapiddweller.format.xls;

import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.TimeUtil;
import com.rapiddweller.format.Address;
import com.rapiddweller.format.DataContainer;
import com.rapiddweller.format.PersonWithAddress;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

/**
 * Tests the {@link XLSJavaBeanIterator}.
 * Created: 18.09.2014 18:57:05
 *
 * @author Volker Bergmann
 * @since 1.0.0
 */
public class XLSJavaBeanIteratorTest {

  /**
   * Test flat.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testFlat() throws IOException {
    XLSJavaBeanIterator<PersonWithAddress> iterator =
        new XLSJavaBeanIterator<PersonWithAddress>("com/rapiddweller/format/xls/person_lines.xls", "persons", true, PersonWithAddress.class);
    DataContainer<PersonWithAddress> wrapper = new DataContainer<PersonWithAddress>();
    assertNotNull(iterator.next(wrapper));
    assertContent("Alice", 23, TimeUtil.date(2011, 0, 1), wrapper);
    assertNotNull(iterator.next(wrapper));
    assertContent("Bob", 34, TimeUtil.date(2011, 0, 2), wrapper);
    assertNull(iterator.next(wrapper));
    IOUtil.close(iterator);
  }


  /**
   * Test trailing null headers.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testTrailingNullHeaders() throws IOException {
    XLSJavaBeanIterator<PersonWithAddress> iterator =
        new XLSJavaBeanIterator<PersonWithAddress>("com/rapiddweller/format/xls/person_lines_empty_headers_trailing.xls", "persons", true,
            PersonWithAddress.class);
    DataContainer<PersonWithAddress> wrapper = new DataContainer<PersonWithAddress>();
    assertNotNull(iterator.next(wrapper));
    assertContent("Alice", 23, TimeUtil.date(2011, 0, 1), wrapper);
    assertNotNull(iterator.next(wrapper));
    assertContent("Bob", 34, TimeUtil.date(2011, 0, 2), wrapper);
    assertNull(iterator.next(wrapper));
    IOUtil.close(iterator);
  }

  /**
   * Test one to one association.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testOneToOneAssociation() throws IOException {
    XLSJavaBeanIterator<PersonWithAddress> iterator =
        new XLSJavaBeanIterator<PersonWithAddress>("com/rapiddweller/format/xls/persons_with_address.xls", "persons", true, PersonWithAddress.class);
    DataContainer<PersonWithAddress> wrapper = new DataContainer<PersonWithAddress>();
    assertNotNull(iterator.next(wrapper));
    assertContent("Alice", 23, "London", wrapper);
    assertNotNull(iterator.next(wrapper));
    assertContent("Bob", 34, "New York", wrapper);
    assertNull(iterator.next(wrapper));
    IOUtil.close(iterator);
  }

  /**
   * Test one to many association.
   *
   * @throws IOException the io exception
   */
  @Test
  public void testOneToManyAssociation() throws IOException {
    XLSJavaBeanIterator<PersonWithAddress> iterator =
        new XLSJavaBeanIterator<PersonWithAddress>("com/rapiddweller/format/xls/persons_with_addresses.xls", "persons", true,
            PersonWithAddress.class);
    DataContainer<PersonWithAddress> wrapper = new DataContainer<PersonWithAddress>();
    assertNotNull(iterator.next(wrapper));
    assertContent("Alice", 23, "London", "Dover", wrapper);
    assertNotNull(iterator.next(wrapper));
    assertContent("Bob", 34, "New York", "Hauppauge", wrapper);
    assertNull(iterator.next(wrapper));
    IOUtil.close(iterator);
  }


  // private helpers -------------------------------------------------------------------------------------------------

  private static void assertContent(String name, int age, String city, DataContainer<PersonWithAddress> wrapper) {
    PersonWithAddress data = wrapper.getData();
    assertNotNull(data);
    assertEquals(name, data.getName());
    assertEquals(age, data.getAge());
    assertEquals(city, data.getAddress().getCity());
  }

  /**
   * Test get feature component type.
   */
  @Test
  public void testGetFeatureComponentType() {
    Class<?> actualFeatureComponentType = XLSJavaBeanIterator.getFeatureComponentType(Object.class, "class");
    assertSame(Class.class, actualFeatureComponentType);
  }

  private static void assertContent(String name, int age, Date date, DataContainer<PersonWithAddress> wrapper) {
    Object data = wrapper.getData();
    assertNotNull(data);
    PersonWithAddress person = (PersonWithAddress) data;
    assertEquals(name, person.getName());
    assertEquals(age, person.getAge());
    assertEquals(date, person.getDate());
  }

  private static void assertContent(String name, int age, String city1, String city2, DataContainer<PersonWithAddress> wrapper) {
    Object data = wrapper.getData();
    assertNotNull(data);
    PersonWithAddress person = (PersonWithAddress) data;
    assertEquals(name, person.getName());
    assertEquals(age, person.getAge());
    List<Address> addresses = person.getAddresses();
    assertEquals(city1, addresses.get(0).getCity());
    assertEquals(city2, addresses.get(1).getCity());
  }

}
