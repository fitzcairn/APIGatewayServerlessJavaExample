package com.stevezero.aws.api.apps.goaltender.service.user;

import com.stevezero.aws.api.ApiProxyHandler;
import com.stevezero.aws.api.apps.goaltender.service.resource.ResourceType;
import com.stevezero.aws.api.apps.goaltender.service.user.handlers.GetUserHandler;
import com.stevezero.aws.api.apps.goaltender.service.user.handlers.PutUserHandler;
import com.stevezero.aws.api.apps.goaltender.storage.service.impl.UserStorageService;
import com.stevezero.aws.api.http.MethodType;
import com.stevezero.aws.api.storage.service.StorageService;

/**
 * Entry point for handling handlers for the UserResource API.
 */
public class UserProxyHandler extends ApiProxyHandler {

  public UserProxyHandler() {
    super(new UserStorageService(), ResourceType.USER);
  }

  // For testing only.
  public UserProxyHandler(StorageService storageService) {
    super(storageService, ResourceType.USER);
  }

  @Override
  protected void createHandlers() {
    methodHandlers.put(MethodType.GET, new GetUserHandler());
    methodHandlers.put(MethodType.PUT, new PutUserHandler());
  }
}
