package com.stevezero.aws.api;

import com.amazonaws.services.lambda.runtime.Context;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.http.MethodType;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.mocks.MockContext;
import com.stevezero.aws.api.service.ApiGatewayProxyRequest;
import com.stevezero.aws.api.service.ApiGatewayProxyResponse;
import com.stevezero.aws.api.service.ApiPath;
import com.stevezero.aws.api.service.ApiProxyHandler;
import com.stevezero.aws.api.service.method.ApiMethodHandler;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the ApiProxyHandler class.
 */
public class ApiProxyHandlerTest {
  private enum TestResource {
    APPLE,
    LEMON,
    PEAR,
  }

  private class TestMethodHandler implements ApiMethodHandler {
    @Override
    public ApiGatewayProxyResponse handleRequest(ApiGatewayProxyRequest request,
                                                 ApiPath parsedApiPath,
                                                 Context context) throws ApiException {
      return new ApiGatewayProxyResponse.Builder()
          .withBody(request.getBody())
          .withStatusCode(StatusCode.OK)
          .build();
    }
  }

  private class TestProxyHandler extends ApiProxyHandler {
    TestProxyHandler() {
      super(TestResource.class);
    }

    @Override
    protected ApiMethodHandler getMethodHandler(MethodType methodType, ApiPath parsedApiPath)
        throws ApiException {
      return new TestMethodHandler();
    }
  }

  @Test
  public void testHandleRequestValid() {
    MockContext context = new MockContext();
    ApiProxyHandler testHandler = new TestProxyHandler();

    ApiGatewayProxyRequest testRequest = new ApiGatewayProxyRequest();
    testRequest.setBody("OK");
    testRequest.setHttpMethod(MethodType.GET.toString());
    testRequest.setPath("/apple/1234");

    // Invoke the handler
    ApiGatewayProxyResponse testResponse = testHandler.handleRequest(testRequest, context);

    // Should succeed, and return the request body in the response.
    assertEquals(StatusCode.OK.getCode(), testResponse.getStatusCode());
    assertEquals(testRequest.getBody(), testResponse.getBody());
 }


  @Test
  public void testHandleRequestBadResource() {
    MockContext context = new MockContext();
    ApiProxyHandler testHandler = new TestProxyHandler();

    ApiGatewayProxyRequest testRequest = new ApiGatewayProxyRequest();
    testRequest.setBody("FAIL");
    testRequest.setHttpMethod(MethodType.GET.toString());
    testRequest.setPath("/test/"); // <-- Not a valid resource.

    // Invoke the handler
    ApiGatewayProxyResponse testResponse = testHandler.handleRequest(testRequest, context);

    // Should return 400 bad request.
    assertEquals(StatusCode.BAD_REQUEST.getCode(), testResponse.getStatusCode());
  }
}
