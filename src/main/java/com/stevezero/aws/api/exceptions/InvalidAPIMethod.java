package com.stevezero.aws.api.exceptions;

import com.stevezero.aws.api.http.StatusCode;

/**
 * Thrown when an invalid method is invoked on a resource.
 * Checked exception by design.
 */
public class InvalidApiMethod extends ApiException {
  public InvalidApiMethod(String method) {
    this.message = method + " is not a valid method on this resource.";
    this.returnCode = StatusCode.NOT_ALLOWED;
  }
}
