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
package com.rapiddweller.format.script.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.Writer;
import java.io.IOException;

import com.rapiddweller.common.Context;
import com.rapiddweller.format.script.AbstractScript;
import com.rapiddweller.format.script.Script;
import com.rapiddweller.format.script.ScriptException;

/**
 * {@link Script} implementation that uses the FreeMarker engine.
 * 
 * Created: 31.01.2007 19:56:20
 * @author Volker Bergmann
 */
public class FreeMarkerScript extends AbstractScript {
    
    private final Template template;

    // constructors ----------------------------------------------------------------------------------------------------
    
    public FreeMarkerScript(String filename, Configuration cfg) throws IOException {
        this(cfg.getTemplate(filename));
    }

    public FreeMarkerScript(Template template) {
        this.template = template;
    }
    
    // Script interface implementation ---------------------------------------------------------------------------------

    @Override
    public void execute(Context context, Writer out) throws IOException, ScriptException {
        try {
            template.process(context, out);
        } catch (TemplateException e) {
            throw new ScriptException(e);
        }
    }
    
    // java.lang.Object overrides --------------------------------------------------------------------------------------
    
    @Override
    public String toString() {
        return template.toString();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return template.hashCode();
    }
    
    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        return template.equals(obj);
    }
    
}
