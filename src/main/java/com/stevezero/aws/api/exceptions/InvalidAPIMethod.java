package com.stevezero.aws.api.exceptions.impl;

import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.exceptions.APIException;

/**
 * Thrown when an invalid method is invoked on a resource.
 * Checked exception by design.
 */
public class InvalidAPIMethod extends APIException {
  public InvalidAPIMethod(String method) {
    this.message = method + " is not a valid method on this resource.";
    this.returnCode = StatusCode.NOT_ALLOWED;
  }
}
