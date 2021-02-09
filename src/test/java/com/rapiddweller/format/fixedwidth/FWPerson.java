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

/**
 * Helper class for testing.
 * Created: 14.03.2014 16:30:40
 *
 * @author Volker Bergmann
 * @since 0.7.2
 */
public class FWPerson {

  private String name;
  private int age;
  private FWPet pet;

  /**
   * Instantiates a new Fw person.
   *
   * @param name the name
   * @param age  the age
   * @param pet  the pet
   */
  public FWPerson(String name, int age, FWPet pet) {
    this.name = name;
    this.age = age;
    this.pet = pet;
  }

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
   * Gets pet.
   *
   * @return the pet
   */
  public FWPet getPet() {
    return pet;
  }

  /**
   * Sets pet.
   *
   * @param pet the pet
   */
  public void setPet(FWPet pet) {
    this.pet = pet;
  }

}
