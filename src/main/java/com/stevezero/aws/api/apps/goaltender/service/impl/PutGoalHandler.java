package com.stevezero.aws.api.apps.goaltender.service.impl;

import com.amazonaws.services.lambda.runtime.Context;
import com.stevezero.aws.api.service.ApiGatewayProxyRequest;
import com.stevezero.aws.api.service.ApiGatewayProxyResponse;
import com.stevezero.aws.api.apps.goaltender.id.PathIdExtractor;
import com.stevezero.aws.api.apps.goaltender.id.impl.GoalId;
import com.stevezero.aws.api.apps.goaltender.resource.ResourceType;
import com.stevezero.aws.api.apps.goaltender.resource.impl.GoalResource;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.exceptions.InvalidApiResource;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.id.ApiResourceId;
import com.stevezero.aws.api.service.ApiMethodHandler;
import com.stevezero.aws.api.storage.service.StorageService;

/**
 * Handler for PUT user/ID/goal/{ID}
 * Update goal with JSON payload data.
 */
public class PutGoalHandler implements ApiMethodHandler {

  @Override
  public ApiGatewayProxyResponse handleRequest(ApiGatewayProxyRequest request,
                                               Context context,
                                               StorageService storageService) throws ApiException {
    ApiGatewayProxyResponse.Builder responseBuilder = new ApiGatewayProxyResponse.Builder();

    // Pull out the ID.
    ApiResourceId userId = PathIdExtractor.extractIdFromPath(request.getPath(), ResourceType.USER);
    ApiResourceId goalId = PathIdExtractor.extractIdFromPath(request.getPath(), ResourceType.GOAL);

    // Get the goal resource from the body.
    GoalResource resource = new GoalResource(request.getBody(), userId.toBase64String());

    // Check that the body goal ID matches the goal ID on the path.
    if (!goalId.toBase64String().equals(((GoalId)resource.getResourceId()).getGoalIdString()))
      throw new InvalidApiResource(ResourceType.GOAL);

    // Upsert new item and return it.
    storageService.update(resource.toItem());

    return responseBuilder
        .withBody(resource.toJsonString())
        .withStatusCode(StatusCode.OK)
        .build();
  }
}
