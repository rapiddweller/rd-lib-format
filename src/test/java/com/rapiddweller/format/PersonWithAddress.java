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
package com.rapiddweller.format;

import java.util.Date;
import java.util.List;

/**
 * Person JavaBean for testing.
 * Created: 18.09.2014 19:03:00
 *
 * @author Volker Bergmann
 * @since 1.0.0
 */
public class PersonWithAddress {

  /**
   * The Name.
   */
  String name;
  /**
   * The Age.
   */
  int age;
  /**
   * The Date.
   */
  Date date;
  /**
   * The Address.
   */
  Address address;
  /**
   * The Addresses.
   */
  List<Address> addresses;

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
		return name;
	}

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
		this.name = name;
	}

  /**
   * Gets age.
   *
   * @return the age
   */
  public int getAge() {
		return age;
	}

  /**
   * Sets age.
   *
   * @param age the age
   */
  public void setAge(int age) {
		this.age = age;
	}

  /**
   * Gets date.
   *
   * @return the date
   */
  public Date getDate() {
		return date;
	}

  /**
   * Sets date.
   *
   * @param date the date
   */
  public void setDate(Date date) {
		this.date = date;
	}

  /**
   * Gets address.
   *
   * @return the address
   */
  public Address getAddress() {
		return address;
	}

  /**
   * Sets address.
   *
   * @param address the address
   */
  public void setAddress(Address address) {
		this.address = address;
	}

  /**
   * Gets addresses.
   *
   * @return the addresses
   */
  public List<Address> getAddresses() {
		return addresses;
	}

  /**
   * Sets addresses.
   *
   * @param addresses the addresses
   */
  public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

}
