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
package com.rapiddweller.format;

import static org.junit.Assert.*;

import java.util.Map;

import com.rapiddweller.common.version.VersionInfo;
import org.junit.Test;

/**
 * Tests the {@link VersionInfo}.
 * Created: 23.03.2011 11:34:32
 * @since 0.5.8
 * @author Volker Bergmann
 */
public class VersionInfoTest {

	@Test
	public void testVersion() {
		VersionInfo version = getVersion();
		checkVersionNumber(version.getVersion());
		System.out.println(version);
	}

	@Test
	public void testVerifyDependencies() {
		VersionInfo version = getVersion();
		version.verifyDependencies();
	}
	
	private static void checkVersionNumber(String versionNumber) {
		assertFalse("version number is empty", versionNumber == null || versionNumber.length() == 0);
		assertFalse("version number was not substituted", versionNumber.startsWith("${"));
	}
	
	private static VersionInfo getVersion() {
		return VersionInfo.getInfo("format");
	}

}
