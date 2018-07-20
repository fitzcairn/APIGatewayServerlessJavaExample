package com.stevezero.aws.goaltender.api.user.data;

import com.stevezero.aws.goaltender.exceptions.InvalidAPIResource;
import com.stevezero.aws.goaltender.exceptions.InvalidUserIdException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the GetRequest class.
 */
public class UserIdTest {
  @BeforeClass
  public static void setup() throws IOException {
  }

  @Test
  public void testFromPathOk() throws InvalidAPIResource, InvalidUserIdException {
    // {"t": "g","i": "1234"}
    String testIdString = "eyJ0IjogImciLCJpIjogIjEyMzQifQ==";
    String path = "/user/" + testIdString;

    UserId result = UserId.fromPathString(path);
    assertEquals("1234", result.getId());
    assertEquals(UserId.IdentityType.GOOGLE, result.getType());
  }

  @Test(expected = InvalidAPIResource.class)
  public void testFromPathInvalidResource() throws InvalidAPIResource, InvalidUserIdException {
    // {"t": "g","i": "1234"}
    String testIdString = "eyJ0IjogImciLCJpIjogIjEyMzQifQ==";
    String path = "/notauser/" + testIdString;
    UserId result = UserId.fromPathString(path);
  }

  @Test(expected = InvalidAPIResource.class)
  public void testFromPathInvalidPath() throws InvalidAPIResource, InvalidUserIdException {
    // {"t": "g","i": "1234"}
    String testIdString = "eyJ0IjogImciLCJpIjogIjEyMzQifQ==";
    String path = "/oops/user/" + testIdString;
    UserId result = UserId.fromPathString(path);
  }

  @Test
  public void testFromIdStringOk() throws InvalidUserIdException {
    // {"t": "g","i": "1234"}
    String testIdString = "eyJ0IjogImciLCJpIjogIjEyMzQifQ==";

    UserId result = UserId.fromIdString(testIdString);
    assertEquals("1234", result.getId());
    assertEquals(UserId.IdentityType.GOOGLE, result.getType());
  }

  @Test(expected = InvalidUserIdException.class)
  public void testFromIdStringInvalidBase64() throws InvalidUserIdException {
    UserId.fromIdString("///");
  }

  @Test(expected = InvalidUserIdException.class)
  public void testFromIdStringInvalidJson() throws InvalidUserIdException {
    // {t: "g",i: "1234"} <-- missing quotes on keys, invalid
    String testIdString = "e3Q6ICJnIixpOiAiMTIzNCJ9";
    UserId.fromIdString("testIdString");
  }

  @Test(expected = InvalidUserIdException.class)
  public void testFromIdStringInvalidIdentity() throws InvalidUserIdException {
    // {"t": "XX","i": "1234"}
    String testIdString = "eyJ0IjogIlhYIiwiaSI6ICIxMjM0In0=";
    UserId.fromIdString(testIdString);
  }

  @Test
  public void testToString() throws InvalidUserIdException {
    String json = "{\"t\": \"g\",\"i\": \"1234\"}";
    String testIdString = "eyJ0IjogImciLCJpIjogIjEyMzQifQ==";
    UserId parsed = UserId.fromIdString(testIdString);

    try {
      JSONObject userIdJson = (JSONObject)new JSONParser().parse(json);
      assertEquals(userIdJson.toJSONString(), parsed.toString());
    }
    catch (ParseException e) {
      assert(false);
    }
  }
}