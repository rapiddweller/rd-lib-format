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

/**
 * Address JavaBean for testing.
 * Created: 18.09.2014 19:03:30
 *
 * @author Volker Bergmann
 * @since 1.0.0
 */
public class Address {

  /**
   * The City.
   */
  String city;

  /**
   * Gets city.
   *
   * @return the city
   */
  public String getCity() {
		return city;
	}

  /**
   * Sets city.
   *
   * @param city the city
   */
  public void setCity(String city) {
		this.city = city;
	}

}
