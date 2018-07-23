package com.stevezero.aws.api;

import com.amazonaws.services.lambda.runtime.Context;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.http.MethodType;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.mocks.MockContext;
import com.stevezero.aws.api.mocks.MockStorageService;
import com.stevezero.aws.api.service.ApiGatewayProxyRequest;
import com.stevezero.aws.api.service.ApiGatewayProxyResponse;
import com.stevezero.aws.api.service.ApiMethodHandler;
import com.stevezero.aws.api.service.ApiProxyHandler;
import com.stevezero.aws.api.storage.service.StorageService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the ApiProxyHandler class.
 */
public class ApiProxyHandlerTest {
  private class TestMethodHandler implements ApiMethodHandler {
    @Override
    public ApiGatewayProxyResponse handleRequest(ApiGatewayProxyRequest request,
                                                 Context context,
                                                 StorageService storageService) throws ApiException {
      return new ApiGatewayProxyResponse.Builder()
          .withBody(request.getBody())
          .withStatusCode(StatusCode.OK)
          .build();
    }
  }

  private class TestProxyHandler extends ApiProxyHandler {
    protected TestProxyHandler(StorageService storageService) {
      super(storageService);
    }

    @Override
    protected void createHandlers() {
      methodHandlers.put(MethodType.GET, new TestMethodHandler());
    }
  }

  @Test
  public void testHandleRequestHasMethod() {
    MockContext context = new MockContext();
    MockStorageService storageService = new MockStorageService();

    ApiProxyHandler testHandler = new TestProxyHandler(storageService);

    ApiGatewayProxyRequest testRequest = new ApiGatewayProxyRequest();
    testRequest.setBody("OK");
    testRequest.setHttpMethod(MethodType.GET.toString());

    // Invoke the handler for a GET call.
    ApiGatewayProxyResponse testResponse = testHandler.handleRequest(testRequest, context);

    // Should succeed, and return the request body in the response.
    assertEquals(StatusCode.OK.getCode(), testResponse.getStatusCode());
    assertEquals(testRequest.getBody(), testResponse.getBody());
 }

  @Test
  public void testHandleRequestNoMethod() {
    MockContext context = new MockContext();
    MockStorageService storageService = new MockStorageService();

    ApiProxyHandler testHandler = new TestProxyHandler(storageService);

    // Build a request for a PUT call, which there is no handler for.
    ApiGatewayProxyRequest testRequest = new ApiGatewayProxyRequest();
    testRequest.setHttpMethod(MethodType.PUT.toString());

    // Invoke the handler for a PUT call.
    ApiGatewayProxyResponse testResponse = testHandler.handleRequest(testRequest, context);

    // Should fail with a NOT_ALLOWED
    assertEquals(StatusCode.NOT_ALLOWED.getCode(), testResponse.getStatusCode());
  }

}
