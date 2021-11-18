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

import com.rapiddweller.common.CharSet;
import com.rapiddweller.common.CollectionUtil;
import com.rapiddweller.common.StringUtil;
import com.rapiddweller.common.exception.SyntaxError;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Tests the {@link RegexParser}.
 * Created: 18.08.2006 21:56:01
 * @author Volker Bergmann
 * @since 0.1
 */
public class RegexParserTest {

  private static final Logger logger = LoggerFactory.getLogger(RegexParserTest.class);

  @Test
  public void testEmpty() throws Exception {
    check(null, null, 0, 0);
    check("", new RegexString(""), 0, 0);
  }

  @Test
  public void testSpecialCharacters() throws Exception {
    checkChar("\\+", '+');
    checkChar("\\-", '-');
    checkChar("\\*", '*');
    checkChar("\\?", '?');
    checkChar("\\\\", '\\');
    checkChar("\\.", '.');
    checkChar("\\,", ',');
    checkChar("\\?", '?');
    checkChar("\\&", '&');
    checkChar("\\^", '^');
    checkChar("\\$", '$');
    checkChar("\\t", '\t');
    checkChar("\\n", '\n');
    checkChar("\\r", '\r');
    checkChar("\\f", '\u000C');
    checkChar("\\a", '\u0007');
    checkChar("\\e", '\u001B');
  }

  @Test
  public void testHexCharacter() throws Exception {
    checkChar("\\xfe", (char) 0xfe);
    checkChar("\\ufedc", (char) 0xfedc);
  }

  @Test
  public void testOctalCharacter() throws Exception {
    checkChar("\\0123", (char) 0123);
  }

  @Test
  public void testCodedCharacter() throws Exception {
    checkChar("\\cB", (char) 1);
  }

  @Test
  public void testCustomClasses() throws Exception {
    check("[a-c]", new CustomCharClass(CollectionUtil.toList(new CharRange("a-c", 'a', 'c'))), 1, 1);
    check("[a-cA-C]", new CustomCharClass(
        CollectionUtil.toList(new CharRange("a-c", 'a', 'c'), new CharRange("A-C", 'A', 'C'))), 1, 1);
    check("[^\\w]", new CustomCharClass(
        CollectionUtil.toList(new SimpleCharSet(".", new CharSet().addAnyCharacters().getSet())),
        CollectionUtil.toList(new SimpleCharSet("\\w", new CharSet().addWordChars().getSet()))
    ), 1, 1);
  }

  @Test(expected = SyntaxError.class)
  public void testInvalidCustomClass() {
    new RegexParser().parseRegex("[a-f");
  }

  @Test
  public void testPredefClasses() throws Exception {
    check(".", new SimpleCharSet(".", new CharSet().addAnyCharacters().getSet()), 1, 1);
    check("\\d", new SimpleCharSet("\\d", new CharSet().addDigits().getSet()), 1, 1);
    check("\\s", new SimpleCharSet("\\s", new CharSet().addWhitespaces().getSet()), 1, 1);
    check("\\w", new SimpleCharSet("\\w", new CharSet().addWordChars().getSet()), 1, 1);
  }

  @Test(expected = SyntaxError.class)
  public void testInvalidPredefClass() {
    new RegexParser().parseRegex("\\X");
  }

  @Test
  public void testQuantifiers() throws Exception {
    check("a", new RegexChar('a'), 1, 1);
    check("a?", new Factor(new RegexChar('a'), 0, 1), 0, 1);
    check("a*", new Factor(new RegexChar('a'), 0, null), 0, null);
    check("a+", new Factor(new RegexChar('a'), 1, null), 1, null);

    check("a{3}", new Factor(new RegexChar('a'), 3, 3), 3, 3);
    check("a{3,}", new Factor(new RegexChar('a'), 3, null), 3, null);
    check("a{3,5}", new Factor(new RegexChar('a'), 3, 5), 3, 5);
  }

  @Test(expected = SyntaxError.class)
  public void testInvalidQuantifier() throws Exception {
    new RegexParser().parseRegex("a{,4}");
  }

