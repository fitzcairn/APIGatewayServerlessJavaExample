package com.stevezero.aws.api.apps.goaltender.service.method.impl;

import com.amazonaws.services.lambda.runtime.Context;
import com.stevezero.aws.api.apps.goaltender.id.impl.UserId;
import com.stevezero.aws.api.apps.goaltender.resource.GoalTenderResourceType;
import com.stevezero.aws.api.apps.goaltender.resource.impl.UserResource;
import com.stevezero.aws.api.apps.goaltender.service.method.GoalTenderApiMethodHandler;
import com.stevezero.aws.api.apps.goaltender.storage.service.impl.UserStorageService;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.exceptions.InvalidApiResource;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.id.ApiResourceId;
import com.stevezero.aws.api.service.ApiGatewayProxyRequest;
import com.stevezero.aws.api.service.ApiGatewayProxyResponse;
import com.stevezero.aws.api.service.ApiPath;
import com.stevezero.aws.api.storage.service.StorageService;

/**
 * Handler for PUT /user/{ID}
 * Update user with JSON payload data.
 */
public class UserPutHandler extends GoalTenderApiMethodHandler {
  public UserPutHandler() {
    super(new UserStorageService());
  }

  // For testing only.
  UserPutHandler(StorageService storageService) {
    super(storageService);
  }

  @Override
  public ApiGatewayProxyResponse handleRequest(ApiGatewayProxyRequest request,
                                               ApiPath parsedApiPath,
                                               Context context) throws ApiException {
    ApiGatewayProxyResponse.Builder responseBuilder = new ApiGatewayProxyResponse.Builder();

    // Pull out the ID.
    ApiResourceId userId = new UserId(parsedApiPath.getIdFor(GoalTenderResourceType.USER));

    // Get the user resource from the body.
    UserResource resource = new UserResource(request.getBody());

    // Check that the body ID matches the path ID.
    if (!userId.toBase64String().equals(resource.getResourceId().toBase64String()))
      throw new InvalidApiResource(GoalTenderResourceType.USER);

    // Upsert new item and return it.
    storageService.update(resource.toItem());

    return responseBuilder
        .withBody(resource.toJsonString())
        .withStatusCode(StatusCode.OK)
        .build();
  }
}
