package com.stevezero.aws.api.exceptions;

import com.stevezero.aws.api.apps.goaltender.resource.GoalTenderResourceType;
import com.stevezero.aws.api.http.StatusCode;

/**
 * Create a resource that already exists.
 */
public class InvalidApiResourceUpdate extends ApiException {
  public InvalidApiResourceUpdate(GoalTenderResourceType type, String id) {
    this.message = "Resource of type " + type.toString() + " with id " + id + " does not exist.";
    this.returnCode = StatusCode.CONFLICT;
  }
}
