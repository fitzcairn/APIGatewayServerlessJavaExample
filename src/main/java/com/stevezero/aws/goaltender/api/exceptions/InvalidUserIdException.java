package com.stevezero.aws.goaltender.api.exceptions;

import com.stevezero.aws.goaltender.api.http.StatusCode;

/**
 * Thrown when an invalid/unparseable user ID is supplied.
 * Checked exception by design.
 */
public class InvalidUserIdException extends APIException {
  public InvalidUserIdException(String id) {
    this.message = "User ID " + id + " is not valid.";
    this.returnCode = StatusCode.NOT_FOUND;
  }
}
