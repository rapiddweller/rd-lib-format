package com.rapiddweller.format.html;

import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * The type File anchor test.
 */
public class FileAnchorTest {
  /**
   * Test create anchor for new window.
   */
  @Test
  public void testCreateAnchorForNewWindow() {
    FileAnchor actualCreateAnchorForNewWindowResult = FileAnchor
        .createAnchorForNewWindow(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile(), "Label");
    String expectedToStringResult = String.join("", "<a href='",
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toString(), "' target='_blank'>Label</a>");
    assertEquals(expectedToStringResult, actualCreateAnchorForNewWindowResult.toString());
    assertEquals("Label", actualCreateAnchorForNewWindowResult.label);
    assertEquals("_blank", actualCreateAnchorForNewWindowResult.target);
  }

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
   * Test relative link from 2.
   */
  @Test
  public void testRelativeLinkFrom2() {
    FileAnchor createAnchorForNewWindowResult = FileAnchor
        .createAnchorForNewWindow(Paths.get(System.getProperty("log4jUl"), "test.txt").toFile(), "Label");
    String actualRelativeLinkFromResult = createAnchorForNewWindowResult
        .relativeLinkFrom(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile());
    assertEquals(String.join("", "<a href='..",
        Paths.get(System.getProperty("user.dir"), "null", "test.txt").toString(), "' target='_blank'>Label</a>"),
        actualRelativeLinkFromResult);
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

  /**
   * Test to string.
   */
  @Test
  public void testToString() {
    String actualToStringResult = FileAnchor
        .createAnchorForNewWindow(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile(), "Label")
        .toString();
    assertEquals(String.join("", "<a href='",
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toString(), "' target='_blank'>Label</a>"),
        actualToStringResult);
  }

  /**
   * Test to string 2.
   */
  @Test
  public void testToString2() {
    String actualToStringResult = (new FileAnchor(Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toFile(),
        "Label")).toString();
    assertEquals(String.join("", "<a href='",
        Paths.get(System.getProperty("java.io.tmpdir"), "test.txt").toUri().toString(), "'>Label</a>"),
        actualToStringResult);
  }
}

