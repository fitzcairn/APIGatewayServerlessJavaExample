package com.stevezero.aws.api.apps.goaltender.service.user.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.stevezero.aws.api.ApiGatewayProxyRequest;
import com.stevezero.aws.api.ApiGatewayProxyResponse;
import com.stevezero.aws.api.MockContext;
import com.stevezero.aws.api.apps.goaltender.service.user.UserProxyHandler;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.UserItem;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.service.MockStorageService;
import com.stevezero.aws.api.storage.service.StorageService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the public class GetUserHandler { class.
 */
public class GetUserHandlerTest {
  // {"t": "g","i": "1234"}
  private final String testIdString = "eyJ0IjoiZyIsImkiOiIxMjM0In0=";
  private final GetUserHandler requestHandler = new GetUserHandler();

  @Test
  public void testHandleRequest() {
    MockContext context = new MockContext();
    MockStorageService storageService = new MockStorageService();

    UserItem expected = new UserItem();
    expected.setKey(testIdString);
    storageService.add(testIdString, expected);

    // Create a fake request.
    ApiGatewayProxyRequest request = new ApiGatewayProxyRequest()
        .setPath("/user/" + testIdString)
        .setHttpMethod("GET")
        .setBase64Encoded(false);

    ApiGatewayProxyResponse output = requestHandler.handleRequest(request, context, storageService);
    assertEquals(StatusCode.OK.getCode(), output.getStatusCode());
  }

  @Test
  public void testHandleRequestMalformedId() {
    MockContext context = new MockContext();
    MockStorageService storageService = new MockStorageService();

    // Create a fake request with a malformed ID.
    ApiGatewayProxyRequest request = new ApiGatewayProxyRequest()
        .setPath("/user/eyJ0IjogImciLCJpIjogIjEyMzQifQa")
        .setHttpMethod("GET")
        .setBase64Encoded(false);

    ApiGatewayProxyResponse output = requestHandler.handleRequest(request, context, storageService);
    assertEquals(StatusCode.NOT_FOUND.getCode(), output.getStatusCode());
  }

  @Test
  public void testHandleRequestNotFound() {
    MockContext context = new MockContext();
    MockStorageService storageService = new MockStorageService();

    // Valid ID, but nothing in storage that matches.
    ApiGatewayProxyRequest request = new ApiGatewayProxyRequest()
        .setPath("/user/" + testIdString)
        .setHttpMethod("GET")
        .setBase64Encoded(false);

    ApiGatewayProxyResponse output = requestHandler.handleRequest(request, context, storageService);
    assertEquals(StatusCode.NOT_FOUND.getCode(), output.getStatusCode());
  }
}