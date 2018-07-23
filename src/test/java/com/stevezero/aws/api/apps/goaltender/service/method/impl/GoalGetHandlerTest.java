package com.stevezero.aws.api.apps.goaltender.service.method.impl;

import com.stevezero.aws.api.apps.goaltender.resource.GoalTenderResourceType;
import com.stevezero.aws.api.apps.goaltender.resource.impl.GoalResource;
import com.stevezero.aws.api.apps.goaltender.service.MethodTestHelper;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.exceptions.InvalidApiResourceId;
import com.stevezero.aws.api.http.MethodType;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.service.ApiGatewayProxyResponse;
import com.stevezero.aws.api.service.ApiPath;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GoalGetHandlerTest extends MethodTestHelper {
  private final GoalResource testResource = new GoalResource(
      testGoalId,
      "Goal Text",
      "Timestamp",
      true);

  public GoalGetHandlerTest() {
    requestHandler = new GoalGetHandler(storageService);
    testMethod = MethodType.GET;
  }

  @Test
  public void testHandleRequest() throws ApiException {
    ApiPath apiPath = ApiPath.of("/user/" + testUserIdString + "/goal/" + testGoalId.getGoalIdString(),
        GoalTenderResourceType.class);

    // Add the item to storage.
    storageService.add(testResource.getResourceId().toBase64String(), testResource.toItem());

    // Run the query.
    ApiGatewayProxyResponse output = getOutput(apiPath);

    // Ensure we get the correct response code and body.
    assertEquals(StatusCode.OK.getCode(), output.getStatusCode());
    assertEquals(testResource.toJsonString(), output.getBody());
  }

  @Test(expected = InvalidApiResourceId.class)
  public void testHandleRequestMalformedId() throws ApiException {
    // Garbage user ID.
    ApiPath apiPath = ApiPath.of("/user/eyJ0IjogImciLCJpIjogIjEyMzQifQa/goal/1234", GoalTenderResourceType.class);

    assertEquals(StatusCode.NOT_FOUND.getCode(), getOutput(apiPath).getStatusCode());
  }

  @Test
  public void testHandleRequestNotFound() throws ApiException {
    ApiPath apiPath = ApiPath.of("/user/" + testUserIdString + "/goal/" + testGoalId.getGoalIdString(),
        GoalTenderResourceType.class);

    // No items in storage.

    assertEquals(StatusCode.NOT_FOUND.getCode(), getOutput(apiPath).getStatusCode());
  }
}