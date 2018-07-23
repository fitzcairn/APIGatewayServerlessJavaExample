package com.stevezero.aws.api.apps.goaltender.id.impl;

import com.stevezero.aws.api.exceptions.InvalidApiResourceId;
import com.stevezero.aws.api.id.IdentityType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the UserId class.
 */
public class UserIdTest {
  @BeforeClass
  public static void setup() throws IOException {
  }

  @Test
  public void testToBase64() {
    UserId test = new UserId("1234", IdentityType.GOOGLE);
    String expected = "eyJ0IjoiZyIsImkiOiIxMjM0In0="; // Encoded version of output JSON.

    assertEquals(expected, test.toBase64String());
  }

  @Test
  public void testUserIdFromBase64StringOk() throws InvalidApiResourceId {
    // {"t": "g","i": "1234"}
    String testIdString = "eyJ0IjoiZyIsImkiOiIxMjM0In0=";

    UserId result = new UserId(testIdString);
    assertEquals("1234", result.getId());
    assertEquals(IdentityType.GOOGLE, result.getType());
  }

  @Test(expected = InvalidApiResourceId.class)
  public void testUserIdFromBase64StringInvalidBase64() throws InvalidApiResourceId {
    UserId result = new UserId("///");
  }

  @Test(expected = InvalidApiResourceId.class)
  public void testUserIdFromBase64StringInvalidJson() throws InvalidApiResourceId {
    // {t: "g",i: "1234"} <-- missing quotes on keys, invalid
    String testIdString = "e3Q6ICJnIixpOiAiMTIzNCJ9";
    UserId result = new UserId(testIdString);
  }

  @Test(expected = InvalidApiResourceId.class)
  public void testUserIdFromBase64StringInvalidIdentity() throws InvalidApiResourceId {
    // {"t": "XX","i": "1234"}
    String testIdString = "eyJ0IjogIlhYIiwiaSI6ICIxMjM0In0=";
    UserId result = new UserId(testIdString);
  }

}