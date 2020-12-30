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

import java.util.HashMap;

import com.rapiddweller.format.html.model.HtmlComponent;

/**
 * Entity enumeration.
 * 
 * Created: 26.01.2007 10:04:35
 * @author Volker Bergmann
 */
public class HtmlEntity extends HtmlComponent {
	
	private static final HashMap<String, HtmlEntity> HTML_CODES = new HashMap<String, HtmlEntity>();
	private static final HashMap<Integer, HtmlEntity> NUMBERS = new HashMap<Integer, HtmlEntity>();
	
	public static final HtmlEntity NBSP = new HtmlEntity("nbsp",   160);
	
	private static final HtmlEntity[] ENTITIES = {
	    NBSP,
	    new HtmlEntity("iexcl",  161),
	    new HtmlEntity("cent",   162),
	    new HtmlEntity("pound",  163),
	    new HtmlEntity("curren", 164),
	    new HtmlEntity("yen",    165),
	    new HtmlEntity("brvbar", 166),
	    new HtmlEntity("sect",   167),
	    new HtmlEntity("uml",    168),
	    new HtmlEntity("copy",   169),
	    new HtmlEntity("ordf",   170),
	    new HtmlEntity("laquo",  171),
	    new HtmlEntity("not",    172),
	    new HtmlEntity("shy",    173),
	    new HtmlEntity("reg",    174),
	    new HtmlEntity("macr",   175),
	    new HtmlEntity("deg",    176),
	    new HtmlEntity("plusmn", 177),
	    new HtmlEntity("sup2",   178),
	    new HtmlEntity("sup3",   179),
	    new HtmlEntity("acute",  180),
	    new HtmlEntity("micro",  181),
	    new HtmlEntity("para",   182),
	    new HtmlEntity("middot", 183),
	    new HtmlEntity("cedil",  184),
	    new HtmlEntity("sup1",   185),
	    new HtmlEntity("ordm",   186),
	    new HtmlEntity("raquo",  187),
	    new HtmlEntity("frac14", 188),
	    new HtmlEntity("frac12", 189),
	    new HtmlEntity("frac34", 190),
	    new HtmlEntity("iquest", 191),
	    new HtmlEntity("Agrave", 192),
	    new HtmlEntity("Aacute", 193),
	    new HtmlEntity("Acirc",  194),
	    new HtmlEntity("Atilde", 195),
	    new HtmlEntity("Auml",   196),
	    new HtmlEntity("Aring",  197),
	    new HtmlEntity("AElig",  198),
	    new HtmlEntity("Ccedil", 199),
	    new HtmlEntity("Egrave", 200),
	    new HtmlEntity("Eacute", 201),
	    new HtmlEntity("Ecirc",  202),
	    new HtmlEntity("Euml",   203),
	    new HtmlEntity("Igrave", 204),
	    new HtmlEntity("Iacute", 205),
	    new HtmlEntity("Icirc",  206),
	    new HtmlEntity("Iuml",   207),
	    new HtmlEntity("ETH",    208),
	    new HtmlEntity("Ntilde", 209),
	    new HtmlEntity("Ograve", 210),
	    new HtmlEntity("Oacute", 211),
	    new HtmlEntity("Ocirc",  212),
	    new HtmlEntity("Otilde", 213),
	    new HtmlEntity("Ouml",   214),
	    new HtmlEntity("times",  215),
	    new HtmlEntity("Oslash", 216),
	    new HtmlEntity("Ugrave", 217),
	    new HtmlEntity("Uacute", 218),
	    new HtmlEntity("Ucirc",  219),
	    new HtmlEntity("Uuml",   220),
	    new HtmlEntity("Yacute", 221),
	    new HtmlEntity("THORN",  222),
	    new HtmlEntity("szlig",  223),
	    new HtmlEntity("agrave", 224),
	    new HtmlEntity("aacute", 225),
	    new HtmlEntity("acirc",  226),
	    new HtmlEntity("atilde", 227),
	    new HtmlEntity("auml",   228),
	    new HtmlEntity("aring",  229),
	    new HtmlEntity("aelig",  230),
	    new HtmlEntity("ccedil", 231),
	    new HtmlEntity("egrave", 232),
	    new HtmlEntity("eacute", 233),
	    new HtmlEntity("ecirc",  234),
	    new HtmlEntity("euml",   235),
	    new HtmlEntity("igrave", 236),
	    new HtmlEntity("iacute", 237),
	    new HtmlEntity("icirc",  238),
	    new HtmlEntity("iuml",   239),
	    new HtmlEntity("eth",    240),
	    new HtmlEntity("ntilde", 241),
	    new HtmlEntity("ograve", 242),
	    new HtmlEntity("oacute", 243),
	    new HtmlEntity("ocirc",  244),
	    new HtmlEntity("otilde", 245),
	    new HtmlEntity("ouml",   246),
	    new HtmlEntity("divide", 247),
	    new HtmlEntity("oslash", 248),
	    new HtmlEntity("ugrave", 249),
	    new HtmlEntity("uacute", 250),
	    new HtmlEntity("ucirc",  251),
	    new HtmlEntity("uuml",   252),
	    new HtmlEntity("yacute", 253),
	    new HtmlEntity("thorn",  254),
	    new HtmlEntity("yuml",   255),
	    new HtmlEntity("fnof",     402),
	    new HtmlEntity("Alpha",    913),
	    new HtmlEntity("Beta",     914),
	    new HtmlEntity("Gamma",    915),
	    new HtmlEntity("Delta",    916),
	    new HtmlEntity("Epsilon",  917),
	    new HtmlEntity("Zeta",     918),
	    new HtmlEntity("Eta",      919),
	    new HtmlEntity("Theta",    920),
	    new HtmlEntity("Iota",     921),
	    new HtmlEntity("Kappa",    922),
	    new HtmlEntity("Lambda",   923),
	    new HtmlEntity("Mu",       924),
	    new HtmlEntity("Nu",       925),
	    new HtmlEntity("Xi",       926),
	    new HtmlEntity("Omicron",  927),
	    new HtmlEntity("Pi",       928),
	    new HtmlEntity("Rho",      929),
	    new HtmlEntity("Sigma",    931),
	    new HtmlEntity("Tau",      932),
	    new HtmlEntity("Upsilon",  933),
	    new HtmlEntity("Phi",      934),
	    new HtmlEntity("Chi",      935),
	    new HtmlEntity("Psi",      936),
	    new HtmlEntity("Omega",    937),
	    new HtmlEntity("alpha",    945),
	    new HtmlEntity("beta",     946),
	    new HtmlEntity("gamma",    947),
	    new HtmlEntity("delta",    948),
	    new HtmlEntity("epsilon",  949),
	    new HtmlEntity("zeta",     950),
	    new HtmlEntity("eta",      951),
	    new HtmlEntity("theta",    952),
	    new HtmlEntity("iota",     953),
	    new HtmlEntity("kappa",    954),
	    new HtmlEntity("lambda",   955),
	    new HtmlEntity("mu",       956),
	    new HtmlEntity("nu",       957),
	    new HtmlEntity("xi",       958),
	    new HtmlEntity("omicron",  959),
	    new HtmlEntity("pi",       960),
	    new HtmlEntity("rho",      961),
	    new HtmlEntity("sigmaf",   962),
	    new HtmlEntity("sigma",    963),
	    new HtmlEntity("tau",      964),
	    new HtmlEntity("upsilon",  965),
	    new HtmlEntity("phi",      966),
	    new HtmlEntity("chi",      967),
	    new HtmlEntity("psi",      968),
	    new HtmlEntity("omega",    969),
	    new HtmlEntity("thetasym", 977),
	    new HtmlEntity("upsih",    978),
	    new HtmlEntity("piv",      982),
	    new HtmlEntity("bull",     8226),
	    new HtmlEntity("hellip",   8230),
	    new HtmlEntity("prime",    8242),
	    new HtmlEntity("Prime",    8243),
	    new HtmlEntity("oline",    8254),
	    new HtmlEntity("frasl",    8260),
	    new HtmlEntity("weierp",   8472),
	    new HtmlEntity("image",    8465),
	    new HtmlEntity("real",     8476),
	    new HtmlEntity("trade",    8482),
	    new HtmlEntity("alefsym",  8501),
	    new HtmlEntity("larr",     8592),
	    new HtmlEntity("uarr",     8593),
	    new HtmlEntity("rarr",     8594),
	    new HtmlEntity("darr",     8595),
	    new HtmlEntity("harr",     8596),
	    new HtmlEntity("crarr",    8629),
	    new HtmlEntity("lArr",     8656),
	    new HtmlEntity("uArr",     8657),
	    new HtmlEntity("rArr",     8658),
	    new HtmlEntity("dArr",     8659),
	    new HtmlEntity("hArr",     8660),
	    new HtmlEntity("forall",   8704),
	    new HtmlEntity("part",     8706),
	    new HtmlEntity("exist",    8707),
	    new HtmlEntity("empty",    8709),
	    new HtmlEntity("nabla",    8711),
	    new HtmlEntity("isin",     8712),
	    new HtmlEntity("notin",    8713),
	    new HtmlEntity("ni",       8715),
	    new HtmlEntity("prod",     8719),
	    new HtmlEntity("sum",      8721),
	    new HtmlEntity("minus",    8722),
	    new HtmlEntity("lowast",   8727),
	    new HtmlEntity("radic",    8730),
	    new HtmlEntity("prop",     8733),
	    new HtmlEntity("infin",    8734),
	    new HtmlEntity("ang",      8736),
	    new HtmlEntity("and",      8743),
	    new HtmlEntity("or",       8744),
	    new HtmlEntity("cap",      8745),
	    new HtmlEntity("cup",      8746),
	    new HtmlEntity("int",      8747),
	    new HtmlEntity("there4",   8756),
	    new HtmlEntity("sim",      8764),
	    new HtmlEntity("cong",     8773),
	    new HtmlEntity("asymp",    8776),
	    new HtmlEntity("ne",       8800),
	    new HtmlEntity("equiv",    8801),
	    new HtmlEntity("le",       8804),
	    new HtmlEntity("ge",       8805),
	    new HtmlEntity("sub",      8834),
	    new HtmlEntity("sup",      8835),
	    new HtmlEntity("nsub",     8836),
	    new HtmlEntity("sube",     8838),
	    new HtmlEntity("supe",     8839),
	    new HtmlEntity("oplus",    8853),
	    new HtmlEntity("otimes",   8855),
	    new HtmlEntity("perp",     8869),
	    new HtmlEntity("sdot",     8901),
	    new HtmlEntity("lceil",    8968),
	    new HtmlEntity("rceil",    8969),
	    new HtmlEntity("lfloor",   8970),
	    new HtmlEntity("rfloor",   8971),
	    new HtmlEntity("lang",     9001),
	    new HtmlEntity("rang",     9002),
	    new HtmlEntity("loz",      9674),
	    new HtmlEntity("spades",   9824),
	    new HtmlEntity("clubs",    9827),
	    new HtmlEntity("hearts",   9829),
	    new HtmlEntity("diams",    9830),
	    new HtmlEntity("quot",    34),
	    new HtmlEntity("amp",     38),
	    new HtmlEntity("lt",      60),
	    new HtmlEntity("gt",      62),
	    new HtmlEntity("OElig",   338),
	    new HtmlEntity("oelig",   339),
	    new HtmlEntity("Scaron",  352),
	    new HtmlEntity("scaron",  353),
	    new HtmlEntity("Yuml",    376),
	    new HtmlEntity("circ",    710),
	    new HtmlEntity("tilde",   732),
	    new HtmlEntity("ensp",    8194),
	    new HtmlEntity("emsp",    8195),
	    new HtmlEntity("thinsp",  8201),
	    new HtmlEntity("zwnj",    8204),
	    new HtmlEntity("zwj",     8205),
	    new HtmlEntity("lrm",     8206),
	    new HtmlEntity("rlm",     8207),
	    new HtmlEntity("ndash",   8211),
	    new HtmlEntity("mdash",   8212),
	    new HtmlEntity("lsquo",   8216),
	    new HtmlEntity("rsquo",   8217),
	    new HtmlEntity("sbquo",   8218),
	    new HtmlEntity("ldquo",   8220),
	    new HtmlEntity("rdquo",   8221),
	    new HtmlEntity("bdquo",   8222),
	    new HtmlEntity("dagger",  8224),
	    new HtmlEntity("Dagger",  8225),
	    new HtmlEntity("permil",  8240),
	    new HtmlEntity("lsaquo",  8249),
	    new HtmlEntity("rsaquo",  8250),
	    new HtmlEntity("euro",    8364)
    };

