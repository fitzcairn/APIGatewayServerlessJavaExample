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
import com.stevezero.aws.api.exceptions.InvalidApiResourceCreate;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.id.ApiResourceId;
import com.stevezero.aws.api.service.ApiGatewayProxyRequest;
import com.stevezero.aws.api.service.ApiGatewayProxyResponse;
import com.stevezero.aws.api.service.ApiPath;
import com.stevezero.aws.api.storage.items.MappedItem;
import com.stevezero.aws.api.storage.service.StorageService;

/**
 * Handler for POST user/ID/goal
 * Create a new goal.
 */
public class GoalPostHandler extends GoalTenderApiMethodHandler {
  public GoalPostHandler() {
    super(new GoalStorageService());
  }

  // For testing only.
  GoalPostHandler(StorageService storageService) {
    super(storageService);
  }

  @Override
  public ApiGatewayProxyResponse handleRequest(ApiGatewayProxyRequest request,
                                               ApiPath parsedApiPath,
                                               Context context) throws ApiException {
    ApiGatewayProxyResponse.Builder responseBuilder = new ApiGatewayProxyResponse.Builder();

    // Pull out the user ID.
    ApiResourceId userId = new UserId(parsedApiPath.getIdFor(GoalTenderResourceType.USER));

    // Get the goal resource from the body.
    GoalResource resource = new GoalResource(request.getBody(), userId.toBase64String());

    // Ensure that this goal does not exist.
    // (Naive, non-atomic check.)
    if (storageService.get(resource.getResourceId()) != null)
      throw new InvalidApiResourceCreate(GoalTenderResourceType.GOAL,
          ((GoalId)resource.getResourceId()).getClientGoalId());

    // Insert new item and return it.
    storageService.update(resource.toItem());

    return responseBuilder
        .withBody(resource.toJsonString())
        .withStatusCode(StatusCode.OK)
        .build();
  }
}
