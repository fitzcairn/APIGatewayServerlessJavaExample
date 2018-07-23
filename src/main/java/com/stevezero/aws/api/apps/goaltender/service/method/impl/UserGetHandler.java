package com.stevezero.aws.api.apps.goaltender.service.method.impl;

import com.amazonaws.services.lambda.runtime.Context;
import com.stevezero.aws.api.apps.goaltender.id.impl.UserId;
import com.stevezero.aws.api.apps.goaltender.resource.GoalTenderResourceType;
import com.stevezero.aws.api.apps.goaltender.resource.impl.UserResource;
import com.stevezero.aws.api.apps.goaltender.service.method.GoalTenderApiMethodHandler;
import com.stevezero.aws.api.apps.goaltender.storage.items.impl.UserItem;
import com.stevezero.aws.api.apps.goaltender.storage.service.impl.UserStorageService;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.id.ApiResourceId;
import com.stevezero.aws.api.service.ApiGatewayProxyRequest;
import com.stevezero.aws.api.service.ApiGatewayProxyResponse;
import com.stevezero.aws.api.service.ApiPath;
import com.stevezero.aws.api.storage.service.StorageService;

/**
 * Handler for GET /user/{ID}
 * Look up user and return information, or not found.
 */
public class UserGetHandler extends GoalTenderApiMethodHandler {
  public UserGetHandler() {
    super(new UserStorageService());
  }

  // For testing only.
  UserGetHandler(StorageService storageService) {
    super(storageService);
  }

  @Override
  public ApiGatewayProxyResponse handleRequest(ApiGatewayProxyRequest request,
                                               ApiPath parsedApiPath,
                                               Context context) throws ApiException {
    ApiGatewayProxyResponse.Builder responseBuilder = new ApiGatewayProxyResponse.Builder();

    // Pull out the ID.
    ApiResourceId userId = new UserId(parsedApiPath.getIdFor(GoalTenderResourceType.USER));

    // Attempt to fetch from storage.
    UserItem userItem = (UserItem)(storageService.get(userId));

    if (userItem == null) {
      responseBuilder
          .withStatusCode(StatusCode.NOT_FOUND);
    } else {
      // Re-encode into an API resource.
      UserResource userResource = new UserResource(userItem);

      responseBuilder
          .withBody(userResource.toJsonString())
          .withStatusCode(StatusCode.OK);
    }

    return responseBuilder.build();
  }
}
