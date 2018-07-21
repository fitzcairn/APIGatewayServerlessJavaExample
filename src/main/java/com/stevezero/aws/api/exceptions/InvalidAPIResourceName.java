package com.stevezero.aws.api.exceptions;

import com.stevezero.aws.api.http.StatusCode;

/**
 * Thrown when an invalid resource name is supplied.
 * Checked exception by design.
 */
public class InvalidApiResourceName extends ApiException {
  public InvalidApiResourceName(String path) {
    this.message = path + " is not a valid API call.";
    this.returnCode = StatusCode.NOT_FOUND;
  }
}
