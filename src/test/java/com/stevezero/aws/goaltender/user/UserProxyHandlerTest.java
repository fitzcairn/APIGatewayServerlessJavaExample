package com.stevezero.aws.goaltender.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.stevezero.aws.goaltender.ApiGatewayProxyRequest;
import com.stevezero.aws.goaltender.ApiGatewayProxyResponse;
import com.stevezero.aws.goaltender.StatusCode;
import com.stevezero.aws.goaltender.TestContext;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the GetRequest class.
 */
public class UserProxyHandlerTest {
  // {"t": "g","i": "1234"}
  private final String testIdString = "eyJ0IjogImciLCJpIjogIjEyMzQifQ==";

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
        .setPath("user/" + testIdString)
        .setHttpMethod("GET")
        .setBase64Encoded(false);

    ApiGatewayProxyResponse output = requestHandler.handleRequest(request, context);

    if (output != null) {
      assertEquals(StatusCode.OK.getCode(), output.getStatusCode());

      System.out.println(output.toString());
    }
  }
}