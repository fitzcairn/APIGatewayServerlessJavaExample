package com.stevezero.aws.api.apps.goaltender.id.impl;

import com.stevezero.aws.api.exceptions.InvalidApiResourceId;
import com.stevezero.aws.api.id.IdentityType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the GoalId class.
 */
public class GoalIdTest {
  protected final UserId testUserId = new UserId("1234", IdentityType.GOOGLE);

  @BeforeClass
  public static void setup() throws IOException {
  }

  @Test
  public void testToBase64() {
    GoalId test = new GoalId("abcd", testUserId);

    // {"u":"1234","g":"abcd"}
    String expected = "eyJ1IjoiZXlKMElqb2laeUlzSW1raU9pSXhNak0wSW4wPSIsImciOiJhYmNkIn0=";
    assertEquals(expected, test.toBase64String());
  }

  @Test
  public void testFromBase64Ok() throws InvalidApiResourceId {
    GoalId test = new GoalId("eyJ1IjoiZXlKMElqb2laeUlzSW1raU9pSXhNak0wSW4wPSIsImciOiJhYmNkIn0=");
    GoalId expected = new GoalId("abcd", testUserId);

    assertEquals(expected.toBase64String(), test.toBase64String());
  }

  @Test(expected = InvalidApiResourceId.class)
  public void testFromBase64Invalid() throws InvalidApiResourceId {
    GoalId test = new GoalId("eyJ1IjoiMTIzNCOiJhYmNkIn0=");
  }
}
