package com.stevezero.aws.api.apps.goaltender.service;

import com.stevezero.aws.api.service.ApiProxyHandler;
import com.stevezero.aws.api.apps.goaltender.service.impl.GetUserHandler;
import com.stevezero.aws.api.apps.goaltender.service.impl.PutUserHandler;
import com.stevezero.aws.api.apps.goaltender.storage.service.impl.UserStorageService;
import com.stevezero.aws.api.http.MethodType;
import com.stevezero.aws.api.storage.service.StorageService;

/**
 * Entry point for handling handlers for the User API.
 */
public class UserProxyHandler extends ApiProxyHandler {

  public UserProxyHandler() {
    super(new UserStorageService());
  }

  // For testing only.
  public UserProxyHandler(StorageService storageService) {
    super(storageService);
  }

  @Override
  protected void createHandlers() {
    methodHandlers.put(MethodType.GET, new GetUserHandler());
    methodHandlers.put(MethodType.PUT, new PutUserHandler());
  }
}
