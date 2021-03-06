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
import com.rapiddweller.common.ConfigurationError;
import com.rapiddweller.common.Context;
import com.rapiddweller.common.FileUtil;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.LogCategoriesConstants;
import com.rapiddweller.common.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;


/**
 * Utility class for scripting.
 *
 * @author Volker Bergmann
 * @since 0.3.0
 */
public class ScriptUtil {

  private static final Logger CONFIG_LOGGER = LogManager.getLogger(LogCategoriesConstants.CONFIG);
  private static final Logger SCRIPTUTIL_LOGGER = LogManager.getLogger(ScriptUtil.class);

  // extension mapping -----------------------------------------------------------------------------------------------

  private static String defaultScriptEngine = "ftl";

  private static Map<String, ScriptFactory> factories;

  private static final String SETUP_FILE_NAME = "com/rapiddweller/format/script/script.properties";

  static {
    parseConfigFile();
  }

  // utility methods -------------------------------------------------------------------------------------------------

  /**
   * Execute object.
   *
   * @param script  the script
   * @param context the context
   * @return the object
   */
  public static Object execute(Script script, Context context) {
    return script.evaluate(context);
  }

    /*    
    public static void execute(String filename, Writer out,
            String variableName, Object variableValue) throws IOException,
            ScriptException {
        Context context = new DefaultContext();
        context.set(variableName, variableValue);
        execute(filename, context, out);
    }

    public static void execute(String filename, Context context, Writer out)
            throws IOException, ScriptException {
        Script script = getInstance(filename);
        script.execute(context, out);
    }

    public static void execute(String filename, Writer out,
            ScriptVariable... variables) throws IOException, ScriptException {
        Script script = getInstance(filename);
        Context context = new DefaultContext();
        for (ScriptVariable variable : variables)
            context.set(variable.getName(), variable.getValue());
        script.execute(context, out);
    }
*/

  /**
   * Evaluate object.
   *
   * @param text    the text
   * @param context the context
   * @return the object
   */
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

  private static final Map<String, Script> scriptsByName = new WeakHashMap<String, Script>();

  /**
   * Read file script.
   *
   * @param uri the uri
   * @return the script
   * @throws IOException the io exception
   */
  public static Script readFile(String uri) throws IOException {
    Script script = scriptsByName.get(uri);
    if (script == null) {
      String engineId = FileUtil.suffix(uri);
      ScriptFactory factory = getFactory(engineId, true);
      script = factory.readFile(uri);
      scriptsByName.put(uri, script);
    }
    return script;
  }

  /**
   * Parse unspecific text script.
   *
   * @param text the text
   * @return the script
   */
  public static Script parseUnspecificText(String text) {
    if (isScript(text)) {
      return parseScriptText(text.substring(1, text.length() - 1), false);
    }
    return new ConstantScript(text);
  }

  /**
   * Is script boolean.
   *
   * @param text the text
   * @return the boolean
   */
  public static boolean isScript(String text) {
    if (StringUtil.isEmpty(text)) {
      return false;
    }
    String trimmedText = text.trim();
    return trimmedText.startsWith("{") && trimmedText.endsWith("}");
  }

  /**
   * Parse script text script.
   *
   * @param text the text
   * @return the script
   */
  public static Script parseScriptText(String text) {
    return parseScriptText(text, true);
  }

  /**
   * Parse script text script.
   *
   * @param text           the text
   * @param removeBrackets the remove brackets
   * @return the script
   */
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

  /**
   * Parse script text script.
   *
   * @param text     the text
   * @param engineId the engine id
   * @return the script
   */
  public static Script parseScriptText(String text, String engineId) {
    if (engineId == null) {
      throw new IllegalArgumentException("engineId is null");
    }
    ScriptFactory factory = getFactory(engineId, false);
    if (factory != null) {
      return factory.parseText(text);
    } else {
      return new ConstantScript(text);
    }
  }

  /**
   * Add factory.
   *
   * @param name    the name
   * @param factory the factory
   */
  public static void addFactory(String name, ScriptFactory factory) {
    factories.put(name, factory);
  }

  /**
   * Gets default script engine.
   *
   * @return the default script engine
   */
  public static String getDefaultScriptEngine() {
    return defaultScriptEngine;
  }

  /**
   * Sets default script engine.
   *
   * @param defaultScriptEngine the default script engine
   */
  public static void setDefaultScriptEngine(String defaultScriptEngine) {
    if (factories.get(defaultScriptEngine) == null) {
      throw new RuntimeException("Unknown script engine id: " + defaultScriptEngine);
    }
    ScriptUtil.defaultScriptEngine = defaultScriptEngine;
  }

  /**
   * Combine scriptable parts string.
   *
   * @param parts the parts
   * @return the string
   */
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

  /**
   * Gets common script engine.
   *
   * @param parts the parts
   * @return the common script engine
   */
  static String getCommonScriptEngine(String... parts) {
    ScriptDescriptor[] descriptors = describe(parts);
    for (int i = 0; i < parts.length; i++) {
      if (descriptors[i].scriptEngine != null) {
        return descriptors[i].scriptEngine;
      }
    }
    return null;
  }

  /**
   * Describe script descriptor [ ].
   *
   * @param parts the parts
   * @return the script descriptor [ ]
   */
  static ScriptDescriptor[] describe(String... parts) {
    ScriptDescriptor[] descriptors = new ScriptDescriptor[parts.length];
    for (int i = 0; i < parts.length; i++) {
      descriptors[i] = new ScriptDescriptor(parts[i]);
    }
    return descriptors;
  }

  /**
   * Gets factory.
   *
   * @param engineId the engine id
   * @param required the required
   * @return the factory
   */
  static ScriptFactory getFactory(String engineId, boolean required) {
    ScriptFactory factory = factories.get(engineId);
    if (factory == null && required) {
      throw new ConfigurationError("Not a supported script engine: " + engineId);
    }
    return factory;
  }

  private static void parseConfigFile() {
    String className;
    try {
      factories = new HashMap<>();
//            org.graalvm.polyglot.Engine graalVMEngine = Engine.newBuilder().build();
//            CONFIG_LOGGER.info("supported script languages GraalVM : " + graalVMEngine.getLanguages().keySet());
      // read config file
      SCRIPTUTIL_LOGGER.debug("Initializing Script mapping from file " + SETUP_FILE_NAME);
      Map<String, String> properties = IOUtil.readProperties(SETUP_FILE_NAME);
      for (Map.Entry<String, String> entry : properties.entrySet()) {
        className = entry.getValue();
        ScriptFactory factory = (ScriptFactory) BeanUtil.newInstance(className);
        addFactory(entry.getKey(), factory);
      }
    } catch (FileNotFoundException e) {
      throw new ConfigurationError("Setup file not found: " + SETUP_FILE_NAME, e);
    } catch (IOException e) {
      throw new ConfigurationError("I/O Error while reading file: " + SETUP_FILE_NAME, e);
    }
  }

}