    public final String htmlCode;
    public final int xmlCode;
    public final char character;

    public HtmlEntity(String htmlCode, int xmlCode) {
        this.htmlCode = htmlCode;
        this.xmlCode = xmlCode;
        this.character = (char) xmlCode;
        HTML_CODES.put(htmlCode, this);
        NUMBERS.put(xmlCode, this);
    }

    public static HtmlEntity[] getAllInstances() {
        return ENTITIES;
    }

    public static HtmlEntity getEntity(String s, int position) {
    	int semIndex = s.indexOf(';', position);
    	if (semIndex < 0)
    		return null;
    	boolean num = (s.charAt(position + 1) == '#');
    	boolean hex = (num && s.charAt(position + 2) == 'x');
    	if (hex)
    		return findByNumber(Integer.parseInt(s.substring(position + 3, semIndex), 16));
    	else if (num)
    		return findByNumber(Integer.parseInt(s.substring(position + 2, semIndex)));
    	else
    		return findByHtmlCode(s.substring(position + 1, semIndex));
    }
    
    private static HtmlEntity findByHtmlCode(String code) {
	    return HTML_CODES.get(code);
    }

	private static HtmlEntity findByNumber(int number) {
	    return NUMBERS.get(number);
    }

	@Override
    public String toString() {
        return '&' + htmlCode + ';';
    }
}
