package com.stevezero.aws.api.apps.goaltender.id.impl;

import com.stevezero.aws.api.exceptions.InvalidResourceIdException;
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
  public void testToIdString() throws InvalidResourceIdException {
    UserId userId = new UserId("1234", IdentityType.GOOGLE);

    // {"t": "g","i": "1234"}
    String expected = "eyJ0IjoiZyIsImkiOiIxMjM0In0=";
    assertEquals(expected, userId.toEncoded());
  }

  @Test
  public void testFromIdStringOk() throws InvalidResourceIdException {
    // {"t": "g","i": "1234"}
    String testIdString = "eyJ0IjoiZyIsImkiOiIxMjM0In0=";

    UserId result = UserId.fromEncoded(testIdString);
    assertEquals("1234", result.getId());
    assertEquals(IdentityType.GOOGLE, result.getType());
  }

  @Test(expected = InvalidResourceIdException.class)
  public void testFromIdStringInvalidBase64() throws InvalidResourceIdException {
    UserId.fromEncoded("///");
  }

  @Test(expected = InvalidResourceIdException.class)
  public void testFromIdStringInvalidJson() throws InvalidResourceIdException {
    // {t: "g",i: "1234"} <-- missing quotes on keys, invalid
    String testIdString = "e3Q6ICJnIixpOiAiMTIzNCJ9";
    UserId.fromEncoded(testIdString);
  }

  @Test(expected = InvalidResourceIdException.class)
  public void testFromIdStringInvalidIdentity() throws InvalidResourceIdException {
    // {"t": "XX","i": "1234"}
    String testIdString = "eyJ0IjogIlhYIiwiaSI6ICIxMjM0In0=";
    UserId.fromEncoded(testIdString);
  }

  @Test
  public void testToEncoded() {
    UserId test = new UserId("1234", IdentityType.GOOGLE);
    String expected = "eyJ0IjoiZyIsImkiOiIxMjM0In0="; // Encoded version of output JSON.

    assertEquals(expected, test.toEncoded());
  }
}