package com.stevezero.aws.api.apps.goaltender.service.method.impl;

import com.stevezero.aws.api.apps.goaltender.resource.GoalTenderResourceType;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.UserItem;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.exceptions.InvalidResourceIdException;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.mocks.MockContext;
import com.stevezero.aws.api.mocks.MockStorageService;
import com.stevezero.aws.api.service.ApiGatewayProxyRequest;
import com.stevezero.aws.api.service.ApiGatewayProxyResponse;
import com.stevezero.aws.api.service.ApiPath;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the public class UserGetHandler class.
 */
public class UserGetHandlerTest {
  // {"t": "g","i": "1234"}
  private final String testIdString = "eyJ0IjoiZyIsImkiOiIxMjM0In0=";
  private final MockStorageService storageService = new MockStorageService();
  private final UserGetHandler requestHandler = new UserGetHandler(storageService);
  private ApiGatewayProxyRequest testRequest;

  @Before
  public void reset() {
    storageService.reset();
    testRequest = new ApiGatewayProxyRequest()
        .setHttpMethod("GET")
        .setBase64Encoded(false);
  }

  private ApiGatewayProxyResponse getOutput(ApiPath apiPath) throws ApiException {
    MockContext context = new MockContext();
    testRequest.setPath(apiPath.getPath());
    return requestHandler.handleRequest(testRequest, apiPath, context);
  }

  @Test
  public void testHandleRequest() throws ApiException {
    ApiPath apiPath = ApiPath.of("/user/" + testIdString, GoalTenderResourceType.class);

    UserItem expected = new UserItem();
    expected.setKey(testIdString);
    storageService.add(testIdString, expected);

    assertEquals(StatusCode.OK.getCode(), getOutput(apiPath).getStatusCode());
  }

  @Test(expected = InvalidResourceIdException.class)
  public void testHandleRequestMalformedId() throws ApiException {
    // Garbage ID.
    ApiPath apiPath = ApiPath.of("/user/eyJ0IjogImciLCJpIjogIjEyMzQifQa", GoalTenderResourceType.class);

    assertEquals(StatusCode.NOT_FOUND.getCode(), getOutput(apiPath).getStatusCode());
  }

  @Test
  public void testHandleRequestNotFound() throws ApiException {
    ApiPath apiPath = ApiPath.of("/user/" + testIdString, GoalTenderResourceType.class);

    // No items in storage.

    assertEquals(StatusCode.NOT_FOUND.getCode(), getOutput(apiPath).getStatusCode());
  }
}