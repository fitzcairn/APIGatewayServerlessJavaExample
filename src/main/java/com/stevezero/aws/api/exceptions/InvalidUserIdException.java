package com.stevezero.aws.api.exceptions.impl;

import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.exceptions.APIException;

/**
 * Thrown when an invalid/unparseable user ID is found.
 * Checked exception by design.
 */
public class InvalidUserIdException extends APIException {
  public InvalidUserIdException(String id) {
    this.message = "User ID " + id + " is not valid.";
    this.returnCode = StatusCode.NOT_FOUND;
  }
}
