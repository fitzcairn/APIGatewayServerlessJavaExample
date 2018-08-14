package com.stevezero.aws.api.apps.goaltender.service;

import com.stevezero.aws.api.apps.goaltender.resource.GoalTenderResourceType;
import com.stevezero.aws.api.apps.goaltender.service.method.impl.*;
import com.stevezero.aws.api.exceptions.ApiException;
import com.stevezero.aws.api.exceptions.InvalidApiMethod;
import com.stevezero.aws.api.exceptions.InvalidApiResourceName;
import com.stevezero.aws.api.http.MethodType;
import com.stevezero.aws.api.service.ApiPath;
import com.stevezero.aws.api.service.ApiProxyHandler;
import com.stevezero.aws.api.service.method.ApiMethodHandler;

/**
 * Entry point for handling handlers for the GoalTender API.
 */
public class GoalTenderProxyApiHandler extends ApiProxyHandler {

  public GoalTenderProxyApiHandler() {
    super(GoalTenderResourceType.class);
  }

  @Override
  protected ApiMethodHandler getMethodHandler(MethodType methodType, ApiPath parsedApiPath)
      throws ApiException {
    // Instantiate and return only the handler for specific resource/method in the API invocation.
    GoalTenderResourceType resourceType = (GoalTenderResourceType)parsedApiPath.getLastResource();
    switch(resourceType) {
      case USER:
        // Instantiate handler for method on User resource.
        switch(methodType) {
          case GET:
            return new UserGetHandler();
          case PUT:
            return new UserPutHandler();
          case POST:
          default:
            throw new InvalidApiMethod(methodType, resourceType);
        }
      case GOAL:
        // Instantiate handler for method on Goal resource.
        switch(methodType) {
          case GET:
            return new GoalGetHandler();
          case PUT:
            return new GoalPutHandler();
          case POST:
            return new GoalPostHandler();
          default:
            throw new InvalidApiMethod(methodType, resourceType);
        }
      default:
        throw new InvalidApiResourceName(resourceType.toString());
    }
  }
}
