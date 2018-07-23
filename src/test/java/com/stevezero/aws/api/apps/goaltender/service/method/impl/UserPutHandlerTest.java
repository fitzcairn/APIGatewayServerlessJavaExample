package com.stevezero.aws.api.apps.goaltender.service.method.impl;

import com.stevezero.aws.api.apps.goaltender.id.impl.UserId;
import com.stevezero.aws.api.apps.goaltender.resource.GoalTenderResourceType;
import com.stevezero.aws.api.apps.goaltender.resource.impl.UserResource;
import com.stevezero.aws.api.apps.goaltender.service.MethodTestHelper;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.UserItem;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.exceptions.InvalidApiResource;
import com.stevezero.aws.api.http.MethodType;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.id.IdentityType;
import com.stevezero.aws.api.mocks.MockContext;
import com.stevezero.aws.api.service.ApiGatewayProxyRequest;
import com.stevezero.aws.api.service.ApiPath;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class UserPutHandlerTest extends MethodTestHelper {
  private final UserResource testResource = new UserResource(
      testUserId,
      true,
      true,
      "2018-07-22T02:33:57+00:00",
      "2:33:57+00:00");

  public UserPutHandlerTest() {
    requestHandler = new UserPutHandler(storageService);
    testMethod = MethodType.PUT;
  }

  @Test
  public void testHandleRequestCreateNew() throws ApiException {
    ApiPath apiPath = ApiPath.of("/user/" + testUserIdString, GoalTenderResourceType.class);

    // Storage is empty.

    // PUT a resource.
    assertEquals(StatusCode.OK.getCode(), getOutput(apiPath, testResource).getStatusCode());

    // Storage should now have the item in it.
    UserItem resultItem = (UserItem)storageService.getMap().get(testUserIdString);
    assertTrue(new UserResource(resultItem).toJsonString().equals(testResource.toJsonString()));
  }

  @Test
  public void testHandleRequestUpdate() throws ApiException {
    ApiPath apiPath = ApiPath.of("/user/" + testUserIdString, GoalTenderResourceType.class);

    // Storage has an item in it.
    storageService.add(testUserIdString, testResource.toItem());

    // Now, create an updated item.
    UserItem expectedItem = (UserItem)testResource.toItem();
    expectedItem.setReminderTimeString("UPDATED!");
    expectedItem.setLastUpdateDateTimeString("UPDATED!");
    expectedItem.setHasSeenFtux(false);
    UserResource expectedResource = new UserResource(expectedItem);

    // PUT the new resource.
    assertEquals(StatusCode.OK.getCode(), getOutput(apiPath, expectedResource).getStatusCode());

    // Storage should now have the updates in it.
    UserItem resultItem = (UserItem)storageService.getMap().get(testUserIdString);
    assertTrue(new UserResource(resultItem).toJsonString().equals(expectedResource.toJsonString()));
  }

  @Test(expected = InvalidApiResource.class)
  public void testHandleResourcePathIdMismatch() throws ApiException {
    ApiPath apiPath = ApiPath.of("/user/" + testUserIdString, GoalTenderResourceType.class);

    // Create a new resource with a different ID than the ID on the path.
    UserResource testResource = new UserResource(
        new UserId("5678", IdentityType.FACEBOOK), // <-- Does not match this.testUserId.
        true,
        true,
        "2018-07-22T02:33:57+00:00",
        "2:33:57+00:00");

    // Attempt the PUT.  This should blow up with a InvalidApiResource exception.
    getOutput(apiPath, testResource);
    assert false; // Should never reach here.
  }

  @Test(expected = InvalidApiResource.class)
  public void testHandleResourcePathNoBody() throws ApiException {
    ApiPath apiPath = ApiPath.of("/user/" + testUserIdString, GoalTenderResourceType.class);

    // Create a PUT request with no body.
    ApiGatewayProxyRequest request = new ApiGatewayProxyRequest()
        .setPath(apiPath.getPath())
        .setHttpMethod(MethodType.PUT.toString())
        .setBase64Encoded(false);
        // Missing: body!

    // Attempt the PUT.  This should blow up with a InvalidApiResource exception.
    requestHandler.handleRequest(request, apiPath, new MockContext());
    assert false; // Should never reach here.
  }
}
