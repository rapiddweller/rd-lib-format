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
package com.rapiddweller.formats.script.graaljs;

import com.rapiddweller.commons.IOUtil;
import com.rapiddweller.formats.script.GraalScript;
import com.rapiddweller.formats.script.Script;
import com.rapiddweller.formats.script.ScriptFactory;
import org.graalvm.polyglot.Engine;

import java.io.IOException;

/**
 * Creates {@link GraalScript}s.
 * Created at 30.12.2020
 *
 * @author Alexander Kell
 * @since 1.1.0
 */

public class GraalJsScriptFactory implements ScriptFactory {


    private static final String LANGUAGE = "js";
    private final Engine jsEngine;

    public GraalJsScriptFactory() {
        this.jsEngine = Engine.newBuilder().build();
    }


    @Override
    public Script parseText(String text) {
        return parseText(text, jsEngine);
    }

    @Override
    public Script readFile(String uri) throws IOException {
        String text = IOUtil.getContentOfURI(uri);
        return parseText(text);
    }

    private static Script parseText(String text, Engine generalEngine) {
        if (!generalEngine.getLanguages().containsKey(LANGUAGE)) {
            throw new IllegalStateException(String.format("A language with id '%s' is not installed", LANGUAGE));
        } else {
            return new GraalScript(text, generalEngine, LANGUAGE);
        }

    }

}
