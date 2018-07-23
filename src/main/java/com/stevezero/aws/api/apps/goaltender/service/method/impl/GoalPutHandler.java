package com.stevezero.aws.api.apps.goaltender.service.method.impl;

import com.amazonaws.services.lambda.runtime.Context;
import com.stevezero.aws.api.apps.goaltender.id.impl.GoalId;
import com.stevezero.aws.api.apps.goaltender.id.impl.UserId;
import com.stevezero.aws.api.apps.goaltender.resource.GoalTenderResourceType;
import com.stevezero.aws.api.apps.goaltender.resource.impl.GoalResource;
import com.stevezero.aws.api.apps.goaltender.service.method.GoalTenderApiMethodHandler;
import com.stevezero.aws.api.apps.goaltender.storage.service.impl.GoalStorageService;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.exceptions.InvalidApiResource;
import com.stevezero.aws.api.exceptions.InvalidApiResourceUpdate;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.id.ApiResourceId;
import com.stevezero.aws.api.service.ApiGatewayProxyRequest;
import com.stevezero.aws.api.service.ApiGatewayProxyResponse;
import com.stevezero.aws.api.service.ApiPath;
import com.stevezero.aws.api.storage.service.StorageService;

/**
 * Handler for PUT user/ID/goal/{ID}
 * Update goal with JSON payload data.
 */
public class GoalPutHandler extends GoalTenderApiMethodHandler {
  public GoalPutHandler() {
    super(new GoalStorageService());
  }

  // For testing only.
  GoalPutHandler(StorageService storageService) {
    super(storageService);
  }

  @Override
  public ApiGatewayProxyResponse handleRequest(ApiGatewayProxyRequest request,
                                               ApiPath parsedApiPath,
                                               Context context) throws ApiException {
    ApiGatewayProxyResponse.Builder responseBuilder = new ApiGatewayProxyResponse.Builder();

    // Pull out the ID.
    ApiResourceId userId = new UserId(parsedApiPath.getIdFor(GoalTenderResourceType.USER));
    ApiResourceId goalId = new GoalId(parsedApiPath.getIdFor(GoalTenderResourceType.GOAL), (UserId) userId);

    // Get the goal resource from the body.
    GoalResource resource = new GoalResource(request.getBody(), userId.toBase64String());

    // Check that the body goal ID matches the goal ID on the path.
    if (!((GoalId)resource.getResourceId()).getGoalIdString().equals(
        parsedApiPath.getIdFor(GoalTenderResourceType.GOAL)))
      throw new InvalidApiResource(GoalTenderResourceType.GOAL);

    // Check to make sure we have a goal with that ID in storage.
    // (Naive, non-atomic check.)
    if (storageService.get(resource.getResourceId()) == null)
      throw new InvalidApiResourceUpdate(GoalTenderResourceType.GOAL,
          ((GoalId)resource.getResourceId()).getClientGoalId());

    // Update new item and return it.
    storageService.update(resource.toItem());

    return responseBuilder
        .withBody(resource.toJsonString())
        .withStatusCode(StatusCode.OK)
        .build();
  }
}
