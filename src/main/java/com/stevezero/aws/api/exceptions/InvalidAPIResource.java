package com.stevezero.aws.api.exceptions;

import com.stevezero.aws.api.http.StatusCode;

/**
 * Thrown when an invalid/unparseable resource is supplied.
 * Checked exception by design.
 */
public class InvalidApiResource extends ApiException {
  public InvalidApiResource(String type) {
    this.message = "Malformed/invalid resource of type: " + type;
    this.returnCode = StatusCode.BAD_REQUEST;
  }
}
