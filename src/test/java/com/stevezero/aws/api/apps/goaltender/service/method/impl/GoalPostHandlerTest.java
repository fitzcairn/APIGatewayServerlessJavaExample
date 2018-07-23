package com.stevezero.aws.api.apps.goaltender.service.method.impl;

import com.stevezero.aws.api.apps.goaltender.resource.GoalTenderResourceType;
import com.stevezero.aws.api.apps.goaltender.resource.impl.GoalResource;
import com.stevezero.aws.api.apps.goaltender.service.MethodTestHelper;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.GoalItem;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.exceptions.InvalidApiResource;
import com.stevezero.aws.api.exceptions.InvalidApiResourceCreate;
import com.stevezero.aws.api.http.MethodType;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.mocks.MockContext;
import com.stevezero.aws.api.service.ApiGatewayProxyRequest;
import com.stevezero.aws.api.service.ApiPath;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GoalPostHandlerTest extends MethodTestHelper {
  private final GoalResource testResource = new GoalResource(
      testGoalId,
      "Goal Text",
      "Timestamp",
      true);

  public GoalPostHandlerTest() {
    requestHandler = new GoalPostHandler(storageService);
    testMethod = MethodType.GET;
  }

  @Test
  public void testHandleRequestCreateNewGoal() throws ApiException {
    ApiPath apiPath = ApiPath.of("/user/" + testUserIdString + "/goal", GoalTenderResourceType.class);

    // Storage is empty.

    // POST a resource.
    assertEquals(StatusCode.OK.getCode(), getOutput(apiPath, testResource).getStatusCode());

    // Storage should now have the item in it.
    GoalItem resultItem = (GoalItem)storageService.getMap().get(testGoalId.toBase64String());
    assertEquals(new GoalResource(resultItem).toJsonString(), testResource.toJsonString());
  }


  @Test(expected = InvalidApiResourceCreate.class)
  public void testHandleRequestCreateDuplicateGoalFail() throws ApiException {
    ApiPath apiPath = ApiPath.of("/user/" + testUserIdString + "/goal", GoalTenderResourceType.class);

    // Storage has an item in it.
    storageService.add(testGoalId.toBase64String(), testResource.toItem());

    // Attempt to POST the same resource.  Should blow up with a conflict.
    getOutput(apiPath, testResource);
  }

  @Test(expected = InvalidApiResource.class)
  public void testHandleResourcePathNoBody() throws ApiException {
    ApiPath apiPath = ApiPath.of("/user/" + testUserIdString + "/goal", GoalTenderResourceType.class);

    // Create a PUT request with no body.
    ApiGatewayProxyRequest request = new ApiGatewayProxyRequest()
        .setPath(apiPath.getPath())
        .setHttpMethod(MethodType.POST.toString())
        .setBase64Encoded(false);
    // Missing: body!

    // Attempt the POST.  This should blow up with a InvalidApiResource exception.
    requestHandler.handleRequest(request, apiPath, new MockContext());
    assert false; // Should never reach here.
  }
}
