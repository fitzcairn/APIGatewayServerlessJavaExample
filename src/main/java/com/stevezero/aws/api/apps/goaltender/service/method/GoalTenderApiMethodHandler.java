package com.stevezero.aws.api.apps.goaltender.service.method;

import com.stevezero.aws.api.service.method.ApiMethodHandler;
import com.stevezero.aws.api.storage.service.StorageService;

public abstract class GoalTenderApiMethodHandler implements ApiMethodHandler {
  protected final StorageService storageService;

  public GoalTenderApiMethodHandler(StorageService storageService) {
    this.storageService = storageService;
  }
}
