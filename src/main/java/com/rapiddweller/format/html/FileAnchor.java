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

import com.rapiddweller.common.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * {@link Anchor} implementation which relates to a {@link File}
 * and provides the resolution of relative paths.
 * Created: 13.06.2011 12:29:36
 *
 * @author Volker Bergmann
 * @since 0.5.8
 */
public class FileAnchor extends Anchor {

  /**
   * The File.
   */
  public final File file;

  /**
   * Instantiates a new File anchor.
   *
   * @param file  the file
   * @param label the label
   */
  public FileAnchor(File file, String label) {
    super(label);
    this.file = file;
  }

  /**
   * Instantiates a new File anchor.
   *
   * @param file   the file
   * @param label  the label
   * @param target the target
   */
  public FileAnchor(File file, String label, String target) {
    super(label, target);
    this.file = file;
  }

  /**
   * Create anchor for new window file anchor.
   *
   * @param file  the file
   * @param label the label
   * @return the file anchor
   */
  public static FileAnchor createAnchorForNewWindow(File file, String label) {
    return new FileAnchor(file, label, "_blank");
  }

  /**
   * Relative link from string.
   *
   * @param referer the referer
   * @return the string
   */
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
