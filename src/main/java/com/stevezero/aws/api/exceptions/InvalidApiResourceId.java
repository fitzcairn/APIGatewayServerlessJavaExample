package com.stevezero.aws.api.exceptions;

import com.stevezero.aws.api.http.StatusCode;

/**
 * Thrown when an invalid/unparseable resource ID is found.
 * Checked exception by design.
 */
public class InvalidApiResourceId extends ApiException {
  public InvalidApiResourceId(String id) {
    this.message = "Resource ID " + id + " is not valid.";
    this.returnCode = StatusCode.NOT_FOUND;
  }
}
