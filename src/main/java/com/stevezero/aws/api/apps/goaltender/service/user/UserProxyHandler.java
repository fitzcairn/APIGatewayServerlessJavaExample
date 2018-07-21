package com.stevezero.aws.api.goaltender.service.user;

import com.stevezero.aws.api.ApiProxyHandler;
import com.stevezero.aws.api.http.MethodType;
import com.stevezero.aws.api.goaltender.service.user.handlers.GetUserHandler;
import com.stevezero.aws.api.goaltender.common.ResourceType;
import com.stevezero.aws.api.storage.service.StorageService;
import com.stevezero.aws.api.goaltender.storage.service.impl.UserStorageService;

/**
 * Entry point for handling handlers for the User API.
 */
public class UserProxyHandler extends ApiProxyHandler {

  public UserProxyHandler() {
    super(new UserStorageService(), ResourceType.USER);
  }

  // For testing only.  I'm ducking guice. :)
  public UserProxyHandler(StorageService storageService) {
    super(storageService, ResourceType.USER);
  }

  @Override
  protected void init() {
    // Right now, only GET is implemented.
    // TODO: Add other method implementations.
    methodHandlers.put(MethodType.GET, new GetUserHandler());
  }
}
