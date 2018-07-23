package com.stevezero.aws.api.apps.goaltender.service.method.impl;

import com.stevezero.aws.api.apps.goaltender.resource.GoalTenderResourceType;
import com.stevezero.aws.api.apps.goaltender.resource.impl.GoalResource;
import com.stevezero.aws.api.apps.goaltender.service.MethodTestHelper;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.GoalItem;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.exceptions.InvalidApiResource;
import com.stevezero.aws.api.exceptions.InvalidApiResourceUpdate;
import com.stevezero.aws.api.http.MethodType;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.service.ApiPath;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class GoalPutHandlerTest extends MethodTestHelper {
  private final GoalResource testResourceComplete = new GoalResource(
      testGoalId,
      "Goal Text",
      "Timestamp",
      true);
  private final GoalResource testResourceIncomplete = new GoalResource(
      testGoalId,
      "Goal Text",
      "Timestamp",
      false);

  public GoalPutHandlerTest() {
    requestHandler = new GoalPutHandler(storageService);
    testMethod = MethodType.PUT;
  }

  @Test(expected = InvalidApiResource.class)
  public void testHandleRequestIdMismatch() throws ApiException {
    ApiPath apiPath = ApiPath.of("/user/" + testUserIdString + "/goal/WRONG",
        GoalTenderResourceType.class);

    // Attempt to PUT a resource update where the body ID does not match the path ID.  Blow up.
    getOutput(apiPath, testResourceComplete);
  }

  @Test(expected = InvalidApiResourceUpdate.class)
  public void testHandleRequestDoesNotExistFail() throws ApiException {
    ApiPath apiPath = ApiPath.of("/user/" + testUserIdString + "/goal/" + testGoalId.getGoalIdString(),
        GoalTenderResourceType.class);

    // Storage is empty.

    // Attempt to PUT a resource update; should blow up.
    getOutput(apiPath, testResourceComplete);
  }

  @Test
  public void testHandleRequestUpdateGoalOk() throws ApiException {
    ApiPath apiPath = ApiPath.of("/user/" + testUserIdString + "/goal/" + testGoalId.getGoalIdString(),
        GoalTenderResourceType.class);

    // Storage has the incomplete goal.
    storageService.add(testResourceIncomplete.getResourceId().toBase64String(), testResourceIncomplete.toItem());

    // PUT an updated resource to complete the goal.
    assertEquals(StatusCode.OK.getCode(), getOutput(apiPath, testResourceComplete).getStatusCode());

    // Storage should now have the item in it.
    GoalItem resultItem = (GoalItem)storageService.getMap().get(testGoalId.toBase64String());
    assertEquals(new GoalResource(resultItem).toJsonString(), testResourceComplete.toJsonString());
  }

  @Test
  public void testHandleRequestUpdateNoop() throws ApiException {
    ApiPath apiPath = ApiPath.of("/user/" + testUserIdString + "/goal/" + testGoalId.getGoalIdString(),
        GoalTenderResourceType.class);

    // Storage has the incomplete goal.
    storageService.add(testResourceIncomplete.getResourceId().toBase64String(), testResourceIncomplete.toItem());

    // PUT the exact same resource.
    assertEquals(StatusCode.OK.getCode(), getOutput(apiPath, testResourceIncomplete).getStatusCode());

    // Storage should now have the item in it.
    GoalItem resultItem = (GoalItem)storageService.getMap().get(testGoalId.toBase64String());
    assertEquals(new GoalResource(resultItem).toJsonString(), testResourceIncomplete.toJsonString());
  }

  @Test
  public void testHandleRequestUpdateGoalIdempotent() throws ApiException {
    ApiPath apiPath = ApiPath.of("/user/" + testUserIdString + "/goal/" + testGoalId.getGoalIdString(),
        GoalTenderResourceType.class);

    // Storage has the incomplete goal.
    storageService.add(testResourceIncomplete.getResourceId().toBase64String(), testResourceIncomplete.toItem());

    // PUT an updated resource to complete the goal multiple times; shouldn't change outcome.
    assertEquals(StatusCode.OK.getCode(), getOutput(apiPath, testResourceComplete).getStatusCode());
    assertEquals(StatusCode.OK.getCode(), getOutput(apiPath, testResourceComplete).getStatusCode());
    assertEquals(StatusCode.OK.getCode(), getOutput(apiPath, testResourceComplete).getStatusCode());

    // Storage should now have the item in it.
    GoalItem resultItem = (GoalItem)storageService.getMap().get(testGoalId.toBase64String());
    assertEquals(new GoalResource(resultItem).toJsonString(), testResourceComplete.toJsonString());

    // ... And that should be the ONLY item in storage.
    assertEquals(1, storageService.getMap().keySet().size());
  }


}
