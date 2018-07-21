package com.stevezero.aws.api.exceptions;

import com.stevezero.aws.api.http.StatusCode;

/**
 * Thrown when an invalid/unparseable resource ID is found.
 * Checked exception by design.
 */
public class InvalidResourceIdException extends ApiException {
  public InvalidResourceIdException(String id) {
    this.message = "Resource ID " + id + " is not valid.";
    this.returnCode = StatusCode.NOT_FOUND;
  }
}
