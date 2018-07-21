package com.stevezero.aws.goaltender.api.exceptions;

import com.stevezero.aws.goaltender.api.http.StatusCode;

/**
 * Thrown when an invalid resource is supplied.
 * Checked exception by design.
 */
public class InvalidAPIResource extends APIException {
  public InvalidAPIResource(String path) {
    this.message = path + " is not a valid API call.";
    this.returnCode = StatusCode.NOT_FOUND;
  }
}
