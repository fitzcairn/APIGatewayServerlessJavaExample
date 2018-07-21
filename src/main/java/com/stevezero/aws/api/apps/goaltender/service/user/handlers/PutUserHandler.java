package com.stevezero.aws.api.apps.goaltender.service.user.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.stevezero.aws.api.ApiGatewayProxyRequest;
import com.stevezero.aws.api.ApiGatewayProxyResponse;
import com.stevezero.aws.api.apps.goaltender.id.IdExtractor;
import com.stevezero.aws.api.apps.goaltender.id.impl.UserId;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.exceptions.InvalidApiResource;
import com.stevezero.aws.api.exceptions.InvalidApiResourceName;
import com.stevezero.aws.api.service.ApiMethodHandler;
import com.stevezero.aws.api.apps.goaltender.service.resource.ResourceType;
import com.stevezero.aws.api.apps.goaltender.service.resource.impl.UserResource;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.UserItem;
import com.stevezero.aws.api.exceptions.InvalidResourceIdException;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.id.ResourceId;
import com.stevezero.aws.api.storage.service.StorageService;

/**
 * Handler for PUT /user/{ID}
 * Update user with JSON payload data.
 */
public class PutUserHandler implements ApiMethodHandler {

  @Override
  public ApiGatewayProxyResponse handleRequest(ApiGatewayProxyRequest request,
                                               Context context,
                                               StorageService storageService) throws ApiException {
    ApiGatewayProxyResponse.Builder responseBuilder = new ApiGatewayProxyResponse.Builder();
    LambdaLogger logger = context.getLogger();

    logger.log("Executing PutUserHandler");

    // Pull out the ID.
    ResourceId id = IdExtractor.extractIdFromPath(request.getPath(), ResourceType.USER);

    // Get the user resource from the body.
    UserResource resource = UserResource.fromJson(request.getBody(), (UserId)id);

    // Upsert new item and return it.
    storageService.update(resource.toItem());

    return responseBuilder
        .withBody(resource.toJsonString())
        .withStatusCode(StatusCode.OK)
        .build();
  }
}
