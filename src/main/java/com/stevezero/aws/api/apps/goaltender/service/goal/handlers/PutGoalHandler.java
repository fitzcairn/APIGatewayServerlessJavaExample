package com.stevezero.aws.api.apps.goaltender.service.goal.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.stevezero.aws.api.ApiGatewayProxyRequest;
import com.stevezero.aws.api.ApiGatewayProxyResponse;
import com.stevezero.aws.api.apps.goaltender.id.IdExtractor;
import com.stevezero.aws.api.apps.goaltender.resource.ResourceType;
import com.stevezero.aws.api.apps.goaltender.resource.impl.GoalResource;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.GoalItem;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.exceptions.InvalidApiResource;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.id.ResourceId;
import com.stevezero.aws.api.service.ApiMethodHandler;
import com.stevezero.aws.api.storage.service.StorageService;

/**
 * Handler for PUT /goal/{ID}
 * Update goal with JSON payload data.
 */
public class PutGoalHandler implements ApiMethodHandler {

  @Override
  public ApiGatewayProxyResponse handleRequest(ApiGatewayProxyRequest request,
                                               Context context,
                                               StorageService storageService) throws ApiException {
    ApiGatewayProxyResponse.Builder responseBuilder = new ApiGatewayProxyResponse.Builder();
    LambdaLogger logger = context.getLogger();

    logger.log("Executing PutGoalHandler");

    // Pull out the ID.
    ResourceId id = IdExtractor.extractIdFromPath(request.getPath(), ResourceType.USER);

    // Get the goal resource from the body.
    GoalResource resource = GoalResource.of(request.getBody());

    // Check that the body ID matches the path ID.
    if (!id.toEncoded().equals(resource.getId().toEncoded()))
      throw new InvalidApiResource(ResourceType.USER);

    // Upsert new item and return it.
    storageService.update(resource.toItem());

    return responseBuilder
        .withBody(resource.toJsonString())
        .withStatusCode(StatusCode.OK)
        .build();
  }
}
