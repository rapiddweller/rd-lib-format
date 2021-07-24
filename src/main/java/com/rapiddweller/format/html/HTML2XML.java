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
import com.rapiddweller.common.Encodings;
import com.rapiddweller.common.IOUtil;
import com.rapiddweller.common.ParseUtil;
import com.rapiddweller.common.StringCharacterIterator;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.SystemInfo;
import com.rapiddweller.common.xml.XMLUtil;
import com.rapiddweller.format.html.parser.DefaultHTMLTokenizer;
import com.rapiddweller.format.html.parser.HTMLTokenizer;
import com.rapiddweller.format.html.util.HTMLUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Provides utility methods for converting HTML to XML.
 * <p>
 * Created: 25.01.2007 17:10:37
 *
 * @author Volker Bergmann
 */
public class HTML2XML {

  private static final Logger LOGGER = LogManager.getLogger(HTML2XML.class);
  private static final Set<String> COMMON_CODES = CollectionUtil.toSet("lt", "gt", "amp");

  /**
   * Convert string.
   *
   * @param html the html
   * @return the string
   * @throws ParseException the parse exception
   */
  public static String convert(String html) throws ParseException {
    Reader reader = new StringReader(html);
    StringWriter writer = new StringWriter();
    try {
      ConversionContext context = new ConversionContext(reader, writer, "UTF-8");
      convert(context);
      return writer.getBuffer().toString();
    } catch (IOException e) {
      throw new RuntimeException(e); // this is not supposed to happen
    } finally {
      IOUtil.close(reader);
    }
  }

  /**
   * Convert.
   *
   * @param reader   the reader
   * @param out      the out
   * @param encoding the encoding
   * @throws ParseException               the parse exception
   * @throws UnsupportedEncodingException the unsupported encoding exception
   */
  public static void convert(Reader reader, OutputStream out, String encoding)
      throws ParseException, UnsupportedEncodingException {
    Writer writer = new OutputStreamWriter(out, encoding);
    try {
      ConversionContext context = new ConversionContext(reader, writer, encoding);
      convert(context);
    } catch (IOException e) {
      throw new RuntimeException(e); // this is not supposed to happen
    } finally {
      IOUtil.close(reader);
      IOUtil.close(writer);
    }
  }

  /**
   * Parse html as xml document.
   *
   * @param url            the url
   * @param namespaceAware the namespace aware
   * @return the document
   * @throws IOException    the io exception
   * @throws ParseException the parse exception
   */
  public static Document parseHtmlAsXml(String url, boolean namespaceAware)
      throws IOException, ParseException {
    String htmlText = downloadHtml(url);
    return parseHtmlTextAsXml(htmlText, namespaceAware);
  }

  /**
   * Download html string.
   *
   * @param url the url
   * @return the string
   * @throws IOException the io exception
   */
  public static String downloadHtml(String url) throws IOException {
    byte[] bytes = IOUtil.getBinaryContentOfUri(url);
    String encoding = getEncoding(bytes);
    if (StringUtil.isEmpty(encoding)) {
      encoding = Encodings.ISO_8859_1;
    }
    return new String(bytes, encoding);
  }

  private static String getEncoding(byte[] bytes) throws UnsupportedEncodingException {
    String tmp = new String(bytes, StandardCharsets.ISO_8859_1);
    int startIndex = tmp.indexOf("charset=");
    if (startIndex < 0) {
      return null;
    }
    startIndex += "charset=".length();
    StringCharacterIterator it = new StringCharacterIterator(tmp, startIndex);
    it.skipWhitespace();
    StringBuilder builder = new StringBuilder();
    int c;
    while ((c = it.next()) >= 0 && ParseUtil.isNMAfterStartChar((char) c)) {
      builder.append((char) c);
    }
    return builder.toString();
  }

