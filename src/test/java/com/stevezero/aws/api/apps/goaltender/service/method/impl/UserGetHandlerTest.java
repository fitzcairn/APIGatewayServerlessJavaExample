package com.stevezero.aws.api.apps.goaltender.service.method.impl;

import com.stevezero.aws.api.apps.goaltender.resource.GoalTenderResourceType;
import com.stevezero.aws.api.apps.goaltender.service.MethodTestHelper;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.UserItem;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.exceptions.InvalidApiResourceId;
import com.stevezero.aws.api.http.MethodType;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.service.ApiPath;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the public class UserGetHandler class.
 */
public class UserGetHandlerTest extends MethodTestHelper {

  public UserGetHandlerTest() {
    requestHandler = new UserGetHandler(storageService);
    testMethod = MethodType.GET;
  }

  @Test
  public void testHandleRequest() throws ApiException {
    ApiPath apiPath = ApiPath.of("/user/" + testUserIdString, GoalTenderResourceType.class);

    UserItem expected = new UserItem();
    expected.setKey(testUserIdString);
    storageService.add(testUserIdString, expected);

    assertEquals(StatusCode.OK.getCode(), getOutput(apiPath).getStatusCode());
  }

  @Test(expected = InvalidApiResourceId.class)
  public void testHandleRequestMalformedId() throws ApiException {
    // Garbage ID.
    ApiPath apiPath = ApiPath.of("/user/eyJ0IjogImciLCJpIjogIjEyMzQifQa", GoalTenderResourceType.class);

    assertEquals(StatusCode.NOT_FOUND.getCode(), getOutput(apiPath).getStatusCode());
  }

  @Test
  public void testHandleRequestNotFound() throws ApiException {
    ApiPath apiPath = ApiPath.of("/user/" + testUserIdString, GoalTenderResourceType.class);

    // No items in storage.

    assertEquals(StatusCode.NOT_FOUND.getCode(), getOutput(apiPath).getStatusCode());
  }
}