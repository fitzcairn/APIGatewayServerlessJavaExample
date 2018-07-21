package com.stevezero.aws.api.goaltender.service;

import com.amazonaws.services.lambda.runtime.Context;
import com.stevezero.aws.api.ApiGatewayProxyRequest;
import com.stevezero.aws.api.ApiGatewayProxyResponse;
import com.stevezero.aws.api.goaltender.common.ResourceType;
import com.stevezero.aws.api.exceptions.impl.InvalidAPIResource;
import com.stevezero.aws.api.exceptions.impl.InvalidUserIdException;
import com.stevezero.aws.api.id.ResourceId;
import com.stevezero.aws.api.goaltender.id.impl.UserId;
import com.stevezero.aws.api.storage.service.StorageService;

/**
 * Defines an API method handler, and holds common functionality.
 */
abstract public class ApiMethodHandler {

  /**
   * Handles a method call on the API.
   * @param request the API request.
   * @param context the lambda execution context.
   * @param storageService the underlying storage service.
   * @return
   */
  abstract public ApiGatewayProxyResponse handle(ApiGatewayProxyRequest request,
                                                 Context context,
                                                 StorageService storageService);

  /**
   * Extract the resource ID from the path.
   * @param pathString
   * @return
   * @throws InvalidAPIResource
   * @throws InvalidUserIdException
   */
  public ResourceId extractIdFromPath(String pathString, ResourceType resourceType)
      throws InvalidAPIResource, InvalidUserIdException {
    // Ex: /user/ID
    String[] components = pathString.split("/");

    // Make sure we have expected input.
    if (components.length != 3 || !resourceType.toString().equals(components[1]))
      throw new InvalidAPIResource("Invalid API call.");

    switch(resourceType) {
      case USER:
        return UserId.fromEncoded(components[2]);
      case GOAL:
        // TODO: implement.
      default:
        throw new InvalidAPIResource("Invalid API call.");
    }
  }
}
