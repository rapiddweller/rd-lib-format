package com.rapiddweller.format.html;

import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * The type File anchor test.
 */
public class FileAnchorTest {

  /**
   * Test relative link from.
   */
  @Test
  public void testRelativeLinkFrom() {
    FileAnchor createAnchorForNewWindowResult = FileAnchor
        .createAnchorForNewWindow(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile(), "Label");
    assertEquals("<a href='test.txt' target='_blank'>Label</a>", createAnchorForNewWindowResult
        .relativeLinkFrom(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }

  /**
   * Test relative link from 3.
   */
  @Test
  public void testRelativeLinkFrom3() {
    FileAnchor fileAnchor = new FileAnchor(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile(),
        "Label");
    assertEquals("<a href='test.txt'>Label</a>",
        fileAnchor.relativeLinkFrom(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile()));
  }
}

