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

import com.rapiddweller.common.Context;
import com.rapiddweller.common.DocumentWriter;
import com.rapiddweller.common.context.DefaultContext;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * A DocumentWriter that uses {@link Script}s for rendering head, body parts and footer.
 * Created: 07.06.2007 11:32:09
 *
 * @param <E> the type of objects to write
 * @author Volker Bergmann
 */
public class ScriptedDocumentWriter<E> implements DocumentWriter<E> {

  private final Writer out;
  private final Map<String, Object> vars;

  private Script headerScript;
  private final Script bodyPartScript;
  private Script footerScript;

  private boolean writeHeader;

  /**
   * Instantiates a new Scripted document writer.
   *
   * @param out               the out
   * @param headerScriptUrl   the header script url
   * @param bodyPartScriptUrl the body part script url
   * @param footerScriptUrl   the footer script url
   * @throws IOException the io exception
   */
  public ScriptedDocumentWriter(Writer out, String headerScriptUrl, String bodyPartScriptUrl, String footerScriptUrl)
      throws IOException {
    this(out,
        (headerScriptUrl != null ? ScriptUtil.readFile(headerScriptUrl) : null),
        (bodyPartScriptUrl != null ? ScriptUtil.readFile(bodyPartScriptUrl) : null),
        (footerScriptUrl != null ? ScriptUtil.readFile(footerScriptUrl) : null)
    );
  }

  /**
   * Instantiates a new Scripted document writer.
   *
   * @param out            the out
   * @param headerScript   the header script
   * @param bodyPartScript the body part script
   * @param footerScript   the footer script
   */
  public ScriptedDocumentWriter(Writer out, Script headerScript, Script bodyPartScript, Script footerScript) {
    this.out = out;
    this.headerScript = headerScript;
    this.bodyPartScript = bodyPartScript;
    this.footerScript = footerScript;
    this.vars = new HashMap<>();
    this.writeHeader = true;
  }

  /**
   * Gets header script.
   *
   * @return the header script
   */
  public Script getHeaderScript() {
    return headerScript;
  }

  /**
   * Sets header script.
   *
   * @param headerScript the header script
   */
  public void setHeaderScript(Script headerScript) {
    this.headerScript = headerScript;
  }

  /**
   * Gets footer script.
   *
   * @return the footer script
   */
  public Script getFooterScript() {
    return footerScript;
  }

  /**
   * Sets footer script.
   *
   * @param footerScript the footer script
   */
  public void setFooterScript(Script footerScript) {
    this.footerScript = footerScript;
  }

  /**
   * Sets write header.
   *
   * @param writeHeader the write header
   */
  public void setWriteHeader(boolean writeHeader) {
    this.writeHeader = writeHeader;
  }

  // Script interface implementation ---------------------------------------------------------------------------------

  @Override
  public void setVariable(String name, Object value) {
    vars.put(name, value);
  }

  @Override
  public void writeElement(E part) throws IOException {
    if (writeHeader) {
      writeHeader();
      writeHeader = false;
    }
    if (bodyPartScript != null) {
      Context context = new DefaultContext();
      context.set("var", vars);
      context.set("part", part);
      bodyPartScript.execute(context, out);
    }
  }

  @Override
  public void close() throws IOException {
    writeFooter();
    out.close();
  }

  // helpers ---------------------------------------------------------------------------------------------------------

  /**
   * Write header.
   *
   * @throws IOException the io exception
   */
  protected void writeHeader() throws IOException {
    if (headerScript != null) {
      Context context = new DefaultContext();
      context.set("var", vars);
      headerScript.execute(context, out);
    }
  }

  /**
   * Write footer.
   *
   * @throws IOException the io exception
   */
  protected void writeFooter() throws IOException {
    if (footerScript != null) {
      Context context = new DefaultContext();
      context.set("var", vars);
      footerScript.execute(context, out);
    }
  }
}
