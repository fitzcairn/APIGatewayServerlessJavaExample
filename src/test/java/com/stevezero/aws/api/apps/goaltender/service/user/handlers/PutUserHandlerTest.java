package com.stevezero.aws.api.apps.goaltender.service.user.handlers;

import com.stevezero.aws.api.ApiGatewayProxyRequest;
import com.stevezero.aws.api.ApiGatewayProxyResponse;
import com.stevezero.aws.api.mocks.MockContext;
import com.stevezero.aws.api.apps.goaltender.id.impl.UserId;
import com.stevezero.aws.api.apps.goaltender.service.resource.impl.UserResource;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.UserItem;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.exceptions.InvalidApiResource;
import com.stevezero.aws.api.http.MethodType;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.id.IdentityType;
import com.stevezero.aws.api.mocks.MockStorageService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the PutUserHandler class.
 */
public class PutUserHandlerTest {
  // {"t": "g","i": "1234"}
  private final String testIdString = "eyJ0IjoiZyIsImkiOiIxMjM0In0=";
  private final UserResource testResource = new UserResource(
      new UserId("1234", IdentityType.GOOGLE),
      true,
      true,
      "2018-07-22T02:33:57+00:00",
      "2:33:57+00:00");

  private final PutUserHandler requestHandler = new PutUserHandler();

  @Test
  public void testHandleRequestCreateNew() throws ApiException {
    MockContext context = new MockContext();
    MockStorageService storageService = new MockStorageService();

    // Storage is empty.

    // PUT a resource.
    ApiGatewayProxyRequest request = new ApiGatewayProxyRequest()
        .setPath("/user/" + testIdString)
        .setHttpMethod(MethodType.PUT.toString())
        .setBody(testResource.toJsonString())
        .setBase64Encoded(false);

    ApiGatewayProxyResponse output = requestHandler.handleRequest(request, context, storageService);
    assertEquals(StatusCode.OK.getCode(), output.getStatusCode());

    // Storage should now have the item in it.
    UserItem resultItem = (UserItem)storageService.getMap().get(testIdString);
    assertTrue(UserResource.of(resultItem).toJsonString().equals(testResource.toJsonString()));
  }

  @Test
  public void testHandleRequestUpdate() throws ApiException {
    MockContext context = new MockContext();
    MockStorageService storageService = new MockStorageService();

    // Storage has an item in it.
    storageService.add(testIdString, testResource.toItem());

    // Now, create an updated item.
    UserItem expectedItem = (UserItem)testResource.toItem();
    expectedItem.setReminderTimeString("UPDATED!");
    expectedItem.setLastUpdateDateTimeString("UPDATED!");
    expectedItem.setHasSeenFtux(false);
    UserResource expectedResource = UserResource.of(expectedItem);

    // PUT the new resource.
    ApiGatewayProxyRequest request = new ApiGatewayProxyRequest()
        .setPath("/user/" + testIdString)
        .setHttpMethod(MethodType.PUT.toString())
        .setBody(expectedResource.toJsonString())
        .setBase64Encoded(false);

    ApiGatewayProxyResponse output = requestHandler.handleRequest(request, context, storageService);
    assertEquals(StatusCode.OK.getCode(), output.getStatusCode());

    // Storage should now have the updates in it.
    UserItem resultItem = (UserItem)storageService.getMap().get(testIdString);
    assertTrue(UserResource.of(resultItem).toJsonString().equals(expectedResource.toJsonString()));
  }

  @Test(expected = InvalidApiResource.class)
  public void testHandleResourcePathIdMismatch() throws ApiException {
    MockContext context = new MockContext();
    MockStorageService storageService = new MockStorageService();

    UserId testId = new UserId("1234", IdentityType.GOOGLE);
    UserResource testResource = new UserResource(
        new UserId("5678", IdentityType.FACEBOOK), // <-- Does not match testId.
        true,
        true,
        "2018-07-22T02:33:57+00:00",
        "2:33:57+00:00");

    // Attempt the PUT.  This should blow up with a InvalidApiResource exception.
    ApiGatewayProxyRequest request = new ApiGatewayProxyRequest()
        .setPath("/user/" + testId.toEncoded())
        .setHttpMethod(MethodType.PUT.toString())
        .setBody(testResource.toJsonString())
        .setBase64Encoded(false);

    ApiGatewayProxyResponse output = requestHandler.handleRequest(request, context, storageService);
    assert false; // Should never reach here.
  }

  @Test(expected = InvalidApiResource.class)
  public void testHandleResourcePathNoBody() throws ApiException {
    MockContext context = new MockContext();
    MockStorageService storageService = new MockStorageService();

    UserId testId = new UserId("1234", IdentityType.GOOGLE);

    // Attempt the PUT.  This should blow up with a InvalidApiResource exception.
    ApiGatewayProxyRequest request = new ApiGatewayProxyRequest()
        .setPath("/user/" + testId.toEncoded())
        .setHttpMethod(MethodType.PUT.toString())
        .setBase64Encoded(false);

    ApiGatewayProxyResponse output = requestHandler.handleRequest(request, context, storageService);
    assert false; // Should never reach here.
  }
}
