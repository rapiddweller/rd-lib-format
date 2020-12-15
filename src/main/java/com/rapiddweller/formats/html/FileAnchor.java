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
package com.rapiddweller.formats.html;

import java.io.File;
import java.io.IOException;

import com.rapiddweller.commons.FileUtil;

/**
 * {@link Anchor} implementation which relates to a {@link File} 
 * and provides the resolution of relative paths.
 * Created: 13.06.2011 12:29:36
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class FileAnchor extends Anchor {

	public final File file;
	
	public FileAnchor(File file, String label) {
		super(label);
		this.file = file;
	}
	
	public FileAnchor(File file, String label, String target) {
		super(label, target);
		this.file = file;
	}
	
	public static FileAnchor createAnchorForNewWindow(File file, String label) {
		return new FileAnchor(file, label, "_blank");
	}
	
	public String relativeLinkFrom(File referer) {
		return linkForURL(FileUtil.relativePath(referer, file, '/'));
	}
	
	@Override
	public String toString() {
		try {
			return linkForURL("file://" + file.getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
			return linkForURL("file://" + file.getAbsolutePath());
		}
	}

	private String linkForURL(String url) {
		return "<a href='" + url + "'" + (target != null ? " target='" + target + "'" : "") + ">" + label + "</a>";
	}
	
}
