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

package com.rapiddweller.format.regex;

import com.rapiddweller.common.Assert;
import com.rapiddweller.common.CharSet;
import com.rapiddweller.common.CollectionUtil;
import com.rapiddweller.common.LocaleUtil;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.exception.ExceptionFactory;
import com.rapiddweller.common.exception.SyntaxError;
import com.rapiddweller.format.regex.antlr.RegexLexer;
import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.ParserRuleReturnScope;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Parses a regular expression into an object model.<br/><br/>
 * Created: 18.08.2006 19:10:42
 * @author Volker Bergmann
 * @since 0.1
 */
public class RegexParser {

  private static final Logger logger = LoggerFactory.getLogger(RegexParser.class);

  private final Locale locale;

  // constructors ----------------------------------------------------------------------------------------------------

  public RegexParser() {
    this(LocaleUtil.getFallbackLocale());
  }

  public RegexParser(Locale locale) {
    this.locale = locale;
  }

  // interface -------------------------------------------------------------------------------------------------------

  public RegexPart parseRegex(String pattern) throws SyntaxError {
    if (pattern == null) {
      return null;
    }
    if (pattern.length() == 0) {
      return new RegexString("");
    }
    try {
      RegexLexer lex = new RegexLexer(new ANTLRReaderStream(new StringReader(pattern)));
      CommonTokenStream tokens = new CommonTokenStream(lex);
      com.rapiddweller.format.regex.antlr.RegexParser parser = new com.rapiddweller.format.regex.antlr.RegexParser(tokens);
      com.rapiddweller.format.regex.antlr.RegexParser.expression_return r = parser.expression();
      checkForSyntaxErrors(pattern, "regex", parser, r);
      CommonTree tree = (CommonTree) r.getTree();
      logParseResult(pattern, tree);
      return convertRegexPart(tree);
    } catch (RuntimeException e) {
      if (e.getCause() instanceof RecognitionException) {
        throw mapToSyntaxError((RecognitionException) e.getCause(), pattern);
      } else {
        throw e;
      }
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().internalError("Encountered illegal state in regex parsing", e);
    } catch (RecognitionException e) {
      throw mapToSyntaxError(e, pattern);
    }
  }

  private void logParseResult(String pattern, CommonTree tree) {
    if (logger.isDebugEnabled()) {
      logger.debug("parsed {} to {}", pattern, tree.toStringTree());
    }
  }

  public RegexCharClass parseSingleChar(String pattern) throws SyntaxError {
    if (pattern == null) {
      return null;
    }
    if (pattern.length() == 0) {
      throw ExceptionFactory.getInstance().illegalArgument("Not a character class pattern: '" + pattern + "'");
    }
    try {
      RegexLexer lex = new RegexLexer(new ANTLRReaderStream(new StringReader(pattern)));
      CommonTokenStream tokens = new CommonTokenStream(lex);
      com.rapiddweller.format.regex.antlr.RegexParser parser = new com.rapiddweller.format.regex.antlr.RegexParser(tokens);
      com.rapiddweller.format.regex.antlr.RegexParser.singlechar_return r = parser.singlechar();
      if (parser.getNumberOfSyntaxErrors() > 0) {
        throw ExceptionFactory.getInstance().syntaxErrorForText(pattern, "Illegal regex");
      }
      if (r != null) {
        CommonTree tree = (CommonTree) r.getTree();
        logParseResult(pattern, tree);
        RegexPart regex = convertRegexPart(tree);
        if (!(regex instanceof RegexCharClass)) {
          throw ExceptionFactory.getInstance().illegalArgument("Not a character class pattern: '" + pattern + "'");
        }
        return (RegexCharClass) regex;
      } else {
        return null;
      }
    } catch (RuntimeException e) {
      if (e.getCause() instanceof RecognitionException) {
        throw mapToSyntaxError((RecognitionException) e.getCause(), pattern);
      } else {
        throw e;
      }
    } catch (IOException e) {
      throw ExceptionFactory.getInstance().syntaxErrorForText(pattern, "Encountered illegal state in regex parsing");
    } catch (RecognitionException e) {
      throw mapToSyntaxError(e, pattern);
    }
  }

    /*
	public static CharSet toCharSet(RegexCharSet o) {
		if (o instanceof CharSet)
			return (CharSet) o;
		else if (o instanceof Character)
			return new CharSet(CollectionUtil.toSet((Character) o));
		else if (o instanceof CustomCharClass)
			return ((CustomCharClass) o).getCharSet();
		else
			throw ExceptionFactory.getInstance().illegalArgument("Not a supported character regex type: " + o.getClass());
	}

    public static Set<Character> toSet(RegexCharSet regex) {
	    return toCharSet(regex).getSet();
    }
     */

  private static SyntaxError mapToSyntaxError(RecognitionException e, String parsedText) {
    return ExceptionFactory.getInstance().syntaxErrorForText(
        "Error parsing regular expression: " + e.getMessage(), parsedText, e.line, e.charPositionInLine);
  }

