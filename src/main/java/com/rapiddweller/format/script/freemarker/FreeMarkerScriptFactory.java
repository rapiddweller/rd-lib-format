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

package com.rapiddweller.format.script.freemarker;

import com.rapiddweller.common.IOUtil;
import com.rapiddweller.format.script.Script;
import com.rapiddweller.format.script.ScriptFactory;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * Creates {@link FreeMarkerScript}s.
 * Created: 27.01.2008 16:47:21
 *
 * @author Volker Bergmann
 * @since 0.3.0
 */
public class FreeMarkerScriptFactory implements ScriptFactory {

  private final Configuration config;

  /**
   * Instantiates a new Free marker script factory.
   */
  public FreeMarkerScriptFactory() {
    this(Locale.getDefault());
  }

  /**
   * Instantiates a new Free marker script factory.
   *
   * @param locale the locale
   */
  public FreeMarkerScriptFactory(Locale locale) {
    config = new Configuration();
    config.setClassForTemplateLoading(FreeMarkerScript.class, "/");
    config.setObjectWrapper(new DefaultObjectWrapper());
    config.setNumberFormat("0.##");
    config.setLocale(locale);
  }

  @Override
  public Script parseText(String text) {
    try {
      StringReader reader = new StringReader(text);
      Template template = new Template(text, reader, config);
      return new FreeMarkerScript(template);
    } catch (IOException e) {
      throw new RuntimeException(e); // This is not supposed to happen
    }
  }

  @Override
  public Script readFile(String uri) throws IOException {
    InputStreamReader reader = new InputStreamReader(IOUtil.getInputStreamForURI(uri), StandardCharsets.UTF_8);
    Template template = new Template(null, reader, config);
    return new FreeMarkerScript(template);
  }

}