  @Test
  public void testClassAndQuantifierSequences() throws Exception {
    check("\\w+\\d+", new Sequence(
        new Factor(new SimpleCharSet("\\w", new CharSet().addWordChars().getSet()), 1, null),
        new Factor(new SimpleCharSet("\\d", new CharSet().addDigits().getSet()), 1, null)
    ), 2, null);
    check("[a-c][A-C]", new Sequence(
        new CustomCharClass(CollectionUtil.toList(new CharRange("a-c", 'a', 'c'))),
        new CustomCharClass(CollectionUtil.toList(new CharRange("A-C", 'A', 'C')))
    ), 2, 2);

    RegexCharClass CS_DIGIT = new SimpleCharSet("\\d", new CharSet().addDigits().getSet());
    CharRange CS_0_9 = new CharRange("0-9", '0', '9');
    CustomCharClass CC_0_9 = new CustomCharClass(CollectionUtil.toList(CS_0_9));

    RegexCharClass CS_POS_DIGIT = new CharRange("1-9", '1', '9');
    CustomCharClass CC_POS_DIGIT = new CustomCharClass(CollectionUtil.toList(CS_POS_DIGIT));

    check("\\+[1-9]\\d{1,2}/\\d+/\\d+", new Sequence(
        new RegexChar('+'),
        CC_POS_DIGIT,
        new Factor(CS_DIGIT, 1, 2),
        new RegexChar('/'),
        new Factor(CS_DIGIT, 1, null),
        new RegexChar('/'),
        new Factor(CS_DIGIT, 1, null)
    ), 7, null);

    check("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}", new Sequence(
        new Factor(CC_0_9, 1, 3),
        new RegexChar('.'),
        new Factor(CC_0_9, 1, 3),
        new RegexChar('.'),
        new Factor(CC_0_9, 1, 3),
        new RegexChar('.'),
        new Factor(CC_0_9, 1, 3)
    ), 7, 15);
  }

  @Test
  public void testGroups() throws Exception {
    check("(a)", new Group(new RegexChar('a')), 1, 1);

    check("(ab)", new Group(new Sequence(new RegexChar('a'), new RegexChar('b'))), 2, 2);

    check("(a)*", new Factor(new Group(new RegexChar('a')), 0, null), 0, null);

    check("(a?b+)*",
        new Factor(new Group(new Sequence(
            new Factor(new RegexChar('a'), 0, 1),
            new Factor(new RegexChar('b'), 1, null)
        )),
            0, null), 0, null);

    check("(a{1}b{2,3}){4,5}",
        new Factor(new Group(
            new Sequence(
                new Factor(new RegexChar('a'), 1, 1),
                new Factor(new RegexChar('b'), 2, 3)
            )),
            4, 5
        ), 12, 20
    );
  }

  @Test
  public void testChoices() throws Exception {
    check("(a|b)", new Group(new Choice(new RegexChar('a'), new RegexChar('b'))), 1, 1);
    check("(a?|b+)*",
        new Factor(
            new Group(new Choice(
                new Factor(new RegexChar('a'), 0, 1),
                new Factor(new RegexChar('b'), 1, null)
            )),
            0, null
        ), 0, null);
    check("(a{1,2}|b)",
        new Group(new Choice(
            new Factor(new RegexChar('a'), 1, 2),
            new RegexChar('b'))
        ), 1, 2);
  }

  @Test
  public void testRecursion() throws Exception {
    check("(a{1,2}|b){1,3}",
        new Factor(
            new Group(new Choice(
                new Factor(new RegexChar('a'), 1, 2),
                new RegexChar('b'))),
            1, 3
        ), 1, 6);
  }

  private static void checkChar(String pattern, char expectedChar) throws Exception {
    check(pattern, new RegexChar(expectedChar), 1, 1);
  }

  private static void check(String pattern, Object expectedPart, int expMinLength, Integer expMaxLength) throws Exception {
    logger.debug("checking " + pattern);
    RegexPart result = new RegexParser().parseRegex(pattern);
    logger.debug("parsed as: " + StringUtil.normalize(String.valueOf(result)));
    if (pattern == null) {
      assertEquals(expectedPart, result);
    } else if (result == null) {
      assertNull(expectedPart);
    } else {
      assertEquals(expectedPart, result);
    }
    if (pattern != null) {
      assertEquals("Wrong minLength.", expMinLength, result.minLength());
      assertEquals("Wrong maxLength.", expMaxLength, result.maxLength());
    }
  }

}
