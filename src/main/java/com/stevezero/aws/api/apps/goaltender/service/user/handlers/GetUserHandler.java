package com.stevezero.aws.api.apps.goaltender.service.user.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.stevezero.aws.api.ApiGatewayProxyRequest;
import com.stevezero.aws.api.ApiGatewayProxyResponse;
import com.stevezero.aws.api.apps.goaltender.id.IdExtractor;
import com.stevezero.aws.api.apps.goaltender.resource.ResourceType;
import com.stevezero.aws.api.apps.goaltender.resource.impl.UserResource;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.UserItem;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.id.ResourceId;
import com.stevezero.aws.api.service.ApiMethodHandler;
import com.stevezero.aws.api.storage.service.StorageService;

/**
 * Handler for GET /user/{ID}
 * Look up user and return information, or not found.
 */
public class GetUserHandler implements ApiMethodHandler {


  @Override
  public ApiGatewayProxyResponse handleRequest(ApiGatewayProxyRequest request,
                                               Context context,
                                               StorageService storageService) throws ApiException {
    ApiGatewayProxyResponse.Builder responseBuilder = new ApiGatewayProxyResponse.Builder();
    LambdaLogger logger = context.getLogger();

    logger.log("Executing GetUserHandler");

    // Pull out the ID.
    ResourceId id = IdExtractor.extractIdFromPath(request.getPath(), ResourceType.USER);

    // Attempt to fetch from storage.
    UserItem userItem = (UserItem)(storageService.get(id));

    if (userItem == null) {
      responseBuilder
          .withStatusCode(StatusCode.NOT_FOUND);
    } else {
      // Re-encode into an API resource.
      UserResource userResource = UserResource.of(userItem);

      responseBuilder
          .withBody(userResource.toJsonString())
          .withStatusCode(StatusCode.OK);
    }

    return responseBuilder.build();
  }
}
