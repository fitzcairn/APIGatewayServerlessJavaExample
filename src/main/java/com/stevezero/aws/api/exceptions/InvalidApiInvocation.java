package com.stevezero.aws.api.exceptions;

import com.stevezero.aws.api.http.StatusCode;

/**
 * Thrown when the invocation path is malformed.
 * Checked exception by design.
 */
public class InvalidApiInvocation extends ApiException {
  public InvalidApiInvocation() {
    this.message = "Malformed API call.";
    this.returnCode = StatusCode.BAD_REQUEST;
  }
  public InvalidApiInvocation(String path) {
    this.message = path + " is not a well-formed API call.";
    this.returnCode = StatusCode.BAD_REQUEST;
  }
}