  /**
   * Parse html text as xml document.
   *
   * @param html           the html
   * @param namespaceAware the namespace aware
   * @return the document
   * @throws ParseException the parse exception
   * @throws IOException    the io exception
   */
  public static Document parseHtmlTextAsXml(String html, boolean namespaceAware) throws ParseException, IOException {
    String xml = convert(html);
    ByteArrayInputStream in = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
    return XMLUtil.parse(in, namespaceAware, null, null, null, null);
  }

  // private helpers -------------------------------------------------------------------------------------------------

  private static void convert(ConversionContext context) throws IOException, ParseException {
    // TODO use XML serializer
    int token;
    while ((token = context.tokenizer.nextToken()) != HTMLTokenizer.END) {
      switch (token) {
        case HTMLTokenizer.START_TAG:
        case HTMLTokenizer.CLOSED_TAG:
          ensureXmlHeader(context);
          // ignore scripts
          if ("script".equalsIgnoreCase(context.tokenizer.name())) // TODO test script handling
          {
            continue;
          }
          String lcTagName = context.tokenizer.name().toLowerCase();
          if (!"html".equals(lcTagName) && !context.rootCreated) {
            ensureRootElement(context);
          } else if ("html".equals(lcTagName) && context.rootCreated) {
            // ignore html element if there already was one
            LOGGER.warn("Malformed HTML document: misplaced <HTML> element");
            break;
          } else {
            if (context.path.size() > 0) {
              String lastTagName = context.path.peek();
              if (HTMLUtil.isEmptyTag(lastTagName) && !context.tokenizer.name().equals(lastTagName)) {
                context.writer.write("</" + lastTagName + '>');
                context.path.pop();
              }
            }
          }
          context.rootCreated = true;
          if (token == HTMLTokenizer.CLOSED_TAG) {
            writeEmptyTag(context.writer, context.tokenizer);
          } else {
            writeStartTag(context.writer, context.tokenizer);
            context.path.push(context.tokenizer.name());
          }
          break;
        case (HTMLTokenizer.END_TAG):
          if ("script".equalsIgnoreCase(context.tokenizer.name())) {
            continue;
          }
          boolean done = false;
          if (contains(context.path, context.tokenizer.name())) {
            do {
              String pathTagName = context.path.pop();
              context.writer.write("</" + pathTagName + '>');
              if (pathTagName.equals(context.tokenizer.name())) {
                done = true;
              }
            } while (!done);
          }
          if ("html".equalsIgnoreCase(context.tokenizer.name())) {
            return;
          }
          break;
        case HTMLTokenizer.TEXT:
          ensureXmlHeader(context);
          String text = context.tokenizer.text();
          if (text != null && text.trim().length() > 0) {
            ensureRootElement(context);
          }
          writeText(context.writer, text);
          break;
        case HTMLTokenizer.COMMENT:
          ensureRootElement(context);
          String comment = context.tokenizer.text();
          int s = comment.indexOf("<!--") + "<!--".length();
          int e = comment.lastIndexOf("-->");
          comment = comment.substring(s, e);
          while (comment.contains("--")) {
            comment = comment.replace("--", " -");
          }
          if (comment.endsWith("-")) {
            comment = comment.substring(0, comment.length() - 1) + " ";
          }
          comment = "<!--" + comment + "-->";
          writeXml(context.writer, comment);
          break;
        case HTMLTokenizer.DOCUMENT_TYPE:
          // leave out doc type
          break;
        case HTMLTokenizer.PROCESSING_INSTRUCTION:
          String piText = context.tokenizer.text();
          writeXml(context.writer, piText);
          if (piText.startsWith("<?xml")) {
            context.xmlHeaderCreated = true;
          }
          break;
        case HTMLTokenizer.SCRIPT:
          // ignore this
          break;
        default:
          throw new UnsupportedOperationException("Unsupported token type: " + token);
      }
    }
    while (context.path.size() > 0) {
      String tagName = context.path.pop();
      context.writer.write("</" + tagName + '>');
    }
  }

