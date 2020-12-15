/*
 * (c) Copyright 2009 by Volker Bergmann. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, is permitted under the terms of the
 * GNU General Public License.
 *
 * For redistributing this software or a derivative work under a license other
 * than the GPL-compatible Free Software License as defined by the Free
 * Software Foundation or approved by OSI, you must first obtain a commercial
 * license to this software product from Volker Bergmann.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * WITHOUT A WARRANTY OF ANY KIND. ALL EXPRESS OR IMPLIED CONDITIONS,
 * REPRESENTATIONS AND WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE
 * HEREBY EXCLUDED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
 
grammar Regex;

options {
	output=AST;
	backtrack=true;
	memoize=true;
}

@header {
	package com.rapiddweller.regex.antlr;
}

@lexer::header{ 
	package com.rapiddweller.regex.antlr;
}

@lexer::members {
	boolean numQuantifierMode = false;
	boolean classMode = false;
	
	@Override
	public Token nextToken() {
		while (true) {
			state.token = null;
			state.channel = Token.DEFAULT_CHANNEL;
			state.tokenStartCharIndex = input.index();
			state.tokenStartCharPositionInLine = input.getCharPositionInLine();
			state.tokenStartLine = input.getLine();
			state.text = null;
			if ( input.LA(1)==CharStream.EOF ) {
				return Token.EOF_TOKEN;
			}
			try {
				mTokens();
				if ( state.token==null ) {
					emit();
				}
				else if ( state.token==Token.SKIP_TOKEN ) {
					continue;
				}
				return state.token;
			}
			catch (RecognitionException re) {
				reportError(re);
				throw new RuntimeException(getClass().getSimpleName() + " error", re); // or throw Error
			}
		}
	}

}

@members {
protected void mismatch(IntStream input, int ttype, BitSet follow)
  throws RecognitionException
{
  throw new MismatchedTokenException(ttype, input);
}

public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow)
  throws RecognitionException
{
  throw e;
}
}

@rulecatch {
catch (RecognitionException e) {
  throw e;
}
}


/********************************************************************************************
                          Parser section
*********************************************************************************************/

expression 
	:	'^'!? choice '$'!?;

choice
	:	sequence ('|' sequence)+ -> ^(CHOICE sequence+)
	|	sequence
	;

sequence:	factor factor+ -> ^(SEQUENCE factor+)
	|	factor;

factor	:	atom quantifier -> ^(FACTOR atom quantifier)
	|	atom;

atom	:	singlechar
	|	group
	;

singlechar
	:	classchar^
	|	'-'^
	|	charclass
	|	PREDEFINEDCLASS^
	;
	
classchar
	:	ALPHANUM
	|	SPECIALCHARACTER
        |	ESCAPEDCHARACTER
	|	NONTYPEABLECHARACTER
	|	OCTALCHAR
	|	HEXCHAR
	|	CODEDCHAR
        ;

charclass
	:	'[' includedelements? ('^' excludedelements)? ']' -> ^(CLASS ^(INCL includedelements?) ^(EXCL excludedelements)?)
	;

includedelements
	:	classelement*
	;
	
excludedelements
	:	classelement+
	;
	
classelement
	:	classchar
	|	charrange
	|	PREDEFINEDCLASS
	;

charrange:	classchar '-' classchar -> ^(RANGE classchar+);

group	:	'(' expression ')' -> ^(GROUP expression);

quantifier
	:	SIMPLEQUANTIFIER
	|	'{' min=INT ',' max=INT '}' -> ^(QUANT $min $max)
	|	'{' INT ',' '}' -> ^(QUANT INT)
	|	'{' INT '}' -> ^(QUANT INT INT)
	;


/********************************************************************************************
                  Lexer section
*********************************************************************************************/

// imaginary nodes ---------------------------------------------------------

fragment
GROUP	:	;
fragment
CHOICE	:	;
fragment
SEQUENCE:	;
fragment
FACTOR	:	;
fragment
CLASS	:	;
fragment
RANGE	:	;
fragment
INCL	:	;
fragment
EXCL	:	;
fragment
QUANT	:	;



// regex tokens ----------------------------------------------------------------

PREDEFINEDCLASS
	:	'.'
	|	'\\d'
	|	'\\D'
	|	'\\s'
	|	'\\S'
	|	'\\w'
	|	'\\W'
	;

ALPHANUM:	LETTER
	|	{!numQuantifierMode}?=> DIGIT;

SPECIALCHARACTER
	: ' ' | '!' | '\'' | '"' | '%' | '&'
	| '/' | ':'  | ';' | '<' | '=' | '>'
	| '@' | '_'  | '`' | '~' | '#' 
	| {!numQuantifierMode}?=> ','
	| {classMode}?=> ('?' | '*' | '+')
	;

ESCAPEDCHARACTER
	: '\\$' | '\\&' | '\\(' | '\\)' | 
	| '\\,' | '\\-' | '\\.' | '\\[' | '\\]' | '\\^'
	| '\\{' | '\\}' | '\\\\' | '\\|'
	| '\\*' | '\\+' | '\\?'
	;

NONTYPEABLECHARACTER
	:	'\\r'
	|	'\\n'
	|	'\\t'
	|	'\\f'
	|	'\\a'
	|	'\\e'
	;

OCTALCHAR
	:	'\\0' OCTALDIGIT OCTALDIGIT OCTALDIGIT // TODO allow one- or two-digit octals
	;

HEXCHAR	:	'\\x' HEXDIGIT HEXDIGIT
	|	'\\u' HEXDIGIT HEXDIGIT HEXDIGIT HEXDIGIT
	;

CODEDCHAR:	'\\c' LETTER;

SIMPLEQUANTIFIER
	: {!classMode}?=> ('?' | '*' | '+');

INT	:	{numQuantifierMode}?=> DIGIT+;

LBRACE	:	'{' { numQuantifierMode = true; };

RBRACE	:	'}' { numQuantifierMode = false; };

LBRACKET:	'[' { classMode = true; };

RBRACKET:	']' { classMode = false; };



// lexer fragments -------------------------------------------------------------

fragment
LETTER	:	'A'..'Z' | 'a'..'z';
	
fragment
HEXDIGIT:	DIGIT | 'A'..'F' | 'a'..'f';

fragment
OCTALDIGIT:	'0'..'7';

fragment
DIGIT	:	'0'..'9';
