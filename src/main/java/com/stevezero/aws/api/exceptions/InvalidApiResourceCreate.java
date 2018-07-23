package com.stevezero.aws.api.exceptions;

import com.stevezero.aws.api.apps.goaltender.resource.GoalTenderResourceType;
import com.stevezero.aws.api.http.StatusCode;

/**
 * Create a resource that already exists.
 */
public class InvalidApiResourceCreate extends ApiException {
  public InvalidApiResourceCreate(GoalTenderResourceType type, String id) {
    this.message = "A resource of type " + type.toString() + " already exists with id " + id;
    this.returnCode = StatusCode.CONFLICT;
  }
}
