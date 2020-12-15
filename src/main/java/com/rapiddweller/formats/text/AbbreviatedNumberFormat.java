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
package com.rapiddweller.formats.text;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.rapiddweller.commons.LocaleUtil;
import com.rapiddweller.commons.ParseUtil;
import com.rapiddweller.commons.StringUtil;

/**
 * Formats and parses numbers with abbreviations, e.g. 5Mio for 5,000,000.
 * 
 * Created: 16.05.2005 21:41:17
 */
public class AbbreviatedNumberFormat extends NumberFormat {

	private static final long serialVersionUID = -3938256314974549704L;
	
	private static final Map<Locale, List<AbbreviatedScale>> ABBREVIATIONS;

	static {
		ABBREVIATIONS = new HashMap<Locale, List<AbbreviatedScale>>();
		createAbbreviation("Mrd",  new Double(1E9), Locale.GERMAN);
        createAbbreviation("Mrd.", new Double(1E9), Locale.GERMAN);
        createAbbreviation("Mio",  new Double(1E6), Locale.GERMAN);
        createAbbreviation("Mio.", new Double(1E6), Locale.GERMAN);
        createAbbreviation("Tsd",  new Double(1E3), Locale.GERMAN);
        createAbbreviation("Tsd.", new Double(1E3), Locale.GERMAN);
        createAbbreviation("T",    new Double(1E3), Locale.GERMAN);
        
        createAbbreviation("Tsd", new Double(1E3),  Locale.ENGLISH); // Thousand
        createAbbreviation("T",   new Double(1E12), Locale.ENGLISH); // Trillion
        createAbbreviation("B",   new Double(1E9 ), Locale.ENGLISH); // Billion
        createAbbreviation("M",   new Double(1E6 ), Locale.ENGLISH); // Million
        createAbbreviation("K",   new Double(1E3 ), Locale.ENGLISH); // Kilo
        createAbbreviation("k",   new Double(1E3 ), Locale.ENGLISH); // kilo
	};
	
    private static void createAbbreviation(String abbreviation, Double factor, Locale locale) {
		List<AbbreviatedScale> localAbbrevs = ABBREVIATIONS.get(locale);
		if (localAbbrevs == null) {
			localAbbrevs = new ArrayList<AbbreviatedScale>();
			ABBREVIATIONS.put(locale, localAbbrevs);
		}
		AbbreviatedScale entry = new AbbreviatedScale(abbreviation, factor);
		localAbbrevs.add(entry);
	}

    private String defaultScaleId;
    private double defaultScale;
    private List<AbbreviatedScale> abbreviations;
    private NumberFormat snf;

    public AbbreviatedNumberFormat() {
        this(1);
    }

	public AbbreviatedNumberFormat(double scale) {
        this(scale, Locale.getDefault());
    }

    public AbbreviatedNumberFormat(Locale locale) {
        this(1, locale);
    }

    public AbbreviatedNumberFormat(double scale, Locale locale) {
    	abbreviations = abbreviationsForLocale(locale);
        defaultScale = scale;
        defaultScaleId = "";
        for (int i = 0; i < abbreviations.size(); i++) {
        	AbbreviatedScale abbScale = abbreviations.get(i);
            if (abbScale.factor == scale) {
                defaultScaleId = (String) abbScale.code;
                break;
            }
        }
        snf = NumberFormat.getInstance(locale);
        snf.setMinimumFractionDigits(2);
        snf.setMaximumFractionDigits(2);
        snf.setGroupingUsed(true);
    }

    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
        if (!StringUtil.isEmpty(defaultScaleId))
            return formatFixed(number, toAppendTo, pos);
        else
            return formatFree(number, toAppendTo, pos);
    }

    public StringBuffer formatFixed(double number, StringBuffer toAppendTo, FieldPosition pos) {
        snf.format(number / defaultScale, toAppendTo, pos);
        if (!StringUtil.isEmpty(defaultScaleId)) {
            toAppendTo.append(' ');
            toAppendTo.append(defaultScaleId);
        }
        return toAppendTo;
    }

    private StringBuffer formatFree(double number, StringBuffer toAppendTo, FieldPosition pos) {
        String selectedPrefix = "";
        for (int i = 0; i < abbreviations.size(); i++) {
            double scale = ((Double) abbreviations.get(i).factor).doubleValue();
            if (number >= scale) {
                selectedPrefix = (String) abbreviations.get(i).code;
                number /= scale;
                snf.format(number, toAppendTo, pos);
                toAppendTo.append(' ');
                toAppendTo.append(selectedPrefix);
                return toAppendTo;
            }
        }
        return snf.format(number, toAppendTo, pos);
    }

    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
        return format((double) number, toAppendTo, pos);
    }

    @Override
    public Number parse(String source, ParsePosition pos) {
        Number value = snf.parse(StringUtil.trim(source), pos);
        int start = ParseUtil.nextNonWhitespaceIndex(source, pos.getIndex());
        if (start == -1)
            return value;
        for (int i = 0; i < abbreviations.size(); i++) {
            String prefix = (String) abbreviations.get(i).code;
            if (source.substring(start).startsWith(prefix))  {
                value = new Double(value.doubleValue() * ((Double) abbreviations.get(i).factor).doubleValue());
                pos.setIndex(start + prefix.length());
                break;
            }
        }
        return value;
    }
    
	private static List<AbbreviatedScale> abbreviationsForLocale(Locale locale) {
		Locale tmp = locale;
		List<AbbreviatedScale> abbs;
		do {
			abbs = ABBREVIATIONS.get(tmp);
			if (abbs != null)
				return abbs;
			tmp = LocaleUtil.parent(tmp);
		} while (tmp != null);
		throw new UnsupportedOperationException("Locale not supported: " + locale);
	}

    private static class AbbreviatedScale {
    	
    	public String code;
    	public double factor;
    	
		public AbbreviatedScale(String code, double factor) {
			this.code = code;
			this.factor = factor;
		}
    }
    
}
