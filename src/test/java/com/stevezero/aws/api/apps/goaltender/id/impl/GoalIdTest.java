package com.stevezero.aws.api.apps.goaltender.id.impl;

import com.stevezero.aws.api.exceptions.InvalidResourceIdException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the GoalId class.
 */
public class GoalIdTest {
  @BeforeClass
  public static void setup() throws IOException {
  }

  @Test
  public void testToBase64() {
    GoalId test = new GoalId("abcd", "1234");

    // {"u":"1234","g":"abcd"}
    String expected = "eyJ1IjoiMTIzNCIsImciOiJhYmNkIn0=";
    assertEquals(expected, test.toBase64String());
  }

  @Test
  public void testFromBase64Ok() throws InvalidResourceIdException {
    GoalId test = new GoalId("eyJ1IjoiMTIzNCIsImciOiJhYmNkIn0=");
    GoalId expected = new GoalId("abcd", "1234");

    assertEquals(expected.toBase64String(), test.toBase64String());
  }

  @Test(expected = InvalidResourceIdException.class)
  public void testFromBase64Invalid() throws InvalidResourceIdException {
    GoalId test = new GoalId("eyJ1IjoiMTIzNCOiJhYmNkIn0=");
  }
}
