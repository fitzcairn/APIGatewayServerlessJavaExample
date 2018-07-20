package com.stevezero.aws.goaltender.data;

import com.amazonaws.services.lambda.runtime.Context;
import com.stevezero.aws.goaltender.ApiGatewayProxyRequest;
import com.stevezero.aws.goaltender.ApiGatewayProxyResponse;
import com.stevezero.aws.goaltender.StatusCode;
import com.stevezero.aws.goaltender.TestContext;
import com.stevezero.aws.goaltender.user.UserProxyHandler;
import com.stevezero.aws.goaltender.user.data.UserId;
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
  public void testFromPathOk() {
    // {"t": "g","i": "1234"}
    String testIdString = "eyJ0IjogImciLCJpIjogIjEyMzQifQ==";
    String path = "user/" + testIdString;

    UserId result = UserId.fromPathString(path);
    assertEquals("1234", result.getId());
    assertEquals(UserId.IdentityType.GOOGLE, result.getType());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFromPathInvalidResource() {
    // {"t": "g","i": "1234"}
    String testIdString = "eyJ0IjogImciLCJpIjogIjEyMzQifQ==";
    String path = "notauser/" + testIdString;
    UserId result = UserId.fromPathString(path);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFromPathInvalidPath() {
    // {"t": "g","i": "1234"}
    String testIdString = "eyJ0IjogImciLCJpIjogIjEyMzQifQ==";
    String path = "prod/user/" + testIdString;
    UserId result = UserId.fromPathString(path);
  }

  @Test
  public void testFromIdStringOk() {
    // {"t": "g","i": "1234"}
    String testIdString = "eyJ0IjogImciLCJpIjogIjEyMzQifQ==";

    UserId result = UserId.fromIdString(testIdString);
    assertEquals("1234", result.getId());
    assertEquals(UserId.IdentityType.GOOGLE, result.getType());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFromIdStringInvalidBase64() {
    UserId.fromIdString("///");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFromIdStringInvalidJson() {
    // {t: "g",i: "1234"} <-- missing quotes on keys, invalid
    String testIdString = "e3Q6ICJnIixpOiAiMTIzNCJ9";
    UserId.fromIdString("testIdString");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFromIdStringInvalidIdentity() {
    // {"t": "XX","i": "1234"}
    String testIdString = "eyJ0IjogIlhYIiwiaSI6ICIxMjM0In0=";
    UserId.fromIdString(testIdString);
  }

  @Test
  public void testToString() {
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