  private RegexPart convertRegexPart(CommonTree node) throws SyntaxError {
    if (node == null) {
      return null;
    }
    if (node.getToken() == null) {
      return new RegexString("");
    }
    switch (node.getType()) {
      case RegexLexer.CHOICE:
        return convertChoice(node);
      case RegexLexer.GROUP:
        return convertGroup(node);
      case RegexLexer.SEQUENCE:
        return convertSequence(node);
      case RegexLexer.FACTOR:
        return convertFactor(node);
      case RegexLexer.PREDEFINEDCLASS:
        return convertPredefClass(node);
      case RegexLexer.CLASS:
        return convertClass(node);
      case RegexLexer.RANGE:
        return convertRange(node);
      case RegexLexer.SPECIALCHARACTER:
        return convertAlphanum(node);
      case RegexLexer.ESCAPEDCHARACTER:
        return convertEscaped(node);
      case RegexLexer.NONTYPEABLECHARACTER:
        return convertNonTypeable(node);
      case RegexLexer.OCTALCHAR:
        return convertOctal(node);
      case RegexLexer.HEXCHAR:
        return convertHexChar(node);
      case RegexLexer.CODEDCHAR:
        return convertCodedChar(node);
      case RegexLexer.ALPHANUM:
        return convertAlphanum(node);
      case RegexLexer.T__34: // local bug fix since I do not want to touch the ANTLR parser
        return convertAlphanum(node);
      default: {
        throw ExceptionFactory.getInstance().syntaxErrorForText("Not a supported token type: " + node.getToken(), node.toString(),
            node.getLine(), node.getCharPositionInLine());
      }
    }
  }

  @SuppressWarnings("unchecked")
  private Group convertGroup(CommonTree node) throws SyntaxError {
    List<CommonTree> childNodes = node.getChildren();
    Assert.equals(1, childNodes.size(), "Group is expected to have exactly one child node");
    return new Group(convertRegexPart(childNodes.get(0)));
  }

  @SuppressWarnings("unchecked")
  private Choice convertChoice(CommonTree node) throws SyntaxError {
    List<CommonTree> childNodes = node.getChildren();
    List<RegexPart> childPatterns = new ArrayList<>();
    if (childNodes != null) {
      for (CommonTree childNode : childNodes) {
        childPatterns.add(convertRegexPart(childNode));
      }
    }
    return new Choice(CollectionUtil.toArray(childPatterns, RegexPart.class));
  }

  @SuppressWarnings("unchecked")
  private Sequence convertSequence(CommonTree tree) throws SyntaxError {
    List<CommonTree> childNodes = tree.getChildren();
    List<RegexPart> children = new ArrayList<>();
    if (childNodes != null) {
      for (CommonTree child : childNodes) {
        children.add(convertRegexPart(child));
      }
    }
    return new Sequence(CollectionUtil.toArray(children, RegexPart.class));
  }

  @SuppressWarnings("unchecked")
  private Factor convertFactor(CommonTree tree) throws SyntaxError {
    List<CommonTree> children = tree.getChildren();
    RegexPart subPattern = convertRegexPart(children.get(0));
    Quantifier quantifier;
    if (children.size() > 1) {
      quantifier = convertQuantifier(children.get(1));
    } else {
      quantifier = new Quantifier(1, 1);
    }
    return new Factor(subPattern, quantifier);
  }

  @SuppressWarnings("unchecked")
  private CustomCharClass convertClass(CommonTree node) throws SyntaxError {
    CustomCharClass result = new CustomCharClass();
    List<CommonTree> childNodes = node.getChildren();
    if (childNodes != null) {
      for (CommonTree child : childNodes) {
        if (child.getType() == RegexLexer.INCL) {
          List<CommonTree> gdChildren = child.getChildren();
          if (gdChildren != null) {
            for (CommonTree gdChild : gdChildren) {
              result.addInclusion((RegexCharClass) convertRegexPart(gdChild));
            }
          }
        } else {
          List<CommonTree> gdChildren = child.getChildren();
          if (gdChildren != null) {
            for (CommonTree gdChild : gdChildren) {
              result.addExclusions((RegexCharClass) convertRegexPart(gdChild));
            }
          }
        }
      }
    }
    if (!result.hasInclusions()) {
      result.addInclusion(new SimpleCharSet(".", new CharSet().addAnyCharacters())); // for e.g. [^\d]
    }
    return result;
  }

  private static RegexChar convertAlphanum(CommonTree node) {
    return new RegexChar(node.getText().charAt(0));
  }

  private static RegexChar convertNonTypeable(CommonTree node) throws SyntaxError {
    switch (node.getText().charAt(1)) {
      case 'r':
        return new RegexChar('\r'); // the carriage-return character
      case 'n':
        return new RegexChar('\n'); // the newline (line feed) character
      case 't':
        return new RegexChar('\t'); // the tab character
      case 'f':
        return new RegexChar('\f'); // the form-feed character
      case 'a':
        return new RegexChar('\u0007'); // the alert (bell) character
      case 'e':
        return new RegexChar('\u001B'); // the escape character
      default:
        throw ExceptionFactory.getInstance().syntaxErrorForText("invalid non-typeable char", node.getText(),
            node.getLine(), node.getCharPositionInLine());
    }
  }

