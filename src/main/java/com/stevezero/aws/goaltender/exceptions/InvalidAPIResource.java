package com.stevezero.aws.goaltender.exceptions;

import com.stevezero.aws.goaltender.http.StatusCode;

/**
 * Thrown when an invalid resource is supplied.
 * Checked exception by design.
 */
public class InvalidAPIResource extends GoalTenderAPIException {
  public InvalidAPIResource(String path) {
    this.message = path + " is not a valid API call.";
    this.returnCode = StatusCode.NOT_FOUND;
  }
}
