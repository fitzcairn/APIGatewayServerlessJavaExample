package com.stevezero.aws.goaltender.exceptions;

import com.stevezero.aws.goaltender.http.StatusCode;

/**
 * Thrown when an invalid method is invoked on a resource.
 * Checked exception by design.
 */
public class InvalidAPIMethod extends GoalTenderAPIException {
  public InvalidAPIMethod(String method) {
    this.message = method + " is not a valid method on this resource.";
    this.returnCode = StatusCode.NOT_ALLOWED;
  }
}
