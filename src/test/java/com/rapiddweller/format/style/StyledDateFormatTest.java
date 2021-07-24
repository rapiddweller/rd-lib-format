package com.rapiddweller.format.style;

import com.rapiddweller.common.format.Alignment;
import com.rapiddweller.common.format.ConcurrentDateFormat;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * The type Styled date format test.
 */
public class StyledDateFormatTest {
  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    assertEquals(Alignment.RIGHT, (new StyledDateFormat()).getAlignment());
    assertEquals(Alignment.RIGHT, (new StyledDateFormat()).getAlignment());
  }

  /**
   * Test constructor 6.
   */
  @Test
  public void testConstructor6() {
    ConcurrentDateFormat concurrentDateFormat = new ConcurrentDateFormat("Pattern");
    assertEquals(Alignment.RIGHT, (new StyledDateFormat(concurrentDateFormat)).getAlignment());
    assertNull(concurrentDateFormat.getCalendar());
    assertNull(concurrentDateFormat.getNumberFormat());
  }

}

