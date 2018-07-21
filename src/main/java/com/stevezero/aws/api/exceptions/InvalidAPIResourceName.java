package com.stevezero.aws.api.exceptions;

import com.stevezero.aws.api.http.StatusCode;
import com.stevezero.aws.api.exceptions.APIException;

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
