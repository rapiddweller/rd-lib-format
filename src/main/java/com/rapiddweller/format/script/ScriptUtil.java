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

import com.rapiddweller.common.BeanUtil;
import com.rapiddweller.common.Context;
import com.rapiddweller.common.FileUtil;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.common.file.FileResourceNotFoundException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Utility class for scripting.
 * @author Volker Bergmann
 * @since 0.3.0
 */
public class ScriptUtil {

  private static final Logger logger = LoggerFactory.getLogger(ScriptUtil.class);

  public static final String COMPONENT_NAME = "rd-lib-script";

  // extension mapping -----------------------------------------------------------------------------------------------

  private static String defaultScriptEngine = "ftl";

  private static Map<String, ScriptFactory> factories;

  private static final String SETUP_FILE_NAME = "com/rapiddweller/format/script/script.properties";

  static {
    parseConfigFile();
  }

  private ScriptUtil() {
    // private constructor to prevent instantiation
  }

  // utility methods -------------------------------------------------------------------------------------------------

  public static Object execute(Script script, Context context) {
    return script.evaluate(context);
  }

  public static Object evaluate(String text, Context context) {
    String trimmedText = text.trim();
    if (trimmedText.startsWith("{{") && trimmedText.endsWith("}}")) {
      return trimmedText.substring(1, trimmedText.length() - 1);
    } else if (isScript(trimmedText)) {
      Script script = parseUnspecificText(text);
      return execute(script, context);
    } else {
      return text;
    }
  }

  // static factory methods ------------------------------------------------------------------------------------------

  private static final Map<String, Script> scriptsByName = new WeakHashMap<>();

  public static Script readFile(String uri) {
    try {
      Script script = scriptsByName.get(uri);
      if (script == null) {
        script = factoryForUri(uri).readFile(uri);
        scriptsByName.put(uri, script);
      }
      return script;
    } catch (FileNotFoundException e) {
      throw ExceptionFactory.getInstance().fileNotFound(uri, e);
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().fileAccessException("Failed to read file " + uri, e);
    }
  }

  public static Script parseUnspecificText(String text) {
    if (isScript(text)) {
      return parseScriptText(text.substring(1, text.length() - 1), false);
    }
    return new ConstantScript(text);
  }

  public static boolean isScript(String text) {
    if (StringUtil.isEmpty(text)) {
      return false;
    }
    String trimmedText = text.trim();
    return trimmedText.startsWith("{") && trimmedText.endsWith("}");
  }

  public static Script parseScriptText(String text) {
    return parseScriptText(text, true);
  }

  public static Script parseScriptText(String text, boolean removeBrackets) {
    if (text == null) {
      return null;
    }
    if (removeBrackets && text.startsWith("{") && text.endsWith("}")) {
      text = text.substring(1, text.length() - 1);
    }
    String[] tokens = StringUtil.splitOnFirstSeparator(text, ':');
    String engineId = tokens[0];
    if (getFactory(engineId, false) != null) {
      String scriptText = tokens[1];
      return parseScriptText(scriptText, engineId);
    } else {
      return parseScriptText(text, ScriptUtil.getDefaultScriptEngine());
    }
  }

  public static Script parseScriptText(String text, String engineId) {
    if (engineId == null) {
      throw ExceptionFactory.getInstance().illegalArgument("engineId is null");
    }
    ScriptFactory factory = getFactory(engineId, false);
    if (factory != null) {
      return factory.parseText(text);
    } else {
      return new ConstantScript(text);
    }
  }

  public static void addFactory(String name, ScriptFactory factory) {
    factories.put(name, factory);
  }

  public static String getDefaultScriptEngine() {
    return defaultScriptEngine;
  }

  public static void setDefaultScriptEngine(String defaultScriptEngine) {
    if (factories.get(defaultScriptEngine) == null) {
      throw ExceptionFactory.getInstance().illegalArgument("Unknown script engine id: " + defaultScriptEngine);
    }
    ScriptUtil.defaultScriptEngine = defaultScriptEngine;
  }

  public static String combineScriptableParts(String... parts) {
    String scriptEngine = getCommonScriptEngine(parts);
    boolean template = ("ftl".equals(scriptEngine));
    boolean language = (scriptEngine != null && !template);
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < parts.length; i++) {
      if (i > 0 && language) {
        builder.append(" + ");
      }
      String part = parts[i];
      ScriptDescriptor descriptor = new ScriptDescriptor(part);
      if (descriptor.level != ScriptLevel.NONE || !language) {
        builder.append(descriptor.text);
      } else {
        builder.append("'").append(descriptor.text).append("'");
      }
    }
    if (scriptEngine != null) {
      builder.insert(0, '{');
      if (!ScriptUtil.getDefaultScriptEngine().equals(scriptEngine)) {
        builder.insert(1, scriptEngine + ":");
      }
      builder.append('}');
    }
    return builder.toString();
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private static ScriptFactory factoryForUri(String uri) {
    String engineId = FileUtil.suffix(uri);
    return getFactory(engineId, true);
  }

  static String getCommonScriptEngine(String... parts) {
    ScriptDescriptor[] descriptors = describe(parts);
    for (int i = 0; i < parts.length; i++) {
      if (descriptors[i].scriptEngine != null) {
        return descriptors[i].scriptEngine;
      }
    }
    return null;
  }

  static ScriptDescriptor[] describe(String... parts) {
    ScriptDescriptor[] descriptors = new ScriptDescriptor[parts.length];
    for (int i = 0; i < parts.length; i++) {
      descriptors[i] = new ScriptDescriptor(parts[i]);
    }
    return descriptors;
  }

  static ScriptFactory getFactory(String engineId, boolean required) {
    ScriptFactory factory = factories.get(engineId);
    if (factory == null && required) {
      throw ExceptionFactory.getInstance().illegalArgument("Not a supported script engine: " + engineId);
    }
    return factory;
  }

  private static void parseConfigFile() {
    String className;
    try {
      factories = new HashMap<>();
      // read config file
      logger.debug("Initializing Script mapping from file " + SETUP_FILE_NAME);
      Map<String, String> properties = IOUtil.readProperties(SETUP_FILE_NAME);
      for (Map.Entry<String, String> entry : properties.entrySet()) {
        className = entry.getValue();
        ScriptFactory factory = (ScriptFactory) BeanUtil.newInstance(className);
        addFactory(entry.getKey(), factory);
      }
    } catch (Exception e) {
      throw ExceptionFactory.getInstance().componentInitializationFailed(COMPONENT_NAME, e);
    }

  }

}