  private static RegexChar convertOctal(CommonTree node) {
    return new RegexChar((char) Integer.parseInt(node.getText().substring(2), 8));
  }

  private static RegexChar convertHexChar(CommonTree node) {
    return new RegexChar((char) Integer.parseInt(node.getText().substring(2), 16));
  }

  private static RegexChar convertCodedChar(CommonTree node) {
    return new RegexChar((char) (Character.toUpperCase(node.getText().charAt(2)) - 'A'));
  }

  private RegexCharClass convertPredefClass(CommonTree node) throws SyntaxError {
    String text = node.getText();
    if (".".equals(text)) {
      return new SimpleCharSet(".", CharSet.getAnyCharacters());
    }
    char charClass = text.charAt(1);
    switch (charClass) {
      case 'd':
        return new SimpleCharSet("\\d", CharSet.getDigits());
      case 'D':
        return new SimpleCharSet("\\D", CharSet.getNonDigits());
      case 's':
        return new SimpleCharSet("\\s", CharSet.getWhitespaces());
      case 'S':
        return new SimpleCharSet("\\S", CharSet.getNonWhitespaces());
      case 'w':
        return new SimpleCharSet("\\w", CharSet.getWordChars(locale));
      case 'W':
        return new SimpleCharSet("\\W", CharSet.getNonWordChars());
      default:
        throw ExceptionFactory.getInstance().syntaxErrorForText(
            "Unsupported character class", text, node.getLine(), node.getCharPositionInLine());
    }
  }

  private static RegexChar convertEscaped(CommonTree node) {
    String text = node.getText();
    return new RegexChar(text.charAt(1));
  }

  @SuppressWarnings("unchecked")
  private RegexCharClass convertRange(CommonTree node) throws SyntaxError {
    List<CommonTree> children = node.getChildren();
    CommonTree fromNode = children.get(0);
    char from = ((RegexChar) convertRegexPart(fromNode)).getChar();
    CommonTree toNode = children.get(1);
    char to = ((RegexChar) convertRegexPart(toNode)).getChar();
    return new CharRange(fromNode.getText() + "-" + toNode.getText(), from, to);
  }

  private static Quantifier convertQuantifier(CommonTree node) throws SyntaxError {
    switch (node.getType()) {
      case RegexLexer.SIMPLEQUANTIFIER:
        return convertSimpleQuantifier(node);
      case RegexLexer.QUANT:
        return convertExplicitQuantifier(node);
      default:
        throw ExceptionFactory.getInstance().syntaxErrorForText("Error parsing quantifier", node.getText(),
            node.getLine(), node.getCharPositionInLine());
    }
  }

  @SuppressWarnings("unchecked")
  private static Quantifier convertExplicitQuantifier(CommonTree tree) {
    int min;
    Integer max = null;
    List<CommonTree> children = tree.getChildren();
    min = convertInt(children.get(0));
    if (children.size() > 1) {
      max = convertInt(children.get(1));
    }
    return new Quantifier(min, max);
  }

  private static Integer convertInt(CommonTree node) {
    Assert.equals(RegexLexer.INT, node.getType(), "node is not an integer");
    return Integer.parseInt(node.getText());
  }

  private static Quantifier convertSimpleQuantifier(CommonTree node) throws SyntaxError {
    Assert.equals(RegexLexer.SIMPLEQUANTIFIER, node.getType(), "node is not an simple quantifier");
    char letter = node.getText().charAt(0);
    switch (letter) {
      case '?':
        return new Quantifier(0, 1);
      case '*':
        return new Quantifier(0, null);
      case '+':
        return new Quantifier(1, null);
      default:
        throw ExceptionFactory.getInstance().syntaxErrorForText("Error parsing simple quantifier", node.getText(),
            node.getLine(), node.getCharPositionInLine());
    }
  }

  private static void checkForSyntaxErrors(
      String text, String type, com.rapiddweller.format.regex.antlr.RegexParser parser, ParserRuleReturnScope r) {
    if (parser.getNumberOfSyntaxErrors() > 0) {
      throw ExceptionFactory.getInstance().syntaxErrorForText("Illegal " + type, text, -1, -1);
    }
    CommonToken stop = (CommonToken) r.stop;
    if (stop.getStopIndex() < StringUtil.trimRight(text).length() - 1) {
      if (stop.getStopIndex() == 0) {
        throw ExceptionFactory.getInstance().syntaxErrorForText(text, "Syntax error after " + stop.getText());
      } else {
        throw ExceptionFactory.getInstance().syntaxErrorForText(text, "Syntax error");
      }
    }
  }

}