  private static void ensureXmlHeader(ConversionContext context) throws IOException {
    if (!context.xmlHeaderCreated) {
      context.writer.write("<?xml " +
          "version=\"1.0\" " +
          "encoding=\"" + context.encoding + "\"?>" +
          SystemInfo.getLineSeparator());
      context.xmlHeaderCreated = true;
    }
  }

  private static void ensureRootElement(ConversionContext context) throws IOException {
    ensureXmlHeader(context);
    // ensure that there is an html root tag
    if (!context.rootCreated && !"html".equals(context.tokenizer.name())) {
      writeStartTag(context.writer, "html");
      context.path.push("html");
      context.rootCreated = true;
    }
  }

  private static boolean contains(Stack<String> path, String name) {
    for (String tagName : path) {
      if (tagName.equals(name)) {
        return true;
      }
    }
    return false;
  }

  private static void writeEmptyTag(Writer writer, HTMLTokenizer tokenizer) throws IOException {
    writer.write('<' + tokenizer.name());
    writeAttributes(writer, tokenizer);
    writer.write("/>");
  }

  private static void writeStartTag(Writer writer, HTMLTokenizer tokenizer) throws IOException {
    writer.write('<' + tokenizer.name());
    writeAttributes(writer, tokenizer);
    writer.write('>');
  }

  private static void writeStartTag(Writer writer, String name) throws IOException {
    writer.write('<' + name + '>');
  }

  private static void writeAttributes(Writer writer, HTMLTokenizer tokenizer) throws IOException {
    for (Map.Entry<String, String> entry : tokenizer.attributes().entrySet()) {
      String value = entry.getValue();
      char quote = '"';
      if (value == null) {
        value = "";
      } else if (value.contains("\"")) {
        quote = '\'';
      }
      writer.write(' ');
      writer.write(entry.getKey());
      writer.write('=');
      writer.write(quote);
      writeText(writer, value);
      writer.write(quote);
    }
  }

  private static void writeXml(Writer writer, String s) throws IOException {
    s = resolveEntities(writer, s);
    writer.write(s);
  }

  private static void writeText(Writer writer, String s) throws IOException {
    s = s.replace("<", "&lt;");
    s = s.replace(">", "&gt;");
    s = resolveEntities(writer, s);
    writer.write(s);
  }

  private static String resolveEntities(Writer writer, String s) throws IOException {
    int i;
    while ((i = s.indexOf('&')) >= 0) {
      HtmlEntity entity = HtmlEntity.getEntity(s, i);
      if (entity != null) {
        writer.write(s.substring(0, i + 1));
        if (COMMON_CODES.contains(entity.htmlCode)) {
          writer.write(entity.htmlCode + ';');
        } else {
          writer.write("#" + entity.xmlCode + ";");
        }
        s = s.substring(s.indexOf(';', i) + 1);
      } else {
        writer.write(s.substring(0, i));
        writer.write("&amp;");
        s = s.substring(i + 1);
      }
    }
    return s;
  }

  private static class ConversionContext {

    /**
     * The Encoding.
     */
    public String encoding;
    /**
     * The Writer.
     */
    Writer writer;
    /**
     * The Tokenizer.
     */
    HTMLTokenizer tokenizer;
    /**
     * The Path.
     */
    Stack<String> path;
    /**
     * The Xml header created.
     */
    boolean xmlHeaderCreated;
    /**
     * The Root created.
     */
    boolean rootCreated;

    /**
     * Instantiates a new Conversion context.
     *
     * @param reader   the reader
     * @param writer   the writer
     * @param encoding the encoding
     */
    ConversionContext(Reader reader, Writer writer, String encoding) {
      this.tokenizer = new DefaultHTMLTokenizer(reader);
      this.path = new Stack<>();
      this.xmlHeaderCreated = false;
      this.rootCreated = false;
      this.writer = writer;
      this.encoding = encoding;
    }
  }

}
