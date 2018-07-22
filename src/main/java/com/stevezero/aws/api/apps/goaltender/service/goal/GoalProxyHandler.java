package com.stevezero.aws.api.apps.goaltender.service.goal;

import com.stevezero.aws.api.ApiProxyHandler;
import com.stevezero.aws.api.apps.goaltender.service.goal.handlers.GetGoalsHandler;
import com.stevezero.aws.api.apps.goaltender.service.goal.handlers.PutGoalHandler;
import com.stevezero.aws.api.apps.goaltender.storage.service.impl.UserStorageService;
import com.stevezero.aws.api.http.MethodType;
import com.stevezero.aws.api.storage.service.StorageService;

/**
 * Entry point for handling handlers for the Goal API.
 */
public class GoalProxyHandler extends ApiProxyHandler {

  public GoalProxyHandler() {
    super(new UserStorageService());
  }

  // For testing only.
  public GoalProxyHandler(StorageService storageService) {
    super(storageService);
  }

  @Override
  protected void createHandlers() {
    methodHandlers.put(MethodType.GET, new GetGoalsHandler());
    methodHandlers.put(MethodType.PUT, new PutGoalHandler());
  }
}
