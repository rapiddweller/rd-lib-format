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

import com.rapiddweller.commons.Assert;
import com.rapiddweller.commons.Context;
import com.rapiddweller.formats.script.Script;
import com.rapiddweller.formats.script.ScriptException;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Value;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * Provides {@link Script} functionality based on JSR 227: Scripting for the Java platform.
 * <p>
 * Created at 23.12.2008 07:19:54
 *
 * @author Volker Bergmann
 * @since 0.4.7
 */

public class GraalJsScript implements Script {

    private final String text;

    public GraalJsScript(String text, Engine jsEngine) {
        Assert.notEmpty(text, "text");
        Assert.notNull(jsEngine, "engine");
        this.text = text;
        org.graalvm.polyglot.Context jsContext = org.graalvm.polyglot.Context.create();
    }

    @Override
    public Object evaluate(Context context) throws ScriptException {
        org.graalvm.polyglot.Context polyglot = org.graalvm.polyglot.Context.create();
        for (Map.Entry<String, Object> entry : context.entrySet())
            polyglot.getBindings("js").putMember(entry.getKey(), entry.getValue());
        Value returnValue = polyglot.eval("js", text);
        return returnValue.fitsInInt() ? returnValue.asInt() :
                returnValue.fitsInLong() ? returnValue.asLong() :
                        returnValue.fitsInFloat() ? returnValue.asFloat() :
                                returnValue.asString();
    }

    @Override
    public void execute(Context context, Writer out) throws ScriptException, IOException {
        out.write(String.valueOf(evaluate(context)));
    }

    @Override
    public String toString() {
        return text;
    }
}
