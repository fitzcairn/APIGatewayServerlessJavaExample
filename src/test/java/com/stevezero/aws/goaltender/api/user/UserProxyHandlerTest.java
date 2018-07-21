package com.stevezero.aws.goaltender.api.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.stevezero.aws.goaltender.api.ApiGatewayProxyRequest;
import com.stevezero.aws.goaltender.api.ApiGatewayProxyResponse;
import com.stevezero.aws.goaltender.api.TestContext;
import com.stevezero.aws.goaltender.api.http.StatusCode;
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
  public void testHandleRequest() {
    Context context = createContext();
    UserProxyHandler requestHandler = new UserProxyHandler();

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
    Context context = createContext();
    UserProxyHandler requestHandler = new UserProxyHandler();

    // Create a fake request with a malformed ID.
    ApiGatewayProxyRequest request = new ApiGatewayProxyRequest()
        .setPath("/user/eyJ0IjogImciLCJpIjogIjEyMzQifQa")
        .setHttpMethod("GET")
        .setBase64Encoded(false);

    ApiGatewayProxyResponse output = requestHandler.handleRequest(request, context);
    assertEquals(StatusCode.NOT_FOUND.getCode(), output.getStatusCode());
  }
}