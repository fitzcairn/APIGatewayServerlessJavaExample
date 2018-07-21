package com.stevezero.aws.goaltender.common;

import com.stevezero.aws.goaltender.api.exceptions.InvalidUserIdException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the UserIdTest class.
 */
public class UserIdTest {
  @BeforeClass
  public static void setup() throws IOException {
  }

  @Test
  public void testToIdString() throws InvalidUserIdException {
    UserId userId = new UserId("1234", IdentityType.GOOGLE);

    // {"t": "g","i": "1234"}
    String expected = "eyJ0IjoiZyIsImkiOiIxMjM0In0";
    assertEquals(expected, userId.toEncoded());
  }

  @Test
  public void testFromIdStringOk() throws InvalidUserIdException {
    // {"t": "g","i": "1234"}
    String testIdString = "eyJ0IjogImciLCJpIjogIjEyMzQifQ==";

    UserId result = UserId.fromEncoded(testIdString);
    assertEquals("1234", result.getId());
    assertEquals(IdentityType.GOOGLE, result.getType());
  }

  @Test(expected = InvalidUserIdException.class)
  public void testFromIdStringInvalidBase64() throws InvalidUserIdException {
    UserId.fromEncoded("///");
  }

  @Test(expected = InvalidUserIdException.class)
  public void testFromIdStringInvalidJson() throws InvalidUserIdException {
    // {t: "g",i: "1234"} <-- missing quotes on keys, invalid
    String testIdString = "e3Q6ICJnIixpOiAiMTIzNCJ9";
    UserId.fromEncoded("testIdString");
  }

  @Test(expected = InvalidUserIdException.class)
  public void testFromIdStringInvalidIdentity() throws InvalidUserIdException {
    // {"t": "XX","i": "1234"}
    String testIdString = "eyJ0IjogIlhYIiwiaSI6ICIxMjM0In0=";
    UserId.fromEncoded(testIdString);
  }

  @Test
  public void testToString() throws InvalidUserIdException {
    String json = "{\"t\": \"g\",\"i\": \"1234\"}";
    String testIdString = "eyJ0IjogImciLCJpIjogIjEyMzQifQ==";
    UserId parsed = UserId.fromEncoded(testIdString);

    try {
      JSONObject userIdJson = (JSONObject)new JSONParser().parse(json);
      assertEquals(userIdJson.toJSONString(), parsed.toString());
    }
    catch (ParseException e) {
      assert(false);
    }
  }
}