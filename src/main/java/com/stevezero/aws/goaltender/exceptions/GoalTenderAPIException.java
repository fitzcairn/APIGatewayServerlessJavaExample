package com.stevezero.aws.goaltender.exceptions;

import com.stevezero.aws.goaltender.http.StatusCode;

/**
 * Base class for API exceptions, checked by design.
 */
public abstract class GoalTenderAPIException extends Exception {
  protected String message = "An unkown error occurred";
  protected StatusCode returnCode = StatusCode.SERVER_ERROR;

  public StatusCode getReturnCode() {
    return returnCode;
  }

  @Override
  public String toString() {
    return message;
  }
}
