package com.stevezero.aws.goaltender.api.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.stevezero.aws.goaltender.api.ApiGatewayProxyRequest;
import com.stevezero.aws.goaltender.api.ApiGatewayProxyResponse;
import com.stevezero.aws.goaltender.api.TestContext;
import com.stevezero.aws.goaltender.api.exceptions.InvalidAPIResource;
import com.stevezero.aws.goaltender.api.exceptions.InvalidUserIdException;
import com.stevezero.aws.goaltender.api.http.StatusCode;
import com.stevezero.aws.goaltender.common.IdentityType;
import com.stevezero.aws.goaltender.common.UserId;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the GetRequest class.
 */
public class UserProxyHandlerTest {
  // {"t": "g","i": "1234"}
  private final String testIdString = "eyJ0IjogImciLCJpIjogIjEyMzQifQ";

  @BeforeClass
  public static void setup() throws IOException {
  }

  private Context createContext() {
    TestContext context = new TestContext();
    // TODO: customize context here.
    context.setFunctionName("LambdaForm");
    return context;
  }

  @Test
  public void testPathOk() throws InvalidAPIResource, InvalidUserIdException {
    UserProxyHandler requestHandler = new UserProxyHandler();

    // {"t": "g","i": "1234"}
    String testIdString = "eyJ0IjogImciLCJpIjogIjEyMzQifQ==";
    String path = "/user/" + testIdString;

    UserId result = requestHandler.extractIdFromPath(path);
    assertEquals("1234", result.getId());
    assertEquals(IdentityType.GOOGLE, result.getType());
  }

  @Test(expected = InvalidAPIResource.class)
  public void testPathInvalidResource() throws InvalidAPIResource, InvalidUserIdException {
    UserProxyHandler requestHandler = new UserProxyHandler();

    // {"t": "g","i": "1234"}
    String testIdString = "eyJ0IjogImciLCJpIjogIjEyMzQifQ==";
    String path = "/notauser/" + testIdString;
    UserId result = requestHandler.extractIdFromPath(path);
  }

  @Test(expected = InvalidAPIResource.class)
  public void testPathInvalidPath() throws InvalidAPIResource, InvalidUserIdException {
    UserProxyHandler requestHandler = new UserProxyHandler();

    // {"t": "g","i": "1234"}
    String testIdString = "eyJ0IjogImciLCJpIjogIjEyMzQifQ==";
    String path = "/oops/user/" + testIdString;
    UserId result = requestHandler.extractIdFromPath(path);
  }

  @Test
  public void testHandleRequest() {
    UserProxyHandler requestHandler = new UserProxyHandler();
    Context context = createContext();

    // Create a fake request.
    ApiGatewayProxyRequest request = new ApiGatewayProxyRequest()
        .setPath("/user/" + testIdString)
        .setHttpMethod("GET")
        .setBase64Encoded(false);

    ApiGatewayProxyResponse output = requestHandler.handleRequest(request, context);
    assertEquals(StatusCode.OK.getCode(), output.getStatusCode());
  }


  @Test
  public void testHandleRequestMalformedId() {
    UserProxyHandler requestHandler = new UserProxyHandler();
    Context context = createContext();

    // Create a fake request with a malformed ID.
    ApiGatewayProxyRequest request = new ApiGatewayProxyRequest()
        .setPath("/user/eyJ0IjogImciLCJpIjogIjEyMzQifQa")
        .setHttpMethod("GET")
        .setBase64Encoded(false);

    ApiGatewayProxyResponse output = requestHandler.handleRequest(request, context);
    assertEquals(StatusCode.NOT_FOUND.getCode(), output.getStatusCode());
  }
}