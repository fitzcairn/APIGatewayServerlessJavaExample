package com.stevezero.aws.api.goaltender.service.user.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.stevezero.aws.api.ApiGatewayProxyRequest;
import com.stevezero.aws.api.ApiGatewayProxyResponse;
import com.stevezero.aws.api.goaltender.common.ResourceType;
import com.stevezero.aws.api.goaltender.service.ApiMethodHandler;
import com.stevezero.aws.api.goaltender.service.user.User;
import com.stevezero.aws.api.goaltender.storage.items.impl.UserItem;
import com.stevezero.aws.api.storage.service.StorageService;
import com.stevezero.aws.api.goaltender.storage.service.impl.UserStorageService;
import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.id.ResourceId;

/**
 * Look up user and return information, or not found.
 */
public class GetUserHandler extends ApiMethodHandler {


  @Override
  public ApiGatewayProxyResponse handle(ApiGatewayProxyRequest request,
                                        Context context,
                                        StorageService storageService) {
    ApiGatewayProxyResponse.Builder responseBuilder = new ApiGatewayProxyResponse.Builder();
    LambdaLogger logger = context.getLogger();

    try {
      logger.log("Executing GetUserHandler");

      // Pull out the ID.
      ResourceId id = extractIdFromPath(request.getPath(), ResourceType.USER);

      UserStorageService userStorageService = (UserStorageService) storageService;
      User user = User.fromUserItem((UserItem)userStorageService.get(id));

      if (user == null) {
        responseBuilder
            .withStatusCode(StatusCode.NOT_FOUND);
      } else {
        responseBuilder
            .withBody(user.toJsonString())
            .withStatusCode(StatusCode.OK);
      }
    } catch(Exception e) {
      responseBuilder
          .withStatusCode(StatusCode.SERVER_ERROR)
          .withBody(e.toString());
    }

    return responseBuilder.build();
  }
}
