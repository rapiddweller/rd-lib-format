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

package com.rapiddweller.format.script;

/**
 * Indicates the failure of a {@link Script} execution.
 * <p>
 * Created: 03.02.2007 12:19:00
 *
 * @author Volker Bergmann
 */
public class ScriptException extends RuntimeException {

  private static final long serialVersionUID = 837802417051369136L;

  /**
   * Instantiates a new Script exception.
   */
  public ScriptException() {
  }

  /**
   * Instantiates a new Script exception.
   *
   * @param message the message
   */
  public ScriptException(String message) {
    super(message);
  }

  /**
   * Instantiates a new Script exception.
   *
   * @param message the message
   * @param cause   the cause
   */
  public ScriptException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Instantiates a new Script exception.
   *
   * @param cause the cause
   */
  public ScriptException(Throwable cause) {
    super(cause);
  }
}
