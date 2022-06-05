/* (c) Copyright 2022 by Volker Bergmann. All rights reserved. */

package com.rapiddweller.format.script;

/**
 * Holds the parsed date of a Benerator script spec like "{{ftl:Hello ${name}}}".<br/><br/>
 * Created: 05.06.2022 08:16:24
 * @author Volker Bergmann
 * @since 1.1.5
 */
public class ScriptSpec {

	private final String engineId;
	private final String text;
	private final boolean dynamic;

	public ScriptSpec(String engineId, String text, boolean dynamic) {
		this.engineId = engineId;
		this.text = text;
		this.dynamic = dynamic;
	}

	public String getEngineId() {
		return engineId;
	}

	public String getText() {
		return text;
	}

	public boolean isDynamic() {
		return dynamic;
	}

	public boolean isScript() {
		return (engineId != null);
	}

	public boolean isTemplateScript() {
		ScriptFactory factory = ScriptUtil.getFactory(engineId, true);
		return factory.isTemplateEngine();
	}

}
