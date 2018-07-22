package com.stevezero.aws.api.exceptions;

import com.stevezero.aws.api.apps.goaltender.service.resource.ResourceType;
import com.stevezero.aws.api.http.StatusCode;

/**
 * Thrown when an invalid/unparseable resource is supplied.
 * Checked exception by design.
 */
public class InvalidApiResource extends ApiException {
  public InvalidApiResource(ResourceType type) {
    this.message = "Malformed/invalid resource of type: " + type.toString();
    this.returnCode = StatusCode.BAD_REQUEST;
  }
}
